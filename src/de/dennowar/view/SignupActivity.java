package de.dennowar.view;

import de.dennowar.R;
import de.dennowar.db.JfDbAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends Activity {
    /** Called when the activity is first created. */
	private JfDbAdapter adapter;
	private Context ctx = this;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        
        adapter = new JfDbAdapter(this);
        adapter.open();
        Button btn = (Button) findViewById(R.id.btnSignup);
        btn.setOnClickListener(new OnClickListener(){
        	
        	@Override
        	public void onClick(View v){
        		EditText etemail = (EditText) findViewById(R.id.etEmailSignup);
        		EditText etpasswd = (EditText) findViewById(R.id.etPasswsSignup);
        		EditText etverify = (EditText) findViewById(R.id.etVerifySignup);
        		
        		if(etpasswd.getText().toString().equals(etverify.getText().toString())){
        			adapter.create(etemail.getText().toString(), etpasswd.getText().toString());
        			startActivity(new Intent(SignupActivity.this, JFActivity.class));
        		}
        		else{
        			CharSequence s = "Sory, try again!";
					Toast t = Toast.makeText(ctx, s, Toast.LENGTH_SHORT);
					t.show();
        		}
        	}
        });
    }
}