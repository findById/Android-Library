package org.cn.rpc;

import org.cn.rpc.utils.ExecutorUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class RealService {

    private Request request;

    public RealService(Request request) {
        this.request = request;
    }

    public Response execute() {
        Response response = getResponse();
        return response;
    }

    private Response getResponse() {
        Response response = new Response();
        response.timestamp = System.currentTimeMillis();
        try {
            response = httpRequest(request.method(), request.url(), request.body());
            // response.response = Service.post(request.url(), request.body());
        } catch (Throwable e) {
            response.statusCode = -500;
            response.description = e.getMessage();
        }
        return response;
    }

    public Response httpRequest(String method, String url, String body) {
        Response response = new Response();
        response.timestamp = System.currentTimeMillis();
        HttpURLConnection connection = null;
        try {
            URL parsedUrl = new URL(url);
            connection = (HttpURLConnection) parsedUrl.openConnection();
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(1000 * 10);
            connection.setReadTimeout(1000 * 10);

            connection.addRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.addRequestProperty("User-Agent", "android-os");

            switch (method) {
                case "POST": {
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    out.write(body.getBytes("UTF-8"));
                    out.flush();
                    out.close();
                    break;
                }
                case "GET":
                default: {
                    connection.setRequestMethod("GET");
                    break;
                }
            }

            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                throw new IOException("" + responseCode);
            }

            response.response = org.cn.rpc.utils.IOUtil.asString(connection.getInputStream(), "UTF-8");
            response.statusCode = 200;
            response.description = "success.";
        } catch (ConnectException e) { // Connect
            response.statusCode = -200;
            response.description = e.getMessage();
        } catch (SocketTimeoutException e) { // Socket Timeout
            response.statusCode = -200;
            response.description = e.getMessage();
        } catch (MalformedURLException e) { // Bad URL
            response.statusCode = -200;
            response.description = e.getMessage();
        } catch (ProtocolException e) { // Protocol
            response.statusCode = -200;
            response.description = e.getMessage();
        } catch (IOException e) {
            response.statusCode = -200;
            response.description = e.getMessage();
        } catch (Throwable e) {
            response.statusCode = -200;
            response.description = e.getMessage();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return response;
    }

    // == asynchronous start ==
    public void enqueue(ResponseListener<Response> listener) {
        ExecutorUtil.getThreadPool().execute(new AsyncTask(listener));
    }

    class AsyncTask implements Runnable {
        private final ResponseListener<Response> listener;

        private AsyncTask(ResponseListener<Response> listener) {
            this.listener = listener;
        }

        @Override
        public void run() {
            Response response = getResponse();
            if (listener != null) {
                listener.onResponse(response);
            }
        }
    }
    // == asynchronous end ==
}
