package com.github.alsnake.cyber;

import com.github.alsnake.cyber.http.*;

public class Cyber {
    public static void main(String[] args) {
		HttpServer httpServer = Http.createServer(new HttpHandleCallback() {
			@Override
			public void handle(HttpRequest req, HttpResponse res) {
				res.send("200 OK", "Hello World").end();
			}
		});
		httpServer.listen("0.0.0.0", 8081);
    }
}
