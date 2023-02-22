package io.github.knit_prg.kmc.misskey.guiComponents

import io.github.knit_prg.kmc.misskey.objects.Note
import javax.swing.JLabel
import javax.swing.JPanel

/**
 * 1ノートを示す
 *
 * @author Knit prg.
 * @since 0.1.0
 */
class NoteViewer(note: Note) : JPanel() {

	/**
	 * ノートをつくる
	 *
	 * @since 0.1.0
	 */
	init {
		add(Mfm(note.text))
	}
}