package io.github.knit_prg.kmc.misskey.objects

import com.fasterxml.jackson.databind.JsonNode

class User(json: JsonNode) {
	val id = json.get("id")?.asText() ?: ""
	val name = json.get("name")?.asText() ?: ""
	val username = json.get("username")?.asText() ?: ""
	val host = json.get("host")?.asText()
	val avatarUrl = json.get("avatarUrl")?.asText() ?: ""
	val avatarBlurhash = json.get("avatarBlurhash")?.asText() ?: ""
	val isBot = json.get("isBot")?.asBoolean() ?: false
	val isCat = json.get("isCat")?.asBoolean() ?: false
	val instance = json.get("instance")?.run { Instance(this) }

	//val emojis
	val onlineStatus = json.get("onlineStatus")?.asText() ?: "unknown"
}