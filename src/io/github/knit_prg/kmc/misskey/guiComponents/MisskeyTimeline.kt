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
import javax.swing.ScrollPaneConstants

/**
 * Misskeyのタイムラインを示す
 *
 * @author Knit prg.
 * @since 0.1.0
 */
class MisskeyTimeline : JPanel() {

	val innerPanel = object : JPanel() {
		init {
			layout = BoxLayout(this, BoxLayout.Y_AXIS)
		}
	}

	val scrollPane = object : JScrollPane(innerPanel) {
		init {
			horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
		}
	}

	/**
	 * つくる
	 *
	 * @since 0.1.0
	 */
	init {
		layout = BorderLayout()
		add(scrollPane, BorderLayout.CENTER)
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

				else -> {
					println(json)
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