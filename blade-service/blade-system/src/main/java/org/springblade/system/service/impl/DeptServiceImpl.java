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
package org.springblade.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.system.entity.Dept;
import org.springblade.system.mapper.DeptMapper;
import org.springblade.system.service.IDeptService;
import org.springblade.system.vo.DeptSubVO;
import org.springblade.system.vo.DeptVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 * @author 呵呵哒
 */
@Service
@AllArgsConstructor
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

	private DeptMapper deptMapper;

	//private IDeadlineClient deadlineClient;

	@Override
	public IPage<DeptVO> selectDeptPage(IPage<DeptVO> page, DeptVO dept) {
		return page.setRecords(baseMapper.selectDeptPage(page, dept));
	}

	@Override
	public List<DeptVO> treeDeptImage(String id,String type,String deptName) {
		return ForestNodeMerger.merge(baseMapper.treeDeptImage(id,type,deptName));
	}

	@Override
	public List<DeptVO> tree(String id,String type) {
		return ForestNodeMerger.merge(baseMapper.tree(id,type));
	}

	@Override
	public List<DeptVO> treeDG(String id) {
		return ForestNodeMerger.merge(baseMapper.treeDG(id));
	}

	@Override
	public List<DeptVO> treeList(String id) {
		return baseMapper.treeDG(id);
	}

	@Override
	public List<DeptVO> YDtree(String id) {
		return ForestNodeMerger.merge(baseMapper.YDtree(id));
	}

	@Override
	public Dept  selectByTreeCode(Integer id) {
		return deptMapper.selectByTreeCode(id);
	}

	@Override
	public int selectCountByparentId(Integer id) {
		return deptMapper.selectCountByparentId(id);
	}

	@Override
	public Dept selectDeptById(Integer id) {
		return deptMapper.selectDeptById(id);
	}

	@Override
	public int selectMaxId() {
		return deptMapper.selectMaxId();
	}

	@Override
	public int selectByName(String fullName) {
		return deptMapper.selectByName(fullName);
	}

	@Override
	public boolean insertDept(Dept dept) {
		return deptMapper.insertDept(dept);
	}

	@Override
	public Dept selectByJGBM(String type, String deptId) {
//		//唯一标识
//		String mainboard= SerialNumberUtil.getmainboard();
//		Deadline deadline = deadlineClient.selectByMainboard(mainboard);
//		if(deadline ==null){
//			Deadline blogs=new Deadline();
//			blogs.setMainboard(mainboard);
//			deadlineClient.saveOrUpdate(blogs);
//		}else{
//			//当前时间
//			String now = DateUtil.now();
//			if(deadline.getDeadline().toString()!=null){
//				Integer i=now.compareTo(deadline.getDeadline().toString());
//				System.out.println(i);
//				if(i<0){
//					deptId="0";
//				}
//			}else{
//				deptId="0";
//			}
//		}
		return deptMapper.selectByJGBM(type,deptId);
	}

	@Override
	public Dept selectByJGQY(String type, String deptId) {
		return deptMapper.selectByJGBM(type,deptId);
	}

	@Override
	public Dept selectByDeptId(Integer id) {
		return deptMapper.selectByDeptId(id);
	}

    @Override
    public List<DeptSubVO> getDeptSubTree(Integer deptId) {
        return ForestNodeMerger.merge(deptMapper.getDeptSubTree(deptId));
    }

    @Override
    public List<Integer> getDetpIds(Integer deptId) {
        return deptMapper.getDetpIds(deptId);
    }

	@Override
	public List<DeptSubVO> getDeptById(Integer parentId, Integer type, Integer remark){
		if(parentId == null){
			parentId = 0;
		}
		if(remark == null || remark == 0){
			if(type == 1){
				return deptMapper.getRegionList("市",parentId);
			}else if(type == 2){
				return deptMapper.getRegionList("县",parentId);
			}else{
				return deptMapper.getRegionList("省",0);
			}
		}else{
			if(type == 1){
//				return deptMapper.getRegionGovList("shiZF",parentId);
				return deptMapper.getRegionGovList_Shi(parentId);
			}else if(type == 2){
				return deptMapper.getRegionGovList("xianZF",parentId);
			}else{
				return deptMapper.getRegionGovList("shengZF",0);
			}
		}
	}

	@Override
	public Dept getDeptByName(String name) {
		return deptMapper.getDeptByName(name);
	}

	@Override
	public List<Dept> getByName(String deptname) {
		return deptMapper.getByName(deptname);
	}

	@Override
	public List<Dept> QiYeList(Integer deptId) {
		return deptMapper.QiYeList(deptId);
	}
}
