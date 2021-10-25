package com.p3lb.apptravel.Network;

import com.google.gson.annotations.SerializedName;
import com.p3lb.apptravel.Model.Flight.Flight;
import com.p3lb.apptravel.Model.Flight.GetFlight;
import com.p3lb.apptravel.Model.Login.Login;
import com.p3lb.apptravel.Model.Login.LoginUsers;
import com.p3lb.apptravel.Model.Order.Order;
import com.p3lb.apptravel.Model.Train.GetTrain;
import com.p3lb.apptravel.Model.Train.Train;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    //===========================Login==================================//
    @POST("users/login")
    @FormUrlEncoded
    Call<Login> loginUsers(@Field("nama_user") String nama_user,
                           @Field("password_user") String password_user);

    @POST("users/register")
    @FormUrlEncoded
    Call<LoginUsers> regisUser(@Field("nama_user") String nama_user,
                               @Field("password_user") String password_user,
                               @Field("phonenumber_user") String phonenumber_user,
                               @Field("birthdate_user") String birthdate_user
                               );
    @POST("users/profil")
    @FormUrlEncoded
    Call<Login> getUser(@Field("nama_user") String nama_user);
    //===========================Flight==================================//
    @POST("flights/addflights")
    @FormUrlEncoded
    Call<GetFlight> addflight(@Field("from_flight") String from_flight,
                              @Field("to_flight") String to_flight,
                              @Field("fromtime_flight") String fromtime_flight,
                              @Field("totime_flight") String totime_flight,
                              @Field("price_flight") String price_flight,
                              @Field("class_flight") String class_flight,
                              @Field("name_flight") String name_flight);
    @GET("flights")
    Call<Flight> getFlight();
    @POST("flights/buyticket")
    @FormUrlEncoded
    Call<Order> buyFlightTicket(@Field("firstname_order") String firstname_order,
                          @Field("lastname_order") String lastname_order,
                          @Field("email_order") String email_order,
                          @Field("phonenumber_order") String phonenumber_order,
                          @Field("fromflight_order") String fromflight_order,
                          @Field("toflight_order") String toflight_order,
                          @Field("fromflighttime_order") String fromflighttime_order,
                          @Field("toflighttime_order") String toflighttime_order,
                          @Field("nameflight_order") String nameflight_order,
                          @Field("username_order") String username_order,
                          @Field("price_order") String price_order
                           );
    @POST("trains/buyticket")
    @FormUrlEncoded
    Call<Order> buyTrainTicket(@Field("firstname_order") String firstname_order,
                          @Field("lastname_order") String lastname_order,
                          @Field("email_order") String email_order,
                          @Field("phonenumber_order") String phonenumber_order,
                          @Field("fromflight_order") String fromflight_order,
                          @Field("toflight_order") String toflight_order,
                          @Field("fromflighttime_order") String fromflighttime_order,
                          @Field("toflighttime_order") String toflighttime_order,
                          @Field("nameflight_order") String nameflight_order,
                          @Field("username_order") String username_order,
                          @Field("price_order") String price_order
    );

    @POST("orders/showorder")
    @FormUrlEncoded
    Call<Order> showOrder(@Field("username_order") String username_order);
    //===========================Train==================================//
    @POST("trains/addtrains")
    @FormUrlEncoded
    Call<GetTrain> addTrain(@Field("from_train") String from_train,
                            @Field("to_train") String to_train,
                            @Field("fromtime_train") String fromtime_train,
                            @Field("totime_train") String totime_train,
                            @Field("price_train") String price_train,
                            @Field("class_train") String class_train,
                            @Field("name_train") String name_train);
    @GET("trains")
    Call<Train> getTrain();


}
