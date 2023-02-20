package io.github.knit_prg.kmc;

import org.apache.commons.lang3.StringUtils;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
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

	private final HashMap<String, String> langs = new HashMap<>();

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

	private final DefaultComboBoxModel<String> langsModel = new DefaultComboBoxModel<>() {
		{
			{
				{
					File langDir = new File("KMC/lang/");
					File[] langFiles = langDir.listFiles(pathname -> pathname.getName().startsWith("lang") && pathname.getName().endsWith(".properties"));
					for (File langFile : langFiles) {
						String langFileName = langFile.getName();
						String langId;
						if (langFileName.equals("lang.properties")) {
							langId = "ja-jp";
						} else {
							langId = StringUtils.stripStart(langFileName, "lang_");
							langId = StringUtils.stripEnd(langId, ".properties");
						}
						try {
							LangPropertiesMetaData langPropertiesMetaData = new LangPropertiesMetaData(new FileInputStream(langFile));
							String langName = Objects.requireNonNullElse(langPropertiesMetaData.getName(), langId);
							langs.put(langName, langId);
							addElement(langName);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	};

	/**
	 * 言語を選ぶやつ
	 *
	 * @since 0.1.0
	 */
	private final JComboBox<String> langComboBox = new JComboBox<>(langsModel) {
		{
			setMaximumSize(new Dimension(getMaximumSize().width, Settings.getInstance().getLineHeight()));
			Set<String> langNames = langs.keySet();
			for (String langName : langNames) {
				if (langs.get(langName).equals(Settings.getInstance().getLang())) {
					setSelectedItem(langName);
				}
			}
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
				add(Box.createGlue());
			}
		});
		contentPane.add(new JPanel() {
			{
				setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
				add(new JLabel(Lang.get("kmc.settings.lang")));
				add(langComboBox);
				add(Box.createGlue());
			}
		});
		contentPane.add(Box.createGlue());
	}

	/**
	 * 閉じた時の処理
	 *
	 * @since 0.1.0
	 */
	private void onClose() {
		Settings.getInstance().setLineHeight((Integer) lineHeightSpinner.getValue());
		Settings.getInstance().setLang(langs.get((String) langComboBox.getSelectedItem()));
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
