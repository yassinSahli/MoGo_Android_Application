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
import com.example.mogo_app.MainMenu;
import com.example.mogo_app.RegisterMenu;

public class LoginMenu
extends AppCompatActivity {
    DBHelper DB;
    Button login;
    EditText passwordET;
    Button register;
    EditText usernameET;

    protected void onCreate(Bundle bundle) {
        Button button;
        super.onCreate(bundle);
        this.setContentView(2131427373);
        this.DB = new DBHelper((Context)this);
        this.usernameET = (EditText)this.findViewById(2131231253);
        this.passwordET = (EditText)this.findViewById(2131231084);
        this.login = (Button)this.findViewById(2131230981);
        this.register = button = (Button)this.findViewById(2131231090);
        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Intent intent = new Intent((Context)LoginMenu.this, RegisterMenu.class);
                LoginMenu.this.startActivity(intent);
            }
        });
        this.login.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                String string2 = LoginMenu.this.usernameET.getText().toString();
                String string3 = LoginMenu.this.passwordET.getText().toString();
                if (!string2.equals((Object)"") && !string3.equals((Object)"")) {
                    if (LoginMenu.this.DB.checkUserPassword(string2, string3).booleanValue()) {
                        Toast.makeText((Context)LoginMenu.this, (CharSequence)"LOGIN SUCCESSFUL !", (int)0).show();
                        Intent intent = new Intent((Context)LoginMenu.this, MainMenu.class);
                        intent.putExtra("key", string2);
                        LoginMenu.this.startActivity(intent);
                        return;
                    }
                    Toast.makeText((Context)LoginMenu.this, (CharSequence)"Login Failed", (int)0).show();
                    return;
                }
                Toast.makeText((Context)LoginMenu.this, (CharSequence)"ERROR, ALL FIELDS REQUIRED !", (int)0).show();
            }
        });
    }

}

