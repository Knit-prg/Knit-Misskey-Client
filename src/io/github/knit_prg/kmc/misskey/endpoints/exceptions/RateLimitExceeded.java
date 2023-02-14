package io.github.knit_prg.kmc.misskey.endpoints.exceptions;

import io.github.knit_prg.kmc.Lang;
import org.jetbrains.annotations.NotNull;

/**
 * RATE_LIMIT_EXCEEDED
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public class RateLimitExceeded extends EndpointsError {

	@Override
	public @NotNull String getCode() {
		return "RATE_LIMIT_EXCEEDED";
	}

	@Override
	public @NotNull String getId() {
		return "d5826d14-3982-4d2e-8011-b9e9f02499ef";
	}

	@Override
	public @NotNull String getMessage() {
		return Lang.get("kmc.errors.rate_limit_exceeded");
	}
}
