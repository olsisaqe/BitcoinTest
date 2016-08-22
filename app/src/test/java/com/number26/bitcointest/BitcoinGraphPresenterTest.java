package com.number26.bitcointest;

import com.google.gson.GsonBuilder;
import com.number26.bitcointest.data.BitcoinRepository;
import com.number26.bitcointest.bitcoingraph.BitcoinGraphContract;
import com.number26.bitcointest.bitcoingraph.BitcoinGraphPresenter;
import com.number26.bitcointest.data.model.BitcoinsGraphResponse;
import com.number26.bitcointest.util.ConfigUtils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;


/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class BitcoinGraphPresenterTest {

	private BitcoinGraphPresenter mBitcoinGraphPresenter;
	public static final BitcoinsGraphResponse BITCOIN_GRAPH_FAKE_DATA = new GsonBuilder().
			create().
			fromJson(ConfigUtils.JSON_FAKE_DATA, BitcoinsGraphResponse.class);

	/**
	 * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
	 * perform further actions or assertions on them.
	 */
	@Captor
	private ArgumentCaptor<BitcoinRepository.LoadBitcoinGrapDataCallback> mLoadGraphDataCallbackCaptor;

	@Mock
	private BitcoinRepository mBitcoinRepository;

	@Mock
	private BitcoinGraphContract.View mGraphDataView;


	@Before
	public void setupBitcoinGrapDataPresenter() {
		MockitoAnnotations.initMocks(this);
		mBitcoinGraphPresenter = new BitcoinGraphPresenter(mBitcoinRepository, mGraphDataView);
	}


	@Test
	public void loadBitcoinGrapDataFromRepositoryAndLoadIntoView() {
		mBitcoinGraphPresenter.loadGraph(true);

		// Callback is captured and invoked with stubbed notes
		verify(mBitcoinRepository).getBitcoinsGraphData(eq(ConfigUtils.CHART_TYPE_MARKET_PRICE), mLoadGraphDataCallbackCaptor.capture());
		mLoadGraphDataCallbackCaptor.getValue().onBitcoinsDataLoaded(BITCOIN_GRAPH_FAKE_DATA);

		// Then progress indicator is hidden and notes are shown in UI
		InOrder inOrder = Mockito.inOrder(mGraphDataView);
		inOrder.verify(mGraphDataView).setProgressIndicator(true);
		inOrder.verify(mGraphDataView).setProgressIndicator(false);
		verify(mGraphDataView).showGraph(BITCOIN_GRAPH_FAKE_DATA);
	}
}