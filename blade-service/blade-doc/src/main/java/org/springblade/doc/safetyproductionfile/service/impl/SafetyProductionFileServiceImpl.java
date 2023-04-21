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
package org.springblade.doc.safetyproductionfile.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.node.INode;
import org.springblade.doc.safetyproductionfile.entity.AnbiaoSafetyproductionfileNum;
import org.springblade.doc.safetyproductionfile.entity.SafetyProductionFile;
import org.springblade.doc.safetyproductionfile.mapper.AnbiaoSafetyproductionfileNumMapper;
import org.springblade.doc.safetyproductionfile.mapper.SafetyProductionFileMapper;
import org.springblade.doc.safetyproductionfile.service.IAnbiaoSafetyproductionfileNumService;
import org.springblade.doc.safetyproductionfile.service.ISafetyProductionFileService;
import org.springblade.doc.safetyproductionfile.to.SafetyProductionFileTO;
import org.springblade.doc.safetyproductionfile.vo.SafetyProductionFileVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2019-05-28
 */
@Service
@AllArgsConstructor
public class SafetyProductionFileServiceImpl extends ServiceImpl<SafetyProductionFileMapper, SafetyProductionFile> implements ISafetyProductionFileService {

	private SafetyProductionFileMapper mapper;

	@Override
	public IPage<SafetyProductionFileVO> selectSafetyProductionFilePage(IPage<SafetyProductionFileVO> page, SafetyProductionFileVO SafetyProductionFile) {
		return page.setRecords(baseMapper.selectSafetyProductionFilePage(page, SafetyProductionFile));
	}

	@Override
	public Integer selectMaxId() {
		return mapper.selectMaxId();
	}

	@Override
	public Integer selectMaxSorByParentId(Integer id) {
		return mapper.selectMaxSorByParentId(id);
	}

	@Override
	public SafetyProductionFileVO selectMaxDocumentNumber(Integer parentId, Integer deptId) {
		return mapper.selectMaxDocumentNumber(deptId,parentId);
	}

	@Override
	public List<SafetyProductionFileVO> tree(Integer deptId, Integer parentId, String name) {
		return ForestNodeMerger.merge(mapper.tree(deptId,parentId,name));
	}

	@Override
	public List<SafetyProductionFile> getByParentId(Integer parentId,Integer deptId) {
		return mapper.getByParentId(parentId,deptId);
	}

	@Override
	public boolean updateDocumentNumberById(Integer id, String documentNumber) {
		return mapper.updateDocumentNumberById(id,documentNumber);
	}

	@Override
	public boolean updateSortById(Integer originId, Integer sort) {
		return mapper.updateSortById(originId,sort);
	}

	@Override
	public List<SafetyProductionFileVO> boxTree(Integer deptId) {
		return ForestNodeMerger.merge(mapper.boxTree(deptId));
	}

	@Override
	public int updatePreviewRecordById(Integer id) {
		return mapper.updatePreviewRecordById(id);
	}

    @Override
    public List<SafetyProductionFileVO> getMubanTree(Integer deptId,String type,String leixingid) {
		return ForestNodeMerger.merge(mapper.getMubanTree(deptId,type,leixingid));
    }

	@Override
	public List<SafetyProductionFileVO> getMubanTreeWJ(Integer deptId,String type,String leixingid) {
		return ForestNodeMerger.merge(mapper.getMubanTreeWJ(deptId,type,leixingid));
	}

	@Override
    public int getCountByDetpId(Integer id) {
        return mapper.getCountByDetpId(id);
    }

    @Override
    public List<SafetyProductionFileTO> selectByBind(Integer id) {
        return mapper.selectByBind(id);
    }

    @Override
    public SafetyProductionFile selectByBindId(Integer id) {
		return mapper.selectByBindId(id);
    }

    @Override
    public List<SafetyProductionFileVO> bindTree(Integer deptId, Integer parentId, Integer biaozhunhuamubanId) {
        return mapper.bindTree(deptId,parentId,biaozhunhuamubanId);
    }

	@Override
	public List<SafetyProductionFileVO> getSafetyProductionFileTree(Integer deptId, Integer parentId,String Name) {
		return ForestNodeMerger.merge(mapper.getSafetyProductionFileTree(deptId,parentId,Name));
	}

	@Override
	public boolean insertSelective(SafetyProductionFile safetyProductionFile) {
		return mapper.insertSelective(safetyProductionFile);
	}

	@Override
	public List<SafetyProductionFileVO> getSafetyProductionFileMuBan(Integer deptId) {
		return mapper.getSafetyProductionFileMuBan(deptId);
	}

	@Override
	public boolean updateSelective(SafetyProductionFile safetyProductionFile) {
		return mapper.updateSelective(safetyProductionFile);
	}

	@Override
	public boolean updaTierById(SafetyProductionFile safetyProductionFile) {
		return mapper.updaTierById(safetyProductionFile);
	}

	@Override
	public SafetyProductionFileVO getSafetyProductionFileNameByDept(Integer deptId, String Name,String Id,String leixingid,String safeid) {
		return mapper.getSafetyProductionFileNameByDept(deptId, Name, Id, leixingid, safeid);
	}

	@Override
	public SafetyProductionFileVO boxTreeNum(Integer deptId) {
		SafetyProductionFileVO safetyProductionFileVO = new SafetyProductionFileVO();
		Integer num = 0;
		Integer finshNum = 0;
		List<SafetyProductionFileVO> folderList = mapper.boxTreeFolder(deptId,null);
		if(folderList != null && folderList.size() > 0){
			for(int i = 0;i< folderList.size();i++){
				folderList = mapper.boxTreeFolder(deptId,folderList.get(i).getId().toString());
				if(folderList != null && folderList.size() > 0){
					for(int j = 0;j< folderList.size();j++){
						num += folderList.size();
					}
				}
			}
		}
		List<SafetyProductionFileVO> fileVOList = mapper.boxTreeFile(deptId,null);
		if(fileVOList != null && fileVOList.size() > 0){
			for(int j = 0;j< fileVOList.size();j++){
				finshNum += 1;
			}
		}
		safetyProductionFileVO.setFinshNum(finshNum);
		safetyProductionFileVO.setNum(num);
		return safetyProductionFileVO;
	}


}
