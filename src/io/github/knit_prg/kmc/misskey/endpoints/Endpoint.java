package io.github.knit_prg.kmc.misskey.endpoints;

import io.github.knit_prg.kmc.settingsProperties.Token;
import org.jetbrains.annotations.NotNull;

/**
 * エンドポイントを示す
 *
 * @since 0.1.0
 * @author Knit prg.
 */
public abstract class Endpoint {

	/**
	 * レスポンスを受け取る
	 * @param request
	 * @return
	 */
	public abstract @NotNull EndpointResponse get(@NotNull Token token, @NotNull EndpointRequest request);
}
