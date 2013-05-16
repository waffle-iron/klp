package ltv.karavn;

import ltv.karavn.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;

public class Contact extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		((Button) findViewById(R.id.btn_submit)).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String error = "";
		String fullName = ((EditText) findViewById(R.id.et_fullname)).getText().toString().trim();
		String phone = ((EditText) findViewById(R.id.et_phone)).getText().toString().trim();
		String email = ((EditText) findViewById(R.id.et_email)).getText().toString().trim();
		String msg = ((EditText) findViewById(R.id.et_msg)).getText().toString().trim();
		if (fullName.equals("")) {
			error += "- Họ và tên\n";
		}

		if (msg.equals("")) {
			error += "- Nội dung\n";
		}
		
		if (error.equals("")) {
			Intent mail = new Intent(Intent.ACTION_SEND);
			mail.setType("message/rfc822");
			mail.putExtra(Intent.EXTRA_EMAIL, new String[] {"me@duongtuanluc.com"});
			mail.putExtra(Intent.EXTRA_SUBJECT, "[KARAOKE LIST PRO] Liên hệ từ " + fullName);
			
			String content = "";
			try {
				content = String.format(
						"Version: %s\n" + 
						"Họ và tên: %s\n" + 
						"Số điện thoại: %s\n" + 
						"Email: %s\n" + 
						"Nội dung: %s", getPackageManager().getPackageInfo(getPackageName(), 0).versionName, 
						fullName, phone, email, msg);
			} catch (NameNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			mail.putExtra(Intent.EXTRA_TEXT, content);
			
			try {
				startActivity(Intent.createChooser(mail, "Chọn ứng dụng email để gửi..."));
			} catch(android.content.ActivityNotFoundException e) {
				Toast.makeText(Contact.this, "Không có ứng email nào được cài đặt.", 
						Toast.LENGTH_SHORT).show();				
			}
		} else {
			Toast.makeText(Contact.this, "Các trường sau không được để trống: \n" + error, 
					Toast.LENGTH_SHORT).show();
		}
	}
}
