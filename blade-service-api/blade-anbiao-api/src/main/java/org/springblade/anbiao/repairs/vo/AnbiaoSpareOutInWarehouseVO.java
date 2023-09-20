package org.springblade.anbiao.repairs.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springblade.anbiao.repairs.entity.AnbiaoSpareOutInWarehouse;

import java.util.List;

/**
 * @author Administrator
 * @create 2023-09-19 10:59
 */
@Data
@ApiModel(value = "AnbiaoSpareOutInWarehouseVO对象", description = "AnbiaoSpareOutInWarehouseVO对象")
public class AnbiaoSpareOutInWarehouseVO {

	private List<AnbiaoSpareOutInWarehouse> spareOutInWarehouseList;

}
