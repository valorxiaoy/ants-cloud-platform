package com.ants.base.goods.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品单位表
 * 
 * @author 小米
 * @date 2020-12-26 16:34:50
 */
@Data
@TableName("goods_unit")
public class ProductUnitEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 单位名称
	 */
	private String unitName;
	/**
	 * 单位编码
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
	private Date createTime;
	/**
	 * 创建人
	 */
	private Long createId;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 更新人
	 */
	private Long updateId;

}
