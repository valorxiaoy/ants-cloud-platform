package com.ants.base.goods.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品规格表
 * 
 * @author 小米
 * @date 2020-12-26 16:34:50
 */
@Data
@TableName("goods_specs")
public class ProductSpecsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private String goodCode;
	/**
	 * sku属性
	 */
	private String productSpecs;
	/**
	 * 商品主图
	 */
	private String productMasterPicture;
	/**
	 * 商品缩略图
	 */
	private String productThumbnail;
	/**
	 * 商品图集
	 */
	private String productAtlas;
	/**
	 * 是否默认选项
	 */
	private Integer isDefaultOption;
	/**
	 * 商品进价
	 */
	private Integer inprice;
	/**
	 * 零售价
	 */
	private Integer retailPrice;
	/**
	 * 商品状态  正常/淘汰/暂停进货/停售   0/1/2/3
	 */
	private Integer state;
	/**
	 * 毛利率
	 */
	private Double grossMargin;
	/**
	 * 最低限价
	 */
	private Integer lowerPrice;
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
