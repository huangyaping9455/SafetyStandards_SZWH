package org.springblade.anbiao.anquanhuiyi.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipOutputStream;
import org.json.JSONObject;
import org.springblade.anbiao.anquanhuiyi.VO.AnbiaoAnquanhuiyiDetailVO;
import org.springblade.anbiao.anquanhuiyi.VO.AnbiaoAnquanhuiyiVO;
import org.springblade.anbiao.anquanhuiyi.VO.AnquanhuiyiledgerVO;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyi;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyiDetail;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyiSource;
import org.springblade.anbiao.anquanhuiyi.page.AnQuanHuiYiPage;
import org.springblade.anbiao.anquanhuiyi.service.IAnbiaoAnquanhuiyiDetailService;
import org.springblade.anbiao.anquanhuiyi.service.IAnbiaoAnquanhuiyiService;
import org.springblade.anbiao.anquanhuiyi.service.IAnbiaoAnquanhuiyiSourceService;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.guanlijigouherenyuan.service.IOrganizationsService;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDetail;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDetailService;
import org.springblade.common.configurationBean.FileServer;
import org.springblade.common.configurationBean.TrainServer;
import org.springblade.common.constant.FilePathConstant;
import org.springblade.common.tool.*;
import org.springblade.common.tool.face.util.FaceUtil;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.system.entity.Dept;
import org.springblade.system.feign.ISysClient;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.feign.IUserClient;
import org.springblade.system.user.page.UserPage;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * <p>
 * 安全会议记录表 前端控制器
 * </p>
 *
 * @author lmh
 * @since 2022-11-01
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/anquanhuiyi")
@Api(value = "安全会议", tags = "安全会议")
public class AnbiaoAnquanhuiyiController {

	private IAnbiaoAnquanhuiyiService anquanhuiyiService;

	private IFileUploadClient fileUploadClient;

	private IAnbiaoAnquanhuiyiDetailService anquanhuiyiDetailService;

	private IOrganizationsService organizationService;

	private FileServer fileServer;

	private IUserClient userClient;

	private IJiaShiYuanService iJiaShiYuanService;

	private IAnbiaoAnquanhuiyiSourceService anquanhuiyiSourceService;

	private ISysClient iSysClient;

	@Autowired
	private IAnbiaoRiskDetailService riskDetailService;

	private TrainServer trainServer;


	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("新增-安全会议记录信息")
	@ApiOperation(value = "新增-安全会议记录信息", notes = "传入AnbiaoAnquanhuiyi")
	public R insert(@RequestBody AnbiaoAnquanhuiyi anquanhuiyi, BladeUser user) throws ParseException {
		R r = new R();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		QueryWrapper<AnbiaoAnquanhuiyi> anquanhuiyiQueryWrapper = new QueryWrapper<>();
		anquanhuiyiQueryWrapper.lambda().eq(AnbiaoAnquanhuiyi::getDeptId, anquanhuiyi.getDeptId());
		anquanhuiyiQueryWrapper.lambda().eq(AnbiaoAnquanhuiyi::getIsDeleted, 0);
		anquanhuiyiQueryWrapper.lambda().eq(AnbiaoAnquanhuiyi::getHuiyimingcheng, anquanhuiyi.getHuiyimingcheng());
		anquanhuiyiQueryWrapper.lambda().eq(AnbiaoAnquanhuiyi::getHuiyikaishishijian, anquanhuiyi.getHuiyikaishishijian());
		anquanhuiyiQueryWrapper.lambda().eq(AnbiaoAnquanhuiyi::getHuiyijieshushijian, anquanhuiyi.getHuiyijieshushijian());
		anquanhuiyiQueryWrapper.lambda().eq(AnbiaoAnquanhuiyi::getHuiyixingshi, anquanhuiyi.getHuiyixingshi());
		AnbiaoAnquanhuiyi deail = anquanhuiyiService.getBaseMapper().selectOne(anquanhuiyiQueryWrapper);


		//验证会议开始时间
		if (anquanhuiyi.getHuiyikaishishijian().length() >= 10) {
			String huiyikaishishijian = anquanhuiyi.getHuiyikaishishijian().substring(0, 10);
			if (StringUtils.isNotBlank(huiyikaishishijian) && !huiyikaishishijian.equals("null")) {
				if (DateUtils.isDateString(huiyikaishishijian, null) == true) {
					anquanhuiyi.setHuiyikaishishijian(huiyikaishishijian);
				} else {
					r.setMsg(anquanhuiyi.getHuiyikaishishijian() + ",该会议开始时间，不是时间格式；");
					r.setCode(500);
					r.setSuccess(false);
					return r;
				}
			}
		} else {
			r.setMsg(anquanhuiyi.getHuiyikaishishijian() + ",该会议开始时间，不是时间格式；");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}


		//验证会议结束时间
		if (anquanhuiyi.getHuiyijieshushijian().length() >= 10) {
			if (StringUtils.isNotBlank(anquanhuiyi.getHuiyijieshushijian()) && !anquanhuiyi.getHuiyijieshushijian().equals("null")) {
				String huiyikaishishijian = anquanhuiyi.getHuiyijieshushijian().substring(0, 10);
				if (DateUtils.isDateString(huiyikaishishijian, null) == true) {
					anquanhuiyi.setHuiyijieshushijian(huiyikaishishijian);
				} else {
					r.setMsg(anquanhuiyi.getHuiyijieshushijian() + ",该会议结束时间，不是时间格式；");
					r.setCode(500);
					r.setSuccess(false);
					return r;
				}
			}
		} else {
			r.setMsg(anquanhuiyi.getHuiyijieshushijian() + ",该会议结束时间，不是时间格式；");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}


		//验证 会议开始时间 不能大于 会议结束时间
		if (StringUtils.isNotBlank(anquanhuiyi.getHuiyikaishishijian()) && !anquanhuiyi.getHuiyikaishishijian().equals("null") && StringUtils.isNotBlank(anquanhuiyi.getHuiyijieshushijian()) && !anquanhuiyi.getHuiyijieshushijian().equals("null")) {
			int a1 = anquanhuiyi.getHuiyikaishishijian().length();
			int b1 = anquanhuiyi.getHuiyijieshushijian().length();
			if (a1 == b1) {
				if (a1 <= 10) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					if (DateUtils.belongCalendar(format.parse(anquanhuiyi.getHuiyikaishishijian()), format.parse(anquanhuiyi.getHuiyijieshushijian())) == false) {
						r.setMsg("会议开始日期,不能大于会议结束日期;");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
				if (a1 > 10) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if (DateUtils.belongCalendar(format.parse(anquanhuiyi.getHuiyikaishishijian()), format.parse(anquanhuiyi.getHuiyijieshushijian())) == false) {
						r.setMsg("会议开始日期,不能大于会议结束日期;");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
			} else {
				r.setMsg("会议开始日期与会议结束日期,时间格式不一致;");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}

		if (anquanhuiyi.getHuiyixingshi().equals("线下")) {
			if (anquanhuiyi.getHuiyikaishishijian().equals(anquanhuiyi.getHuiyijieshushijian())) {
				anquanhuiyi.setHuiyikaishishijian(anquanhuiyi.getHuiyikaishishijian());
				anquanhuiyi.setHuiyijieshushijian(anquanhuiyi.getHuiyijieshushijian());
			} else {
				r.setMsg("会议开始日期与会议结束日期不是同一天;");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}

		if (deail == null) {
			anquanhuiyi.setDeptId(anquanhuiyi.getDeptId());
			anquanhuiyi.setCaozuoren(user.getUserName());
			anquanhuiyi.setCaozuorenid(user.getUserId());
			anquanhuiyi.setCreatetime(DateUtil.now());
			if (anquanhuiyi.getHuiyixingshi().equals("线上")) {
				anquanhuiyi.setHuiyixingshi("0");
			} else if (anquanhuiyi.getHuiyixingshi().equals("线下")) {
				anquanhuiyi.setHuiyixingshi("1");
			} else {
				anquanhuiyi.setHuiyixingshi(anquanhuiyi.getHuiyixingshi());
			}
			anquanhuiyi.setIsDeleted(0);

			boolean i = anquanhuiyiService.save(anquanhuiyi);
			if (i) {
				AnbiaoAnquanhuiyi deail2 = anquanhuiyiService.getBaseMapper().selectOne(anquanhuiyiQueryWrapper);
				if (deail2 != null) {
					List<AnbiaoAnquanhuiyiDetail> anquanhuiyiDetailList = anquanhuiyi.getAnquanhuiyiDetails();
					for (int j = 0; j <= anquanhuiyiDetailList.size() - 1; j++) {
						AnbiaoAnquanhuiyiDetail anbiaoAnquanhuiyiDetail = new AnbiaoAnquanhuiyiDetail();
						QueryWrapper<AnbiaoAnquanhuiyiDetail> anquanhuiyiDetailQueryWrapper = new QueryWrapper<>();
						anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAadAaIds, deail2.getId());
						anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAadApIds, anquanhuiyiDetailList.get(j).getAadApIds());
						AnbiaoAnquanhuiyiDetail anquanhuiyiDetail = anquanhuiyiDetailService.getBaseMapper().selectOne(anquanhuiyiDetailQueryWrapper);
						if (anquanhuiyiDetail == null) {
							anbiaoAnquanhuiyiDetail.setAadAaIds(deail2.getId());
							anbiaoAnquanhuiyiDetail.setAadApIds(anquanhuiyiDetailList.get(j).getAadApIds());
							anbiaoAnquanhuiyiDetail.setAadApName(anquanhuiyiDetailList.get(j).getAadApName());
							anbiaoAnquanhuiyiDetail.setAadApType(anquanhuiyiDetailList.get(j).getAadApType());
							anbiaoAnquanhuiyiDetail.setAddApAutograph(anquanhuiyiDetailList.get(j).getAddApAutograph());
							boolean is = anquanhuiyiDetailService.save(anbiaoAnquanhuiyiDetail);
							if (is) {
								r.setMsg("新增成功");
								r.setCode(200);
								r.setSuccess(false);
							} else {
								r.setMsg("新增失败");
								r.setCode(500);
								r.setSuccess(false);
							}
						}
					}
				} else {
					r.setMsg("新增失败");
					r.setCode(500);
					r.setSuccess(false);
					return r;
				}
			}
		} else {
			r.setMsg("该会议已存在");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
		return r;
	}

	/**
	 * 编辑
	 */
	@PostMapping("/update")
	@ApiLog("编辑-安全会议信息")
	@ApiOperation(value = "编辑-安全会议信息", notes = "传入AnbiaoAnquanhuiyi")
	public R update(@RequestBody AnbiaoAnquanhuiyi anquanhuiyi, BladeUser user) {
		R r = new R();
		boolean b;
		QueryWrapper<AnbiaoAnquanhuiyi> anquanhuiyiQueryWrapper = new QueryWrapper<>();
		anquanhuiyiQueryWrapper.lambda().eq(AnbiaoAnquanhuiyi::getId, anquanhuiyi.getId());
		AnbiaoAnquanhuiyi deail = anquanhuiyiService.getBaseMapper().selectOne(anquanhuiyiQueryWrapper);
		if (deail != null) {
			anquanhuiyi.setCaozuoren(user.getUserName());
			anquanhuiyi.setCaozuorenid(user.getUserId());
			anquanhuiyi.setCaozuoshijian(DateUtil.now());
			int i = anquanhuiyiService.getBaseMapper().updateById(anquanhuiyi);
			if (i > 0) {
//					QueryWrapper<AnbiaoAnquanhuiyiDetail> anquanhuiyiDetailQueryWrapper = new QueryWrapper<>();
//					anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAadAaIds,anquanhuiyi.getId());
//					anquanhuiyiDetailService.getBaseMapper().delete(anquanhuiyiDetailQueryWrapper);
				List<AnbiaoAnquanhuiyiDetail> anquanhuiyiDetailList = anquanhuiyi.getAnquanhuiyiDetails();
				for (int j = 0; j <= anquanhuiyiDetailList.size() - 1; j++) {
					QueryWrapper<AnbiaoAnquanhuiyiDetail> anbiaoAnquanhuiyiDetailQueryWrapper = new QueryWrapper<>();
					anbiaoAnquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAadAaIds, anquanhuiyi.getId());
					anbiaoAnquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAadApIds, anquanhuiyiDetailList.get(j).getAadApIds());
					AnbiaoAnquanhuiyiDetail anbiaoAnquanhuiyiDetail1 = anquanhuiyiDetailService.getBaseMapper().selectOne(anbiaoAnquanhuiyiDetailQueryWrapper);
					if (anbiaoAnquanhuiyiDetail1 == null) {
						AnbiaoAnquanhuiyiDetail anbiaoAnquanhuiyiDetail = new AnbiaoAnquanhuiyiDetail();
						anbiaoAnquanhuiyiDetail.setAadAaIds(anquanhuiyi.getId());
						anbiaoAnquanhuiyiDetail.setAadApIds(anquanhuiyiDetailList.get(j).getAadApIds());
						anbiaoAnquanhuiyiDetail.setAadApName(anquanhuiyiDetailList.get(j).getAadApName());
						anbiaoAnquanhuiyiDetail.setAadApType(anquanhuiyiDetailList.get(j).getAadApType());
						if (StringUtils.isNotBlank(anquanhuiyiDetailList.get(j).getAddTime()) && !anquanhuiyiDetailList.get(j).getAddTime().equals("null")) {
							anbiaoAnquanhuiyiDetail.setAddTime(anquanhuiyiDetailList.get(j).getAddTime());
						}

						anbiaoAnquanhuiyiDetail.setAddApBeingJoined(anquanhuiyiDetailList.get(j).getAddApBeingJoined());
						anbiaoAnquanhuiyiDetail.setAddApHeadPortrait(anquanhuiyiDetailList.get(j).getAddApHeadPortrait());
						anbiaoAnquanhuiyiDetail.setAddApAutograph(anquanhuiyiDetailList.get(j).getAddApAutograph());
						b = anquanhuiyiDetailService.save(anbiaoAnquanhuiyiDetail);
					} else {
						anbiaoAnquanhuiyiDetail1.setAadAaIds(anquanhuiyi.getId());
						anbiaoAnquanhuiyiDetail1.setAadApIds(anquanhuiyiDetailList.get(j).getAadApIds());
						anbiaoAnquanhuiyiDetail1.setAadApName(anquanhuiyiDetailList.get(j).getAadApName());
						anbiaoAnquanhuiyiDetail1.setAadApType(anquanhuiyiDetailList.get(j).getAadApType());
						anbiaoAnquanhuiyiDetail1.setAddApBeingJoined(anbiaoAnquanhuiyiDetail1.getAddApBeingJoined());
						anbiaoAnquanhuiyiDetail1.setAddApHeadPortrait(anquanhuiyiDetailList.get(j).getAddApHeadPortrait());
						anbiaoAnquanhuiyiDetail1.setAddApAutograph(anquanhuiyiDetailList.get(j).getAddApAutograph());
						if (StringUtils.isNotBlank(anquanhuiyiDetailList.get(j).getAddTime()) && !anquanhuiyiDetailList.get(j).getAddTime().equals("null")) {
							anbiaoAnquanhuiyiDetail1.setAddTime(anquanhuiyiDetailList.get(j).getAddTime());
						}
						int i1 = anquanhuiyiDetailService.getBaseMapper().updateById(anbiaoAnquanhuiyiDetail1);
						if (i1 >= 0) {
							b = true;
						} else {
							b = false;
						}
					}

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


	/**
	 * 删除
	 */
	@GetMapping("/del")
	@ApiLog("删除-安全会议")
	@ApiOperation(value = "删除-安全会议", notes = "传入Id", position = 3)
	@ApiImplicitParams({@ApiImplicitParam(name = "Id", value = "数据Id", required = true),
	})
	public R del(String Id, BladeUser user) {
		R r = new R();
		AnbiaoAnquanhuiyi anquanhuiyi = new AnbiaoAnquanhuiyi();
		if (user != null) {
			anquanhuiyi.setCaozuoren(user.getUserName());
			anquanhuiyi.setCaozuorenid(user.getUserId());
		}
		anquanhuiyi.setCaozuoshijian(DateUtil.now());
		anquanhuiyi.setIsDeleted(1);
		anquanhuiyi.setId(Id);
		boolean i = anquanhuiyiService.updateById(anquanhuiyi);
		if (i) {
			r.setMsg("删除成功");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		} else {
			r.setMsg("删除失败");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
	}

	/**
	 * 查询详情
	 */
	@GetMapping("/detail")
	@ApiLog("详情-安全会议")
	@ApiOperation(value = "详情-安全会议", notes = "传入Id")
	@ApiImplicitParams({@ApiImplicitParam(name = "Id", value = "数据Id", required = true),
		@ApiImplicitParam(name = "jsyId", value = "驾驶员Id")})
	public R detail(String Id, String jsyId) {
		R r = new R();
		AnbiaoAnquanhuiyi anquanhuiyiInfo = anquanhuiyiService.getById(Id);
		if (anquanhuiyiInfo != null) {
			QueryWrapper<Organizations> organizationsVOQueryWrapper = new QueryWrapper<Organizations>();
			organizationsVOQueryWrapper.lambda().eq(Organizations::getDeptId, anquanhuiyiInfo.getDeptId());
			organizationsVOQueryWrapper.lambda().eq(Organizations::getIsdelete, 0);
			Organizations organizationsVO = organizationService.getBaseMapper().selectOne(organizationsVOQueryWrapper);
			if (organizationsVO != null) {
				anquanhuiyiInfo.setDeptname(organizationsVO.getDeptName());
			}
			QueryWrapper<AnbiaoAnquanhuiyiDetail> anquanhuiyiDetailQueryWrapper = new QueryWrapper<>();
			anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAadAaIds, Id);
			//司机端只能看到自己的详情
			if (StringUtils.isNotEmpty(jsyId)) {
				anquanhuiyiDetailQueryWrapper.lambda().like(StringUtils.isNotEmpty(jsyId), AnbiaoAnquanhuiyiDetail::getAadApIds, jsyId);
			}
			List<AnbiaoAnquanhuiyiDetail> details = anquanhuiyiDetailService.getBaseMapper().selectList(anquanhuiyiDetailQueryWrapper);
			List<AnbiaoAnquanhuiyiDetail> details1 = new ArrayList<>();
			for (AnbiaoAnquanhuiyiDetail a:details) {
				QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<>();
				jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete,"0");
				jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId,a.getAadApIds());
				JiaShiYuan jiaShiYuan = iJiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
				if (jiaShiYuan!=null){
					details1.add(a);
				}
			}

			for (int i = 0; i <= details1.size() - 1; i++) {
				AnbiaoAnquanhuiyiDetail anbiaoAnquanhuiyiDetail = details1.get(i);
				//参会人头像附件
				if (StrUtil.isNotEmpty(anbiaoAnquanhuiyiDetail.getAddApHeadPortrait()) && anbiaoAnquanhuiyiDetail.getAddApHeadPortrait().contains("http") == false) {
					anbiaoAnquanhuiyiDetail.setAddApHeadPortrait(fileUploadClient.getUrl(anbiaoAnquanhuiyiDetail.getAddApHeadPortrait()));
				}
			}
			anquanhuiyiInfo.setAnquanhuiyiDetails(details1);
			AnbiaoAnquanhuiyiDetail anbiaoAnquanhuiyiDetail = new AnbiaoAnquanhuiyiDetail();
			anbiaoAnquanhuiyiDetail.setAadAaIds(Id);
//			anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAadAaIds, Id);
//			anquanhuiyiDetailQueryWrapper.groupBy("aad_ap_type");
			details1 = anquanhuiyiDetailService.selectPersonnelType(anbiaoAnquanhuiyiDetail);
			String leixing = "";
			for (int i = 0; i <= details1.size() - 1; i++) {
				leixing += details1.get(i).getAadApType() + ",";
			}
			;
			System.out.println(leixing);
			anquanhuiyiInfo.setType(leixing);
			//照片附件
			if (StrUtil.isNotEmpty(anquanhuiyiInfo.getFujian()) && anquanhuiyiInfo.getFujian().contains("http") == false) {
				anquanhuiyiInfo.setFujian(fileUploadClient.getUrl(anquanhuiyiInfo.getFujian()));
			}
			r.setData(anquanhuiyiInfo);
			r.setCode(200);
			r.setMsg("获取成功");
			return r;
		} else {
			r.setCode(500);
			r.setMsg("暂无数据");
			return r;
		}
	}


	/**
	 * 分页
	 */
	@PostMapping("/getAnquanhuiyiPage")
	@ApiLog("分页-安全会议")
	@ApiOperation(value = "分页-安全会议", notes = "传入AnQuanHuiYiPage")
	public R<AnQuanHuiYiPage<AnbiaoAnquanhuiyi>> getAnquanhuiyiPage(@RequestBody AnQuanHuiYiPage anQuanHuiYiPage) {
		R r = new R();
		AnQuanHuiYiPage<AnbiaoAnquanhuiyi> list = anquanhuiyiService.selectGetAll(anQuanHuiYiPage);
		return R.data(list);
	}


	/**
	 * 签到
	 */
	@PostMapping("/signIn")
	@ApiLog("签到-安全会议")
	@ApiOperation(value = "签到-安全会议", notes = "传入AnbiaoAnquanhuiyi,AnbiaoAnquanhuiyiDetail")
	public R SignIn(@RequestBody AnbiaoAnquanhuiyiDetail anquanhuiyiDetail, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoAnquanhuiyiDetail> anquanhuiyiDetailQueryWrapper = new QueryWrapper<>();
		anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAadAaIds, anquanhuiyiDetail.getAadAaIds());
		anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAadApIds, anquanhuiyiDetail.getAadApIds());
		anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAddApBeingJoined, "0");
		AnbiaoAnquanhuiyiDetail detail = anquanhuiyiDetailService.getBaseMapper().selectOne(anquanhuiyiDetailQueryWrapper);
		if (detail != null) {
			detail.setAddApAutograph(anquanhuiyiDetail.getAddApAutograph());
			detail.setAddApHeadPortrait(anquanhuiyiDetail.getAddApHeadPortrait());
			detail.setAadApType(anquanhuiyiDetail.getAadApType());
			detail.setAddApBeingJoined(anquanhuiyiDetail.getAddApBeingJoined());
			detail.setAddTime(DateUtil.now());
			detail.setAddApBeingJoined("1");

			QueryWrapper<AnbiaoAnquanhuiyi> anquanhuiyiQueryWrapper = new QueryWrapper<>();
			anquanhuiyiQueryWrapper.lambda().eq(AnbiaoAnquanhuiyi::getId, anquanhuiyiDetail.getAadAaIds());
			anquanhuiyiQueryWrapper.lambda().eq(AnbiaoAnquanhuiyi::getIsDeleted, 0);
			AnbiaoAnquanhuiyi anquanhuiyi = anquanhuiyiService.getBaseMapper().selectOne(anquanhuiyiQueryWrapper);


			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle, "未按时参加安全会议");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, detail.getAadApIds());
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, anquanhuiyi.getHuiyimingcheng());
			AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
			if (riskDetail != null) {
				riskDetail.setArdIsRectification("1");
				riskDetail.setArdRectificationDate(DateUtil.now().substring(0, 10));
				riskDetailService.getBaseMapper().updateById(riskDetail);
			}

			return R.status(anquanhuiyiDetailService.updateById(detail));
		} else {
			r.setCode(200);
			r.setMsg("已签到");
			r.setSuccess(false);
			return r;
		}
	}

	@GetMapping("ledgerlist")
	@ApiLog("分页 列表-安全会议台账")
	@ApiOperation(value = "安全会议台账分页", notes = "传入AnquanhuiyiledgerVO", position = 1)
	public R<IPage<AnquanhuiyiledgerVO>> ledgerList(AnquanhuiyiledgerVO anquanhuiyiledgerVO, Query query) {
		IPage<AnquanhuiyiledgerVO> pages = anquanhuiyiService.selectLedgerList(Condition.getPage(query), anquanhuiyiledgerVO);
		return R.data(pages);
	}

	@GetMapping("/goExport_Excel")
	@ApiLog("安全会议台账-导出")
	@ApiOperation(value = "安全会议台账-导出", notes = "传入deptId、date", position = 22)
	public R goExport_HiddenDanger_Excel(HttpServletRequest request, HttpServletResponse response, String Id, String deptId, String date, BladeUser user) throws Exception {
		int a = 1;
		int width = 220;
		int height = 220;
		String uuid = UUID.randomUUID().toString().replace("-", "");
		R rs = new R();
		List<String> urlList = new ArrayList<>();
		AnQuanHuiYiPage anQuanHuiYiPage = new AnQuanHuiYiPage();
		anQuanHuiYiPage.setDeptId(deptId);
		anQuanHuiYiPage.setHuiyikaishishijian(date);
		// TODO 渲染其他类型的数据请参考官方文档
		DecimalFormat df = new DecimalFormat("######0.00");
		Calendar now = Calendar.getInstance();
		//word模板地址
		String templatePath = fileServer.getPathPrefix() + "muban\\" + "Anquanhuiyi.xlsx";
		String[] nyr = DateUtil.today().split("-");
		String[] idsss = anQuanHuiYiPage.getDeptId().split(",");


		// 内容的策略
		WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
		// 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
		contentWriteCellStyle.setWrapped(true);
		// 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
		HorizontalCellStyleStrategy horizontalCellStyleStrategy =
			new HorizontalCellStyleStrategy(null, contentWriteCellStyle);


		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i = 0; i < idsss.length; i++) {
			if (!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] idss = listid.toArray(new String[1]);
		for (int j = 0; j < idss.length; j++) {
			anQuanHuiYiPage.setSize(0);
			anQuanHuiYiPage.setCurrent(0);
			anQuanHuiYiPage.setDeptId(idss[j]);
//			anQuanHuiYiPage.setId(Id);
			anquanhuiyiService.selectGetAll(anQuanHuiYiPage);
			List<AnbiaoAnquanhuiyi> anbiaoAnquanhuiyiList = anQuanHuiYiPage.getRecords();
			//Excel中的结果集ListData
			if (anbiaoAnquanhuiyiList.size() == 0) {

			} else if (anbiaoAnquanhuiyiList.size() > 3000) {
				rs.setMsg("数据超过30000条无法下载");
				rs.setCode(500);
				return rs;
			} else {
				for (int i = 0; i < anbiaoAnquanhuiyiList.size(); i++) {
					Map<String, Object> map = new HashMap<>();
					String templateFile = templatePath;
					// 渲染文本
					AnbiaoAnquanhuiyi t = anbiaoAnquanhuiyiList.get(i);
					map.put("deptName", t.getDeptname());
					map.put("huiyimingcheng", t.getHuiyimingcheng());
					if (t.getHuiyixingshi().equals("0")) {
						t.setHuiyixingshi("线上");
					} else {
						t.setHuiyixingshi("线下");
					}
					map.put("huiyixingshi", t.getHuiyixingshi());
					map.put("huiyikaishishijian", t.getDateShow());
					map.put("zhuchiren", t.getZhuchiren());
					map.put("canhuirenshu", t.getCanhuirenshu());
					map.put("huiyineirong", t.getHuiyineirong());
					map.put("shijicanhuirenshu",t.getShijicanhuirenshu());

					if (StringUtils.isNotBlank(t.getFujian()) && !t.getFujian().equals("null")) {

						// context是二维码里面的内容，如果是网址则会跳转到网址界面
						String context = t.getFujian();
//						// 获取类路径下的logo文件
//						ClassPathResource resource = new ClassPathResource("templates/logo.jpg");
//						//获logo.jpg的绝对路径
//						String logoPath = resource.getFile().getPath();
						String A = uuid + ".jpg";
						String destPath = fileServer.getPathPrefix() + FilePathConstant.ENCLOSURE_PATH + nyr[0] + "/" + nyr[1] + "/" + nyr[2] + "/" + A;
						// String destPath = "D:\\qrCode\\csdn.jpg";
						System.out.println("生成二维码");
						System.out.println(destPath);
						QrCodeUtils.encode(context, null, destPath, false);
						// 输出logo.jpg文件的绝对路径
						System.out.println(destPath);
						destPath="file:///"+destPath;

//						File file = new File(destPath);
						String url= StrUtil.replace(fileServer.getUrlPrefix()+ destPath,"\\","/");
						AnbiaoAnquanhuiyiVO anbiaoAnquanhuiyiVO = new AnbiaoAnquanhuiyiVO();
						//添加图片到工作表的指定位置
						try {
//							URL url = file.toURI().toURL();
							anbiaoAnquanhuiyiVO.setTwoDimensionalCodeUrl(new URL(destPath));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						map.put("huiyijiyao", anbiaoAnquanhuiyiVO.getTwoDimensionalCodeUrl());

					} else {
						map.put("huiyijiyao", "无");
					}

					List<AnbiaoAnquanhuiyiDetail> ListData = new ArrayList<>();
					QueryWrapper<AnbiaoAnquanhuiyiDetail> anquanhuiyiDetailQueryWrapper = new QueryWrapper<>();
					anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAadAaIds, t.getId());
					List<AnbiaoAnquanhuiyiDetail> details = anquanhuiyiDetailService.getBaseMapper().selectList(anquanhuiyiDetailQueryWrapper);
					List<AnbiaoAnquanhuiyiDetail> details1 = new ArrayList<>();
					for (AnbiaoAnquanhuiyiDetail aa:details) {
						QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<>();
						jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete,"0");
						jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId,aa.getAadApIds());
						JiaShiYuan jiaShiYuan = iJiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
						if (jiaShiYuan!=null){
							details1.add(aa);
						}
					}
					if (details1.size() > 0) {
						for (int q = 0; q <= details1.size() - 1; q++) {
							AnbiaoAnquanhuiyiDetailVO aa = new AnbiaoAnquanhuiyiDetailVO();
							AnbiaoAnquanhuiyiDetail anbiaoAnquanhuiyiDetail = details1.get(q);
							aa.setAddTime(anbiaoAnquanhuiyiDetail.getAddTime());
							aa.setAadApName(anbiaoAnquanhuiyiDetail.getAadApName());
							aa.setAddApHeadPortrait(anbiaoAnquanhuiyiDetail.getAddApHeadPortrait());
							aa.setAddApAutograph(anbiaoAnquanhuiyiDetail.getAddApAutograph());
							if (StrUtil.isNotEmpty(aa.getAddApHeadPortrait()) && aa.getAddApHeadPortrait().contains("http") == false) {
								aa.setAddApHeadPortrait(fileUploadClient.getUrl(aa.getAddApHeadPortrait()));
								//添加图片到工作表的指定位置
								try {
									aa.setAddApHeadPortraitImgUrl(new URL(aa.getAddApHeadPortrait()));
								} catch (MalformedURLException e) {
									e.printStackTrace();
								}
								aa.setAddApHeadPortraitImgUrl(aa.getAddApHeadPortraitImgUrl());
							} else if (StrUtil.isNotEmpty(aa.getAddApHeadPortrait())) {
								//添加图片到工作表的指定位置
								try {
									aa.setAddApHeadPortraitImgUrl(new URL(aa.getAddApHeadPortrait()));
								} catch (MalformedURLException e) {
									e.printStackTrace();
								}
								aa.setAddApHeadPortraitImgUrl(aa.getAddApHeadPortraitImgUrl());
							} else {
								aa.setAddApHeadPortraitImgUrl(null);
							}
							if (StrUtil.isNotEmpty(aa.getAddApAutograph()) && aa.getAddApAutograph().contains("http") == false) {
								aa.setAddApHeadPortrait(fileUploadClient.getUrl(aa.getAddApAutograph()));
								//添加图片到工作表的指定位置
								try {
									aa.setAddApAutographImgUrl(new URL(aa.getAddApAutograph()));
								} catch (MalformedURLException e) {
									e.printStackTrace();
								}
								aa.setAddApAutographImgUrl(aa.getAddApAutographImgUrl());
							} else if (StrUtil.isNotEmpty(aa.getAddApAutograph())) {
								//添加图片到工作表的指定位置
								try {
									aa.setAddApAutographImgUrl(new URL(aa.getAddApAutograph()));
								} catch (MalformedURLException e) {
									e.printStackTrace();
								}
								aa.setAddApAutographImgUrl(aa.getAddApAutographImgUrl());
							} else {
								aa.setAddApAutographImgUrl(null);
							}
							ListData.add(aa);
						}
					}
					// 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
					// {} 代表普通变量 {.} 代表是list的变量
					// 这里模板 删除了list以后的数据，也就是统计的这一行
					String templateFileName = templateFile;
					//alarmServer.getTemplateUrl()+
					String fileName = fileServer.getPathPrefix() + FilePathConstant.ENCLOSURE_PATH + nyr[0] + "/" + nyr[1] + "/" + nyr[2];
					File newFile = new File(fileName);
					//判断目标文件所在目录是否存在
					if (!newFile.exists()) {
						//如果目标文件所在的目录不存在，则创建父目录
						newFile.mkdirs();
					}
					fileName = fileName + "/" + t.getDeptname() + "-" + t.getHuiyimingcheng() + "-" + t.getDateShow() + "-安全会议台账.xlsx";
					ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).registerWriteHandler(horizontalCellStyleStrategy).build();
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
		String fileName = fileServer.getPathPrefix() + FilePathConstant.ENCLOSURE_PATH + nyr[0] + "\\" + nyr[1] + "\\" + "安全会议台账.zip";
		ExcelUtils.deleteFile(fileName);
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

	/**
	 * 查询详情
	 */
	@GetMapping("/standingBookDetail")
	@ApiLog("安全会议台账-详情")
	@ApiOperation(value = "安全会议台账-详情", notes = "传入数据Id")
	@ApiImplicitParams({@ApiImplicitParam(name = "Id", value = "数据Id", required = true)})
	public R standingBookDetail(String Id, String deptName) {
		R r = new R();
		AnbiaoAnquanhuiyi anquanhuiyiInfo = anquanhuiyiService.getById(Id);
		if (anquanhuiyiInfo != null) {
			QueryWrapper<AnbiaoAnquanhuiyiDetail> anquanhuiyiDetailQueryWrapper = new QueryWrapper<>();
			anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAadAaIds, Id);
			anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAddApBeingJoined, "0");
			List<AnbiaoAnquanhuiyiDetail> details = anquanhuiyiDetailService.getBaseMapper().selectList(anquanhuiyiDetailQueryWrapper);
			AnbiaoAnquanhuiyiVO anquanhuiyiVO = new AnbiaoAnquanhuiyiVO();
			anquanhuiyiVO.setDeptName(deptName);
			anquanhuiyiVO.setHuiyimingcheng(anquanhuiyiInfo.getHuiyimingcheng());
			String dates = anquanhuiyiInfo.getHuiyijieshushijian().substring(0, 10) + "至" + anquanhuiyiInfo.getHuiyijieshushijian().substring(0, 10);
			anquanhuiyiVO.setDateShow(dates);
			String message = "";
			if (details != null && details.size() > 0) {
				for (AnbiaoAnquanhuiyiDetail detail : details) {
					QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<>();
					jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getJiashiyuanxingming,detail.getAadApName());
					jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId,detail.getAadApIds());
					jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete,"0");
					JiaShiYuan jiaShiYuan = iJiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
					if (jiaShiYuan!=null){
						message += detail.getAadApName() + ",";
					}
				}
			}
			anquanhuiyiVO.setMessage(message);
			r.setData(anquanhuiyiVO);
			r.setCode(200);
			r.setMsg("获取成功");
			return r;
		} else {
			r.setCode(200);
			r.setMsg("暂无数据");
			return r;
		}
	}

	/**
	 * 新增（批量）
	 */
	@PostMapping("/batchInsert")
	@ApiLog("安全会议记录信息-发布")
	@ApiOperation(value = "安全会议记录信息-发布", notes = "传入deptIds，huiYiId")
	public R batchInsert(@RequestBody AnbiaoAnquanhuiyi anquanhuiyi, BladeUser user) throws ParseException {
		R r = new R();
		if (user == null) {
			r.setCode(401);
			r.setMsg("用户权限验证失败");
			r.setData(null);
			r.setSuccess(false);
			return r;
		}
		AnbiaoAnquanhuiyiSource anquanhuiyiSource = anquanhuiyiSourceService.getById(anquanhuiyi.getHuiYiId());
		if (anquanhuiyiSource == null) {
			r.setMsg("该会议不存在！");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
		QueryWrapper<AnbiaoAnquanhuiyi> anquanhuiyiQueryWrapper = new QueryWrapper<>();
		String[] idsss = anquanhuiyi.getDeptIds().split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i = 0; i < idsss.length; i++) {
			if (!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] idss = listid.toArray(new String[1]);
		for (int q = 0; q < idss.length; q++) {
			anquanhuiyi.setDeptId(Integer.parseInt(idss[q]));
			anquanhuiyiQueryWrapper.lambda().eq(AnbiaoAnquanhuiyi::getDeptId, idss[q]);
			anquanhuiyiQueryWrapper.lambda().eq(AnbiaoAnquanhuiyi::getIsDeleted, 0);
			anquanhuiyiQueryWrapper.lambda().eq(AnbiaoAnquanhuiyi::getHuiyimingcheng, anquanhuiyi.getHuiyimingcheng());
			anquanhuiyiQueryWrapper.lambda().eq(AnbiaoAnquanhuiyi::getHuiyikaishishijian, anquanhuiyi.getHuiyikaishishijian());
			anquanhuiyiQueryWrapper.lambda().eq(AnbiaoAnquanhuiyi::getHuiyijieshushijian, anquanhuiyi.getHuiyijieshushijian());
			anquanhuiyiQueryWrapper.lambda().eq(AnbiaoAnquanhuiyi::getHuiyixingshi, anquanhuiyi.getHuiyixingshi());
			AnbiaoAnquanhuiyi deail = anquanhuiyiService.getBaseMapper().selectOne(anquanhuiyiQueryWrapper);
			//验证会议开始时间
			if (anquanhuiyi.getHuiyikaishishijian().length() >= 10) {
				String huiyikaishishijian = anquanhuiyi.getHuiyikaishishijian().substring(0, 10);
				if (StringUtils.isNotBlank(huiyikaishishijian) && !huiyikaishishijian.equals("null")) {
					if (DateUtils.isDateString(huiyikaishishijian, null) == true) {
						anquanhuiyi.setHuiyikaishishijian(huiyikaishishijian);
					} else {
						r.setMsg(anquanhuiyi.getHuiyikaishishijian() + ",该会议开始时间，不是时间格式；");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
			} else {
				r.setMsg(anquanhuiyi.getHuiyikaishishijian() + ",该会议开始时间，不是时间格式；");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
			//验证会议结束时间
			if (anquanhuiyi.getHuiyijieshushijian().length() >= 10) {
				if (StringUtils.isNotBlank(anquanhuiyi.getHuiyijieshushijian()) && !anquanhuiyi.getHuiyijieshushijian().equals("null")) {
					String huiyikaishishijian = anquanhuiyi.getHuiyijieshushijian().substring(0, 10);
					if (DateUtils.isDateString(huiyikaishishijian, null) == true) {
						anquanhuiyi.setHuiyijieshushijian(huiyikaishishijian);
					} else {
						r.setMsg(anquanhuiyi.getHuiyijieshushijian() + ",该会议结束时间，不是时间格式；");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
			} else {
				r.setMsg(anquanhuiyi.getHuiyijieshushijian() + ",该会议结束时间，不是时间格式；");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}

			//验证 会议开始时间 不能大于 会议结束时间
			if (StringUtils.isNotBlank(anquanhuiyi.getHuiyikaishishijian()) && !anquanhuiyi.getHuiyikaishishijian().equals("null") && StringUtils.isNotBlank(anquanhuiyi.getHuiyijieshushijian()) && !anquanhuiyi.getHuiyijieshushijian().equals("null")) {
				int a1 = anquanhuiyi.getHuiyikaishishijian().length();
				int b1 = anquanhuiyi.getHuiyijieshushijian().length();
				if (a1 == b1) {
					if (a1 <= 10) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						if (DateUtils.belongCalendar(format.parse(anquanhuiyi.getHuiyikaishishijian()), format.parse(anquanhuiyi.getHuiyijieshushijian())) == false) {
							r.setMsg("会议开始日期,不能大于会议结束日期;");
							r.setCode(500);
							r.setSuccess(false);
							return r;
						}
					}
					if (a1 > 10) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if (DateUtils.belongCalendar(format.parse(anquanhuiyi.getHuiyikaishishijian()), format.parse(anquanhuiyi.getHuiyijieshushijian())) == false) {
							r.setMsg("会议开始日期,不能大于会议结束日期;");
							r.setCode(500);
							r.setSuccess(false);
							return r;
						}
					}
				} else {
					r.setMsg("会议开始日期与会议结束日期,时间格式不一致;");
					r.setCode(500);
					r.setSuccess(false);
					return r;
				}
			}

			if (anquanhuiyi.getHuiyixingshi().equals("线下")) {
				if (anquanhuiyi.getHuiyikaishishijian().equals(anquanhuiyi.getHuiyijieshushijian())) {
					anquanhuiyi.setHuiyikaishishijian(anquanhuiyi.getHuiyikaishishijian());
					anquanhuiyi.setHuiyijieshushijian(anquanhuiyi.getHuiyijieshushijian());
				} else {
					r.setMsg("会议开始日期与会议结束日期不是同一天;");
					r.setCode(500);
					r.setSuccess(false);
					return r;
				}
			}
			boolean is = false;
			int count = 0;
			UserPage<User> userPage = userClient.selectUserByDeptPage(anquanhuiyi.getDeptId(), 0);
			List<User> userList = userPage.getRecords();
			count += userList.size();
			List<JiaShiYuan> jiaShiYuanList = iJiaShiYuanService.jiaShiYuanList(anquanhuiyi.getDeptId().toString());
			count += jiaShiYuanList.size();
			if (deail == null) {
				String uuid1 = UUID.randomUUID().toString().replace("-", "");
				anquanhuiyi.setId(uuid1);
				anquanhuiyi.setCanhuirenshu(count);
				anquanhuiyi.setDeptId(anquanhuiyi.getDeptId());
				anquanhuiyi.setCaozuoren(user.getUserName());
				anquanhuiyi.setCaozuorenid(user.getUserId());
				anquanhuiyi.setCreatetime(DateUtil.now());
				if (anquanhuiyi.getHuiyixingshi().equals("线上")) {
					anquanhuiyi.setHuiyixingshi("0");
				} else if (anquanhuiyi.getHuiyixingshi().equals("线下")) {
					anquanhuiyi.setHuiyixingshi("1");
				} else {
					anquanhuiyi.setHuiyixingshi(anquanhuiyi.getHuiyixingshi());
				}
				anquanhuiyi.setIsDeleted(0);

				if (anquanhuiyi.getCanhuirenshu() < 1) {
					Dept dept = iSysClient.selectDeptById(anquanhuiyi.getDeptId());
					if (dept != null) {
						String deptName = dept.getDeptName();
						r.setMsg(deptName + "，该企业无人员资料，请校验后再发布会议！");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
				boolean i = anquanhuiyiService.save(anquanhuiyi);
				if (i) {
					AnbiaoAnquanhuiyi deail2 = anquanhuiyiService.getBaseMapper().selectById(anquanhuiyi.getId());
					if (deail2 != null) {
						//管理员
						if (userList.size() > 0 && userList != null) {
							for (int j = 0; j <= userList.size() - 1; j++) {
								AnbiaoAnquanhuiyiDetail anbiaoAnquanhuiyiDetail = new AnbiaoAnquanhuiyiDetail();
								QueryWrapper<AnbiaoAnquanhuiyiDetail> anquanhuiyiDetailQueryWrapper = new QueryWrapper<>();
								anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAadAaIds, deail2.getId());
								anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAadApIds, userList.get(j).getId());
								AnbiaoAnquanhuiyiDetail anquanhuiyiDetail = anquanhuiyiDetailService.getBaseMapper().selectOne(anquanhuiyiDetailQueryWrapper);
								if (anquanhuiyiDetail == null) {
									anbiaoAnquanhuiyiDetail.setAadAaIds(deail2.getId());
									anbiaoAnquanhuiyiDetail.setAadApIds(userList.get(j).getId().toString());
									anbiaoAnquanhuiyiDetail.setAadApName(userList.get(j).getName());
									anbiaoAnquanhuiyiDetail.setAadApType("0");
									is = anquanhuiyiDetailService.save(anbiaoAnquanhuiyiDetail);
								}
							}
						}

						//驾驶员
						if (jiaShiYuanList.size() > 0 && jiaShiYuanList != null) {
							for (int j = 0; j <= jiaShiYuanList.size() - 1; j++) {
								AnbiaoAnquanhuiyiDetail anbiaoAnquanhuiyiDetail = new AnbiaoAnquanhuiyiDetail();
								QueryWrapper<AnbiaoAnquanhuiyiDetail> anquanhuiyiDetailQueryWrapper = new QueryWrapper<>();
								anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAadAaIds, deail2.getId());
								anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAadApIds, jiaShiYuanList.get(j).getId());
								AnbiaoAnquanhuiyiDetail anquanhuiyiDetail = anquanhuiyiDetailService.getBaseMapper().selectOne(anquanhuiyiDetailQueryWrapper);
								if (anquanhuiyiDetail == null) {
									anbiaoAnquanhuiyiDetail.setAadAaIds(deail2.getId());
									anbiaoAnquanhuiyiDetail.setAadApIds(jiaShiYuanList.get(j).getId());
									anbiaoAnquanhuiyiDetail.setAadApName(jiaShiYuanList.get(j).getJiashiyuanxingming());
									anbiaoAnquanhuiyiDetail.setAadApType("1");
									is = anquanhuiyiDetailService.save(anbiaoAnquanhuiyiDetail);
								}
							}
						}
					}
				} else {
					r.setMsg("新增失败");
					r.setCode(500);
					r.setSuccess(false);
					return r;
				}
			}
			if (is) {
				r.setMsg("新增成功");
				r.setCode(200);
				r.setSuccess(false);
			} else {
				r.setMsg("新增失败");
				r.setCode(500);
				r.setSuccess(false);
			}
//			else {
//				r.setMsg("该会议已存在");
//				r.setCode(500);
//				r.setSuccess(false);
//				return r;
//			}
		}
		return r;
	}

	@GetMapping("/searchUser")
	@ApiOperation(value = "安全会议--人脸对比", notes = "安全会议--人脸对比", position = 7)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "faceUrl", value = "原人脸图片URL）", required = true),
		@ApiImplicitParam(name = "seachUrl", value = "需对比的人脸图片URL）", required = true)
	})
	public R searchUser(String faceUrl,String seachUrl) throws Exception{
		R rs = new R();
		//查询过滤条件
		if(StringUtils.isEmpty(faceUrl) || StringUtils.isEmpty(seachUrl)){
			rs.setCode(500);
			rs.setMsg("参数不能为空");
			rs.setSuccess(false);
			rs.setData(null);
			return rs;
		}
		//截取/之前字符串
		//获取第一个/索引
		int index = faceUrl.indexOf("/");
		//获取第二个/索引
		index = faceUrl.indexOf("/", index + 2);
		String str1 = faceUrl.substring(0, index);
		System.out.println("截取第二个/之前字符串：" + str1);
		//截取第二个_之后字符串
		String str2 = faceUrl.substring(str1.length() + 1, faceUrl.length());
		System.out.println("截取第二个/之后字符串：" + str2);
		faceUrl = fileServer.getPathPrefix()+str2;

		//截取_之前字符串
		//获取第一个/索引
		index = seachUrl.indexOf("/");
		//获取第二个/索引
		index = seachUrl.indexOf("/", index + 2);
		str1 = seachUrl.substring(0, index);
		System.out.println("截取第二个/之前字符串：" + str1);
		//截取第二个_之后字符串
		str2 = seachUrl.substring(str1.length() + 1, seachUrl.length());
		System.out.println("截取第二个/之后字符串：" + str2);
		seachUrl = fileServer.getPathPrefix()+str2;

		JSONObject res = FaceUtil.SeachUserUrl(faceUrl,seachUrl);
		if(Integer.parseInt(res.get("error_code").toString()) == 222001){
			rs.setCode(500);
			rs.setMsg("认证失败，请联系管理员");
			rs.setSuccess(false);
			rs.setData(0);
			return rs;
		}
		if(Integer.parseInt(res.get("error_code").toString()) == 222202){
			rs.setCode(500);
			rs.setMsg("请上传正确的头像与参会照片（需包含人脸）");
			rs.setSuccess(false);
			rs.setData(0);
			return rs;
		}
		res = res.getJSONObject("result");
		try {
			if(res != null){
				double score = Double.parseDouble(res.get("score").toString());
				if(score>Integer.parseInt(trainServer.getSearchScore())){
					rs.setCode(200);
					rs.setMsg("认证成功");
					rs.setSuccess(true);
					rs.setData(score);
					return rs;
				}else{
					rs.setCode(200);
					rs.setMsg("认证失败");
					rs.setSuccess(false);
					rs.setData(0);
					return rs;
				}
			}else{
				rs.setCode(500);
				rs.setMsg("认证失败，请联系管理员");
				rs.setSuccess(false);
				rs.setData(0);
				return rs;
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setCode(200);
			rs.setMsg("认证失败");
			rs.setSuccess(true);
			rs.setData(0);
			return rs;
		}
	}

}
