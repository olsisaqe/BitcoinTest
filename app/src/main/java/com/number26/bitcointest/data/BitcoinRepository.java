package com.number26.bitcointest.data;

import android.support.annotation.NonNull;

import com.number26.bitcointest.data.model.BitcoinsGraphResponse;


/**
 * Main entry point for accessing bitcoin data from the api
 */
public interface BitcoinRepository {

    interface LoadBitcoinGrapDataCallback {
        void onBitcoinsDataLoaded(BitcoinsGraphResponse points);

        void onError(Throwable cause);
    }

	/**
     * Method that will call the api implementation to load the data for the graph
     * @param chartType - see at the {@link com.number26.bitcointest.util.ConfigUtils}
     * @param callback
     */
    void getBitcoinsGraphData(String chartType, @NonNull LoadBitcoinGrapDataCallback callback);
}
