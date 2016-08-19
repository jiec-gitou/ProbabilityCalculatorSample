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
		//FIXME:(4)Itemの数だけRangeを作成し、リストthis.rangesに追加して、Rangeのaboveとbelowを連結します。
		//↓の行を消して、同じ処理をループ構文を使って記述します。
		Range last = items.stream().map(Range::new).peek(r -> this.ranges.add(r)).reduce(new Range(), (left, right) -> left.linkToNext(right));
		
		if(last.getAbove().doubleValue() < 100.0d){
			// 100%に不足している場合はダミーを挿入
			Item item = new Item();
			items.add(item);
			item.setKind("不足分ダミー");
			//FIXME:(5)合計が100%に満たない場合はダミーのItemとRangeを作って、100%になるようにします。
		}
	}

	/**
	 * ガチャを回す
	 * @param d 0以上100未満の任意の値
	 * @return 選択された排出要素。どの範囲にも該当しない場合はItem.EMPTYを返す
	 */
	public Item roll(double d){
		//FIXME:(6)引数で与えられたdouble値がどの範囲に含まれているかを探して、その範囲のItemを返します。
		//      引数のdouble値は0〜100の任意値なのでthis.rangesに格納されているRangeのどれか一つに必ず合致します。
		//      double値がRangeで表現される範囲内にあるかどうかはcontain()メソッドを使います。
		//      Rangeに対応するItemはgetItem()メソッドで取得できます。
		return Item.EMPTY;
	}
}