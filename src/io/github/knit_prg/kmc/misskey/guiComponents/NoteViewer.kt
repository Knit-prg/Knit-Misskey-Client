package io.github.knit_prg.kmc.misskey.guiComponents

import io.github.knit_prg.kmc.Gui
import io.github.knit_prg.kmc.misskey.objects.MisskeyNote

import java.awt.Color
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.border.LineBorder

/**
 * 1ノートを示す
 *
 * @author Knit prg.
 * @since 0.1.0
 */
class NoteViewer(note: MisskeyNote) : JPanel() {

	val userName = MfmViewer(note.user.name)

	val userNameConstraints = object : GridBagConstraints() {
		init {
			gridx = 0
			gridy = 0
			anchor = CENTER
			weightx = 0.0
		}
	}

	val userId = JLabel(note.user.username)

	val userIdConstraints = object : GridBagConstraints() {
		init {
			gridx = 1
			gridy = 0
			anchor = NORTHWEST
			weightx = 1.0
		}
	}

	val text = MfmViewer(note.text)

	val textConstraints = object : GridBagConstraints() {
		init {
			gridx = 1
			gridy = 1
			anchor = NORTHWEST
			weightx = 1.0
		}
	}

	val resizeEvent = object : ComponentAdapter() {
		override fun componentResized(e: ComponentEvent?) {
			maximumSize = Dimension(Gui.mainFrame.width, maximumSize.height)
			Gui.mainFrame.contentPane.isVisible = false
			Gui.mainFrame.contentPane.isVisible = true
			text.maximumSize = Dimension(Gui.mainFrame.width, text.maximumSize.height)
		}
	}

	/**
	 * ノートをつくる
	 *
	 * @since 0.1.0
	 */
	init {
		resizeEvent.componentResized(null)
		border = LineBorder(Color.WHITE, 1, true)
		val grid = GridBagLayout()
		layout = grid
		grid.setConstraints(userName, userNameConstraints)
		grid.setConstraints(userId, userIdConstraints)
		grid.setConstraints(text, textConstraints)
		add(userName)
		add(userId)
		add(text)
		Gui.mainFrame.addComponentListener(resizeEvent)
	}

	fun delete() {
		Gui.mainFrame.removeComponentListener(resizeEvent)
	}
}