package org.springblade.anbiao.labor.cotroller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.tools.zip.ZipOutputStream;
import org.springblade.anbiao.labor.DTO.laborDTO;

import org.springblade.anbiao.labor.VO.LaborledgerVO;
import org.springblade.anbiao.labor.entity.Labor;
import org.springblade.anbiao.labor.entity.LaborEntity;
import org.springblade.anbiao.labor.entity.LaborlingquEntity;
import org.springblade.anbiao.labor.page.LaborPage;
import org.springblade.anbiao.labor.page.laborledgerPage;
import org.springblade.anbiao.labor.service.laborLingquService;
import org.springblade.anbiao.labor.service.laborService;
import org.springblade.common.configurationBean.FileServer;
import org.springblade.common.constant.FilePathConstant;
import org.springblade.common.tool.ApacheZipUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;


/**
 * @Description : 劳保用品
 * @Author : long
 * @Date :2022/11/3 21:41
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/labor/")
@Api(value = "劳保用品", tags = "劳保用品信息")
public class laborController {

	private laborService service;

	private laborLingquService lingquService;

	private FileServer fileServer;

	private IFileUploadClient fileUploadClient;

	@PostMapping("list")
	@ApiLog("列表-劳保用品信息")
	@ApiOperation(value = "分页劳保用品信息", notes = "传入LaborPage ", position = 1)
	public R saList(@RequestBody LaborPage laborPage) {
		LaborPage laborPage1 = service.selectPage(laborPage);
		return R.data(laborPage1);
	}

//
//	@PostMapping("all")
//	public R selectALL(@Param("rid") String rid){
//		List<LaborVO> laborVOS = service.selectAll();
//		return R.data(laborVOS);
//	}

	@PostMapping("insert")
	@ApiLog("添加-劳保用品信息")
	@ApiOperation(value = "添加劳保用品信息", notes = "传入laborDTO", position = 2)
	public R insert(@RequestBody laborDTO laborDTO, BladeUser user) {
		if (laborDTO != null) {
			String replace = UUID.randomUUID().toString().replace("-", "");
			laborDTO.setAliIds(replace);
			List<Labor> labor = laborDTO.getLabor();
			String str = "";
			String aadApType = "";
			for (Labor list : labor) {
				aadApType += list.getAadApType() + ",";
				String replace1 = UUID.randomUUID().toString().replace("-", "");
				Labor labor1 = new Labor();
				labor1.setAadApName(list.getAadApName());
				labor1.setAadApIds(list.getAadApIds());
				labor1.setAadApType(list.getAadApType());
				labor1.setAlrIds(replace1);
				labor1.setAlrAliIds(replace);
				labor1.setAlrCreateByIds(user.getUserId().toString());
				labor1.setAlrCreateByName(user.getUserName());
				labor1.setAlrCreateTime(DateUtil.now());
				service.insertA(labor1);
			}
			str = aadApType;

			String[] split = str.split(",");
			List<String> list = new ArrayList<String>();
			for (String i : split) {
				if (!list.contains(i)) {//boolean contains(Object o):
					list.add(i);
				}
			}
			String s = list.toString();
			laborDTO.setAliApplicationScope(s);
		}
		laborDTO.setAliCreateByIds(user.getUserId().toString());
		laborDTO.setAliCreateByName(user.getUserName());
		laborDTO.setAliCreateTime(DateUtil.now());
		return R.status(service.insertOne(laborDTO));
	}


	@GetMapping("labor")
	@ApiLog("查询-劳保用品信息")
	@ApiOperation(value = "查询劳保图形", notes = "传入laborDTO", position = 2)
	public R selectlabor(String ali_name,String aliIssueDate,String aliDeptIds) {
		return R.data(service.selectGraphics(ali_name,aliIssueDate,aliDeptIds));
	}

	@PostMapping("all")
	@ApiLog("查询-劳保用品信息")
	@ApiOperation(value = "查询用品信息", notes = "传入laborDTO", position = 2)
	public R selectAll(@RequestBody LaborPage laborPage) {
		LaborEntity laborEntities = service.selectAll(laborPage);
		List<Labor> labor = service.selectC(laborPage);
		laborEntities.setLabor(labor);
		return R.data(laborEntities);
	}

	@PostMapping("delete")
	@ApiLog("删除-劳保用品信息")
	@ApiOperation(value = "删除劳保用品信息", notes = "传入id", position = 3)
	public R delete(@RequestBody laborDTO laborDTO) {
		return R.status(service.deleteAccident(laborDTO));
	}

	@PostMapping("update")
	@ApiLog("修改-劳保用品信息")
	@ApiOperation(value = "修改劳保用品信息", notes = "传入laborDTO", position = 4)
	public R update(@RequestBody LaborEntity laborEntity) {
		R r = new R();
		QueryWrapper<LaborEntity> laborQueryWrapper = new QueryWrapper<>();
		laborQueryWrapper.lambda().eq(LaborEntity::getAliIds, laborEntity.getAliIds());
		LaborEntity laborEntity1 = service.getBaseMapper().selectOne(laborQueryWrapper);
		if (laborEntity1 != null) {
			laborEntity1.setAliUpdateByIds(laborEntity.getDeptId());
			laborEntity1.setAliIssuePeopleNumber(laborEntity.getAliIssuePeopleNumber());
			laborEntity1.setAliName(laborEntity.getAliName());
			laborEntity1.setAliDeptIds(laborEntity.getAliDeptIds());
			laborEntity1.setAliIssueQuantity(laborEntity.getAliIssueQuantity());
			laborEntity1.setAliIssueDate(laborEntity.getAliIssueDate());
			laborEntity1.setAliStatus(laborEntity.getAliStatus());
			laborEntity1.setAliDelete(laborEntity.getAliDelete());
			int i = service.getBaseMapper().updateById(laborEntity1);
			if (i > 0) {
				QueryWrapper<LaborlingquEntity> laborlingquEntityQueryWrapper = new QueryWrapper<>();
				laborlingquEntityQueryWrapper.lambda().eq(LaborlingquEntity::getAlrAliIds, laborEntity.getAliIds());
				lingquService.getBaseMapper().delete(laborlingquEntityQueryWrapper);
				List<Labor> labor2 = laborEntity.getLabor();
				for (Labor list : labor2) {
					LaborlingquEntity labor1 = new LaborlingquEntity();
					labor1.setAlrPersonName(list.getAadApName());
					labor1.setAlrPersonIds(list.getAadApIds());
					labor1.setAlrIds(list.getAlrIds());
					labor1.setAlrAliIds(laborEntity.getAliIds());
					labor1.setAliApplicationScope(list.getAadApType());
					boolean b = lingquService.save(labor1);
					if (b) {
						r.setMsg("更新成功");
						r.setCode(200);
						r.setSuccess(false);
					} else {
						r.setMsg("更新失败");
						r.setCode(500);
						r.setSuccess(false);
					}

				}
			}
		} else {
			r.setMsg("该数据不存在");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
		return r;
	}

	@PostMapping("updatel")
	@ApiLog("修改-劳保用品信息")
	@ApiOperation(value = "劳保用品领取", notes = "传入LaborlingquEntity", position = 5)
	public R update(@RequestBody LaborlingquEntity laborlingqu) {
		QueryWrapper<LaborlingquEntity> laborQueryWrapper = new QueryWrapper<LaborlingquEntity>();
		laborQueryWrapper.lambda().eq(LaborlingquEntity::getAlrAliIds, laborlingqu.getAlrAliIds());
		laborQueryWrapper.lambda().eq(LaborlingquEntity::getAlrPersonIds, laborlingqu.getAlrPersonIds());
		LaborlingquEntity laborEntity1 = lingquService.getBaseMapper().selectOne(laborQueryWrapper);
		if(laborEntity1 != null){
			laborlingqu.setAlrIds(laborEntity1.getAlrIds());
			laborlingqu.setAlrUpdateByIds(laborlingqu.getAlrPersonIds());
			laborlingqu.setAlrUpdateTime(DateUtil.now());
			return R.status(service.updateL(laborlingqu));
		}else{
			R rs = new R();
			rs.setCode(200);
			rs.setMsg("暂无数据");
			rs.setSuccess(false);
			return rs;
		}
	}

	@GetMapping("ledgerlist")
	@ApiLog("分页 列表-劳保台账")
	@ApiOperation(value = "劳保台账分页", notes = "传入laborledgerVO", position = 1)
	public R<IPage<LaborledgerVO>> ledgerList(LaborledgerVO laborledgerVO, Query query) {
		IPage<LaborledgerVO> pages = service.selectLedgerList(Condition.getPage(query), laborledgerVO);
		return R.data(pages);
	}

	@PostMapping("/getledgerPage")
	@ApiLog("分页 列表-劳保台账")
	@ApiOperation(value = "分页 列表-劳保台账", notes = "传入laborledgerPage", position = 2)
	public R getLedgerPage(@RequestBody laborledgerPage laborledgerPage, BladeUser us) {
		R rs = new R();
		laborledgerPage list = service.selectLedgerList(laborledgerPage);
		return R.data(list);
	}

	@GetMapping("/goExport_Excel")
	@ApiLog("劳保信息-导出")
	@ApiOperation(value = "劳保信息-导出", notes = "传入laborledgerPage", position = 22)
	public R goExport_HiddenDanger_Excel(HttpServletRequest request, HttpServletResponse response, String deptId , String date, BladeUser user) throws IOException {
		int a=1;
		R rs = new R();
		List<String> urlList = new ArrayList<>();
		laborledgerPage laborledgerPage = new laborledgerPage();
		laborledgerPage.setDeptId(deptId);
		laborledgerPage.setDate(date);
		// TODO 渲染其他类型的数据请参考官方文档
		DecimalFormat df = new DecimalFormat("######0.00");
		Calendar now = Calendar.getInstance();
		//word模板地址
		String templatePath =fileServer.getPathPrefix()+"muban\\"+"Labor.xlsx";
		String [] nyr= DateUtil.today().split("-");
		String[] idsss = laborledgerPage.getDeptId().split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] idss= listid.toArray(new String[1]);
		for(int j = 0;j< idss.length;j++){
			laborledgerPage.setDeptName("");
			laborledgerPage.setSize(0);
			laborledgerPage.setCurrent(0);
			laborledgerPage.setDeptId(idss[j]);
			service.selectLedgerList(laborledgerPage);
			List<LaborledgerVO> LaborledgerVOS = laborledgerPage.getRecords();
			//Excel中的结果集ListData
//			List<LaborledgerVO> ListData = new ArrayList<>();
			if(LaborledgerVOS.size()==0){

			}else if(LaborledgerVOS.size()>3000){
				rs.setMsg("数据超过30000条无法下载");
				rs.setCode(500);
				return rs;
			}else{
				for(int i = 0; i < LaborledgerVOS.size() ; i++) {
					List<LaborledgerVO> ListData = new ArrayList<>();
					Map<String, Object> map = new HashMap<>();
					String templateFile = templatePath;
					// 渲染文本
					LaborledgerVO t = LaborledgerVOS.get(i);
					map.put("deptName", t.getDeptName());
					map.put("aliName", t.getAliName());
					map.put("aliIssueDate", t.getAliIssueDate());
					map.put("aliIssuePeopleNumber", t.getAliIssuePeopleNumber());
					map.put("aliIssueQuantity", t.getAliIssueQuantity());
					QueryWrapper<LaborlingquEntity> laborledgerVOQueryWrapper = new QueryWrapper<>();
					laborledgerVOQueryWrapper.lambda().eq(LaborlingquEntity::getAlrAliIds,t.getAliIds());
					laborledgerVOQueryWrapper.lambda().eq(LaborlingquEntity::getAlrDelete,"0");
					List<LaborlingquEntity> list=lingquService.getBaseMapper().selectList(laborledgerVOQueryWrapper);
					for (LaborlingquEntity laborlingquEntity:list) {
						LaborledgerVO laborledgerVO = new LaborledgerVO();
						laborledgerVO.setAlrPersonName(laborlingquEntity.getAlrPersonName());
						laborledgerVO.setAlrReceiptsNumber(laborlingquEntity.getAlrReceiptsNumber());
						laborledgerVO.setAlrReceiptDate(laborlingquEntity.getAlrReceiptDate());

						if (StrUtil.isNotEmpty(laborlingquEntity.getAlrPersonAutograph()) && laborlingquEntity.getAlrPersonAutograph().contains("http") == false) {
							laborledgerVO.setAlrPersonAutograph(fileUploadClient.getUrl(laborlingquEntity.getAlrPersonAutograph()));
							//添加图片到工作表的指定位置
							try {
								laborledgerVO.setImgUrl(new URL(laborledgerVO.getAlrPersonAutograph()));
							} catch (MalformedURLException e) {
								e.printStackTrace();
							}
							laborledgerVO.setImgUrl(laborlingquEntity.getImgUrl());
						}else if(StrUtil.isNotEmpty(laborlingquEntity.getAlrPersonAutograph())){
							//添加图片到工作表的指定位置
							try {
								laborledgerVO.setImgUrl(new URL(laborlingquEntity.getAlrPersonAutograph()));
							} catch (MalformedURLException e) {
								e.printStackTrace();
							}
							laborledgerVO.setImgUrl(laborlingquEntity.getImgUrl());
						}else{
							laborledgerVO.setImgUrl(null);
						}
						ListData.add(laborledgerVO);
					}
					// 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
					// {} 代表普通变量 {.} 代表是list的变量
					// 这里模板 删除了list以后的数据，也就是统计的这一行
					String templateFileName = templateFile;
					//alarmServer.getTemplateUrl()+

					String fileName = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"/"+nyr[1]+"/"+nyr[2];
					File newFile = new File(fileName);
					//判断目标文件所在目录是否存在
					if(!newFile.exists()){
						//如果目标文件所在的目录不存在，则创建父目录
						newFile.mkdirs();
					}
					fileName = fileName+"/"+t.getDeptName()+"-"+a+"-劳保台账.xlsx";

//					String fileName = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"/"+nyr[1]+"/"+nyr[2]+"/"+t.getDeptName()+"-劳保台账.xlsx";
					ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
					WriteSheet writeSheet = EasyExcel.writerSheet().build();
					// 写入list之前的数据
					excelWriter.fill(map, writeSheet);
					// 直接写入数据
					excelWriter.fill(ListData, writeSheet);
					excelWriter.finish();
					urlList.add(fileName);
					a++;
				}
			}
		}

		String fileName = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"\\"+nyr[1]+"\\"+"劳保台账.zip";
		ZipOutputStream bizOut = new ZipOutputStream(new FileOutputStream(fileName));
		ApacheZipUtils.doCompress1(urlList, bizOut);
		//不要忘记调用
		bizOut.close();

		rs.setMsg("下载成功");
		rs.setCode(200);
		rs.setData(fileName);
		rs.setSuccess(true);
		return rs;
	}
}
