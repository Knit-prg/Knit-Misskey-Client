package io.github.knit_prg.kmc

import com.fasterxml.jackson.databind.ObjectMapper

import io.github.knit_prg.kmc.settingsProperties.Token

import java.io.File
import java.lang.StringBuilder

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
		val sb = StringBuilder("{\n")
		sb.append("\t\"lang\":\"${lang}\",\n")
		sb.append("\t\"tokens\":[\n")
		tokens.forEachIndexed { i, it ->
			if (i != 0) {
				sb.append(",\n")
			}
			sb.append("\t\t{\n")
			sb.append("\t\t\t\"token\":\"${it.token}\",\n")
			sb.append("\t\t\t\"type\":\"${it.type}\",\n")
			sb.append("\t\t\t\"user\":\"${it.user}\"\n")
			sb.append("\t\t}")
		}
		sb.append("\n")
		sb.append("\t]\n")
		sb.append("}\n")
		return sb.toString()
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