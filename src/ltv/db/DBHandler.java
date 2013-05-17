package ltv.db;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

@SuppressLint("SdCardPath")
public class DBHandler extends SQLiteOpenHelper {
	private String TAG = DBHandler.class.getSimpleName();
	public static String DB_PATH = "";
	private static String DB_NAME = "kara.sqlite";
	private SQLiteDatabase sqlDb; 
	private final Context context;	
	
	public DBHandler(Context context) {
		super(context, DB_NAME, null, 1);
		this.context = context; 
		DB_PATH = "/data/data/" + this.context.getPackageName() + "/databases/";
		try {
			this.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			this.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		context.deleteDatabase("kara.sqlite");
		onCreate(db);
	}
	
	public void createDataBase() throws IOException{
	    // for first database;
	    boolean dbExist = checkDataBase();
	    if(!dbExist){
	        try {
	            copyDataBase(DB_NAME);
	        } catch (Exception e) {
	        	Log.e(TAG, "createDatabse -> Copy failed!");
	            throw new Error("Error copying database");
	        }
	    } else {
	    	openDataBase();
	    	boolean isExist = false;
	    	Cursor cursor = sqlDb.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = 'config'", null);
	    	if (cursor != null) {
	    		isExist = true;
	    		cursor.close();
	    	} else {
	    		isExist = false;
	    	}
	    	close();
	    	Log.d(TAG, isExist + "");
	    	if (!isExist) {
	    		this.context.deleteDatabase(DB_NAME);
		        try {
		        	Log.d(TAG, "createDatabase when database has existed");
		            copyDataBase(DB_NAME);
		        } catch (Exception e) {
		        	Log.e(TAG, "createDatabse -> Copy failed!");
		            throw new Error("Error copying database");
		        }	    		
	    	}
	    }
	}	
	/**
	 * Copies your database from your local assets-folder to the just created empty database in the
	 * system folder, from where it can be accessed and handled.
	 * This is done by transfering bytestream.
	 * */
	private void copyDataBase(String DB) {
	    //Open your local db as the input stream
	    InputStream myInput = null;
	    //Open the empty db as the output stream
	    OutputStream myOutput = null;
	    try {
	        myInput = context.getResources().getAssets().open(DB);

	        // Path to the just created empty db
	        String outFileName = DB_PATH + DB_NAME; 
	        myOutput = new FileOutputStream(outFileName);
	      
	        //transfer bytes from the inputfile to the outputfile
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = myInput.read(buffer)) > 0){
	            myOutput.write(buffer, 0, length);
	        }
	    } catch (FileNotFoundException e) {
	    	Log.e(TAG, "copyDatabase -> File not found.");
	    } catch (IOException e) {
	    	Log.e(TAG, "copyDatabase");
	    } finally {
	          //Close the streams
	        try {
		        myOutput.flush();
		        myOutput.close();
		        myInput.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	    }
	}	
	private boolean checkDataBase(){
	    boolean checkDB = false;
	    try{
	    	String myPath = DB_PATH + DB_NAME;
	        File dbFile = new File(myPath); 
	        checkDB = dbFile.isFile();
	        Log.d(TAG, "checkDatabase: " + String.valueOf(checkDB));
	        try {
		        File fTmp = new File(DB_PATH);
		        if (!fTmp.exists()) {
		        	fTmp.mkdir();
		        }
	        } catch (Exception e) {
	        	Log.e(TAG, "checkDatabase" + e.getMessage());
	        }
	    }catch(SQLiteException e){}
	    return checkDB;
	}
	public void openDataBase() {
	    try {
	        String myPath = DB_PATH + DB_NAME;
	        sqlDb = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);	    	
	    } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}
	
	@Override
	public synchronized void close() {
	        if(sqlDb != null)
	            sqlDb.close();
	        super.close();
	}	    	
	public SQLiteDatabase getSqlDb() {
		return sqlDb;
	}
	public void setSqlDb(SQLiteDatabase sqlDb) {
		this.sqlDb = sqlDb;
	}    
}
