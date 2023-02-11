package io.github.knit_prg.kmc.misskey;

import io.github.knit_prg.kmc.Gui;

import java.awt.Container;

/**
 * Misskeyのタイムライン画面を示す
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public final class MisskeyTimeline {

	/**
	 * 画面を開く
	 *
	 * @since 0.1.0
	 */
	public static void open() {
		Container contentPane = Gui.mainFrame.getContentPane();
		contentPane.removeAll();
		contentPane.setVisible(false);
		contentPane.setVisible(true);
		contentPane.repaint();
	}
}
