package com.example.first_task_k__r__o__s__h;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.widget.ImageView;



@SuppressLint("AppCompatCustomView")
public class RoundRectCornerImageView extends ImageView {

    private float radius = 38.0f;
    private Path path;
    private RectF rect;

    public RoundRectCornerImageView(Context context) {
        super(context);
        init();
    }

    public RoundRectCornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundRectCornerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        path = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        path.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}

//@SuppressLint("AppCompatCustomView")
//public class RoundRectCornerImageView extends ImageView {
//
//    private Path mMaskPath;
//    private Paint mMaskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//    private int mCornerRadius = 10;
//
//    public RoundRectCornerImageView(Context context) {
//        super(context);
//
//        init();
//    }
//
//    public RoundRectCornerImageView(Context context, AttributeSet attributeSet) {
//        super(context, attributeSet);
//
//        init();
//    }
//
//    public RoundRectCornerImageView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//
//        init();
//    }
//
//    private void init() {
//        ViewCompat.setLayerType(this, ViewCompat.LAYER_TYPE_SOFTWARE, null);
//        mMaskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//    }
//
//
//    public void setCornerRadius(int cornerRadius) {
//        mCornerRadius = cornerRadius;
//        generateMaskPath(getWidth(), getHeight());
//        invalidate();
//    }
//
//    @Override
//    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
//        super.onSizeChanged(w, h, oldW, oldH);
//
//        if (w != oldW || h != oldH) {
//            generateMaskPath(w, h);
//        }
//    }
//
//    private void generateMaskPath(int w, int h) {
//        mMaskPath = new Path();
//        mMaskPath.addRoundRect(new RectF(0, 0, w, h), mCornerRadius, mCornerRadius, Path.Direction.CW);
//        mMaskPath.setFillType(Path.FillType.INVERSE_WINDING);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        if (canvas.isOpaque()) { // If canvas is opaque, make it transparent
//            canvas.saveLayerAlpha(0, 0, canvas.getWidth(), canvas.getHeight(), 255, Canvas.ALL_SAVE_FLAG);
//        }
//
//        super.onDraw(canvas);
//
//        if (mMaskPath != null) {
//            canvas.drawPath(mMaskPath, mMaskPaint);
//        }
//    }
//}