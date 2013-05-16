package ltv.model;

import java.util.ArrayList;
import java.util.List;

import ltv.db.DBHandler;
import ltv.lib.Util;
import ltv.obj.Song;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class MSong {
	private DBHandler db;
	
	public MSong(Context context) {
		db = new DBHandler(context);
	}
	
	public void addSong(Song song) {
		db.openDataBase();
		ContentValues cValues = new ContentValues();
		cValues.put(MKey.SONG_ID, song.getId());
		cValues.put(MKey.SONG_NAME, song.getName());
		cValues.put(MKey.SONG_SUMMARY, song.getSummary());
		cValues.put(MKey.SONG_LYRIC, song.getLink());
		cValues.put(MKey.SONG_AUTHOR, song.getAuthor());
		cValues.put(MKey.SONG_FAVORITE, song.getFavorite());
		cValues.put(MKey.SONG_LINK, song.getLink());
		cValues.put(MKey.SONG_NAME_UNSIGN, song.getName_unsign());
		cValues.put(MKey.SONG_CATID, song.getCatid());
		db.getSqlDb().insert(MKey.TB_SONG, null, cValues);
		db.close();
	}
	
    public void addListSong(List<Song> ls) {
    	db.openDataBase();
    	db.getSqlDb().execSQL("BEGIN IMMEDIATE TRANSACTION");
    	for (Song s : ls) {
    		db.getSqlDb().execSQL(String.format("INSERT INTO song (\"%s\", \"%s\",s\"," + 
    				" \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\") VALUES" + 
    				" (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%d\")", 
    				MKey.SONG_ID, MKey.SONG_NAME, MKey.SONG_SUMMARY, MKey.SONG_LYRIC, MKey.SONG_AUTHOR, 
    				MKey.SONG_FAVORITE, MKey.SONG_LINK, MKey.SONG_NAME_UNSIGN, MKey.SONG_CATID,
    				s.getId(), s.getName(), s.getSummary(), s.getLyric(), s.getAuthor(), 
    				s.getFavorite(), s.getLink(), s.getName_unsign(), s.getCatid()));
    		Log.i("=>", s.toString());
    	}
    	db.getSqlDb().execSQL("COMMIT TRANSACTION");
    	db.close();
    }	
    
    public Song getSong(String id) {
    	db.openDataBase();
    	Cursor c = db.getSqlDb().query(MKey.TB_SONG, new String[] {MKey.SONG_ID, 
    			MKey.SONG_NAME, MKey.SONG_SUMMARY, MKey.SONG_LYRIC, MKey.SONG_AUTHOR, 
				MKey.SONG_FAVORITE, MKey.SONG_LINK, MKey.SONG_NAME_UNSIGN, MKey.SONG_CATID}, 
    			MKey.SONG_ID + "=?", new String[]{id}, null, null, null, null);
    	if (c != null) {
    		c.moveToFirst();
    	}
    	Song song = new Song(c.getString(0), c.getString(1), 
    			c.getString(2), c.getString(3), c.getString(4), 
    			c.getString(5), c.getString(6), c.getString(7), Integer.parseInt(c.getString(8)));
    	db.close();
    	return song;
    } 
    
    public List<Song> getList(String nameList) {
    	List<Song> ls = new ArrayList<Song>();
    	String query = "SELECT id, name, summary, favorite FROM " + nameList;
    	db.openDataBase();
    	Cursor c = db.getSqlDb().rawQuery(query, null);
    	if (c.moveToFirst()) {
    		do {
    			Song s = new Song();
    			s.setId(c.getString(0));
    			s.setName(c.getString(1));
    			s.setSummary(c.getString(2));
    			s.setFavorite(c.getString(3));
    			ls.add(s);
    		} while (c.moveToNext());
    	}    	
    	db.close();
    	return ls;
    }    
    
    public List<Song> search(String key) {
    	List<Song> ls = new ArrayList<Song>();
    	String query = "";
    	String sql = "SELECT id, name, summary, favorite FROM " + MKey.TB_SONG;
		if (key.matches("^[\\@].*")) {
			key = key.replace("@", "");
			System.out.println(key);
			query = sql + " WHERE author like '%" + key + "%'";
		} else if (key.matches("^[\\*][0-9]+")) {
			key = key.replace("*", "");
			query = sql + " WHERE id like '%" + key + "%'";
		} else {
			query = sql + " WHERE name_unsign like '%" + Util.nonUnicode(key) + "%'";
		}    	
    	db.openDataBase();
    	try {
    		Cursor c = db.getSqlDb().rawQuery(query, null);
        	if (c.moveToFirst()) {
        		do {
        			Song s = new Song();
        			s.setId(c.getString(0));
        			s.setName(c.getString(1));
        			s.setSummary(c.getString(2));
        			s.setFavorite(c.getString(3));
        			ls.add(s);
        		} while (c.moveToNext());
        	}
        	db.close();    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return ls;
    }
    
    public boolean setFav(String id, String fav) {
    	db.openDataBase();
    	ContentValues cv = new ContentValues();
    	cv.put(MKey.SONG_FAVORITE, fav);
    	//db.getSqlDb().insert(MKey.TB_SONG, null, cv);
    	int info = db.getSqlDb().update(MKey.TB_SONG, cv, MKey.SONG_ID + "=?", new String[]{id});
    	db.close();
    	return info > 0 ? true : false;    
    }    
    public void deleteSong(String id) {
    	db.openDataBase();
    	db.getSqlDb().delete(MKey.TB_SONG, MKey.SONG_ID + "=?", new String[]{id});
    	db.close();
    }    
}
