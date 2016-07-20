package jp.co.jiec.probability;

import java.math.BigDecimal;

/**
 * 確率範囲オブジェクト
 * @author K.Taira
 */
public class Range {
	/** 上限 */
	private BigDecimal below = BigDecimal.ZERO;
	
	/** 下限 */
	private BigDecimal above = BigDecimal.ZERO;
	
	/** この範囲内にある排出種別 */
	private Item item;
	
	/**
	 * デフォルトコンストラクタ
	 */
	public Range(){
		this(null);
	}
	
	/**
	 * コンストラクタ
	 * @param item
	 */
	public Range(Item item) {
		this.item = item;
	}
	
	/**
	 * 次の確率範囲と接続
	 * @param other 一つ上の確率範囲
	 * @return otherを返す
	 */
	public Range linkToNext(Range other) {
		other.below = this.above;
		other.above = this.above.add(other.getItem().getProbability());
		return other;
	}
	public BigDecimal getBelow(){
		return this.below;
	}
	public BigDecimal getAbove(){
		return this.above;
	}
	public void setBelow(BigDecimal bd){
		this.below = bd;
	}
	public void setAbove(BigDecimal bd){
		this.above = bd;
	}
	public Item getItem(){
		return this.item;
	}
	
	/**
	 * 値が上限と下限の間に入っているかどうか
	 * @param d 確率（乱数）
	 * @return この範囲に含まれているならtrue
	 */
	public boolean contain(double d){
		return below.doubleValue() <= d && d < above.doubleValue();
	}
	
	@Override
	public String toString(){
		return String.format("%3.2f〜%3.2f", below.doubleValue(), above.doubleValue());
	}
}