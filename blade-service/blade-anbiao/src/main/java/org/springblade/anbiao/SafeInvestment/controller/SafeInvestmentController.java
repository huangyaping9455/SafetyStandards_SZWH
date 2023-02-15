package org.springblade.anbiao.SafeInvestment.controller;

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
import org.apache.commons.lang.math.NumberUtils;
import org.apache.tools.zip.ZipOutputStream;
import org.springblade.anbiao.AccidentReports.VO.AccidentLedgerReportsVO;
import org.springblade.anbiao.AccidentReports.page.AccidentLedgerReportsPage;
import org.springblade.anbiao.SafeInvestment.DTO.SafeInvestmentDTO;
import org.springblade.anbiao.SafeInvestment.VO.SafeAllVO;
import org.springblade.anbiao.SafeInvestment.VO.SafetyInvestmentDetailsVO;
import org.springblade.anbiao.SafeInvestment.entity.AnbiaoSafetyInput;
import org.springblade.anbiao.SafeInvestment.entity.AnbiaoSafetyInputDetailed;
import org.springblade.anbiao.SafeInvestment.page.SafelInfoPage;
import org.springblade.anbiao.SafeInvestment.VO.SafelInfoledgerVO;
import org.springblade.anbiao.SafeInvestment.page.SafelInfoledgerPage;
import org.springblade.anbiao.SafeInvestment.service.IAnbiaoSafetyInputDetailedService;
import org.springblade.anbiao.SafeInvestment.service.impl.SafeInvestmentServiceImpl;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuanTJMX;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanPage;
import org.springblade.anbiao.risk.vo.LedgerDetailVO;
import org.springblade.common.configurationBean.AlarmServer;
import org.springblade.common.configurationBean.FileServer;
import org.springblade.common.constant.FilePathConstant;
import org.springblade.common.tool.ApacheZipUtils;
import org.springblade.core.boot.ctrl.BladeController;
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
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author long
 * @create 2022-10-28-11:31
 */
@RequestMapping("/anbiao/SafeInvestment/")
@RestController
@AllArgsConstructor
@Api(value = "安全投入", tags = "安全投入")
public class SafeInvestmentController extends BladeController {

	private SafeInvestmentServiceImpl safeInvestmentService;
	private IAnbiaoSafetyInputDetailedService safetyInputDetailedService;

	private AlarmServer alarmServer;
	private FileServer fileServer;
	private IFileUploadClient fileUploadClient;

	@PostMapping("list")
	@ApiLog("分页 列表-安全投入")
	@ApiOperation(value = "安全投入分页", notes = "传入SafeInvestmentDTO", position = 1)
	public R saList(@RequestBody SafelInfoPage safelInfoPage) {
		SafelInfoPage safelInfoPage1 = safeInvestmentService.selectPage(safelInfoPage);
		return R.data(safelInfoPage1);
	}

	@PostMapping("all")
	@ApiLog("列表-安全投入详情")
	@ApiOperation(value = "安全投入详情", notes = "SafeInvestmentDTO", position = 2)
	public R selectALL(@RequestBody SafeInvestmentDTO safeInvestmentDTO) {
		SafeAllVO safeAllVO = safeInvestmentService.selectAll(safeInvestmentDTO);
		return R.data(safeAllVO);
	}

	@PostMapping("insert")
	@ApiLog("新增-安全投入详情")
	@ApiOperation(value = "新增安全投入", notes = "SafeInvestmentDTO ", position = 3)
	public R insert(@RequestBody SafeInvestmentDTO safeInvestmentDTO) {
		R rs = new R();
		AnbiaoSafetyInput anbiaoSafetyInput = new AnbiaoSafetyInput();
		String s = UUID.randomUUID().toString().replace("-", "");
		anbiaoSafetyInput.setAsi_ids(s);
		anbiaoSafetyInput.setAsi_update_by_ids(safeInvestmentDTO.getDeptId());
		anbiaoSafetyInput.setAsi_year(safeInvestmentDTO.getAsiYear());
		anbiaoSafetyInput.setAsi_accrued_amount(NumberUtils.createBigDecimal(safeInvestmentDTO.getAsiAccruedAmount()));
		anbiaoSafetyInput.setAsi_withdrawal_amount(NumberUtils.createBigDecimal(safeInvestmentDTO.getAsiWithdrawalAmount()));
		String replace = safeInvestmentDTO.getAsiExtractionProportion().replace("%", "");
		anbiaoSafetyInput.setAsi_extraction_proportion(NumberUtils.createBigDecimal(replace));
		anbiaoSafetyInput.setAsi_amount_used(NumberUtils.createBigDecimal(safeInvestmentDTO.getAsiAmountUsed()));
		anbiaoSafetyInput.setAsi_remaining_amount(NumberUtils.createBigDecimal(safeInvestmentDTO.getAsiRemainingAmount()));
		anbiaoSafetyInput.setAsi_dept_ids(safeInvestmentDTO.getAsiDeptIds().toString());
		anbiaoSafetyInput.setAsi_last_years_turnover(NumberUtils.createBigDecimal(safeInvestmentDTO.getAsiLastYearsTurnover()));
		Boolean aBoolean = false;
		if (anbiaoSafetyInput != null) {
			aBoolean = safeInvestmentService.insertOne(anbiaoSafetyInput);
		}

		List<SafetyInvestmentDetailsVO> detailsVOList = safeInvestmentDTO.getSafetyInvestmentDetailsVOS();
		Boolean insert = true;
		if (detailsVOList != null) {
			for (SafetyInvestmentDetailsVO list : detailsVOList) {
				String uuid1 = UUID.randomUUID().toString().replace("-", "");
				AnbiaoSafetyInputDetailed anbiaoSafetyInputDetailed = new AnbiaoSafetyInputDetailed();
				anbiaoSafetyInputDetailed.setAsidIds(uuid1);
				anbiaoSafetyInputDetailed.setAsidAsiIds(s);
				anbiaoSafetyInputDetailed.setAsidEntryName(list.getAsidEntryName());
				anbiaoSafetyInputDetailed.setAsidHandledByName(list.getAsidHandledByName());
				anbiaoSafetyInputDetailed.setAsidInvestmentScope(list.getAsidInvestmentScope());
				anbiaoSafetyInputDetailed.setAsidInvestmentDare(list.getAsidInvestmentDare());
				anbiaoSafetyInputDetailed.setAsidAmountUsed(list.getAsidAmountUsed());
				anbiaoSafetyInputDetailed.setAsidHandledByIds(safeInvestmentDTO.getDeptId());
				insert = safeInvestmentService.insert(anbiaoSafetyInputDetailed);
			}
		}
		if (aBoolean && insert) {
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("新增成功");
		} else {
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("新增失败");
		}
		return rs;
	}

	@PostMapping("delete")
	@ApiLog("删除-安全投入详情")
	@ApiOperation(value = "删除安全投入", notes = "SafeInvestmentDTO ", position = 4)
	public R delete(@RequestBody SafeInvestmentDTO safeInvestmentDTO) {
		return R.status(safeInvestmentService.delete(safeInvestmentDTO));
	}

	@PostMapping("update")
	@ApiLog("修改-安全投入详情")
	@ApiOperation(value = "修改安全投入", notes = "AnbiaoSafetyInput ", position = 5)
	public R update(@RequestBody SafeInvestmentDTO safeInvestmentDTO) {
		R rs = new R();
		List<SafetyInvestmentDetailsVO> detailsVOList = safeInvestmentDTO.getSafetyInvestmentDetailsVOS();
		Boolean update = true;
		Boolean insert = true;
		if (detailsVOList != null) {
			List<AnbiaoSafetyInputDetailed> selectd = safeInvestmentService.selectd(safeInvestmentDTO);
			if (selectd != null && selectd.size() != 0) {
				for (SafetyInvestmentDetailsVO list : detailsVOList) {
					SafetyInvestmentDetailsVO safetyInvestmentDetailsVO = new SafetyInvestmentDetailsVO();
					safetyInvestmentDetailsVO.setAsidEntryName(list.getAsidEntryName());
					safetyInvestmentDetailsVO.setAsidHandledByName(list.getAsidHandledByName());
					safetyInvestmentDetailsVO.setAsidInvestmentScope(list.getAsidInvestmentScope());
					safetyInvestmentDetailsVO.setAsidInvestmentDare(list.getAsidInvestmentDare());
					safetyInvestmentDetailsVO.setAsidAmountUsed(list.getAsidAmountUsed());
					safetyInvestmentDetailsVO.setAsidHandledByIds(safeInvestmentDTO.getDeptId());
					safetyInvestmentDetailsVO.setAsidIds(safeInvestmentDTO.getAsiIds());
					update = safeInvestmentService.updateSafede(safetyInvestmentDetailsVO);
				}
			} else {
				for (SafetyInvestmentDetailsVO list : detailsVOList) {
					String uuid1 = UUID.randomUUID().toString().replace("-", "");
					AnbiaoSafetyInputDetailed anbiaoSafetyInputDetailed = new AnbiaoSafetyInputDetailed();
					anbiaoSafetyInputDetailed.setAsidIds(uuid1);
					anbiaoSafetyInputDetailed.setAsidAsiIds(safeInvestmentDTO.getAsiIds());
					anbiaoSafetyInputDetailed.setAsidEntryName(list.getAsidEntryName());
					anbiaoSafetyInputDetailed.setAsidHandledByName(list.getAsidHandledByName());
					anbiaoSafetyInputDetailed.setAsidInvestmentScope(list.getAsidInvestmentScope());
					anbiaoSafetyInputDetailed.setAsidInvestmentDare(list.getAsidInvestmentDare());
					anbiaoSafetyInputDetailed.setAsidAmountUsed(list.getAsidAmountUsed());
					anbiaoSafetyInputDetailed.setAsidHandledByIds(safeInvestmentDTO.getDeptId());
					insert = safeInvestmentService.insert(anbiaoSafetyInputDetailed);
				}
			}
			Boolean aBoolean = safeInvestmentService.updateSafe(safeInvestmentDTO);
			if (aBoolean && update && insert) {
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("修改成功");
			} else {
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("修改失败");
			}
		}
		return rs;
	}

	@GetMapping("ledgerlist")
	@ApiLog("分页 列表-安全投入台账")
	@ApiOperation(value = "安全投入台账分页", notes = "传入SafelInfoledgerVO", position = 1)
	public R<IPage<SafelInfoledgerVO>> ledgerList(SafelInfoledgerVO safelInfoledgerVO, Query query) {
		IPage<SafelInfoledgerVO> pages = safeInvestmentService.selectLedgerList(Condition.getPage(query), safelInfoledgerVO);
		return R.data(pages);
	}

	@PostMapping("/getledgerPage")
	@ApiLog("分页 列表-安全投入台账")
	@ApiOperation(value = "分页 列表-安全投入台账", notes = "传入SafelInfoledgerPage", position = 2)
	public R getLedgerPage(@RequestBody SafelInfoledgerPage safelInfoledgerPage, BladeUser us) {
		R rs = new R();
		SafelInfoledgerPage list = safeInvestmentService.selectLedgerList(safelInfoledgerPage);
		return R.data(list);
	}

	@GetMapping("/goExport_Excel")
	@ApiLog("安全投入信息-导出")
	@ApiOperation(value = "安全投入信息-导出", notes = "传入SafelInfoledgerPage", position = 22)
	public R goExport_HiddenDanger_Excel(HttpServletRequest request, HttpServletResponse response, String deptId , String date, BladeUser user) throws IOException {
		int a=1;
		R rs = new R();
		List<String> urlList = new ArrayList<>();
		SafelInfoledgerPage safelInfoledgerPage = new SafelInfoledgerPage();
		safelInfoledgerPage.setDeptId(deptId);
		safelInfoledgerPage.setAsiYear(date);
		// TODO 渲染其他类型的数据请参考官方文档
		DecimalFormat df = new DecimalFormat("######0.00");
		Calendar now = Calendar.getInstance();
		//word模板地址
		String templatePath =fileServer.getPathPrefix()+"muban\\"+"SafeInvestment.xlsx";
		String [] nyr= DateUtil.today().split("-");
		String[] idsss = safelInfoledgerPage.getDeptId().split(",");
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
			safelInfoledgerPage.setDeptName("");
			safelInfoledgerPage.setSize(0);
			safelInfoledgerPage.setCurrent(0);
			safelInfoledgerPage.setDeptId(idss[j]);
			safeInvestmentService.selectLedgerList(safelInfoledgerPage);
			List<SafelInfoledgerVO> safelInfoledgerVOS = safelInfoledgerPage.getRecords();
			//Excel中的结果集ListData
//			List<SafelInfoledgerVO> ListData = new ArrayList<>();
			if(safelInfoledgerVOS.size()==0){

			}else if(safelInfoledgerVOS.size()>3000){
				rs.setMsg("数据超过30000条无法下载");
				rs.setCode(500);
				return rs;
			}else{
				for( int i = 0 ; i < safelInfoledgerVOS.size() ; i++) {
					List<SafelInfoledgerVO> ListData = new ArrayList<>();
					Map<String, Object> map = new HashMap<>();
					int b=1;
					String templateFile = templatePath;
					// 渲染文本
					SafelInfoledgerVO t = safelInfoledgerVOS.get(i);
					map.put("deptName", t.getDeptName());
					map.put("asiYear", t.getAsiYear());
					map.put("asiLastYearsTurnover", t.getAsiLastYearsTurnover());
					map.put("asiExtractionProportion", t.getAsiExtractionProportion());
					map.put("asiWithdrawalAmount", t.getAsiWithdrawalAmount());
					map.put("asiAccruedAmount", t.getAsiAccruedAmount());
					map.put("asiAmountUsed", t.getAsiAmountUsed());
					map.put("asiRemainingAmount", t.getAsiRemainingAmount());
					QueryWrapper<AnbiaoSafetyInputDetailed> safetyInvestmentDetailsVOQueryWrapper = new QueryWrapper<>();
					safetyInvestmentDetailsVOQueryWrapper.lambda().eq(AnbiaoSafetyInputDetailed::getAsidAsiIds,t.getAsiIds());
					safetyInvestmentDetailsVOQueryWrapper.lambda().eq(AnbiaoSafetyInputDetailed::getAsidDelete,"0");
					List<AnbiaoSafetyInputDetailed> safetyInputDetaileds = safetyInputDetailedService.getBaseMapper().selectList(safetyInvestmentDetailsVOQueryWrapper);
					for (AnbiaoSafetyInputDetailed anbiaoSafetyInputDetailed: safetyInputDetaileds) {
						SafelInfoledgerVO safelInfoledgerVO = new SafelInfoledgerVO();
						safelInfoledgerVO.setAsidEntryName(anbiaoSafetyInputDetailed.getAsidEntryName());
						safelInfoledgerVO.setAsidHandledByName(anbiaoSafetyInputDetailed.getAsidHandledByName());
						safelInfoledgerVO.setAsidInvestmentScope(anbiaoSafetyInputDetailed.getAsidInvestmentScope());
						safelInfoledgerVO.setAsidInvestmentDare(anbiaoSafetyInputDetailed.getAsidInvestmentDare());
						safelInfoledgerVO.setAsidAmountUsed(anbiaoSafetyInputDetailed.getAsidAmountUsed());

						safelInfoledgerVO.setSerialNumber(b);
						b++;

						ListData.add(safelInfoledgerVO);
					}

					// 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
					// {} 代表普通变量 {.} 代表是list的变量
					// 这里模板 删除了list以后的数据，也就是统计的这一行
					String templateFileName = templateFile;
					//alarmServer.getTemplateUrl()+
//					String fileName = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"/"+nyr[1]+"/"+nyr[2]+"/"+t.getDeptName()+t.getAsiYear()+"-安全投入台账.xlsx";

					String fileName = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"/"+nyr[1]+"/"+nyr[2];
					File newFile = new File(fileName);
						//判断目标文件所在目录是否存在
					if(!newFile.exists()){
						//如果目标文件所在的目录不存在，则创建父目录
						newFile.mkdirs();
					}
					fileName = fileName+"/"+t.getDeptName()+t.getAsiYear()+"-"+a+"-安全投入台账.xlsx";

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
		String fileName = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"\\"+nyr[1]+"\\"+"安全投入台账.zip";
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
