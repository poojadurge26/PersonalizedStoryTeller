package com.example.mypersonalizedstory;

public interface ResponseCallback {
    void onResponse(String response);
    void onError(Throwable throwable);
}
