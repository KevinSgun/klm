package cn.kuailaimei.store.pushmsg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.entity.Message;
import cn.kuailaimei.store.common.ListAdapter;
import cn.kuailaimei.store.utils.ViewHolderUtil;

/**
 * Created by ymh on 2016/7/24 0024.
 */
public class MessageAdapter extends ListAdapter<Message> {

    private TextView msgdatetv;
    private TextView msgcontenttv;

    public MessageAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_message,null);
        }
        Message item = mList.get(position);
        TextView msgdatetv = ViewHolderUtil.get(convertView,R.id.msg_date_tv);
        TextView msgcontenttv = ViewHolderUtil.get(convertView,R.id.msg_content_tv);
        if(item != null){
            msgdatetv.setText(item.getInTime());
            msgcontenttv.setText(item.getContent());
        }
        return convertView;
    }
}
