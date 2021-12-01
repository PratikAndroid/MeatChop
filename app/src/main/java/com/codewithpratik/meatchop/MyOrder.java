package com.codewithpratik.meatchop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyOrder extends AppCompatActivity {
     private RecyclerView recyclerView;
     private MyOrderAdapter myOrderAdapter;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ImageView myOrderBackArrow;
    private ArrayList<MyOrderContent> MyOrderContentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
         myOrderBackArrow=findViewById(R.id.my_order_back_arrow_cart);
        recyclerView = findViewById(R.id.my_order_recyclerView);
         rootNode = FirebaseDatabase.getInstance();
        recycle();

    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(MyOrder.this,Home.class);
        startActivity(intent);
        finish();}
    public void myOrderToHome(View view)
    {

        Intent intent = new Intent(MyOrder.this,Home.class);
        startActivity(intent);
        finish();
    }

    public void recycle()
    {
        SharedPreferences getSharedData = getSharedPreferences("com.codewithpratik.meatchop", MODE_PRIVATE);
        String number = getSharedData.getString("mobile_number", "0000");
        Log.d("db12", "number :" + number);
        reference = rootNode.getReference("User/" + number);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myOrderAdapter = new MyOrderAdapter(this,MyOrderContentList);
        recyclerView.setAdapter(myOrderAdapter);

        reference.child("MyOrder").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("db12","children:"+snapshot.getChildrenCount() );
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {      Log.d("db12","children:"+dataSnapshot.getChildrenCount() );
                    MyOrderContent myOrderContent = dataSnapshot.getValue(MyOrderContent.class);
                    MyOrderContentList.add(myOrderContent);
                    Log.d("db12",MyOrderContentList.get(0).getOrderId());
                }
               myOrderAdapter.notifyDataSetChanged();
                // cartAdapter.notifyDataSetChanged();
                Log.d("db12", "size2 :"+ MyOrderContentList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}