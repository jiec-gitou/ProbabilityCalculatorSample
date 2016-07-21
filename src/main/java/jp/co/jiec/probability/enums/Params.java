package jp.co.jiec.probability.enums;

import java.math.BigDecimal;

/**
 * 初期値のサンプル１
 * @author K.Taira
 */
public enum Params implements Gachable{
	R("88.5"),SR("10.0"),SSR("1.5");
	
	private BigDecimal probability;

	private Params(String s){
		this.probability = new BigDecimal(s);
	}
	
	@Override
	public BigDecimal getProbability(){
		return this.probability;
	}
}