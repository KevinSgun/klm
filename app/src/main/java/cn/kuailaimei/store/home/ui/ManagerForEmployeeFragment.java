package cn.kuailaimei.store.home.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zitech.framework.data.network.entity.Basic;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.response.FileUploadResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.transform.CropCircleTransformation;
import com.zitech.framework.utils.LogUtils;
import com.zitech.framework.widget.LoadingDialog;
import com.zitech.framework.widget.RemoteImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.ApiFactory;
import cn.kuailaimei.store.api.entity.EditData;
import cn.kuailaimei.store.api.entity.ImageWorks;
import cn.kuailaimei.store.api.request.IdRequest;
import cn.kuailaimei.store.api.request.ImagesRequest;
import cn.kuailaimei.store.api.request.NullDataRequest;
import cn.kuailaimei.store.api.request.Request;
import cn.kuailaimei.store.api.request.UpdateProfileRequest;
import cn.kuailaimei.store.api.response.FilePathResponse;
import cn.kuailaimei.store.bossmanage.ui.OrderManagerActivity;
import cn.kuailaimei.store.common.User;
import cn.kuailaimei.store.common.event.EventFactory;
import cn.kuailaimei.store.common.ui.EditActivity;
import cn.kuailaimei.store.common.ui.PhotoGridActivity;
import cn.kuailaimei.store.common.ui.PhotoPickingFragment;
import cn.kuailaimei.store.common.vo.Photo;
import cn.kuailaimei.store.common.widget.OnRippleCompleteListener;
import cn.kuailaimei.store.common.widget.PhotoGridView;
import cn.kuailaimei.store.common.widget.RippleLinearLayout;
import cn.kuailaimei.store.pushmsg.ui.MessageActivity;
import cn.kuailaimei.store.utils.ToastMaster;
import rx.functions.Action1;

/**
 * Created by ymh on 2016/7/6 0006.
 */
public class ManagerForEmployeeFragment extends PhotoPickingFragment implements OnRippleCompleteListener, View.OnClickListener {
    private TextView actionbartitle;
    private TextView msgcounttv;
    private RippleLinearLayout msglayout;
    private LinearLayout ordermanagerlayout;
    private RemoteImageView avatariv;
    private LinearLayout chooseavatarlayout;
    private TextView inputsignaturetv;
    private LinearLayout profilenicknamelayout;
    private String avatarUrl;
    private ImageView rightarrowiv;
    private PhotoGridView postphotolayout;
    private ArrayList<Photo> selectedPhotos;
    private static final int REQUEST_FOR_CHOOSE_PHOTOS = 200;
    private static final int AVATAR = 1;
    private static final int WORKS = 2;
    private int chooseType;
    private static final int UPLOAD = 11;
    private static final int FINISH = 22;
    private int POSITION;
    private int SIZE;
    boolean isCancel = false;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == UPLOAD) {
                UploadMessage uMsg = (UploadMessage) msg.obj;
                uploadPic(uMsg.imagPath);
            } else if (msg.what == FINISH) {
                canUpload = true;
                loadingDialog.dismiss();
                LogUtils.i("上传--结束--第" + debugPosition + "张---------");
                ToastMaster.shortToast("上传成功");
            }

            return false;
        }
    });
    private LoadingDialog loadingDialog;
    public boolean canUpload;
    public boolean isDestroy;
    private int debugPosition;//调试用

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_for_emp;
    }

    @Override
    protected void onPreInflate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onPreInflate(inflater, container, savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void MainThreadEditeData(final EventFactory.EditContent data) {
        UpdateProfileRequest profileRequest = new UpdateProfileRequest();
        if (!TextUtils.isEmpty(data.editContent))
            profileRequest.setSignature(data.editContent);
        Request request = new Request(profileRequest);
        request.sign();
        ApiFactory.updateProfile(request).subscribe(new Action1<ApiResponse>() {
            @Override
            public void call(ApiResponse apiResponse) {
                Basic basic = apiResponse.getBasic();
                ToastMaster.shortToast(basic.getMsg());
                inputsignaturetv.setText(data.editContent);
                User.get().storeSignature(data.editContent);
                User.get().notifyChange();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });

    }

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        actionbartitle = (TextView) contentView.findViewById(R.id.action_bar_title);
        msgcounttv = (TextView) contentView.findViewById(R.id.msg_count_tv);
        msglayout = (RippleLinearLayout) contentView.findViewById(R.id.msg_layout);
        ordermanagerlayout = (LinearLayout) contentView.findViewById(R.id.order_manager_layout);
        avatariv = (RemoteImageView) contentView.findViewById(R.id.avatar_iv);
        chooseavatarlayout = (LinearLayout) contentView.findViewById(R.id.choose_avatar_layout);
        inputsignaturetv = (TextView) contentView.findViewById(R.id.input_signature_et);
        profilenicknamelayout = (LinearLayout) contentView.findViewById(R.id.profile_signature_layout);
        rightarrowiv = (ImageView) contentView.findViewById(R.id.right_arrow_iv);
        postphotolayout = (PhotoGridView) contentView.findViewById(R.id.post_photo_layout);

        postphotolayout.setOnAddButtonClickedListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                chooseType = WORKS;
                requestTakePhoto(getString(R.string.personal_works), EFFECT_TYPE_NONE);
            }
        });
        postphotolayout.setOnDeleteListener(new PhotoGridView.OnDeleteListener() {
            @Override
            public void delete(Photo photo) {
                deleteWork(photo);
            }
        });

        msglayout.setOnRippleCompleteListener(this);
        chooseavatarlayout.setOnClickListener(this);
        ordermanagerlayout.setOnClickListener(this);
        profilenicknamelayout.setOnClickListener(this);
        avatariv.setBitmapTransformation(new CropCircleTransformation(getActivity()));
    }

    private void deleteWork(Photo photo) {
        IdRequest idRequest = new IdRequest();
        idRequest.setId(photo.id);
        Request request = new Request(idRequest);
        request.sign();
        ApiFactory.deleteMyWorks(request).subscribe(new ProgressSubscriber<ApiResponse>(this) {
            @Override
            protected void onNextInActive(ApiResponse apiResponse) {
                ToastMaster.shortToast(apiResponse.getBasic().getMsg());
            }
        });
    }

    @Override
    public void onPrepareData() {
        super.onPrepareData();
        if (!TextUtils.isEmpty(User.get().getPortrait())) {
            avatariv.setImageUri(R.mipmap.ic_avatar_def_b, User.get().getPortrait());
        }
        if (!TextUtils.isEmpty(User.get().getSignature())) {
            rightarrowiv.setVisibility(View.GONE);
            inputsignaturetv.setText(User.get().getSignature());
        } else {
            rightarrowiv.setVisibility(View.VISIBLE);
        }
        requestMyWorks();
    }

    private void requestMyWorks() {
        ApiFactory.getMyWorks(new Request(new NullDataRequest())).subscribe(new Action1<ApiResponse<List<ImageWorks>>>() {
            @Override
            public void call(ApiResponse<List<ImageWorks>> listApiResponse) {
                postphotolayout.getPhotos().clear();
                List<Photo> photoList = new ArrayList<Photo>();
                for (ImageWorks image : listApiResponse.getData()) {
                    Photo photo = new Photo();
                    photo.id = String.valueOf(image.getId());
                    photo.imgPath = image.getUrl();
                    photo.thumbPath = image.getUrl();
                    photo.isNetUrl = 1;
                    photoList.add(photo);
                }
                postphotolayout.setAll(photoList);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public void onComplete(View v) {
        //TODO 进入消息列表页面
        MessageActivity.launch(getActivity());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_avatar_layout:
                chooseType = AVATAR;
                requestTakePhoto("用户头像", EFFECT_TYPE_CUT);
                break;
            case R.id.order_manager_layout:
                OrderManagerActivity.launch(getActivity(), "我的订单");
                break;
            case R.id.profile_signature_layout:
                EditData editData = new EditData();
                editData.setTitleResId(R.string.edit_signature);
                editData.setHintResId(R.string.edit_signature_hint);
                if (!TextUtils.isEmpty(User.get().getSignature()))
                    editData.setContentStr(User.get().getSignature());
                editData.setLimit(30);
                editData.setLimitTips("个性签名长度不能超过30个字符");
                EditActivity.launch(getActivity(), editData);
                break;
        }
    }

    @Override
    protected void onPhotoCut(String picturePath, final String cutPicturePath) {
        super.onPhotoCut(picturePath, cutPicturePath);
        File file = new File(cutPicturePath);
        ApiFactory.upload("2", file).subscribe(new ProgressSubscriber<FileUploadResponse<FilePathResponse>>(this) {
            @Override
            protected void onNextInActive(FileUploadResponse<FilePathResponse> apiResponse) {
                FilePathResponse response = apiResponse.getData();
                if (response.getImgSrc() != null && response.getImgSrc().size() > 0) {
                    avatarUrl = response.getImgSrc().get(0);
                    avatariv.setImageUri(cutPicturePath);
                    UpdateProfileRequest profileRequest = new UpdateProfileRequest();
                    if (!TextUtils.isEmpty(avatarUrl))
                        profileRequest.setPortrait(avatarUrl);
                    Request request = new Request(profileRequest);
                    request.sign();
                    ApiFactory.updateProfile(request).subscribe(new ProgressSubscriber<ApiResponse>(ManagerForEmployeeFragment.this) {
                        @Override
                        protected void onNextInActive(ApiResponse apiResponse) {
                            Basic basic = apiResponse.getBasic();
                            if (basic.getStatus() == 1) {
                                if (!TextUtils.isEmpty(avatarUrl))
                                    User.get().storePortrait(avatarUrl);
                                User.get().notifyChange();
                            }

                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onPhotoTaked(String picturePath) {
        Photo photo = new Photo();
        photo.id = String.valueOf(System.currentTimeMillis());
        photo.imgPath = picturePath;
        photo.thumbPath = picturePath;
        photo.compressedPath = picturePath;
        postphotolayout.addPhoto(photo);
        uploadPics();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        if (requestCode == REQUEST_FOR_CHOOSE_PHOTOS) {
            selectedPhotos = data.getParcelableArrayListExtra(PhotoGridActivity.RES_PHOTO_LIST);// ()
            postphotolayout.setAll(selectedPhotos);
            uploadPics();
//            upLoadMutiPics();
        }
    }

//    void upLoadMutiPics(){
//        final ArrayList<Photo> photoArrayList = postphotolayout.getPhotos();
//        if (photoArrayList != null && photoArrayList.size() > 0) {
//            final int size = photoArrayList.size();
//            List<File> fileList = new ArrayList<>();
//            for (int i = 0; i < size; i++) {
//                Photo photo = photoArrayList.get(i);
//                fileList.add(new File(photo.compressedPath));
//            }
//            ApiFactory.upload("3", fileList).subscribe(new ProgressSubscriber<FileUploadResponse<FilePathResponse>>(this) {
//                @Override
//                protected void onNextInActive(FileUploadResponse<FilePathResponse> responseFileUploadResponse) {
//
//                }
//            });
//        }
//    }

    public void uploadPics() {
        final ArrayList<Photo> photoArrayList = postphotolayout.getPhotos();
        if (photoArrayList != null && photoArrayList.size() > 0) {

            final int size = photoArrayList.size();

            SIZE = size;
            loadingDialog = LoadingDialog.newInstance();
            loadingDialog.setOnDialogCancelListener(new LoadingDialog.OnDialogCancelListener() {
                @Override
                public void onCancel() {
                    isCancel = true;
                }
            });
            loadingDialog.show(getFragmentManager(), "upload");
            canUpload = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < size; i++) {
                        POSITION = i;
                        Photo photo = photoArrayList.get(i);
                        if (photo.isNetUrl != 1) {
                            UploadMessage uMsg = new UploadMessage();
                            uMsg.imagPath = photo.compressedPath;
                            final Message message = mHandler.obtainMessage();
                            message.obj = uMsg;
                            message.what = UPLOAD;
                            if (canUpload && !isDestroy&&!isCancel) {
                                debugPosition = POSITION;
                                LogUtils.i("上传--发送--第" + debugPosition + "张---------");
                                canUpload = false;
                                mHandler.sendMessage(message);
                                while (!canUpload && !isDestroy&&!isCancel) {
                                    try {
                                        LogUtils.i("上传--等待--第" + debugPosition + "张---------");
                                        Thread.sleep(200);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                    LogUtils.i("上传--线程结束--第" + POSITION + "个位置---------");
                }
            }).start();

        }
    }

    private void uploadPic(final String imgPath) {
        LogUtils.i("上传--开始--第" + debugPosition + "张---------");
        ApiFactory.upload("3", new File(imgPath)).subscribe(new Action1<FileUploadResponse<FilePathResponse>>() {
            @Override
            public void call(FileUploadResponse<FilePathResponse> apiResponse) {
                FilePathResponse response = apiResponse.getData();
                if (response.getImgSrc() != null && response.getImgSrc().size() > 0) {
                    ImagesRequest imagesRequest = new ImagesRequest();
                    imagesRequest.setImages(response.getImgSrc());
                    final Request request = new Request(imagesRequest);
                    request.sign();
                    ApiFactory.uploadMyWorks(request).subscribe(new ProgressSubscriber<ApiResponse>(ManagerForEmployeeFragment.this) {
                        @Override
                        protected void onNextInActive(ApiResponse apiResponse) {
                            canUpload = true;
                            if(POSITION==SIZE-1){
                                Message message = mHandler.obtainMessage();
                                message.what = FINISH;
                                mHandler.sendMessage(message);
                                requestMyWorks();
                            }
                            LogUtils.i("上传--成功--第" + debugPosition + "张---------");
                        }
                    });
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                canUpload = true;
                ToastMaster.shortToast(throwable.getMessage());
                throwable.printStackTrace();
            }
        });
    }

    public static class UploadMessage {
        public String imagPath;
    }

    @Override
    public void onItemClick(int itemId) {
        if (chooseType == AVATAR) {
            super.onItemClick(itemId);
            return;
        }
        switch (itemId) {
            case 1:
                startChoosePhotoActivity();
                break;
            case 0:
                takePhoto();
                break;

            default:
                break;
        }
    }

    private void startChoosePhotoActivity() {
        Intent intent = new Intent(getActivity(), PhotoGridActivity.class);
        intent.putExtra(PhotoGridActivity.INTENT_CHOOSE_RECENTLY, true);
        intent.putExtra(PhotoGridActivity.SELECTED_PHOTO_LIST, postphotolayout.getPhotos());
        startActivityForResult(intent, REQUEST_FOR_CHOOSE_PHOTOS);// (intent);
    }


    @Override
    public void onDestroy() {
        isDestroy = true;
        isCancel = true;
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
