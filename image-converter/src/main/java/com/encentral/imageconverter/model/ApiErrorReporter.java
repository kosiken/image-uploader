package com.encentral.imageconverter.model;


public class ApiErrorReporter {
    public  Integer code = 400;
    public String reason;

    public ApiErrorReporter(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\": " + code +
                ", \"reason\": \"" + reason + '"' +
                '}';
    }
}
