package com.komlin.ratiolayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * @author yawei
 * @data on 2018/9/12 下午1:07
 * @email zyawei@live.com
 */
public class RatioLayout extends FrameLayout {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int DEFAULT_ORIENTATION = HORIZONTAL;

    private int orientation = DEFAULT_ORIENTATION;
    private float ratio = 0.0f;

    public RatioLayout(Context context) {
        this(context, null);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (null != attrs) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
            ratio = array.getFloat(R.styleable.RatioLayout_km_ratio, 0.0f);
            orientation = array.getInt(R.styleable.RatioLayout_android_orientation, DEFAULT_ORIENTATION);
            array.recycle();
        }
    }

    private static final String TAG = "KM-RatioLayout";

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (ratio == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        int count = getChildCount();
        int maxHeight = 0;
        int maxWidth = 0;
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                maxWidth = Math.max(maxWidth, child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
            }
        }

        int defaultWidth = getDefaultSize(0, widthMeasureSpec);
        int defaultHeight = getDefaultSize(0, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(widthMeasureSpec);
        int resultWidth;
        int resultHeight;
        //Log.i(TAG, "onMeasure default: " + defaultWidth + "/" + defaultHeight + " max " + maxWidth + "/" + maxHeight);
        if (orientation == HORIZONTAL) {
            if (widthMode == MeasureSpec.AT_MOST) {
                resultWidth = defaultWidth > maxWidth ? maxWidth : defaultWidth;
            } else {
                resultWidth = defaultWidth;
            }
            //父布局提供的高度是否足够
            int tempHeight = (int) (resultWidth * ratio);
            if (tempHeight > defaultHeight && defaultHeight != 0) {
                resultHeight = defaultHeight;
            } else {
                resultHeight = tempHeight;
            }

        } else {
            if (heightMode == MeasureSpec.AT_MOST) {
                resultHeight = defaultHeight > maxHeight ? maxHeight : defaultHeight;
            } else {
                resultHeight = defaultHeight;
            }
            int tempWidth = (int) (resultHeight * ratio);
            if (tempWidth > defaultWidth && defaultWidth != 0) {
                resultWidth = defaultWidth;
            } else {
                resultWidth = tempWidth;
            }
        }

        //Log.d(TAG, "result: " + resultWidth + "/" + resultHeight);
        setMeasuredDimension(resultWidth, resultHeight);
        //Log.d(TAG, "Measured: " + getMeasuredWidth() + "/" + getMeasuredHeight());
        int widthSpec;
        int heightSpec;
        if (orientation == HORIZONTAL) {
            widthSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), widthMode);
            heightSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY);
        } else {
            widthSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY);
            heightSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(), heightMode);
        }
        super.onMeasure(widthSpec, heightSpec);
    }
}
