package ltv.karavn;

import ltv.adapter.KaraAdapter;
import ltv.karavn.R;
import ltv.model.MKey;
import ltv.model.MSong;
import ltv.obj.Song;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;

public class Favorite extends Activity {
	
	private java.util.List<Song> list;
	private MSong db;
	private ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorite);
		
		db = new MSong(getApplicationContext());
		list = db.getList(MKey.VI_FAV);
		if (list.isEmpty()) {
			TextView tv = (TextView) findViewById(R.id.txt_info);
			tv.setText("Không có bài hát nào.");
		} else {
			lv = (ListView) findViewById(R.id.listKara);
			KaraAdapter ka = new KaraAdapter(this, list);
			lv.setAdapter(ka);
		}
	}
}
