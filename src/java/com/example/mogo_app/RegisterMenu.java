/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.text.Editable
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.Toast
 *  androidx.appcompat.app.AppCompatActivity
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package com.example.mogo_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mogo_app.DBHelper;
import com.example.mogo_app.LoginMenu;
import com.example.mogo_app.MainMenu;

public class RegisterMenu
extends AppCompatActivity {
    DBHelper DB;
    Button Register;
    EditText email;
    Button login;
    EditText name;
    EditText password;
    EditText password_2;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427430);
        this.DB = new DBHelper((Context)this);
        this.name = (EditText)this.findViewById(2131231041);
        this.email = (EditText)this.findViewById(2131230906);
        this.password = (EditText)this.findViewById(2131231085);
        this.password_2 = (EditText)this.findViewById(2131231086);
        this.Register = (Button)this.findViewById(2131231091);
        this.login = (Button)this.findViewById(2131230982);
        this.Register.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (RegisterMenu.this.name.getText().toString().equals((Object)"") || RegisterMenu.this.email.getText().toString().equals((Object)"") || RegisterMenu.this.password.getText().toString().equals((Object)"") || !RegisterMenu.this.password.getText().toString().equals((Object)RegisterMenu.this.password_2.getText().toString())) {
                    Toast.makeText((Context)RegisterMenu.this, (CharSequence)"PLEASE FILL UP ALL FIELDS !", (int)1).show();
                }
                if (!RegisterMenu.this.password.getText().toString().equals((Object)RegisterMenu.this.password_2.getText().toString())) {
                    Toast.makeText((Context)RegisterMenu.this, (CharSequence)"RE-WRITE PASSWORDS !", (int)1).show();
                    return;
                }
                Boolean bl = RegisterMenu.this.DB.checkUserExists(RegisterMenu.this.name.getText().toString());
                String string2 = RegisterMenu.this.name.getText().toString();
                String string3 = RegisterMenu.this.password.getText().toString();
                if (!bl.booleanValue()) {
                    if (RegisterMenu.this.DB.insertData(string2, string3).booleanValue()) {
                        Toast.makeText((Context)RegisterMenu.this, (CharSequence)"Registered Successfully !", (int)1).show();
                        Intent intent = new Intent((Context)RegisterMenu.this, MainMenu.class);
                        intent.putExtra("key", string2);
                        RegisterMenu.this.startActivity(intent);
                    } else {
                        Toast.makeText((Context)RegisterMenu.this, (CharSequence)"Register Failed.", (int)1).show();
                    }
                    return;
                }
                Toast.makeText((Context)RegisterMenu.this, (CharSequence)"User already exists ! Please Login instead ! ", (int)1).show();
            }
        });
        this.login.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Intent intent = new Intent((Context)RegisterMenu.this, LoginMenu.class);
                RegisterMenu.this.startActivity(intent);
            }
        });
    }

}

