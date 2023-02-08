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
		try {
			return settings.get(path);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unused")
	public static boolean getAsBoolean(String path) {
		try {
			return settings.get(path).asBoolean();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unused")
	public static boolean getAsBoolean(String path, boolean defaultValue) {
		try {
			return settings.get(path).asBoolean(defaultValue);
		} catch (Exception e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	@SuppressWarnings("unused")
	public static String getAsString(String path) {
		try {
			return settings.get(path).asText();
		} catch (Exception e) {
			e.printStackTrace();
			return path;
		}
	}

	public static String getAsString(String path, String defaultValue) {
		try {
			return settings.get(path).asText(defaultValue);
		} catch (Exception e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	@SuppressWarnings("unused")
	public static void put(String path, String value) {
		try {
			settings.put(path, value);
		} catch (Exception e) {
			e.printStackTrace();
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
