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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailsMyOrder extends AppCompatActivity {

    private RecyclerView recyclerView_details_order;
    private DetailsOrderAdapter detailsOrderAdapter;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private FirebaseAuth mAuth;
    ArrayList<String> detailsList = new ArrayList<>();
    private ArrayList<CartModel> detailListCartModel = new ArrayList<>();
    SharedPreferences getSharedData;
    String number;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          back = findViewById(R.id.order_details_back_arrow_cart);
        Intent intent = getIntent();
        String id = intent.getStringExtra("orderId");
        Log.d("db123456",id);
        setContentView(R.layout.activity_details_my_order);
        recyclerView_details_order=findViewById(R.id.recycle_view_details_order);
        rootNode = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        recyclerView_details_order.setHasFixedSize(true);
        recyclerView_details_order.setLayoutManager(new LinearLayoutManager(this));
        detailsOrderAdapter = new DetailsOrderAdapter(this,detailListCartModel);
        recyclerView_details_order.setAdapter(detailsOrderAdapter);
        getSharedData = getSharedPreferences("com.codewithpratik.meatchop", MODE_PRIVATE);
        number = getSharedData.getString("mobile_number", "0000");
        Log.d("db12345","number:"+ number );
        reference = rootNode.getReference("User/" + number).child("MyOrder");



        reference.child(id).child("items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("db12345","children:"+snapshot.getChildrenCount() );
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {      Log.d("db12345","children:"+dataSnapshot.getChildrenCount() );
                    CartModel cartModel = dataSnapshot.getValue(CartModel.class);
                    detailListCartModel.add(cartModel);
                    Log.d("db12345",detailListCartModel.get(0).getBoneType());
                }
                detailsOrderAdapter.notifyDataSetChanged();
                         }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void orderDetailsToMyOrder(View view)
    {
        Intent intent = new Intent(this,MyOrder.class);
        startActivity(intent);
    }
}