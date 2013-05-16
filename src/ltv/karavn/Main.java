package ltv.karavn;

import ltv.karavn.R;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;


@SuppressWarnings("deprecation")
public class Main extends TabActivity {
	LinearLayout btn;
	TabHost tabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btn = (LinearLayout) findViewById(R.id.btn);
		tabHost = getTabHost();
		tabHost.setup();
		tabHost.getTabWidget().setOrientation(LinearLayout.VERTICAL);
		
		
		// add tab
		this.addTab(getResources(), tabHost, "search", 
				new Intent(this, Search.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		this.addTab(getResources(), tabHost, "list",
				new Intent(this, List.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		this.addTab(getResources(), tabHost, "favorite",
				new Intent(this, Favorite.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		this.addTab(getResources(), tabHost, "contact", 
				new Intent().setClass(this, Contact.class));
		this.addTab(getResources(), tabHost, "info", 
				new Intent().setClass(this, Info.class));
		setTab(0);
	}
	
	public void tabHandler(View target){
		tabHost.invalidate();
		switch (target.getId()) {
			case R.id.btn_search: {
				tabHost.setCurrentTab(0);
				setTab(0);
				break;
			}
			case R.id.btn_list: {
				tabHost.setCurrentTab(1);
				setTab(1);
				break;
			}
			case R.id.btn_favorite: {
				tabHost.setCurrentTab(2);
				setTab(2);
				break;
			}
			case R.id.btn_contact: {
				tabHost.setCurrentTab(3);
				setTab(3);
				break;
			}
			case R.id.btn_info: {
				tabHost.setCurrentTab(4);
				setTab(4);
				break;
			}			
			default:
				break;
		}
	}
	
	/*
	 * What about this method?
	 * Argument pos stands for posistion of button in LinearLayout
	 * Set current button by selected and other by unselected
	 */
	private void setTab(int pos) {
		int numChild = btn.getChildCount();
		tabHost.setCurrentTab(pos);
		for (int i = 0; i < numChild; i++) {
			btn.getChildAt(i).setSelected(false);
		}
		btn.getChildAt(pos).setSelected(true);
	}
	/*
	 * Before using this method, you have to create an Activity which extends TabActivity
	 * After that, declaring a TabHost by this way: TabHost tabHost = getTabHost();
	 * And getResourses by this way: Resources resources =  getResources(); 
	 * Declaring an Intent like this way: Intent myIntent = new Intent().setClass(this, MyActivity.class);
	 * MyActivity is Activity which you are going to show in tab content
	 * Drawable??? This is layout of tab
	 */
	private void addTab(Resources resources, TabHost tabHost, 
			String tag, Intent intent) {
		TabSpec tabSpec = tabHost
				.newTabSpec(tag)
				.setIndicator("")
				.setContent(intent);
		tabHost.addTab(tabSpec);
	}	
}
