package com.number26.bitcointest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.number26.bitcointest.R;
import com.number26.bitcointest.data.model.Point;
import com.number26.bitcointest.databinding.ViewBitcoinGraphBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by olsisaqe on 21/08/16.
 */
public class BitcoinGraphView extends FrameLayout {

	private ViewBitcoinGraphBinding mBinding;
	private int mNumYSlices, mNumXSlices;
	private float mXMin, mYMin, mXMax, mYMax;
	private int mXMaxPositionPx = -1, mXMinPostionPx = -1, mYMaxPositionPx = -1, mYMinPositionPx = -1;
	private String mXName, mYName;
	private boolean didCalculatePositions = false;
	private List<Point> mPoints;
	private SimpleDateFormat dateFormat;
	private Paint mPaint;

	public BitcoinGraphView(Context context) {
		super(context);
		init(context, null);
	}


	public BitcoinGraphView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}


	public BitcoinGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}


	private void init(Context ctx, AttributeSet attrs){
		LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mBinding = DataBindingUtil.inflate(inflater, R.layout.view_bitcoin_graph, this, true);
		TypedArray typedArray = ctx.obtainStyledAttributes(attrs, R.styleable.BitcoinGraphView);
		mNumXSlices = typedArray.getInt(R.styleable.BitcoinGraphView_x_num_slices, Integer.MIN_VALUE);
		mNumYSlices = typedArray.getInt(R.styleable.BitcoinGraphView_y_num_slices, Integer.MIN_VALUE);
		mXMin = typedArray.getFloat(R.styleable.BitcoinGraphView_x_min, Float.MIN_VALUE);
		mXMax = typedArray.getFloat(R.styleable.BitcoinGraphView_x_max, Float.MIN_VALUE);
		mYMin = typedArray.getFloat(R.styleable.BitcoinGraphView_y_min, Float.MIN_VALUE);
		mYMax = typedArray.getFloat(R.styleable.BitcoinGraphView_y_max, Float.MIN_VALUE);
		mXName = typedArray.getString(R.styleable.BitcoinGraphView_x_name);
		mYName = typedArray.getString(R.styleable.BitcoinGraphView_y_name);
		this.setWillNotDraw(false);
		initPaint();
		if(isSetCorrectly()) {
			renderFrameTexts();
		}
	}


	private void initPaint() {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(3);
		mPaint.setColor(Color.GREEN);  //Change to what you want

	}


	/**
	 * Method to init the frame of the cartezian graph
	 */
	public void renderFrameTexts() {
		mBinding.llXContainerBitcoinGraph.removeAllViews();
		mBinding.llYContainerBitcoinGraph.removeAllViews();
		if(isSetCorrectly()) {
			if(mXName != null && !mXName.isEmpty()){
				mBinding.tvXName.setText(mXName);
			}
			if(mYName != null && !mYName.isEmpty()){
				mBinding.tvYName.setText(mYName);
			}
			mBinding.llXContainerBitcoinGraph.setWeightSum(mNumYSlices+1);
			mBinding.llYContainerBitcoinGraph.setWeightSum(mNumYSlices+1);
			dateFormat = new SimpleDateFormat("dd/MM");
			float xSlice = (mXMax - mXMin) / mNumXSlices;
			float ySlice = (mYMax - mYMin) / mNumYSlices;
			for(int i = 0; i <= mNumXSlices; i++) {
				TextView textView = new TextView(getContext(), null, 0);
				Date time = new Date((long) ((mXMin + xSlice * i) * 1000));
				textView.setText(dateFormat.format(time));
				textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimensionPixelSize(R.dimen.global_text_size_caption));
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				layoutParams.weight = 1.0f;
				if(i==0){
					textView.setId(R.id.tv_first_x);
				} else if(i == mNumXSlices) {
					textView.setId(R.id.tv_last_x);
				}
				mBinding.llXContainerBitcoinGraph.addView(textView, layoutParams);

			}

			for(int i = mNumYSlices; i >= 0; i--) {
				TextView textView = new TextView(getContext(), null, 0);
				textView.setText(String.format("%.2f",mYMin + ySlice * i ) + " _");
				textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimensionPixelSize(R.dimen.global_text_size_caption));
				textView.setGravity(Gravity.BOTTOM);
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				layoutParams.weight = 1.0f;
				if(i==mNumYSlices){
					textView.setId(R.id.tv_last_y);
				} else if(i == 0) {
					textView.setId(R.id.tv_first_y);
				}
				mBinding.llYContainerBitcoinGraph.addView(textView, layoutParams);
			}
			didCalculatePositions = false;
		}
	}


	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		mYMinPositionPx = mBinding.llYContainerBitcoinGraph.findViewById(R.id.tv_first_y).getBottom();
		mYMaxPositionPx = mBinding.llYContainerBitcoinGraph.findViewById(R.id.tv_last_y).getBottom();
		mXMinPostionPx = mBinding.viewDividerXBitcoinGraph.getLeft();
		mXMaxPositionPx = mBinding.viewDividerXBitcoinGraph.getRight();
		didCalculatePositions = true;

	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(mPoints != null && mPoints.size() > 0 && didCalculatePositions){
			canvas.drawColor(Color.WHITE);

			Path path = new Path();
			path.moveTo(calculateXPosition(mPoints.get(0)), calculateYPosition(mPoints.get(0)));
			for(int sec = 1; sec < mPoints.size(); sec++)
				path.lineTo(calculateXPosition(mPoints.get(sec)), calculateYPosition(mPoints.get(sec)));

			canvas.drawPath(path, mPaint);
		}
	}


	private int calculateYPosition(Point point){
		if(point != null){
			if(point.getY() == mYMin){
				return mYMinPositionPx;
			} else {
				// calculate the mapping in percentage of the value to the top bottom of the divider view and then
				// the result we substract from the bottom position of the divider (the end of the divider that is the 0 of our
				// cartezian
				return (int) (mYMinPositionPx + (mYMaxPositionPx - mYMinPositionPx) * (point.getY() - mYMin) / (mYMax - mYMin));
			}
		} else {
			return -1;
		}
	}


	private int calculateXPosition(Point point){
		if(point != null){
			if(point.getX() == mXMin){
				return mXMinPostionPx;
			} else {
				// calculate the mapping in percentage of the value to the top bottom of the divider view and then
				// the result we substract from the bottom position of the divider (the end of the divider that is the 0 of our
				// cartezian
				return (int) (mXMinPostionPx + ((mXMaxPositionPx - mXMinPostionPx) * (point.getX() - mXMin) / (mXMax - mXMin)));
			}
		} else {
			return -1;
		}
	}


	public boolean isSetCorrectly(){
		return mXMax != Float.MIN_VALUE && mXMin != Float.MIN_VALUE && mYMax != Float.MIN_VALUE && mYMin != Float.MIN_VALUE
				&& mNumXSlices != Integer.MIN_VALUE && mNumYSlices != Integer.MIN_VALUE;
	}

	public int getNumYSlices() {
		return mNumYSlices;
	}


	public void setNumYSlices(int numYSlices) {
		mNumYSlices = numYSlices;
	}


	public int getNumXSlices() {
		return mNumXSlices;
	}


	public void setNumXSlices(int numXSlices) {
		mNumXSlices = numXSlices;
	}


	public float getXMin() {
		return mXMin;
	}


	public void setXMin(float XMin) {
		mXMin = XMin;
	}


	public float getYMin() {
		return mYMin;
	}


	public void setYMin(float YMin) {
		mYMin = YMin;
	}


	public float getXMax() {
		return mXMax;
	}


	public void setXMax(float XMax) {
		mXMax = XMax;
	}


	public float getYMax() {
		return mYMax;
	}


	public void setYMax(float YMax) {
		mYMax = YMax;
	}


	public List<Point> getPoints() {
		return mPoints;
	}


	public void setPoints(List<Point> points) {
		mPoints = points;
		invalidate();
	}


	public String getXName() {
		return mXName;
	}


	public void setXName(String XName) {
		mXName = XName;
		mBinding.tvXName.setText(XName);
	}


	public String getYName() {
		return mYName;
	}


	public void setYName(String YName) {
		mYName = YName;
		mBinding.tvYName.setText(YName);
	}
}
