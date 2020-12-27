package com.ants.base.goods.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品sku键表
 * 
 * @author 小米
 * @date 2020-12-26 16:34:51
 */
@Data
@TableName("goods_sku_keys")
public class ProductSkuKeysEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 键编码
	 */
	private String skuKeyCode;
	/**
	 * 商品品类编码
	 */
	private Long categoryCode;
	/**
	 * 属性名称
	 */
	private String attributeName;
	/**
	 * 名称排序
	 */
	private Integer nameSort;
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
