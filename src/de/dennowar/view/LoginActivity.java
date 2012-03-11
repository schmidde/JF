package de.dennowar.view;

import de.dennowar.R;
import de.dennowar.db.JfDbAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
    /** Called when the activity is first created. */
	private Context ctx = this;
	private JfDbAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        adapter = new JfDbAdapter(this);
        adapter.open();
        
        Button login = (Button) findViewById(R.id.btnLogin);
        
        login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				EditText etEmail = (EditText) findViewById(R.id.emailLogin);
		        String email = etEmail.getText().toString();
		        EditText etPasswd = (EditText) findViewById(R.id.passwdLogin);
		        String passwd = etPasswd.getText().toString();
		        
				Log.i(LoginActivity.class.getName(),"onClick(): " + v);
				if(checkLogin(email, passwd)){
					startActivity(new Intent(LoginActivity.this, JFActivity.class));
				}
				else{
					CharSequence s = "Wrong credentials, try again!";
					Toast t = Toast.makeText(ctx, s, Toast.LENGTH_SHORT);
					t.show();
				}
			}
		});
    }
    
    public boolean checkLogin(String email, String passwd){
    	Log.i(LoginActivity.class.getName(), "checking Credentials");
    	Cursor c = adapter.fetchUser(email);
    	if(c.getCount() > 0){
    		Log.i(LoginActivity.class.getName(), c.getString(c.getColumnIndex("email")));
    		if(c.getString(c.getColumnIndex("passwd")).equals(passwd)){
    			return true;
    		}
    	}
    	return false;
    }
}