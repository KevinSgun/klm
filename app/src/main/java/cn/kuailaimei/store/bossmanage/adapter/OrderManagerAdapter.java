package cn.kuailaimei.store.bossmanage.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shizhefei.mvc.IDataAdapter;
import com.zitech.framework.data.network.IContext;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.transform.RoundedCornersTransformation;
import com.zitech.framework.utils.ViewUtils;
import com.zitech.framework.widget.RemoteImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.ApiFactory;
import cn.kuailaimei.store.api.entity.OrderItem;
import cn.kuailaimei.store.api.request.OrderIdRequest;
import cn.kuailaimei.store.api.request.Request;
import cn.kuailaimei.store.common.ListAdapter;
import cn.kuailaimei.store.common.event.EventFactory;
import cn.kuailaimei.store.common.widget.CommonDialog;
import cn.kuailaimei.store.utils.ToastMaster;
import cn.kuailaimei.store.utils.ViewHolderUtil;

/**
 * Created by ymh on 2016/7/9 0009.
 */
public class OrderManagerAdapter extends ListAdapter<OrderItem> implements IDataAdapter<List<OrderItem>> {

    private boolean isBoss;

    public OrderManagerAdapter(Context context, ArrayList<OrderItem> list, boolean isBoss) {
        super(context);
        mList = list;
        this.isBoss = isBoss;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_order_manager, null);
        }

        final OrderItem item = mList.get(position);
        TextView ordernumtv = ViewHolderUtil.get(convertView, R.id.order_num_tv);
        final TextView orderdatetv = ViewHolderUtil.get(convertView, R.id.order_date_tv);
        TextView orderstatus = ViewHolderUtil.get(convertView, R.id.order_status);
        RemoteImageView shopiconiv = ViewHolderUtil.get(convertView, R.id.shop_icon_iv);
        TextView shopnametv = ViewHolderUtil.get(convertView, R.id.shop_name_tv);
        TextView consumeritemstv = ViewHolderUtil.get(convertView, R.id.consumer_items_tv);
        TextView consumertechniciantv = ViewHolderUtil.get(convertView, R.id.consumer_technician_tv);
        TextView ordertotalmoneytv = ViewHolderUtil.get(convertView, R.id.order_total_money_tv);
        final Button leftbtn = ViewHolderUtil.get(convertView, R.id.left_btn);
        final Button rightbtn = ViewHolderUtil.get(convertView, R.id.right_btn);
        if (item != null) {
            shopiconiv.setBitmapTransformation(new RoundedCornersTransformation(mContext, ViewUtils.getDimenPx(R.dimen.w20)));
            shopiconiv.setImageUri(R.mipmap.ic_shop_def, item.getSIcon());

            ordernumtv.setText("订单编号：" + item.getOrderId());
            orderdatetv.setText("下单时间：" + item.getAddDate());
            orderstatus.setText(item.getMsg());
//            shopnametv.setText(item.getsName());
            consumeritemstv.setText(item.getServiceName());
            consumertechniciantv.setText(item.getDesignerName());
            ordertotalmoneytv.setText(String.format(mContext.getString(R.string.rmb), item.getAmount()));
            final int status = item.getStatus();
            if (status == OrderItem.WAIT_CONFIRM && !isBoss) {
                leftbtn.setVisibility(View.GONE);
                rightbtn.setVisibility(View.VISIBLE);
                rightbtn.setText("结束服务");
            }else{
                leftbtn.setVisibility(View.GONE);
                rightbtn.setVisibility(View.GONE);
            }

            rightbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if (status == OrderItem.WAIT_CONFIRM) {
                        //TODO  结束服务
                       CommonDialog commonDialog = new CommonDialog(mContext,"确定要服务已经完成了吗");
                       commonDialog.setOnPositiveButtonClickListener(new CommonDialog.OnPositiveButtonClickListener() {
                           @Override
                           public void onClick(Dialog dialog) {
                               finifService(item);
                           }
                       });
                      commonDialog.show();
                    }
                }
            });
        }
        return convertView;
    }

    private void finifService(OrderItem item) {
        OrderIdRequest orderIdRequest = new OrderIdRequest();
        orderIdRequest.setOrderId(item.getOrderId());
        ApiFactory.confirmEmpOrder(new Request(orderIdRequest)).subscribe(new ProgressSubscriber<ApiResponse>((IContext) mContext) {
            @Override
            protected void onNextInActive(ApiResponse apiResponse) {
                ToastMaster.shortToast(apiResponse.getBasic().getMsg());
                EventBus.getDefault().post(new EventFactory.OrderDataChange());
            }
        });
    }

    @Override
    public void notifyDataChanged(List<OrderItem> orderItems, boolean isRefresh) {
        if (isRefresh) {
            mList.clear();
        }
        mList.addAll(orderItems);
        notifyDataSetChanged();
    }

    @Override
    public List<OrderItem> getData() {
        return mList;
    }

}
