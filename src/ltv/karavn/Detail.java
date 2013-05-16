package ltv.karavn;

import java.io.File;

import ltv.karavn.R;
import ltv.lib.DialogHandler;
import ltv.lib.Util;
import ltv.model.MSong;
import ltv.obj.Song;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;


@SuppressLint({ "NewApi", "DefaultLocale" })
public class Detail extends Activity  implements OnClickListener, OnTouchListener, 
								OnCompletionListener, OnBufferingUpdateListener{
	boolean fav = false;
	String url;
	String nameFile;
	String name;
	boolean isBack = false;
	private ImageButton buttonPlayPause;
	private SeekBar seekBarProgress;
	
	private MediaPlayer mediaPlayer;
	private int mediaFileLengthInMilliseconds; // this value contains the song duration in milliseconds. Look at getDuration() method in MediaPlayer class
	
	private final Handler handler = new Handler();
	private ltv.model.MSong mSong;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		mSong = new MSong(getApplicationContext());
		
		Bundle bundle = getIntent().getExtras();
		final String id = bundle.getString("id");
		Song song = mSong.getSong(id);
		init(id, song);		
		
		// FOR PLAY MP3 FILE
		url = Util.BASE_URL + song.getLink();
		initView();
	}
	private void init(final String id, Song song) {
		TextView txtId = (TextView) findViewById(R.id.txt_id);
		txtId.setText(song.getId());
		TextView txtName = (TextView) findViewById(R.id.txt_name);
		txtName.setText(song.getName());
		name = song.getName();
		// Here for nameFile
		nameFile = song.getName_unsign().replace(" ", "_").toLowerCase() + ".mp3";
		
		TextView txtLyric = (TextView) findViewById(R.id.txt_lyric);
		txtLyric.setText(song.getLyric() );
		TextView txtAuthor = (TextView) findViewById(R.id.txt_author);
		txtAuthor.setText(song.getAuthor());
		ImageView imgFav = (ImageView) findViewById(R.id.imgFav);
		
		// Ghet ImageResource Id
		final int imgLike = getBaseContext().getResources()
				.getIdentifier("like", "drawable", getPackageName());
		final int imgUnlike = getBaseContext().getResources()
				.getIdentifier("unlike", "drawable", getPackageName());

		if (song.getFavorite().equalsIgnoreCase("true")) {
			imgFav.setImageResource(imgUnlike);
			fav = false;
			//imgFav.setOn
		} else {
			imgFav.setImageResource(imgLike);
			fav = true;
		}
		
		// For Button Like clicked
		imgFav.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean res = mSong.setFav(id, String.valueOf(fav));
				if (res) {
					ImageView imgFav = (ImageView) findViewById(R.id.imgFav);
					if (fav) {
						imgFav.setImageResource(imgUnlike);
						fav = false;
					} else {
						imgFav.setImageResource(imgLike);
						fav = true;
					}
				}
			}
		});
		
		
		// For Download 
		Button btnDown = (Button) findViewById(R.id.btn_download);
		btnDown.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				String dir = Environment.DIRECTORY_MUSIC;
				dir += "/klp";
				File fileDir = new File(dir);
				if (!fileDir.isDirectory()) {
					fileDir.mkdir();
				}
				
				Toast.makeText(Detail.this, "Tải bài hát " + name, Toast.LENGTH_SHORT).show();
				// Download File
				DownloadManager.Request request = 
						new DownloadManager.Request(Uri.parse(url));
				request.setDescription(nameFile);
				request.setTitle(name);
				// in order for this if to run, you must use the android 3.2 to compile your app
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				    request.allowScanningByMediaScanner();
				    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
				}
				request.setDestinationInExternalPublicDir(dir, nameFile);

				// get download service and enqueue file
				DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
				manager.enqueue(request);	

			}
		});
		
		Button btnDownBeat = (Button) findViewById(R.id.btn_download_beat);
		btnDownBeat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(Detail.this, "Tính năng này đang được phát triển.", Toast.LENGTH_LONG).show();
			}
		});
		
	}

    /** This method initialise all the views in project*/
    private void initView() {
		buttonPlayPause = (ImageButton)findViewById(R.id.ButtonTestPlayPause);
		buttonPlayPause.setOnClickListener(this);
		
		seekBarProgress = (SeekBar)findViewById(R.id.SeekBarTestPlay);	
		seekBarProgress.setMax(99); // It means 100% .0-99
		seekBarProgress.setOnTouchListener(this);
		
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setOnBufferingUpdateListener(this);
		mediaPlayer.setOnCompletionListener(this);
	}

	/** Method which updates the SeekBar primary progress by current song playing position*/
    private void primarySeekBarProgressUpdater() {
    	seekBarProgress.setProgress((int)(((float)mediaPlayer.getCurrentPosition()/mediaFileLengthInMilliseconds)*100)); // This math construction give a percentage of "was playing"/"song length"
		if (mediaPlayer.isPlaying()) {
			Runnable notification = new Runnable() {
		        public void run() {
		        	primarySeekBarProgressUpdater();
				}
		    };
		    handler.postDelayed(notification,1000);
    	}
    }

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.ButtonTestPlayPause){
			 /** ImageButton onClick event handler. Method which start/pause mediaplayer playing */
			try {
				mediaPlayer.setDataSource(url); // setup song from http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
				mediaPlayer.prepare(); // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer. 
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL
			
			if(!mediaPlayer.isPlaying()){
				mediaPlayer.start();
				buttonPlayPause.setImageResource(R.drawable.button_pause);
			}else {
				mediaPlayer.pause();
				buttonPlayPause.setImageResource(R.drawable.button_play);
			}
			
			primarySeekBarProgressUpdater();
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(v.getId() == R.id.SeekBarTestPlay){
			/** Seekbar onTouch event handler. Method which seeks MediaPlayer to seekBar primary progress position*/
			if(mediaPlayer.isPlaying()){
		    	SeekBar sb = (SeekBar)v;
				int playPositionInMillisecconds = (mediaFileLengthInMilliseconds / 100) * sb.getProgress();
				mediaPlayer.seekTo(playPositionInMillisecconds);
			}
		}
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		 /** MediaPlayer onCompletion event handler. Method which calls then song playing is complete*/
		buttonPlayPause.setImageResource(R.drawable.button_play);
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		/** Method which updates the SeekBar secondary progress by current song loading from URL position*/
		seekBarProgress.setSecondaryProgress(percent);
	}	
	
	@Override
	public void onBackPressed() {
		if (isBack) {
			super.onBackPressed();
		} else {
			if (mediaPlayer != null) {
				if (mediaPlayer.isPlaying()) {
					back();
				} else {
					super.onBackPressed();
				}
			} else {
				super.onBackPressed();
			}
		}
	}	
	
    public void back() {
        DialogHandler appdialog = new DialogHandler();
        appdialog.Confirm(this, "Đang nghe thử bài hát...", 
        		"Bạn có chắc chắn quay lại danh sách?",
        		"Vâng", "Không", 
				new Runnable() {
					@Override
					public void run() {
						isBack = false;
						return;
					}
				}, new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						isBack = true;
						mediaPlayer.stop();
						onBackPressed();
					}
				});
    }	
}
