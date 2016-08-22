package com.number26.bitcointest.bitcoingraph;

import android.support.annotation.NonNull;

import com.number26.bitcointest.data.BitcoinRepository;
import com.number26.bitcointest.data.model.BitcoinsGraphResponse;
import com.number26.bitcointest.util.ConfigUtils;


/**
 * Created by olsisaqe on 20/08/16.
 */
public class BitcoinGraphPresenter implements BitcoinGraphContract.UserActionsListener {


	private final BitcoinRepository mBitcoinRepository;
	private final BitcoinGraphContract.View mBitcoinsGraphDataView;

	public BitcoinGraphPresenter(@NonNull BitcoinRepository bitcoinRepository, @NonNull BitcoinGraphContract.View bitcoinsGraphDataView) {
		mBitcoinRepository = bitcoinRepository;
		mBitcoinsGraphDataView = bitcoinsGraphDataView;
	}


	@Override
	public void loadGraph(boolean refresh) {
		mBitcoinsGraphDataView.setProgressIndicator(true);
		mBitcoinRepository.getBitcoinsGraphData(ConfigUtils.CHART_TYPE_MARKET_PRICE, new BitcoinRepository.LoadBitcoinGrapDataCallback() {

			@Override
			public void onBitcoinsDataLoaded(BitcoinsGraphResponse points) {
				mBitcoinsGraphDataView.setProgressIndicator(false);
				if(points.getValues() != null && points.getValues().size()>0) {
					mBitcoinsGraphDataView.showGraph(points);
				} else {
					mBitcoinsGraphDataView.showEmptyState();
				}
			}


			@Override
			public void onError(Throwable cause) {
				mBitcoinsGraphDataView.setProgressIndicator(false);
				mBitcoinsGraphDataView.showErrorState(cause.getMessage());
			}
		});
	}
}
