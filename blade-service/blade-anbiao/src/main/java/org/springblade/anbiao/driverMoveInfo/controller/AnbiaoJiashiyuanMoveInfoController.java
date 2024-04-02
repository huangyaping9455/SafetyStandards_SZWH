package org.springblade.anbiao.driverMoveInfo.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.driverMoveInfo.entity.AnbiaoJiashiyuanMoveInfo;
import org.springblade.anbiao.driverMoveInfo.service.IAnbiaoJiashiyuanMoveInfoService;
import org.springblade.anbiao.jiashiyuan.entity.*;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanPage;
import org.springblade.anbiao.jiashiyuan.service.*;
import org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanListVO;
import org.springblade.common.tool.IdCardUtil;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DigestUtil;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-03-25
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/jiashiyuanMoveInfo")
@Api(value = "驾驶员资料状态记录管理", tags = "驾驶员资料状态记录管理")
public class AnbiaoJiashiyuanMoveInfoController {

	private IAnbiaoJiashiyuanMoveInfoService jiashiyuanMoveInfoService;
	private IJiaShiYuanService jiaShiYuanService;
	private IAnbiaoJiashiyuanRuzhiService ruzhiService;
	private IAnbiaoJiashiyuanJiashizhengService jiashizhengService;
	private IAnbiaoJiashiyuanCongyezigezhengService congyezigezhengService;
	private IAnbiaoJiashiyuanTijianService tijianService;
	private IAnbiaoJiashiyuanGangqianpeixunService gangqianpeixunService;
	private IAnbiaoJiashiyuanWuzezhengmingService wuzezhengmingService;
	private IAnbiaoJiashiyuanAnquanzerenshuService anquanzerenshuService;
	private IAnbiaoJiashiyuanWeihaigaozhishuService weihaigaozhishuService;
	private IAnbiaoJiashiyuanLaodonghetongService laodonghetongService;
	private IAnbiaoJiashiyuanQitaService qitaService;
	private IAnbiaoCheliangJiashiyuanDailyService cheliangJiashiyuanDailyService;
	private IFileUploadClient fileUploadClient;


	/*@PostMapping("/insert")
	@ApiLog("新增-驾驶员异动信息")
	@ApiOperation(value = "新增-驾驶员异动信息", notes = "传入jiashiyuanMoveInfo", position = 1)
	public R insert(@RequestBody AnbiaoJiashiyuanMoveInfo jiashiyuanMoveInfo, BladeUser user) {
		R r = new R();
		if(user == null) {
			r.setMsg("未授权");
			r.setCode(401);
			return r;
		}
		String date = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		jiashiyuanMoveInfo.setUpdateTime(date);
		jiashiyuanMoveInfo.setUpdateUserId(user.getUserId().toString());
		jiashiyuanMoveInfo.setUpdateUser(user.getUserName());
		boolean i = jiashiyuanMoveInfoService.save(jiashiyuanMoveInfo);
		if(i == true){
			r.setCode(200);
			r.setMsg("异动成功");
		}else{
			r.setCode(500);
			r.setMsg("异动失败");
		}
		return r;
	}*/

	@PostMapping("/updateDriver")
	@ApiLog("驾驶员更新状态信息的变更记录-新增")
	@ApiOperation(value = "驾驶员更新状态信息的变更记录-新增", notes = "传入jiashiyuanMoveInfo", position = 2)
	public R updateDriver(@RequestBody AnbiaoJiashiyuanMoveInfo jiashiyuanMoveInfo, BladeUser user) {
		R r = new R();
		if(user == null) {
			r.setMsg("未授权");
			r.setCode(401);
			return r;
		}
		boolean ii = false;
		jiashiyuanMoveInfo.setUpdateUserId(jiashiyuanMoveInfo.getUpdateUserId());
		jiashiyuanMoveInfo.setUpdateUser(jiashiyuanMoveInfo.getUpdateUser());
		jiashiyuanMoveInfo.setUpdateTime(jiashiyuanMoveInfo.getUpdateTime());
		QueryWrapper<AnbiaoJiashiyuanMoveInfo> dangerQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanMoveInfo>();
		dangerQueryWrapper.lambda().eq(AnbiaoJiashiyuanMoveInfo::getJsyId, jiashiyuanMoveInfo.getJsyId());
		dangerQueryWrapper.lambda().eq(AnbiaoJiashiyuanMoveInfo::getType, jiashiyuanMoveInfo.getType());
		AnbiaoJiashiyuanMoveInfo deail = jiashiyuanMoveInfoService.getBaseMapper().selectOne(dangerQueryWrapper);
		if(deail == null){
			ii = jiashiyuanMoveInfoService.save(jiashiyuanMoveInfo);
			if(ii){
				JiaShiYuan jiaShiYuan = new JiaShiYuan();
				jiaShiYuan.setId(jiashiyuanMoveInfo.getJsyId());
				if(user != null){
					jiaShiYuan.setCaozuoren(user.getUserName());
					jiaShiYuan.setCaozuorenid(user.getUserId());
				}
				jiaShiYuan.setCaozuoshijian(DateUtil.now());
				jiaShiYuan.setStatus(jiashiyuanMoveInfo.getType());
				if(jiashiyuanMoveInfo.getType() == 1 || jiashiyuanMoveInfo.getType() == 2 || jiashiyuanMoveInfo.getType() == 5 || jiashiyuanMoveInfo.getType() == 6){
					jiaShiYuan.setIsdelete(jiashiyuanMoveInfo.getType());
				}
				ii = jiaShiYuanService.updateById(jiaShiYuan);
				if(ii){
					//解绑人车绑定关系
					QueryWrapper<AnbiaoCheliangJiashiyuanDaily> anbiaoCheliangJiashiyuanDailyQueryWrapper = new QueryWrapper<>();
					anbiaoCheliangJiashiyuanDailyQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuanDaily::getJiashiyuanid,jiashiyuanMoveInfo.getJsyId());
					anbiaoCheliangJiashiyuanDailyQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuanDaily::getVstatus,"1");
					AnbiaoCheliangJiashiyuanDaily anbiaoCheliangJiashiyuanDaily = cheliangJiashiyuanDailyService.getBaseMapper().selectOne(anbiaoCheliangJiashiyuanDailyQueryWrapper);
					if (anbiaoCheliangJiashiyuanDaily!=null){
						anbiaoCheliangJiashiyuanDaily.setVstatus(0);
						anbiaoCheliangJiashiyuanDaily.setUpdatetime(DateUtil.now());
						cheliangJiashiyuanDailyService.updateById(anbiaoCheliangJiashiyuanDaily);
					}
					anbiaoCheliangJiashiyuanDailyQueryWrapper = new QueryWrapper<>();
					anbiaoCheliangJiashiyuanDailyQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuanDaily::getJiashiyuanid,jiashiyuanMoveInfo.getJsyId());
					anbiaoCheliangJiashiyuanDailyQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuanDaily::getGstatus,"1");
					anbiaoCheliangJiashiyuanDaily = cheliangJiashiyuanDailyService.getBaseMapper().selectOne(anbiaoCheliangJiashiyuanDailyQueryWrapper);
					if (anbiaoCheliangJiashiyuanDaily!=null){
						anbiaoCheliangJiashiyuanDaily.setGstatus(0);
						anbiaoCheliangJiashiyuanDaily.setUpdatetime(DateUtil.now());
						cheliangJiashiyuanDailyService.updateById(anbiaoCheliangJiashiyuanDaily);
					}
					r.setMsg("更新成功");
					r.setCode(200);
					r.setSuccess(true);
				}else{
					r.setMsg("更新失败");
					r.setCode(500);
					r.setSuccess(false);
				}
			}else{
				r.setMsg("更新失败");
				r.setCode(500);
				r.setSuccess(false);
			}
		}else{
			r.setMsg("该数据已添加");
			r.setCode(500);
			r.setSuccess(false);
		}
		return r;
	}

	@PostMapping("/updateDeptId")
	@ApiLog("驾驶员管理-驾驶员异动")
	@ApiOperation(value = "驾驶员管理-驾驶员异动", notes = "传入驾驶员ID,企业ID", position = 3)
	public R updateDeptId(@RequestParam String id, @RequestParam String deptId, BladeUser user) {
		R r = new R();
		boolean ss = false;
		if(user == null) {
			r.setMsg("未授权");
			r.setCode(401);
			return r;
		}
		String[] idsss = id.split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[]  idss= listid.toArray(new String[1]);
		for(int i = 0;i< idss.length;i++){
			//将之前企业的资料进行封存，新增到新的企业下
			//身份证
			UUID sfzId = UUID.randomUUID();
			QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<JiaShiYuan>();
			jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId, id);
			jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete, 0);
			JiaShiYuan jiaShiYuan = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
			if(jiaShiYuan != null){
				jiaShiYuan.setDeptId(Integer.parseInt(deptId));
				jiaShiYuan.setId(sfzId.toString());
				jiaShiYuan.setCaozuoren(user.getUserName());
				jiaShiYuan.setCaozuorenid(user.getUserId());
				jiaShiYuan.setCreatetime(DateUtil.now());
				jiaShiYuan.setDenglumima(DigestUtil.encrypt(jiaShiYuan.getShoujihaoma().substring(jiaShiYuan.getShoujihaoma().length() - 6)));
				jiaShiYuan.setIsdelete(0);
				jiaShiYuan.setStatus(0);
				ss = jiaShiYuanService.save(jiaShiYuan);
			}
			//入职登记表
			QueryWrapper<AnbiaoJiashiyuanRuzhi> ruzhiQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanRuzhi>();
			ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrAjIds, id);
			ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrDelete, "0");
			AnbiaoJiashiyuanRuzhi ruzhi = ruzhiService.getBaseMapper().selectOne(ruzhiQueryWrapper);
			if(ruzhi != null){
				UUID uuid = UUID.randomUUID();
				ruzhi.setAjrIds(uuid.toString());
				ruzhi.setAjrAjIds(sfzId.toString());
				ruzhi.setAjrCreateByName(user.getUserName());
				ruzhi.setAjrCreateByIds(user.getUserId().toString());
				ruzhi.setAjrApproverTime(DateUtil.now());
				ruzhi.setAjrCreateTime(DateUtil.now());
				ruzhi.setAjrDelete("0");
				ss = ruzhiService.save(ruzhi);
			}
			//驾驶证
			QueryWrapper<AnbiaoJiashiyuanJiashizheng> jiashizhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanJiashizheng>();
			jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjAjIds, id);
			jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjDelete, "0");
			AnbiaoJiashiyuanJiashizheng jiashizheng = jiashizhengService.getBaseMapper().selectOne(jiashizhengQueryWrapper);
			if(jiashizheng != null){
				UUID uuid = UUID.randomUUID();
				jiashizheng.setAjjIds(uuid.toString());
				jiashizheng.setAjjAjIds(sfzId.toString());
				jiashizheng.setAjjCreateByName(user.getUserName());
				jiashizheng.setAjjCreateByIds(user.getUserId().toString());
				jiashizheng.setAjjFirstIssue(DateUtil.now());
				jiashizheng.setAjjCreateTime(DateUtil.now());
				jiashizheng.setAjjDelete("0");
				ss = jiashizhengService.save(jiashizheng);
			}
			//从业资格证
			QueryWrapper<AnbiaoJiashiyuanCongyezigezheng> congyezigezhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanCongyezigezheng>();
			congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcAjIds, id);
			congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcDelete, "0");
			AnbiaoJiashiyuanCongyezigezheng congyezigezheng = congyezigezhengService.getBaseMapper().selectOne(congyezigezhengQueryWrapper);
			if(congyezigezheng != null){
				UUID uuid = UUID.randomUUID();
				congyezigezheng.setAjcIds(uuid.toString());
				congyezigezheng.setAjcAjIds(sfzId.toString());
				congyezigezheng.setAjcCreateByName(user.getUserName());
				congyezigezheng.setAjcCreateByIds(user.getUserId().toString());
				congyezigezheng.setAjcInitialIssuing(DateUtil.now());
				congyezigezheng.setAjcCreateTime(DateUtil.now());
				congyezigezheng.setAjcDelete("0");
				ss = congyezigezhengService.save(congyezigezheng);
			}
			//体检表
			QueryWrapper<AnbiaoJiashiyuanTijian> tijianQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanTijian>();
			tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtAjIds, id);
			tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtDelete, "0");
			AnbiaoJiashiyuanTijian tijian = tijianService.getBaseMapper().selectOne(tijianQueryWrapper);
			if(tijian != null){
				UUID uuid = UUID.randomUUID();
				tijian.setAjtIds(uuid.toString());
				tijian.setAjtAjIds(sfzId.toString());
				tijian.setAjtCreateByName(user.getUserName());
				tijian.setAjtCreateByIds(user.getUserId().toString());
				tijian.setAjtCreateTime(DateUtil.now());
				tijian.setAjtDelete("0");
				ss = tijianService.save(tijian);
			}
			//岗位培训三级教育卡
			QueryWrapper<AnbiaoJiashiyuanGangqianpeixun> gangqianpeixunQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanGangqianpeixun>();
			gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgAjIds, id);
			gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgDelete, "0");
			AnbiaoJiashiyuanGangqianpeixun gangqianpeixun = gangqianpeixunService.getBaseMapper().selectOne(gangqianpeixunQueryWrapper);
			if(gangqianpeixun != null){
				UUID uuid = UUID.randomUUID();
				gangqianpeixun.setAjgIds(uuid.toString());
				gangqianpeixun.setAjgAjIds(sfzId.toString());
				gangqianpeixun.setAjgCreateByName(user.getUserName());
				gangqianpeixun.setAjgCreateByIds(user.getUserId().toString());
				gangqianpeixun.setAjgCreateTime(DateUtil.now());
				gangqianpeixun.setAjgDelete("0");
				ss = gangqianpeixunService.save(gangqianpeixun);
			}
			//三年无重大责任证明
			QueryWrapper<AnbiaoJiashiyuanWuzezhengming> wuzezhengmingQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanWuzezhengming>();
			wuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming::getAjwAjIds, id);
			wuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming::getAjwDelete, "0");
			AnbiaoJiashiyuanWuzezhengming wuzezhengming = wuzezhengmingService.getBaseMapper().selectOne(wuzezhengmingQueryWrapper);
			if (wuzezhengming != null){
				UUID uuid = UUID.randomUUID();
				wuzezhengming.setAjwIds(uuid.toString());
				wuzezhengming.setAjwAjIds(sfzId.toString());
				wuzezhengming.setAjwCreateByName(user.getUserName());
				wuzezhengming.setAjwCreateByIds(user.getUserId().toString());
				wuzezhengming.setAjwDate(DateUtil.now());
				wuzezhengming.setAjwCreateTime(DateUtil.now());
				wuzezhengming.setAjwDelete("0");
				ss = wuzezhengmingService.save(wuzezhengming);
			}
			//驾驶员安全责任书
			QueryWrapper<AnbiaoJiashiyuanAnquanzerenshu> anquanzerenshuQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanAnquanzerenshu>();
			anquanzerenshuQueryWrapper.lambda().eq(AnbiaoJiashiyuanAnquanzerenshu::getAjaAjIds, id);
			anquanzerenshuQueryWrapper.lambda().eq(AnbiaoJiashiyuanAnquanzerenshu::getAjaDelete, "0");
			AnbiaoJiashiyuanAnquanzerenshu anquanzerenshu = anquanzerenshuService.getBaseMapper().selectOne(anquanzerenshuQueryWrapper);
			if(anquanzerenshu != null){
				UUID uuid = UUID.randomUUID();
				anquanzerenshu.setAjaIds(uuid.toString());
				anquanzerenshu.setAjaAjIds(sfzId.toString());
				anquanzerenshu.setAjaCreateByName(user.getUserName());
				anquanzerenshu.setAjaCreateByIds(user.getUserId().toString());
				anquanzerenshu.setAjaCreateByName(anquanzerenshu.getAjaCreateByName());
				anquanzerenshu.setAjaCreateByIds(anquanzerenshu.getAjaCreateByIds());
				anquanzerenshu.setAjaAutographTime(DateUtil.now());
				anquanzerenshu.setAjaCreateTime(DateUtil.now());
				anquanzerenshu.setAjaDelete("0");
				ss = anquanzerenshuService.save(anquanzerenshu);
			}
			//驾驶员职业危害告知书
			QueryWrapper<AnbiaoJiashiyuanWeihaigaozhishu> weihaigaozhishuQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanWeihaigaozhishu>();
			weihaigaozhishuQueryWrapper.lambda().eq(AnbiaoJiashiyuanWeihaigaozhishu::getAjwAjIds, id);
			weihaigaozhishuQueryWrapper.lambda().eq(AnbiaoJiashiyuanWeihaigaozhishu::getAjwDelete, "0");
			AnbiaoJiashiyuanWeihaigaozhishu weihaigaozhishu = weihaigaozhishuService.getBaseMapper().selectOne(weihaigaozhishuQueryWrapper);
			if(weihaigaozhishu != null){
				UUID uuid = UUID.randomUUID();
				weihaigaozhishu.setAjwIds(uuid.toString());
				weihaigaozhishu.setAjwAjIds(sfzId.toString());
				weihaigaozhishu.setAjwCreateByName(user.getUserName());
				weihaigaozhishu.setAjwCreateByIds(user.getUserId().toString());
				weihaigaozhishu.setAjwAutographTime(DateUtil.now());
				weihaigaozhishu.setAjwCreateTime(DateUtil.now());
				weihaigaozhishu.setAjwDelete("0");
				ss = weihaigaozhishuService.save(weihaigaozhishu);
			}
			//劳动合同
			QueryWrapper<AnbiaoJiashiyuanLaodonghetong> laodonghetongQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanLaodonghetong>();
			laodonghetongQueryWrapper.lambda().eq(AnbiaoJiashiyuanLaodonghetong::getAjwAjIds, id);
			laodonghetongQueryWrapper.lambda().eq(AnbiaoJiashiyuanLaodonghetong::getAjwDelete, "0");
			AnbiaoJiashiyuanLaodonghetong laodonghetong = laodonghetongService.getBaseMapper().selectOne(laodonghetongQueryWrapper);
			if(laodonghetong != null){
				UUID uuid = UUID.randomUUID();
				laodonghetong.setAjwIds(uuid.toString());
				laodonghetong.setAjwAjIds(sfzId.toString());
				laodonghetong.setAjwCreateByName(user.getUserName());
				laodonghetong.setAjwCreateByIds(user.getUserId().toString());
				laodonghetong.setAjwAutographAutograph(DateUtil.now());
				laodonghetong.setAjwCreateTime(DateUtil.now());
				laodonghetong.setAjwDelete("0");
				ss = laodonghetongService.save(laodonghetong);
			}
			//其他
			QueryWrapper<AnbiaoJiashiyuanQita> qitaQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanQita>();
			qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita::getAjtAjIds, id);
			qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita::getAjtDelete, "0");
			AnbiaoJiashiyuanQita qita = qitaService.getBaseMapper().selectOne(qitaQueryWrapper);
			if(qita != null){
				UUID uuid = UUID.randomUUID();
				qita.setAjtIds(uuid.toString());
				qita.setAjtAjIds(sfzId.toString());
				qita.setAjtCreateByName(user.getUserName());
				qita.setAjtCreateByIds(user.getUserId().toString());
				qita.setAjtPhysicalExaminationDate(DateUtil.now());
				qita.setAjtCreateTime(DateUtil.now());
				qita.setAjtDelete("0");
				ss = qitaService.save(qita);
			}
//			boolean ss = jiaShiYuanService.updateDeptId(deptId,idsss[i]);
			if (ss){
				r.setMsg("更新成功");
				r.setCode(200);
				r.setData(ss);
			}else{
				r.setMsg("更新失败");
				r.setCode(500);
				r.setData(null);
			}
		}
		return r;
	}

	@PostMapping("/list")
	@ApiLog("分页-非在职驾驶员资料")
	@ApiOperation(value = "分页-非在职驾驶员资料", notes = "传入JiaShiYuanPage", position = 5)
	public R<JiaShiYuanPage<JiaShiYuanListVO>> list(@RequestBody JiaShiYuanPage jiaShiYuanPage) {
		JiaShiYuanPage<JiaShiYuanListVO> pages = jiashiyuanMoveInfoService.selectPageList(jiaShiYuanPage);
		return R.data(pages);
	}

	@PostMapping("/getGHCPageList")
	@ApiLog("分页-公海池驾驶员资料")
	@ApiOperation(value = "分页-公海池驾驶员资料", notes = "传入JiaShiYuanPage", position = 6)
	public R<JiaShiYuanPage<JiaShiYuanListVO>> getGHCPageList(@RequestBody JiaShiYuanPage jiaShiYuanPage) {
		JiaShiYuanPage<JiaShiYuanListVO> pages = jiashiyuanMoveInfoService.selectGHCPageList(jiaShiYuanPage);
		return R.data(pages);
	}

	/**
	 * 查询详情
	 */
	@GetMapping("/detail")
	@ApiLog("详情-驾驶员资料管理")
	@ApiOperation(value = "详情-驾驶员资料管理", notes = "传入id、type", position = 4)
	@ApiImplicitParams({@ApiImplicitParam(name = "id", value = "驾驶员ID", required = true),
		@ApiImplicitParam(name = "type", value = "分类（1：入职登记表，2：身份证，3：驾驶证，4:从业资格证,5:体检表,6:岗前培训三级教育卡," +
			"7:三年无重大责任事故正面,8:驾驶员安全责任书,9:驾驶员职业危害告知书,10:劳动合同,11:其他,）", required = true),
	})
	public R detail(String id, int type) {
		R r = new R();
		QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<JiaShiYuan>();
		jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId, id);
		JiaShiYuan detal = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
		if (detal != null) {
			///入职登记表///
			if (type == 1) {
				QueryWrapper<AnbiaoJiashiyuanRuzhi> ruzhiQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanRuzhi>();
				ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrAjIds, detal.getId());
				AnbiaoJiashiyuanRuzhi ruzhiInfo = ruzhiService.getBaseMapper().selectOne(ruzhiQueryWrapper);
				if (ruzhiInfo != null) {
					//本人照片(人员头像)
					if (StrUtil.isNotEmpty(ruzhiInfo.getAjrHeadPortrait()) && ruzhiInfo.getAjrHeadPortrait().contains("http") == false) {
						ruzhiInfo.setAjrHeadPortrait(fileUploadClient.getUrl(ruzhiInfo.getAjrHeadPortrait()));
					}
					ruzhiInfo.setAjrAjIds(detal.getId());
					r.setData(ruzhiInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(200);
					r.setMsg("暂无数据");
					return r;
				}

			}

			///身份证///
			if (type == 2) {
				jiaShiYuanQueryWrapper = new QueryWrapper<JiaShiYuan>();
				jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId, id);
				JiaShiYuan shenfenzhengInfo = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
				if (shenfenzhengInfo != null) {
					//照片(人员头像)
//					if(StrUtil.isNotEmpty(detal.getZhaopian()) && detal.getZhaopian().contains("http") == false){
//						detal.setZhaopian(fileUploadClient.getUrl(detal.getZhaopian()));
//					}
					//身份证附件
					if (StrUtil.isNotEmpty(shenfenzhengInfo.getShenfenzhengfujian()) && shenfenzhengInfo.getShenfenzhengfujian().contains("http") == false) {
						shenfenzhengInfo.setShenfenzhengfujian(fileUploadClient.getUrl(shenfenzhengInfo.getShenfenzhengfujian()));
					}
					//身份证附件反面
					if (StrUtil.isNotEmpty(shenfenzhengInfo.getShenfenzhengfanmianfujian()) && shenfenzhengInfo.getShenfenzhengfanmianfujian().contains("http") == false) {
						shenfenzhengInfo.setShenfenzhengfanmianfujian(fileUploadClient.getUrl(shenfenzhengInfo.getShenfenzhengfanmianfujian()));
					}

					r.setData(shenfenzhengInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(200);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///驾驶证///
			if (type == 3) {
				QueryWrapper<AnbiaoJiashiyuanJiashizheng> jiashizhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanJiashizheng>();
				jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjAjIds, detal.getId());
				AnbiaoJiashiyuanJiashizheng jiashizhengInfo = jiashizhengService.getBaseMapper().selectOne(jiashizhengQueryWrapper);
				if (jiashizhengInfo != null) {
					//驾驶证正面照片
					if (StrUtil.isNotEmpty(jiashizhengInfo.getAjjFrontPhotoAddress()) && jiashizhengInfo.getAjjFrontPhotoAddress().contains("http") == false) {
						jiashizhengInfo.setAjjFrontPhotoAddress(fileUploadClient.getUrl(jiashizhengInfo.getAjjFrontPhotoAddress()));
					}
					//驾驶证反面照片
					if (StrUtil.isNotEmpty(jiashizhengInfo.getAjjAttachedPhotos()) && jiashizhengInfo.getAjjAttachedPhotos().contains("http") == false) {
						jiashizhengInfo.setAjjAttachedPhotos(fileUploadClient.getUrl(jiashizhengInfo.getAjjAttachedPhotos()));
					}
					r.setData(jiashizhengInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(200);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///从业资格证///
			if (type == 4) {
				QueryWrapper<AnbiaoJiashiyuanCongyezigezheng> congyezigezhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanCongyezigezheng>();
				congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcAjIds, detal.getId());
				AnbiaoJiashiyuanCongyezigezheng congyezigezhengInfo = congyezigezhengService.getBaseMapper().selectOne(congyezigezhengQueryWrapper);
				if (congyezigezhengInfo != null) {
					//从业资格证照片
					if (StrUtil.isNotEmpty(congyezigezhengInfo.getAjcLicence()) && congyezigezhengInfo.getAjcLicence().contains("http") == false) {
						congyezigezhengInfo.setAjcLicence(fileUploadClient.getUrl(congyezigezhengInfo.getAjcLicence()));
					}
					congyezigezhengInfo.setAjcAjIds(detal.getId());
					r.setData(congyezigezhengInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(200);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///体检表///
			if (type == 5) {
				QueryWrapper<AnbiaoJiashiyuanTijian> tijianQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanTijian>();
				tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtAjIds, detal.getId());
				AnbiaoJiashiyuanTijian tijianInfo = tijianService.getBaseMapper().selectOne(tijianQueryWrapper);
				if (tijianInfo != null) {
					//体检附件
					if (StrUtil.isNotEmpty(tijianInfo.getAjtEnclosure()) && tijianInfo.getAjtEnclosure().contains("http") == false) {
						tijianInfo.setAjtEnclosure(fileUploadClient.getUrl(tijianInfo.getAjtEnclosure()));
					}
					tijianInfo.setAjtAjIds(detal.getId());
					r.setData(tijianInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(200);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///岗前培训三级教育卡///
			if (type == 6) {
				QueryWrapper<AnbiaoJiashiyuanGangqianpeixun> gangqianpeixunQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanGangqianpeixun>();
				gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgAjIds, detal.getId());
				AnbiaoJiashiyuanGangqianpeixun gangqianpeixunInfo = gangqianpeixunService.getBaseMapper().selectOne(gangqianpeixunQueryWrapper);
				if (gangqianpeixunInfo != null) {
					//培训附件
					if (StrUtil.isNotEmpty(gangqianpeixunInfo.getAjgTrainingEnclosure()) && gangqianpeixunInfo.getAjgTrainingEnclosure().contains("http") == false) {
						gangqianpeixunInfo.setAjgTrainingEnclosure(fileUploadClient.getUrl(gangqianpeixunInfo.getAjgTrainingEnclosure()));
					}
					gangqianpeixunInfo.setAjgAjIds(detal.getId());
					r.setData(gangqianpeixunInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(200);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///三年无重大责任事故正面///
			if (type == 7) {
				QueryWrapper<AnbiaoJiashiyuanWuzezhengming> wuzezhengmingQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanWuzezhengming>();
				wuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming::getAjwAjIds, detal.getId());
				AnbiaoJiashiyuanWuzezhengming wuzezhengmingInfo = wuzezhengmingService.getBaseMapper().selectOne(wuzezhengmingQueryWrapper);
				if (wuzezhengmingInfo != null) {
					//无责证明附件
					if (StrUtil.isNotEmpty(wuzezhengmingInfo.getAjwEnclosure()) && wuzezhengmingInfo.getAjwEnclosure().contains("http") == false) {
						wuzezhengmingInfo.setAjwEnclosure(fileUploadClient.getUrl(wuzezhengmingInfo.getAjwEnclosure()));
					}
					wuzezhengmingInfo.setAjwAjIds(detal.getId());
					r.setData(wuzezhengmingInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(200);
					r.setMsg("暂无数据");
					return r;
				}

			}

			///驾驶员安全责任书///
			if (type == 8) {
				QueryWrapper<AnbiaoJiashiyuanAnquanzerenshu> anquanzerenshuQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanAnquanzerenshu>();
				anquanzerenshuQueryWrapper.lambda().eq(AnbiaoJiashiyuanAnquanzerenshu::getAjaAjIds, detal.getId());
				AnbiaoJiashiyuanAnquanzerenshu anquanzerenshuInfo = anquanzerenshuService.getBaseMapper().selectOne(anquanzerenshuQueryWrapper);
				if (anquanzerenshuInfo != null) {
					//安全责任书附件
					if (StrUtil.isNotEmpty(anquanzerenshuInfo.getAjaEnclosure()) && anquanzerenshuInfo.getAjaEnclosure().contains("http") == false) {
						anquanzerenshuInfo.setAjaEnclosure(fileUploadClient.getUrl(anquanzerenshuInfo.getAjaEnclosure()));
					}
					//安全责任书签名附件
					if (StrUtil.isNotEmpty(anquanzerenshuInfo.getAjaAutographEnclosure()) && anquanzerenshuInfo.getAjaAutographEnclosure().contains("http") == false) {
						anquanzerenshuInfo.setAjaAutographEnclosure(fileUploadClient.getUrl(anquanzerenshuInfo.getAjaAutographEnclosure()));
					}
					anquanzerenshuInfo.setAjaAjIds(detal.getId());
					anquanzerenshuInfo.setDeptName(detal.getDeptName());
					r.setData(anquanzerenshuInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(200);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///驾驶员职业危害告知书///
			if (type == 9) {
				QueryWrapper<AnbiaoJiashiyuanWeihaigaozhishu> weihaigaozhishuQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanWeihaigaozhishu>();
				weihaigaozhishuQueryWrapper.lambda().eq(AnbiaoJiashiyuanWeihaigaozhishu::getAjwAjIds, detal.getId());
				AnbiaoJiashiyuanWeihaigaozhishu weihaigaozhishuInfo = weihaigaozhishuService.getBaseMapper().selectOne(weihaigaozhishuQueryWrapper);

				if (weihaigaozhishuInfo != null) {
					//无责证明附件
					if (StrUtil.isNotEmpty(weihaigaozhishuInfo.getAjwEnclosure()) && weihaigaozhishuInfo.getAjwEnclosure().contains("http") == false) {
						weihaigaozhishuInfo.setAjwEnclosure(fileUploadClient.getUrl(weihaigaozhishuInfo.getAjwEnclosure()));
					}
					//无责证明签名附件
					if (StrUtil.isNotEmpty(weihaigaozhishuInfo.getAjwAutographEnclosure()) && weihaigaozhishuInfo.getAjwAutographEnclosure().contains("http") == false) {
						weihaigaozhishuInfo.setAjwAutographEnclosure(fileUploadClient.getUrl(weihaigaozhishuInfo.getAjwAutographEnclosure()));
					}
					weihaigaozhishuInfo.setAjwAjIds(detal.getId());
					r.setData(weihaigaozhishuInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(200);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///劳动合同///
			if (type == 10) {
				QueryWrapper<AnbiaoJiashiyuanLaodonghetong> laodonghetongQueryWrapper = new QueryWrapper<>();
				laodonghetongQueryWrapper.lambda().eq(AnbiaoJiashiyuanLaodonghetong::getAjwAjIds, detal.getId());
				AnbiaoJiashiyuanLaodonghetong laodonghetongInfo = laodonghetongService.getBaseMapper().selectOne(laodonghetongQueryWrapper);

				if (laodonghetongInfo != null) {
					//劳动合同附件
					if (StrUtil.isNotEmpty(laodonghetongInfo.getAjwEnclosure()) && laodonghetongInfo.getAjwEnclosure().contains("http") == false) {
						laodonghetongInfo.setAjwEnclosure(fileUploadClient.getUrl(laodonghetongInfo.getAjwEnclosure()));
					}
					//劳动合同签名附件
					if (StrUtil.isNotEmpty(laodonghetongInfo.getAjwAutographEnclosure()) && laodonghetongInfo.getAjwAutographEnclosure().contains("http") == false) {
						laodonghetongInfo.setAjwAutographEnclosure(fileUploadClient.getUrl(laodonghetongInfo.getAjwAutographEnclosure()));
					}
					laodonghetongInfo.setAjwAjIds(detal.getId());
					laodonghetongInfo.setDeptName(detal.getDeptName());
					QueryWrapper<AnbiaoJiashiyuanRuzhi> ruzhiQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanRuzhi>();
					ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrAjIds, detal.getId());
					ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrDelete, "0");
					AnbiaoJiashiyuanRuzhi ruzhiInfo = ruzhiService.getBaseMapper().selectOne(ruzhiQueryWrapper);
					if (ruzhiInfo != null) {
						laodonghetongInfo.setDriverAddress(ruzhiInfo.getAjrAddress());
					}
					laodonghetongInfo.setDriverNo(detal.getShenfenzhenghao());
					laodonghetongInfo.setDriverPhone(detal.getShoujihaoma());

					r.setData(laodonghetongInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(200);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///其他///
			if (type == 11) {
				QueryWrapper<AnbiaoJiashiyuanQita> qitaQueryWrapper = new QueryWrapper<>();
				qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita::getAjtAjIds, detal.getId());
				AnbiaoJiashiyuanQita qitaInfo = qitaService.getBaseMapper().selectOne(qitaQueryWrapper);

				if (qitaInfo != null) {
					//劳动合同附件
					if (StrUtil.isNotEmpty(qitaInfo.getAjtEnclosure()) && qitaInfo.getAjtEnclosure().contains("http") == false) {
						qitaInfo.setAjtEnclosure(fileUploadClient.getUrl(qitaInfo.getAjtEnclosure()));
					}
					qitaInfo.setAjtAjIds(detal.getId());
					r.setData(qitaInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(200);
					r.setMsg("暂无数据");
					return r;
				}
			}

		} else {
			r.setMsg("暂无数据");
			r.setCode(200);
			return r;
		}
		return r;
	}


}
