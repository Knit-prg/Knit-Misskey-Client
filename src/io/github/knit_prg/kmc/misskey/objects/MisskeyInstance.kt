package io.github.knit_prg.kmc.misskey.objects

import com.fasterxml.jackson.databind.JsonNode

class MisskeyInstance(json: JsonNode) {
	val name = json.get("name")?.asText() ?: ""
	val softwareName = json.get("softwareName")?.asText() ?: ""
	val softwareVersion = json.get("softwareVersion")?.asText() ?: ""
	val iconUrl = json.get("iconUrl").asText() ?: ""
	val faviconUrl = json.get("faviconUrl")?.asText() ?: ""
	val themeColor = json.get("themeColor")?.asText() ?: ""
}