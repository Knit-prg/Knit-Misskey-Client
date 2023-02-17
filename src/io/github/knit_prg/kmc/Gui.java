package io.github.knit_prg.kmc;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import org.jetbrains.annotations.NotNull;

/**
 * GUIを管理する。
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public final class Gui {

	/**
	 * ウインドウ
	 *
	 * @since 0.1.0
	 */
	public static @NotNull JFrame mainFrame = new JFrame();

	/**
	 * 初期化
	 *
	 * @since 0.1.0
	 */
	public static void init() {
		mainFrame.setSize(800, 500);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setTitle("Knit Misskey Client v0.1.0");
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try (BufferedWriter writer = new BufferedWriter(new FileWriter("KMC/settings.json"))) {
					writer.write(Settings.getInstance().toJson());
				} catch (IOException excp) {
					excp.printStackTrace();
					System.exit(1);
				}
				System.exit(0);
			}
		});
		mainFrame.setJMenuBar(new JMenuBar() {
			{
				add(new JMenu(Lang.get("kmc.menubar.about")) {
					{
						addMouseListener(new MouseAdapter() {
							@Override
							public void mousePressed(MouseEvent e) {
								JDialog dialog = new JDialog(mainFrame) {
									{
										setSize(700, 500);
										setLocationRelativeTo(null);
										setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
									}
								};
								Container contentPane = dialog.getContentPane();
								contentPane.add(new JPanel() {
									{
										setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
										add(new JLabel("Knit Misskey Client") {
											{
												setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
												setAlignmentX(Component.CENTER_ALIGNMENT);
											}
										});
										add(new JLabel("Libraries used") {
											{
												setAlignmentX(Component.CENTER_ALIGNMENT);
											}
										});
										add(new JLabel("Apache commons Text 1.10.0 by Apache Commons") {
											{
												setAlignmentX(Component.CENTER_ALIGNMENT);
											}
										});
										add(new JLabel("FlatLaf 3.0 by FormDev Software") {
											{
												setAlignmentX(Component.CENTER_ALIGNMENT);
											}
										});
										add(new JLabel("Jackson 2.14.0 by FasterXML") {
											{
												setAlignmentX(Component.CENTER_ALIGNMENT);
											}
										});
									}
								});
								dialog.setVisible(true);
							}
						});
					}
				});
			}
		});
		mainFrame.setVisible(true);
	}
}
