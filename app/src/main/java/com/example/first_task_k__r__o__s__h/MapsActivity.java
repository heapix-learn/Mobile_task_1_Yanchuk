package com.example.first_task_k__r__o__s__h;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.common.api.internal.BackgroundDetector;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import android.graphics.drawable.Drawable;
import com.google.maps.android.ui.IconGenerator;
import com.google.maps.android.ui.RotationLayout;
import com.google.maps.android.ui.SquareTextView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private ClusterManager<ToDoDocuments> mClusterManager;
    private Cluster<ToDoDocuments> chosenCluster;
    private ImageButton imageButtonAddNoteMaps;
    public static int TODO_NOTE_REQUEST=1;
    public static List<ToDoDocuments> listDocuments = new ArrayList<ToDoDocuments>();
    public int size=0;
    private final static UserApi userApi=Controller.getApi();
    private SizeOfAccounts accounts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        imageButtonAddNoteMaps = (ImageButton) findViewById(R.id.add_button);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style_dark));

            if (!success) {
            }
        } catch (Resources.NotFoundException e) {

        }




//        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//
//            // Use default InfoWindow frame
//            @Override
//            public View getInfoWindow(Marker arg0) {
//                return null;
//            }
//
//            // Defines the contents of the InfoWindow
//            @Override
//            public View getInfoContents(Marker arg0) {
//
//                // Getting view from the layout file info_window_layout
//                View v = getLayoutInflater().inflate(R.layout.info_window, null);
//
//                // Getting the snippet from the marker
//                final String snippet = arg0.getSnippet();
//
//                // Getting the snippet from the marker
//                String titlestr = arg0.getTitle();
//
//                String cutchar1= "%#";
//                String cutchar2= "%##";
//                final String types = snippet.substring(0,snippet.indexOf(cutchar1));
//                String vicinitystr = snippet.substring(snippet.indexOf(cutchar1)+2, snippet.indexOf(cutchar2));
//                String base= snippet.substring(snippet.indexOf(cutchar2)+3);
//
//                // Getting reference to the TextView to set latitude
//                final TextView title = (TextView) v.findViewById(R.id.place_title);
//
//                TextView vicinity = (TextView) v.findViewById(R.id.place_vicinity);
//
//                ImageView image = (ImageView) v.findViewById(R.id.place_icon);
//
//                // Setting the latitude
//                title.setText(titlestr);
//
//                vicinity.setText(vicinitystr);
////                String img=getImage(base, types);
////                if (!img.equals("")) {
////
//////                       Picasso.with(MapsActivity.this).load(Uri.parse(img)).resize(250, 250).into(image);
////                    byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
//////
//////                    File file=null;
//////                    Picasso.with(MapsActivity.this).load(file).resize(250, 250).into(image);
////                    image.setImageBitmap(ToDoDocuments.ConvertBase64ToBitmap(img));
//                //}
//
//                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//                    public void onInfoWindowClick(Marker marker) {
//                        Intent intent = new Intent(getApplicationContext(), ViewActivity.class);
//                        intent.putExtra("name", snippet);
//                        intent.putExtra("title", title.getText());
//                        startActivity(intent);
//                    }
//
//                });
//
//                return v;
//            }
//
//        });

        setUpClusterer();
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {

                if (marker.getId()!= null && marker.getSnippet() != null) {
                    String help=marker.getTitle();

                    String id = marker.getSnippet();

                    marker.setSnippet(null);
                    marker.setTitle(null);
                    Intent myIntent = new Intent(MapsActivity.this, MarkerPreview.class);
                    myIntent.putExtra("markerId",id);

                    startActivityForResult(myIntent, TODO_NOTE_REQUEST);
                    marker.showInfoWindow();
                    marker.setSnippet(id);
                    marker.setTitle(help);
                }
                return true;
            }
        });

    }



    private void setUpClusterer() {
        mClusterManager = new ClusterManager<ToDoDocuments>(this, mMap);
        mMap.setOnCameraIdleListener(mClusterManager);
        mClusterManager.setRenderer(new OwnIconRendered(getApplicationContext(), mMap, mClusterManager));
        mMap.setOnMarkerClickListener(mClusterManager);

        addItems_help();
        addItems();
    }

    private void addItems() {
        List<ToDoDocuments> listDocuments= new ArrayList<ToDoDocuments>();

        // Set some lat/lng coordinates to start with.
//        int info = getIntent().getExtras().getInt("global", 0);
//
//
//        if (info==0) {
//            listDocuments = DBNotes.getNotesAllMy(LoginActivity.myUser.username);
//        }else{
//            listDocuments = DBNotes.getNotesAllPublic();
//        }
        listDocuments = DBNotes.getNotesAllMy("");

        for (int i=0; i<listDocuments.size(); i++) {
            ToDoDocuments toDoDocuments = listDocuments.get(i);

            mClusterManager.addItem(toDoDocuments);
        }

    }


    private void addItems_help() {

        // Set some lat/lng coordinates to start with.
        double lat = 51.5145160;
        double lng = -0.1270060;

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 200; i++) {
            double offset = 0.0005;
            lat = lat + offset;
            lng = lng + offset;
            ToDoDocuments offsetItem = new ToDoDocuments();
            offsetItem.setLocation(lat+"/"+lng);
//            mClusterManager.setRenderer(new OwnIconRendered(getApplicationContext(), mMap, mClusterManager));

            mClusterManager.addItem(offsetItem);

        }
    }



    public class OwnIconRendered extends DefaultClusterRenderer<ToDoDocuments>{

        private final myIconGenerator mIconGenerator;
        private boolean shouldCluster = true;
        private static final int MIN_CLUSTER_SIZE = 1;
        private float mDensity;
        private ShapeDrawable mColoredCircleBackground;
        private SparseArray<BitmapDescriptor> mIcons = new SparseArray();


        public OwnIconRendered(Context context, GoogleMap map, ClusterManager<ToDoDocuments> clusterManager) {
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

            outline.getPaint().setColor(Color.argb(50, 176, 179, 20));
            LayerDrawable background = new LayerDrawable(new Drawable[]{outline, this.mColoredCircleBackground});
            int strokeWidth = (int)(this.mDensity * 5.0F);
            background.setLayerInset(1, strokeWidth, strokeWidth, strokeWidth, strokeWidth);
            return background;
        }

        protected void onBeforeClusterRendered(Cluster<ToDoDocuments> cluster, MarkerOptions markerOptions) {
            int bucket = this.getBucket(cluster);
            BitmapDescriptor descriptor = (BitmapDescriptor)this.mIcons.get(bucket);
            if (descriptor == null) {
                this.mColoredCircleBackground.getPaint().setColor(Color.rgb(255, 255, 100));
                descriptor = BitmapDescriptorFactory.fromBitmap(this.mIconGenerator.makeIcon(this.getClusterText(bucket)));
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
                    mTextView.setTextColor(R.color.colorPrimaryDark);
                    this.mTextView.setText(text);
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

                Bitmap r1 = BitmapFactory.decodeResource(getResources(), R.drawable.ellipse);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                    r1= Bitmap.createScaledBitmap(r1, measuredWidth,
                            measuredHeight, false);


            }

                Bitmap r = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888);

                r.eraseColor(0);

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
//        protected void onClusterRendered(Cluster<ToDoDocuments> cluster, Marker marker) {
//
//            Bitmap bitmapImg = BitmapFactory.decodeResource(getResources(), R.drawable.ellipse);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                if (cluster.getSize()!=0) {
//                    bitmapImg= Bitmap.createScaledBitmap(bitmapImg, (int) (bitmapImg.getWidth()*Math.log(cluster.getSize())/3),
//                            (int) (bitmapImg.getHeight()*Math.log(cluster.getSize()))/3, false);
//                }
//
//            }
//
//            BitmapDescriptor img = BitmapDescriptorFactory.fromBitmap(bitmapImg);
//            marker.setIcon(img);
//            marker.setAnchor(0.5f, 0.5f);
//            marker.setTitle(String.valueOf(cluster.getSize()));
//            marker.setInfoWindowAnchor((float) 0.5, (float) 0.5);
//        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster<ToDoDocuments> cluster) {

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
        startActivityForResult(myIntent, TODO_NOTE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode ,Intent data){
        imageButtonAddNoteMaps.setVisibility(View.VISIBLE);
        if (requestCode == TODO_NOTE_REQUEST){
            switch(resultCode){
                case RESULT_CANCELED: {
                    break;
                }
                case Note.RESULT_SAVE: {
                    final ToDoDocuments toDoDocuments =  data.getParcelableExtra(AppContext.TODO_DOCUMENT);

                    if (toDoDocuments.getId().equals("-1")){

                        userApi.getSizeOfNotes("1").enqueue(new Callback<SizeOfAccounts>() {
                            @Override
                            public void onResponse(Call<SizeOfAccounts> call, Response<SizeOfAccounts> response) {
                                addDocument(toDoDocuments);
                                accounts=response.body();
                                accounts.setSize(String.valueOf(Integer.parseInt(accounts.getSize())+1));
                                userApi.deleteSizeOfNotes("1").enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        userApi.pushSizeOfNotes(accounts).enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                toDoDocuments.setId(accounts.getSize());
                                                DBNotes.insertNote(toDoDocuments);
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
                            public void onFailure(Call<SizeOfAccounts> call, Throwable t) {
                                Toast.makeText(MapsActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    else {
                        DBNotes.updateNote(toDoDocuments);
                    }
                    break;
                }
                case Note.RESULT_DELETE: {
                    ToDoDocuments toDoDocuments = data.getParcelableExtra("ToDoDocuments");
                    DBNotes.deleteNote(toDoDocuments);
                    deleteDocument(toDoDocuments);
                    break;
                }

                default:
                    break;

            }
        }
    }

    private void addDocument(ToDoDocuments toDoDocuments){
        if (toDoDocuments.getNumber()==-1) {
            toDoDocuments.setNumber(listDocuments.size());
            listDocuments.add(toDoDocuments);

        }   else {
            listDocuments.set(toDoDocuments.getNumber(), toDoDocuments);
        }
        mClusterManager.addItem(toDoDocuments);
    }

    public void deleteDocument(ToDoDocuments toDoDocuments){
        listDocuments.remove(toDoDocuments);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        listDocuments.clear();
    }


}
