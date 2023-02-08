package io.github.knit_prg.kmc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public final class Settings {

	public static final ObjectNode settings = createSettings();

	private static ObjectNode createSettings() {
		try {
			return (ObjectNode) new ObjectMapper().readTree(new File("KMC/settings.json"));
		} catch (IOException e) {
			e.printStackTrace();
			return new ObjectNode(new JsonNodeFactory(true));
		}
	}

	public static JsonNode get(String path) {
		String[] pathSplit = path.split("\\.");
		JsonNode searching = null;
		for (String pathPart : pathSplit) {
			JsonNode temp;
			if (searching == null) {
				temp = settings.get(pathPart);
			} else {
				temp = searching.get(pathPart);
			}
			if (temp == null) {
				return null;
			}
			searching = temp;
		}
		return searching;
	}

	@SuppressWarnings("unused")
	public static boolean getAsBoolean(String path) {
		return getAsBoolean(path, false);
	}

	public static boolean getAsBoolean(String path, boolean defaultValue) {
		JsonNode get = get(path);
		if (get == null) {
			return defaultValue;
		} else {
			return get.asBoolean();
		}
	}

	@SuppressWarnings("unused")
	public static String getAsString(String path) {
		return getAsString(path, null);
	}

	public static String getAsString(String path, String defaultValue) {
		JsonNode get = get(path);
		if (get == null) {
			return defaultValue;
		} else {
			return get.asText();
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
