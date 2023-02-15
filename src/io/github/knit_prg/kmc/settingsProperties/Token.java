package io.github.knit_prg.kmc.settingsProperties;

/**
 * トークンの保存データを示す。
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public final class Token {

	/**
	 * トークン本体
	 *
	 * @since 0.1.0
	 */
	public String token = "";

	/**
	 * ログイン型、misskey-miauthしかないはず
	 *
	 * @since 0.1.0
	 */
	public String type = "";

	/**
	 * ユーザー名
	 *
	 * @since 0.1.0
	 */
	public String user = "";
}
