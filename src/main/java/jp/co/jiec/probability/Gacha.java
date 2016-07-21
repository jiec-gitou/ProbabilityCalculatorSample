package jp.co.jiec.probability;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.New;
import javax.inject.Inject;

/**
 * ガチャ計算機
 * @author K.Taira
 */
@RequestScoped
public class Gacha {

	/** すべての確率範囲 */
	@Inject
	@New(LinkedList.class)
	private List<Range> ranges;
	
	@PostConstruct
	public void init(){
	}
	
	/**
	 * すべての排出要素によってガチャを初期化
	 * @param items 入力された排出要素のリスト
	 */
	public void setUp(Collection<Item> items){
		ranges.clear();
		//FIXME:(4)Itemの数だけRangeを作成し、Rangeのaboveとbelowを連結します。
		Range last = null;
		
		if(last.getAbove().doubleValue() < 100.0d){
			//FIXME:(5)合計が100%に満たない場合はダミーのItemを作成して合計が100%になるようにします。
			Item item = new Item();
			item.setKind("不足分ダミー");
		}
	}

	/**
	 * ガチャを回す
	 * @param d 0以上100未満の任意の値
	 * @return 選択された排出要素
	 */
	public Item roll(double d){
		//FIXME:(6)与えられたdouble値がどの範囲に含まれているかを探して、その範囲のItemを返します。
		return null;
	}
}