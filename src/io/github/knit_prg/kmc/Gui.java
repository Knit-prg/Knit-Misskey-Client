package io.github.knit_prg.kmc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;


public final class Gui {

	public static JFrame mainFrame = new JFrame();

	public static void init() {
		mainFrame.setSize(800, 500);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setTitle("Knit Misskey Client v0.1.0");
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try (BufferedWriter writer = new BufferedWriter(new FileWriter("KMC/settings.json"))) {
					writer.write(Settings.toPrettyString());
				} catch (IOException excp) {
					excp.printStackTrace();
					System.exit(1);
				}
				System.exit(0);
			}
		});
		mainFrame.setVisible(true);
	}

	public static void openMiAuth() {
		Container contentPane = mainFrame.getContentPane();
		contentPane.removeAll();
		BoxLayout layout = new BoxLayout(contentPane, BoxLayout.Y_AXIS);
		contentPane.setLayout(layout);
		JLabel order = new JLabel(Lang.get("kmc.login.input_instance"));
		order.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(order);
		JTextField textField = new JTextField();
		textField.setAlignmentX(Component.CENTER_ALIGNMENT);
		textField.setMaximumSize(new Dimension(200, 25));
		contentPane.add(textField);
		JButton button = new JButton(Lang.get("kmc.login.button"));
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setEnabled(false);
		contentPane.add(button);
		JLabel warn = new JLabel(Lang.get("kmc.login.invalid"));
		warn.setAlignmentX(Component.CENTER_ALIGNMENT);
		warn.setForeground(Color.RED);
		contentPane.add(warn);
		JLabel info = new JLabel();
		info.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(info);
		JButton finalButton = new JButton(Lang.get("kmc.login.final_button"));
		finalButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		finalButton.setVisible(false);
		contentPane.add(finalButton);
		final Thread[] thread = {null};
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (thread[0] != null) {
					thread[0].interrupt();
				}
				thread[0] = new Thread(() -> {
					String input = textField.getText();
					try {
						info.setText(Lang.get("kmc.loading"));
						URL url = new URL(input + "/manifest.json");
						HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
						connection.setRequestMethod("GET");
						connection.setDoInput(true);
						connection.setDoOutput(true);
						connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
						connection.connect();
						info.setText(connection.getResponseMessage());
						JsonNode json = new ObjectMapper().readTree(connection.getInputStream());
						if (input.equals(textField.getText())) {
							info.setText(json.get("name").asText());
							warn.setVisible(false);
							button.setEnabled(true);
						}
					} catch (Exception excp) {
						if (input.equals(textField.getText())) {
							info.setText("");
							warn.setVisible(true);
							button.setEnabled(false);
						}
					}
				});
				thread[0].start();
			}
		});
		String[] uuids = {null};
		button.addActionListener(e -> {
			textField.setEnabled(false);
			String uuid = new UUID(new Random().nextLong(Long.MAX_VALUE), new Random().nextLong(Long.MAX_VALUE)).toString();
			String miAuthUrl = textField.getText() + "/miauth/" + uuid + "?name=Knit%20Misskey%20Client&permission=read:account,write:account,read:blocks,write:blocks,read:drive,write:drive,read:favorites,write:favorites,read:following,write:following,read:messaging,write:messaging,read:mutes,write:mutes,write:notes,read:notifications,write:notifications,write:reactions,write:votes,read:pages,write:pages,write:page-likes,read:page-likes";
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				try {
					Desktop.getDesktop().browse(new URI(miAuthUrl));
					finalButton.setVisible(true);
					uuids[0] = uuid;
				} catch (IOException | URISyntaxException excp) {
					excp.printStackTrace();
					warn.setText(Lang.get("kmc.login.failed_to_open_browser"));
					textField.setEnabled(true);
				}
			} else {
				info.setText(miAuthUrl);
				finalButton.setVisible(true);
				uuids[0] = uuid;
			}
		});
		TimerTask getToken = new TimerTask() {
			@Override
			public void run() {
				try {
					URL url = new URL(textField.getText() + "/api/miauth/" + uuids[0] + "/check");
					HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
					connection.setRequestMethod("POST");
					connection.setDoInput(true);
					connection.setDoOutput(true);
					connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
					PrintStream print = new PrintStream(connection.getOutputStream());
					print.print("{}");
					print.close();
					connection.connect();
					String instanceName = textField.getText();
					instanceName = StringUtils.stripStart(instanceName, "https://");
					instanceName = StringUtils.stripEnd(instanceName, "/");
					JsonNode json = new ObjectMapper().readTree(connection.getInputStream());
					String token = json.get("token").asText();
					String userName = json.get("user").get("username").asText() + "@" + instanceName;
					ArrayNode tokens = (ArrayNode) Settings.settings.get("tokens");
					ObjectMapper mapper = new ObjectMapper();
					ObjectNode newToken = mapper.createObjectNode();
					newToken.put("token", token);
					newToken.put("type", "misskey-miauth");
					newToken.put("user", userName);
					tokens.add(newToken);
					openTimeline();
				} catch (Exception excp) {
					excp.printStackTrace();
					warn.setText(Lang.get("kmc.login.failed_to_get_token"));
				}
			}
		};
		finalButton.addActionListener(e -> new Timer().schedule(getToken, 500));
		contentPane.add(Box.createHorizontalGlue());
		TimerTask repaint = new TimerTask() {
			@Override
			public void run() {
				contentPane.repaint();
			}
		};
		new Timer().schedule(repaint, 500);
	}

	public static void openTimeline() {
		Container contentPane = mainFrame.getContentPane();
		contentPane.removeAll();
		contentPane.repaint();
	}
}
