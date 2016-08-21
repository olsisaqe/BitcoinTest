package com.number26.bitcointest.data;

import android.support.annotation.NonNull;

import com.number26.bitcointest.data.model.BitcoinsGraphResponse;


/**
 * Created by olsisaqe on 20/08/16.
 */
public class RemoteBitcoinRepository implements BitcoinRepository {

	private final BitcoinServiceApi mBitcoinChartDataServiceApi;

	public RemoteBitcoinRepository(@NonNull BitcoinServiceApi api) {
		mBitcoinChartDataServiceApi = api;
	}


	@Override
	public void getBitcoinsGraphData(String chartType, @NonNull LoadBitcoinGrapDataCallback callback) {
		mBitcoinChartDataServiceApi.getBitcoinsGrapData(chartType, new BitcoinServiceApi.BitcoinsServiceCallback<BitcoinsGraphResponse>() {

			@Override
			public void onLoaded(BitcoinsGraphResponse bitcoinsGraphData) {
				callback.onBitcoinsDataLoaded(bitcoinsGraphData);
			}


			@Override
			public void onError(Throwable t) {
				callback.onError(t);
			}
		});
	}
}
