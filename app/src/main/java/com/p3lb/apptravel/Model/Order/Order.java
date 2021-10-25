package com.p3lb.apptravel.Model.Order;

import com.google.gson.annotations.SerializedName;
import com.p3lb.apptravel.Model.Flight.GetFlight;

import java.util.List;

public class Order {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<GetOrder> getOrders;
    @SerializedName("message")
    String message;

    public Order(String status, List<GetOrder> getOrders, String message) {
        this.status = status;
        this.getOrders = getOrders;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GetOrder> getGetOrders() {
        return getOrders;
    }

    public void setGetOrders(List<GetOrder> getOrders) {
        this.getOrders = getOrders;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
