package com.number26.bitcointest.data.bitcoingraph;

import com.number26.bitcointest.data.model.BitcoinsGraphResponse;


/**
 * Created by olsisaqe on 20/08/16.
 */
public interface BitcoinGraphContract {

	interface View{
		void setProgressIndicator(boolean active);

		void showGraph(BitcoinsGraphResponse points);

		void showEmptyState();

		void showErrorState(String msg);
	}


	interface UserActionsListener {

		void loadGraph(boolean refresh);
	}
}
