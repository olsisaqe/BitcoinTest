package com.number26.bitcointest;


import com.number26.bitcointest.data.BitcoinRepositories;
import com.number26.bitcointest.data.BitcoinRepository;
import com.number26.bitcointest.data.FakeBitcoinServiceApiImpl;


/**
 * Enables injection of mock implementations at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {

    public static BitcoinRepository provideBitcoinsGraphDataRepository() {
        return BitcoinRepositories.getRemoteBitcoinRepository(new FakeBitcoinServiceApiImpl());
    }
}
