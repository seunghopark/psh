package psh.testapp.pshfont;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;

public class SubActivityN extends Activity implements View.OnClickListener{

	private static final String TYPEFACE_NAME = "fonts/NanumGothic.otf";
	private Typeface typeface = null;
	private static final String TYPEFACE_NAME2 = "fonts/NanumGothic.otf";
	private Typeface typeface2 = null;
	
	private void loadTypeface() {
		if (typeface == null)
			typeface = Typeface.createFromAsset(getAssets(), TYPEFACE_NAME);
		if (typeface2 == null)
			typeface2 = Typeface.createFromAsset(getAssets(), TYPEFACE_NAME2);
	}
	
	public String textName;

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this); // Add this method.
		// May return null if EasyTracker has not yet been initialized with a
		// property
		// ID.
		Tracker easyTracker = EasyTracker.getInstance(this);

		// This screen name value will remain set on the tracker and sent with
		// hits until it is set to a new value or to null.
		easyTracker.set(Fields.SCREEN_NAME, "Home XXXn");

		easyTracker.send(MapBuilder.createAppView().build());
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this); // Add this method.
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
		Log.d("Ŭ��", "Ŭ����");
		onBackPressed();
	    return true;
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.backbutton1:			
			finish();
			break;
		}
		// TODO �ڵ� ������ �޼ҵ� ����

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent myintent = getIntent();
		String fontCnt = myintent.getStringExtra("fontContent");
		
		loadTypeface();

		setContentView(R.layout.activity_sub_n);
		
		View myButtonLayout = getLayoutInflater().inflate(R.layout.psh_sctionbar, null);
		
		myButtonLayout.findViewById(R.id.backbutton1).setOnClickListener(this);
		
		TextView test = (TextView) myButtonLayout.findViewById(R.id.titleView1);
		
		test.setText("여기는 어디");
		test.setTypeface(typeface2);
		
		ActionBar barbar = getActionBar();
		barbar.setCustomView(myButtonLayout);				
		barbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		LinearLayout linerLO = new LinearLayout(this);

		linerLO.setOrientation(LinearLayout.VERTICAL);
		
		linerLO.setPaddingRelative(15, 66, 15, 15);

		TextView txt = new TextView(this);

		txt.setText("NanumGothic");

		linerLO.addView(txt);

		TextView[] tv = new TextView[140];

		ScrollView scc = new ScrollView(this);


		for (int i = 0; i < tv.length; i++) {
			tv[i] = new TextView(this);
			tv[i].setTypeface(typeface);
			tv[i].setText((i + 10) + fontCnt);
			tv[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, i + 10);
			linerLO.addView(tv[i]);
		}

		scc.addView(linerLO);
		

		setContentView(scc);

	}

}
