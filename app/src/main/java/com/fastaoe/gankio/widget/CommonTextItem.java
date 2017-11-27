package com.fastaoe.gankio.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fastaoe.gankio.R;


/**
 * Created by jinjin on 16/3/25.
 */
public class CommonTextItem extends LinearLayout {

    private ImageView iv_left;
    private TextView tv_left;
    private ImageView iv_right;
    private TextView tv_right;

    public CommonTextItem(Context context) {
        super(context);
    }

    public CommonTextItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.widget_common_text, this, true);
        iv_left = (ImageView) view.findViewById(R.id.iv_left);
        iv_right = (ImageView) view.findViewById(R.id.iv_right);
        tv_left = (TextView) view.findViewById(R.id.tv_left);
        tv_right = (TextView) view.findViewById(R.id.tv_right);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CommonTextItem);

        int leftImageR = ta.getResourceId(R.styleable.CommonTextItem_ctiLeftImg, -1);
        if (leftImageR == -1) {
            iv_left.setVisibility(View.GONE);
        } else {
            iv_left.setImageResource(leftImageR);
        }

        int rightImageR = ta.getResourceId(R.styleable.CommonTextItem_ctiRightImg, -1);
        if (rightImageR == -1) {
            iv_right.setVisibility(View.GONE);
        } else {
            iv_right.setImageResource(rightImageR);
        }


        String leftText = ta.getString(R.styleable.CommonTextItem_ctiLeftText);
        if (TextUtils.isEmpty(leftText)) {
            tv_left.setVisibility(View.GONE);
        } else {
            tv_left.setText(leftText);
        }

        String rightText = ta.getString(R.styleable.CommonTextItem_ctiRightText);
        if (TextUtils.isEmpty(rightText)) {
            tv_right.setVisibility(View.GONE);
        } else {
            tv_right.setText(rightText);
        }

        int rightTextColor = ta.getColor(R.styleable.CommonTextItem_ctiRightTextColor, -1);
        if (rightTextColor != -1) {
            tv_right.setTextColor(rightTextColor);
        }

        float size = ta.getDimension(R.styleable.CommonTextItem_ctiRightTextSize, 0);
        if (size !=0){
            tv_right.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
        }

        ta.recycle();

    }

    public CommonTextItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void hideRightImg() {
        iv_right.setVisibility(View.GONE);
    }

    public void setLeftText(String text) {
        tv_left.setVisibility(View.VISIBLE);
        tv_left.setText(text);
    }

    public void setRightText(String text) {
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(text);
    }

    public void setLeftTextColor(int resourceId) {
        tv_left.setTextColor(resourceId);
    }

    public void setRightTextColor(int resourceId) {
        tv_right.setTextColor(resourceId);
    }


    public void setLeftTextSize(float size ){
        tv_left.setTextSize(size);
    }

    public void setRightTextSize(float size ){
        tv_right.setTextSize(size);
    }
}
