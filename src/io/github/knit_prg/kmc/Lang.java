package io.github.knit_prg.kmc;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 言語リソースを管理する
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public final class Lang {

	/**
	 * 言語リソース本体
	 *
	 * @since 0.1.0
	 */
	private static final @Nullable ResourceBundle lang = createLang();

	/**
	 * 言語リソースを読み込む
	 *
	 * @return リソース
	 * @since 0.1.0
	 */
	private static @Nullable ResourceBundle createLang() {
		try (URLClassLoader loader = new URLClassLoader(new URL[]{Paths.get("KMC/lang").toFile().toURI().toURL()})) {
			Locale locale = new Locale(Settings.getInstance().getLang());
			return ResourceBundle.getBundle("lang", locale, loader);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * キーから言語リソースの値を取得する
	 *
	 * @param key キー
	 * @return 値、見つからなければキー
	 */
	public static @NotNull String get(@NotNull String key) {
		try {
			return lang.getString(key);
		} catch (MissingResourceException | NullPointerException e) {
			e.printStackTrace();
			return key;
		}
	}
}
