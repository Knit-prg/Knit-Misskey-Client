package io.github.knit_prg.kmc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.ZonedDateTime;
import java.util.Properties;

public final class Main {

	public static void main(String[] args) {
		extractResource();
		System.out.println(Lang.get("kmc.test"));
		GuiStore.init();
	}

	private static void extractResource() {
		try {
			Properties resourcesList = new Properties();
			resourcesList.load(ClassLoader.getSystemResourceAsStream("resourcesList.properties"));
			resourcesList.keySet().forEach(key -> {
				String value = resourcesList.getProperty(key.toString());
				Path destination = Paths.get(value + "/" + key);
				boolean exists = Files.exists(destination);
				boolean willExtract = !exists;
				if (exists && value.equals("KMC/lang")) {
					try {
						InputStream inJar = ClassLoader.getSystemResourceAsStream(key.toString());
						if (inJar == null) {
							System.out.println("No resource: " + key);
							return;
						}
						LangPropertiesMetaData inJarProperties = new LangPropertiesMetaData(inJar);
						InputStream inLang = new FileInputStream("KMC/lang/" + key);
						LangPropertiesMetaData inLangProperties = new LangPropertiesMetaData(inLang);
						ZonedDateTime inJarUpdateTime = inJarProperties.getUpdateTime();
						ZonedDateTime inLangUpdateTime = inLangProperties.getUpdateTime();
						if (inJarUpdateTime != null && inLangUpdateTime != null && inJarUpdateTime.isAfter(inLangUpdateTime)) {
							willExtract = true;
						}
					} catch (FileNotFoundException e) {
						System.out.println("No resource: " + key);
					}
				}
				if (!willExtract) {
					return;
				}
				try {
					Path pathToSave = Paths.get(value);
					Files.createDirectories(pathToSave);
					InputStream resource = ClassLoader.getSystemResourceAsStream(key.toString());
					if (resource == null) {
						System.out.println("The resource file could not be found: " + key);
						return;
					}
					Files.copy(resource, destination, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
