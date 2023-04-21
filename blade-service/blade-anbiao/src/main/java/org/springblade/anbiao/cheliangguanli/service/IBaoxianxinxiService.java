/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package org.springblade.anbiao.cheliangguanli.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.cheliangguanli.entity.Baoxianxinxi;
import org.springblade.anbiao.cheliangguanli.page.BaoxianxinxiPage;
import org.springblade.anbiao.cheliangguanli.vo.BaoxianxinxiVO;

/**
 *  服务类
 */
public interface IBaoxianxinxiService extends IService<Baoxianxinxi> {

	BaoxianxinxiVO selectByIds(String id);

	boolean updateDel(String id);

	/**
	 * 自定义分页
	 * @param
	 * @return
	 */
	BaoxianxinxiPage<BaoxianxinxiVO> selectPageList(BaoxianxinxiPage Page);

	/**
	 * 企业端修改保险信息（车辆资料修改）
	 * @param baoxianxinxi
	 * @return
	 */
	boolean updateSelective(Baoxianxinxi baoxianxinxi);
}
