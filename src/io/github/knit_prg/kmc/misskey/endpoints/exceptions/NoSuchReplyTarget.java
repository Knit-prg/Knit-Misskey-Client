package io.github.knit_prg.kmc.misskey.endpoints.exceptions;

import io.github.knit_prg.kmc.Lang;
import org.jetbrains.annotations.NotNull;

/**
 * NO_SUCH_REPLY_TARGET
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public class NoSuchReplyTarget extends EndpointsError{
	@Override
	public @NotNull String getCode() {
		return "NO_SUCH_REPLY_TARGET";
	}

	@Override
	public @NotNull String getId() {
		return "749ee0f6-d3da-459a-bf02-282e2da4292c";
	}

	@Override
	public @NotNull String getMessage() {
		return Lang.get("kmc.errors.no_such_reply_target");
	}
}
