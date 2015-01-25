package baidumapsdk.demo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.VersionInfo;

public class BMapApiDemoMain extends Activity {
	private static final String LTAG = BMapApiDemoMain.class.getSimpleName();

	/**
	 * 构造广播监听类，监听 SDK key 验证以及网络异常广播
	 */
	public class SDKReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			Log.d(LTAG, "action: " + s);
			TextView text = (TextView) findViewById(R.id.text_Info);
			text.setTextColor(Color.RED);
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				text.setText("key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置");
			} else if (s
					.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				text.setText("网络出错");
			}
		}
	}

	private SDKReceiver mReceiver;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TextView text = (TextView) findViewById(R.id.text_Info);
		text.setTextColor(Color.YELLOW);
		text.setText("欢迎使用百度地图Android SDK v" + VersionInfo.getApiVersion());
		ListView mListView = (ListView) findViewById(R.id.listView);
		// 添加ListItem，设置事件响应
		mListView.setAdapter(new DemoListAdapter());
		mListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int index,
					long arg3) {
				onListItemClick(index);
			}
		});

		// 注册 SDK 广播监听者
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		mReceiver = new SDKReceiver();
		registerReceiver(mReceiver, iFilter);
	}

	void onListItemClick(int index) {
		Intent intent = null;
		intent = new Intent(BMapApiDemoMain.this, demos[index].demoClass);
		this.startActivity(intent);
	}

	private static final DemoInfo[] demos = {

			new DemoInfo(R.string.demo_title_location,
					R.string.demo_desc_location, LocationDemo.class),
			
	};

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 取消监听 SDK 广播
		unregisterReceiver(mReceiver);
	}

	private class DemoListAdapter extends BaseAdapter {
		public DemoListAdapter() {
			super();
		}

		@Override
		public View getView(int index, View convertView, ViewGroup parent) {
			convertView = View.inflate(BMapApiDemoMain.this,
					R.layout.demo_info_item, null);
			TextView title = (TextView) convertView.findViewById(R.id.title);
			TextView desc = (TextView) convertView.findViewById(R.id.desc);
			title.setText(demos[index].title);
			desc.setText(demos[index].desc);
			if (index >= 14) {
				title.setTextColor(Color.YELLOW);
			}
			return convertView;
		}

		@Override
		public int getCount() {
			return demos.length;
		}

		@Override
		public Object getItem(int index) {
			return demos[index];
		}

		@Override
		public long getItemId(int id) {
			return id;
		}
	}

	private static class DemoInfo {
		private final int title;
		private final int desc;
		private final Class<? extends android.app.Activity> demoClass;

		public DemoInfo(int title, int desc,
				Class<? extends android.app.Activity> demoClass) {
			this.title = title;
			this.desc = desc;
			this.demoClass = demoClass;
		}
	}
}