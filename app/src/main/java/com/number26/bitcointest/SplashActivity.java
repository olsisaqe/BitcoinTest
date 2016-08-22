package com.number26.bitcointest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.number26.bitcointest.bitcoingraph.BitcoinGraphActivity;


/**
 * Created by olsisaqe on 06/08/16.
 */
public class SplashActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = new Intent(this, BitcoinGraphActivity.class);
		startActivity(intent);
		finish();
	}
}