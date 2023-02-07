package io.github.knit_prg.kmc;

import com.fasterxml.jackson.databind.JsonNode;

public final class Settings {

	public static final String DEFAULT = "{" +
			"\"hasLaunched\":false" +
			"}";

	public static JsonNode defaultSettings;

	public static JsonNode settings;
}
