package io.github.knit_prg.kmc.misskey.endpoints.exceptions;

import io.github.knit_prg.kmc.Lang;
import org.jetbrains.annotations.NotNull;

/**
 * CANNOT_REPLY_TO_A_PURE_RENOTE
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public class CannotReplyToAPureRenote extends EndpointsError {
	@Override
	public @NotNull String getCode() {
		return "CANNOT_REPLY_TO_A_PURE_RENOTE";
	}

	@Override
	public @NotNull String getId() {
		return "3ac74a84-8fd5-4bb0-870f-01804f82ce15";
	}

	@Override
	public @NotNull String getMessage() {
		return Lang.get("kmc.errors.cannot_reply_to_a_pure_renote");
	}
}
