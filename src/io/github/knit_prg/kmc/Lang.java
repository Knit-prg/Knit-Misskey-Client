package io.github.knit_prg.kmc;


import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class Lang {
	private static final ResourceBundle lang = createLang();

	private static ResourceBundle createLang() {
		try (URLClassLoader loader = new URLClassLoader(new URL[]{Paths.get("KMC/lang").toFile().toURI().toURL()})) {
			Locale locale = new Locale(Settings.getAsString("lang", "ja-jp"));
			ResourceBundle bundle = ResourceBundle.getBundle("lang", locale, loader);
			return bundle;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String get(String key) {
		try {
			return lang.getString(key);
		} catch (MissingResourceException | NullPointerException e) {
			e.printStackTrace();
			return key;
		}
	}
}
