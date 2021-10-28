package com.finalP.reborn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText username, password, email, confirmpass;
    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username=findViewById(R.id.input_username);
        email=findViewById(R.id.inputemail);
        password=findViewById(R.id.inputpassword);


        confirmpass=findViewById(R.id.inputcpassword);
        DB=new DBHelper(this);



        Button btn1=findViewById(R.id.register);

        TextView btn=findViewById(R.id.alreadyaccount);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int count;
                String emailv = email.getText().toString().trim();
                String regexe = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
                if (emailv.matches(regexe)){
                    count=1;
                }
                else{
                    count=0;
                }
                String pass= password.getText().toString();
                String cpass=confirmpass.getText().toString();

                int pass1;
                String regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
                if (pass.matches(regexp)){
                    pass1=0;
                }
                else{
                    pass1=1;
                }
                if (username.getText().toString().trim().isEmpty())  {

                    Toast.makeText(getApplicationContext(),"Username cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else if(count==0){

                    Toast.makeText(getApplicationContext(),"Email is wrong",Toast.LENGTH_SHORT).show();
                }
                else if (!pass.equals(cpass)) {

                    Toast.makeText(getApplicationContext(),"both password should be same",Toast.LENGTH_SHORT).show();
                }
                else if (pass1==1) {

                    Toast.makeText(getApplicationContext(),"Password should be strong",Toast.LENGTH_SHORT).show();
                }
                else {
                    String user = email.getText().toString();
                    String passw= password.getText().toString();
                    Boolean checkuser = DB.checkusername(user);
                    if(checkuser==false){
                        Boolean insert = DB.insertData(user, passw);
                        if(insert==true){
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

                        }else{
                            Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                    }





                }
            }
        });
    }
}
