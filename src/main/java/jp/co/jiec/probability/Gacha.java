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
		Range last = items.stream()
				.map(Range::new)
				.peek(ranges::add)
				.reduce(new Range(), (left, right) -> left.linkToNext(right));
		
		if(last.getAbove().doubleValue() < 100.0d){
			// 100%に不足している場合はダミーを挿入
			Item item = new Item();
			item.setKind("不足分ダミー");
			item.setProbability(new BigDecimal(100).subtract(last.getAbove()));
			items.add(item);
			ranges.add(last.linkToNext(new Range(item)));
		}
	}

	/**
	 * ガチャを回す
	 * @param d 0以上100未満の任意の値
	 * @return 選択された排出要素
	 */
	public Item roll(double d){
		return ranges.stream()
				.filter(range -> range.contain(d))
				.map(Range::getItem)
				.findFirst()
				.orElse(Item.EMPTY);
	}
}