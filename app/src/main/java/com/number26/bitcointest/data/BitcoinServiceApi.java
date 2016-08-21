package com.number26.bitcointest.data;


import com.number26.bitcointest.data.model.BitcoinsGraphResponse;


/**
 * Created by olsisaqe on 20/08/16.
 * \
 * Defines an interface to the service API for getting bitcoin data from api that is used by this application. If we need
 * data and we request we should pass through this api
 */
public interface BitcoinServiceApi {

	interface BitcoinsServiceCallback<T>{

		void onLoaded(T bitcoinsGraphData);

		void onError(Throwable t);
	}

	void getBitcoinsGrapData(String queryString, BitcoinsServiceCallback<BitcoinsGraphResponse> callback);
}
