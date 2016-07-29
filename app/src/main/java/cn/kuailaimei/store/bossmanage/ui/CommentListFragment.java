package cn.kuailaimei.store.bossmanage.ui;

import android.os.Bundle;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.common.ui.BaseFragment;

/**
 * Created by ymh on 2016/7/9 0009.
 */
public class CommentListFragment extends BaseFragment{
    private static final String DATE = "date";
    private String date = "0";
    @Override
    protected int getContentViewId() {
        return R.layout.fragment_comment_list;
    }

    public static CommentListFragment getInstance(String date) {
        Bundle bundle = new Bundle();
        bundle.putString(DATE,date);
        CommentListFragment fragment = new CommentListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
