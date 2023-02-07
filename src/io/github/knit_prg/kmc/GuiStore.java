package io.github.knit_prg.kmc;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.JFrame;
import javax.swing.UIManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public final class GuiStore {

	public static JFrame mainFrame = new JFrame();

	public static void init() {
		FlatDarkLaf.setup();
		UIManager.put("TitlePane.menuBarEmbedded", false);
		UIManager.put("TitlePane.unifiedBackground", false);
		mainFrame.setSize(800, 500);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setTitle("Knit Misskey Client v0.1.0");
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try (BufferedWriter writer = new BufferedWriter(new FileWriter("KMC/settings.json"))) {
					writer.write(Settings.settings.toPrettyString());
				} catch (IOException excp) {
					excp.printStackTrace();
					System.exit(1);
				}
				System.exit(0);
			}
		});
		mainFrame.setVisible(true);
	}
}
