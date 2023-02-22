package io.github.knit_prg.kmc.misskey;

import io.github.knit_prg.kmc.Gui;
import io.github.knit_prg.kmc.misskey.guiComponents.MisskeyTimeline;
import io.github.knit_prg.kmc.misskey.guiComponents.MisskeyNoteCreator;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Misskeyのホーム画面を示す
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public final class MisskeyHomeScreen {

	/**
	 * 画面を開く
	 *
	 * @since 0.1.0
	 */
	public static void open() {
		Container contentPane = Gui.mainFrame.getContentPane();
		contentPane.removeAll();
		contentPane.setLayout(new BorderLayout());
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		contentPane.add(centerPanel, BorderLayout.CENTER);
		centerPanel.add(new MisskeyNoteCreator());
		centerPanel.add(new MisskeyTimeline());
		centerPanel.add(Box.createHorizontalGlue());
		contentPane.setVisible(false);
		contentPane.setVisible(true);
		contentPane.repaint();
	}
}
