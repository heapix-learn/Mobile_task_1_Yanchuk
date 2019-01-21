package com.example.first_task_k__r__o__s__h.MainActivity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.first_task_k__r__o__s__h.R;
import com.google.maps.android.ui.RotationLayout;

class myIconGenerator{

    private Context mContext;
    private ViewGroup mContainer;
    private RotationLayout mRotationLayout;
    private TextView mTextView;
    private int mRotation;
    private BubbleDrawable mBackground;


    myIconGenerator(Context context) {
        this.mContext = context;
        this.mBackground = new myIconGenerator.BubbleDrawable(this.mContext.getResources());
        this.mContainer = (ViewGroup)LayoutInflater.from(this.mContext).inflate(com.google.maps.android.R.layout.amu_text_bubble, (ViewGroup)null);
        this.mRotationLayout = (RotationLayout)this.mContainer.getChildAt(0);
        this.setStyle(1);
    }


    Bitmap makeIcon(CharSequence text) {
        if (this.mTextView != null) {
            this.mTextView.setTextColor(R.color.black);
            this.mTextView.setText(text);
            this.mTextView.setTextSize(15);

        }

        return this.makeIcon();
    }

    private Bitmap makeIcon() {
        int measureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mContainer.measure(measureSpec, measureSpec);
        int measuredWidth = this.mContainer.getMeasuredWidth();
        int measuredHeight = this.mContainer.getMeasuredHeight();
        this.mContainer.layout(0, 0, measuredWidth, measuredHeight);
        if (this.mRotation == 1 || this.mRotation == 3) {
            measuredHeight = this.mContainer.getMeasuredWidth();
            measuredWidth = this.mContainer.getMeasuredHeight();
        }


        Bitmap r = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888);


        Canvas canvas = new Canvas(r);
        if (this.mRotation != 0) {
            if (this.mRotation == 1) {
                canvas.translate((float)measuredWidth, 5.0F);
                canvas.rotate(90.0F);
            } else if (this.mRotation == 2) {
                canvas.rotate(180.0F, (float)(measuredWidth / 2), (float)(measuredHeight / 2));
            } else {
                canvas.translate(5.0F, (float)measuredHeight);
                canvas.rotate(270.0F);
            }
        }

        this.mContainer.draw(canvas);
        return r;
    }

    public void setContentView(View contentView) {
        this.mRotationLayout.removeAllViews();
        this.mRotationLayout.addView(contentView);
        View view = this.mRotationLayout.findViewById(com.google.maps.android.R.id.amu_text);
        this.mTextView = view instanceof TextView ? (TextView)view : null;
    }

    public void setRotation(int degrees) {
        this.mRotation = (degrees + 360) % 360 / 90;
    }

    private void setTextAppearance(Context context, int resid) {
        if (this.mTextView != null) {
            this.mTextView.setTextAppearance(context, resid);
        }

    }

    public void setTextAppearance(int resid) {
        this.setTextAppearance(this.mContext, resid);
    }

    public void setStyle(int style) {
        this.setColor(getStyleColor(style));
        this.setTextAppearance(this.mContext, getTextStyle(style));
    }

    public void setColor(int color) {
        this.mBackground.setColor(color);
        this.setBackground(this.mBackground);
    }

    public void setBackground(Drawable background) {
        this.mContainer.setBackgroundDrawable(background);
        if (background != null) {
            Rect rect = new Rect();
            background.getPadding(rect);
            this.mContainer.setPadding(rect.left, rect.top, rect.right, rect.bottom);
        } else {
            this.mContainer.setPadding(0, 0, 0, 0);
        }

    }

    private int getStyleColor(int style) {
        switch(style) {
            case 1:
            case 2:
            default:
                return -1;
            case 3:
                return -3407872;
            case 4:
                return -16737844;
            case 5:
                return -10053376;
            case 6:
                return -6736948;
            case 7:
                return -30720;
        }
    }

    private int getTextStyle(int style) {
        switch (style) {
            case 1:
            case 2:
            default:
                return R.style.amu_ClusterIcon_TextAppearance;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                return R.style.amu_ClusterIcon_TextAppearance;
        }
    }
    class BubbleDrawable extends Drawable {
        private final Drawable mShadow;
        private final Drawable mMask;
        private int mColor = -1;

        public BubbleDrawable(Resources res) {
            this.mMask = res.getDrawable(com.google.maps.android.R.drawable.amu_bubble_mask);
            this.mShadow = res.getDrawable(com.google.maps.android.R.drawable.amu_bubble_shadow);
        }

        public void setColor(int color) {
            this.mColor = color;
        }


        public void draw(Canvas canvas) {
            this.mMask.draw(canvas);
            canvas.drawColor(this.mColor, PorterDuff.Mode.SRC_IN);
            this.mShadow.draw(canvas);
        }

        public void setAlpha(int alpha) {
            throw new UnsupportedOperationException();
        }

        public void setColorFilter(ColorFilter cf) {
            throw new UnsupportedOperationException();
        }

        public int getOpacity() {
            return -3;
        }

        public void setBounds(int left, int top, int right, int bottom) {
            this.mMask.setBounds(left, top, right, bottom);
            this.mShadow.setBounds(left, top, right, bottom);
        }

        public void setBounds(Rect bounds) {
            this.mMask.setBounds(bounds);
            this.mShadow.setBounds(bounds);
        }

        public boolean getPadding(Rect padding) {
            return this.mMask.getPadding(padding);
        }
    }

}