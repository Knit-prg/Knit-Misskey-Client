package io.github.knit_prg.kmc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public final class Settings {

	private static final JsonNode settings = createSettings();

	private static JsonNode createSettings() {
		try {
			return new ObjectMapper().readTree(new File("KMC/settings.json"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getAsString(String path) {
		try {
			return settings.get(path).asText();
		} catch (NullPointerException e) {
			e.printStackTrace();
			return path;
		}
	}

	public static String getAsString(String path, String defaultValue) {
		try {
			return settings.get(path).asText();
		} catch (NullPointerException e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	public static String toPrettyString() {
		try {
			return settings.toPrettyString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

}
