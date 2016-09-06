package org.cn.rpc;

public class Response {
	public int statusCode;
	public String description;
	public long timestamp;
	public String response;

	public boolean isSuccess() {
		return statusCode == 0 || statusCode == 200;
	}

	@Override
	public String toString() {
		return "Response [statusCode=" + statusCode + ", description=" + description + ", timestamp=" + timestamp
				+ ", response=" + response + "]";
	}

}
