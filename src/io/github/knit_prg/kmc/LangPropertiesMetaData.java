package io.github.knit_prg.kmc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 言語ファイルのメタデータを表す
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public final class LangPropertiesMetaData {

	/**
	 * 言語名
	 *
	 * @since 0.1.0
	 */
	private @Nullable String name;

	/**
	 * 言語ファイルが更新された日時
	 *
	 * @since 0.1.0
	 */
	private @Nullable ZonedDateTime updateTime;

	/**
	 * メタデータを読み込む
	 *
	 * @param is InputStream
	 * @since 0.1.0
	 */
	public LangPropertiesMetaData(@NotNull InputStream is) {
		String text;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
			while ((text = br.readLine()) != null) {
				if (text.startsWith("#name=")) {
					name = StringUtils.stripStart(text, "#name=");
				} else if (text.startsWith("#updateTime=")) {
					updateTime = ZonedDateTime.parse(StringUtils.stripStart(text, "#updateTime="));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 言語名を取得する
	 *
	 * @return 言語名
	 * @since 0.1.0
	 */
	public @Nullable String getName() {
		return name;
	}

	/**
	 * 更新時刻を取得する
	 *
	 * @return 更新時刻
	 * @since 0.1.0
	 */
	public @Nullable ZonedDateTime getUpdateTime() {
		return updateTime;
	}
}
