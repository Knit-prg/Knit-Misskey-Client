package io.github.knit_prg.kmc.misskey.endpoints.exceptions;

import io.github.knit_prg.kmc.Lang;
import org.jetbrains.annotations.NotNull;

/**
 * NO_SUCH_RENOTE_TARGET
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public class NoSuchRenoteTarget extends EndpointsError {
	@Override
	public @NotNull String getCode() {
		return "NO_SUCH_RENOTE_TARGET";
	}

	@Override
	public @NotNull String getId() {
		return "b5c90186-4ab0-49c8-9bba-a1f76c282ba4";
	}

	@Override
	public @NotNull String getMessage() {
		return Lang.get("kmc.errors.no_such_renote_target");
	}
}
