package org.cn.rpc;

public interface ResponseListener<T> {
	void onResponse(T response);
}
