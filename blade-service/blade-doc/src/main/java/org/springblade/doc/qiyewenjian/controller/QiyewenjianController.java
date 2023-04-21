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
package org.springblade.doc.qiyewenjian.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springblade.common.configurationBean.FileServer;
import org.springblade.common.constant.FilePathConstant;
import org.springblade.common.tool.CommonUtil;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.doc.biaozhunhuamuban.entity.FileParse;
import org.springblade.doc.qiyewenjian.entity.Qiyewenjian;
import org.springblade.doc.qiyewenjian.service.IQiyewenjianService;
import org.springblade.doc.qiyewenjian.vo.QiyewenjianVO;
import org.springblade.system.feign.IDictClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  控制器
 *
 * @author Blade
 * @since 2019-05-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/doc/qiyewenjian")
@Api(value = "企业文件", tags = "企业文件接口")
public class QiyewenjianController extends BladeController {

	private IQiyewenjianService qiyewenjianService;

	private IDictClient dictClient;

	private FileParse fileParse;

	private FileServer fileServer;

	/**
	 *添加目录
	 * @author: hyp
	 * @date: 2019/5/30 13:50
	 * @param id
	 * @param fileName
	 * @param user
	 * @return: org.springblade.core.tool.api.R<org.springblade.anbiao.QiyewenjianVO>
	 */
	@PostMapping("/addDir")
	@ApiOperation(value = "新增目录（文件夹）", notes = "传入本节点id，新增目录名称", position = 2)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "需要新增下级节点的id", required = true),
		@ApiImplicitParam(name = "fileName", value = "新增文件夹的名称", required = true)
	})
	public R<QiyewenjianVO> addDir(@RequestParam(name="id",required=true)Integer id, String fileName, BladeUser user) {
		Qiyewenjian parent = qiyewenjianService.getById(id);
		String msg = "新增成功";
		//如果父级目录不存在，则创建目录
		if(!FileUtil.isDirectory(fileParse.getPath(parent.getPath()))){
			//生成文件夹
			FileUtil.mkdir(fileParse.getPath(parent.getPath()));
		}
		FileUtil.mkdir(fileParse.getPath(parent.getPath())+File.separator+fileName);
		//如果当前路径是目录
//		if(FileUtil.isDirectory(fileParse.getPath(parent.getPath()))){
//			//生成文件夹
//			FileUtil.mkdir(fileParse.getPath(parent.getPath())+File.separator+fileName);
//		}else{
//			msg = "新增失败，该节点不是目录或不存在";
//			return R.fail(msg);
//		}
		//查询最大id
		Integer newId = qiyewenjianService.selectMaxId()+1;
		//查询下级最大sort
		Integer newSort = qiyewenjianService.selectMaxSorByParentId(id)+1;
		parent.setParentId(id);
		parent.setId(newId);
		parent.setPath(parent.getPath()+ File.separator+fileName);
		parent.setTier(parent.getTier()+"-"+newId);
		parent.setName(fileName);
		parent.setSort(newSort);
		parent.setDocumentNumber(null);
		qiyewenjianService.save(parent);
		QiyewenjianVO vo = new QiyewenjianVO();
		BeanUtil.copyProperties(parent,vo);
		vo.setFileNum(0);

		return R.data(vo,msg);
	}

	/**
	 *添加文件
	 * @author: hyp
	 * @date: 2019/5/30 13:50
	 * @param id
	 * @param file
	 * @param user
	 * @return: org.springblade.core.tool.api.R<org.springblade.anbiao.QiyewenjianVO>
	 */
	@PostMapping("/addFile")
	@ApiOperation(value = "新增企业文件", notes = "传入本节点id，文件", position = 2)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "需要新增下级节点的id", required = true),
		@ApiImplicitParam(name = "file", value = "新增的文件", required = true)
	})
	public R<QiyewenjianVO> addFile(Integer id, MultipartFile file, BladeUser user) {
		Qiyewenjian parent = qiyewenjianService.getById(id);
		String msg = "新增成功";

		//如果父级目录不存在，则创建目录
		if(!FileUtil.isDirectory(fileParse.getPath(parent.getPath()))){
			//生成文件夹
			FileUtil.mkdir(fileParse.getPath(parent.getPath()));
		}

		//如果当前路径是目录
//		if(FileUtil.isDirectory(fileParse.getPath(parent.getPath()))){
		//获取文件名称
		String fileName = file.getOriginalFilename();
		//获得文件物理路径
		String filePath = fileParse.getPath(parent.getPath())+File.separator+fileName;

		try {
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
		Integer newId = qiyewenjianService.selectMaxId()+1;
		//查询下级最大sort
		Integer newSort = qiyewenjianService.selectMaxSorByParentId(id)+1;
		parent.setParentId(id);
		parent.setId(newId);
		parent.setPath(fileParse.getInsertPath(filePath));
		parent.setTier(parent.getTier()+"-"+newId);
		parent.setName(fileName);
		parent.setSort(newSort);
		parent.setType("文件");
		parent.setDocumentNumber(null);
		boolean save = qiyewenjianService.save(parent);
		//保存成功，生成对应的pdf，图片
		if(save){
			fileParse.creatFormalFile(parent);
		}
//		}else{
//			msg = "新增失败，该节点不是目录或不存在";
//			return R.fail(msg);
//		}
		QiyewenjianVO vo = new QiyewenjianVO();
		BeanUtil.copyProperties(parent,vo);
		vo.setFileNum(0);
		return R.data(vo,msg);
	}



	/**
	 *文件替换
	 * @author: hyp
	 * @date: 2019/5/30 13:50
	 * @param id
	 * @param file
	 * @param user
	 * @return: org.springblade.core.tool.api.R<org.springblade.anbiao.QiyewenjianVO>
	 */
	@PostMapping("/replaceFile")
	@ApiOperation(value = "替换企业文件", notes = "传入本节点id，文件", position = 2)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "需要被替换节点的id", required = true),
		@ApiImplicitParam(name = "file", value = "替换的文件", required = true)
	})
	public R<QiyewenjianVO> replaceFile(Integer id,MultipartFile file,BladeUser user) {
		Qiyewenjian wenjian = qiyewenjianService.getById(id);
		String newPath = wenjian.getPath().replaceFirst(wenjian.getName(),file.getOriginalFilename());
		String newName = file.getOriginalFilename();
		String msg = "替换成功";
		/*if(user==null){
			msg = "替换失败，user信息为null";
			return R.fail(msg);
		}*/
		//如果当前路径存在
		if(FileUtil.exist(fileParse.getPath(wenjian.getPath()))){
			//删除源企业模板文件
			FileUtil.del(fileParse.getPath(wenjian.getPath()));
			//删除正式文件，pdf，图片
			FileUtil.del(StrUtil.replace(StrUtil.replace(fileParse.getPath(wenjian.getPath()), FilePathConstant.QYWJ_PATH,FilePathConstant.QYWJ_PDF_PATH),".docx",".pdf"));
			FileUtil.del(StrUtil.replace(StrUtil.replace(fileParse.getPath(wenjian.getPath()), FilePathConstant.QYWJ_PATH,FilePathConstant.QYWJ_IMG_PATH),".docx",""));

		}

		try {
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
		/*muban.setCaozuoren(user.getUserName());
		muban.setCaozuorenid(user.getUserId());
		muban.setCaozuoshijian(DateUtil.now());*/
		wenjian.setName(newName);
		boolean updateById = qiyewenjianService.updateById(wenjian);

		//替换成功，生成对应的正式文件，pdf，图片
		if(updateById){
			fileParse.creatFormalFile(wenjian);
		}
		QiyewenjianVO vo = new QiyewenjianVO();
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
	@ApiOperation(value = "删除节点", notes = "id", position = 3)
	public R del(@ApiParam(value = "主键", required = true) @RequestParam Integer id) {
		//查询下级节点
		List<Qiyewenjian> list = qiyewenjianService.getByParentId(id);
		String msg="删除成功";
		//如果有下级节点,不允许删除
		if(list!=null && list.size()>0){
			msg = "该节点下存在子节点,不允许直接删除";
		}else{
			Qiyewenjian wenjian = qiyewenjianService.getById(id);
			if(FileUtil.exist(fileParse.getPath(wenjian.getPath()))){
				FileUtil.del(fileParse.getPath(wenjian.getPath()));
				//如果是文件类型，就继续pdf，图片
				if("文件".equals(wenjian.getType())){
					FileUtil.del(StrUtil.replace(StrUtil.replace(fileParse.getPath(wenjian.getPath()), FilePathConstant.QYWJ_PATH,FilePathConstant.QYWJ_PDF_PATH),".docx",".pdf"));
					FileUtil.del(StrUtil.replace(StrUtil.replace(fileParse.getPath(wenjian.getPath()), FilePathConstant.QYWJ_PATH,FilePathConstant.QYWJ_IMG_PATH),".docx",""));
				}
			}

			qiyewenjianService.removeById(id);

		}
		return R.success(msg);
	}

	/**
	 *目录树
	 * @author: hyp
	 * @date: 2019/5/30 13:51
	 * @param deptId
	 * @param parentId
	 * @return: org.springblade.core.tool.api.R<java.util.List<org.springblade.anbiao.QiyewenjianVO>>
	 */
	@GetMapping("/tree")
	@ApiOperation(value = "获取企业文件目录树", notes = "传入deptId", position = 1)
	@ApiImplicitParams({ @ApiImplicitParam(name = "parentId", value = "上级id", required = false),
		@ApiImplicitParam(name = "deptId", value = "单位id", required = true)
	})
	public R<List<QiyewenjianVO>> tree(@RequestParam Integer deptId,@RequestParam(required = true,defaultValue="0")  Integer parentId) {
		List<QiyewenjianVO> list= qiyewenjianService.tree(deptId,parentId);
		return R.data(list);
	}

	/**
	 *图片预览
	 * @author: hyp
	 * @date: 2019/5/30 13:51
	 * @param id
	 * @return: org.springblade.core.tool.api.R<org.springblade.anbiao.QiyewenjianVO>
	 */
	@PostMapping("/imgPreview")
	@ApiOperation(value = "企业文件对应图片预览", notes = "传入id", position = 9)
	public R<QiyewenjianVO> imgPreview(@ApiParam(value = "id", required = true)@RequestParam Integer id){
		Qiyewenjian byId = qiyewenjianService.getById(id);
		if(byId==null){
			return R.fail("该文件不存在对应的预览图片资源");
		}
		List<String> list = new ArrayList<String>();
		String pic = StrUtil.replace(StrUtil.replace(fileParse.getPath(byId.getPath()),FilePathConstant.QYWJ_PATH,FilePathConstant.QYWJ_IMG_PATH),".docx","");
		System.out.println(pic);
		File file = new File(pic);
		File[] files=file.listFiles();
		for(int i = 0; i < files.length; i++){
			if (files[i].isFile()) {
				try{
					//String base64 = CommonUtil.encodeBase64File(files[i].getAbsolutePath());
					//list.add(base64);
					list.add(fileParse.pathToUrl(files[i].getPath()));
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
		QiyewenjianVO vo = new  QiyewenjianVO();
		BeanUtil.copyProperties(byId,vo);
		vo.setImgList(list);
		int i  = qiyewenjianService.updatePreviewRecordById(id);
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
	@ApiOperation(value = "下载企业文件", notes = "根据id下载企业文件", position = 1)
	@ApiImplicitParam(name = "id", value = "id", required = true)
	public R<String> downloadSubtemplateFile(HttpServletResponse response, @RequestParam Integer id){
		Qiyewenjian byId = qiyewenjianService.getById(id);
		if(byId==null){
			R.fail("不存在该文件");
		}
		return R.data(fileParse.insertPathToUrl(byId.getPath()));
	}


	@GetMapping("/updateDocumentNumber")
	@ApiOperation(value = "修改文档编号", notes = "根据id修改文档的编号", position = 1)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "documentNumber", value = "文档编号", required = true),
		@ApiImplicitParam(name = "id", value = "id", required = true)
	})
	public R updateDocumentNumber(@RequestParam Integer id,@RequestParam  String documentNumber) {
		boolean a= qiyewenjianService.updateDocumentNumberById(id,documentNumber);
//		Qiyewenjian byId = qiyewenjianService.getById(id);
//		QiyewenjianVO vo = new QiyewenjianVO();
//		BeanUtil.copyProperties(byId,vo);

		return R.success("修改文档编号成功");
	}



	@GetMapping("/swapFileSort")
	@ApiOperation(value = "文件排序上下移动", notes = "根据模板文件id实现两文件排序号对调,实现文件排序上下移动", position = 1)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "originId", value = "源文件id", required = true),
		@ApiImplicitParam(name = "targetId", value = "目标文件id", required = true)
	})
	public R swapFileSort(@RequestParam Integer originId,@RequestParam  Integer targetId) {
		Qiyewenjian origin = qiyewenjianService.getById(originId);
		Qiyewenjian target = qiyewenjianService.getById(targetId);
		boolean a = qiyewenjianService.updateSortById(originId,target.getSort());
		boolean b = qiyewenjianService.updateSortById(targetId,origin.getSort());
		return R.success("移动成功");
	}

	@PostMapping("/uploadfile")
	@ApiLog("上传文件-法律法规")
	@ApiOperation(value = "上传文件-法律法规", notes = "传入file,table", position = 7)
	public R uploadfile(MultipartFile file, String table) throws IOException {
		Map<String,String> map = new HashMap<String,String>();
		String [] nyr= DateUtil.today().split("-");
		String path= fileServer.getPathPrefix()+FilePathConstant.ENCLOSURE_PATH;
		String pathPdf = fileServer.getPathPrefix()+FilePathConstant.ENCLOSURE_PATH;
		String pathPic = fileServer.getPathPrefix()+FilePathConstant.ENCLOSURE_PATH;
		String name=file.getOriginalFilename();
		String[] a=name.split("\\.");
		String filename = nyr[0]+"\\"+nyr[1]+"\\"+table+"\\"+System.currentTimeMillis()+"."+a[a.length-1];
		String filenamepdf = nyr[0]+"\\"+nyr[1]+"\\"+table+"\\"+System.currentTimeMillis()+".pdf";
		String filenamepic = pathPic+nyr[0]+"\\"+nyr[1]+"\\"+table;
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File dirs = new File(pathPdf);
		if(!dirs.exists()){
			dirs.mkdirs();
		}
		File dirp = new File(filenamepic);
		if(!dirp.exists()){
			dirp.mkdirs();
		}
		File targetFile = new File(path+"\\"+filename);
		//执行上传
		file.transferTo(targetFile);
		//判断是否上传成功
		if(targetFile.exists()){
			//存放数据
			long ab=System.currentTimeMillis();
			System.out.println("正在转换中 请等待-------");
			CommonUtil.world2pdf(path+"\\"+filename
				, pathPdf+"\\"+filenamepdf);
			long b=System.currentTimeMillis();
//			String urlpath = CommonUtil.pdf2Image(pathPdf+filenamepdf,pathPic+filenamepic,300,0);
//			System.out.println(urlpath);
//			String[] urls = urlpath.split("\\|");
//			String urlpic="";
//			for(int i=0;i<urls.length;i++){
//				System.out.println(urls[i]);
//				urlpic = urlpic+urls[i]+"|";
//			}
//			System.out.println(urlpath);
			System.out.println("转换完成用时："+(b-ab)/1000+"秒");

			map.put("fileName",name);
			map.put("worldurl",FilePathConstant.ENCLOSURE_PATH+filename);
			map.put("pdfurl",FilePathConstant.ENCLOSURE_PATH+filenamepdf);
//			map.put("picurl",urlpic);

		}else{
			map.put("fileName",name);
			map.put("worldurl","");
			map.put("pdfurl","");
			map.put("error","文件上传失败");
		}
		return R.data(map);
	}


}
