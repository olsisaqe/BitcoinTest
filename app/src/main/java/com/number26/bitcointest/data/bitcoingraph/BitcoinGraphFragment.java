package com.number26.bitcointest.data.bitcoingraph;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.number26.bitcointest.Injection;
import com.number26.bitcointest.R;
import com.number26.bitcointest.data.model.BitcoinsGraphResponse;
import com.number26.bitcointest.data.model.Point;
import com.number26.bitcointest.databinding.FragmentGraphBitcoinBinding;

import cz.kinst.jakub.view.StatefulLayout;


public class BitcoinGraphFragment extends Fragment implements BitcoinGraphContract.View, SwipeRefreshLayout.OnRefreshListener {

	public static final String TAG = "BitcoinGraphFragment";

	private BitcoinGraphContract.UserActionsListener mActionsListener;
	private FragmentGraphBitcoinBinding binding;
	private BitcoinsGraphResponse mPoints;


	public static BitcoinGraphFragment newInstance() {
		BitcoinGraphFragment fragment = new BitcoinGraphFragment();
		return fragment;
	}


	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public BitcoinGraphFragment() {
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		mActionsListener = new BitcoinGraphPresenter(Injection.provideBitcoinsGraphDataRepository(), this);
	}


	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Retain this fragment across configuration changes.
		setRetainInstance(true);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_graph_bitcoin, container, false);
		return binding.getRoot();
	}


	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if(mPoints == null) {
			mActionsListener.loadGraph(true);
		} else {
			showGraph(mPoints);
		}
	}


	@Override
	public void setProgressIndicator(boolean active) {
		binding.swiperefresh.setRefreshing(false);
		if(active) {
			binding.statefulView.setState(StatefulLayout.State.PROGRESS);
		} else {
			binding.statefulView.setState(StatefulLayout.State.CONTENT);
		}
	}


	@Override
	public void showGraph(BitcoinsGraphResponse points) {
		mPoints = points;
		if(mPoints != null && !mPoints.getValues().isEmpty()) {
			binding.swiperefresh.setOnRefreshListener(this);
			renderGraph();
		}
	}


	@Override
	public void showEmptyState() {
		binding.statefulView.setState(StatefulLayout.State.EMPTY);
	}


	@Override
	public void showErrorState(String msg) {
		binding.statefulView.setState(StatefulLayout.State.EMPTY);
		binding.statefulView.setEmptyText(msg);
	}


	@Override
	public void onRefresh() {
		mActionsListener.loadGraph(true);
	}


	private void renderGraph() {
		if(mPoints != null && mPoints.getValues().size() > 0) {
			float mMinX = Float.MAX_VALUE, mMinY = Float.MAX_VALUE, mMaxX = Float.MIN_VALUE, mMaxY = Float.MIN_VALUE;
			for(Point point : mPoints.getValues()) {
				if(point.getX() < mMinX) {
					mMinX = point.getX();
				}
				if(point.getX() > mMaxX) {
					mMaxX = point.getX();
				}
				if(point.getY() < mMinY) {
					mMinY = point.getY();
				}
				if(point.getY() > mMaxY) {
					mMaxY = point.getY();
				}
			}
			binding.vGraphBitcoin.setXMin(mMinX);
			binding.vGraphBitcoin.setYMin(mMinY);
			binding.vGraphBitcoin.setXMax(mMaxX);
			binding.vGraphBitcoin.setYMax(mMaxY);
			binding.vGraphBitcoin.setPoints(mPoints.getValues());
			binding.vGraphBitcoin.setXName(mPoints.getPeriod() + " / " + mPoints.getName());
			binding.vGraphBitcoin.setYName(mPoints.getUnit());
			binding.vGraphBitcoin.renderFrameTexts();
			binding.vGraphBitcoin.setVisibility(View.VISIBLE);
		}
	}
}
