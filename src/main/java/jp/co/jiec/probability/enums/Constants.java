package jp.co.jiec.probability.enums;

/**
 * 定数定義
 * @author K.Taira
 */
public interface Constants {
	/** 一度の試行で実行する数 */
	int MAX_COUNT = 10;
	
	/** 小数点以下有効桁数 */
	int SCALE = 2;
	
	/** 初期値 */
	Enum<? extends Gachable>[] INIT_PARAMS = Params.values();
}