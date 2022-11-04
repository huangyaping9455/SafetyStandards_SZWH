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
import org.springblade.anbiao.anquanhuiyi.service.IAnbiaoAnquanhuiyiService;
import org.springblade.anbiao.anquanhuiyi.page.AnQuanHuiYiPage;
import org.springblade.common.tool.DateUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;


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

		if (anquanhuiyi.getHuiyileixing().equals("线下")){
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
			if (anquanhuiyi.getHuiyileixing().equals("线上")){
				anquanhuiyi.setHuiyileixing("0");
			}else {
				anquanhuiyi.setHuiyileixing("1");
			}
			anquanhuiyi.setIsDeleted(0);
			return R.status(anquanhuiyiService.save(anquanhuiyi));
		}else {
			if (anquanhuiyi.getHuiyileixing().equals("线上")){
				deail.setHuiyileixing("0");
			}else {
				deail.setHuiyileixing("1");
			}
			anquanhuiyi.setId(deail.getId());
			anquanhuiyi.setCaozuoren(user.getUserName());
			anquanhuiyi.setCaozuorenid(user.getUserId());
			anquanhuiyi.setCaozuoshijian(DateUtil.now());
			return R.status(anquanhuiyiService.updateById(anquanhuiyi));
		}
	}

	/**
	 * 删除
	 */
	@PostMapping("/del")
	@ApiLog("删除-安全会议")
	@ApiOperation(value = "删除-安全会议", notes = "传入Id", position = 3)
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true),
	})
	public R del( String id, BladeUser user){
		R r = new R();
		AnbiaoAnquanhuiyi anquanhuiyi = new AnbiaoAnquanhuiyi();
		if (user != null){
			anquanhuiyi.setCaozuoren(user.getUserName());
			anquanhuiyi.setCaozuorenid(user.getUserId());
		}
		anquanhuiyi.setCaozuoshijian(DateUtil.now());
		anquanhuiyi.setIsDeleted(1);
		anquanhuiyi.setId(id);
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
	@ApiImplicitParam(name = "Id",value = "ID",required = true)
	public R detail(String Id){
		R r = new R();
		AnbiaoAnquanhuiyi anquanhuiyiInfo = anquanhuiyiService.getById(Id);
		if (anquanhuiyiInfo != null){
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
	@PostMapping("/list")
	@ApiLog("分页-安全会议")
	@ApiOperation(value = "分页-安全会议",notes = "传入AnQuanHuiYiPage")
	public R<AnQuanHuiYiPage> getAnquanhuiyiPage(@RequestBody AnQuanHuiYiPage anQuanHuiYiPage) {

		R r = new R();
		AnQuanHuiYiPage<AnbiaoAnquanhuiyi> list = anquanhuiyiService.selectPage(anQuanHuiYiPage);
		return R.data(list);
	}
}
