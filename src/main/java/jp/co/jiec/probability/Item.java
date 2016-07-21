package jp.co.jiec.probability;

import java.math.BigDecimal;

import javax.enterprise.context.Dependent;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import jp.co.jiec.probability.enums.Constants;
import jp.co.jiec.probability.enums.Gachable;
import jp.co.jiec.probability.enums.Label;

/**
 * ガチャ排出種別
 * @author K.Taira
 */
@Dependent
public class Item{
	/** 種別名称 */
	@NotNull
	private String kind;
	
	/** 排出率（百分率） */
	@NotNull 
	@DecimalMin("0.00")
	@DecimalMax("100.00")
	private BigDecimal probability;
	
	/** Bootstrapのラベル */
	private Label label = Label.DEFAULT;
	
	public static final Item EMPTY = new Item();

	public Item(){
		this.setKind(" ");
		this.setProbability(BigDecimal.ZERO);
	}
	
	public Item(Enum<? extends Gachable> t){
		this.setKind(t.name());
		this.setProbability(((Gachable) t).getProbability()); 
	}
	
	public String getKind() {
		return kind;
	}
	
	public void setKind(String kind) {
		this.kind = kind;
	}
	
	public BigDecimal getProbability() {
		if(probability == null){
			this.setProbability(BigDecimal.ZERO);
		}
		return this.probability;
	}
	
	public void setProbability(BigDecimal probability) {
		probability.setScale(Constants.SCALE, BigDecimal.ROUND_HALF_UP);
		this.probability = probability;
	}

	public Label getLabel(){
		return this.label;
	}
	
	public void setLabel(Label label) {
		this.label = label;
	}
	
	@Override
	public String toString(){
		return String.format("%s=%s", this.kind, this.getProbability().toString());
	}
}