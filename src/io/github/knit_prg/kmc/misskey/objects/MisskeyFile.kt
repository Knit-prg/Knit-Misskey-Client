package io.github.knit_prg.kmc.misskey.objects

import com.fasterxml.jackson.databind.JsonNode
import java.awt.Dimension

class MisskeyFile(json: JsonNode) {
	val id = json.get("id")?.asText() ?: ""
	val createdAt = json.get("createdAt")?.asText() ?: ""
	val name = json.get("name")?.asText() ?: ""
	val type = json.get("type")?.asText() ?: ""
	val md5 = json.get("md5")?.asText() ?: ""
	val size = json.get("size")?.asInt() ?: 0
	val isSensitive = json.get("isSensitive")?.asBoolean() ?: false
	val blurhash = json.get("blurhash")?.asText() ?: ""
	val properties = Dimension(
		json.get("properties")?.get("width")?.asInt() ?: 0,
		json.get("properties")?.get("height")?.asInt() ?: 0
	)
	val url = json.get("url")?.asText() ?: ""
	val thumbnailUrl = json.get("thumbnailUrl")?.asText() ?: ""
	val comment = json.get("comment")?.asText()
	val folderId = json.get("folderId")?.asText()
	val folder = json.get("folder")?.asText()
	val userId = json.get("userId")?.asText()
	val user = json.get("user")?.asText()

	companion object {
		public fun getFiles(json: JsonNode): Array<MisskeyFile> {
			val arrayList = arrayListOf<MisskeyFile>()
			if (!json.isArray) return arrayList.toTypedArray()
			for (i in 0 until json.size()) {
				json.get(i)?.run {
					arrayList.add(MisskeyFile(this))
				}
			}
			return arrayList.toTypedArray()
		}
	}
}