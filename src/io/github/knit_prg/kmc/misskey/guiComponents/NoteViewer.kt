package io.github.knit_prg.kmc.misskey.guiComponents

import io.github.knit_prg.kmc.misskey.objects.MisskeyNote
import javax.swing.JPanel

/**
 * 1ノートを示す
 *
 * @author Knit prg.
 * @since 0.1.0
 */
class NoteViewer(note: MisskeyNote) : JPanel() {

	/**
	 * ノートをつくる
	 *
	 * @since 0.1.0
	 */
	init {
		add(MfmViewer(note.text))
	}
}