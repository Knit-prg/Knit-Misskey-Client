package io.github.knit_prg.kmc.misskey.endpoints.exceptions;

import org.jetbrains.annotations.NotNull;

/**
 * エラーを表す
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public abstract class EndpointsError extends Exception {

	/**
	 * エラーコード
	 *
	 * @return エラーコード
	 * @since 0.1.0
	 */
	public abstract @NotNull String getCode();

	/**
	 * エラーのUUID
	 *
	 * @return UUID
	 * @since 0.1.0
	 */
	public abstract @NotNull String getId();

	/**
	 * エラー状況を示すメッセージ
	 *
	 * @return メッセージ
	 * @since 0.1.0
	 */
	public abstract @NotNull String getMessage();
}
