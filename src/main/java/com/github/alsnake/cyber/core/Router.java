package com.github.alsnake.cyber.core;

import com.github.alsnake.cyber.http.HttpHandleCallback;
import com.github.alsnake.cyber.http.HttpMethod;
import com.github.alsnake.cyber.http.HttpRequest;
import com.github.alsnake.cyber.http.HttpResponse;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Router {
	private HttpRequest request;
	private HttpResponse response;
	private MultiValuedMap<HttpMethod, Route> routerMap;

	public Router(HttpRequest request, HttpResponse response) {
		this.request = request;
		this.response = response;
		routerMap = new ArrayListValuedHashMap<>();
	}

	public HttpRequest getRequest() {
		return request;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}

	public HttpResponse getResponse() {
		return response;
	}

	public void setResponse(HttpResponse response) {
		this.response = response;
	}

	public void get(String uri, HttpHandleCallback callback) {
		routerMap.put(HttpMethod.GET, new Route(HttpMethod.GET, uri, callback));
	}

	public Collection<Route> getRoutes(HttpMethod method) {
		return routerMap.get(method);
	}

	public boolean resolve() {
		HttpMethod method = request.getMethod();
		String uri = request.getUri();

		List<Route> routes = getRoutes(method).stream().filter(r -> r.method.equals(method) && r.uri.equals(uri)).collect(Collectors.toList());
		if(routes == null || routes.size() < 1) {
			response.send("404 NOT FOUND", Templates.NotFound404(uri));
			return false;
		}

		Route route = routes.get(0);
		route.callback.handle(request, response);
		return true;
	}

	private class Route {
		private HttpMethod method;
		private String uri;
		private HttpHandleCallback callback;

		public Route(HttpMethod method, String uri, HttpHandleCallback callback) {
			this.method = method;
			this.uri = uri;
			this.callback = callback;
		}

		public HttpMethod getMethod() {
			return method;
		}

		public String getUri() {
			return uri;
		}

		public HttpHandleCallback getCallback() {
			return callback;
		}

		@Override
		public String toString() {
			return "Route{" +
					"method=" + method +
					", uri='" + uri + '\'' +
					", callback=" + callback +
					'}';
		}
	}
}
