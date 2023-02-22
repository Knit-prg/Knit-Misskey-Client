package io.github.knit_prg.kmc.misskey.guiComponents;

import io.github.knit_prg.kmc.Dialogs;
import io.github.knit_prg.kmc.Lang;
import io.github.knit_prg.kmc.Settings;
import io.github.knit_prg.kmc.misskey.endpoints.exceptions.EndpointsError;
import io.github.knit_prg.kmc.misskey.endpoints.notes.Create;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * Misskeyの投稿画面
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public class MisskeyNoteCreator extends JPanel {

	/**
	 * 公開範囲の一覧
	 *
	 * @since 0.1.0
	 */
	private static final String[] VISIBILITIES = new String[]{
			Lang.get("kmc.notes.create.visibility.public"),
			Lang.get("kmc.notes.create.visibility.home"),
			Lang.get("kmc.notes.create.visibility.followers"),
			//Lang.get("kmc.notes.create.visibility.specified"),
	};

	/**
	 * 行の高さ
	 *
	 * @since 0.1.0
	 */
	private static final int LINE_HEIGHT = Settings.getInstance().getLineHeight();

	/**
	 * 絵文字非展開の選択部
	 *
	 * @since 0.1.0
	 */
	private final JCheckBox noExtractEmojisCheckBox = new JCheckBox(Lang.get("kmc.notes.create.no_extract_emojis"));

	/**
	 * ハッシュタグ非展開の選択部
	 *
	 * @since 0.1.0
	 */
	private final JCheckBox noExtractHashtagsCheckBox = new JCheckBox(Lang.get("kmc.notes.create.no_extract_hashtags"));

	/**
	 * メンション非展開の選択部
	 *
	 * @since 0.1.0
	 */
	private final JCheckBox noExtractMentionsCheckBox = new JCheckBox(Lang.get("kmc.notes.create.no_extract_mentions"));

	/**
	 * ローカルのみかの選択部
	 *
	 * @since 0.1.0
	 */
	private final JCheckBox localOnlyCheckBox = new JCheckBox(Lang.get("kmc.notes.create.local_only"));

	/**
	 * 公開範囲の選択部
	 *
	 * @since 0.1.0
	 */
	private final JComboBox<String> visibilityComboBox = new JComboBox<>(VISIBILITIES) {
		{
			setMaximumSize(new Dimension(getMaximumSize().width, LINE_HEIGHT));
		}
	};

	/**
	 * cwにするかの選択部
	 *
	 * @since 0.1.0
	 */
	private final JCheckBox cwCheckBox = new JCheckBox(Lang.get("kmc.notes.create.is_cw"));

	/**
	 * cw時の説明入力部
	 *
	 * @since 0.1.0
	 */
	private final JTextField cwTextField = new JTextField() {
		{
			setMaximumSize(new Dimension(getMaximumSize().width, LINE_HEIGHT));
			setEnabled(cwCheckBox.isSelected());
			setToolTipText(Lang.get("kmc.notes.create.cw_text_tooltip"));
		}
	};

	/**
	 * テキスト入力部
	 *
	 * @since 0.1.0
	 */
	private final JTextArea textTextArea = new JTextArea() {
		{
			setMaximumSize(new Dimension(getMaximumSize().width, LINE_HEIGHT * 5));
			setLineWrap(true);
		}
	};

	/**
	 * 投票を行うかの選択部
	 *
	 * @since 0.1.0
	 */
	private final JCheckBox pollCheckBox = new JCheckBox(Lang.get("kmc.notes.create.poll")) {
		{
			setAlignmentX(Component.CENTER_ALIGNMENT);
		}
	};

	/**
	 * 投票の選択肢実体
	 *
	 * @since 0.1.0
	 */
	private final ArrayList<String> pollSelections = new ArrayList<>() {
		{
			add("select 1");
			add("select 2");
		}
	};

	/**
	 * 投票の選択肢設定部
	 *
	 * @since 0.1.0
	 */
	private final JList<String> pollChoicesList = new JList<>(pollSelections.toArray(String[]::new));


	/**
	 * 投票の複数選択を許容するかの選択部
	 *
	 * @since 0.1.0
	 */
	private final JCheckBox pollMultipleCheckBox = new JCheckBox(Lang.get("kmc.notes.create.poll.multiple"));

	/**
	 * 選択肢に加える文字列の設定部
	 *
	 * @since 0.1.0
	 */
	private final JTextArea pollAddText = new JTextArea() {
		{
			setMaximumSize(new Dimension(getMaximumSize().width, LINE_HEIGHT));
		}
	};

	/**
	 * 選択肢に加えるボタン
	 *
	 * @since 0.1.0
	 */
	private final JButton pollAddButton = new JButton(Lang.get("kmc.notes.create.poll.add"));

	/**
	 * 選択肢から消すボタン
	 *
	 * @since 0.1.0
	 */
	private final JButton pollDeleteButton = new JButton(Lang.get("kmc.notes.create.poll.delete"));

	/**
	 * 投稿ボタン
	 *
	 * @since 0.1.0
	 */
	JButton postButton = new JButton(Lang.get("kmc.notes.create")) {
		{
			setAlignmentX(Component.CENTER_ALIGNMENT);
		}
	};

	/**
	 * 投票の締め切り期日の選択部
	 *
	 * @since 0.1.0
	 */
	private final JSpinner pollExpireSpinner = new JSpinner(new SpinnerDateModel()) {
		{
			setMaximumSize(new Dimension(getMaximumSize().width, LINE_HEIGHT));
		}
	};

	/**
	 * noExtracts系を纏めたパネル
	 *
	 * @since 0.1.0
	 */
	@SuppressWarnings("FieldCanBeLocal")
	private final JPanel noExtractsPanel = new JPanel() {
		{
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			add(noExtractEmojisCheckBox);
			add(noExtractHashtagsCheckBox);
			add(noExtractMentionsCheckBox);
		}
	};

	/**
	 * ノートの可視範囲を設定するパネル
	 *
	 * @since 0.1.0
	 */
	@SuppressWarnings("FieldCanBeLocal")
	private final JPanel visibilityPanel = new JPanel() {
		{
			setBorder(new TitledBorder(new LineBorder(Color.WHITE, 1), Lang.get("kmc.notes.create.visibility")));
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			add(localOnlyCheckBox);
			add(visibilityComboBox);
		}
	};

	/**
	 * cw関係の設定をするパネル
	 *
	 * @since 0.1.0
	 */
	@SuppressWarnings("FieldCanBeLocal")
	private final JPanel cwPanel = new JPanel() {
		{
			setBorder(new TitledBorder(new LineBorder(Color.WHITE, 1), Lang.get("kmc.notes.create.cw")));
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			add(cwCheckBox);
			add(cwTextField);
			setToolTipText(Lang.get("kmc.notes.create.tooltip"));
		}
	};

	/**
	 * 投票関係の設定をするパネル
	 *
	 * @since 0.1.0
	 */
	private final JPanel pollPanel = new JPanel() {
		{
			setBorder(new TitledBorder(new LineBorder(Color.WHITE, 1), Lang.get("kmc.notes.create.poll.settings")));
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			add(pollChoicesList);
			add(pollMultipleCheckBox);
			add(pollAddText);
			add(pollAddButton);
			add(pollDeleteButton);
			add(pollExpireSpinner);
			setVisible(false);
		}
	};

	/**
	 * 投稿画面をつくる
	 *
	 * @since 0.1.0
	 */
	public MisskeyNoteCreator() {
		setBorder(new TitledBorder(new LineBorder(Color.WHITE, 1), Lang.get("kmc.notes.create.settings")));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(noExtractsPanel);
		add(visibilityPanel);
		add(cwPanel);
		add(textTextArea);
		add(pollCheckBox);
		add(pollPanel);
		add(postButton);
		addEvents();
	}

	/**
	 * イベント追加
	 *
	 * @since 0.1.0
	 */
	private void addEvents() {
		cwCheckBox.addChangeListener(e -> cwTextField.setEnabled(cwCheckBox.isSelected()));
		pollAddButton.addActionListener(e -> {
			if (!pollAddText.getText().isEmpty()) {
				pollSelections.add(pollAddText.getText());
				pollChoicesList.setListData(pollSelections.toArray(String[]::new));
			}
		});
		pollCheckBox.addActionListener(e -> pollPanel.setVisible(pollCheckBox.isSelected()));
		pollDeleteButton.addActionListener(e -> {
			List<String> toDelete = pollChoicesList.getSelectedValuesList();
			toDelete.forEach(item -> {
				if (pollSelections.size() <= 2) {
					return;
				}
				pollSelections.remove(item);
				pollChoicesList.setListData(pollSelections.toArray(String[]::new));
			});
		});
		postButton.addActionListener(e -> createNote());
	}

	/**
	 * 投稿する
	 *
	 * @since 0.1.0
	 */
	private void createNote() {
		Create.NotesCreateRequest request = new Create.NotesCreateRequest() {
			{
				setText(textTextArea.getText());
				if (cwCheckBox.isSelected()) {
					setCw(cwTextField.getText());
				}
				setNoExtractEmojis(noExtractEmojisCheckBox.isSelected());
				setNoExtractHashtags(noExtractHashtagsCheckBox.isSelected());
				setNoExtractMentions(noExtractMentionsCheckBox.isSelected());
				switch (visibilityComboBox.getSelectedIndex()) {
					case 0 -> setVisibility("public");
					case 1 -> setVisibility("home");
					case 2 -> setVisibility("followers");
					case 3 -> setVisibility("specified");
				}
				setLocalOnly(localOnlyCheckBox.isSelected());
				if (pollCheckBox.isSelected()) {
					setPoll(new Create.NotesCreatePoll() {
						{
							setChoices(pollSelections);
							Date value = (Date) pollExpireSpinner.getValue();
							setExpiresAt(value.getTime());
							setMultiple(pollMultipleCheckBox.isSelected());
						}
					});
				}
			}
		};
		try {
			new Create().get(Settings.getInstance().getTokens().get(0), request);
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof EndpointsError ee) {
				Dialogs.errorMsg(ee.getMessage() + "\n" + ee.getCode() + "\n" + ee.getId());
			} else {
				Dialogs.errorMsg(e.getMessage());
			}
		}
	}
}
