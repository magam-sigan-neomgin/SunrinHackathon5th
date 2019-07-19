package com.jeongwoochang.sunrinhackathon5th.API;

import android.content.Context;
import com.jeongwoochang.sunrinhackathon5th.util.SharedPreferencesHelper;
import okhttp3.Interceptor;
import okhttp3.Response;
import timber.log.Timber;

import java.io.IOException;
import java.util.HashSet;

public class ReceivedCookiesInterceptor implements Interceptor {
    private SharedPreferencesHelper mDsp;

    public ReceivedCookiesInterceptor(Context context) {
        mDsp = new SharedPreferencesHelper(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());



        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>(originalResponse.headers("Set-Cookie"));
            Timber.d(cookies.toString());
            mDsp.setCookies(cookies);
        }
        return originalResponse;
    }
}
