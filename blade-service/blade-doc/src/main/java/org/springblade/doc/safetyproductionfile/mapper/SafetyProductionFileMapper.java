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
package org.springblade.doc.safetyproductionfile.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import feign.Param;
import org.springblade.doc.safetyproductionfile.entity.SafetyProductionFile;
import org.springblade.doc.safetyproductionfile.to.SafetyProductionFileTO;
import org.springblade.doc.safetyproductionfile.vo.SafetyProductionFileVO;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author Blade
 * @since 2019-05-28
 */
public interface SafetyProductionFileMapper extends BaseMapper<SafetyProductionFile> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param SafetyProductionFile
	 * @return
	 */
	List<SafetyProductionFileVO> selectSafetyProductionFilePage(IPage page, SafetyProductionFileVO SafetyProductionFile);

	/**
	 *查询企业文件最大id
	 * @author: hyp
	 * @date: 2019/5/28 16:54
	 * @param
	 * @return: java.lang.Integer
	 */
	Integer selectMaxId();

	/**
	 *查询同级文件最大排序号
	 * @author: hyp
	 * @date: 2019/5/28 16:57
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
	 *目录树
	 * @author: hyp
	 * @date: 2019/5/30 9:38
	 * @param deptId
	 * @param parentId
	 * @return: java.util.List<org.springblade.anbiao.SafetyProductionFileVO>
	 */
	List<SafetyProductionFileVO> tree(@Param("deptId") Integer deptId, @Param("parentId") Integer parentId, @Param("name") String name);

	/**
	 *查询下级节点
	 * @author: hyp
	 * @date: 2019/5/30 9:38
	 * @param parentId
	 * @return: java.util.List<org.springblade.anbiao.SafetyProductionFile>
	 */
	List<SafetyProductionFile> getByParentId(Integer parentId,Integer deptId);

	/**
	 *修改文件编号
	 * @author: hyp
	 * @date: 2019/5/30 17:24
	 * @param id
	 * @param documentNumber
	 * @return: boolean
	 */
	boolean updateDocumentNumberById(@Param("id") Integer id, @Param("documentNumber") String documentNumber);

	/**
	 *修改文件排序号
	 * @author: hyp
	 * @date: 2019/5/30 17:29
	 * @param id
	 * @param sort
	 * @return: boolean
	 */
	boolean updateSortById(@Param("id") Integer id, @Param("sort") Integer sort);

	/**
	 *获取盒子树-前二级
	 * @author: hyp
	 * @date: 2019/7/31 12:32
	 * @param deptId
	 * @return: java.util.List<org.springblade.doc.safetyproductionfile.vo.SafetyProductionFileVO>
	 */
	List<SafetyProductionFileVO> boxTree(Integer deptId);

	/**
	 *更新访问记录
	 * @author: hyp
	 * @date: 2019/8/12 23:09
	 * @param id
	 * @return: int
	 */
	int updatePreviewRecordById(Integer id);

	/**
	 *获取模板数据
	 * @author: hyp
	 * @date: 2019/9/6 12:49
	 * @param
	 * @return: java.util.List<org.springblade.doc.safetyproductionfile.entity.SafetyProductionFile>
	 */
	List<SafetyProductionFileVO> getMubanTreeWJ(@Param("deptId") Integer deptId,@Param("type") String type,@Param("leixingid") String leixingid);
	List<SafetyProductionFileVO> getMubanTree(@Param("deptId") Integer deptId,@Param("type") String type,@Param("leixingid") String leixingid);

	/**
	 *查询机构下的管理文档数量
	 * @author: hyp
	 * @date: 2019/10/28 16:50
	 * @param id
	 * @return: int
	 */
    int getCountByDetpId(Integer id);

   /**
    *查询标准化文件id绑定的安全生产文档
    * @author: hyp
    * @date: 2019/11/12 22:11
    * @param id
    * @return: org.springblade.doc.safetyproductionfile.entity.SafetyProductionFile
    */
   List<SafetyProductionFileTO> selectByBind(Integer id);

   /**
    *根据绑定id查询安全生产文档
    * @author: hyp
    * @date: 2019/11/12 23:50
    * @param id
    * @return: org.springblade.doc.safetyproductionfile.entity.SafetyProductionFile
    */
	SafetyProductionFile selectByBindId(Integer id);

	/**
	 *获取绑定树
	 * @author: hyp
	 * @date: 2019/11/13 0:45
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
	 * 根据企业ID、文件名称获取数据
	 * @param deptId
	 * @param Name
	 * @return
	 */
	SafetyProductionFileVO getSafetyProductionFileName(Integer deptId,String Name);

	SafetyProductionFileVO getSafetyProductionFileNameByDept(Integer deptId,String Name,String Id,String leixingid,String safeid);

	/**
	 * 修改文件排tier、parent_id
	 * @param safetyProductionFile
	 * @return
	 */
	boolean updaTierById(SafetyProductionFile safetyProductionFile);

	List<SafetyProductionFileVO> boxTreeFolder(Integer deptId,String Id);

	List<SafetyProductionFileVO> boxTreeFile(Integer deptId,String Id);

}
