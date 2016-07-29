package cn.kuailaimei.store.partner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shizhefei.mvc.IDataAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.entity.PartnerItem;
import cn.kuailaimei.store.common.ListAdapter;
import cn.kuailaimei.store.utils.ViewHolderUtil;

/**
 * Created by ymh on 2016/7/8 0008.
 */
public class PartnerListAdapter extends ListAdapter<PartnerItem> implements IDataAdapter<List<PartnerItem>> {
    private TextView partnernametv;
    private TextView profittv;
    private LinearLayout cashlayout;

    public PartnerListAdapter(Context context, ArrayList<PartnerItem> partnerItems) {
        super(context);
        mList = partnerItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_partner_list,null);
        }
        PartnerItem item = mList.get(position);
        TextView partnernametv = ViewHolderUtil.get(convertView,R.id.partner_name_tv);
        TextView profittv = ViewHolderUtil.get(convertView,R.id.profit_tv);
        if(item != null){

        }
        return convertView;
    }

    @Override
    public void notifyDataChanged(List<PartnerItem> partnerItems, boolean isRefresh) {
        if (isRefresh) {
            mList.clear();
        }
        mList.addAll(partnerItems);
        notifyDataSetChanged();
    }

    @Override
    public List<PartnerItem> getData() {
        return mList;
    }
}
