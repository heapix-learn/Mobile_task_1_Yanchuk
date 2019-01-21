package com.example.first_task_k__r__o__s__h.MainActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.SquareTextView;

public class OwnIconRendered extends DefaultClusterRenderer<MapItem> {

    private final myIconGenerator mIconGenerator;
    private boolean shouldCluster;
    private static final int MIN_CLUSTER_SIZE = 1;
    private float mDensity;
    private ShapeDrawable mColoredCircleBackground;
    private SparseArray<BitmapDescriptor> mIcons = new SparseArray();


    public OwnIconRendered(Context context, GoogleMap map, ClusterManager<MapItem> clusterManager) {
        super(context, map, clusterManager);
        shouldCluster=true;

        this.mDensity = context.getResources().getDisplayMetrics().density;
        this.mIconGenerator = new myIconGenerator(context);
        this.mIconGenerator.setContentView(this.makeSquareTextView(context));
        this.mIconGenerator.setTextAppearance(com.google.maps.android.R.style.amu_ClusterIcon_TextAppearance);
        this.mIconGenerator.setBackground(this.makeClusterBackground());
    }


    private SquareTextView makeSquareTextView(Context context) {
        SquareTextView squareTextView = new SquareTextView(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -2);
        squareTextView.setLayoutParams(layoutParams);
        squareTextView.setId(com.google.maps.android.R.id.amu_text);
        int twelveDpi = (int)(12.0F * this.mDensity);
        squareTextView.setPadding(twelveDpi, twelveDpi, twelveDpi, twelveDpi);
        return squareTextView;
    }
    private LayerDrawable makeClusterBackground() {
        this.mColoredCircleBackground = new ShapeDrawable(new OvalShape());
        ShapeDrawable outline = new ShapeDrawable(new OvalShape());
        outline.getPaint().setColor(Color.argb(10, 255, 255, 0));
        LayerDrawable background = new LayerDrawable(new Drawable[]{outline, this.mColoredCircleBackground});
        int strokeWidth = (int)(this.mDensity * 8.0F);

        background.setLayerInset(1, strokeWidth, strokeWidth, strokeWidth, strokeWidth);
        return background;
    }

    protected void onBeforeClusterRendered(Cluster<MapItem> cluster, MarkerOptions markerOptions) {
        int bucket = this.getBucket(cluster);


        BitmapDescriptor descriptor = (BitmapDescriptor)this.mIcons.get(bucket);
        if (descriptor == null) {

            this.mColoredCircleBackground.getPaint().setColor(Color.rgb(  255, 255, 255));
            Bitmap bitmap =this.mIconGenerator.makeIcon(this.getClusterText(bucket));
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            paint.setColor(Color.argb(10,255, 255, 0));
            RectF oval2 = new RectF(16,16, bitmap.getWidth()-16, bitmap.getHeight()-16);
            canvas.drawOval(oval2, paint);

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.argb(20,255, 255, 0));
            oval2 = new RectF(0,0, bitmap.getWidth(), bitmap.getHeight());
            canvas.drawOval(oval2, paint);


            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(14);
            paint.setColor(Color.argb(10,255, 255, 0));
            oval2 = new RectF(16,16, bitmap.getWidth()-16, bitmap.getHeight()-16);
            canvas.drawOval(oval2, paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(18);
            paint.setColor(Color.argb(10,255, 255, 0));
            oval2 = new RectF(16,16, bitmap.getWidth()-16, bitmap.getHeight()-16);
            canvas.drawOval(oval2, paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(22);
            paint.setColor(Color.argb(10,255, 255, 0));
            oval2 = new RectF(16,16, bitmap.getWidth()-16, bitmap.getHeight()-16);
            canvas.drawOval(oval2, paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(26);
            paint.setColor(Color.argb(10,255, 255, 0));
            oval2 = new RectF(16,16, bitmap.getWidth()-16, bitmap.getHeight()-16);
            canvas.drawOval(oval2, paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(30);
            paint.setColor(Color.argb(5,255, 255, 0));
            oval2 = new RectF(16,16, bitmap.getWidth()-16, bitmap.getHeight()-16);
            canvas.drawOval(oval2, paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(34);
            paint.setColor(Color.argb(5,255, 255, 0));
            oval2 = new RectF(16,16, bitmap.getWidth()-16, bitmap.getHeight()-16);
            canvas.drawOval(oval2, paint);



            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(42);
            paint.setColor(Color.argb(5,255, 255, 0));
            oval2 = new RectF(16,16, bitmap.getWidth()-16, bitmap.getHeight()-16);
            canvas.drawOval(oval2, paint);

            descriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
            this.mIcons.put(bucket, descriptor);

        }

        markerOptions.icon(descriptor);
    }





    @Override
    protected boolean shouldRenderAsCluster(Cluster<MapItem> cluster) {

        if(shouldCluster)
        {
            return cluster.getSize() > MIN_CLUSTER_SIZE;
        }
        else
        {
            return shouldCluster;
        }
    }




}
