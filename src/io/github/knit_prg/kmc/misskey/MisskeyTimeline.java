package io.github.knit_prg.kmc.misskey;

import io.github.knit_prg.kmc.Gui;
import io.github.knit_prg.kmc.Settings;
import io.github.knit_prg.kmc.misskey.guiComponents.NoteCreator;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

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
		contentPane.setLayout(new BorderLayout());
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		contentPane.add(centerPanel, BorderLayout.CENTER);
		centerPanel.add(new NoteCreator());
		centerPanel.add(Box.createHorizontalGlue());
		Streaming streaming = new Streaming(Settings.getInstance().getTokens().get(0), Streaming.Channel.LOCAL_TIME_LINE);
		streaming.connect();
		Gui.mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				streaming.disconnect();
			}
		});
		contentPane.setVisible(false);
		contentPane.setVisible(true);
		contentPane.repaint();
	}
}
