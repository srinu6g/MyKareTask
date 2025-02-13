package com.mykare.in.response;


import lombok.Data;

@Data
public class BaseResponse<T> {

    private int status;

    private String desc;

    private T body;

    public BaseResponse(int status, String success, T dataSaved) {
        this.status = status;
        this.desc = success;
        this.body = dataSaved;
    }

    public BaseResponse() {

    }

    public BaseResponse<T> createResponse(int status, String desc, T body){
        this.status = status;
        this.desc = desc;
        this.body = body;
        return this;
    }
    public BaseResponse<T> createResponse(int status, T body){
        this.status = status;
        switch (status){
            case 200:
                this.desc = "OK";
                break;
            case 400:
                this.desc = "Bad Request";
                break;
            case 404:
                this.desc = "Not Found";
                break;
            case 401:
                this.desc = "Unauthorized Access";
                break;
            default:
                this.desc = "Server Error";
                break;
        }
        this.body = body;
        return this;
    }
}
