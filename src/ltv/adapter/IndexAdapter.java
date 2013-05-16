package ltv.adapter;

import java.util.List;

import ltv.karavn.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class IndexAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final List<String> list;
	
	public IndexAdapter(Context context, List<String> values) {
		super(context, R.layout.index_row, values);
		this.context = context;
		this.list = values;
	}
	
	@Override
	public View getView(final int location, View convertView, ViewGroup parent) {
		LayoutInflater inflater = 
				(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.index_row, parent, false);
		
		TextView tv = (TextView) rowView.findViewById(R.id.txt);
		tv.setText(list.get(location));
		
		
		return rowView;
	}
}
