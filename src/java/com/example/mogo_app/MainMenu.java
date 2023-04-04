/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.ImageView
 *  android.widget.TextView
 *  androidx.appcompat.app.AppCompatActivity
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package com.example.mogo_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mogo_app.LoginMenu;

public class MainMenu
extends AppCompatActivity {
    Button logout;
    EditText mainTask;
    ImageView profilePic;
    EditText task1;
    EditText task2;
    EditText task3;
    EditText task4;
    TextView userName;

    protected void onCreate(Bundle bundle) {
        Button button;
        super.onCreate(bundle);
        this.setContentView(2131427377);
        this.task1 = (EditText)this.findViewById(2131231174);
        this.task2 = (EditText)this.findViewById(2131231175);
        this.task3 = (EditText)this.findViewById(2131231176);
        this.task4 = (EditText)this.findViewById(2131231177);
        this.mainTask = (EditText)this.findViewById(2131230983);
        this.profilePic = (ImageView)this.findViewById(2131231080);
        this.userName = (TextView)this.findViewById(2131231040);
        Bundle bundle2 = this.getIntent().getExtras();
        if (bundle2 != null) {
            String string2 = bundle2.getString("key");
            this.showInfo(this.userName, string2);
        }
        this.logout = button = (Button)this.findViewById(2131230980);
        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Intent intent = new Intent((Context)MainMenu.this, LoginMenu.class);
                MainMenu.this.startActivity(intent);
            }
        });
    }

    public void showInfo(TextView textView, String string2) {
        textView.setText((CharSequence)string2);
    }

}

