package io.github.knit_prg.kmc;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Main {

	public static void main(String[] args) {
		try {
			Path kmcDirectory = Paths.get("KMC");
			if (!Files.exists(kmcDirectory)) {
				Files.createDirectory(kmcDirectory);
			}
			Settings.defaultSettings = new ObjectMapper().readTree(Settings.DEFAULT);
			if (Files.exists(Paths.get("KMC/settings.json"))) {
				Settings.settings = new ObjectMapper().readTree(new File("KMC/settings.json"));
			} else {
				Settings.settings = Settings.defaultSettings;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		GuiStore.init();
	}
}
