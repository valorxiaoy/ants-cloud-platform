package com.ants.module.goods.base.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * 商品等级表
 * 
 * @author 小米
 * @date 2020-12-26 16:34:51
 */
@Data
public class ProductGradeDto implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Long id;
	/**
	 * 等级名称
	 */
	private String gradeName;
	/**
	 * 等级编码
	 */
	private String code;
	/**
	 * 所属门店ID
	 */
	private Long storeId;
	/**
	 * 是否禁用
	 */
	private Integer isDelete;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 创建人
	 */
	private Long createId;
	/**
	 * 更新时间
	 */
	private String updateTime;
	/**
	 * 更新人
	 */
	private Long updateId;

}
