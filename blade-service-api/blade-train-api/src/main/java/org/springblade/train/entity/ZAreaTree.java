package org.springblade.train.entity;

import lombok.Data;

/**
 * 行政区域树形实体对象
 * @author tan
 *
 */
@Data
public class ZAreaTree {

	/**
	 * id
	 */
	private Integer id;
	/**
	 * 父Id
	 */
	private Integer pId;
	/**
	 * 行政区简称
	 */
	private String name;


//	/**
//	 *
//	 */
//	private int type;
//	private String isParent;
}
