package ltv.adapter;

import java.util.List;


import ltv.karavn.Detail;
import ltv.karavn.R;
import ltv.lib.Util;
import ltv.model.MSong;
import ltv.obj.Song;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class KaraAdapter extends ArrayAdapter<Song> {

	private final Context context;
	private final List<Song> values;
	private MSong mSong;
	private boolean isFav = false;
	private ImageView iv_fav;
	  
	public KaraAdapter(Context context, List<Song> values)
	{
		super(context, R.layout.row_layout, values);
		this.context = context;
		this.values = values;
		mSong = new MSong(context);
	}
	
	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View rowView = inflater.inflate(R.layout.row_layout, parent, false);
		
		TextView txtId = (TextView) rowView.findViewById(R.id.tv_id);
		txtId.setText(values.get(position).getId() + "\t");
		
		TextView tvName = (TextView) rowView.findViewById(R.id.tv_name);
		tvName.setText(values.get(position).getName());
		
		TextView txtSum = (TextView) rowView.findViewById(R.id.tv_summary);
		txtSum.setText(values.get(position).getSummary());
		
		iv_fav = (ImageView) rowView.findViewById(R.id.iv_fav);
		
		if (values.get(position).getFavorite().equals("true")) {
			txtId.setTextColor(context.getResources().getColor(R.color.text_error));
			tvName.setTextColor(context.getResources().getColor(R.color.text_error));
			iv_fav.setImageResource(R.drawable.ic_liked);
			isFav = false;
		} else {
			iv_fav.setImageResource(R.drawable.ic_like);  // yet
			isFav = true;
		}
		
		final String id = values.get(position).getId();
		// For Like
		iv_fav.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ImageView iv = (ImageView) v;
				
				boolean res = mSong.setFav(id, String.valueOf(isFav));
				if (res) {
					if (isFav) {
						iv.setImageResource(R.drawable.ic_liked);
						Util.showMessage(context, "???? th??ch");
						isFav = false;
					} else {
						iv.setImageResource(R.drawable.ic_like);
						isFav = true;
					}
				} else {
					Util.showMessage(context, "L???i");
				}
			}
		});		
		
		OnClickListener onClick = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, Detail.class);
				intent.putExtra("id", values.get(position).getId());
				context.startActivity(intent);
			}
		};
		LinearLayout row = (LinearLayout) rowView.findViewById(R.id.row);
		row.setOnClickListener(onClick);
		return rowView;
	}
}
