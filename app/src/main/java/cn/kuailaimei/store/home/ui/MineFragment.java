package cn.kuailaimei.store.home.ui;

import android.view.View;
import android.widget.LinearLayout;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.common.User;
import cn.kuailaimei.store.common.ui.BaseFragment;
import cn.kuailaimei.store.mine.ui.AboutUsActivity;
import cn.kuailaimei.store.mine.ui.FeedBackActivity;
import cn.kuailaimei.store.mine.ui.MyAccountActivity;
import cn.kuailaimei.store.mine.ui.SettingActivity;

/**
 * Created by ymh on 2016/7/6 0006.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout myaccountlayout;
    private LinearLayout aboutuslayout;
    private LinearLayout feedbacklayout;
    private LinearLayout settinglayout;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);

        myaccountlayout = (LinearLayout) contentView.findViewById(R.id.my_account_layout);
        aboutuslayout = (LinearLayout) contentView.findViewById(R.id.about_us_layout);
        feedbacklayout = (LinearLayout) contentView.findViewById(R.id.feed_back_layout);
        settinglayout = (LinearLayout) contentView.findViewById(R.id.setting_layout);

        myaccountlayout.setOnClickListener(this);
        aboutuslayout.setOnClickListener(this);
        feedbacklayout.setOnClickListener(this);
        settinglayout.setOnClickListener(this);

        if(User.get().isBoss()){
            myaccountlayout.setVisibility(View.VISIBLE);
        }else{
            myaccountlayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_account_layout:
                //TODO 我的账户
                MyAccountActivity.launch(getActivity());
                break;
            case R.id.about_us_layout:
                //TODO 关于我们
                AboutUsActivity.launch(getActivity());
                break;
            case R.id.feed_back_layout:
                //TODO 意见反馈
                FeedBackActivity.launch(getActivity());
                break;
            case R.id.setting_layout:
                //TODO 系统设置
                SettingActivity.launch(getActivity());
                break;
        }
    }
}
