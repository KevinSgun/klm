package cn.kuailaimei.store.home.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.entity.ManagerItem;
import cn.kuailaimei.store.bossmanage.ui.ConsumeItemManagerActivity;
import cn.kuailaimei.store.bossmanage.ui.EmployeeManagerActivity;
import cn.kuailaimei.store.bossmanage.ui.FinancialSettleActivity;
import cn.kuailaimei.store.bossmanage.ui.LookCommentActivity;
import cn.kuailaimei.store.bossmanage.ui.OrderManagerActivity;
import cn.kuailaimei.store.bossmanage.ui.StoreManagerActivity;
import cn.kuailaimei.store.common.ui.BaseFragment;
import cn.kuailaimei.store.common.widget.OnRippleCompleteListener;
import cn.kuailaimei.store.common.widget.RippleLinearLayout;
import cn.kuailaimei.store.home.adapter.ManagerAdapter;
import cn.kuailaimei.store.pushmsg.ui.MessageActivity;

/**
 * Created by ymh on 2016/7/6 0006.
 */
public class ManagerForBossFragment extends BaseFragment implements AdapterView.OnItemClickListener, OnRippleCompleteListener {
    private TextView actionbartitle;
    private RippleLinearLayout msglayout;
    private GridView managergv;
    private String[] managerNames = {
            "店铺管理",
            "员工管理",
            "订单管理",
//            "邀请小伙伴",
            "项目管理",
            "财务结算",
            "查看评论"};
    private int[] managerIcons = {
            R.mipmap.ic_store_manager,
            R.mipmap.ic_employee_manager,
            R.mipmap.ic_order_manager,
//            R.mipmap.ic_invite,
            R.mipmap.ic_item_manager,
            R.mipmap.ic_financial_settlement,
            R.mipmap.ic_look_comment};
    private ArrayList<ManagerItem> items;
    private TextView msgcounttv;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_manager_boss;
    }

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);

        msglayout = (RippleLinearLayout) contentView.findViewById(R.id.msg_layout);
        managergv = (GridView) contentView.findViewById(R.id.manager_gv);
        msglayout = (RippleLinearLayout) contentView.findViewById(R.id.msg_layout);
        msgcounttv = (TextView) contentView.findViewById(R.id.msg_count_tv);

        msglayout.setOnRippleCompleteListener(this);
    }

    @Override
    public void onPrepareData() {
        super.onPrepareData();
        items = new ArrayList<>();
        for(int i = 0;i<managerNames.length;i++){
            ManagerItem item = new ManagerItem();
            item.setName(managerNames[i]);
            item.setIconResId(managerIcons[i]);
            items.add(item);
        }
        managergv.setAdapter(new ManagerAdapter(getActivity(),items));
        managergv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ManagerItem item = items.get(position);
        switch (position){
            case 0://店铺管理
                StoreManagerActivity.launch(getActivity(),item.getName());
                break;
            case 1://员工管理
                EmployeeManagerActivity.launch(getActivity(),item.getName());
                break;
            case 2://订单管理
                OrderManagerActivity.launch(getActivity(),item.getName());
                break;
//            case 3://邀请小伙伴
//                break;
            case 3://项目管理
                ConsumeItemManagerActivity.launch(getActivity(),item.getName());
                break;
            case 4://财务结算
                FinancialSettleActivity.launch(getActivity(),item.getName());
                break;
            case 5://查看评论
                LookCommentActivity.launch(getActivity(),item.getName());
                break;

        }
    }

    @Override
    public void onComplete(View v) {
        //TODO 进入消息列表页面
        MessageActivity.launch(getActivity());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
