package com.cmov.tp1.customer.networking;

public class GetShowsRequest {

    private static final String PATH = "/show";
    private static final String METHOD = "GET";

    public GetShowsRequest(AsyncRequest.OnTaskCompleted onTaskCompleted) {
        AsyncRequest request = new AsyncRequest(onTaskCompleted);
        request.execute(PATH, METHOD);
    }


}
