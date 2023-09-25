package com.example.fist_android.model;

public class ResponseDTO<T> {
    private String isSuccess = "";
    private String code = "";
    private String message = "";
    private String count = "";
    private T data[];

    public T[] getData(){
        return this.data;
    }
}
