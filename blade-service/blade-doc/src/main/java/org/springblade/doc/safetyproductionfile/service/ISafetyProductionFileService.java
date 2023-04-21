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
package org.springblade.doc.safetyproductionfile.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import feign.Param;
import org.springblade.doc.safetyproductionfile.entity.SafetyProductionFile;
import org.springblade.doc.safetyproductionfile.to.SafetyProductionFileTO;
import org.springblade.doc.safetyproductionfile.vo.SafetyProductionFileVO;

import java.util.List;

/**
 *  服务类
 *
 * @author Blade
 * @since 2019-05-28
 */
public interface ISafetyProductionFileService extends IService<SafetyProductionFile> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param SafetyProductionFile
	 * @return
	 */
	IPage<SafetyProductionFileVO> selectSafetyProductionFilePage(IPage<SafetyProductionFileVO> page, SafetyProductionFileVO SafetyProductionFile);

	/**
	 *查询企业文件最大id
	 * @author: hyp
	 * @date: 2019/5/28 16:52
	 * @param
	 * @return: java.lang.Integer
	 */
	Integer selectMaxId();

	/**
	 *查询同级文件最大排序号
	 * @author: hyp
	 * @date: 2019/5/28 16:55
	 * @param id
	 * @return: java.lang.Integer
	 */
	Integer selectMaxSorByParentId(Integer id);

	/**
	 * 获取父级ID下最大的documentNumber
	 * @param parentId
	 * @param deptId
	 * @return
	 */
	SafetyProductionFileVO selectMaxDocumentNumber(Integer parentId,Integer deptId);

	/**
	 *企业文件树形结构
	 * @author: hyp
	 * @CreateDate2021-05-29 23:16
	 * @param deptId
	 * @param parentId
	 * @return: java.util.List<org.springblade.anbiao.SafetyProductionFileVO>
	 */
	List<SafetyProductionFileVO> tree(Integer deptId, Integer parentId, String name);

	/**
	 *查询是否存在下级节点
	 * @author: hyp
	 * @date: 2019/5/30 9:37
	 * @param id
	 * @return: java.util.List<org.springblade.anbiao.SafetyProductionFile>
	 */
	List<SafetyProductionFile> getByParentId(Integer id,Integer deptId);

	/**
	 *修改文件编号
	 * @author: hyp
	 * @date: 2019/5/30 17:23
	 * @param id
	 * @param documentNumber
	 * @return: boolean
	 */
	boolean updateDocumentNumberById(Integer id, String documentNumber);

	/**
	 * 修改文件排序号
	 * @param originId
	 * @param sort
	 * @return
	 */
	boolean updateSortById(Integer originId, Integer sort);

	/**
	 *获取盒子树,前二级
	 * @author: hyp
	 * @date: 2019/7/31 12:31
	 * @param deptId
	 * @return: java.util.List<org.springblade.doc.safetyproductionfile.vo.SafetyProductionFileVO>
	 */
	List<SafetyProductionFileVO> boxTree(Integer deptId);

	/**
	 *更新访问记录
	 * @author: hyp
	 * @date: 2019/8/12 23:08
	 * @param id
	 * @return: int
	 */
	int updatePreviewRecordById(Integer id);

	/**
	 *获取模板数据
	 * @author: hyp
	 * @date: 2019/9/6 12:48
	 * @param
	 */
    List<SafetyProductionFileVO> getMubanTree(Integer deptId,String type,String leixingid);
	List<SafetyProductionFileVO> getMubanTreeWJ(Integer deptId,String type,String leixingid);
    /**
     *查询机构的管理文档数量
     * @author: hyp
     * @date: 2019/10/28 16:47
     * @param id
     * @return: int
     */
    int getCountByDetpId(Integer id);

    /**
     *查询标准化文件id绑定的安全生产文档
     * @author: hyp
     * @date: 2019/11/12 22:09
     * @param id
     * @return: org.springblade.doc.safetyproductionfile.entity.SafetyProductionFile
     */
	List<SafetyProductionFileTO> selectByBind(Integer id);

	/**
	 *根据绑定id查询安全生产文档
	 * @author: hyp
	 * @date: 2019/11/12 23:48
	 * @param id
	 * @return: void
	 */
	SafetyProductionFile selectByBindId(Integer id);

	/**
	 *获取绑定树
	 * @author: hyp
	 * @date: 2019/11/13 0:44
	 * @param deptId
	 * @param parentId
	 * @param biaozhunhuamubanId
	 * @return: java.util.List<org.springblade.doc.safetyproductionfile.vo.SafetyProductionFileVO>
	 */
	List<SafetyProductionFileVO> bindTree(Integer deptId, Integer parentId, Integer biaozhunhuamubanId);

	/**
	 * 根据企业ID、父级ID获取安全台账目录树
	 * @param deptId
	 * @param parentId
	 * @return
	 */
	List<SafetyProductionFileVO> getSafetyProductionFileTree(Integer deptId,Integer parentId,String Name);

	/**
	 * 添加数据
	 * @param safetyProductionFile
	 * @return
	 */
	boolean insertSelective(SafetyProductionFile safetyProductionFile);

	/**
	 * 查询模板
	 * @param deptId
	 * @return
	 */
	List<SafetyProductionFileVO> getSafetyProductionFileMuBan(Integer deptId);

	/**
	 * 编辑数据
	 * @param safetyProductionFile
	 * @return
	 */
	boolean updateSelective(SafetyProductionFile safetyProductionFile);

	/**
	 * 修改文件排tier、parent_id
	 * @param safetyProductionFile
	 * @return
	 */
	boolean updaTierById(SafetyProductionFile safetyProductionFile);

	/**
	 * 根据企业ID、文件名称获取数据
	 * @param deptId
	 * @param Name
	 * @return
	 */
	SafetyProductionFileVO getSafetyProductionFileNameByDept(Integer deptId,String Name,String Id,String leixingid,String safeid);

	SafetyProductionFileVO boxTreeNum(Integer deptId);

}
