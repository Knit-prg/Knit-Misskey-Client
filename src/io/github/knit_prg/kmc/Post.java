package io.github.knit_prg.kmc;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * POSTを簡略化する為のやつ
 *
 * @author Knit prg.
 * @since 0.1.0
 */
public final class Post {

	/**
	 * 接続の実体
	 *
	 * @since 0.1.0
	 */
	public HttpsURLConnection connection;

	/**
	 * Postする
	 *
	 * @param urlS    URL
	 * @param request リクエスト
	 * @throws IOException 爆発
	 * @since 0.1.0
	 */
	public Post(String urlS, String request) throws IOException {
		URL url = new URL(urlS);
		connection = (HttpsURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
		PrintStream print = new PrintStream(connection.getOutputStream());
		print.print(request);
		print.close();
		connection.connect();
	}
}
