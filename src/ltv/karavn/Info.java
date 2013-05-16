package ltv.karavn;

import ltv.karavn.R;
import ltv.lib.Util;
import android.os.Bundle;
import android.widget.ImageView;
//import android.widget.LinearLayout;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Info extends Activity {
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
        this.imageView = (ImageView)findViewById(R.id.news_image);

        // The image is coming from resource folder but it could also 
        // load from the internet or whatever
        Drawable drawable = getResources().getDrawable(R.drawable.info);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

        // Get scaling factor to fit the max possible width of the ImageView
        float scalingFactor = this.getBitmapScalingFactor(bitmap);

        // Create a new bitmap with the scaling factor
        Bitmap newBitmap = Util.ScaleBitmap(bitmap, scalingFactor);

        // Set the bitmap as the ImageView source
        this.imageView.setImageBitmap(newBitmap);		
	}
    private float getBitmapScalingFactor(Bitmap bm) {
        // Get display width from device
        @SuppressWarnings("deprecation")
		int displayWidth = getWindowManager().getDefaultDisplay().getWidth();
        
        int imageViewWidth = displayWidth;
        return ( (float) imageViewWidth / (float) bm.getWidth() );
    }	
}
