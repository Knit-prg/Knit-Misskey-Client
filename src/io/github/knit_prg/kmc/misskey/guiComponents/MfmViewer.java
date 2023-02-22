package io.github.knit_prg.kmc.misskey.guiComponents;

import javax.swing.JLabel;
import javax.swing.JPanel;

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
		add(new JLabel(mfm));
	}
}
