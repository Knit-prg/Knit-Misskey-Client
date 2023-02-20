package io.github.knit_prg.kmc

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode

import io.github.knit_prg.kmc.settingsProperties.Token

import java.io.File

/**
 * 設定を管理する
 *
 * @author Knit prg.
 * @since 0.1.0
 */
class Settings private constructor() {

	/**
	 * 言語設定
	 *
	 * @since 0.1.0
	 */
	var lang = "ja-jp"

	/**
	 * 取得しているトークン一覧
	 *
	 * @since 0.1.0
	 */
	val tokens = ArrayList<Token>()

	/**
	 * 行の高さ
	 *
	 * @since 0.1.0
	 */
	var lineHeight = 20

	/**
	 * 設定を読み込む居
	 *
	 * @since 0.1.0
	 */
	init {
		try {
			val json = ObjectMapper().readTree(File("KMC/settings.json"))
			json?.get("lang")?.asText()?.run { lang = this }
			val tokenSize = json?.get("tokens")?.size() ?: 0
			for (i in 0 until tokenSize) {
				val oneToken = json?.get("tokens")?.get(i) ?: continue
				val token = oneToken.get("token")?.asText() ?: continue
				val type = oneToken.get("type")?.asText() ?: continue
				val user = oneToken.get("user")?.asText() ?: continue
				val tokenT = Token()
				tokenT.token = token
				tokenT.type = type
				tokenT.user = user
				tokens.add(tokenT)
			}
			json?.get("line_height")?.asInt()?.run { lineHeight = this }
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	/**
	 * 設定をJSONにする
	 *
	 * @since 0.1.0
	 */
	fun toJson(): String {
		val mapper = ObjectMapper()
		val node = mapper.createObjectNode()
		node.put("lang", lang)
		val tokensArrayNode = mapper.createArrayNode()
		tokens.forEach {
			val tokenObjectNode = mapper.createObjectNode()
			tokenObjectNode.put("token", it.token)
			tokenObjectNode.put("type", it.type)
			tokenObjectNode.put("user", it.user)
			tokensArrayNode.add(tokenObjectNode)
		}
		node.set("tokens", tokensArrayNode) as ObjectNode
		node.put("line_height",lineHeight)
		return node.toPrettyString()
	}

	companion object {

		/**
		 * 設定データ
		 *
		 * @since 0.1.0
		 */
		@JvmStatic
		val instance = Settings()
	}

}