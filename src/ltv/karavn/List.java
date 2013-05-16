package ltv.karavn;

import ltv.adapter.KaraAdapter;
import ltv.karavn.R;
import ltv.model.MKey;
import ltv.model.MSong;
import ltv.obj.Song;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;

public class List extends Activity implements OnItemSelectedListener {

	private java.util.List<Song> list;
	private MSong db;
	private ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		Spinner spinner = (Spinner) findViewById(R.id.category);
		int numChild = spinner.getChildCount();
		for (int i = 0; i < numChild; i++) {
			((TextView) spinner.getChildAt(i)).setTypeface(null, Typeface.BOLD);
			((TextView) spinner.getChildAt(i)).setTextColor(Color.RED);
		}
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.category, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
		
		db = new MSong(getApplicationContext());
		list = db.getList(MKey.VI_ARIANG);
		lv = (ListView) findViewById(R.id.listKara);
		KaraAdapter ka = new KaraAdapter(this, list);
		lv.setAdapter(ka);			
		
		// FOR INDEXABLE
		TextView i0 = (TextView) findViewById(R.id.index_0);
		i0.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lv.setSelection(0);
			}
		});
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
			case 0: {
				// Ariang
				list = db.getList(MKey.VI_ARIANG);
				break;
			}
			case 1: {
				// California
				list = db.getList(MKey.VI_CALI);
				break;
			}
			case 2: {
				// English
				list = db.getList(MKey.VI_ENG);
				break;
			}
			default: {
				break;
			}
		}
		//lv.invalidateViews();
		KaraAdapter ka = new KaraAdapter(this, list);
		lv.setAdapter(ka);		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}
}
