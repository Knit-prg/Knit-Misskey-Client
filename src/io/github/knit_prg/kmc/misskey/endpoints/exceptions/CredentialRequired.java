package io.github.knit_prg.kmc.misskey.endpoints.exceptions;

import io.github.knit_prg.kmc.Lang;
import org.jetbrains.annotations.NotNull;

/**
 * CREDENTIAL_REQUIRED
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public class CredentialRequired extends EndpointsError {
	@Override
	public @NotNull String getCode() {
		return "CREDENTIAL_REQUIRED";
	}

	@Override
	public @NotNull String getId() {
		return "1384574d-a912-4b81-8601-c7b1c4085df1";
	}

	@Override
	public @NotNull String getMessage() {
		return Lang.get("kmc.errors.credential_required");
	}
}
