package com.number26.bitcointest.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by olsisaqe on 08/07/16.
 */
public class RetrofitFactory {

	public static <T> T createRetrofitService(final Class<T> clazz) {
		final Retrofit restAdapter = new Retrofit.Builder()
				.baseUrl(ConfigUtils.BASE_URL)
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(buildGsonConverter())
				.build();
		T service = restAdapter.create(clazz);

		return service;
	}


	private static GsonConverterFactory buildGsonConverter() {
		GsonBuilder gsonBuilder = new GsonBuilder();

		// Adding custom deserializers
		gsonBuilder.registerTypeAdapter(Boolean.class, new CustomBooleanGsonSerializer());
		gsonBuilder.registerTypeAdapter(boolean.class, new CustomBooleanGsonSerializer());
		Gson myGson = gsonBuilder.create();

		return GsonConverterFactory.create(myGson);
	}
}
