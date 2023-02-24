package io.github.knit_prg.kmc.misskey.guiComponents;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.FlowLayout;

/**
 * MFMを表示する(予定)
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public class MfmViewer extends JPanel {

	/**
	 * つくるやつ
	 *
	 * @param mfm MFM
	 */
	public MfmViewer(String mfm) {
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		setAlignmentX(Component.LEFT_ALIGNMENT);
		for (int i = 0; i < mfm.length(); i++) {
			add(new JLabel(String.valueOf(mfm.charAt(i))));
		}
	}
}
