package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestRootResponse extends RequestRootBase {
    RequestRootResponse request;

    public RequestRootResponse(){}

    public RequestRootResponse(String name, RequestRootResponse request){
        super(name);
        this.request = request;
    }

    public RequestRootResponse getRequest() {
        return request;
    }

    public void setRequest(RequestRootResponse request) {
        this.request = request;
    }
}
