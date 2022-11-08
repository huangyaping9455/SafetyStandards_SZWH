package org.springblade.anbiao.anquanhuiyi.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyi;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyiDetail;
import org.springblade.anbiao.anquanhuiyi.service.IAnbiaoAnquanhuiyiDetailService;
import org.springblade.anbiao.anquanhuiyi.service.IAnbiaoAnquanhuiyiService;
import org.springblade.anbiao.anquanhuiyi.page.AnQuanHuiYiPage;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Anquanhuiyi;
import org.springblade.common.tool.DateUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


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


	/**
	 *新增
	 */
	@PostMapping("/insert")
	@ApiLog("新增-安全会议记录信息")
	@ApiOperation(value = "新增-安全会议记录信息", notes = "传入AnbiaoAnquanhuiyi")
	public R insert(@RequestBody AnbiaoAnquanhuiyi anquanhuiyi, BladeUser user) throws ParseException {
		R r = new R();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		QueryWrapper<AnbiaoAnquanhuiyi> anquanhuiyiQueryWrapper = new QueryWrapper<>();
		anquanhuiyiQueryWrapper.lambda().eq(AnbiaoAnquanhuiyi::getDeptId,anquanhuiyi.getDeptId());
		anquanhuiyiQueryWrapper.lambda().eq(AnbiaoAnquanhuiyi::getIsDeleted,0);
		AnbiaoAnquanhuiyi deail = anquanhuiyiService.getBaseMapper().selectOne(anquanhuiyiQueryWrapper);


		//验证会议开始时间
		if (StringUtils.isNotBlank(anquanhuiyi.getHuiyikaishishijian().toString()) && !anquanhuiyi.getHuiyikaishishijian().toString().equals("null")){
			if (DateUtils.isDateString(anquanhuiyi.getHuiyikaishishijian().toString(),null) == true){
				anquanhuiyi.setHuiyikaishishijian(anquanhuiyi.getHuiyikaishishijian());
			}else {
				r.setMsg(anquanhuiyi.getHuiyikaishishijian()+",该会议开始时间，不是时间格式；");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}

		//验证会议结束时间
		if (StringUtils.isNotBlank(anquanhuiyi.getHuiyijieshushijian().toString()) && !anquanhuiyi.getHuiyijieshushijian().toString().equals("null")){
			if (DateUtils.isDateString(anquanhuiyi.getHuiyijieshushijian().toString(),null) == true){
				anquanhuiyi.setHuiyijieshushijian(anquanhuiyi.getHuiyijieshushijian());
			}else {
				r.setMsg(anquanhuiyi.getHuiyijieshushijian()+",该会议结束时间，不是时间格式；");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}

		//验证 会议开始时间 不能大于 会议结束时间
		if(StringUtils.isNotBlank(anquanhuiyi.getHuiyikaishishijian().toString()) && !anquanhuiyi.getHuiyikaishishijian().toString().equals("null") && StringUtils.isNotBlank(anquanhuiyi.getHuiyijieshushijian().toString()) && !anquanhuiyi.getHuiyijieshushijian().toString().equals("null")) {
			int a1 = anquanhuiyi.getHuiyikaishishijian().toString().length();
			int b1 = anquanhuiyi.getHuiyijieshushijian().toString().length();
			if (a1 == b1) {
				if (a1 <= 10) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					if (DateUtils.belongCalendar(format.parse(anquanhuiyi.getHuiyikaishishijian().toString()), format.parse(anquanhuiyi.getHuiyijieshushijian().toString()))) {
						anquanhuiyi.setHuiyikaishishijian(anquanhuiyi.getHuiyikaishishijian());
						anquanhuiyi.setHuiyijieshushijian(anquanhuiyi.getHuiyijieshushijian());
					} else {
						r.setMsg("会议开始日期,不能大于会议结束日期;");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
				if (a1 > 10) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if (DateUtils.belongCalendar(format.parse(anquanhuiyi.getHuiyikaishishijian().toString()), format.parse(anquanhuiyi.getHuiyijieshushijian().toString()))) {
						anquanhuiyi.setHuiyikaishishijian(anquanhuiyi.getHuiyikaishishijian());
						anquanhuiyi.setHuiyijieshushijian(anquanhuiyi.getHuiyijieshushijian());
					} else {
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

		if (anquanhuiyi.getHuiyixingshi().equals("线下")){
			if (anquanhuiyi.getHuiyikaishishijian().toString().equals(anquanhuiyi.getHuiyijieshushijian().toString())){
				anquanhuiyi.setHuiyikaishishijian(anquanhuiyi.getHuiyikaishishijian());
				anquanhuiyi.setHuiyijieshushijian(anquanhuiyi.getHuiyijieshushijian());
			}else{
				r.setMsg("会议开始日期与会议结束日期不是同一天;");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}

		if (deail == null){
			anquanhuiyi.setDeptId(anquanhuiyi.getDeptId());
			anquanhuiyi.setCaozuoren(user.getUserName());
			anquanhuiyi.setCaozuorenid(user.getUserId());
			anquanhuiyi.setCreatetime(DateUtil.now());
			if (anquanhuiyi.getHuiyixingshi().equals("线上")){
				anquanhuiyi.setHuiyixingshi("0");
			}else {
				anquanhuiyi.setHuiyixingshi("1");
			}
			anquanhuiyi.setIsDeleted(0);

			boolean i = anquanhuiyiService.save(anquanhuiyi);
			if (i){
				AnbiaoAnquanhuiyi deail2 = anquanhuiyiService.getBaseMapper().selectOne(anquanhuiyiQueryWrapper);
				if (deail2!=null){
					List<AnbiaoAnquanhuiyiDetail> anquanhuiyiDetailList = anquanhuiyi.getAnquanhuiyiDetails();
					for (int j = 0; j <= anquanhuiyiDetailList.size()-1; j++) {
						AnbiaoAnquanhuiyiDetail anbiaoAnquanhuiyiDetail = new AnbiaoAnquanhuiyiDetail();
						QueryWrapper<AnbiaoAnquanhuiyiDetail> anquanhuiyiDetailQueryWrapper = new QueryWrapper<>();
						anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAadAaIds,deail2.getId());
						AnbiaoAnquanhuiyiDetail anquanhuiyiDetail = anquanhuiyiDetailService.getBaseMapper().selectOne(anquanhuiyiDetailQueryWrapper);
						if (anquanhuiyiDetail == null){
							anbiaoAnquanhuiyiDetail.setAadAaIds(deail2.getId());
							anbiaoAnquanhuiyiDetail.setAadApIds(anquanhuiyiDetailList.get(j).getAadApIds());
							anbiaoAnquanhuiyiDetail.setAadApName(anquanhuiyiDetailList.get(j).getAadApName());
							anbiaoAnquanhuiyiDetail.setAadApType(anquanhuiyiDetailList.get(j).getAadApType());
							anbiaoAnquanhuiyiDetail.setAddApAutograph(anquanhuiyiDetailList.get(j).getAddApAutograph());
							boolean is = anquanhuiyiDetailService.save(anbiaoAnquanhuiyiDetail);
							if (is){
								r.setMsg("新增成功");
								r.setCode(200);
								r.setSuccess(false);
								return r;
							}else {
								r.setMsg("新增失败");
								r.setCode(500);
								r.setSuccess(false);
								return r;
							}
						}
					}


				}else {
					r.setMsg("新增失败");
					r.setCode(500);
					r.setSuccess(false);
					return r;
				}
			}
		}else {
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
	@ApiOperation(value = "编辑-安全会议信息",notes = "传入AnbiaoAnquanhuiyi")
	public R update(@RequestBody AnbiaoAnquanhuiyi anquanhuiyi,BladeUser user){
		R r = new R();
		QueryWrapper<AnbiaoAnquanhuiyi> anquanhuiyiQueryWrapper = new QueryWrapper<>();
		anquanhuiyiQueryWrapper.lambda().eq(AnbiaoAnquanhuiyi::getId,anquanhuiyi.getId());
		AnbiaoAnquanhuiyi deail = anquanhuiyiService.getBaseMapper().selectOne(anquanhuiyiQueryWrapper);
		if (deail!=null){
			anquanhuiyi.setCaozuoren(user.getUserName());
			anquanhuiyi.setCaozuorenid(user.getUserId());
			anquanhuiyi.setCaozuoshijian(DateUtil.now());
			int i = anquanhuiyiService.getBaseMapper().updateById(anquanhuiyi);
			if (i>0) {
					QueryWrapper<AnbiaoAnquanhuiyiDetail> anquanhuiyiDetailQueryWrapper = new QueryWrapper<>();
					anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAadAaIds,anquanhuiyi.getId());
					anquanhuiyiDetailService.getBaseMapper().delete(anquanhuiyiDetailQueryWrapper);
					List<AnbiaoAnquanhuiyiDetail> anquanhuiyiDetailList = anquanhuiyi.getAnquanhuiyiDetails();
					for (int j = 0; j <= anquanhuiyiDetailList.size() - 1; j++) {
						AnbiaoAnquanhuiyiDetail anbiaoAnquanhuiyiDetail = new AnbiaoAnquanhuiyiDetail();
							anbiaoAnquanhuiyiDetail.setAadAaIds(anquanhuiyi.getId());
							anbiaoAnquanhuiyiDetail.setAadApIds(anquanhuiyiDetailList.get(j).getAadApIds());
							anbiaoAnquanhuiyiDetail.setAadApName(anquanhuiyiDetailList.get(j).getAadApName());
							anbiaoAnquanhuiyiDetail.setAadApType(anquanhuiyiDetailList.get(j).getAadApType());
							anbiaoAnquanhuiyiDetail.setAddApAutograph(anquanhuiyiDetailList.get(j).getAddApAutograph());
							boolean b = anquanhuiyiDetailService.save(anbiaoAnquanhuiyiDetail);
							if (b) {
								r.setMsg("更新成功");
								r.setCode(200);
								r.setSuccess(false);
								return r;
							} else {
								r.setMsg("更新失败");
								r.setCode(500);
								r.setSuccess(false);
								return r;
							}
					}
			}
		}else {
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
	@PostMapping("/del")
	@ApiLog("删除-安全会议")
	@ApiOperation(value = "删除-安全会议", notes = "传入Id", position = 3)
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true),
	})
	public R del( String Id, BladeUser user){
		R r = new R();
		AnbiaoAnquanhuiyi anquanhuiyi = new AnbiaoAnquanhuiyi();
		if (user != null){
			anquanhuiyi.setCaozuoren(user.getUserName());
			anquanhuiyi.setCaozuorenid(user.getUserId());
		}
		anquanhuiyi.setCaozuoshijian(DateUtil.now());
		anquanhuiyi.setIsDeleted(1);
		anquanhuiyi.setId(Id);
		boolean i = anquanhuiyiService.updateById(anquanhuiyi);
		if (i){
			r.setMsg("删除成功");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}else {
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
	@ApiOperation(value = "详情-安全会议",notes = "传入Id")
	@ApiImplicitParam(name = "Id",value = "数据ID",required = true)
	public R detail(String Id){
		R r = new R();
		AnbiaoAnquanhuiyi anquanhuiyiInfo = anquanhuiyiService.getById(Id);
		if (anquanhuiyiInfo != null){
			QueryWrapper<AnbiaoAnquanhuiyiDetail> anquanhuiyiDetailQueryWrapper = new QueryWrapper<>();
			anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAadAaIds,Id);
			List<AnbiaoAnquanhuiyiDetail> details = anquanhuiyiDetailService.getBaseMapper().selectList(anquanhuiyiDetailQueryWrapper);
			for (int i = 0; i <= details.size() - 1; i++) {
				AnbiaoAnquanhuiyiDetail anbiaoAnquanhuiyiDetail = details.get(i);
				//参会人头像附件
				if(StrUtil.isNotEmpty(anbiaoAnquanhuiyiDetail.getAddApHeadPortrait()) && anbiaoAnquanhuiyiDetail.getAddApHeadPortrait().contains("http") == false){
					anbiaoAnquanhuiyiDetail.setAddApHeadPortrait(fileUploadClient.getUrl(anbiaoAnquanhuiyiDetail.getAddApHeadPortrait()));
				}
			}
			anquanhuiyiInfo.setAnquanhuiyiDetails(details);
			//照片附件
			if(StrUtil.isNotEmpty(anquanhuiyiInfo.getFujian()) && anquanhuiyiInfo.getFujian().contains("http") == false){
				anquanhuiyiInfo.setFujian(fileUploadClient.getUrl(anquanhuiyiInfo.getFujian()));
			}
			r.setData(anquanhuiyiInfo);
			r.setCode(200);
			r.setMsg("获取成功");
			return r;
		}else {
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
	@ApiOperation(value = "分页-安全会议",notes = "传入AnQuanHuiYiPage")
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
	@ApiOperation(value = "签到-安全会议",notes = "传入AnbiaoAnquanhuiyi,AnbiaoAnquanhuiyiDetail")
	public R SignIn(@RequestBody AnbiaoAnquanhuiyiDetail anquanhuiyiDetail, BladeUser user){
		R r = new R();
		QueryWrapper<AnbiaoAnquanhuiyiDetail> anquanhuiyiDetailQueryWrapper = new QueryWrapper<>();
		anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAadAaIds,anquanhuiyiDetail.getAadAaIds());
		anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAadApIds,anquanhuiyiDetail.getAadApIds());
		anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAddApBeingJoined,"0");
		AnbiaoAnquanhuiyiDetail detail = anquanhuiyiDetailService.getBaseMapper().selectOne(anquanhuiyiDetailQueryWrapper);
		if (detail != null){
			detail.setAddApAutograph(anquanhuiyiDetail.getAddApAutograph());
			detail.setAddApHeadPortrait(anquanhuiyiDetail.getAddApHeadPortrait());
			detail.setAadApType(anquanhuiyiDetail.getAadApType());
			detail.setAddApBeingJoined(anquanhuiyiDetail.getAddApBeingJoined());
			detail.setAddTime(DateUtil.now());
			detail.setAddApBeingJoined("1");
			return R.status(anquanhuiyiDetailService.updateById(detail));
		}else{
			r.setCode(500);
			r.setMsg("暂无数据");
			return r;
		}
	}

}
