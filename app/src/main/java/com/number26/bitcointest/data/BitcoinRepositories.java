package com.number26.bitcointest.data;

import org.antlr.v4.runtime.misc.NotNull;


/**
 * Created by olsisaqe on 20/08/16.
 */
public class BitcoinRepositories {

	private static BitcoinRepository repository = null;

	private BitcoinRepositories() {
	}

	public synchronized static BitcoinRepository getRemoteBitcoinRepository(@NotNull BitcoinServiceApi api){
		if (null == repository) {
			repository = new RemoteBitcoinRepository(api);
		}
		return repository;
	}
}
