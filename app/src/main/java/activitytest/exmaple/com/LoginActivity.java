package activitytest.exmaple.com.thefirstshoes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText account ;
    private EditText password;
    private Button button;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        account =  findViewById(R.id.account_login);
        password = findViewById(R.id.password);
        button = findViewById(R.id.b_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String acc = account.getText().toString();
                String pas = password.getText().toString();
                if(acc.equals("admin") && pas.equals("123456"))
                {
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this,"账号或者密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
