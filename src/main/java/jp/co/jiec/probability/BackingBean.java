package jp.co.jiec.probability;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.New;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import jp.co.jiec.probability.enums.Constants;
import jp.co.jiec.probability.enums.Label;

/**
 * ガチャ計算機のバッキングBean
 * @author K.Taira
 */
@Named
@ApplicationScoped
public class BackingBean implements Serializable{
	private static final long serialVersionUID = -2392047587843315898L;

	/** Itemのリスト */
	@Inject
	@New(LinkedList.class)
	private List<Item> items;
	
	/** ガチャ結果のリスト（一回） */
	@Inject
	@New(LinkedList.class)
	private List<Item> resultOne;
	
	/** 出るまで実行した結果のリスト */
	@Inject
	@New(LinkedList.class)
	private List<List<Item>> resultAny;
	
	@Inject
	@New(HashMap.class)
	private Map<Item, Long> resultCount;

	@Inject
	private Gacha gacha;

	/**
	 * 初期化
	 */
	@PostConstruct
	public void init(){
		//FIXME:(1)Constants.INIT_PARAMSを使ってthis.itemsにItemをセットします
		//ヒント：Constants.INIT_PARAMSはEnumの配列で、Itemクラスのコンストラクタは引数にEnumを渡して初期化します
	}

	public List<List<Item>> getResultAny() {
		return resultAny;
	}
	
	public Set<Map.Entry<Item, Long>> getResultCount() {
		return resultCount.entrySet();
	}
	
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	/**
	 * 新しいItemを返す
	 * @return
	 */
	public Item newItem(){
		Item item = new Item();
		this.items.add(item);
		return item;
	}
	
	/**
	 * 削除ボタンのイベントハンドラ
	 * @param item
	 */
	public void delete(Item item){
 		this.items.remove(item);
	}
	
	/**
	 * @return ガチャ結果のリスト
	 */
	public List<Item> getResultOne(){
		return this.resultOne;
	}
	
	/**
	 * 結果の削除
	 */
	public void clear(){
		this.resultOne.clear();
		this.resultAny.clear();
		this.resultCount.clear();
	}
	
	/**
	 * 実行ボタンのイベントハンドラ
	 */
	public void action(){
		clear();
		if(isValid()){
			this.resultOne.addAll(this.rollGacha());
		}else{
			this.actionFailed();
		}
	}
	
	/**
	 * ガチャ実行
	 */
	private List<Item> rollGacha() {
		gacha.setUp(this.items);
		this.colorredItems();
		//FIXME:(3)Constants.MAX_COUNT個の乱数を作ってgacha.roll()を実行し、結果をリストに格納して返します。
		//ヒント：java.lang.Mathクラスのrandomメソッドは0.0d〜1.0dまでのdouble値の乱数を返します。
		//ヒント：ここではjava.util.Listのインスタンスを生成し、それをreturnしてください。
		List<Item> list = new ArrayList<>();
		return list;
	}

	/**
	 * 実行（バリデーション失敗時）
	 */
	private void actionFailed() {
		final FacesContext ctx = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage("確率の合計が１００％を超えています");
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		ctx.addMessage("actionId", message);
	}

	/**
	 * Itemの色付け
	 */
	private void colorredItems() {
		this.items.stream().reduce((left, right) -> {
			Label leftLabel = (left == null) ? Label.DANGER : left.getLabel();
			right.setLabel(leftLabel.getNext());
			return right;
		});
	}
	
	/**
	 * 確率合計のオーバーフロー判定
	 * @return 合計が100%を超えていたらfalse
	 * @see java.math.BigDecimal
	 */
	private boolean isValid() {
		//FIXME:(2)this.itemsのprobabilityの合計が100%を超えていたらfalseを、そうでなければtrueを返します。
		//ヒント：Itemクラスのprobabilityフィールドはgetter/setterを持っており、型はBigDecimalです。
		//      BigDecimal型の足し算にはBigDecimalクラスのadd()メソッドを使います。
		return true;
	}
	
	/**
	 * 最も確率の低いItemを返す
	 * @return
	 */
	public Item getMostRare(){
		//FIXME:(7)this.itemsの中で最もprobabilityが小さいItemを抽出します。
		return Item.EMPTY;
	}
	
	/**
	 * 目的のものが出るまで繰り返す
	 * @param item 目的のItem
	 */
	public void keepRollingFor(Item purpose){
		clear();
		if(!isValid()){
			actionFailed();
		}else if(!purpose.equals(Item.EMPTY)){
			//FIXME:(8)purposeと同じItemが出現するまでrollGachaを繰り返し、結果をthis.resultAnyに格納します。
			
			countResultAny();
		}
	}
	
	/**
	 * Item毎の出現数をカウントして結果を更新する。
	 */
	private void countResultAny(){
		//FIXME:(9)this.resultAnyに含まれるItemの種類毎に出現回数をカウントしてthis.resultCountに格納します。
	}
}