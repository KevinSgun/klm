package cn.kuailaimei.store.common.widget;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.zitech.framework.utils.VersionUtils;
import com.zitech.framework.widget.RemoteImageView;

import java.util.ArrayList;
import java.util.List;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.common.ui.PhotoGridActivity;
import cn.kuailaimei.store.common.vo.Photo;
import cn.kuailaimei.store.utils.ToastMaster;

public class PhotoGridView extends GridLayout implements View.OnClickListener {
    private LayoutTransition mTransition;
    private OnPhotoRemovedListener onPhotoRemovedListener;
    private ArrayList<Photo> currentPhotos = new ArrayList<Photo>();
    private LinearLayout addVoice;
    private ImageView deleteIcon;
    private OnDeleteListener deletListener;

    public PhotoGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public PhotoGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PhotoGridView(Context context) {
        super(context);
        init();
    }

    private OnClickListener onAddBtnClickListener;

    // private String currentRecordPath;

    public void setOnAddButtonClickedListener(OnClickListener onClickListener) {
        this.onAddBtnClickListener = onClickListener;
    }

    @SuppressLint({"InlinedApi", "NewApi"})
    public void init() {
        setRowCount(2);
        setColumnCount(3);
        View imageView = inflate(getContext(), R.layout.item_post_add_picture_btn, this);
        ImageView addImage = (ImageView) imageView.findViewById(R.id.add_image);
        addImage.setOnClickListener(this);

        // deleteIcon.setEnabled(false);
        if (VersionUtils.hasHoneycomb()) {
            mTransition = new LayoutTransition();
            mTransition.setAnimator(LayoutTransition.APPEARING, (mTransition.getAnimator(LayoutTransition.APPEARING)));
            mTransition.setAnimator(LayoutTransition.CHANGE_APPEARING,
                    (mTransition.getAnimator(LayoutTransition.CHANGE_APPEARING)));
            mTransition.setAnimator(LayoutTransition.DISAPPEARING, (mTransition.getAnimator(LayoutTransition.DISAPPEARING)));
            mTransition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING,
                    (mTransition.getAnimator(LayoutTransition.CHANGE_DISAPPEARING)));
            setLayoutTransition(mTransition);
        }


    }

    public void addPhoto(final Photo photo) {
        final View view = inflate(getContext(), R.layout.item_post_picture, null);

        RemoteImageView image = (RemoteImageView) view.findViewById(R.id.item_image);
        image.setScaleType(ScaleType.CENTER_CROP);
        image.setImageUri(R.mipmap.ic_def_pic_b, photo.getThumbUrl());
        int positon = getChildCount() - 1;
        addView(view, positon);
        currentPhotos.add(photo);
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                CommonDialog commonDialog = new CommonDialog(getContext(), "要删除这张照片吗？");
                commonDialog.setNegativeButtonText("删除");
                commonDialog.setPositiveButtonText("取消");
                commonDialog.setOnNegativeButtonClickListener(new CommonDialog.OnNegativeButtonClickListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        removeView(view);
                        currentPhotos.remove(photo);
                        notifyPhotoRemoved(photo);
                        deletListener.delete(photo);
                    }
                });
                commonDialog.show();
            }
        });

    }

    private void notifyPhotoRemoved(Photo photo) {
        if (onPhotoRemovedListener != null)
            onPhotoRemovedListener.onPhotoRemoved(photo);
    }

    public void setOnPhotoRemovedListener(OnPhotoRemovedListener onPhotoRemovedListener) {
        this.onPhotoRemovedListener = onPhotoRemovedListener;
    }

    public ArrayList<Photo> getPhotos() {
        return currentPhotos;
    }

    public boolean hasPhotos() {
        return currentPhotos.size() > 0;
    }

    @Override
    public void onClick(View view) {
        if (currentPhotos.size() == PhotoGridActivity.MAX_SIZE) {
            ToastMaster.popToast(getContext(),
                    String.format(getResources().getString(R.string.image_count_limited), PhotoGridActivity.MAX_SIZE));
            return;
        }
        if (view.getId() == R.id.add_image) {
            if (onAddBtnClickListener != null)
                onAddBtnClickListener.onClick(view);
        }
    }

    public interface OnPhotoRemovedListener {
        public void onPhotoRemoved(Photo photo);
    }

    public void addAll(List<Photo> photos) {
        for (Photo photo : photos) {
            if (!currentPhotos.contains(photo)) {
                addPhoto(photo);
            }
        }
    }

    public void setAll(List<Photo> photos) {
        while (getChildCount() > 1) {
            removeViewAt(0);
        }
        for (Photo photo : currentPhotos) {
            notifyPhotoRemoved(photo);
        }
        currentPhotos.clear();
        for (Photo photo : photos) {
            if (!currentPhotos.contains(photo)) {
                addPhoto(photo);
            }
        }
    }

    public void setOnDeleteListener(OnDeleteListener deletListener){
        this.deletListener = deletListener;
    }

    public interface OnDeleteListener{
        void delete(Photo photo);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//		RecordingView.dimiss((Activity) getContext());

    }

}
