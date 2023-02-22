package io.github.knit_prg.kmc.misskey.guiComponents

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.knit_prg.kmc.Gui
import io.github.knit_prg.kmc.Settings
import io.github.knit_prg.kmc.misskey.MisskeyStreaming
import io.github.knit_prg.kmc.misskey.objects.MisskeyNote
import java.awt.BorderLayout
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.BoxLayout
import javax.swing.JPanel
import javax.swing.JScrollPane

/**
 * Misskeyのタイムラインを示す
 *
 * @author Knit prg.
 * @since 0.1.0
 */
class MisskeyTimeline : JPanel() {

	val scrollPane = JScrollPane()

	val innerPanel = JPanel()

	/**
	 * つくる
	 *
	 * @since 0.1.0
	 */
	init {
		layout = BorderLayout()
		scrollPane.viewport.view = innerPanel
		add(scrollPane, BorderLayout.CENTER)
		innerPanel.layout = BoxLayout(innerPanel, BoxLayout.Y_AXIS)
		val streaming = MisskeyStreaming(
			Settings.instance.tokens[0],
			MisskeyStreaming.Channel.HYBRID_TIME_LINE
		)
		streaming.connect()
		streaming.onReceives.add { text ->
			val json = ObjectMapper().readTree(text.toString())
			when (json?.get("body")?.get("type")?.asText()) {
				"note" -> {
					json.get("body")?.get("body")?.let {
						innerPanel.add(NoteViewer(MisskeyNote(it)), 0)
						innerPanel.isVisible = false
						innerPanel.isVisible = true
					}
				}
			}
		}
		Gui.mainFrame.addWindowListener(object : WindowAdapter() {
			override fun windowClosing(e: WindowEvent?) {
				streaming.disconnect()
			}
		})
	}
}