package io.github.knit_prg.kmc.misskey.endpoints.exceptions;

import io.github.knit_prg.kmc.Lang;
import org.jetbrains.annotations.NotNull;

/**
 * INVALID_PARAM
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public class InvalidParam extends EndpointsError {

	@Override
	public @NotNull String getCode() {
		return "INVALID_PARAM";
	}

	@Override
	public @NotNull String getId() {
		return "3d81ceae-475f-4600-b2a8-2bc116157532";
	}

	@Override
	public @NotNull String getMessage() {
		return Lang.get("kmc.errors.invalid_param");
	}
}
