package com.ants.base.goods.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品sku值表
 * 
 * @author 小米
 * @date 2020-12-26 16:34:50
 */
@Data
@TableName("goods_sku_value")
public class GoodsSkuValueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * sku key编码
	 */
	private String skuKeyCode;
	/**
	 * 值编码
	 */
	private String skuValueCode;
	/**
	 * 属性值
	 */
	private String attributeValue;
	/**
	 * 值排序
	 */
	private Integer valueSort;
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
