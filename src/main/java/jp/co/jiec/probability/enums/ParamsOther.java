package jp.co.jiec.probability.enums;

import java.math.BigDecimal;

/**
 * 初期値のサンプル２
 * @author K.Taira
 */
public enum ParamsOther implements Gachable{
	R("90.27"), SR("9.28"), UR("0.45");
	
	private BigDecimal probability;

	private ParamsOther(String s){
		this.probability = new BigDecimal(s);
	}
	
	@Override
	public BigDecimal getProbability(){
		return this.probability;
	}
}