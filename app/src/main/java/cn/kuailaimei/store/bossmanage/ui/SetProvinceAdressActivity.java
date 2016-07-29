package cn.kuailaimei.store.bossmanage.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.kuailaimei.store.Constants;
import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.entity.AreaEntity;
import cn.kuailaimei.store.api.entity.CityEntity;
import cn.kuailaimei.store.common.ui.AppBarActivity;

public class SetProvinceAdressActivity extends AppBarActivity {
	private ListView arealist;
	private List<CityEntity> listCentity = new ArrayList<CityEntity>();
	private boolean isNeedArea;

	@Override
	protected void initView() {

		setTitle("选择省份");
		isNeedArea = getIntent().getBooleanExtra(Constants.ActivityExtra.IS_NEED_AREA, true);
		arealist = (ListView) findViewById(R.id.userinfo_arealist);
	}

	@Override
	protected void initData() {
		try {
			ParseXml(this.getAssets().open(isNeedArea?"city_has_area.xml":"city_no_area.xml"));
		} catch (IOException e) {
			Log.e("MainActivity", "onCreate ParseXml error is " + e.getMessage());
		}
		arealist.setAdapter(new myAreaListAdapter(listCentity));
		arealist.setOnItemClickListener(new MyEaraList());
	}

	private List<CityEntity> ParseXml(java.io.InputStream inputStream) {

		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(inputStream, "UTF-8");

			boolean done = false;
			int eventType = parser.getEventType();
			CityEntity centity = null;
			//
			AreaEntity aentity = null;
			while (!done) {
				String name = parser.getName();

				switch (eventType) {

				case XmlPullParser.START_TAG:
					if (name.equals("province")) {
						centity = new CityEntity();
						centity.setPname(parser.getAttributeValue(null, "name"));
					} else if (name.equals("city")) {
						aentity = new AreaEntity();
						aentity.setCname(parser.getAttributeValue(null, "name"));
					} else if (name.equals("area")) {
						aentity.getAname().add(parser.getAttributeValue(null, "name"));
					}
					break;
				case XmlPullParser.END_TAG:
					if (name.equals("city")) {
						centity.getAname().add(aentity);
					}
					if (name.equals("province")) {
						listCentity.add(centity);
					}

					break;
				case XmlPullParser.END_DOCUMENT:
					done = true;
					break;

				}
				if (!done)
					try {
						eventType = parser.next();
					} catch (IOException e) {
					}
			}
		} catch (XmlPullParserException e) {
		}

		return listCentity;
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_set_address;
	}

	class myAreaListAdapter extends BaseAdapter {
		
		private List<CityEntity> listCentity;

		public myAreaListAdapter(List<CityEntity> listCentity ){
			this.listCentity = listCentity;
		}
		
		public int getCount() {
			return listCentity.size();
		}

		public Object getItem(int arg0) {
			return listCentity.get(arg0);
		}

		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null){
				TextView tv = new TextView(SetProvinceAdressActivity.this);
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
			TextView province = (TextView) convertView;
			String pName = listCentity.get(position).getPname();
			province.setText(pName);
			return convertView;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		if(requestCode == Constants.ActivityExtra.SELECT_CITY_NAME){
			String pname = data.getStringExtra("pname");
			String cname = data.getStringExtra("cname");
			Intent intent = new Intent();
			if(isNeedArea){
				String aname = data.getStringExtra("aname");
				intent.putExtra("aname", aname);
			}
			intent.putExtra("pname", pname);
			intent.putExtra("cname", cname);
			setResult(Activity.RESULT_OK, intent);
			finish();
		}

	}

	class MyEaraList implements OnItemClickListener {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			ArrayList<AreaEntity> c = (ArrayList<AreaEntity>) listCentity.get(arg2).getAname();
			String pName = listCentity.get(arg2).getPname();
			SetCityAdressActivity.luanch(SetProvinceAdressActivity.this, c, pName,isNeedArea);
		}
	}
	
	/***
	 * 
	 * @param context
	 * @param isNeedArea  是否需要返回地区信息
	 */
	public static void luanchForResult(Activity context, boolean isNeedArea){
		Intent intent = new Intent(context, SetProvinceAdressActivity.class);
		intent.putExtra(Constants.ActivityExtra.IS_NEED_AREA, isNeedArea);
		context.overridePendingTransition(R.anim.right_in,R.anim.right_out);
		context.startActivityForResult(intent, Constants.ActivityExtra.SELECT_PROVINCE_NAME);
	}

	/***
	 *
	 * @param context
	 */
	public static void luanchForResult(Activity context){
		luanchForResult(context,true);
	}

}
