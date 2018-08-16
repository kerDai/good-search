package com.search.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

public class ProductEs implements Serializable {

	private static final long serialVersionUID = 6419559178018665490L;

	public static final String INDEX_NAME = "es_product";

	public static final String TYPE = "product";

	/**
	 * 商品ID
	 */
	private Long goodsId;
	
	/**
	 * 商品编码
	 */
	private String goodsCode;
	
	/**
	 * 商品名称
	 */
	private String goodsName;

	/**
	 * 商品简称
	 */
	private String subtitle;

	/**
	 * 促销标题
	 */
	private String promotionTitle;
	
	/**
	 * 商品主图
	 */
	private String mainImage;

	/**
	 * 商品原价
	 */
	private BigDecimal originPrice;

	/**
	 * 商品实际售价
	 */
	private BigDecimal salesPrice;
	
	/**
	 * 当前商品的规格
	 */
	private String spec;
	
	/**
	 * 月销量
	 */
	private Integer monthlySales;
	
	/**
	 * 好评率
	 */
	private BigDecimal praiseRate;

	/**
	 * 标签
	 */
	private String[] label;

	/**
	 * 推荐指数
	 */
	private Integer recommendationIndex;

	/**
	 * 最低价
	 */
	private BigDecimal minPrice;

	/**
	 * 最高价，等于商品原价
	 */
	private BigDecimal maxPrice;

	/**
	 * 商品状态 1-在售，2-下架
	 */
	private Integer status;


	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public BigDecimal getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(BigDecimal originPrice) {
		this.originPrice = originPrice;
	}

	public BigDecimal getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Integer getMonthlySales() {
		return monthlySales;
	}

	public void setMonthlySales(Integer monthlySales) {
		this.monthlySales = monthlySales;
	}

	public BigDecimal getPraiseRate() {
		return praiseRate;
	}

	public void setPraiseRate(BigDecimal praiseRate) {
		this.praiseRate = praiseRate;
	}

	public String[] getLabel() {
		return label;
	}

	public void setLabel(String[] label) {
		this.label = label;
	}

	public Integer getRecommendationIndex() {
		return recommendationIndex;
	}

	public void setRecommendationIndex(Integer recommendationIndex) {
		this.recommendationIndex = recommendationIndex;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getPromotionTitle() {
		return promotionTitle;
	}

	public void setPromotionTitle(String promotionTitle) {
		this.promotionTitle = promotionTitle;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public ProductEs() {
	}

	public ProductEs(Long goodsId, String goodsCode, String goodsName, String subtitle, String promotionTitle, String mainImage, BigDecimal originPrice,
					 BigDecimal salesPrice, String spec, Integer monthlySales, BigDecimal praiseRate, String[] label, Integer recommendationIndex,
					 BigDecimal minPrice, BigDecimal maxPrice, Integer status) {
		this.goodsId = goodsId;
		this.goodsCode = goodsCode;
		this.goodsName = goodsName;
		this.subtitle = subtitle;
		this.promotionTitle = promotionTitle;
		this.mainImage = mainImage;
		this.originPrice = originPrice;
		this.salesPrice = salesPrice;
		this.spec = spec;
		this.monthlySales = monthlySales;
		this.praiseRate = praiseRate;
		this.label = label;
		this.recommendationIndex = recommendationIndex;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.status = status;
	}

	@Override
	public String toString() {
		return "ProductEs{" +
				"goodsId=" + goodsId +
				", goodsCode='" + goodsCode + '\'' +
				", goodsName='" + goodsName + '\'' +
				", subtitle='" + subtitle + '\'' +
				", promotionTitle='" + promotionTitle + '\'' +
				", mainImage='" + mainImage + '\'' +
				", originPrice=" + originPrice +
				", salesPrice=" + salesPrice +
				", spec='" + spec + '\'' +
				", monthlySales=" + monthlySales +
				", praiseRate=" + praiseRate +
				", label=" + Arrays.toString(label) +
				", recommendationIndex=" + recommendationIndex +
				", minPrice=" + minPrice +
				", maxPrice=" + maxPrice +
				", status=" + status +
				'}';
	}
}
