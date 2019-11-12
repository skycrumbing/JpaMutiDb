package com.example.jpademo.config.base;

/**
 * @ClassName: Result
 * @Description: TODO
 * @Author: tantao
 * @CreateDate: 2019/11/12 14:13
 * @Version: 1.0
 */
public class Result {
    private int code;
    private String message;
    private Object data;

    public Result() {
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code) {
        this.code = code;
    }

    public Result(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

//    @JSONField(
//            serialize = false
//    )
//    public JSONObject getJSONObject() {
//        return this.data == null?null:JSONObject.parseObject(this.data.toString());
//    }
//
//    @JSONField(
//            serialize = false
//    )
//    public JSONArray getJSONArray() {
//        return this.data == null?null:JSONArray.parseArray(this.data.toString());
//    }
//
//    @JSONField(
//            serialize = false
//    )
//    public <T> T toObject(Class<T> clazz) {
//        return this.data == null?null:JSON.parseObject(this.data.toString(), clazz);
//    }
}

