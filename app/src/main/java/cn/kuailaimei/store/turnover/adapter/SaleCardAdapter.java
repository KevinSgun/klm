package cn.kuailaimei.store.turnover.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.mvc.IDataAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.entity.AmountItem;
import cn.kuailaimei.store.common.ListAdapter;
import cn.kuailaimei.store.utils.ViewHolderUtil;

/**
 * Created by ymh on 2016/7/7 0007.
 */
public class SaleCardAdapter extends ListAdapter<AmountItem> implements IDataAdapter<List<AmountItem>> {
    public SaleCardAdapter(Context context, ArrayList<AmountItem> amountItems) {
        super(context);
        mList = amountItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_cash_performance,null);
        }

        AmountItem item = mList.get(position);

        TextView cashtv = ViewHolderUtil.get(convertView,R.id.cash_tv);
        TextView itemcontenttv = ViewHolderUtil.get(convertView,R.id.item_content_tv);
        TextView stafftv = ViewHolderUtil.get(convertView,R.id.staff_tv);
        TextView datetv = ViewHolderUtil.get(convertView,R.id.date_tv);
        TextView orderidtv = ViewHolderUtil.get(convertView,R.id.order_id_tv);

        if(item != null){
            cashtv.setText(String.format(mContext.getString(R.string.rmb),item.getAmount()));
            itemcontenttv.setText(item.getServiceName());
            stafftv.setText(item.getDesignerName());
            datetv.setText(item.getAddTime());
            orderidtv.setText(item.getOrderId());
        }
        return convertView;
    }

    @Override
    public void notifyDataChanged(List<AmountItem> amountItems, boolean isRefresh) {
        if (isRefresh) {
            mList.clear();
        }
        mList.addAll(amountItems);
        notifyDataSetChanged();
    }

    @Override
    public List<AmountItem> getData() {
        return mList;
    }
}
