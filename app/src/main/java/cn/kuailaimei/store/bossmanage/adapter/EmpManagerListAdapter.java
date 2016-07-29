package cn.kuailaimei.store.bossmanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.mvc.IDataAdapter;

import java.util.List;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.entity.EmpItem;
import cn.kuailaimei.store.common.ListAdapter;
import cn.kuailaimei.store.utils.ViewHolderUtil;

/**
 * Created by ymh on 2016/7/9 0009.
 */
public class EmpManagerListAdapter extends ListAdapter<EmpItem> implements IDataAdapter<List<EmpItem>> {

    public EmpManagerListAdapter(Context context, List<EmpItem> empItems) {
        super(context);
        mList = empItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_emp,null);
        }
        EmpItem item = mList.get(position);
        TextView empnametv = ViewHolderUtil.get(convertView,R.id.emp_name_tv);
        TextView positiontv = ViewHolderUtil.get(convertView,R.id.position_tv);
        TextView phonenumtv = ViewHolderUtil.get(convertView,R.id.phone_num_tv);

        if(item != null){
            empnametv.setText(item.getAlias());
            positiontv.setText(item.getPosition());
            phonenumtv.setText(item.getMobile());
        }
        return convertView;
    }

    @Override
    public void notifyDataChanged(List<EmpItem> empItemList, boolean isRefresh) {
        if (isRefresh) {
            mList.clear();
        }
        mList.addAll(empItemList);
        notifyDataSetChanged();
    }

    @Override
    public List<EmpItem> getData() {
        return mList;
    }
}
