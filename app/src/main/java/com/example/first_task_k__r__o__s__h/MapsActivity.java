package com.example.first_task_k__r__o__s__h;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.RotationLayout;
import com.google.maps.android.ui.SquareTextView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ClusterManager<OwnMarker> mClusterManager;
    private ImageButton imageButtonAddNoteMaps;
    private OwnMarker myMarker;

    public static List<OwnMarker> listDocuments = new ArrayList<OwnMarker>();
    public int size=0;
    private final static UserApi userApi=Controller.getApi();
    private NumberOfPosts numberOfPosts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        imageButtonAddNoteMaps = (ImageButton) findViewById(R.id.add_button);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        try {
            boolean success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style_dark));
        } catch (Resources.NotFoundException e) {

        }
        setUpClusterer();
    }



    private void setUpClusterer() {
        mClusterManager = new ClusterManager<OwnMarker>(this, mMap);
        mMap.setOnCameraIdleListener(mClusterManager);
        mClusterManager.setRenderer(new OwnIconRendered(getApplicationContext(), mMap, mClusterManager));
        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<OwnMarker>() {
            @Override
            public boolean onClusterItemClick(OwnMarker ownMarker) {
                Intent myIntent = new Intent(MapsActivity.this, MarkerPreview.class);
                myIntent.putExtra("markerId",ownMarker.getPostId());
                startActivityForResult(myIntent, AppContext.TODO_NOTE_REQUEST);

                return false;
            }
        });
        mMap.setOnMarkerClickListener(mClusterManager);
        addItems();
    }

    private void addItems() {

        // Set some lat/lng coordinates to start with.
//        int info = getIntent().getExtras().getInt("global", 0);
//
//
//        if (info==0) {
//            listDocuments = DBNotes.getNotesAllMy(LoginActivity.myUser.username);
//        }else{
//            listDocuments = DBNotes.getNotesAllPublic();
//        }
        listDocuments = DBPosts.getMarkersAllMy("");

        for (int i=0; i<listDocuments.size(); i++) {
            OwnMarker ownMarker = listDocuments.get(i);
            mClusterManager.addItem(ownMarker);
        }

    }

    public class OwnIconRendered extends DefaultClusterRenderer<OwnMarker>{

        private final myIconGenerator mIconGenerator;
        private boolean shouldCluster = true;
        private static final int MIN_CLUSTER_SIZE = 1;
        private float mDensity;
        private ShapeDrawable mColoredCircleBackground;
        private SparseArray<BitmapDescriptor> mIcons = new SparseArray();


        public OwnIconRendered(Context context, GoogleMap map, ClusterManager<OwnMarker> clusterManager) {
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

        protected void onBeforeClusterRendered(Cluster<OwnMarker> cluster, MarkerOptions markerOptions) {
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


        public class myIconGenerator{

            private Context mContext;
            private ViewGroup mContainer;
            private RotationLayout mRotationLayout;
            private TextView mTextView;
            private View mContentView;
            private int mRotation;
            private float mAnchorU = 0.5F;
            private float mAnchorV = 1.0F;
            private BubbleDrawable mBackground;
            public static final int STYLE_DEFAULT = 1;
            public static final int STYLE_WHITE = 2;
            public static final int STYLE_RED = 3;
            public static final int STYLE_BLUE = 4;
            public static final int STYLE_GREEN = 5;
            public static final int STYLE_PURPLE = 6;
            public static final int STYLE_ORANGE = 7;


            public myIconGenerator(Context context) {
                this.mContext = context;
                this.mBackground = new BubbleDrawable(this.mContext.getResources());
                this.mContainer = (ViewGroup)LayoutInflater.from(this.mContext).inflate(com.google.maps.android.R.layout.amu_text_bubble, (ViewGroup)null);
                this.mRotationLayout = (RotationLayout)this.mContainer.getChildAt(0);
                this.mContentView = this.mTextView = (TextView)this.mRotationLayout.findViewById(com.google.maps.android.R.id.amu_text);
                this.setStyle(1);
            }

            public Bitmap makeIcon(CharSequence text) {
                if (this.mTextView != null) {
                    this.mTextView.setTextColor(R.color.black);
                    this.mTextView.setText(text);
                    this.mTextView.setTextSize(15);

                }

                return this.makeIcon();
            }

            public Bitmap makeIcon() {
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
                this.mContentView = contentView;
                View view = this.mRotationLayout.findViewById(com.google.maps.android.R.id.amu_text);
                this.mTextView = view instanceof TextView ? (TextView)view : null;
            }

            public void setContentRotation(int degrees) {
                this.mRotationLayout.setViewRotation(degrees);
            }

            public void setRotation(int degrees) {
                this.mRotation = (degrees + 360) % 360 / 90;
            }

            public float getAnchorU() {
                return this.rotateAnchor(this.mAnchorU, this.mAnchorV);
            }

            public float getAnchorV() {
                return this.rotateAnchor(this.mAnchorV, this.mAnchorU);
            }

            private float rotateAnchor(float u, float v) {
                switch(this.mRotation) {
                    case 0:
                        return u;
                    case 1:
                        return 1.0F - v;
                    case 2:
                        return 1.0F - u;
                    case 3:
                        return v;
                    default:
                        throw new IllegalStateException();
                }
            }

            public void setTextAppearance(Context context, int resid) {
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

            public void setContentPadding(int left, int top, int right, int bottom) {
                this.mContentView.setPadding(left, top, right, bottom);
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



//        @Override
//        protected void onBeforeClusterItemRendered(ToDoDocuments item, MarkerOptions markerOptions) {
////            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ellipse));
//            mClusterManager.setOnClusterClickListener();
//        }


//        @Override
//        protected void onBeforeClusterRendered(Cluster<ToDoDocuments> cluster, MarkerOptions markerOptions) {
//            Bitmap bitmapImg = BitmapFactory.decodeResource(getResources(), R.drawable.ellipse);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                if (cluster.getSize()!=0) {
//                    bitmapImg= Bitmap.createScaledBitmap(bitmapImg, (int) (bitmapImg.getWidth()*Math.log(cluster.getSize())/3),
//                            (int) (bitmapImg.getHeight()*Math.log(cluster.getSize()))/3, false);
//                }
//
//            }
//            BitmapDescriptor img = BitmapDescriptorFactory.fromBitmap(bitmapImg);
//            markerOptions.icon(img);
//        }


//        @Override
//        protected void onClusterRendered(Cluster<OwnMarker> cluster, Marker marker) {
//
//
//        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster<OwnMarker> cluster) {

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

    public void onClickAddNoteMaps(View view){
        ToDoDocuments toDoDocuments = new ToDoDocuments();
        showDocuments(toDoDocuments);
    }

    private void showDocuments(ToDoDocuments toDoDocuments){
        imageButtonAddNoteMaps.setVisibility(View.GONE);
        Intent myIntent = new Intent(this, Note.class);
        myIntent.putExtra(AppContext.TODO_DOCUMENT,toDoDocuments);
        startActivityForResult(myIntent, AppContext.TODO_NOTE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode ,Intent data){
        imageButtonAddNoteMaps.setVisibility(View.VISIBLE);
        if (requestCode == AppContext.TODO_NOTE_REQUEST){
            switch(resultCode){
                case RESULT_CANCELED: {
                    break;
                }
                case Note.RESULT_SAVE: {
                    final ToDoDocuments toDoDocuments =  data.getParcelableExtra(AppContext.TODO_DOCUMENT);
                    final OwnMarker ownMarker = new OwnMarker();
                    ownMarker.setNumber(""+toDoDocuments.getNumber());
                    ownMarker.setPostId(toDoDocuments.getId());
                    ownMarker.setLocation(toDoDocuments.getLocation());
                    ownMarker.setAccountId(toDoDocuments.getAccountId());
                    ownMarker.setAccess(toDoDocuments.getAccess()+"");
                    ownMarker.setId(ownMarker.getPostId());

                    if (toDoDocuments.getId().equals("-1")){
                        userApi.getNumberOfPosts("1").enqueue(new Callback<NumberOfPosts>() {
                            @Override
                            public void onResponse(Call<NumberOfPosts> call, Response<NumberOfPosts> response) {
                                numberOfPosts=response.body();
                                numberOfPosts.setSize(String.valueOf(Integer.parseInt(numberOfPosts.getSize())+1));
                                userApi.deleteNumberOfPosts("1").enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        userApi.pushNumberOfPosts(numberOfPosts).enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                toDoDocuments.setId(numberOfPosts.getSize());
                                                ownMarker.setPostId(toDoDocuments.getId());
                                                ownMarker.setId(ownMarker.getPostId());
                                                addDocument(ownMarker);

                                                DBPosts.addMarker(ownMarker);
                                                DBPosts.addPost(toDoDocuments);

                                                mMap.animateCamera(CameraUpdateFactory.zoomIn());

                                            }
                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                Toast.makeText(MapsActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(MapsActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                            @Override
                            public void onFailure(Call<NumberOfPosts> call, Throwable t) {
                                Toast.makeText(MapsActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    else {
                        mClusterManager.removeItem(ownMarker);
                        DBPosts.updatePost(toDoDocuments);
                        DBPosts.updateMarker(ownMarker);
                        mClusterManager.addItem(ownMarker);
                        mMap.animateCamera(CameraUpdateFactory.zoomIn());

                    }
                    break;
                }
                case AppContext.DELETE_POST_REQUEST: {
                    ToDoDocuments toDoDocuments = data.getParcelableExtra(AppContext.TODO_DOCUMENT);
                    OwnMarker ownMarker = data.getParcelableExtra(AppContext.OWN_MARKER);
                    deleteDocument(ownMarker);
                    DBPosts.deletePost(toDoDocuments.getId());
                    DBPosts.deleteMarker(ownMarker.getPostId());
                    mMap.animateCamera(CameraUpdateFactory.zoomIn());
                    break;
                }

                case AppContext.EDIT_POST_REQUEST: {
                    ToDoDocuments toDoDocuments = data.getParcelableExtra(AppContext.TODO_DOCUMENT);
                    OwnMarker ownMarker = data.getParcelableExtra(AppContext.OWN_MARKER);
                    String id =  data.getExtras().getString(AppContext.GET_POST_ID);

                    imageButtonAddNoteMaps.setVisibility(View.GONE);
                    Intent myIntent = new Intent(this, EditPost.class);
                    myIntent.putExtra(AppContext.TODO_DOCUMENT,toDoDocuments);
                    startActivityForResult(myIntent, AppContext.TODO_NOTE_REQUEST);
                    mMap.animateCamera(CameraUpdateFactory.zoomIn());
                    break;
                }

                default:
                    break;

            }
        }
    }

    private void addDocument(OwnMarker ownMarker){
        if (Integer.parseInt(ownMarker.getNumber())==-1) {
            ownMarker.setNumber(listDocuments.size()+"");
            listDocuments.add(ownMarker);

        }   else {
            listDocuments.set(Integer.parseInt(ownMarker.getNumber()), ownMarker);
        }
        mClusterManager.addItem(ownMarker);
    }

    public void deleteDocument(OwnMarker ownMarker){
        boolean f =listDocuments.remove(ownMarker);
        mClusterManager.removeItem(ownMarker);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        listDocuments.clear();
    }


}
