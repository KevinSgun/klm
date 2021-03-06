package cn.kuailaimei.store.common.ui;

import android.view.View;

import cn.kuailaimei.store.common.widget.CustomToolBar;
import cn.kuailaimei.store.common.widget.ToolBarHelper;

/**
 * Created by lu on 2016/6/14.
 */
public abstract class AppBarActivity extends BaseActivity {
    public CustomToolBar toolbar;

    @Override
    protected void setContentView() {
        ToolBarHelper toolBarHelper = new ToolBarHelper(this, getContentViewId());
        toolbar = toolBarHelper.getToolBar();
        initToolBarEvent();
        setContentView(toolBarHelper.getContentView());
        /*把 toolbar 设置到Activity 中*/
        setSupportActionBar(toolbar);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        toolbar.setTitleText(title);
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        toolbar.setTitle(titleId);
    }

    private void initToolBarEvent() {
        toolbar.setBtnLeftClick(leftOnClickListener);
        toolbar.setBtnRightClick(rightOnClickListener);
    }

    protected void setBarTitle(int titleId){
        toolbar.setTitle(titleId);
    }

    protected void setRightText(int titleId){
        toolbar.setRightText(titleId);
    }

    protected void setRightText(String title){
        toolbar.setRightText(title);
    }

    protected void setRightTextVisiable(int visiable){
        toolbar.setRightVisible(visiable);
    }

    protected void setRightImg(int imgId){
        toolbar.setBtnRight(imgId);
    }

    protected void setLeftImg(int imgId){
        toolbar.setBtnLeft(imgId);
    }

    protected abstract int getContentViewId();

    protected void onActionBarItemClick(int position){
        if(position == ToolBarHelper.ITEM_LEFT)
            back();
    }

    public View.OnClickListener leftOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onActionBarItemClick(ToolBarHelper.ITEM_LEFT);
        }
    };

    public View.OnClickListener rightOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onActionBarItemClick(ToolBarHelper.ITEM_RIGHT);
        }
    };
    protected void back() {
        try {
            onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        toolbar.destroyView();
        super.onDestroy();
    }
}
