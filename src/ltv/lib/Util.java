package ltv.lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;

public class Util {
	
	public static String BASE_URL = "http://khoinghieptre.vn/nct/?id=";

    public static Bitmap ScaleBitmap(Bitmap bm, float scalingFactor) {
        //int scaleHeight = (int) (bm.getHeight() * scalingFactor);
    	int scaleHeight = bm.getHeight();
        int scaleWidth = (int) (bm.getWidth() * scalingFactor);

        return Bitmap.createScaledBitmap(bm, scaleWidth, scaleHeight, false);
    }
    
    private static InputStream openHttpConnection(String strURL)
            throws IOException {
        URLConnection conn = null;
        InputStream inputStream = null;
        URL url = new URL(strURL);
        conn = url.openConnection();
        HttpURLConnection httpConn = (HttpURLConnection) conn;
        httpConn.setRequestMethod("GET");
        httpConn.connect();
        if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            inputStream = httpConn.getInputStream();
        }
        return inputStream;
    }	
    
    public static String getJSONString(String id) throws IOException {
    	String url = Util.BASE_URL + id;
    	BufferedReader br = new BufferedReader(new InputStreamReader(openHttpConnection(url)));
    	StringBuilder sb = new StringBuilder();
    	String line = "";
    	while ((line = br.readLine()) != null) {
    		sb.append(line);
    	}
    	return sb.toString();
    }
    
    public static String nonUnicode(String str) {
        //str = str.replace('??', 'a');
        String[] arrA = new String[]{
            "??","??","???","???","??", "??","???","???","???",
            "???","???","??","???","???","???","???","???",
            "??","??","???","???","??", "??","???","???",
            "???","???","???","??","???","???","???","???","???"};
        String[] arrE = new String[] {
            "??","??","???","???","???" ,"??","???","???","???","???","???",
            "??","??","???","???","???" ,"??","???","???","???","???","???"            
        };
        String[] arrD = new String[] {
          "??", "??"
        };
        String[] arrY = new String[] {
            "???","??","???","???","???", "???","??","???","???","???"
        };
        String[] arrU = new String[] {
          "??","??","???","???","??", "??","???","???","???","???","???",
          "??","??","???","???","??", "??","???","???","???","???","???"
        };
        String[] arrI = new String[] {
            "??","??","???","???","??",
            "??","??","???","???","??"
        };
        String[] arrO = new String[] {
            "??","??","???","???","??", "??","???","???","???","???","???","??","???","???","???","???","???",
            "??","??","???","???","??", "??","???","???","???","???","???","??","???","???","???","???","???"
        };
        
        for (String string : arrA) {
            str = str.replace(string, "A");
        }
        for (String string : arrE) {
            str = str.replace(string, "E");
        }
        for (String string : arrD) {
            str = str.replace(string, "D");
        }
        for (String string : arrY) {
            str = str.replace(string, "Y");
        }
        for (String string : arrU) {
            str = str.replace(string, "U");
        }
        for (String string : arrI) {
            str = str.replace(string, "I");
        }
        for (String string : arrO) {
            str = str.replace(string, "O");
        }        
        return str;
    }  
    
    public static Uri downloadFileFromURL(URL url, Context context, String fileName) {
        try {
          URLConnection conn = url.openConnection();
          HttpURLConnection httpConnection = conn instanceof HttpURLConnection ? (HttpURLConnection ) conn  : null;
          if(httpConnection != null) {
            //int contentLength = httpConnection.getContentLength();
            int len = 0;
            //int length = 0;
            byte[] buf = new byte[8192];
            InputStream is = httpConnection.getInputStream();
            File file = new File(context.getExternalFilesDir(null), fileName);
            OutputStream os = new FileOutputStream(file);
            try {
              while((len = is.read(buf, 0, buf.length)) > 0) {
                os.write(buf, 0, len);
                //length += len;
                //publishProgress((int) (PROGRESS_MAX * (float) length / contentLength));
              }
              os.flush();
            }
            finally {
              is.close();
              os.close();
            }
            return Uri.fromFile(file);
          }
        }
        catch(IOException e)
        {
           //Exception handling goes here
        }
        return null;
      }    
    public static void showMessage(Context context, String msg) {
    	Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
