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

import java.util.List;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.entity.AreaEntity;
import cn.kuailaimei.store.common.ui.AppBarActivity;

public class SetAreaAdressActivity extends AppBarActivity {
	private ListView arealv;
	private String pname;
//	private SharedPreferences userInfo;
	private AreaEntity areaList;
	private List<String> list;
	private String cname;

	@Override
	protected void initView() {
		setTitle("选择区\\县");
		// userInfo = getSharedPreferences("user_info", 0);
		Intent area = getIntent();
		// lt = (ArrayList<AreaEntity>) city.getSerializableExtra("list");

		//获取地区信息列表areaList
		areaList = area.getParcelableExtra("arealist");
		list = areaList.getAname();
		pname = area.getStringExtra("pname");
		cname = area.getStringExtra("cname");
		arealv = (ListView) this.findViewById(R.id.userinfo_arealist);
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
			return list.size();
		}

		public Object getItem(int arg0) {
			return list.get(arg0);
		}

		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null){
				TextView tv = new TextView(SetAreaAdressActivity.this);
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
			TextView area = (TextView) convertView;
			area.setText(list.get(position));
			return convertView;
		}

	}

	class MyEaraList implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// String city = lt.get(arg2).getCname();
			String area = list.get(arg2);
			if (area != null && pname != null) {
				Intent intent = new Intent();
				intent.putExtra("pname", pname);
				intent.putExtra("aname", area);
				intent.putExtra("cname", cname);
				setResult(Activity.RESULT_OK, intent);
				finish();
				// Intent intent = new Intent();
				// intent.putExtra("pname", pname);
				// intent.putExtra("cname", city);
				// setResult(Activity.RESULT_OK, intent);
				// finish();
			}
		}

	}
}
