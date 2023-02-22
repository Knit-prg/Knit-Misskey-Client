package io.github.knit_prg.kmc.misskey.objects

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.knit_prg.kmc.Util

class Note(json: JsonNode) {

	val id = json.get("id")?.asText() ?: ""

	val createdAt = json.get("createdAt")?.asText() ?: ""

	val userId = json.get("userId")?.asText() ?: ""

	val user = json.get("user")?.run { User(this) } ?: User(ObjectMapper().readTree("{}"))

	val text = json.get("text")?.asText() ?: ""

	val cw = json.get("cw")?.asText()

	val visibility = json.get("visibility")?.asText() ?: "public"

	val localOnly = json.get("localOnly")?.asBoolean() ?: false

	val renoteCount = json.get("renoteCount")?.asInt() ?: 0

	val repliesCount = json.get("repliesCount")?.asInt() ?: 0

	val reactions = json.get("reactions")?.run { Reaction.reactions(this) } ?: mapOf()

	//val reactionEmojis

	//val emojis

	val fileIds = json.get("fileIds")?.run { Util.arrayNodeToStringArray(json) } ?: arrayOf()

	val files = json.get("files")?.run { File.getFiles(this) } ?: arrayOf()

	val replyId = json.get("replyId")?.asText()

	val renoteId = json.get("renoteId")?.asText()

	val uri = json.get("uri")?.asText()
	val url = json.get("url")?.asText()
}