package io.github.knit_prg.kmc.misskey.objects

import com.fasterxml.jackson.databind.JsonNode

class Reaction {
	companion object {
		@JvmStatic
		public fun reactions(json: JsonNode): Map<String, Int> {
			val map = mutableMapOf<String, Int>()
			json.fields().forEach {
				it.value?.asInt()?.run {
					map[it.key] = this
				}
			}
			return map
		}
	}
}