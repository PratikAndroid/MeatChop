package com.codewithpratik.meatchop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Address extends AppCompatActivity {

    TextView add_alternate_number,add_nearby_shop;
    TextInputLayout inputFullName,inputPhoneNumber,inputAlternateNumber,inputPinCode,inputState,inputCity,inputHouseNo,inputRoadName,inputNearby;
    Button save;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private FirebaseAuth mAuth;
    public static ArrayList<String> pinCodeList = new ArrayList<>();
    int alternateNumber = 2;
    int addNearBy = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
            findId();
        rootNode = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        pinCodeList.add("802301");

    }

    public void addAlternateNumber(View view)
    {
        if(alternateNumber==2)
        {
            inputAlternateNumber.setVisibility(View.VISIBLE);
            Log.d("altnumb","num1 :" + alternateNumber);
            alternateNumber = 3;
        }
        else if(alternateNumber==3) {
            inputAlternateNumber.setVisibility(View.GONE);
            Log.d("altnumb","num2 :" + alternateNumber);
            alternateNumber=2;
        }


    }
    public void addNearby(View view)
    {
        if(addNearBy==2)
        {
            inputNearby.setVisibility(View.VISIBLE);
            addNearBy=3;
        }
        else if(addNearBy==3)
        {
            inputNearby.setVisibility(View.GONE);
            addNearBy=2;
        }

    }
    public void saveAddress(View view)
    {
        String full_name = inputFullName.getEditText().getText().toString();
        String phone_number = inputPhoneNumber.getEditText().getText().toString();
        String alternate_phone_number = inputAlternateNumber.getEditText().getText().toString();
        String pin_code = inputPinCode.getEditText().getText().toString();
        String state = inputState.getEditText().getText().toString();
        String city = inputCity.getEditText().getText().toString();
        String house = inputHouseNo.getEditText().getText().toString();
        String road  = inputRoadName.getEditText().getText().toString();
        String nearby = inputNearby.getEditText().getText().toString();



        if(pinCodeList.contains(pin_code) && TextUtils.isEmpty(full_name)==false && TextUtils.isEmpty(phone_number)==false && TextUtils.isEmpty(pin_code)==false && TextUtils.isEmpty(state)==false &&
        TextUtils.isEmpty(city)==false && TextUtils.isEmpty(house)==false && TextUtils.isEmpty(road)==false){
        SharedPreferences getSharedData = getSharedPreferences("com.codewithpratik.meatchop",MODE_PRIVATE);
        String number = getSharedData.getString("mobile_number","0000");


        Log.d("db","numberadd :" + number);
        reference = rootNode.getReference("User/"+ number);
        AddressContent addressContent = new AddressContent(full_name,phone_number,alternate_phone_number,pin_code,state,city,house,road,nearby);

        reference.child("address").setValue(addressContent);
        Intent intent = new Intent(Address.this,CartActivity.class);
        startActivity(intent); }

        else if(pinCodeList.contains(pin_code)==false)
        {
            Toast.makeText(Address.this, "Service is not available at entered pin code", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(Address.this, "Please fill all details..", Toast.LENGTH_SHORT).show();
        }


    }

    public void findId()
    {
        add_alternate_number=findViewById(R.id.tv_add_alternative_phone);
        add_nearby_shop = findViewById(R.id.tv_add_nearby);
        inputFullName = findViewById(R.id.full_name_text_input);
        inputPhoneNumber = findViewById(R.id.phone_number_text_input);
        inputPinCode = findViewById(R.id.pincode_text_input);
        inputState = findViewById(R.id.state_text_input);
        inputCity = findViewById(R.id.city_text_input);
        inputHouseNo = findViewById(R.id.house_no_text_input);
        inputRoadName = findViewById(R.id.road_text_input);
        inputNearby = findViewById(R.id.nearby_text_input);
        inputAlternateNumber=findViewById(R.id.alternate_number_text_input);
        save = findViewById(R.id.btn_save_address);
    }

}