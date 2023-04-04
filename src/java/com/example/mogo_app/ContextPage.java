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
 *  androidx.appcompat.app.AppCompatActivity
 *  java.lang.Class
 *  java.lang.Object
 */
package com.example.mogo_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mogo_app.LoginMenu;

public class ContextPage
extends AppCompatActivity {
    Button next;

    protected void onCreate(Bundle bundle) {
        Button button;
        super.onCreate(bundle);
        this.setContentView(2131427356);
        this.next = button = (Button)this.findViewById(2131230945);
        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Intent intent = new Intent((Context)ContextPage.this, LoginMenu.class);
                ContextPage.this.startActivity(intent);
            }
        });
    }

}

