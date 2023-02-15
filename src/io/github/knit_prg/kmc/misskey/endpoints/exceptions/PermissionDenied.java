package io.github.knit_prg.kmc.misskey.endpoints.exceptions;

import io.github.knit_prg.kmc.Lang;
import org.jetbrains.annotations.NotNull;

/**
 * PERMISSION_DENIED
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public class PermissionDenied extends EndpointsError {
	@Override
	public @NotNull String getCode() {
		return "PERMISSION_DENIED";
	}

	@Override
	public @NotNull String getId() {
		return "1370e5b7-d4eb-4566-bb1d-7748ee6a1838";
	}

	@Override
	public @NotNull String getMessage() {
		return Lang.get("kmc.errors.permission_denied");
	}
}
