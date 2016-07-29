package cn.kuailaimei.store.bossmanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shizhefei.mvc.IDataAdapter;

import java.util.List;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.entity.ConsumeItem;
import cn.kuailaimei.store.common.ListAdapter;
import cn.kuailaimei.store.utils.ViewHolderUtil;

/**
 * Created by ymh on 2016/7/9 0009.
 */
public class ConsumeItemManagerAdapter extends ListAdapter<ConsumeItem> implements IDataAdapter<List<ConsumeItem>> {

    private TextView consumeritemnametv;
    private TextView consumermoneytv;
    private LinearLayout cashlayout;

    public ConsumeItemManagerAdapter(Context context, List<ConsumeItem> consumeItems) {
        super(context);
        mList = consumeItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_consumer_manager,null);
        }
        ConsumeItem item = mList.get(position);

        TextView consumeritemnametv = ViewHolderUtil.get(convertView,R.id.consumer_item_name_tv);
        TextView consumermoneytv = ViewHolderUtil.get(convertView,R.id.consumer_money_tv);

        if(item!=null){
            consumeritemnametv.setText(item.getName());
            consumermoneytv.setText(String.format(mContext.getResources().getString(R.string.rmb),item.getPrice()));
        }
        return convertView;
    }

    @Override
    public void notifyDataChanged(List<ConsumeItem> storeList, boolean isRefresh) {
        if (isRefresh) {
            mList.clear();
        }
        mList.addAll(storeList);
        notifyDataSetChanged();
    }

    @Override
    public List<ConsumeItem> getData() {
        return mList;
    }
}
