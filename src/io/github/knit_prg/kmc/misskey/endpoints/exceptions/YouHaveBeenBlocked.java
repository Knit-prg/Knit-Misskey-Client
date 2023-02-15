package io.github.knit_prg.kmc.misskey.endpoints.exceptions;

import io.github.knit_prg.kmc.Lang;
import org.jetbrains.annotations.NotNull;

/**
 * YOU_HAVE_BEEN_BLOCKED
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public class YouHaveBeenBlocked extends EndpointsError{

	@Override
	public @NotNull String getCode() {
		return "YOU_HAVE_BEEN_BLOCKED";
	}

	@Override
	public @NotNull String getId() {
		return "b390d7e1-8a5e-46ed-b625-06271cafd3d3";
	}

	@Override
	public @NotNull String getMessage() {
		return Lang.get("kmc.errors.you_have_been_blocked");
	}
}
