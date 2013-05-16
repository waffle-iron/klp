package ltv.karavn;

import ltv.adapter.KaraAdapter;
import ltv.karavn.R;
import ltv.model.MSong;
import ltv.obj.Song;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.app.Activity;

public class Search extends Activity implements OnEditorActionListener, OnClickListener {

	private EditText search;
	private java.util.List<Song> list;
	private MSong db;
	private ListView lv;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		search = (EditText) findViewById(R.id.e_search);
		search.setOnEditorActionListener(this);
		
		ImageView iv= (ImageView) findViewById(R.id.img_search);
		iv.setOnClickListener(this);
	}

	@Override
	public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_SEARCH) {
			search();
			return true;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		search();
	}
	
	private void search() {
		TextView tv = (TextView) findViewById(R.id.txt_info);
		String key = search.getText().toString();
		db = new MSong(getApplicationContext());
		list = db.search(key);
		
		if (list.isEmpty()) {
			tv.setText("Không tìm thấy bài hát nào.");
			tv.setTextColor(getResources().getColor(R.color.text_error));
		} else {
			tv.setTextColor(getResources().getColor(R.color.text_blue));
			lv = (ListView) findViewById(R.id.listKara);
			tv.setText("Tìm thấy " + list.size() + " bài hát.");
			KaraAdapter ka = new KaraAdapter(this, list);
			lv.setAdapter(ka);
		}		
	}	
}
