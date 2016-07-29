package cn.kuailaimei.store.bossmanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.mvc.IDataAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.entity.TransItem;
import cn.kuailaimei.store.common.ListAdapter;
import cn.kuailaimei.store.utils.ViewHolderUtil;

/**
 * Created by ymh on 2016/7/9 0009.
 */
public class DetailAccountAdapter extends ListAdapter<TransItem>implements IDataAdapter<List<TransItem>> {

    public DetailAccountAdapter(Context context, ArrayList<TransItem> transItems) {
        super(context);
        mList = transItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_detail_account,null);
        }
        TransItem item = mList.get(position);
        TextView detailaccountnametv = ViewHolderUtil.get(convertView,R.id.detail_account_name_tv);
        TextView detailaccountdatetv = ViewHolderUtil.get(convertView,R.id.detail_account_date_tv);
        TextView detailaccountmoneytv = ViewHolderUtil.get(convertView,R.id.detail_account_money_tv);
        if(item != null){
            detailaccountnametv.setText(item.getRemark());
            detailaccountdatetv.setText(item.getCreateTime());
            if(item.getTType() == 1) {
                detailaccountmoneytv.setTextColor(mContext.getResources().getColor(R.color.textColorPink));
                detailaccountmoneytv.setText("+"+item.getAmont());
            }else{
                detailaccountmoneytv.setTextColor(mContext.getResources().getColor(R.color.textColorSecondary));
                detailaccountmoneytv.setText("-"+item.getAmont());
            }
        }
        return convertView;
    }

    @Override
    public void notifyDataChanged(List<TransItem> transItemList, boolean isRefresh) {
        if (isRefresh) {
            mList.clear();
        }
        mList.addAll(transItemList);
        notifyDataSetChanged();
    }

    @Override
    public List<TransItem> getData() {
        return mList;
    }

}
