package io.github.knit_prg.kmc.misskey.endpoints.exceptions;

import org.jetbrains.annotations.NotNull;

/**
 * CANNOT_RENOTE_TO_A_PURE_RENOTE
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public class CannotRenoteToAPureRenote extends EndpointsError {
	@Override
	public @NotNull String getCode() {
		return "CANNOT_RENOTE_TO_A_PURE_RENOTE";
	}

	@Override
	public @NotNull String getId() {
		return "fd4cc33e-2a37-48dd-99cc-9b806eb2031a";
	}

	@Override
	public @NotNull String getMessage() {
		return "CANNOT_RENOTE_TO_A_PURE_RENOTE";
	}
}
