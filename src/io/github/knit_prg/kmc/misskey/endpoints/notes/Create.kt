package io.github.knit_prg.kmc.misskey.endpoints.notes

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

import io.github.knit_prg.kmc.Post
import io.github.knit_prg.kmc.misskey.endpoints.Endpoint
import io.github.knit_prg.kmc.misskey.endpoints.EndpointRequest
import io.github.knit_prg.kmc.misskey.endpoints.EndpointResponse
import io.github.knit_prg.kmc.misskey.endpoints.exceptions.CannotCreateAlreadyExpiredPoll
import io.github.knit_prg.kmc.misskey.endpoints.exceptions.CannotRenoteToAPureRenote
import io.github.knit_prg.kmc.misskey.endpoints.exceptions.CannotReplyToAPureRenote
import io.github.knit_prg.kmc.misskey.endpoints.exceptions.EndpointsError
import io.github.knit_prg.kmc.misskey.endpoints.exceptions.InternalError
import io.github.knit_prg.kmc.misskey.endpoints.exceptions.NoSuchChannel
import io.github.knit_prg.kmc.misskey.endpoints.exceptions.NoSuchRenoteTarget
import io.github.knit_prg.kmc.misskey.endpoints.exceptions.NoSuchReplyTarget
import io.github.knit_prg.kmc.misskey.endpoints.exceptions.YouHaveBeenBlocked
import io.github.knit_prg.kmc.settingsProperties.Token

/**
 * notes/create
 *
 * @since 0.1.0
 * @author Knit prg.
 */
class Create : Endpoint() {

	override fun get(token: Token, request: EndpointRequest): EndpointResponse {
		if (request !is NotesCreateRequest) {
			throw TypeCastException()
		}
		request.i = token.token
		val post = Post("https://${token.user.split("@")[1]}/api/notes/create", request.toString())
		val json = ObjectMapper().readTree(post.connection.inputStream) ?: throw InternalError()
		//todo:返り値仮置き
		val errorCode = json.get("error")?.get("code")?.asText() ?: return NotesCreateResponse()
		when (errorCode) {
			"CANNOT_CREATE_ALREADY_EXPIRED_POLL" -> throw CannotCreateAlreadyExpiredPoll()
			"CANNOT_RENOTE_TO_A_PURE_RENOTE" -> throw CannotRenoteToAPureRenote()
			"CANNOT_REPLY_TO_A_PURE_RENOTE" -> throw CannotReplyToAPureRenote()
			"NO_SUCH_CHANNEL" -> throw NoSuchChannel()
			"NO_SUCH_RENOTE_TARGET" -> throw NoSuchRenoteTarget()
			"NO_SUCH_REPLY_TARGET" -> throw NoSuchReplyTarget()
			"YOU_HAVE_BEEN_BLOCKED" -> throw YouHaveBeenBlocked()
			else -> throw EndpointsError.commonErrors(errorCode)
		}
	}

	class NotesCreateRequest : EndpointRequest() {
		var i: String = ""
		var channelId: String? = null
		var cw: String? = null
		var fileIds: ArrayList<String>? = null
		var localOnly = false
		var noExtractEmojis = false
		var noExtractHashtags = false
		var noExtractMentions = false
		var poll: NotesCreatePoll? = null
		var renoteId: String? = null
		var replyId: String? = null
		var text: String? = null
		var visibleUserIds: ArrayList<String>? = null
		var visibility: String = "public"

		override fun toString(): String {
			val mapper = ObjectMapper()
			val node = mapper.createObjectNode()
			node.put("i", i)
			channelId?.run { node.put("channelId", this) }
			cw?.run { node.put("cw", this) }
			fileIds?.run {
				val arrayNode = mapper.createArrayNode()
				this.forEach { arrayNode.add(it) }
				node.set("fileIds", arrayNode) as JsonNode
			}
			node.put("localOnly", localOnly)
			node.put("noExtractEmojis", noExtractEmojis)
			node.put("noExtractHashtags", noExtractHashtags)
			node.put("noExtractMentions", noExtractMentions)
			poll?.run {
				val objectNode = mapper.createObjectNode()
				val arrayNode = mapper.createArrayNode()
				this.choices.forEach { arrayNode.add(it) }
				objectNode.set("choices", arrayNode) as JsonNode
				this.expiredAfter?.run { objectNode.put("expiredAfter", this) }
				this.expiresAt?.run { objectNode.put("expiredAt", this) }
				objectNode.put("multiple", this.multiple)
				node.set("poll", objectNode) as JsonNode
			}
			renoteId?.run { node.put("renoteId", this) }
			replyId?.run { node.put("replyId", this) }
			text?.run { node.put("text", this) }
			visibleUserIds?.run {
				val arrayNode = mapper.createArrayNode()
				this.forEach { arrayNode.add(it) }
				node.set("visibleUserIds", arrayNode) as JsonNode
			}
			node.put("visibility", visibility)
			return node.toString()
		}
	}

	class NotesCreatePoll {
		var choices: ArrayList<String> = ArrayList()
		var expiredAfter: Int? = null
		var expiresAt: Int? = null
		var multiple = false
	}

	class NotesCreateResponse : EndpointResponse()
}