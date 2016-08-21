package com.number26.bitcointest.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;


/**
 * Created by olsisaqe on 1/7/16.
 */
public class ViewUtils {

	public static int mShortAnimationDuration = 300;


	// Utility methods for layouting.
	public static int dpToPx(Context ctx, int dp) {
		return (int) (dp * ctx.getResources().getDisplayMetrics().density + 0.5f);
	}


	public static void setLayoutSize(View view, int width, int height) {
		ViewGroup.LayoutParams params = view.getLayoutParams();
		params.width = width;
		params.height = height;
		view.setLayoutParams(params);
	}


	public static void setLayoutHeight(View view, int height) {
		ViewGroup.LayoutParams params = view.getLayoutParams();
		params.height = height;
		view.setLayoutParams(params);
	}


	public static void setMargins(View v, int l, int t, int r, int b) {
		if(v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
			ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
			p.setMargins(l, t, r, b);
			v.requestLayout();
		}
	}


	public static ViewGroup.MarginLayoutParams getMargins(View v) {
		if(v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
			return (ViewGroup.MarginLayoutParams) v.getLayoutParams();
		}
		return null;
	}


	public static void setLayoutSizeAndGravity(View view, int width, int height, int gravity) {
		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
		params.width = width;
		params.height = height;
		params.gravity = gravity;
		view.setLayoutParams(params);
	}


	public static int getStatusBarHeight(Context ctx) {
		int result = 0;
		int resourceId = ctx.getResources().getIdentifier("status_bar_height", "dimen", "android");

		if(resourceId > 0) {
			result = ctx.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}


	public static void showViewWithRevealEffect(View v, int startPositionWidth, int startPositionHeight) {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			v.setVisibility(View.VISIBLE);
			float finalRadius = (float) Math.hypot(startPositionWidth, startPositionHeight);
			Animator anim = ViewAnimationUtils.createCircularReveal(v, startPositionWidth, startPositionHeight, 0, finalRadius);
			anim.start();
		} else {
			v.setVisibility(View.VISIBLE);
		}
	}


	public static void hideViewWithRevealEffect(final View v, int startPositionWidth, int startPositionHeight) {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			float initialRadius = (float) Math.hypot(startPositionWidth, startPositionHeight);
			Animator anim = ViewAnimationUtils.createCircularReveal(v, startPositionWidth, startPositionHeight, initialRadius, 0);
			anim.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					v.setVisibility(View.GONE);
				}
			});
			anim.start();
		} else {
			v.setVisibility(View.GONE);
		}
	}


	public static void fadeInView(View v) {
		// Set the content view to 0% opacity but visible, so that it is visible
		// (but fully transparent) during the animation.
		v.setAlpha(0f);
		v.setVisibility(View.VISIBLE);
		v.animate()
				.alpha(1f)
				.setDuration(mShortAnimationDuration)
				.setListener(null);
	}


	public static void fadeOutView(final View v) {
		v.animate()
				.alpha(0f)
				.setDuration(mShortAnimationDuration)
				.setListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						v.setVisibility(View.GONE);
					}
				});
	}


	public static void fadeOutInView(final View v, final View v2) {
		v.animate()
				.alpha(0f)
				.setDuration(mShortAnimationDuration)
				.setListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						v.setVisibility(View.GONE);
						v2.setAlpha(0f);
						v2.setVisibility(View.VISIBLE);
						v2.animate()
								.alpha(1f)
								.setDuration(mShortAnimationDuration)
								.setListener(null);
					}
				});
	}


	public static void disableTouchTheft(View view) {
		view.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				view.getParent().requestDisallowInterceptTouchEvent(true);
				switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) {
					case MotionEvent.ACTION_UP:
						view.getParent().requestDisallowInterceptTouchEvent(false);
						break;
				}
				return false;
			}
		});
	}


	public static float dpFromPx(final Context context, final float px) {
		return px / context.getResources().getDisplayMetrics().density;
	}


	public static float pxFromDp(final Context context, final float dp) {
		return dp * context.getResources().getDisplayMetrics().density;
	}
}
