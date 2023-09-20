package org.springblade.anbiao.repairs.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springblade.anbiao.repairs.entity.AnbiaoSparePartsStorePerson;

import java.util.List;

/**
 * @author Administrator
 * @create 2023-09-19 10:59
 */
@Data
@ApiModel(value = "AnbiaoSparePartsStorePersonVO对象", description = "AnbiaoSparePartsStorePersonVO对象")
public class AnbiaoSparePartsStorePersonVO {

	private List<AnbiaoSparePartsStorePerson> sparePartsStorePersonList;

}
