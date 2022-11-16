package com.marwadibrothers.mbstatus.sevices;

import com.marwadibrothers.mbstatus.models.AppOpen;
import com.marwadibrothers.mbstatus.models.OtpModel;
import com.marwadibrothers.mbstatus.models.PopUpModel;
import com.marwadibrothers.mbstatus.models.UpdateAppModel;
import com.marwadibrothers.mbstatus.models.WaterMark;
import com.marwadibrothers.mbstatus.models.buiness.BusinessResponse;

import com.marwadibrothers.mbstatus.models.customFrame.CustomFrame;
import com.marwadibrothers.mbstatus.models.customFrameDemo.CustomFrameDemo;
import com.marwadibrothers.mbstatus.models.notification.NotificationResponse;
import com.marwadibrothers.mbstatus.models.payment.PaymentResponse;
import com.marwadibrothers.mbstatus.models.product.ProductResponse;
import com.marwadibrothers.mbstatus.models.profille.MyProfileResponse;
import com.marwadibrothers.mbstatus.models.subscriptionplan.PlanResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface ApiInterface {

    //    @Headers("api_key: DIGITAL_BANNER_API_KEY")


    @POST("user/app_version")
    Call<UpdateAppModel> updateApp();


    @Multipart
    @POST("user/signup")
    Call<ResponseBody> signup(@Part("full_name") RequestBody full_name, @Part("fcm_token") RequestBody fcm_token
            , @Part("designation") RequestBody designation
            , @Part("email") RequestBody email
            , @Part("address") RequestBody address
            , @Part("company_org_name") RequestBody company_org_name
            , @Part("facebook_username") RequestBody facebook_username, @Part("twitter_username") RequestBody twitter_username
            , @Part("instagram_username") RequestBody instagram_username, @Part("linkedin_username") RequestBody linkedin_username
            , @Part("whatsapp_username") RequestBody whatsapp_username, @Part("telegram_username") RequestBody telegram_username
            , @Part("youtube_username") RequestBody youtube_username, @Part("mobile_number") RequestBody mobile_number
            , @Part okhttp3.MultipartBody.Part Photo);


    @Multipart
    @POST("user/add_business")
    Call<ResponseBody> add_business(@Part("user_id") RequestBody user_id
            , @Part("business_name") RequestBody business_name
            , @Part("company_org_name") RequestBody company_org_name
            , @Part("business_website") RequestBody business_website
            , @Part("business_address") RequestBody business_address
            , @Part("business_email") RequestBody business_email, @Part("business_phone_no") RequestBody business_phone_no
            , @Part okhttp3.MultipartBody.Part Photo);


    @GET("user/get_home_screen_data")
    Call<ResponseBody> get_home_screen_data(@Query("user_id")String user_id,@Query("fcm_token")String fcm_token);

    @GET("user/get_greeting_products")
    Call<ResponseBody> get_greeting_products();

    @FormUrlEncoded
    @POST("bulkV2")
    Call<OtpModel> getOtp(@Header("authorization") String authorization,
                          @Field("variables_values") String variables_values,
                          @Field("route") String route,
                          @Field("numbers") String numbers);

    @Multipart
    @POST("user/get_product_list")
    Call<ProductResponse> get_product_list(@Part("sub_category_id") RequestBody sub_category_id);


    @Multipart
    @POST("user/get_user_business_list")
    Call<BusinessResponse> get_user_business_list(@Part("user_id") RequestBody user_id);

    @POST("user/get_watermark")
    Call<WaterMark> getWatermark();

    @POST("user/app_open")
    Call<AppOpen> appOpen();

    @POST("user/get_popup_image")
    Call<PopUpModel> popUPOpen();

    @GET("user/get_watermark")
    Call<ResponseBody> getWatermark1();


    @Multipart
    @POST("user/delete_business")
    Call<ResponseBody> delete_business(@Part("business_id") RequestBody business_id);

    @Multipart
    @POST("user/send_otp")
    Call<ResponseBody> send_otp(@Part("mobile_number") RequestBody mobile_number,@Part("fcm_token") RequestBody fcm_token);

    @Multipart
    @POST("user/my_profile")
    Call<ResponseBody> my_profile_send_otp(@Part("user_id") RequestBody user_id);

    @Multipart
    @POST("user/my_profile")
    Call<MyProfileResponse> my_profile(@Part("user_id") RequestBody user_id);

    @GET("user/get_notification_list")
    Call<NotificationResponse> get_notification_list();

    @GET("user/get_custom_frames_demo")
    Call<CustomFrameDemo> get_custom_frames_demo();

    @Multipart
    @POST("user/edit_business")
    Call<ResponseBody> edit_business(@Part("user_id") RequestBody user_id, @Part("business_name") RequestBody business_name
            , @Part("business_website") RequestBody business_website, @Part("business_address") RequestBody business_address
            , @Part("business_email") RequestBody business_email, @Part("business_id") RequestBody business_id
            , @Part("business_phone_no") RequestBody business_phone_no
            , @Part okhttp3.MultipartBody.Part Photo);


    @Multipart
    @POST("user/edit_profile")
    Call<ResponseBody> edit_profile(@Part("user_id") RequestBody user_id,
                                    @Part("fcm_token") RequestBody fcm_token,@Part("designation") RequestBody designation, @Part("full_name") RequestBody full_name, @Part("email") RequestBody email
            , @Part("address") RequestBody address, @Part("company_org_name") RequestBody company_org_name
            , @Part("facebook_username") RequestBody facebook_username, @Part("twitter_username") RequestBody twitter_username
            , @Part("instagram_username") RequestBody instagram_username, @Part("linkedin_username") RequestBody linkedin_username
            , @Part("whatsapp_username") RequestBody whatsapp_username, @Part("telegram_username") RequestBody telegram_username
            , @Part("youtube_username") RequestBody youtube_username, @Part("mobile_number") RequestBody mobile_number
            , @Part MultipartBody.Part Photo);


    @GET("user/get_greetings_data")
    Call<ResponseBody> get_greetings_data();


    @GET("settings/get_application_settings")
    Call<ResponseBody> get_application_settings();

    @Multipart
    @POST("user/download_image")
    Call<ResponseBody> download_image(@Part("user_id") RequestBody user_id, @Part("product_id") RequestBody product_id);

    @Multipart
    @POST("user/download_product")
    Call<ResponseBody> download_product(@Part("user_id") RequestBody user_id,
                                        @Part("sub_category_id") RequestBody sub_category_id,
                                        @Part("product_id") RequestBody product_id);

    @Multipart
    @POST("user/view_product")
    Call<ResponseBody> viewProduct(@Part("user_id") RequestBody user_id,
                                        @Part("sub_category_id") RequestBody sub_category_id,
                                        @Part("product_id") RequestBody product_id);

    @Multipart
    @POST("user/sub_category_open")
    Call<ResponseBody> openSubCat(@Part("sub_category_id") RequestBody sub_category_id);

    /*-----------------plans api-----------------*/
    @GET("user/get_plan_list")
    Call<PlanResponse> get_plan_list();

    @GET("user/get_payment_list") // get Razorpay key
    Call<PaymentResponse> get_payment_list();

    @Multipart
    @POST("user/add_plan")
    Call<ResponseBody> add_plan(@Part("user_id") RequestBody user_id, @Part("plan_id") RequestBody plan_id
            , @Part("plan_duration") RequestBody plan_duration, @Part("plan_amount") RequestBody plan_amount, @Part("payment_id") RequestBody payment_id);

    @Multipart
    @POST("user/get_user_plan_list")
//    Call<ResponseBody> get_user_plan_list(@Part("user_id") RequestBody user_id);
    Call<ResponseBody> get_user_plan_list(@Part("user_id") RequestBody user_id);


    /*-----------------Custom Frame First-----------------*/
    @Multipart
    @POST("user/get_user_custom_frames")
    Call<CustomFrame> GetCustomFrame(@Part("user_id") RequestBody user_id);

}
