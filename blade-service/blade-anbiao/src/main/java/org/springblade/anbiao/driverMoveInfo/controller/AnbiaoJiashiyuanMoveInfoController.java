package org.springblade.anbiao.driverMoveInfo.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
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
@Api(value = "驾驶员管理-驾驶员异动", tags = "驾驶员管理-驾驶员异动")
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


}
