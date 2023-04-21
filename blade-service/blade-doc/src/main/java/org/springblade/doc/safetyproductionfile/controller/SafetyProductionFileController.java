/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.doc.safetyproductionfile.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.deepoove.poi.data.PictureRenderData;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springblade.common.configurationBean.FileServer;
import org.springblade.common.constant.FilePathConstant;
import org.springblade.common.tool.CommonUtil;
import org.springblade.common.tool.DateUtils;
import org.springblade.common.tool.FileSortTest;
import org.springblade.common.tool.util.FileUtils;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.doc.biaozhunhuamuban.entity.FileParse;
import org.springblade.doc.safetyproductionfile.entity.AnbiaoSafetyproductionfileNum;
import org.springblade.doc.safetyproductionfile.entity.SafetyProductionFile;
import org.springblade.doc.safetyproductionfile.service.IAnbiaoSafetyproductionfileNumService;
import org.springblade.doc.safetyproductionfile.service.ISafetyProductionFileService;
import org.springblade.doc.safetyproductionfile.vo.SafetyProductionFileVO;
import org.springblade.system.feign.IDictClient;
import org.springblade.system.feign.ISysClient;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  控制器
 *
 * @author Blade
 * @since 2019-05-28
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/doc/SafetyProductionFile")
@Api(value = "安全生产文档", tags = "安全生产文档")
public class SafetyProductionFileController extends BladeController {

	private ISafetyProductionFileService safetyProductionFileService;

	private IDictClient dictClient;

	private FileParse fileParse;

	private ISysClient iSysClient;

	private FileServer fileServer;

	private IAnbiaoSafetyproductionfileNumService safetyproductionfileNumService;


	/**
	 *添加目录
	 * @author: hyp
	 * @date: 2019/5/30 13:50
	 * @param id
	 * @param fileName
	 * @param user
	 * @return: org.springblade.core.tool.api.R<org.springblade.anbiao.SafetyProductionFileVO>
	 */
	@PostMapping("/addDir")
	@ApiLog("安全生产文档-新增-文件夹")
	@ApiOperation(value = "新增-文件夹", notes = "传入本节点id，新增目录名称", position = 2)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "需要新增下级节点的id", required = true),
		@ApiImplicitParam(name = "fileName", value = "新增文件夹的名称", required = true),
		@ApiImplicitParam(name = "documentNumber", value = "文档编号", required = true),
		@ApiImplicitParam(name = "ismuban", value = "是否纳为模板", required = false),
		@ApiImplicitParam(name = "isRequired", value = "是否必填", required = false)
	})
	public R<SafetyProductionFileVO> addDir(@RequestParam(name="id",required=true)Integer id, String fileName,String documentNumber,String ismuban,String isRequired, BladeUser user) {
		String msg = "新增成功";
		if (user == null) {
			msg = "新增失败，未授权";
			return R.fail(msg);
		}
		if(id==0){
			//如果id为0，去默认模板的路径
			String path = "SafetyStandards\\安全生产档案\\正式文件\\安全管理平台";
			SafetyProductionFile safe = new SafetyProductionFile();
			//生成文件夹
			FileUtil.mkdir(path+ File.separator + fileName);
			//查询最大id
			Integer newId = safetyProductionFileService.selectMaxId() + 1;
			//查询下级最大sort
			Integer newSort = safetyProductionFileService.selectMaxSorByParentId(id) + 1;
			safe.setParentId(id);
			safe.setId(newId);
			safe.setDeptId(1);
			safe.setPath(path + File.separator + fileName);
			safe.setCaozuoren(user.getUserName());
			safe.setCaozuorenid(user.getUserId());
			safe.setCaozuoshijian(DateUtil.now());
			safe.setTier("0-" + newId);
			safe.setName(fileName);
			safe.setType("文件夹");
			List<SafetyProductionFileVO> list= safetyProductionFileService.getSafetyProductionFileMuBan(1);
			safe.setSort(list.size()+1);
			safe.setDocumentNumber(documentNumber);
			if ("模板".equals(ismuban)) {
				safe.setIsMuban(1);
				safe.setIs_muban(1);
			}
			if ("1".equals(isRequired)) {
				safe.setIsRequired(1);
			}
			safetyProductionFileService.save(safe);
			SafetyProductionFileVO vo = new SafetyProductionFileVO();
			BeanUtil.copyProperties(safe, vo);
			vo.setFileNum(0);
			return R.data(vo,msg);
		}else {
			SafetyProductionFile parent = safetyProductionFileService.getById(id);
			if (FileUtil.isDirectory(fileParse.getPath(parent.getPath()))) {
				//生成文件夹
				FileUtil.mkdir(fileParse.getPath(parent.getPath()) + File.separator + fileName);
			} else {
				FileUtil.mkdir(fileParse.getPath(parent.getPath()) + File.separator + fileName);
			// msg = "新增失败，该节点不是目录或不存在";
			// return R.fail(msg);
			}
			//查询最大id
			Integer newId = safetyProductionFileService.selectMaxId() + 1;
			//查询下级最大sort
			Integer newSort = safetyProductionFileService.selectMaxSorByParentId(id) + 1;
			parent.setParentId(id);
			parent.setId(newId);
			parent.setPath(parent.getPath() + File.separator + fileName);
			parent.setCaozuoren(user.getUserName());
			parent.setCaozuorenid(user.getUserId());
			parent.setCaozuoshijian(DateUtil.now());
			parent.setTier(parent.getTier() + "-" + newId);
			parent.setDeptId(parent.getDeptId());
			parent.setName(fileName);
			parent.setSort(newSort);
			if(documentNumber == null){
				if(parent.getParentId() == 0){
					parent.setDocumentNumber("");
				}else{
					List<SafetyProductionFile> sa = safetyProductionFileService.getByParentId(parent.getParentId(),parent.getDeptId());
					int saSize = sa.size();
					String saNumber = "";
					String regex=".*[a-zA-Z]+.*";
					int n = parent.getTier().length()-parent.getTier().replaceAll("-", "").length();
					if(n ==3){
						Matcher m = Pattern.compile(regex).matcher(parent.getDocumentNumber());
						if(m.matches() == false){
							saNumber = DateUtils.numberToLetter(saSize);
						}else{
							saNumber = parent.getDocumentNumber()+saSize;
						}
					}else{
						saNumber = DateUtils.numberToLetter(saSize);
					}
					parent.setDocumentNumber(saNumber);
				}
			}else{
				parent.setDocumentNumber(documentNumber);
			}
			if ("模板".equals(ismuban)) {
				parent.setIsMuban(1);
				parent.setIs_muban(1);
			}
			if ("1".equals(isRequired)) {
				parent.setIsRequired(1);
			}
			safetyProductionFileService.save(parent);
			SafetyProductionFileVO vo = new SafetyProductionFileVO();
			BeanUtil.copyProperties(parent, vo);
			vo.setFileNum(0);
			return R.data(vo,msg);
		}
	}

	/**
	 *添加文件
	 * @author: hyp
	 * @date: 2019/5/30 13:50
	 * @param id
	 * @param file
	 * @param user
	 * @return: org.springblade.core.tool.api.R<org.springblade.anbiao.SafetyProductionFileVO>
	 */
	@PostMapping("/addFile")
	@ApiLog("安全生产文档-新增-文件")
	@ApiOperation(value = "新增-文件", notes = "传入本节点id，文件", position = 2)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "需要新增下级节点的id", required = true),
		@ApiImplicitParam(name = "file", value = "新增的文件", required = true),
		@ApiImplicitParam(name = "documentNumber", value = "文档编号", required = true),
		@ApiImplicitParam(name = "ismuban", value = "是否纳为模板", required = false),
		@ApiImplicitParam(name = "isRequired", value = "是否必填", required = false)
	})
	public R<SafetyProductionFileVO> addFile(Integer id, MultipartFile file,String documentNumber,String uploadcycle,String ismuban,String isRequired, BladeUser user) throws Exception {
		SafetyProductionFile parent = safetyProductionFileService.getById(id);
		String msg = "新增成功";
		if(user==null){
			msg = "新增失败，未授权";
			return R.fail(msg);
		}
		//如果当前路径是目录
		if("文件夹".equals(parent.getType())){
			//获取文件名称
			String fileName = file.getOriginalFilename();
			List<SafetyProductionFile> byParentId = safetyProductionFileService.getByParentId(id,parent.getDeptId());
			for (int i = 0; i < byParentId.size(); i++) {
				if(fileName.equals(byParentId.get(i).getName())){
					return R.fail("已存在相同文件名称");
				}
			}
			//获得文件物理路径
			String filePath = fileParse.getPath(parent.getPath())+File.separator+fileName;

			try {
				FileUtil.mkParentDirs(filePath);
				//存入本地
				file.transferTo(FileUtil.file(filePath));
				//获取后缀名
				String expandedName =file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
				//如果是doc文件则转换为docx
				if(".doc".equals(expandedName)){
					CommonUtil.convertDocFmt(filePath, StrUtil.replace(filePath,".doc",".docx"),CommonUtil.DOCX_FMT);
					//删除源文件
					FileUtil.del(filePath);
					//得到新的文件名与文件路径
					filePath = StrUtil.replace(filePath,".doc",".docx");
					fileName = StrUtil.replace(fileName,".doc",".docx");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			//查询最大id
			Integer newId = safetyProductionFileService.selectMaxId()+1;
			//查询下级最大sort
			Integer newSort = safetyProductionFileService.selectMaxSorByParentId(id)+1;
			parent.setParentId(id);
			parent.setId(newId);
			parent.setPath(fileParse.getInsertPath(filePath));
			parent.setCaozuoren(user.getUserName());
			parent.setCaozuorenid(user.getUserId());
			parent.setCaozuoshijian(DateUtil.now());
			parent.setTier(parent.getTier()+"-"+newId);
			parent.setName(fileName);
			parent.setSort(newSort);
			parent.setType("文件");
			if(documentNumber == null){
				if(parent.getParentId() == 0){
					parent.setDocumentNumber("");
				}else{
					List<SafetyProductionFile> sa = safetyProductionFileService.getByParentId(parent.getParentId(),parent.getDeptId());
					int saSize = sa.size();
					String saNumber = DateUtils.numberToLetter(saSize);
					parent.setDocumentNumber(saNumber);
				}
			}else{
				parent.setDocumentNumber(documentNumber);
			}
			parent.setIsEdit(1);
			String updateTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			if(StringUtils.isNotBlank(uploadcycle)){
				parent.setUploadcycle(uploadcycle);
			}else{
				parent.setUploadcycle(updateTime);
			}
			if("模板".equals(ismuban)){
				parent.setIsMuban(1);
				parent.setIs_muban(1);
			}
			if("1".equals(isRequired)){
				parent.setIsRequired(1);
			}
			String mubanpath = parent.getPath().substring(0,parent.getPath().lastIndexOf("\\"));
			parent.setMubanPath(mubanpath);
			parent.setSafeid(null);
			boolean save = safetyProductionFileService.save(parent);
			//保存成功，生成对应的pdf，图片
			if(save){
				fileParse.creatFormalFile(parent);
			}
		}else{
			msg = "新增失败，该节点不是目录或不存在";
			return R.fail(msg);
		}

		SafetyProductionFileVO vo = new SafetyProductionFileVO();
		BeanUtil.copyProperties(parent,vo);
		vo.setFileNum(0);
		return R.data(vo,msg);
	}

	@PostMapping("/addImages")
	@ApiLog("安全生产文档-新增-图片文档")
	@ApiOperation(value = "新增-图片文档", notes = "传入本节点id，文件", position = 2)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "需要新增下级节点的id", required = true),
		@ApiImplicitParam(name = "files", value = "新增的图片们", required = true),
		@ApiImplicitParam(name = "documentName", value = "文档名称", required = true),
		@ApiImplicitParam(name = "documentNumber", value = "文档编号", required = true)
	})
	public R<SafetyProductionFileVO> addImages(Integer id, MultipartFile[] files, String documentName, String documentNumber,String uploadcycle,BladeUser user) throws IOException {
		SafetyProductionFile parent = safetyProductionFileService.getById(id);

		String msg = "新增成功";
		if(user==null){
			msg = "新增失败，未授权";
			return R.fail(msg);
		}
		//如果当前路径是目录
		if(files.length>10){
			return R.fail("图片数量超出限制,最大10张图片");
		}
		if("文件夹".equals(parent.getType())){
			List<SafetyProductionFile> byParentId = safetyProductionFileService.getByParentId(id,parent.getDeptId());
			for (int i = 0; i < byParentId.size(); i++) {
				if(documentName.equals(byParentId.get(i).getName())){
					return R.fail("已存在相同文件名称");
				}
			}
			int i = 0;
			Map<String,PictureRenderData> map = new HashMap<String,PictureRenderData>();
			for (MultipartFile file : files) {
				//获取文件名称
				String contentType = file.getContentType();
				if(!contentType.contains("image/")){
					return R.fail("上传类型不符,只能上传图片类型");
				}
				InputStream inputStream = file.getInputStream();
				BufferedImage sourceImg= ImageIO.read(inputStream);
				int width = sourceImg.getWidth()/2;
				int height = sourceImg.getHeight()/2;
				if(sourceImg.getWidth()/2>600){
					int bili = sourceImg.getWidth()/600;
					width = 600;
					height = sourceImg.getHeight()/bili;
				}
				PictureRenderData pictureRenderData = new PictureRenderData(width, height, ".png",file.getBytes());
				map.put("image"+i++,pictureRenderData);

			}
			//获得文件物理路径
			String filePath = fileParse.getPath(parent.getPath())+File.separator+documentName+".docx";
			//查询最大id
			Integer newId = safetyProductionFileService.selectMaxId()+1;
			//查询下级最大sort
			Integer newSort = safetyProductionFileService.selectMaxSorByParentId(id)+1;
			parent.setParentId(id);
			parent.setId(newId);
			parent.setPath(fileParse.getInsertPath(filePath));
			parent.setCaozuoren(user.getUserName());
			parent.setCaozuorenid(user.getUserId());
			parent.setCaozuoshijian(DateUtil.now());
			parent.setTier(parent.getTier()+"-"+newId);
			parent.setName(documentName+".docx");
			parent.setSort(newSort);
			parent.setType("文件");
			parent.setDocumentNumber(documentNumber);
			parent.setIsEdit(1);
			String updateTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			if(StringUtils.isNotBlank(uploadcycle)){
				parent.setUploadcycle(uploadcycle);
			}else{
				parent.setUploadcycle(updateTime);
			}
			boolean save = safetyProductionFileService.save(parent);
//			//保存成功，生成对应的pdf，图片
			if(save){
				fileParse.creatFormalImageFile(map,parent);
			}
		}else{
			msg = "新增失败，该节点不是目录或不存在";
			return R.fail(msg);
		}
//
		SafetyProductionFileVO vo = new SafetyProductionFileVO();
		BeanUtil.copyProperties(parent,vo);
		vo.setFileNum(0);
		return R.data(vo,"上传成功");
	}



	/**
	 *文件替换
	 * @author: hyp
	 * @date: 2019/5/30 13:50
	 * @param id
	 * @param file
	 * @param user
	 * @return: org.springblade.core.tool.api.R<org.springblade.anbiao.SafetyProductionFileVO>
	 */
	@PostMapping("/replaceFile")
	@ApiLog("安全生产文档-替换-文件")
	@ApiOperation(value = "替换-文件", notes = "传入本节点id，文件", position = 2)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "需要被替换节点的id", required = true),
		@ApiImplicitParam(name = "file", value = "替换的文件", required = true)
	})
	public R<SafetyProductionFileVO> replaceFile(Integer id,MultipartFile file,BladeUser user) throws Exception {
		SafetyProductionFile wenjian = safetyProductionFileService.getById(id);
		String newPath = wenjian.getPath().replaceFirst(wenjian.getName(),file.getOriginalFilename());
		String newName = file.getOriginalFilename();
		String msg = "替换成功";
		if(user==null){
			msg = "替换失败，未授权";
			return R.fail(msg);
		}
		//如果当前路径存在
		if(FileUtil.exist(fileParse.getPath(wenjian.getPath()))){
			//删除源企业模板文件
			FileUtil.del(fileParse.getPath(wenjian.getPath()));
			//删除正式文件，pdf，图片
			FileUtil.del(StrUtil.replace(StrUtil.replace(fileParse.getPath(wenjian.getPath()), FilePathConstant.SPF_PATH,FilePathConstant.SPF_PDF_PATH),".docx",".pdf"));
			FileUtil.del(StrUtil.replace(StrUtil.replace(fileParse.getPath(wenjian.getPath()), FilePathConstant.SPF_PATH,FilePathConstant.SPF_IMG_PATH),".docx",""));

		}

		try {
			FileUtil.mkParentDirs(fileParse.getPath(newPath));
			file.transferTo(FileUtil.file(fileParse.getPath(newPath)));
			//获取后缀名
			String expandedName =file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
			//如果是doc文件则转换为docx
			if(".doc".equals(expandedName)){
				CommonUtil.convertDocFmt(fileParse.getPath(newPath), StrUtil.replace(fileParse.getPath(newPath),".doc",".docx"),CommonUtil.DOCX_FMT);
				//删除源文件
				FileUtil.del(fileParse.getPath(newPath));
				newPath = StrUtil.replace(newPath,".doc",".docx");
				newName = StrUtil.replace(newName,".doc",".docx");
			}
		} catch (IOException e) {
			e.printStackTrace();
			msg="替换失败";
			return R.fail(msg);
		} catch (Exception e) {
			msg="docx转换失败";
			e.printStackTrace();
			return R.fail(msg);
		}

		wenjian.setPath(newPath);
		wenjian.setCaozuoren(user.getUserName());
		wenjian.setCaozuorenid(user.getUserId());
		wenjian.setCaozuoshijian(DateUtil.now());
		wenjian.setName(newName);
		wenjian.setIsEdit(1);
		boolean updateById = safetyProductionFileService.updateById(wenjian);

		//替换成功，生成对应的正式文件，pdf，图片
		if(updateById){
			fileParse.creatFormalFile(wenjian);
		}
		SafetyProductionFileVO vo = new SafetyProductionFileVO();
		BeanUtil.copyProperties(wenjian,vo);
		vo.setFileNum(0);
		return R.data(vo,msg);
	}

	/**
	 *删除节点
	 * @author: hyp
	 * @date: 2019/5/30 13:51
	 * @param id
	 * @return: org.springblade.core.tool.api.R
	 */
	@PostMapping("/del")
	@ApiLog("安全生产文档-删除-节点")
	@ApiOperation(value = "删除-节点", notes = "id", position = 3)
	public R del(@ApiParam(value = "主键", required = true) @RequestParam Integer id,@RequestParam Integer deptId) {
		R r = new R();
		//查询下级节点
		List<SafetyProductionFile> list = safetyProductionFileService.getByParentId(id,deptId);
		String msg="删除成功";
		//如果有下级节点,不允许删除
		if(list!=null && list.size()>0){
			msg = "该节点下存在子节点,不允许直接删除";
			r.setCode(500);
			r.setMsg(msg);
			r.setSuccess(false);
			return r;
		}else{
			SafetyProductionFile wenjian = safetyProductionFileService.getById(id);
			String filePath = fileServer.getPathPrefix()+wenjian.getPath();
//			if(FileUtil.exist(fileParse.getPath(wenjian.getPath()))){
			if(FileUtil.exist(filePath)){
				FileUtil.del(fileParse.getPath(wenjian.getPath()));
				//如果是文件类型，就继续pdf，图片
				FileUtil.del(StrUtil.replace(StrUtil.replace(fileParse.getPath(wenjian.getPath()), FilePathConstant.SPF_PATH,FilePathConstant.SPF_PDF_PATH),".docx",".pdf"));
				FileUtil.del(StrUtil.replace(StrUtil.replace(fileParse.getPath(wenjian.getPath()), FilePathConstant.SPF_PATH,FilePathConstant.SPF_IMG_PATH),".docx",""));
			}
			safetyProductionFileService.removeById(id);

		}
		return R.success(msg);
	}

	@GetMapping("/getSafetyProductionFileNameByDept")
	@ApiLog("安全生产文档-获取节点模板是否已经分配")
	@ApiOperation(value = "安全生产文档-获取节点模板是否已经分配", notes = "传入Id", position = 1)
	public R getSafetyProductionFileNameByDept(@RequestParam(required = true)  Integer Id) {
		R r = new R();
		//查询该模板是否已被分发
		SafetyProductionFileVO vo = safetyProductionFileService.getSafetyProductionFileNameByDept(null,null,null,null,Id.toString());
		if(vo != null){
			r.setCode(200);
			r.setMsg("该台账已经分配，请慎重删除！！！");
			r.setData("1");
			r.setSuccess(false);
		}else{
			r.setCode(200);
			r.setData("0");
			r.setMsg("该台账未分配，可以删除");
			r.setSuccess(false);
		}
		return r;
	}

	/**
	 *目录树
	 * @author: hyp
	 * @date: 2019/5/30 13:51
	 * @param deptId
	 * @param parentId
	 * @return: org.springblade.core.tool.api.R<java.util.List<org.springblade.anbiao.SafetyProductionFileVO>>
	 */
	@GetMapping("/tree")
	@ApiLog("安全生产文档-获取-目录树")
	@ApiOperation(value = "获取-目录树", notes = "传入deptId", position = 1)
	@ApiImplicitParams({ @ApiImplicitParam(name = "parentId", value = "上级id", required = false),
		@ApiImplicitParam(name = "deptId", value = "单位id", required = true),
		@ApiImplicitParam(name = "name", value = "名称（用于模糊查询）", required = false)
	})
	public R<List<SafetyProductionFileVO>> tree(@RequestParam Integer deptId,@RequestParam(required = true,defaultValue="0")  Integer parentId, String name) {
		List<SafetyProductionFileVO> list= safetyProductionFileService.tree(deptId,parentId,name);
		return R.data(list);
	}

	@GetMapping("/bindTree")
	@ApiLog("安全生产文档-获取-绑定树")
	@ApiOperation(value = "获取-绑定树", notes = "传入deptId", position = 1)
	@ApiImplicitParams({ @ApiImplicitParam(name = "parentId", value = "上级id", required = false),
			@ApiImplicitParam(name = "deptId", value = "单位id", required = true),
			@ApiImplicitParam(name = "biaozhunhuamubanId", value = "标准化文件目录id", required = true)
	})
	public R<List<SafetyProductionFileVO>> bindTree(@RequestParam Integer deptId,@RequestParam(required = true,defaultValue="0")  Integer parentId,@RequestParam Integer biaozhunhuamubanId) {
		List<SafetyProductionFileVO> list= safetyProductionFileService.bindTree(deptId,parentId,biaozhunhuamubanId);
		return R.data(list);
	}


	@GetMapping("/boxTree")
	@ApiLog("安全生产文档-获取-盒子树")
	@ApiOperation(value = "获取-盒子树", notes = "传入deptId,获取该单位ABCD文档的前二级目录", position = 1)
	@ApiImplicitParam(name = "deptId", value = "单位id", required = true)
	public R<List<SafetyProductionFileVO>> boxTree(@RequestParam Integer deptId) {
		List<SafetyProductionFileVO> list= safetyProductionFileService.boxTree(deptId);
		return R.data(list);
	}

	@GetMapping("/getSafetyProductionFileMuBan")
	@ApiLog("安全生产文档-获取模板数据")
	@ApiOperation(value = "获取-获取模板数据", notes = "传入deptId", position = 1)
	@ApiImplicitParam(name = "deptId", value = "单位id", required = true)
	public R<List<SafetyProductionFileVO>> getSafetyProductionFileMuBan(@RequestParam Integer deptId) {
		List<SafetyProductionFileVO> list= safetyProductionFileService.getSafetyProductionFileMuBan(deptId);
		return R.data(list);
	}

	/**
	 *图片预览
	 * @author: hyp
	 * @date: 2019/5/30 13:51
	 * @param id
	 * @return: org.springblade.core.tool.api.R<org.springblade.anbiao.SafetyProductionFileVO>
	 */
	@PostMapping("/imgPreview")
	@ApiLog("安全生产文档-预览-文件")
	@ApiOperation(value = "预览-文件", notes = "传入id", position = 9)
	public R<SafetyProductionFileVO> imgPreview(@ApiParam(value = "id", required = true)@RequestParam Integer id){
		SafetyProductionFile byId = safetyProductionFileService.getById(id);
		if(byId==null){
			return R.fail("该文件不存在对应的预览图片资源");
		}
		List<String> list = new ArrayList<String>();
		List<File> listfiles = new ArrayList<File>();
		String path = byId.getPath();
		byId.setIs_muban(byId.getIsMuban());
		if(byId.getIsEdit()==0 || byId.getIs_muban() == 1){
			path = byId.getMubanPath();
		}
		String pic = null;
		String wjname = byId.getName();
		if(wjname.contains(".pdf")){
			pic = StrUtil.replace(StrUtil.replace(fileParse.getPath(path),FilePathConstant.SPF_PATH,FilePathConstant.SPF_IMG_PATH),".pdf","");
		}else{
			pic = StrUtil.replace(StrUtil.replace(fileParse.getPath(path),FilePathConstant.SPF_PATH,FilePathConstant.SPF_IMG_PATH),".docx","");
		}
		System.out.println(pic);
		File file = new File(pic);
		File[] files=file.listFiles();
		if(files != null){
			for(int i = 0; i < files.length; i++){
				if (files[i].isFile()) {
					try{
						//String base64 = CommonUtil.encodeBase64File(files[i].getAbsolutePath());
						//list.add(base64);
						//list.add(fileParse.pathToUrl(files[i].getPath()));
						listfiles.add(new File(fileParse.pathToUrl(files[i].getPath())));
					}catch (Exception e){
						e.printStackTrace();
					}
				}
			}
			listfiles = FileSortTest.sortFileByName(listfiles, "asc");
			for (File filess : listfiles) {
				System.out.println(filess);
				System.out.println("--------------------------------");
				System.out.println(filess.getPath());
				String result= filess.getPath().replaceAll("\\\\", "//");
				System.out.println(result);
				list.add(result);
			}
		}
		SafetyProductionFileVO vo = new  SafetyProductionFileVO();
		BeanUtil.copyProperties(byId,vo);
		if(list.size() <1 ){
			pic = pic+"\\"+byId.getName().substring(0, byId.getName().indexOf("."));
			System.out.println(pic);
			file = new File(pic);
			files =file.listFiles();
			if(files != null){
				if(files.length >=1 ){
					for(int i = 0;i < files.length;i++){
						if (files[i].isFile()) {
							try{
//								list.add(fileParse.pathToUrl(files[i].getPath()));
								listfiles.add(new File(fileParse.pathToUrl(files[i].getPath())));
							}catch (Exception e){
								e.printStackTrace();
							}
						}
					}
					listfiles = FileSortTest.sortFileByName(listfiles, "asc");
					for (File filess : listfiles) {
						System.out.println(filess);
						System.out.println("--------------------------------");
						System.out.println(filess.getPath());
						String result= filess.getPath().replaceAll("\\\\", "//");
						System.out.println(result);
						list.add(result);
					}
				}
			}
			vo.setImgList(list);
		}else{
			vo.setImgList(list);
		}
		vo.setImgList(list);
		int i  = safetyProductionFileService.updatePreviewRecordById(id);
		return R.data(vo);
	}



	/**
	 *下载文件
	 * @author: hyp
	 * @date: 2019/5/30 17:18
	 * @param response
	 * @param id
	 * @return: org.springblade.core.tool.api.R<java.lang.String>
	 */
	@GetMapping("downloadFile")
	@ApiLog("安全生产文档-下载-文件")
	@ApiOperation(value = "下载-文件", notes = "根据id下载企业文件", position = 1)
	@ApiImplicitParam(name = "id", value = "id", required = true)
	public R<String> downloadSubtemplateFile(HttpServletResponse response, @RequestParam Integer id){
		SafetyProductionFile byId = safetyProductionFileService.getById(id);
		if(byId==null){
			R.fail("不存在该文件");
		}
		String path = byId.getPath();
		if(byId.getIsEdit()==0){
			path = byId.getMubanPath();
		}
		return R.data(fileParse.insertPathToUrl(path));
	}


	@GetMapping("/updateDocumentNumber")
	@ApiLog("安全生产文档-修改-编号")
	@ApiOperation(value = "修改-编号", notes = "根据id修改文档的编号", position = 1)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "documentNumber", value = "文档编号", required = true),
		@ApiImplicitParam(name = "id", value = "id", required = true)
	})
	public R updateDocumentNumber(@RequestParam Integer id,@RequestParam  String documentNumber) {
		boolean a= safetyProductionFileService.updateDocumentNumberById(id,documentNumber);
//		SafetyProductionFile byId = SafetyProductionFileService.getById(id);
//		SafetyProductionFileVO vo = new SafetyProductionFileVO();
//		BeanUtil.copyProperties(byId,vo);

		return R.success("修改文档编号成功");
	}

	@GetMapping("/swapFileSort")
	@ApiLog("安全生产文档-移动-节点")
	@ApiOperation(value = "移动-节点", notes = "根据模板文件id实现两文件排序号对调,实现文件排序上下移动", position = 1)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "originId", value = "源文件id", required = true),
		@ApiImplicitParam(name = "targetId", value = "目标文件id", required = true)
	})
	public R swapFileSort(@RequestParam Integer originId,@RequestParam  Integer targetId) {
		SafetyProductionFile origin = safetyProductionFileService.getById(originId);
		SafetyProductionFile target = safetyProductionFileService.getById(targetId);
		boolean a = safetyProductionFileService.updateSortById(originId,target.getSort());
		boolean b = safetyProductionFileService.updateSortById(targetId,origin.getSort());
		return R.success("移动成功");
	}

	@PostMapping("/aKeyGeneration")
	@ApiLog("ABCD文档-一键生成")
	@ApiOperation(value = "ABCD文档-一键生成", notes = "传入单位id", position = 8)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "机构id", required = true),
		@ApiImplicitParam(name = "isOnlyDir", value = "是否只生成目录(0否1是)", required = false),
		@ApiImplicitParam(name = "caozuoren", value = "操作时间", required = false),
		@ApiImplicitParam(name = "caozuorenid", value = "操作人id", required = false)
	})
	public R aKeyGeneration(Integer deptId,Integer isOnlyDir,String caozuoren,Integer caozuorenid,String leixingid){
		// TODO: 2019/9/3 非线程安全，改成多例模式或者用消息队列
		List<Integer> deptIds = iSysClient.getDetpIds(deptId);
//		List<Integer> deptIds = new ArrayList<>();
//		deptIds.add(5263);
		String type = "";
		List<SafetyProductionFileVO> mubanList;
		if(isOnlyDir==1){
			type = "文件夹";
			mubanList = safetyProductionFileService.getMubanTree(null,type,leixingid);
		}else{
			mubanList = safetyProductionFileService.getMubanTreeWJ(null,type,leixingid);
		}

		for (Integer id : deptIds) {
			int i = safetyProductionFileService.getCountByDetpId(id);
			//如果改机构已有文件，则跳过
			if(i>0){
				return R.success("该机构已有文件");
			}
			String deptName = iSysClient.getDeptName(id);
//			String deptName = "宿州市双泽运输有限公司";
			int maxId = safetyProductionFileService.selectMaxId()+1;
			fileParse.setId(maxId);
			fileParse.setDeptName(deptName);
			fileParse.setDeptId(id);

//			fileParse.parseAbcdMubanList(mubanList, FilePathConstant.DEFAULT_PARENT_ID,FilePathConstant.DEFAULT_TIER);
            String tier = "0-"+leixingid;
            fileParse.parseAbcdMubanList(mubanList, Integer.parseInt(leixingid),tier,null);
			List<SafetyProductionFile> list = fileParse.getAbcdList();
			fileParse.close();
			list.get(i).setCaozuoren(caozuoren);
			list.get(i).setCaozuorenid(caozuorenid);
			String formatStr2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			list.get(i).setCaozuoshijian(formatStr2);
			list.get(i).setIs_muban(1);
			list.get(i).setIsMuban(1);
			safetyProductionFileService.saveBatch(list);
		}

		return R.success("一键生成成功");
	}

	@GetMapping("/getSafetyProductionFileTree")
	@ApiLog("安全生产文档-获取目录树（运维配置）")
	@ApiOperation(value = "安全生产文档-获取目录树（运维配置）", notes = "传入deptId", position = 10)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "机构id", required = true),
		@ApiImplicitParam(name = "parentId", value = "父级ID", required = false),
		@ApiImplicitParam(name = "name", value = "名称", required = false)
	})
	public R<List<SafetyProductionFileVO>> getSafetyProductionFileTree(@RequestParam Integer deptId,@RequestParam Integer parentId,@RequestParam String name) {
		List<SafetyProductionFileVO> list= safetyProductionFileService.getSafetyProductionFileTree(deptId,parentId,name);
		return R.data(list);
	}

	@PostMapping("/insertDir")
	@ApiLog("安全生产文档-新增-文件夹(运维端)")
	@ApiOperation(value = "安全生产文档-新增-文件夹(运维端)", notes = "传入safetyProductionFile", position = 11)
	public R insertDir(@RequestBody SafetyProductionFileVO safetyProductionFile, BladeUser user) {
		SafetyProductionFile parent = null;
		SafetyProductionFileVO vo = new SafetyProductionFileVO();
		String msg = "新增成功";
		if(safetyProductionFile.getIsParent() == 1){
			parent = new SafetyProductionFile();
			//新增一级目录，默认上级ID为0
			int parentId = 0;
			//查询最大id
			Integer newId = safetyProductionFileService.selectMaxId()+1;
			//查询下级最大sort
			Integer newSort = safetyProductionFileService.selectMaxSorByParentId(parentId)+1;
			parent.setParentId(parentId);
			parent.setId(newId);
			String deptName = iSysClient.getDeptName(safetyProductionFile.getDeptId());
			parent.setDeptId(safetyProductionFile.getDeptId());
			parent.setPath(FilePathConstant.SPF_PATH+deptName+"\\"+safetyProductionFile.getName());
			parent.setMubanPath(FilePathConstant.SPF_PATH+deptName+"\\"+safetyProductionFile.getName());
			parent.setIs_muban(safetyProductionFile.getIsMuban());
			parent.setIsRequired(safetyProductionFile.getIsRequired());
			parent.setIsDeleted(0);
			parent.setIsEdit(0);
			parent.setCaozuoren(user.getUserName());
			parent.setCaozuorenid(user.getUserId());
			parent.setCaozuoshijian(DateUtil.now());
			parent.setTier(parentId+"-"+newId);
			parent.setName(safetyProductionFile.getName());
			parent.setSort(newSort);
			parent.setType("文件夹");
			safetyProductionFileService.save(parent);
//			safetyProductionFileService.insertSelective(parent);
			BeanUtil.copyProperties(parent,vo);
			vo.setFileNum(0);
			return R.data(vo,msg);
		}else{
			parent = safetyProductionFileService.getById(safetyProductionFile.getId());
			if(user==null){
				msg = "新增失败，未授权";
				return R.fail(msg);
			}
			if(FileUtil.isDirectory(fileParse.getPath(parent.getPath()))){
				//生成文件夹
				FileUtil.mkdir(fileParse.getPath(parent.getPath())+File.separator+safetyProductionFile.getName());
			}else{
				FileUtil.mkdir(fileParse.getPath(parent.getPath())+File.separator+safetyProductionFile.getName());
			}
			//查询最大id
			Integer newId = safetyProductionFileService.selectMaxId()+1;
			//查询下级最大sort
			Integer newSort = safetyProductionFileService.selectMaxSorByParentId(safetyProductionFile.getId())+1;
			parent.setParentId(safetyProductionFile.getId());
			parent.setId(newId);
			parent.setPath(parent.getPath()+ File.separator+safetyProductionFile.getName());
			parent.setMubanPath(parent.getPath());
			parent.setIsDeleted(0);
			parent.setIsEdit(0);
			parent.setIsRequired(safetyProductionFile.getIsRequired());
			parent.setCaozuoren(user.getUserName());
			parent.setCaozuorenid(user.getUserId());
			parent.setCaozuoshijian(DateUtil.now());
			parent.setTier(parent.getTier()+"-"+newId);
			parent.setName(safetyProductionFile.getName());
			parent.setSort(newSort);
			parent.setType("文件夹");
			if(safetyProductionFile.getDocumentNumber() == null){
				if(safetyProductionFile.getParentId() == 0){
					parent.setDocumentNumber("");
				}else{
					List<SafetyProductionFile> sa = safetyProductionFileService.getByParentId(parent.getParentId(),parent.getDeptId());
					int saSize = sa.size();
					String saNumber = DateUtils.numberToLetter(saSize);
					parent.setDocumentNumber(saNumber);
				}
			}else{
				parent.setDocumentNumber(safetyProductionFile.getDocumentNumber());
			}
			safetyProductionFileService.save(parent);
			BeanUtil.copyProperties(parent,vo);
			vo.setFileNum(0);
			return R.data(vo,msg);
		}
	}

	@PostMapping("/insertFile")
	@ApiLog("安全生产文档-新增-文件(运维端)")
	@ApiOperation(value = "安全生产文档-新增-文件(运维端)", notes = "传入safetyProductionFile", position = 12)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "file", value = "新增的文件", required = true),
		@ApiImplicitParam(name = "documentNumber", value = "文档编号"),
		@ApiImplicitParam(name = "uploadcycle", value = "上传周期"),
		@ApiImplicitParam(name = "isRequired", value = "是否必填"),
		@ApiImplicitParam(name = "isMuban", value = "是否纳为模板"),
		@ApiImplicitParam(name = "user", value = "用户对象", required = true)
	})
	public R<SafetyProductionFileVO> insertFile(Integer id, MultipartFile file,String documentNumber,String uploadcycle,Integer isRequired,Integer isMuban,BladeUser user) throws Exception {
		SafetyProductionFile parent = safetyProductionFileService.getById(id);
		String msg = "新增成功";
		if(user==null){
			msg = "新增失败，未授权";
			return R.fail(msg);
		}
		//如果当前路径是目录
		if("文件夹".equals(parent.getType())){
			//获取文件名称
			String fileName = file.getOriginalFilename();
			List<SafetyProductionFile> byParentId = safetyProductionFileService.getByParentId(id,parent.getDeptId());
			for (int i = 0; i < byParentId.size(); i++) {
				if(fileName.equals(byParentId.get(i).getName())){
					return R.fail("已存在相同文件名称");
				}
			}
			//获得文件物理路径
			String filePath = fileParse.getPath(parent.getPath())+File.separator+fileName;
			try {
				FileUtil.mkParentDirs(filePath);
				//存入本地
				file.transferTo(FileUtil.file(filePath));
				//获取后缀名
				String expandedName =file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
				//如果是doc文件则转换为docx
				if(".doc".equals(expandedName)){
					CommonUtil.convertDocFmt(filePath, StrUtil.replace(filePath,".doc",".docx"),CommonUtil.DOCX_FMT);
					//删除源文件
					FileUtil.del(filePath);
					//得到新的文件名与文件路径
					filePath = StrUtil.replace(filePath,".doc",".docx");
					fileName = StrUtil.replace(fileName,".doc",".docx");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			//查询最大id
			Integer newId = safetyProductionFileService.selectMaxId()+1;
			//查询下级最大sort
			Integer newSort = safetyProductionFileService.selectMaxSorByParentId(id)+1;
			parent.setParentId(id);
			parent.setId(newId);
			parent.setPath(fileParse.getInsertPath(filePath));
			if(isMuban == 1){
				parent.setMubanPath(parent.getPath());
			}
			parent.setCaozuoren(user.getUserName());
			parent.setCaozuorenid(user.getUserId());
			parent.setCaozuoshijian(DateUtil.now());
			parent.setTier(parent.getTier()+"-"+newId);
			parent.setName(fileName);
			parent.setSort(newSort);
			parent.setType("文件");
			parent.setDocumentNumber(documentNumber);
			parent.setIsEdit(0);
			parent.setIsRequired(isRequired);
			parent.setIsMuban(isMuban);
			String updateTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			if(StringUtils.isNotBlank(uploadcycle)){
				parent.setUploadcycle(uploadcycle);
			}else{
				parent.setUploadcycle(updateTime);
			}
			String mubanpath = parent.getPath().substring(0,parent.getPath().lastIndexOf("\\"));
			parent.setMubanPath(mubanpath);
			boolean save = safetyProductionFileService.save(parent);
			//保存成功，生成对应的pdf，图片
			if(save){
				fileParse.creatFormalFile(parent);
			}
		}else{
			msg = "新增失败，该节点不是目录或不存在";
			return R.fail(msg);
		}
		SafetyProductionFileVO vo = new SafetyProductionFileVO();
		BeanUtil.copyProperties(parent,vo);
		vo.setFileNum(0);
		return R.data(vo,msg);
	}

	@GetMapping("/getSafetyProductionFileById")
	@ApiLog("安全生产文档-根据项ID获取项详情")
	@ApiOperation(value = "安全生产文档-根据项ID获取项详情", notes = "传入项ID", position = 13)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "项ID", required = true)
	})
	public R getSafetyProductionFileById(@RequestParam Integer id) {
		SafetyProductionFile safetyProductionFile = safetyProductionFileService.getById(id);
		return R.data(safetyProductionFile);
	}

	@PostMapping("/updateSelective")
	@ApiLog("安全生产文档-编辑")
	@ApiOperation(value = "安全生产文档-编辑", notes = "传入safetyProductionFile", position = 14)
	public R updateSelective(@RequestBody SafetyProductionFile safetyProductionFile, BladeUser user) {
		SafetyProductionFile vo = new SafetyProductionFile();
		String msg = "编辑成功";
		if(user==null){
			msg = "编辑失败，未授权";
			return R.fail(msg);
		}
		//根据path路径文件名称判断项是否改名称
		String expandedName = safetyProductionFile.getPath().substring(safetyProductionFile.getPath().lastIndexOf("\\"));
		String newpath = safetyProductionFile.getPath().substring(0,safetyProductionFile.getPath().lastIndexOf("\\"));
		if(!expandedName.equals(safetyProductionFile.getName())){
			//生成文件夹
			FileUtil.mkdir(fileParse.getPath(newpath)+File.separator+safetyProductionFile.getName());
		}
		vo.setId(safetyProductionFile.getId());
		vo.setPath(newpath+ File.separator+safetyProductionFile.getName());
		vo.setMubanPath(safetyProductionFile.getMubanPath());
		vo.setIsDeleted(0);
		vo.setIsEdit(0);
		if ("模板".equals(safetyProductionFile.getIsMubanShow())) {
			vo.setIsMuban(1);
		}else{
			vo.setIsMuban(0);
		}
//		if (1 == safetyProductionFile.getIsRequired()) {
//			vo.setIsRequired(safetyProductionFile.getIsRequired());
//		}
		vo.setIsRequired(safetyProductionFile.getIsRequired());
		vo.setCaozuoren(user.getUserName());
		vo.setCaozuorenid(user.getUserId());
		vo.setCaozuoshijian(DateUtil.now());
		vo.setTier(safetyProductionFile.getTier());
		vo.setName(safetyProductionFile.getName());
		vo.setSort(safetyProductionFile.getSort());
		vo.setType(safetyProductionFile.getType());
		vo.setParentId(safetyProductionFile.getParentId());
		vo.setDocumentNumber(safetyProductionFile.getDocumentNumber());
		safetyProductionFileService.updateSelective(vo);
		return R.data(vo,msg);
	}

	@GetMapping("/getMaxDocumentNumber")
	@ApiLog("安全生产文档-获取同级目录下最大的文档编号")
	@ApiOperation(value = "获取-绑定树", notes = "传入deptId", position = 15)
	@ApiImplicitParams({ @ApiImplicitParam(name = "parentId", value = "上级id", required = true),
		@ApiImplicitParam(name = "deptId", value = "单位id", required = true),
	})
	public R<SafetyProductionFileVO> getMaxDocumentNumber(@RequestParam Integer deptId,@RequestParam Integer parentId) {
		SafetyProductionFileVO list= safetyProductionFileService.selectMaxDocumentNumber(parentId,deptId);
		return R.data(list);
	}

	/**
	 *添加文件（文件路径）
	 * @author: hyp
	 * @date: 2019/5/30 13:50
	 * @param id
	 * @param path
	 * @param user
	 * @return: org.springblade.core.tool.api.R<org.springblade.anbiao.SafetyProductionFileVO>
	 */
	@PostMapping("/addFileUrl")
	@ApiLog("安全生产文档-新增-文件（文件路径）")
	@ApiOperation(value = "新增-文件（文件路径）", notes = "传入本节点id，文件", position = 16)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "需要新增下级节点的id", required = true),
		@ApiImplicitParam(name = "path", value = "新增的文件路径", required = true)
	})
	public R<SafetyProductionFileVO> addFileUrl(Integer id, String path,Integer type,BladeUser user) throws Exception {
		R r = new R();
		SafetyProductionFile parent = safetyProductionFileService.getById(id);
		String msg = "新增成功";
		if(user==null){
			msg = "新增失败，未授权";
			return R.fail(msg);
		}
		if(type == 0){
			//如果当前路径是目录
			if("文件夹".equals(parent.getType())){
				//获取文件名称
				String fileName1 = path.substring(path.lastIndexOf("\\")+1);
				String fileName = fileName1.substring(0, fileName1.indexOf("."));
				List<SafetyProductionFile> byParentId = safetyProductionFileService.getByParentId(id,parent.getDeptId());
				for (int i = 0; i < byParentId.size(); i++) {
					if(fileName1.equals(byParentId.get(i).getName())){
						return R.fail("台账文件已存在");
					}
				}
				//获得文件物理路径
				String filePath = fileParse.getPath(parent.getPath())+File.separator+fileName+".docx";
				try {
					FileUtil.mkParentDirs(filePath);
					// 创建文件实例
					System.out.println(path);
					File file = new File(path);
					InputStream inputStream = new FileInputStream(file);
					MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
					log.info("file转multipartFile成功. {}",multipartFile);
	//				FileInputStream inputStream = new FileInputStream(file);
	//				MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
					// 将接收的文件保存到指定文件中
					multipartFile.transferTo(FileUtil.file(filePath));
					//获取后缀名
					String expandedName = multipartFile.getName().substring(multipartFile.getName().lastIndexOf('.'));
					//如果是doc文件则转换为docx
					if(".doc".equals(expandedName)){
						CommonUtil.convertDocFmt(filePath, StrUtil.replace(filePath,".doc",".docx"),CommonUtil.DOCX_FMT);
						//删除源文件
						FileUtil.del(filePath);
						//得到新的文件名与文件路径
						filePath = StrUtil.replace(filePath,".doc",".docx");
						fileName = StrUtil.replace(fileName,".doc",".docx");
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				//查询最大id
				Integer newId = safetyProductionFileService.selectMaxId()+1;
				//查询下级最大sort
				Integer newSort = safetyProductionFileService.selectMaxSorByParentId(id)+1;
				parent.setParentId(id);
				parent.setId(newId);
				parent.setPath(fileParse.getInsertPath(filePath));
				parent.setCaozuoren(user.getUserName());
				parent.setCaozuorenid(user.getUserId());
				parent.setCaozuoshijian(DateUtil.now());
				parent.setTier(parent.getTier()+"-"+newId);
				parent.setName(fileName1);
				parent.setSort(newSort);
				parent.setType("文件");
				if(parent.getParentId() == 0){
					parent.setDocumentNumber("");
				}else{
					List<SafetyProductionFile> sa = safetyProductionFileService.getByParentId(parent.getParentId(),parent.getDeptId());
					int saSize = sa.size();
					String saNumber = DateUtils.numberToLetter(saSize);
					parent.setDocumentNumber(saNumber);
				}
				parent.setIsEdit(1);
				String updateTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				parent.setUploadcycle(updateTime);
				String mubanpath = parent.getPath().substring(0,parent.getPath().lastIndexOf("\\"));
				parent.setMubanPath(mubanpath);
				boolean save = safetyProductionFileService.save(parent);
				//保存成功，生成对应的pdf，图片
				if(save){
					fileParse.creatFormalFile(parent);
				}
			}else{
				msg = "新增失败，该节点不是目录或不存在";
				return R.fail(msg);
			}
			SafetyProductionFileVO vo = new SafetyProductionFileVO();
			BeanUtil.copyProperties(parent,vo);
			vo.setFileNum(0);
			return R.data(vo,msg);
		}else{
			//如果当前路径是目录
			if("文件夹".equals(parent.getType())){
				//获取文件名称
				String fileName1 = path.substring(path.lastIndexOf("\\")+1);
				String fileName = fileName1.substring(0, fileName1.indexOf("."));
				List<SafetyProductionFile> byParentId = safetyProductionFileService.getByParentId(id,parent.getDeptId());
				for (int i = 0; i < byParentId.size(); i++) {
					if(fileName1.equals(byParentId.get(i).getName())){
						SafetyProductionFile wenjian = safetyProductionFileService.getById(byParentId.get(i).getId());
						if(FileUtil.exist(fileParse.getPath(wenjian.getPath()))){
							FileUtil.del(fileParse.getPath(wenjian.getPath()));
							//如果是文件类型，就继续pdf，图片
							FileUtil.del(StrUtil.replace(StrUtil.replace(fileParse.getPath(wenjian.getPath()), FilePathConstant.SPF_PATH,FilePathConstant.SPF_PDF_PATH),".docx",".pdf"));
							FileUtil.del(StrUtil.replace(StrUtil.replace(fileParse.getPath(wenjian.getPath()), FilePathConstant.SPF_PATH,FilePathConstant.SPF_IMG_PATH),".docx",""));
						}
						safetyProductionFileService.removeById(byParentId.get(i).getId());
					}
				}
				//获得文件物理路径
				String filePath = fileParse.getPath(parent.getPath())+File.separator+fileName+".docx";
				try {
					FileUtil.mkParentDirs(filePath);
					// 创建文件实例
					System.out.println(path);
					File file = new File(path);
					InputStream inputStream = new FileInputStream(file);
					MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
					log.info("file转multipartFile成功. {}",multipartFile);
	//				FileInputStream inputStream = new FileInputStream(file);
	//				MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
					// 将接收的文件保存到指定文件中
					multipartFile.transferTo(FileUtil.file(filePath));
					//获取后缀名
					String expandedName = multipartFile.getName().substring(multipartFile.getName().lastIndexOf('.'));
					//如果是doc文件则转换为docx
					if(".doc".equals(expandedName)){
						CommonUtil.convertDocFmt(filePath, StrUtil.replace(filePath,".doc",".docx"),CommonUtil.DOCX_FMT);
						//删除源文件
						FileUtil.del(filePath);
						//得到新的文件名与文件路径
						filePath = StrUtil.replace(filePath,".doc",".docx");
						fileName = StrUtil.replace(fileName,".doc",".docx");
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				//查询最大id
				Integer newId = safetyProductionFileService.selectMaxId()+1;
				//查询下级最大sort
				Integer newSort = safetyProductionFileService.selectMaxSorByParentId(id)+1;
				parent.setParentId(id);
				parent.setId(newId);
				parent.setPath(fileParse.getInsertPath(filePath));
				parent.setCaozuoren(user.getUserName());
				parent.setCaozuorenid(user.getUserId());
				parent.setCaozuoshijian(DateUtil.now());
				parent.setTier(parent.getTier()+"-"+newId);
				parent.setName(fileName1);
				parent.setSort(newSort);
				parent.setType("文件");
				if(parent.getParentId() == 0){
					parent.setDocumentNumber("");
				}else{
					List<SafetyProductionFile> sa = safetyProductionFileService.getByParentId(parent.getParentId(),parent.getDeptId());
					int saSize = sa.size();
					String saNumber = DateUtils.numberToLetter(saSize);
					parent.setDocumentNumber(saNumber);
				}
				parent.setIsEdit(1);
				String updateTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				parent.setUploadcycle(updateTime);
				String mubanpath = parent.getPath().substring(0,parent.getPath().lastIndexOf("\\"));
				parent.setMubanPath(mubanpath);
				boolean save = safetyProductionFileService.save(parent);
				//保存成功，生成对应的pdf，图片
				if(save){
					new Thread(new Runnable() {
						@SneakyThrows
						@Override
						public void run() {
							fileParse.creatFormalFile(parent);
						}
					}).start();
				}
			}else{
				msg = "新增失败，该节点不是目录或不存在";
				return R.fail(msg);
			}
//			SafetyProductionFileVO vo = new SafetyProductionFileVO();
//			BeanUtil.copyProperties(parent,vo);
//			vo.setFileNum(0);
//			return R.data(vo,msg);
			r.setData(null);
			r.setCode(200);
			r.setMsg("生成成功");
			return r;
		}
	}

	/**
	 *添加文件夹
	 * @author: hyp
	 * @date: 2019/5/30 13:50
	 * @param id
	 * @param dir
	 * @return: org.springblade.core.tool.api.R<org.springblade.anbiao.SafetyProductionFileVO>
	 */
	@PostMapping("/addFolder")
	@ApiLog("安全生产文档-新增-文件夹")
	@ApiOperation(value = "新增-文件夹", notes = "传入本节点id，文件夹", position = 2)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "需要新增下级节点的id", required = true),
		@ApiImplicitParam(name = "dir", value = "新增的文件夹", required = true),
		@ApiImplicitParam(name = "documentNumber", value = "文档编号", required = true),
		@ApiImplicitParam(name = "ismuban", value = "是否纳为模板", required = false),
		@ApiImplicitParam(name = "isRequired", value = "是否必填", required = false)
	})
	public R<SafetyProductionFileVO> addFolder(Integer id, MultipartFile[] dir,String documentNumber,String uploadcycle,String ismuban,String isRequired, Integer userid,String username) throws Exception {
		SafetyProductionFile parent = safetyProductionFileService.getById(id);
		String msg = "新增成功";
		if(userid ==null && username == null){
			msg = "新增失败，未授权";
			return R.fail(msg);
		}
		File file;
		System.out.println(dir.length);
		if(dir.length > 0) {
			//如果当前路径是目录
			if("文件夹".equals(parent.getType())) {
				for (MultipartFile f : dir) {
					SafetyProductionFile safetyProductionFile = new SafetyProductionFile();
					String fileName="";
					String filePath="";
					String fileNewPath = "";
					fileName=f.getOriginalFilename();
					List<SafetyProductionFile> byParentId = safetyProductionFileService.getByParentId(id,parent.getDeptId());
					for (int i = 0; i < byParentId.size(); i++) {
						if(fileName.equals(byParentId.get(i).getName())){
							return R.fail("已存在相同文件名称");
						}
					}
					//获得文件物理路径
					filePath = fileParse.getPath(parent.getPath())+"\\"+fileName.substring(0,fileName.lastIndexOf("/"));
//						+"/"+fileName.substring(0,fileName.lastIndexOf("/"));

					//获取后缀名
					String expandedName =f.getOriginalFilename().substring(f.getOriginalFilename().lastIndexOf('.'));
					//如果是doc文件则转换为docx
					if(".doc".equals(expandedName)){
						CommonUtil.convertDocFmt(filePath, StrUtil.replace(filePath,".doc",".docx"),CommonUtil.DOCX_FMT);
						//删除源文件
						FileUtil.del(filePath);
						//得到新的文件名与文件路径
						filePath = StrUtil.replace(filePath,".doc",".docx");
						fileName = StrUtil.replace(fileName,".doc",".docx");
					}

					String type=f.getContentType();
					System.out.println("\n"+fileName+" ,"+type);
					System.out.println(filePath);
					//创建生成文件路径
					if(!FileUtils.isDir(filePath)){
						FileUtils.makeDirs(filePath);
					}
					int index = fileName.indexOf("/");//获取第一个_索引
					String str0 = fileName.substring(0, index);
					fileName = fileName.substring(str0.length() + 1, fileName.length());

					fileName = fileName.substring(fileName.lastIndexOf("/")+1);
					fileNewPath = filePath+"\\"+ fileName;
					file = new File(fileNewPath);
					try {
						System.out.println(file);
						file.createNewFile();
						//存入本地
						f.transferTo(file);
					} catch (IOException e) {
						e.printStackTrace();
					}

					//生成数据，添加至数据库
					//查询最大id
					Integer newId = safetyProductionFileService.selectMaxId()+1;
					//查询下级最大sort
					Integer newSort = safetyProductionFileService.selectMaxSorByParentId(id)+1;
					safetyProductionFile.setParentId(id);
					safetyProductionFile.setId(newId);
					safetyProductionFile.setDeptId(parent.getDeptId());
					safetyProductionFile.setPath(fileParse.getInsertPath(fileNewPath));
					safetyProductionFile.setCaozuoren(username);
					safetyProductionFile.setCaozuorenid(userid);
					safetyProductionFile.setCaozuoshijian(DateUtil.now());
					safetyProductionFile.setTier(parent.getTier()+"-"+newId);
					safetyProductionFile.setName(fileName);
					safetyProductionFile.setSort(newSort);
					safetyProductionFile.setType("文件");
					if(documentNumber == null){
						if(parent.getParentId() == 0){
							safetyProductionFile.setDocumentNumber("");
						}else{
							List<SafetyProductionFile> sa = safetyProductionFileService.getByParentId(parent.getParentId(),parent.getDeptId());
							int saSize = sa.size();
							String saNumber = DateUtils.numberToLetter(saSize);
							safetyProductionFile.setDocumentNumber(saNumber);
						}
					}else{
						safetyProductionFile.setDocumentNumber(documentNumber);
					}
					safetyProductionFile.setIsEdit(1);
					String updateTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					if(StringUtils.isNotBlank(uploadcycle)){
						safetyProductionFile.setUploadcycle(uploadcycle);
					}else{
						safetyProductionFile.setUploadcycle(updateTime);
					}
					if("模板".equals(ismuban)){
						safetyProductionFile.setIsMuban(1);
					}else{
						safetyProductionFile.setIsMuban(0);
					}
					if("1".equals(isRequired)){
						safetyProductionFile.setIsRequired(1);
					}else{
						safetyProductionFile.setIsRequired(0);
					}
					safetyProductionFile.setCreatetime(DateUtil.format(new Date(),"yyyy-MM-dd"));
					safetyProductionFile.setIsDeleted(0);

					String mubanpath = safetyProductionFile.getPath().substring(0,safetyProductionFile.getPath().lastIndexOf("\\"));
					safetyProductionFile.setMubanPath(mubanpath);
					System.out.println("1111111111111111111111111111");
					System.out.println(parent);
					System.out.println("1111111111111111111111111111");
					boolean save = safetyProductionFileService.save(safetyProductionFile);
					//保存成功，生成对应的pdf，图片
					if(save){
						fileParse.creatFormalFile(safetyProductionFile);
					}
				}
			}else{
				msg = "新增失败，该节点不是目录或不存在";
				return R.fail(msg);
			}
		}else{
			msg = "新增失败，该文件夹中无文件";
			return R.fail(msg);
		}
		SafetyProductionFileVO vo = new SafetyProductionFileVO();
		BeanUtil.copyProperties(parent,vo);
		vo.setFileNum(0);
		return R.data(vo,msg);
	}

	@GetMapping("/moveFileSort")
	@ApiLog("安全生产文档-移动")
	@ApiOperation(value = "移动-节点", notes = "根据模板文件id实现两文件排序号对调,实现文件排序上下移动", position = 1)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "originId", value = "源文件Id", required = true),
		@ApiImplicitParam(name = "targetId", value = "目标文件夹Id", required = true)
	})
	public R moveFileSort(@RequestParam Integer originId,@RequestParam Integer targetId,BladeUser user) {
		R r = new R();
		if (user == null) {
			r.setCode(401);
			r.setMsg("未授权");
			r.setSuccess(false);
			return r;
		}
		SafetyProductionFile target = safetyProductionFileService.getById(targetId);
		String tier = target.getTier()+"-"+originId;
		SafetyProductionFile safetyProductionFile = new SafetyProductionFile();
		safetyProductionFile.setTier(tier);
		safetyProductionFile.setId(originId);
		safetyProductionFile.setParentId(targetId);
		safetyProductionFile.setCaozuorenid(user.getUserId());
		safetyProductionFile.setCaozuoren(user.getUserName());
		boolean a = safetyProductionFileService.updaTierById(safetyProductionFile);
		if(a){
			r.setSuccess(true);
			r.setCode(200);
			r.setMsg("移动成功");
		}else{
			r.setSuccess(false);
			r.setCode(500);
			r.setMsg("移动失败");
		}
		return r;
	}

	@PostMapping("/aKeyGenerationUpdate")
	@ApiLog("ABCD文档-一键生成（更新）")
	@ApiOperation(value = "ABCD文档-一键生成（更新）", notes = "传入单位id", position = 8)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "机构id", required = true),
		@ApiImplicitParam(name = "isOnlyDir", value = "是否只生成目录(0否1是)", required = false),
		@ApiImplicitParam(name = "caozuoren", value = "操作时间", required = false),
		@ApiImplicitParam(name = "caozuorenid", value = "操作人id", required = false)
	})
	public R aKeyGenerationUpdate(Integer deptId,Integer isOnlyDir,String caozuoren,Integer caozuorenid,String leixingid){
		// 非线程安全，改成多例模式或者用消息队列
		List<Integer> deptIds = iSysClient.getDetpIds(deptId);
		String type = "";
		List<SafetyProductionFileVO> mubanList;
		if(isOnlyDir==1){
			type = "文件夹";
			mubanList = safetyProductionFileService.getMubanTree(null,type,leixingid);
		}else{
			mubanList = safetyProductionFileService.getMubanTreeWJ(null,type,leixingid);
		}

		for (Integer id : deptIds) {
			int i = safetyProductionFileService.getCountByDetpId(id);
			//如果该机构已有文件，则更新
			if(i>0){
				String deptName = iSysClient.getDeptName(id);
				int maxId = safetyProductionFileService.selectMaxId()+1;
				fileParse.setId(maxId);
				fileParse.setDeptName(deptName);
				fileParse.setDeptId(id);
				String tier = "0-"+leixingid;
				System.out.println(mubanList);
				System.out.println("++++++++++++++++++++++++++++++++++++");
				fileParse.parseAbcdMubanList(mubanList, Integer.parseInt(leixingid),tier,deptId);
				List<SafetyProductionFile> list = fileParse.getAbcdList();
				fileParse.close();
				list.forEach(item->{
					SafetyProductionFileVO vo = null;
					vo = safetyProductionFileService.getSafetyProductionFileNameByDept(deptId,null,null,null,item.getSafeid().toString());
					if(vo != null){
						vo.setId(vo.getId());
						String formatStr2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
						vo.setCaozuoshijian(formatStr2);
						vo.setName(item.getName());
						vo.setDeptId(item.getDeptId());
						vo.setSort(item.getSort());
						vo.setCaozuoren(caozuoren);
						vo.setCaozuorenid(caozuorenid);
						vo.setIs_muban(1);
						vo.setIsMuban(1);
//						vo.setTier(item.getTier());
//						vo.setParentId(item.getParentId());
//						vo.setPath(item.getPath());
//						vo.setMubanPath(item.getMubanPath());
						vo.setIsDeleted(0);
						vo.setDocumentNumber(item.getDocumentNumber());
						vo.setType(item.getType());
						vo.setIsRequired(item.getIsRequired());
						safetyProductionFileService.updateById(vo);
					}else{
						vo = new SafetyProductionFileVO();
						int maxIds = safetyProductionFileService.selectMaxId()+1;
						vo.setId(maxIds);
						vo.setName(item.getName());
						vo.setDeptId(item.getDeptId());
						vo.setSort(item.getSort());
						vo.setCaozuoren(caozuoren);
						vo.setCaozuorenid(caozuorenid);
						String formatStr2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
						//根据模板parentId获取模板名称，然后根据名称获取对应企业的项ID

						vo.setSafeid(item.getSafeid());
						String tiers = null;
						Integer parentId = null;

						//根据safeid 获取模板数据的ID
						SafetyProductionFileVO vos = safetyProductionFileService.getSafetyProductionFileNameByDept(1,null,item.getSafeid().toString(),null,null);
						if(vos != null){
							String titer = vos.getTier();
							String[] idsss = titer.split("-");
							//去除素组中重复的数组
							List<String> listid = new ArrayList<String>();
							for (int t=0; t<idsss.length; t++) {
								if(!listid.contains(idsss[t])) {
									listid.add(idsss[t]);
								}
							}
							//返回一个包含所有对象的指定类型的数组
							String[] idss= listid.toArray(new String[1]);
							for(int q=idss.length-1;q>=0;q--){
								vos = safetyProductionFileService.getSafetyProductionFileNameByDept(deptId,null,null,null,idss[q]);
								if(vos != null){
									tiers = vos.getTier();
									parentId = vos.getId();
									break;
								}
								if(q < 2){
									tiers = "0-"+leixingid;
									parentId = Integer.parseInt(idss[q]);
									break;
								}
							}

//							vo = safetyProductionFileService.getSafetyProductionFileNameByDept(deptId,null,null,null,item.getParentId().toString());
//							if(vo != null){
//								tiers = vo.getTier()+vo.getId();
//							}
						}
						tiers = tiers+"-"+maxIds;
						vo.setTier(tiers);
						vo.setParentId(parentId);
						vo.setPath(item.getPath());
						vo.setMubanPath(item.getMubanPath());
						vo.setIsDeleted(0);
						vo.setCreatetime(formatStr2);
						vo.setDocumentNumber(item.getDocumentNumber());
						vo.setType(item.getType());
						vo.setIsRequired(item.getIsRequired());
						vo.setIs_muban(1);
						vo.setIsMuban(1);

//						SafetyProductionFileVO parentVo = safetyProductionFileService.getSafetyProductionFileNameByDept(1,item.getName(),null,leixingid,null);
//						parentVo = safetyProductionFileService.getSafetyProductionFileNameByDept(1,null,parentVo.getParentId().toString(),null,null);
//						parentVo = safetyProductionFileService.getSafetyProductionFileNameByDept(deptId,item.getName(),null,null,null);
//						if(parentVo != null){
//							vo.setParentId(parentVo.getId());
//							String tiers = parentVo.getTier()+"-"+maxIds;
//							vo.setTier(tiers);
//							vo.setPath(item.getPath());
//							vo.setMubanPath(item.getMubanPath());
//							vo.setIsDeleted(0);
//							vo.setCreatetime(formatStr2);
//							vo.setDocumentNumber(item.getDocumentNumber());
//							vo.setType(item.getType());
//							vo.setIsRequired(item.getIsRequired());
//							vo.setIs_muban(1);
//							vo.setIsMuban(1);
//						}else{
//							vo.setParentId(Integer.parseInt(leixingid));
//							String tiers = tier+"-"+maxIds;
//							vo.setTier(tiers);
//							vo.setPath(item.getPath());
//							vo.setMubanPath(item.getMubanPath());
//							vo.setIsDeleted(0);
//							vo.setCreatetime(formatStr2);
//							vo.setDocumentNumber(item.getDocumentNumber());
//							vo.setType(item.getType());
//							vo.setIsRequired(item.getIsRequired());
//							vo.setIs_muban(1);
//							vo.setIsMuban(1);
//						}
						safetyProductionFileService.save(vo);
					}
				});
			}
		}

		return R.success("一键生成成功");
	}

	@GetMapping("/boxTreeNum")
	@ApiLog("安全生产文档-文件数计算")
	@ApiOperation(value = "安全生产文档-文件数计算", notes = "传入deptId,获取该单位ABCD文档的前二级目录", position = 1)
	@ApiImplicitParam(name = "deptId", value = "企业ID", required = true)
	public R boxTreeNum(@RequestParam Integer deptId) {
		R r = new R();
		SafetyProductionFileVO list= safetyProductionFileService.boxTreeNum(deptId);
		if(list != null){
			AnbiaoSafetyproductionfileNum safetyproductionfileNum = new AnbiaoSafetyproductionfileNum();
			safetyproductionfileNum.setFinshNum(list.getFinshNum());
			safetyproductionfileNum.setUploadedNum(list.getNum());
			safetyproductionfileNum.setCaozuoshijian(DateUtil.now());
			safetyproductionfileNum.setDeptId(deptId);
			double num1 = safetyproductionfileNum.getFinshNum();
			double num2 = safetyproductionfileNum.getUploadedNum();
			double ratio = num1*1.0 / num2;
			DecimalFormat df = new DecimalFormat("#.##");
			String formattedRatio = df.format(ratio)+"%";

			safetyproductionfileNum.setFinshRatio(formattedRatio);
			int i = safetyproductionfileNumService.getBaseMapper().insert(safetyproductionfileNum);
			if(i > 0){
				r.setCode(200);
				r.setMsg("添加成功");
				r.setSuccess(true);
			}else{
				r.setCode(500);
				r.setMsg("添加失败");
				r.setSuccess(false);
			}
		}
		return r;
	}




}
