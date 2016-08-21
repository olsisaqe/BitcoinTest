package com.number26.bitcointest.data.model;

import java.util.ArrayList;


/**
 * Created by olsisaqe on 07/08/16.
 */
public class BitcoinsGraphResponse {

	private ArrayList<Point> values;
	private String status;
	private String name;
	private String unit;
	private String period;


	public BitcoinsGraphResponse(ArrayList<Point> values, String status, String name, String unit, String period) {
		this.values = values;
		this.status = status;
		this.name = name;
		this.unit = unit;
		this.period = period;
	}


	public BitcoinsGraphResponse() {
	}


	public ArrayList<Point> getValues() {
		return values;
	}


	public void setValues(ArrayList<Point> values) {
		this.values = values;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUnit() {
		return unit;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}


	public String getPeriod() {
		return period;
	}


	public void setPeriod(String period) {
		this.period = period;
	}
}
