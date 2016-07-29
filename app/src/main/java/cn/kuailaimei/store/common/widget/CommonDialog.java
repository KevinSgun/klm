package cn.kuailaimei.store.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zitech.framework.widget.ValidDialog;

import cn.kuailaimei.store.R;

/**
 * Created by ymh on 2016/6/30 0030.
 */
public class CommonDialog extends ValidDialog {

    private String mContent;
    private TextView contentView;
    private TextView confirm;
    private TextView cancel;
    private OnPositiveButtonClickListener onPositiveButtonClickListener;
    private OnNegativeButtonClickListener onNegativeButtonClickListener;

    public CommonDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    public CommonDialog(Context context, int theme) {
        super(context, theme);
        init();
    }

    public CommonDialog(Context context, String content) {
        super(context, R.style.CommonDialog);
        mContent = content;
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_simple);
        contentView = (TextView) findViewById(R.id.content);
        contentView.setText(mContent);
        confirm = (TextView) findViewById(R.id.confirm);
        cancel = (TextView) findViewById(R.id.cancel);
        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                if (onPositiveButtonClickListener != null)
                    onPositiveButtonClickListener.onClick(CommonDialog.this);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cancel();
                if(onNegativeButtonClickListener !=null )
                    onNegativeButtonClickListener.onClick(CommonDialog.this);
            }
        });
    }

    public void setPositiveButtonText(String text) {
        confirm.setText(text);
    }

    public void setNegativeButtonText(String text){
        cancel.setText(text);
    }

    public void setOnPositiveButtonClickListener(OnPositiveButtonClickListener onPositiveButtonClickListener) {
        this.onPositiveButtonClickListener = onPositiveButtonClickListener;
    }
    public interface OnPositiveButtonClickListener {
        void onClick(Dialog dialog);
    }

    public void setOnNegativeButtonClickListener(OnNegativeButtonClickListener onNegativeButtonClickListener) {
        this.onNegativeButtonClickListener = onNegativeButtonClickListener;
    }
    public interface OnNegativeButtonClickListener {
        void onClick(Dialog dialog);
    }
}
