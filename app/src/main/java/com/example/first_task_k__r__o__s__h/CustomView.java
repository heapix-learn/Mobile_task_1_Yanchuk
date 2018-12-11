package com.example.first_task_k__r__o__s__h;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class CustomView extends FrameLayout {

    private View mRoot;
    private ImageView mImgPhoto;
    private View mBtnClose;

    private Context mContext;

    public CustomView(final Context context) {
        this(context, null);
    }

    public CustomView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {


        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View customView = null;

        if (inflater != null)
            customView = inflater.inflate(R.layout.mygrid_layout, this);

        if (customView == null)
            return;

        mRoot = customView.findViewById(R.id.root);
        mImgPhoto = (ImageView) customView.findViewById(R.id.img_photo);
        mBtnClose = customView.findViewById(R.id.btn_close);
    }

    public View getRoot() {
        return mRoot;
    }

    public ImageView getImgPhoto() {
        return mImgPhoto;
    }

    public View getBtnClose() {
        return mBtnClose;
    }
}