package com.number26.bitcointest.data.bitcoingraph;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.number26.bitcointest.R;
import com.number26.bitcointest.databinding.ActivityBitcoinGraphBinding;


public class BitcoinGraphActivity extends AppCompatActivity {

	private static final String FRAGMENT_DID_INIT = "fragment_did_init";

	private ActivityBitcoinGraphBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_bitcoin_graph);
		setupToolbar();
		if(savedInstanceState == null || !savedInstanceState.getBoolean(FRAGMENT_DID_INIT, false)){
			initFragment();
		}
	}



	@Override
	public void onSaveInstanceState(Bundle outState) {
		// save current instance state
		super.onSaveInstanceState(outState);

		outState.putBoolean(FRAGMENT_DID_INIT, true);
	}


	private void initFragment() {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.rl_main_content, BitcoinGraphFragment.newInstance(), BitcoinGraphFragment.TAG);
		ft.commitNow();
	}


	private void setupToolbar() {
		setSupportActionBar(binding.toolbar);
		getSupportActionBar().setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
		getSupportActionBar().setDisplayShowTitleEnabled(false); // disable the default title element here (for centered title)
		binding.imgvToolbarTitleLogo.setVisibility(View.VISIBLE);
	}
}
