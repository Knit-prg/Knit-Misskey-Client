package io.github.knit_prg.kmc.misskey.endpoints.exceptions;

import io.github.knit_prg.kmc.Lang;
import org.jetbrains.annotations.NotNull;

/**
 * YOUR_ACCOUNT_SUSPENDED
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public class YourAccountSuspended extends EndpointsError {
	@Override
	public @NotNull String getCode() {
		return "YOUR_ACCOUNT_SUSPENDED";
	}

	@Override
	public @NotNull String getId() {
		return "a8c724b3-6e9c-4b46-b1a8-bc3ed6258370";
	}

	@Override
	public @NotNull String getMessage() {
		return Lang.get("kmc.errors.your_account_suspended");
	}
}
