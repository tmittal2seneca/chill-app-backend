package com.example.assignment2;

import java.util.List;

public class CustomizedResponse<T> { //<T> is generic.
    private String message;
    private List<T> body;

    public CustomizedResponse(){

    }

    public CustomizedResponse(String message, List<T> body) {
        this.message = message;
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getBody() {
        return body;
    }

    public void setBody(List<T> body) {
        this.body = body;
    }
}
