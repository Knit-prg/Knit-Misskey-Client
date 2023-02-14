package io.github.knit_prg.kmc.misskey;

import io.github.knit_prg.kmc.Dialogs;
import io.github.knit_prg.kmc.Gui;
import io.github.knit_prg.kmc.Lang;
import io.github.knit_prg.kmc.Settings;
import io.github.knit_prg.kmc.misskey.endpoints.exceptions.EndpointsError;
import io.github.knit_prg.kmc.misskey.endpoints.notes.Create;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

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
	 * 絵文字非展開の選択部
	 *
	 * @since 0.1.0
	 */
	private static JCheckBox noExtractEmojisCheckBox;

	/**
	 * ハッシュタグ非展開の選択部
	 *
	 * @since 0.1.0
	 */
	private static JCheckBox noExtractHashtagsCheckBox;

	/**
	 * メンション非展開の選択部
	 *
	 * @since 0.1.0
	 */
	private static JCheckBox noExtractMentionsCheckBox;

	/**
	 * ローカルのみかの選択部
	 *
	 * @since 0.1.0
	 */
	private static JCheckBox localOnlyCheckBox;

	/**
	 * 公開範囲の選択部
	 *
	 * @since 0.1.0
	 */
	private static JComboBox<String> visibilityComboBox;

	/**
	 * 投稿部
	 *
	 * @since 0.1.0
	 */
	private static JTextArea postContent;

	/**
	 * 投票を行うかの選択肢
	 *
	 * @since 0.1.0
	 */
	private static JCheckBox pollCheckBox;

	/**
	 * 投票の選択リスト
	 *
	 * @since 0.1.0
	 */
	private static JList<String> pollSelector;

	/**
	 * 投票の選択肢実体
	 *
	 * @since 0.1.0
	 */
	private static ArrayList<String> pollSelections;

	/**
	 * 投票の締め切り期日の選択
	 *
	 * @since 0.1.0
	 */
	private static JSpinner pollExpireSelector;

	/**
	 * 投票の複数選択を許容するかの選択
	 *
	 * @since 0.1.0
	 */
	private static JCheckBox pollMultipleSelector;

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
		noExtractEmojisCheckBox = new JCheckBox() {
			{
				setText(Lang.get("kmc.notes.create.no_extract_emojis"));
			}
		};
		noExtractHashtagsCheckBox = new JCheckBox() {
			{
				setText(Lang.get("kmc.notes.create.no_extract_hashtags"));
			}
		};
		noExtractMentionsCheckBox = new JCheckBox() {
			{
				setText(Lang.get("kmc.notes.create.no_extract_mentions"));
			}
		};
		JPanel noExtractsPanel = new JPanel() {
			{
				setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
				add(noExtractEmojisCheckBox);
				add(noExtractHashtagsCheckBox);
				add(noExtractMentionsCheckBox);
			}
		};
		centerPanel.add(noExtractsPanel);
		String[] visibilities = new String[]{
				Lang.get("kmc.notes.create.visibility.public"),
				Lang.get("kmc.notes.create.visibility.home"),
				Lang.get("kmc.notes.create.visibility.followers"),
				//Lang.get("kmc.notes.create.visibility.specified"),
		};
		localOnlyCheckBox = new JCheckBox() {
			{
				setText(Lang.get("kmc.notes.create.local_only"));
			}
		};
		visibilityComboBox = new JComboBox<>(visibilities) {
			{
				setMaximumSize(new Dimension(new Dimension(getMaximumSize().width, 20)));
			}
		};
		JPanel visibilityPanel = new JPanel() {
			{
				setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
				add(localOnlyCheckBox);
				add(visibilityComboBox);
			}
		};
		centerPanel.add(visibilityPanel);
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
		pollSelections = new ArrayList<>() {
			{
				add("select 1");
				add("select 2");
			}
		};
		pollSelector = new JList<>() {
			{
				setListData(pollSelections.toArray(String[]::new));
			}
		};
		pollMultipleSelector = new JCheckBox() {
			{
				setText(Lang.get("kmc.create.notes.poll.multiple"));
			}
		};
		JTextArea addText = new JTextArea() {
			{
				setMaximumSize(new Dimension(getMaximumSize().width, 20));
			}
		};
		JButton addButton = new JButton() {
			{
				setText(Lang.get("kmc.notes.create.poll.add"));
				addActionListener(e -> {
					if (!addText.getText().isEmpty()) {
						pollSelections.add(addText.getText());
						pollSelector.setListData(pollSelections.toArray(String[]::new));
					}
				});
			}
		};
		JButton deleteButton = new JButton() {
			{
				setText(Lang.get("kmc.notes.create.poll.delete"));
				addActionListener(e -> {
					List<String> toDelete = pollSelector.getSelectedValuesList();
					toDelete.forEach(item -> {
						if (pollSelections.size() <= 2) {
							return;
						}
						pollSelections.remove(item);
						pollSelector.setListData(pollSelections.toArray(String[]::new));
					});
				});
			}
		};
		pollExpireSelector = new JSpinner(new SpinnerDateModel()) {
			{
				setMaximumSize(new Dimension(getMaximumSize().width, 20));
			}
		};
		JPanel pollPanel = new JPanel() {
			{
				setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
				add(pollSelector);
				add(pollMultipleSelector);
				add(addText);
				add(addButton);
				add(deleteButton);
				add(pollExpireSelector);
				setVisible(false);
			}
		};
		pollCheckBox = new JCheckBox() {
			{
				setText(Lang.get("kmc.notes.create.poll"));
				setAlignmentX(Component.CENTER_ALIGNMENT);
				addActionListener(e -> pollPanel.setVisible(pollCheckBox.isSelected()));
			}
		};
		centerPanel.add(pollCheckBox);
		centerPanel.add(pollPanel);
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
					request.setNoExtractEmojis(noExtractEmojisCheckBox.isSelected());
					request.setNoExtractHashtags(noExtractHashtagsCheckBox.isSelected());
					request.setNoExtractMentions(noExtractMentionsCheckBox.isSelected());
					switch (visibilityComboBox.getSelectedIndex()) {
						case 0 -> request.setVisibility("public");
						case 1 -> request.setVisibility("home");
						case 2 -> request.setVisibility("followers");
						case 3 -> request.setVisibility("specified");
					}
					request.setLocalOnly(localOnlyCheckBox.isSelected());
					if (pollCheckBox.isSelected()) {
						Create.NotesCreatePoll pollData = new Create.NotesCreatePoll();
						pollData.setChoices(pollSelections);
						pollData.setExpiresAt(((Date) (pollExpireSelector.getValue())).getTime());
						pollData.setMultiple(pollMultipleSelector.isSelected());
						request.setPoll(pollData);
					}
					try {
						new Create().get(Settings.getInstance().getTokens().get(0), request);
					} catch (Exception excp) {
						excp.printStackTrace();
						if (excp instanceof EndpointsError) {
							Dialogs.errorMsg(excp.getMessage() + "\n" + ((EndpointsError) excp).getCode() + "\n" + ((EndpointsError) excp).getId());
						} else {
							Dialogs.errorMsg(excp.getMessage());
						}
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
