package com.number26.bitcointest.data.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;


/**
 * Created by olsisaqe on 20/08/16.
 *
 * Immutable model class for an Point.
 */
public class Point extends BaseObservable {

	private float x, y;


	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}


	public Point() {
	}

	@Bindable
	public float getX() {
		return x;
	}


	public void setX(float x) {
		this.x = x;
	}

	@Bindable
	public float getY() {
		return y;
	}


	public void setY(float y) {
		this.y = y;
	}
}
