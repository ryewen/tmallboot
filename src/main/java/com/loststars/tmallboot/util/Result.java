package com.loststars.tmallboot.util;

public class Result {
    
    public static final int CODE_SUCCESS = 0;
    
    public static final int CODE_FAILED = 1;

    private int code;
    
    private String message;
    
    private Object data;
    
    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.setData(data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
