package org.springblade.anbiao.chuchejiancha.entity;

import cn.hutool.core.date.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springblade.anbiao.chuchejiancha.vo.AnbiaoCarExamineVO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 *
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
public class FileCarExamineParse {

	/**
	 * ABCD文档各层级File相关信息
	 */
	@Getter
	List<AnbiaoCarExamine> abcdList = new CopyOnWriteArrayList<AnbiaoCarExamine>();


	/**
	 * 文件默认id（取表中最大id+1）
	 */
	@Setter
	private Integer id ;
	@Setter
	private Integer deptId;
	@Setter
	private String createid;

	public void parseAbcdMubanList(List<AnbiaoCarExamineVO> mubanList, Integer upperid,String tier) {
		for (AnbiaoCarExamineVO anbiaoCarExamineVO : mubanList) {
			if(upperid == 1){
				anbiaoCarExamineVO.setParentid(0);
			}else{
				anbiaoCarExamineVO.setParentid(upperid);
			}
			anbiaoCarExamineVO.setId(this.id);
			anbiaoCarExamineVO.setDeptid(this.deptId);
			anbiaoCarExamineVO.setCreateid(this.createid);
			anbiaoCarExamineVO.setCreatetime(DateUtil.now());
			String atier = tier+"-"+this.id;
			anbiaoCarExamineVO.setTreecode(atier);
			this.abcdList.add(anbiaoCarExamineVO);
			int aid = id;
			this.id++;
			List<AnbiaoCarExamineVO> childrenList = (List<AnbiaoCarExamineVO>)(Object)anbiaoCarExamineVO.getChildren();
			if(anbiaoCarExamineVO.getChildren() != null && anbiaoCarExamineVO.getChildren().size()>0){
				parseAbcdMubanList(childrenList,aid,atier);
			}
		}
	}

	public void close() {
    	this.id=null;
    	this.deptId=null;
    	this.abcdList=new ArrayList<AnbiaoCarExamine>();
	}

}
