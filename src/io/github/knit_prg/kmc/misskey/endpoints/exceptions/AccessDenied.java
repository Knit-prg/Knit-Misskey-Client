package io.github.knit_prg.kmc.misskey.endpoints.exceptions;

import io.github.knit_prg.kmc.Lang;
import org.jetbrains.annotations.NotNull;

/**
 * ACCESS_DENIED
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public class AccessDenied extends EndpointsError {

	@Override
	public @NotNull String getCode() {
		return "ACCESS_DENIED";
	}

	@Override
	public @NotNull String getId() {
		return "56f35758-7dd5-468b-8439-5d6fb8ec9b8e";
	}

	@Override
	public @NotNull String getMessage() {
		return Lang.get("kmc.errors.access_denied");
	}
}
