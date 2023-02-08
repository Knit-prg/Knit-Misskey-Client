package io.github.knit_prg.kmc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;

import org.apache.commons.lang3.StringUtils;


public final class LangPropertiesMetaData {

	private String name;

	private ZonedDateTime updateTime;

	public LangPropertiesMetaData(InputStream is) {
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

	public String getName() {
		return name;
	}

	public ZonedDateTime getUpdateTime() {
		return updateTime;
	}
}
