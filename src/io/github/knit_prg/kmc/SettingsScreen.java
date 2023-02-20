package io.github.knit_prg.kmc;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * 設定画面
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public final class SettingsScreen {

	/**
	 * 行の高さを選ぶやつ
	 *
	 * @since 0.1.0
	 */
	private final JSpinner lineHeightSpinner = new JSpinner(new SpinnerNumberModel(Settings.getInstance().getLineHeight(), 0, null, 1)) {
		{
			setMaximumSize(new Dimension(getMaximumSize().width, Settings.getInstance().getLineHeight()));
		}
	};

	/**
	 * 設定画面本体
	 *
	 * @since 0.1.0
	 */
	private final JDialog dialog = new JDialog(Gui.mainFrame) {
		{
			setSize(700, 500);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			setTitle(Lang.get("kmc.settings"));
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					onClose();
				}
			});
		}
	};

	/**
	 * 設定画面をつくる
	 *
	 * @since 0.1.0
	 */
	public SettingsScreen() {
		Container contentPane = dialog.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(new JPanel() {
			{
				setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
				add(new JLabel(Lang.get("kmc.settings.line_height")));
				add(lineHeightSpinner);
			}
		});
	}

	/**
	 * 閉じた時の処理
	 *
	 * @since 0.1.0
	 */
	private void onClose() {
		Settings.getInstance().setLineHeight((Integer) lineHeightSpinner.getValue());
		dialog.dispose();
	}

	/**
	 * 設定画面を開く
	 *
	 * @since 0.1.0
	 */
	public void open() {
		dialog.setVisible(true);
	}
}
