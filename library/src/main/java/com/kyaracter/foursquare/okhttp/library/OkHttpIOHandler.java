package com.kyaracter.foursquare.okhttp.library;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import fi.foyt.foursquare.api.io.IOHandler;
import fi.foyt.foursquare.api.io.Method;
import fi.foyt.foursquare.api.io.MultipartParameter;
import fi.foyt.foursquare.api.io.Response;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkHttpIOHandler extends IOHandler {

    private static final String BOUNDARY = "----------gc0p4Jq0M2Yt08jU534c0p";

    private OkHttpClient client;

    public OkHttpIOHandler(OkHttpClient okhttpClient) {
        client = okhttpClient;
    }

    @Override
    public Response fetchData(String url, Method method) {
        Response response;

        try {
            Request request = new Request.Builder()
                    .url(new URL(url))
                    .method(method.name(), null)
                    .build();

            okhttp3.Response res = client.newCall(request).execute();
            response = new Response(res.body().string(), res.code(), res.message());

        } catch (MalformedURLException me) {
            response = new Response("", 400, "Malformed URL: " + url);
        } catch (IOException e) {
            response = new Response("", 500, e.getMessage());
        }

        return response;
    }

    @Override
    public Response fetchDataMultipartMime(String url, MultipartParameter... multipartParameters) {
        Response response;

        MultipartBody.Builder builder = new MultipartBody.Builder(BOUNDARY);

        for (MultipartParameter multipartParameter : multipartParameters) {
            builder.addPart(RequestBody.create(
                    MediaType.parse(multipartParameter.getContentType()),
                    multipartParameter.getContent()));
        }

        RequestBody requestBody = builder.build();

        try {
            Request request = new Request.Builder()
                    .url(new URL(url))
                    .method(Method.POST.name(), requestBody)
                    .build();

            okhttp3.Response res = client.newCall(request).execute();
            response = new Response(res.body().string(), res.code(), res.message());

        } catch (MalformedURLException me) {
            response = new Response("", 400, "Malformed URL: " + url);
        } catch (IOException e) {
            response = new Response("", 500, e.getMessage());
        }

        return response;
    }
}
