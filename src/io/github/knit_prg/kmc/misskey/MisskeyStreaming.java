package io.github.knit_prg.kmc.misskey;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import io.github.knit_prg.kmc.settingsProperties.Token;

/**
 * MisskeyのストリーミングAPIを実装する。
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public class MisskeyStreaming {

	/**
	 * ID
	 *
	 * @since 0.1.0
	 */
	public final UUID id = new UUID(new Random().nextLong(), new Random().nextLong());

	/**
	 * ソケット本体
	 *
	 * @since 0.1.0
	 */
	public WebSocket webSocket;

	/**
	 * なんか
	 *
	 * @since 0.1.0
	 */
	public WebSocket.Builder webSocketBuilder;

	/**
	 * なんか
	 *
	 * @since 0.1.0
	 */
	public WebSocket.Listener webSocketListener;

	/**
	 * 使用するトークン
	 *
	 * @since 0.1.0
	 */
	public Token token;

	/**
	 * 接続するチャンネル
	 *
	 * @since 0.1.0
	 */
	public Channel channel;

	/**
	 * 受信時のイベント
	 *
	 * @since 0.1.0
	 */
	public ArrayList<Consumer<CharSequence>> onReceives = new ArrayList<>();

	/**
	 * つくる
	 *
	 * @param token   トークン
	 * @param channel 接続するチャンネル
	 */
	public MisskeyStreaming(Token token, Channel channel) {
		this.token = token;
		this.channel = channel;
		HttpClient client = HttpClient.newHttpClient();
		webSocketBuilder = client.newWebSocketBuilder();
		webSocketListener = new WebSocket.Listener() {

			@Override
			public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
				System.out.println("statusCode=" + statusCode + ", reason=" + reason);
				return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
			}

			@Override
			public void onOpen(WebSocket webSocket) {
				webSocket.request(Long.MAX_VALUE);
				ObjectMapper mapper = new ObjectMapper();
				ObjectNode node = mapper.createObjectNode();
				node.put("type", "connect");
				ObjectNode bodyNode = mapper.createObjectNode();
				bodyNode.put("channel", channel.name);
				bodyNode.put("id", id.toString());
				node.set("body", bodyNode);
				webSocket.sendText(node.toString(), true);
			}

			@Override
			public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
				onReceives.forEach(onReceive -> {
					onReceive.accept(data);
				});
				return WebSocket.Listener.super.onText(webSocket, data, last);
			}
		};
	}

	/**
	 * 接続する
	 *
	 * @since 0.1.0
	 */
	public void connect() {
		CompletableFuture<WebSocket> comp = webSocketBuilder.buildAsync(URI.create("wss://" + token.user.split("@")[1] + "/streaming?i=" + token.token), webSocketListener);
		try {
			webSocket = comp.get();
		} catch (ExecutionException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 切断する
	 *
	 * @since 0.1.0
	 */
	public void disconnect() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("type", "disconnect");
		ObjectNode bodyNode = mapper.createObjectNode();
		bodyNode.put("id", id.toString());
		node.set("body", bodyNode);
		webSocket.sendText(node.toString(), true);
		CompletableFuture<WebSocket> end = webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "DISCONNECTED BY USER");
		try {
			end.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * チャンネル一覧
	 *
	 * @author Knit prg.
	 * @since 0.1.0
	 */
	public enum Channel {
		GLOBAL_TIME_LINE("globalTimeline"),
		HOME_TIME_LINE("homeTimeline"),
		HYBRID_TIME_LINE("hybridTimeline"),
		LOCAL_TIME_LINE("localTimeline"),
		MAIN("main");

		public final String name;

		Channel(String name) {
			this.name = name;
		}
	}
}
