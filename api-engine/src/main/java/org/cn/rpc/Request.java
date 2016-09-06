package org.cn.rpc;

public class Request {
	public final long beginTime = System.currentTimeMillis();
	public final String url;
	public final String method;
	public final String body;
	public final int retry;

	private Request(Builder builder) {
		this.url = builder.url;
		this.method = builder.method;
		this.body = builder.body;
		this.retry = builder.retry;
	}

	public String url() {
		return url;
	}

	public String method() {
		return method;
	}

	public String body() {
		return body;
	}

	public int retry() {
		return retry;
	}

	public static Builder create() {
		return new Builder();
	}

	public static class Builder {
		public String url;
		public String method;
		public String body;
		public int retry = 0;

		public Builder get(String url) {
			this.url = url;
			this.method = "GET";
			return this;
		}

		public Builder post(String url) {
			this.url = url;
			this.method = "POST";
			return this;
		}

		public Builder url(String url) {
			return get(url);
		}

		public Builder body(String body) {
			this.body = body;
			return this;
		}

		public Builder retry(int retry) {
			this.retry = retry;
			return this;
		}

		public Request build() {
			if (url == null) {
				throw new IllegalStateException("url must not be null.");
			}
			return new Request(this);
		}
	}
}
