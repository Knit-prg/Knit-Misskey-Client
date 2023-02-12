package io.github.knit_prg.kmc.misskey.endpoints.exceptions;

import io.github.knit_prg.kmc.Lang;
import org.jetbrains.annotations.NotNull;

/**
 * INTERNAL_ERROR
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public class InternalError extends EndpointsError{

	@Override
	public @NotNull String getCode() {
		return "INTERNAL_ERROR";
	}

	@Override
	public @NotNull String getId() {
		return "5d37dbcb-891e-41ca-a3d6-e690c97775ac";
	}

	@Override
	public @NotNull String getMessage() {
		return Lang.get("kmc.errors.internal_error");
	}
}
