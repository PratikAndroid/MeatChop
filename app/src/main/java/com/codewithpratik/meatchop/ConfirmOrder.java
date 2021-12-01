package com.codewithpratik.meatchop;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConfirmOrder extends AppCompatActivity {
    MaterialButton place_order;
    TextView item_size;
    TextView item_total_price;
    TextView place_order_grand_total;
    TextView grand_total;
    LinearLayout confirm_address_layout;
    TextView confirm_address_text;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ArrayList<String> orderIdList = new ArrayList<>();
    private FirebaseAuth mAuth;
    int product_price; // total price of order exclusive delivery charges
    int grand_price; // total price of order inclusive delivery charges
    int delivery_charges=20;
    private RecyclerView recyclerView_confirm_order;
    private ConfirmedOrderAdapter confirmedOrderAdapter;
    SharedPreferences getSharedData;
    String number;
    int size;
    RadioButton payment_type_btn;


    private ArrayList<CartModel> confirm_order_arrayList = new ArrayList<>();
    private ArrayList<CartModel> go_to_my_order_content_arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        confirm_address_layout=findViewById(R.id.confirm_address_view_layout);
        confirm_address_text=findViewById(R.id.tv_confirm_address_show);
        payment_type_btn=findViewById(R.id.radio_btn);
        place_order=findViewById(R.id.btn_place_order);
        rootNode = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();


         getSharedData = getSharedPreferences("com.codewithpratik.meatchop", MODE_PRIVATE);
         number = getSharedData.getString("mobile_number", "0000");
        Log.d("db", "number :" + number);
        reference = rootNode.getReference("User/" + number);
        reference.child("address").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                 //   add_delivery_address.setVisibility(View.GONE);
                    confirm_address_layout.setVisibility(View.VISIBLE);

                    String str1 = (String) snapshot.child("keyName").getValue();
                    String str2 = (String) snapshot.child("keyHouseNo").getValue();
                    String str3 = (String) snapshot.child("keyRoadName").getValue();
                    String str4 = (String) snapshot.child("keyLandMark").getValue();
                    String str5 = (String) snapshot.child("keyCity").getValue();
                    String str6 = (String) snapshot.child("keyState").getValue();
                    String str7 = (String) snapshot.child("keyPinCode").getValue();
                    String str8 = (String) snapshot.child("keyPhoneNumber").getValue();

                    String str = str1 + "\n" + "\n" + str2 + ", " + str3 + ", " + str4 + ", " + str5 + ", " + str6 + " (" + str7 + ")" + "\n" + str8;
                    confirm_address_text.setText(str);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        item_size=findViewById(R.id.tv_place_order_total_item);
        item_total_price=findViewById(R.id.tv_item_total_price);
        place_order_grand_total=findViewById(R.id.tv_place_order_grand_total_price);
        grand_total=findViewById(R.id.tv_grand_total);
        recyclerView_confirm_order=findViewById(R.id.recycle_view_confirm_order);


        recyclerView_confirm_order.setHasFixedSize(true);
        recyclerView_confirm_order.setLayoutManager(new LinearLayoutManager(this));
        confirmedOrderAdapter = new ConfirmedOrderAdapter(this,confirm_order_arrayList);
        recyclerView_confirm_order.setAdapter(confirmedOrderAdapter);

        reference = rootNode.getReference("User/" + number);
        reference.child("MyCart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("db12","children:"+snapshot.getChildrenCount() );
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {      Log.d("db12","children:"+dataSnapshot.getChildrenCount() );
                    CartModel cartModel = dataSnapshot.getValue(CartModel.class);
                    confirm_order_arrayList.add(cartModel);
                    Log.d("db12",confirm_order_arrayList.get(0).getBoneType());
                }
                confirmedOrderAdapter.notifyDataSetChanged();
                size =  confirm_order_arrayList.size();
                Log.d("items",String.valueOf(size));
                String item = String.valueOf(size)+" items";
                item_size.setText((item));
                Log.d("items","ArrayList Received Successfully");

                for (int i=0;i<size;i++)
                {       product_price = product_price +confirm_order_arrayList.get(i).getProductPrice();
                    Log.d("items",String.valueOf(i));
                    Log.d("items",String.valueOf(product_price));
                }
                String str= "\u20B9"+String.valueOf(product_price);
                item_total_price.setText(str);
                grand_price=product_price+delivery_charges;
                String str1="\u20B9" + String.valueOf(grand_price);
                grand_total.setText(str1);
                place_order_grand_total.setText(str1);
                Log.d("db12", "size2 :"+ confirm_order_arrayList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void placeOrder(View view)
    {   if(payment_type_btn.isChecked()==false)
         {
             Toast.makeText(ConfirmOrder.this, "Select Payment Type", Toast.LENGTH_SHORT).show();
         }
         else {



        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want place order ? "+ "\n" + "No cancellation policy")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent thanks = new Intent(ConfirmOrder.this, Thanks.class);
                        startActivity(thanks);
                        Log.d("thanks", "Intent working");
                        Constant date_time = new Constant();
                        MyOrderContent content = new MyOrderContent();
                        String strId = reference.push().getKey();
                        content.setOrderId(strId);
                        content.setTotalCost(grand_total.getText().toString());
                        content.setStatus("Pending");
                        String s = date_time.dateTime();
                        String s1 = s.substring(0, 10);
                        String s2 = s.substring(10, 20);
                        content.setDate(s1);
                        content.setTime(s2);
                        String d = "\u20B9" + String.valueOf(delivery_charges);
                        content.setDeliveryCharges(d);
                        content.setPaymentType("Cash");

                        // content.setCartModel();
                        content.setQuantity(String.valueOf(confirm_order_arrayList.size()));
                        Log.d("sizeabcd", String.valueOf(confirm_order_arrayList.size()));
                        for(int i=0;i<confirmedOrderAdapter.getItemCount();i++)
                        {    go_to_my_order_content_arrayList.add(confirm_order_arrayList.get(i));
                            Log.d("sizeabcd", confirm_order_arrayList.get(i).getProductId());
                            // content.setCartModelArrayList(confirm_order_arrayList.get(i));
                        }
                        content.setItems(go_to_my_order_content_arrayList);
                        getSharedData = getSharedPreferences("com.codewithpratik.meatchop", MODE_PRIVATE);
                        number = getSharedData.getString("mobile_number", "0000");
                        Log.d("db", "number :" + number);
                        reference = rootNode.getReference("User/" + number).child("MyOrder");
                        Log.d("tanks", "data saved");

                        reference.child(strId).setValue(content);
                        FirebaseDatabase.getInstance().getReference().child("User").child("6201436438").child("MyOrder").child(strId).setValue(content);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();




    }


    }


}
