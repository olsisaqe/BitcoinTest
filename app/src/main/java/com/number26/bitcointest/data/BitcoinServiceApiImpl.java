package com.number26.bitcointest.data;


import com.number26.bitcointest.data.model.BitcoinsGraphResponse;
import com.number26.bitcointest.util.RetrofitFactory;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by olsisaqe on 20/08/16.
 */
public class BitcoinServiceApiImpl implements BitcoinServiceApi {

	public interface BitcoinsGraphDataApiService {

		@GET("charts/{chart-type}?cors=true&format=json&lang=en")
		Observable<BitcoinsGraphResponse> getBitcoinsGrapData(@Path("chart-type") String chartType);
	}


	@Override
	public void getBitcoinsGrapData(String chartType, BitcoinsServiceCallback<BitcoinsGraphResponse> callback) {
		BitcoinsGraphDataApiService bitcoinsGrapApiService = RetrofitFactory.createRetrofitService(BitcoinsGraphDataApiService.class);

		Observable<BitcoinsGraphResponse> bitcoinsGraphResponseObservable = bitcoinsGrapApiService.getBitcoinsGrapData(chartType);

		bitcoinsGraphResponseObservable.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.doOnError(throwable ->{
				callback.onError(throwable);
			})
			.subscribe(
				current -> {
					if(current.getStatus().equalsIgnoreCase("ok")) {
						callback.onLoaded(current);
					} else {
						callback.onError(new Throwable(current.getStatus()));
					}
				},
				throwable -> {
					callback.onError(throwable);
				}
			);
	}
}
