package com.marwadibrothers.mbstatus.apicallback;

public interface GetAppSetting {
    void onSuccessResponse(String appNm, String appEmail, String appVersion, String whatsAppNo);
}
