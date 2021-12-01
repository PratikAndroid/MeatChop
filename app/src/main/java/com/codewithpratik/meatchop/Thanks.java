package com.codewithpratik.meatchop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class Thanks extends AppCompatActivity {
         MaterialButton go_to_my_order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);

        go_to_my_order = findViewById(R.id.btn_go_to_my_order);

    }
    public void MyOrderStatus(View view)
    {
        Intent intent = new Intent(this,MyOrder.class);
        startActivity(intent);
    }


}