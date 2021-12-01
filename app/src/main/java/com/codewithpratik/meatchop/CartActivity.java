package com.codewithpratik.meatchop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    ImageView backArrowCart;
    TextView confirm_order;
    TextView show_address;
    LinearLayout show_address_layout;
    MaterialButton add_delivery_address;
    public ArrayList<CartModel> MyCartDetails = new ArrayList<>();
    LinearLayout emptyCartLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
           findId();

           String number = sharedNumber();
            FirebaseDatabase.getInstance().getReference().child("User").child(number).child("address").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                           setAddress(snapshot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
           FirebaseRecyclerOptions<CartModel> options = new FirebaseRecyclerOptions.Builder<CartModel>()
                                                                                 .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(number).child("MyCart"),CartModel.class)
                                                                                  .build();

           cartAdapter = new CartAdapter(options,number,this);
           recyclerView.setAdapter(cartAdapter);
           cartAdapter.startListening();
    }

    public void findId()
    {
        recyclerView = findViewById(R.id.recycle_view);
        backArrowCart = findViewById(R.id.back_arrow_cart);
        confirm_order = findViewById(R.id.tv_confirm_order);
        add_delivery_address = findViewById(R.id.ad_delivery_address);
        show_address = findViewById(R.id.tv_address_show);
        show_address_layout = findViewById(R.id.address_view_layout);
        emptyCartLayout = findViewById(R.id.my_cart_empty_layout);

    }
    public String sharedNumber()
    {
        SharedPreferences getSharedData = getSharedPreferences("com.codewithpratik.meatchop", MODE_PRIVATE);
        return getSharedData.getString("mobile_number", "0000");
    }
    public void setAddress(DataSnapshot snapshot)
    {

        add_delivery_address.setVisibility(View.GONE);
        show_address_layout.setVisibility(View.VISIBLE);


        String str1 = (String) snapshot.child("keyName").getValue();
        String str2 = (String) snapshot.child("keyHouseNo").getValue();
        String str3 = (String) snapshot.child("keyRoadName").getValue();
        String str4 = (String) snapshot.child("keyLandMark").getValue();
        String str5 = (String) snapshot.child("keyCity").getValue();
        String str6 = (String) snapshot.child("keyState").getValue();
        String str7 = (String) snapshot.child("keyPinCode").getValue();
        String str8 = (String) snapshot.child("keyPhoneNumber").getValue();
        String str9 = "Delivery Address";
        String str = str9 + "\n" + "\n" +str1 + "\n"  + str2 + ", " + str3 + ", " + str4 + ", " + str5 + ", " + str6 + " (" + str7 + ")" + "\n" + str8;
        show_address.setText(str);
    }

    public void backArrowCart(View view)
    {
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);

    }
    public void goToAddDeliveryAddress(View view)
    {
        Intent goToAddDeliveryAddress = new Intent(this,Address.class);
        startActivity(goToAddDeliveryAddress);
    }
    public void confirmOrder(View view)
    {
        if(TextUtils.isEmpty(show_address.getText().toString())) {
        Toast.makeText(CartActivity.this, "Please add address...", Toast.LENGTH_SHORT).show(); }
        else{
        Intent goToConfirmOrder = new Intent(this,ConfirmOrder.class);
        goToConfirmOrder.putExtra("confirm", MyCartDetails);
        startActivity(goToConfirmOrder);}
    }

}