package io.github.knit_prg.kmc.misskey.endpoints.exceptions;

import io.github.knit_prg.kmc.Lang;
import org.jetbrains.annotations.NotNull;

/**
 * NO_SUCH_CHANNEL
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public class NoSuchChannel extends EndpointsError {
	@Override
	public @NotNull String getCode() {
		return "NO_SUCH_CHANNEL";
	}

	@Override
	public @NotNull String getId() {
		return "b1653923-5453-4edc-b786-7c4f39bb0bbb";
	}

	@Override
	public @NotNull String getMessage() {
		return Lang.get("kmc.errors.no_such_channel");
	}
}
