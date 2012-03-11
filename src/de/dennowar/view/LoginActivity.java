package de.dennowar.view;

import db.JfDbAdapter;
import de.dennowar.R;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
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
			}
		});
    }
    
    public boolean checkLogin(String email, String passwd){
    	Log.i(LoginActivity.class.getName(), "beginn check");
    	JfDbAdapter adapter = new JfDbAdapter(this);
    	adapter.open();
    	adapter.create(email, passwd);
    	Cursor c = adapter.fetchUser(email);
    	
    	if(c != null){
    		Log.i(LoginActivity.class.getName(), "email vorhanden");
    		String tmp = c.getString(c.getColumnIndex("passwd"));
	    		if(tmp.equals(passwd)){
	    			Log.i(LoginActivity.class.getName(), "Paswort richtig");
	    			return true;
	    		}
	    		else return false;
    	}
    	else{
    		Log.i(LoginActivity.class.getName(), "beginn create");
        	return true;    		
    	}
    }
}