package psh.testapp.pshfont;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;

public class MainActivity extends Activity {

	public static final int REQUEST_CODE_ANOTHER = 1001;

	EditText edittxtContent;

	EditText edittxtSize;

	Button edittxtBtn;

	Button fontTypeA;

	Resources myr;

	Spinner spinner1;

	Spinner spinner2;

	Spinner spinner3;

	TextView text1;
	TextView txt;

	List<String> list = new ArrayList<String>();

	String[] filelist;

	String[] linesArr;

	List<Integer> sizeVal = new ArrayList<Integer>();

	List<Integer> SecondsizeVal = new ArrayList<Integer>();

	ActionBar shbar;

	// String fontName = "fonts/NanumGothicBold.ttf";

	EasyTracker easyTracker = EasyTracker.getInstance(this);

	String selItem;

	LinearLayout fontViewArea;

	Integer selItemSize = 0;

	Integer selSecondItemSize = 0;

	Integer maxVal = 150;
	private static final String TYPEFACE_NAME = "fonts/NanumGothicBold.ttf";

	private Typeface typeface = null;
	private Typeface typefaceAcition = null;

	private void loadTypeface() {
		if (typeface == null)
			typeface = Typeface.createFromAsset(getAssets(), TYPEFACE_NAME);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		loadTypeface();

		setContentView(R.layout.activity_main);

		View myButtonLayout = getLayoutInflater().inflate(
				R.layout.main_sctionbar, null); // 액션바 디자인 xml

		// getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

		ActionBar barbar = getActionBar(); // 액션바 종속

		barbar.setCustomView(myButtonLayout); // 액션바 디자인 적용

		barbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); // 액션바 기본 그능 선언

		text1 = (TextView) findViewById(R.id.Txt1); // 제목

		/* 글내용 넣기 */
		edittxtContent = (EditText) findViewById(R.id.inputTxt1);
		
		InputMethodManager imm = (InputMethodManager)getSystemService(
			      Context.INPUT_METHOD_SERVICE);
		
		imm.hideSoftInputFromWindow(edittxtContent.getWindowToken(), 0);

		/* 폰트이름 넣기 */

		final AssetManager assetManager = getAssets(); // 폰트 위치

		try {

			filelist = assetManager.list("fonts"); // 폰트 폴더의 파일 리스트

			if (filelist == null) {

			} else {
				for (int i = 0; i < filelist.length; i++) {

					String filename = filelist[i];
					list.add(filename);
				}

				System.out.println(list);

				linesArr = list.toArray(new String[list.size()]);

				for (String line : linesArr) {
					System.out.println(line);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		spinner1 = (Spinner) findViewById(R.id.fontSpinner); // 폰트 리스트 스피너

		spinner1.setPrompt("하하하");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, linesArr);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner1.setAdapter(dataAdapter);
		// 폰트이름
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				selItem = (String) spinner1.getSelectedItem();

				Toast.makeText(getBaseContext(), selItem + "선택된 글씨체",
						Toast.LENGTH_LONG).show();
			}

			public void onNothingSelected(AdapterView<?> parent) {
				Toast.makeText(getBaseContext(), selItem + "선태깅 안됨",
						Toast.LENGTH_LONG).show();
			}
		});
		/* 폰트이름 넣기 끝 */

		for (int j = 0; j < maxVal; j++) {
			// sizeVal[j] = j + 1;
			sizeVal.add(j + 1);

		}

		/* 첫번째 폰트 사이즈 넣기 */

		spinner2 = (Spinner) findViewById(R.id.sizeSpinner);

		ArrayAdapter<Integer> dataAdapterSize = new ArrayAdapter<Integer>(this,
				android.R.layout.simple_spinner_item, sizeVal);

		dataAdapterSize
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner2.setAdapter(dataAdapterSize);

		
		final ArrayAdapter<Integer> dataAdapterSecondSize = new ArrayAdapter<Integer>(
				this, android.R.layout.simple_spinner_item, SecondsizeVal);

		dataAdapterSecondSize
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				selItemSize = (Integer) spinner2.getSelectedItem();
				keyBordHiden();
			}

			public void onNothingSelected(AdapterView<?> parent) {
			}
		});		

		edittxtBtn = (Button) findViewById(R.id.btn1); // 보기버튼

		edittxtBtn.setOnClickListener(new ClickHandler()); // 이베튼 등록

	}
	
	public void keyBordHiden() {
		
		((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
	      .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0); //키보드 숨김
	}
	
	
	// 보기 버튼
	private class ClickHandler implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO
			try {
				keyBordHiden();
				
				String myFontContent = edittxtContent.getText().toString();// 입력된
																			// 글
																			// 가져
																			// 오기

				// Intent myFontActivity = new Intent(getApplicationContext(),
				// CsFontActivity.class); //새로운 창 액티비티 연결

				// myFontActivity.putExtra("fontContent", myFontContent);
				// myFontActivity.putExtra("fontName", selItem);
				// myFontActivity.putExtra("fontSize", selItemSize);
				//
				// startActivity(myFontActivity);

				fontViewArea = (LinearLayout) findViewById(R.id.fontViewArea);

				fontViewArea.setOrientation(LinearLayout.VERTICAL);
				fontViewArea.setBackgroundColor(Color.parseColor("#000000"));

				TextView txt = new TextView(MainActivity.this);

				typefaceAcition = Typeface.createFromAsset(getAssets(),
						"fonts/" + selItem);

				txt.setText(myFontContent);
				
				
				txt.setTextColor(Color.parseColor("#FFFFFF"));
				
				txt.setTypeface(typefaceAcition);
				txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, selItemSize);

				fontViewArea.addView(txt);

			} catch (Exception ex) {
				easyTracker.send(MapBuilder.createEvent("����-����",
						"button_press", // Event action (required)
						"play_button", // Event label
						null) // Event value
						.build());

				Toast.makeText(getBaseContext(), ex.getMessage(),
						Toast.LENGTH_LONG).show();
			}

		}
	}

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
		easyTracker.set(Fields.SCREEN_NAME, "메인");
		easyTracker.send(MapBuilder.createAppView().build());
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this); // Add this method.
	}

}
