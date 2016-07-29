package cn.kuailaimei.store.home.ui;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.common.User;
import cn.kuailaimei.store.common.ui.BaseFragment;
import cn.kuailaimei.store.partner.ui.PartnerMainActivity;
import cn.kuailaimei.store.pushmsg.ui.PushMsgActivity;
import cn.kuailaimei.store.turnover.ui.TurnoverMainActivity;

/**
 * Created by ymh on 2016/7/6 0006.
 */
public class HomePageFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout turnoverlayout;
    private LinearLayout pushmsglayout;
    private LinearLayout partnerlayout;
    private TextView actionbartitle;
    private TextView actionbarright;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home_page;
    }

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);

        actionbartitle = (TextView) contentView.findViewById(R.id.action_bar_title);
        turnoverlayout = (LinearLayout) contentView.findViewById(R.id.turnover_layout);
        pushmsglayout = (LinearLayout) contentView.findViewById(R.id.push_msg_layout);
        partnerlayout = (LinearLayout) contentView.findViewById(R.id.partner_layout);

        actionbartitle.setText(R.string.home_page);

        if(User.get().isBoss()){
            pushmsglayout.setVisibility(View.VISIBLE);
            pushmsglayout.setOnClickListener(this);
        }else{
            pushmsglayout.setVisibility(View.GONE);
        }

        turnoverlayout.setOnClickListener(this);
        partnerlayout.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.turnover_layout:
                //TODO 总成交额
                TurnoverMainActivity.launch(getActivity());
                break;
            case R.id.push_msg_layout:
                //TODO 推送消息
                PushMsgActivity.launch(getActivity());
                break;
            case R.id.partner_layout:
                //TODO 我的合伙人
                PartnerMainActivity.launch(getActivity());
                break;
        }
    }
}
