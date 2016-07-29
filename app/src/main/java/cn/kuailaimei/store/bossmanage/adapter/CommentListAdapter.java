package cn.kuailaimei.store.bossmanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.mvc.IDataAdapter;
import com.zitech.framework.widget.RemoteImageView;

import java.util.ArrayList;
import java.util.List;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.entity.CommentItem;
import cn.kuailaimei.store.common.ListAdapter;
import cn.kuailaimei.store.utils.ViewHolderUtil;

/**
 * Created by ymh on 2016/7/7 0007.
 */
public class CommentListAdapter extends ListAdapter<CommentItem> implements IDataAdapter<List<CommentItem>> {

    public CommentListAdapter(Context context, ArrayList<CommentItem> commentItems) {
        super(context);
        mList = commentItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_comment_list, null);
        }

        CommentItem item = mList.get(position);

        RemoteImageView useravatariv = ViewHolderUtil.get(convertView, R.id.user_avatar_iv);
        TextView userphonenumtv = ViewHolderUtil.get(convertView, R.id.user_phone_num_tv);
        TextView commentleveltv = ViewHolderUtil.get(convertView, R.id.comment_level_tv);
        TextView itemcontenttv = ViewHolderUtil.get(convertView, R.id.item_content_tv);
        TextView datetv = ViewHolderUtil.get(convertView, R.id.date_tv);

        if (item != null&&!item.isEmpty()) {
            convertView.setVisibility(View.VISIBLE);
            useravatariv.setImageUri(R.mipmap.ic_avatar_def_s, item.getPortrait());
            userphonenumtv.setText(item.getUname());
//            commentleveltv.setText(item.get);
            itemcontenttv.setText(item.getContent());
            datetv.setText(item.getDate());
        }else{
            convertView.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public void notifyDataChanged(List<CommentItem> commentItemList, boolean isRefresh) {
        if (isRefresh) {
            mList.clear();
        }
        mList.addAll(commentItemList);
        notifyDataSetChanged();
    }

    @Override
    public List<CommentItem> getData() {
        return mList;
    }

}
