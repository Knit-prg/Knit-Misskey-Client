package io.github.knit_prg.kmc.misskey;

import io.github.knit_prg.kmc.Dialogs;
import io.github.knit_prg.kmc.Gui;
import io.github.knit_prg.kmc.Lang;
import io.github.knit_prg.kmc.Settings;
import io.github.knit_prg.kmc.misskey.endpoints.notes.Create;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Misskeyのタイムライン画面を示す
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public final class MisskeyTimeline {

	/**
	 * cwにするかの選択部
	 *
	 * @since 0.1.0
	 */
	private static JCheckBox cwCheckBox;

	/**
	 * cw時の説明
	 *
	 * @since 0.1.0
	 */
	private static JTextField cwText;

	/**
	 * 投稿部
	 *
	 * @since 0.1.0
	 */
	private static JTextArea postContent;

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
		cwCheckBox = new JCheckBox() {
			{
				setText(Lang.get("kmc.notes.create.is_cw"));
				addChangeListener(e -> cwText.setEnabled(cwCheckBox.isSelected()));
			}
		};
		cwText = new JTextField() {
			{
				setMaximumSize(new Dimension(getMaximumSize().width, cwCheckBox.getFont().getSize() * 2));
				setEnabled(cwCheckBox.isSelected());
				setToolTipText(Lang.get("kmc.notes.create.cw_text_tooltip"));
			}
		};
		JPanel cwPanel = new JPanel() {
			{
				setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
				add(cwCheckBox);
				add(cwText);
				setToolTipText(Lang.get("kmc.notes.create.tooltip"));
			}
		};
		centerPanel.add(cwPanel);
		postContent = new JTextArea() {
			{
				setAlignmentX(Component.CENTER_ALIGNMENT);
				setMaximumSize((new Dimension(getMaximumSize().width, 100)));
				setLineWrap(true);
			}
		};
		centerPanel.add(postContent);
		JButton postButton = new JButton() {
			{
				setAlignmentX(Component.CENTER_ALIGNMENT);
				setText(Lang.get("kmc.notes.create"));
				addActionListener(e -> {
					Create.NotesCreateRequest request = new Create.NotesCreateRequest();
					request.setText(postContent.getText());
					if (cwCheckBox.isSelected()) {
						request.setCw(cwText.getText());
					}
					try {
						new Create().get(Settings.getInstance().getTokens().get(0), request);
					} catch (Exception excp) {
						excp.printStackTrace();
						Dialogs.errorMsg(excp.getMessage());
					}
				});
			}
		};
		centerPanel.add(postButton);
		centerPanel.add(Box.createHorizontalGlue());
		contentPane.setVisible(false);
		contentPane.setVisible(true);
		contentPane.repaint();
	}
}
