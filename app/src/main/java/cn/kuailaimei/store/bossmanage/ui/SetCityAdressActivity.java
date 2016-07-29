package cn.kuailaimei.store.bossmanage.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zitech.framework.utils.ViewUtils;

import java.util.ArrayList;

import cn.kuailaimei.store.Constants;
import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.entity.AreaEntity;
import cn.kuailaimei.store.common.ui.AppBarActivity;

public class SetCityAdressActivity extends AppBarActivity {
	private ListView arealv;
	private ArrayList<AreaEntity> lt = null;
	private String pname;
	private boolean isNeedArea;

	@Override
	protected void initView() {
		setTitle("选择城市");
		// userInfo = getSharedPreferences("user_info", 0);
		Intent city = getIntent();
		// lt = (ArrayList<AreaEntity>) city.getSerializableExtra("list");

		//获取城市列表信息，城市列表是通过省份关联
		isNeedArea = city.getBooleanExtra(Constants.ActivityExtra.IS_NEED_AREA, false);
		lt = city.getParcelableArrayListExtra("list");
		pname = city.getStringExtra("pname");
		arealv = (ListView) findViewById(R.id.userinfo_arealist);
	}

	@Override
	protected void initData() {
		arealv.setAdapter(new myAreaListAdapter());
		arealv.setOnItemClickListener(new MyEaraList());
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_set_address;
	}

	class myAreaListAdapter extends BaseAdapter {

		public int getCount() {
			return lt.size();
		}

		public Object getItem(int arg0) {
			return lt.get(arg0);
		}

		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null){
				TextView tv = new TextView(SetCityAdressActivity.this);
				int height = ViewUtils.getDimenPx(R.dimen.h90);
				int padding = ViewUtils.getDimenPx(R.dimen.w20);
				int textSize = ViewUtils.getDimenPx(R.dimen.w28);
				AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height);
				tv.setLayoutParams(params);
				tv.setPadding(padding*2,padding,padding,padding);
				tv.setTextColor(getResources().getColor(R.color.textColorPrimary));
				tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
				tv.setGravity(Gravity.CENTER_VERTICAL);
				convertView = tv;
			}
			TextView city = (TextView) convertView;
			city.setText(lt.get(position).getCname());
			return convertView;
		}

	}

	class MyEaraList implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// String city = lt.get(arg2).getCname();
			AreaEntity areaList = lt.get(arg2);
			if (areaList != null && pname != null) {
				Intent intent = new Intent();
				intent.putExtra("pname", pname);
				intent.putExtra("cname", areaList.getCname());
				if(isNeedArea){
					intent.setClass(SetCityAdressActivity.this,SetAreaAdressActivity.class);
					intent.putExtra("arealist", areaList);
					overridePendingTransition(R.anim.right_in,R.anim.right_out);
					startActivityForResult(intent, Constants.ActivityExtra.SELECT_AREA_NAME);
				}else{
					setResult(Activity.RESULT_OK, intent);
					finish();
				}
			}
		}

	}
	
	public static void luanch(Activity context, ArrayList<AreaEntity> c, String pName, boolean isNeedArea){
		Intent intent = new Intent(context, SetCityAdressActivity.class);
		intent.putParcelableArrayListExtra("list", c);
		intent.putExtra("pname", pName);
		intent.putExtra(Constants.ActivityExtra.IS_NEED_AREA, isNeedArea);
		context.overridePendingTransition(R.anim.right_in,R.anim.right_out);
		context.startActivityForResult(intent, Constants.ActivityExtra.SELECT_CITY_NAME);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		if (requestCode == Constants.ActivityExtra.SELECT_AREA_NAME) {
			String pname = data.getStringExtra("pname");
			String cname = data.getStringExtra("cname");
			String aname = data.getStringExtra("aname");
			Intent intent = new Intent();
			intent.putExtra("pname", pname);
			intent.putExtra("cname", cname);
			intent.putExtra("aname", aname);
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
	}
}
