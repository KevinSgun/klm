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
import cn.kuailaimei.store.api.entity.TotalOrderCountItem;
import cn.kuailaimei.store.common.ListAdapter;
import cn.kuailaimei.store.utils.ViewHolderUtil;

/**
 * Created by ymh on 2016/7/7 0007.
 */
public class TotalOrderCountAdapter extends ListAdapter<TotalOrderCountItem> implements IDataAdapter<List<TotalOrderCountItem>> {

    public TotalOrderCountAdapter(Context context, ArrayList<TotalOrderCountItem> totalOrderCountItems) {
        super(context);
        mList = totalOrderCountItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_total_order_count,null);
        }

        TotalOrderCountItem item = mList.get(position);

        TextView stafftv = ViewHolderUtil.get(convertView,R.id.staff_tv);
        TextView startenddatetv = ViewHolderUtil.get(convertView,R.id.start_end_date_tv);
        TextView ordercounttv = ViewHolderUtil.get(convertView,R.id.order_count_tv);

        if(item != null){
            stafftv.setText(item.getDesignerName());
            startenddatetv.setText(item.getDate());
            ordercounttv.setText(String.valueOf(item.getCous())+"/单");
        }
        return convertView;
    }

    @Override
    public void notifyDataChanged(List<TotalOrderCountItem> orderCountItemList, boolean isRefresh) {
        if (isRefresh) {
            mList.clear();
        }
        mList.addAll(orderCountItemList);
        notifyDataSetChanged();
    }

    @Override
    public List<TotalOrderCountItem> getData() {
        return mList;
    }

}
