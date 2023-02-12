package io.github.knit_prg.kmc.misskey.endpoints.exceptions;

import io.github.knit_prg.kmc.Lang;
import org.jetbrains.annotations.NotNull;

/**
 * CANNOT_CREATE_ALREADY_EXPIRED_POLL
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public class CannotCreateAlreadyExpiredPoll extends EndpointsError {
	@Override
	public @NotNull String getCode() {
		return "CANNOT_CREATE_ALREADY_EXPIRED_POLL";
	}

	@Override
	public @NotNull String getId() {
		return "04da457d-b083-4055-9082-955525eda5a5";
	}

	@Override
	public @NotNull String getMessage() {
		return Lang.get("kmc.errors.cannot_create_already_expired_poll");
	}
}
