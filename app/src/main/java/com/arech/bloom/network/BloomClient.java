package com.arech.bloom.network;

import com.arech.bloom.config.Resources;
import com.arech.bloom.core.crud.UserPreferencesDB;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pili Arancibia on 5/11/19
 */
public class BloomClient {
    private static Retrofit retrofit;

    /**
     * Create an instance of Retrofit object
     * */

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(Resources.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null);
    }

    public static <S> S createService(
            Class<S> serviceClass, String username, String password) {
//        if (!TextUtils.isEmpty(username)
//                && !TextUtils.isEmpty(password)) {
//            String authToken = Credentials.basic(username, password);
//            return createService(serviceClass, authToken);
//        }

        String token = UserPreferencesDB.getToken();
        if(token == null) token = "";
        return createService(serviceClass, token);
    }

    public static <S> S createService(
            Class<S> serviceClass, final String authToken) {
        if (true) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            if (true) {//!httpClient.interceptors().contains(interceptor)) {
                httpClient.interceptors().clear();
                httpClient.addInterceptor(interceptor);

                builder.client(httpClient.build());
            }
        }
        retrofit = builder.build();

        return retrofit.create(serviceClass);
    }

}
