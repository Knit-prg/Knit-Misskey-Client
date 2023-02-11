package io.github.knit_prg.kmc;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;

/**
 * GUIを管理する。
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public final class Gui {

	/**
	 * ウインドウ
	 *
	 * @since 0.1.0
	 */
	public static JFrame mainFrame = new JFrame();

	/**
	 * 初期化
	 *
	 * @since 0.1.0
	 */
	public static void init() {
		mainFrame.setSize(800, 500);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setTitle("Knit Misskey Client v0.1.0");
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try (BufferedWriter writer = new BufferedWriter(new FileWriter("KMC/settings.json"))) {
					writer.write(Settings.getInstance().toJson());
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
