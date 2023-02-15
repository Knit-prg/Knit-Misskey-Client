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

	/**
	 * 共通エラーを解釈します。
	 *
	 * @return エラー
	 * @since 0.1.0
	 */
	public static @NotNull EndpointsError commonErrors(String code) {
		return switch (code) {
			case "ACCESS_DENIED" -> new AccessDenied();
			case "CREDENTIAL_REQUIRED" -> new CredentialRequired();
			case "INVALID_PARAM" -> new InvalidParam();
			case "PERMISSION_DENIED" -> new PermissionDenied();
			case "RATE_LIMIT_EXCEEDED" -> new RateLimitExceeded();
			case "YOUR_ACCOUNT_SUSPENDED" -> new YourAccountSuspended();
			default -> new InternalError();
		};
	}
}
