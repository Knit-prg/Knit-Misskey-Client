package io.github.knit_prg.kmc.misskey.login

import com.fasterxml.jackson.databind.ObjectMapper

import io.github.knit_prg.kmc.Gui
import io.github.knit_prg.kmc.Lang
import io.github.knit_prg.kmc.Settings
import io.github.knit_prg.kmc.misskey.MisskeyTimeline
import io.github.knit_prg.kmc.settingsProperties.Token

import java.awt.Color
import java.awt.Component
import java.awt.Desktop
import java.awt.Dimension
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.io.PrintStream
import java.net.URI
import java.net.URL
import java.util.Random
import java.util.Timer
import java.util.TimerTask
import java.util.UUID
import javax.net.ssl.HttpsURLConnection
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JTextField

import org.apache.commons.lang3.StringUtils

/**
 * MiAuthを司るクラス
 *
 * @author Knit prg.
 * @since 0.1.0
 */
class MiAuth {

	companion object {

		/**
		 * 全権限を示す文字列
		 *
		 * @since 0.1.0
		 */
		private const val ALL_PERMISSIONS =
			"read:account,write:account,read:blocks,write:blocks,read:drive,write:drive,read:favorites,write:favorites,read:following,write:following,read:messaging,write:messaging,read:mutes,write:mutes,write:notes,read:notifications,write:notifications,write:reactions,write:votes,read:pages,write:pages,write:page-likes,read:page-likes"

		/**
		 * 「インスタンスのURL(https://~)を入力して下さい。(MiAuthに対応したmisskeyインスタンスである必要があります。)」
		 *
		 * @since 0.1.0
		 */
		@JvmStatic
		private lateinit var order: JLabel

		/**
		 * インスタンス名入力欄
		 *
		 * @since 0.1.0
		 */
		@JvmStatic
		private lateinit var textField: JTextField

		/**
		 * 決定ボタン
		 *
		 * @since 0.1.0
		 */
		@JvmStatic
		private lateinit var button: JButton

		/**
		 * 警告文表示部
		 *
		 * @since 0.1.0
		 */
		@JvmStatic
		private lateinit var warn: JLabel

		/**
		 * インスタンス情報表示部
		 *
		 * @since 0.1.0
		 */
		@JvmStatic
		private lateinit var info: JLabel

		/**
		 * ログインボタン
		 *
		 * @since 0.1.0
		 */
		@JvmStatic
		private lateinit var finalButton: JButton

		/**
		 * インスタンス情報を非同期で取得する為のスレッド
		 *
		 * @since 0.1.0
		 */
		@JvmStatic
		private var serverInfoGetter: Thread? = null

		/**
		 * MiAuthのセッションID
		 *
		 * @since 0.1.0
		 */
		@JvmStatic
		private lateinit var uuid: String

		/**
		 * MiAuth用のログイン画面を開く
		 *
		 * @since 0.1.0
		 */
		@JvmStatic
		fun open() {
			val contentPane = Gui.mainFrame.contentPane
			contentPane.removeAll()
			val layout = BoxLayout(contentPane, BoxLayout.Y_AXIS)
			contentPane.layout = layout
			order = JLabel(Lang.get("kmc.login.input_instance"))
			order.alignmentX = Component.CENTER_ALIGNMENT
			contentPane.add(order)
			textField = JTextField()
			textField.alignmentX = Component.CENTER_ALIGNMENT
			textField.maximumSize = Dimension(200, 25)
			textField.addKeyListener(object : KeyAdapter() {
				override fun keyTyped(e: KeyEvent?) {
					onInput()
				}
			})
			contentPane.add(textField)
			button = JButton(Lang.get("kmc.login.button"))
			button.alignmentX = Component.CENTER_ALIGNMENT
			button.isEnabled = false
			button.addActionListener { onDecideInstance() }
			contentPane.add(button)
			warn = JLabel(Lang.get("kmc.login.invalid"))
			warn.alignmentX = Component.CENTER_ALIGNMENT
			warn.foreground = Color.RED
			contentPane.add(warn)
			info = JLabel()
			info.alignmentX = Component.CENTER_ALIGNMENT
			contentPane.add(info)
			finalButton = JButton(Lang.get("kmc.login.final_button"))
			finalButton.alignmentX = Component.CENTER_ALIGNMENT
			finalButton.isVisible = false
			finalButton.addActionListener { tryLogin() }
			contentPane.add(finalButton)
			contentPane.add(Box.createHorizontalGlue())
			contentPane.isVisible = false
			contentPane.isVisible = true
			contentPane.repaint()
		}

		/**
		 * インスタンス名が入力されている時に、インスタンス情報を取得しに行く
		 *
		 * @since 0.1.0
		 */
		@JvmStatic
		private fun onInput() {
			serverInfoGetter?.run {
				this.interrupt()
			}
			serverInfoGetter = object : Thread() {
				override fun run() {
					val input = textField.text
					try {
						info.text = Lang.get("kmc.loading")
						val url = URL("$input/manifest.json")
						val connection = url.openConnection() as HttpsURLConnection
						connection.requestMethod = "GET"
						connection.doInput = true
						connection.doOutput = true
						connection.setRequestProperty("Content-Type", "application/json;charset=utf-8")
						connection.connect()
						info.text = connection.responseMessage
						val json = ObjectMapper().readTree(connection.inputStream) ?: throw NullPointerException()
						if (input.equals(textField.text)) {
							json.get("name")?.asText()?.run { info.text = this }
							warn.isVisible = false
							button.isEnabled = true
						}
					} catch (e: Exception) {
						if (input == textField.text) {
							info.text = ""
							warn.isVisible = true
							button.isEnabled = false
						}
					}
				}
			}
			serverInfoGetter?.start()
		}

		/**
		 * ユーザーにMiAuth画面を開かせる
		 *
		 * @since 0.1.0
		 */
		@JvmStatic
		private fun onDecideInstance() {
			textField.isEnabled = false
			uuid = UUID(Random().nextLong(Long.MAX_VALUE), Random().nextLong(Long.MAX_VALUE)).toString()
			val miAuthUrl =
				"${textField.text}/miauth/${uuid}?name=Knit%20Misskey%20Client&permission=${ALL_PERMISSIONS}"
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				try {
					Desktop.getDesktop().browse(URI(miAuthUrl))
					finalButton.isVisible = true
				} catch (e: Exception) {
					e.printStackTrace()
					warn.text = Lang.get("kmc.login.failed_to_open_browser")
					textField.isEnabled = true
				}
			} else {
				info.text = miAuthUrl
				finalButton.isVisible = true
			}
		}

		/**
		 * MiAuthで生成されたAPIトークンを取得しようとする
		 *
		 * @since 0.1.0
		 */
		@JvmStatic
		private fun tryLogin() {
			val task = object : TimerTask() {
				override fun run() {
					try {
						val url = URL("${textField.text}/api/miauth/${uuid}/check")
						val connection = url.openConnection() as HttpsURLConnection
						connection.requestMethod = "POST"
						connection.doInput = true
						connection.doOutput = true
						connection.setRequestProperty("Content-Type", "application/json;charset=utf-8")
						val print = PrintStream(connection.outputStream)
						print.print("{}")
						print.close()
						connection.connect()
						var instanceName = textField.text
						instanceName = StringUtils.stripStart(instanceName, "https://")
						instanceName = StringUtils.stripEnd(instanceName, "/")
						val json = ObjectMapper().readTree(connection.inputStream)
							?: throw NullPointerException("json is null")
						val token = Token()
						val tokenS = json.get("token")?.asText() ?: throw NullPointerException("token is null")
						token.token = tokenS
						token.type = "misskey-miauth"
						val userS = json.get("user")?.get("username")?.asText()
							?: throw NullPointerException("user or user.username is null")
						token.user = "${userS}@${instanceName}"
						Settings.instance.tokens.add(token)
						MisskeyTimeline.open()
					} catch (e: Exception) {
						e.printStackTrace()
						warn.text = Lang.get("kmc.login.failed_to_get_token")
						warn.isVisible = true
					}
				}
			}
			Timer().schedule(task, 1000)
		}
	}
}