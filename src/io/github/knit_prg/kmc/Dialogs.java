package io.github.knit_prg.kmc;

import javax.swing.JOptionPane;

/**
 * ダイアログ系をまとめとく
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public final class Dialogs {

	/**
	 * エラー用
	 *
	 * @param msg 表示するやつ
	 * @since 0.1.0
	 */
	public static void errorMsg(String msg) {
		JOptionPane.showMessageDialog(Gui.mainFrame, msg);
	}
}
