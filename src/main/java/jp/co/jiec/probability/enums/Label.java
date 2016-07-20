package jp.co.jiec.probability.enums;

import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Bootstrapのlabelクラス定数
 * @author K.Taira
 */
public enum Label {
	DEFAULT,PRIMARY,SUCCESS,INFO,WARNING,DANGER;
	
	/**
	 * 次のラベルを返す。
	 * @return 一つ右のラベル。ただしDANGERの次はDEFAULT
	 */
	public Label getNext(){
		Iterator<Label> iter = Stream.of(Label.values()).iterator();
		while(iter.hasNext()){
			if(this.equals(iter.next())){
				return iter.hasNext() ? iter.next() : Label.DEFAULT;
			}
		}
		return Label.DEFAULT;
	}
	
	public String getString(){
		return "label label-" + this.name().toLowerCase();
	}
}