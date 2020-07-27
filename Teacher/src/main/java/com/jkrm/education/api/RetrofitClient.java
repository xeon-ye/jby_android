package com.jkrm.education.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.hzw.baselib.api.gson.LenientGsonConverterFactory;
import com.hzw.baselib.api.gson.NullOnEmptyConverterFactory;
import com.hzw.baselib.util.AwLog;
import com.jkrm.education.constants.MyConstant;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hzw on 2019/4/25.
 */
public class RetrofitClient {

    private static Retrofit mRetrofit;

    private RetrofitClient() {

    }

    public static Retrofit builderRetrofit() {
        if (mRetrofit == null) {
            Gson gson = new GsonBuilder().
                    registerTypeAdapterFactory(new NullToDefaultValueAdapterFactory())
                    .create();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(MyConstant.ServerConstant.API_BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(LenientGsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(getOkhttp().build())
                    .build();

        }
        return mRetrofit;
    }


    public static OkHttpClient.Builder getOkhttp() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        UrlInterceptor requestParamInterceptor = new UrlInterceptor();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                AwLog.d("okHttpLog: " + message);
            }
        });
        //HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Log.d("RetrofitLog", message));
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.writeTimeout(60, TimeUnit.SECONDS);
        httpClient.addNetworkInterceptor(requestParamInterceptor);
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                .allEnabledCipherSuites()
                .build();//解决在Android5.0版本以下https无法访问
        ConnectionSpec spec1 = new ConnectionSpec.Builder(ConnectionSpec.CLEARTEXT).build();//兼容http接口
        httpClient.connectionSpecs(Arrays.asList(spec,spec1));
        //添加ssl  完全信任证书
        httpClient.sslSocketFactory(SSLSocketFactoryCompat.getSSLSocketFactory());
        httpClient.hostnameVerifier(SSLSocketFactoryCompat.getHostnameVerifier());
        return httpClient;
    }






    public static class NullToDefaultValueAdapterFactory<T> implements TypeAdapterFactory {
        @Override
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType == String.class) {
                return (TypeAdapter<T>) new StringNullAdapter();
            } else if (rawType == Boolean.class) {
                return (TypeAdapter<T>) new BooleanNullAdapter();
            } else if (rawType == Integer.class) {
                return (TypeAdapter<T>) new IntegerNullAdapter();
            } else if (rawType == Double.class) {
                return (TypeAdapter<T>) new DoubleNullAdapter();
            } else if (rawType == Long.class) {
                return (TypeAdapter<T>) new LongNullAdapter();
            }
            return null;
        }
    }

    public static class LongNullAdapter extends TypeAdapter<Long> {
        @Override
        public Long read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return 0L;
            }
            return reader.nextLong();
        }

        @Override
        public void write(JsonWriter writer, Long value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }

    public static class DoubleNullAdapter extends TypeAdapter<Double> {
        @Override
        public Double read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return 0.0d;
            }
            double doubleValue = 0.0d;
            try {
                String stringValue = reader.nextString();
                doubleValue = Double.parseDouble(stringValue);
            } catch (NumberFormatException e) {
                return 0.0d;
            }
            return doubleValue;
        }

        @Override
        public void write(JsonWriter writer, Double value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }

    public static class IntegerNullAdapter extends TypeAdapter<Integer> {
        @Override
        public Integer read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return 0;
            }
            return reader.nextInt();
        }

        @Override
        public void write(JsonWriter writer, Integer value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }

    public static class BooleanNullAdapter extends TypeAdapter<Boolean> {
        @Override
        public Boolean read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return false;
            }
            return reader.nextBoolean();
        }

        @Override
        public void write(JsonWriter writer, Boolean value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }

    public static class StringNullAdapter extends TypeAdapter<String> {
        @Override
        public String read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }

        @Override
        public void write(JsonWriter writer, String value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }

}
