package cn.kuailaimei.store.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.entity.ManagerItem;
import cn.kuailaimei.store.common.ListAdapter;
import cn.kuailaimei.store.utils.ViewHolderUtil;

/**
 * Created by ymh on 2016/7/8 0008.
 */
public class ManagerAdapter extends ListAdapter<ManagerItem>{
    public ManagerAdapter(Context context, List<ManagerItem> items) {
        super(context);
        mList = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_manager,null);
        }
        ManagerItem item = mList.get(position);
        ImageView managericoniv = ViewHolderUtil.get(convertView,R.id.manager_icon_iv);
        TextView managernametv = ViewHolderUtil.get(convertView,R.id.manager_name_tv);
        if(item != null){
            managernametv.setText(item.getName());
            managericoniv.setImageResource(item.getIconResId());
        }
        return convertView;
    }

}
