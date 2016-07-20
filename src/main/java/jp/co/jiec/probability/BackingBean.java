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
	
	/** ガチャ結果のリスト */
	@Inject
	@New(LinkedList.class)
	private List<Item> results;
	
	@Inject
	private Gacha gacha;

	/**
	 * 初期化
	 */
	@PostConstruct
	public void init(){
		Arrays.stream(Constants.INIT_PARAMS)
			.map(Item::new)
			.collect(Collectors.toCollection(() -> this.items));
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
	public List<Item> getResults(){
		return this.results;
	}
	
	/**
	 * 実行ボタンのイベントハンドラ
	 */
	public String action(){
		this.results.clear();
		if(isValid()){
			this.actionSucceeded();
		}else{
			this.actionFailed();
		}
		return "./index.xhtml";
	}
	
	/**
	 * ガチャ実行
	 */
	private void actionSucceeded() {
		gacha.setUp(this.items);
		this.colorredItems();
		Stream.generate(() -> Math.random() * 100.0d)
			.limit(Constants.MAX_COUNT)
			.map(d -> gacha.roll(d))
			.collect(Collectors.toCollection(() -> this.results));
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
	 */
	private boolean isValid() {
		BigDecimal summary = this.items.stream()
				.map(Item::getProbability)
				.reduce(BigDecimal.ZERO, (left, right) -> left.add(right));
		int val = summary.multiply(new BigDecimal(Math.pow(10, Constants.SCALE))).intValue();
		return val >= 0 && val <= 100 * Math.pow(10, Constants.SCALE);
	}
}