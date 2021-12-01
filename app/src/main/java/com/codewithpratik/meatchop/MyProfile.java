package com.codewithpratik.meatchop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyProfile extends AppCompatActivity {

       ImageView myProfileBackArrow;
       TextView myProfileName;
       TextView myProfileMobile;
       TextView myProfileEmail;
       TextView myProfileAddress;
       FirebaseDatabase rootNode;
       DatabaseReference reference;
       private FirebaseAuth mAuth;
       SharedPreferences getSharedData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        myProfileName= findViewById(R.id.tv_my_profile_name);
        myProfileEmail = findViewById(R.id.tv_my_profile_email);
        myProfileMobile = findViewById(R.id.tv_my_profile_mobile);
        myProfileAddress = findViewById(R.id.tv_my_profile_address);
        myProfileBackArrow = findViewById(R.id.my_profile_back_arrow_cart);

        rootNode=FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        getSharedData = getSharedPreferences("com.codewithpratik.meatchop",MODE_PRIVATE);
        String number = getSharedData.getString("mobile_number","0000");
        Log.d("db","number :" + number);
        reference = rootNode.getReference("User/" + number);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String s = (String) snapshot.child("mobileNumber").getValue();
                    String s1 = (String) snapshot.child("email").getValue();
                    myProfileEmail.setText(s1);
                    myProfileMobile.setText(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.child("address").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                   if(snapshot.exists())
                   {

                       String str1 = (String) snapshot.child("keyName").getValue();
                       String str2 = (String) snapshot.child("keyHouseNo").getValue();
                       String str3 = (String) snapshot.child("keyRoadName").getValue();
                       String str4 = (String) snapshot.child("keyLandMark").getValue();
                       String str5 = (String) snapshot.child("keyCity").getValue();
                       String str6 = (String) snapshot.child("keyState").getValue();
                       String str7 = (String) snapshot.child("keyPinCode").getValue();
                       String str8 = (String) snapshot.child("keyPhoneNumber").getValue();
                       String str = str1 + "\n" + "\n" + str2 + ", " + str3 + ", " + str4 + ", " + str5 + ", " + str6 + " (" + str7 + ")" + "\n" + str8;
                       myProfileAddress.setText(str);
                       myProfileName.setText(str1);



                   }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(MyProfile.this,Home.class);
        startActivity(intent);
        finish();


         }
    public void myProfileToHome(View view)
    {
        Intent intent = new Intent(MyProfile.this,Home.class);
        startActivity(intent);
        finish();

    }
}