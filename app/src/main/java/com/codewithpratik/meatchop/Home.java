package com.codewithpratik.meatchop;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menubar;
    private long pressedTime;

    int i;
    TextView ownerNumber;
    TextView ownerEmail;
    TextView UserMobileNo;
    TextView UserEmailId;
    LinearLayout onClickHome;
    LinearLayout onClickProfile;
    LinearLayout onClickLog;
    LinearLayout onClickMyOrder;
    ImageView boneless_curry_cut_img;
    TextView boneless_curry_cut_name;
    TextView boneless_curry_cut_wt;
    TextView chicken_type_boneless;
    TextView boneless_farm_chicken_curry_cut_price;
    TextView chicken_type_farm;
    TextView boneless_desi_chicken_curry_cut_price;
    Button farm_chicken_curry_cut_btn;
    Button desi_chicken_curry_cut_btn;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private FirebaseAuth mAuth;
    SharedPreferences getSharedData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findId();
       rootNode = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        getSharedData = getSharedPreferences("com.codewithpratik.meatchop",MODE_PRIVATE);
        String number = getSharedData.getString("mobile_number","0000");
        Log.d("db","number :" + number);

        reference = rootNode.getReference("User/"+ number);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    String s = (String) snapshot.child("email").getValue();
                    String s1 = (String) snapshot.child("mobileNumber").getValue();
                    UserEmailId.setText(s);
                    UserMobileNo.setText(s1);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference = rootNode.getReference("User/"+ number).child("MyCart");
        String s = ownerNumber.getText().toString().trim();
        ownerNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + s));
                startActivity(intent);
            }
        });
        ownerEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("mailto:" + ownerEmail.getText().toString()));
               startActivity(Intent.createChooser(intent,"Send via.."));
               finish();

            }
        });

    }

    @Override
    public void onBackPressed()
    {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
           super.onBackPressed();
           Intent intent = new Intent(Intent.ACTION_MAIN);
           intent.addCategory(Intent.CATEGORY_HOME);
           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           startActivity(intent);

            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();

    }
    public void menuBar(View view)
    {
        //Open drawer
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        // Open drawer Layout
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void goToCart(View view)
    {
        Intent cartIntent = new Intent(this, CartActivity.class);
        startActivity(cartIntent);
        Log.d("details","ArrayList Passed Successfully");
    }

    public void closeNavigationDrawer(View view)
    {
        closeDrawer(drawerLayout);
    }
    public static void closeDrawer(DrawerLayout drawerLayout) {

        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void gotoMyProfile(View view)
    {
        Intent toMyProfile = new Intent(this,MyProfile.class);
        startActivity(toMyProfile);

    }

   public void goToLogOut(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Log out ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Home.this, MainActivity.class);
                        SharedPreferences sharedPreferences = getSharedPreferences("com.codewithpratik.meatchop",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLogIn",false);
                        editor.commit();
                        startActivity(intent);
                        finish();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        closeDrawer(drawerLayout);
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void goToMyOrder(View view)
    {
        Intent toMyOrder = new Intent(this,MyOrder.class);
        startActivity(toMyOrder); }

        public void findId()
       {
        ownerNumber = findViewById(R.id.tv_owner_number);
        ownerEmail = findViewById(R.id.tv_owner_email);
        UserMobileNo=findViewById(R.id.tv_user_mobile_no);
        UserEmailId = findViewById(R.id.tv_user_email_id);
        onClickMyOrder=findViewById(R.id.myOrderClick);
        onClickLog=findViewById(R.id.login_click);
        onClickProfile=findViewById(R.id.myProfile_click);
        onClickHome=findViewById(R.id.home_click);
        menubar = findViewById(R.id.menu_bar);
        drawerLayout=findViewById(R.id.drawer_layout);
        boneless_curry_cut_img=findViewById(R.id.img_chicken_curry_cut_boneless);
        boneless_curry_cut_name=findViewById(R.id.tv_chicken_curry_cut_boneless);
        boneless_curry_cut_wt=findViewById(R.id.tv_chicken_curry_cut_bone_wt);
        chicken_type_boneless=findViewById(R.id.tv_boneless_curry_cut);
        boneless_farm_chicken_curry_cut_price=findViewById(R.id.tv_chicken_curry_boneless_price_farm);
        chicken_type_farm=findViewById(R.id.tv_farm_chicken);
        boneless_desi_chicken_curry_cut_price=
                farm_chicken_curry_cut_btn=findViewById(R.id.btn_add_farm_chicken_curry_cut_boneless);
        desi_chicken_curry_cut_btn=findViewById(R.id.btn_add_desi_chicken_curry_cut_boneless);
    }

    public void addDetailsFarmCurryCutBoneless(View view)
    {
        CartModel cccbf = new CartModel();
        cccbf.setProductId("CCCBF");
        cccbf.setFinalWeightString("500gms");
        cccbf.setFinalPriceString("\u20B9 110");
        cccbf.setProductImg("boneless");
        cccbf.setProductName("Chicken Curry Cuts");
        cccbf.setBoneType("boneless");
        cccbf.setProductPriceString("\u20B9 110");
        cccbf.setProductPrice(110);
        cccbf.setProductPrice1(110);
        cccbf.setProductWeight("500gms");
        cccbf.setQuantity(500);
        cccbf.setQuantity1(500);
        cccbf.setProductType("Farm");
        cccbf.setTag(2);
        cccbf.setGrandTotal(0);
     //  itemDetails.add(cccbf);


        reference.child("CCCBF").setValue(cccbf);
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
        Log.d("details","1");


    }

    public void addDetailsDesiCurryCutBoneless(View view)
    {

        CartModel cccbd = new CartModel();
        cccbd.setProductId("CCCBD");
        cccbd.setFinalWeightString("500gms");
        cccbd.setFinalPriceString("\u20B9 180");
        cccbd.setProductImg("@drawable/boneless");
        cccbd.setProductName("Chicken Curry Cuts");
        cccbd.setBoneType("boneless");
        cccbd.setProductPriceString("\u20B9 180");
        cccbd.setProductPrice(180);
        cccbd.setProductPrice1(180);
        cccbd.setProductWeight("500gms");
        cccbd.setQuantity(500);
        cccbd.setQuantity1(500);
        cccbd.setProductType("Desi");
        cccbd.setTag(2);
        cccbd.setGrandTotal(0);
        reference.child("CCCBD").setValue(cccbd);
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
        Log.d("details","2");


    }

    public void addDetailsFarmCurryCutBone(View view)
    {
        CartModel cccf = new CartModel();
        cccf.setProductId("CCCF");
        cccf.setFinalWeightString("500gms");
        cccf.setFinalPriceString("\u20B9 100");
        cccf.setProductImg("@drawable/bone");
        cccf.setProductName("Chicken Curry Cuts");
        cccf.setBoneType("bone");
        cccf.setProductPriceString("\u20B9 100");
        cccf.setProductPrice(100);
        cccf.setProductPrice1(100);
        cccf.setProductWeight("500gms");
        cccf.setQuantity(500);
        cccf.setQuantity1(500);
        cccf.setProductType("Farm");
        cccf.setTag(2);
        cccf.setGrandTotal(0);
        reference.child("CCCF").setValue(cccf);
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
        Log.d("details","3");

    }

    public void addDetailsDesiCurryCutBone(View view)
    {
        CartModel cccd = new CartModel();
        cccd.setProductId("CCCD");
        cccd.setFinalWeightString("500gms");
        cccd.setFinalPriceString("\u20B9 170");
        cccd.setProductImg("@drawable/bone");
        cccd.setProductName("Chicken Curry Cuts");
        cccd.setBoneType("bone");
        cccd.setProductPriceString("\u20B9 170");
        cccd.setProductPrice(170);
        cccd.setProductPrice1(170);
        cccd.setProductWeight("500gms");
        cccd.setQuantity(500);
        cccd.setQuantity1(500);
        cccd.setProductType("Desi");
        cccd.setTag(2);
        cccd.setGrandTotal(0);
        reference.child("CCCD").setValue(cccd);
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
        Log.d("details","4");
    }

    public void addDetailsFarmChickenDrumStick(View view)
    {
        CartModel cdf = new CartModel();
        cdf.setProductId("CDF");
        cdf.setFinalWeightString("500gms");
        cdf.setFinalPriceString("\u20B9 150");
        cdf.setProductImg("@drawable/legpiece");
        cdf.setProductName("Chicken Drumstick");
        cdf.setBoneType("bone");
        cdf.setProductPriceString("\u20B9 150");
        cdf.setProductPrice(150);
        cdf.setProductPrice1(150);
        cdf.setProductWeight("500gms");
        cdf.setQuantity(500);
        cdf.setQuantity1(500);
        cdf.setProductType("Farm");
        cdf.setTag(2);
        cdf.setGrandTotal(0);
        reference.child("CDF").setValue(cdf);
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
        Log.d("details","5");
    }

    public void addDetailsDesiChickenDrumStick(View view)
    {
        CartModel cdd = new CartModel();
        cdd.setProductId("CDD");
        cdd.setFinalWeightString("500gms");
        cdd.setFinalPriceString("\u20B9 250");
        cdd.setProductImg("@drawable/legpiece");
        cdd.setProductName("Chicken Drumstick");
        cdd.setBoneType("bone");
        cdd.setProductPriceString("\u20B9 250");
        cdd.setProductPrice(250);
        cdd.setProductPrice1(250);
        cdd.setProductWeight("500gms");
        cdd.setQuantity(500);
        cdd.setQuantity1(500);
        cdd.setProductType("Desi");
        cdd.setTag(2);
        cdd.setGrandTotal(0);
        reference.child("CDD").setValue(cdd);
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
        Log.d("details","6");
    }

    public void addDetailsFarmTandoriChicken(View view)
    {
        CartModel tcf = new CartModel();
        tcf.setProductId("TCF");
        tcf.setFinalWeightString("1 piece");
        tcf.setFinalPriceString("\u20B9 160");
        tcf.setProductImg("@drawable/tandoorchicken");
        tcf.setProductName("Tandori Chicken");
        tcf.setBoneType("bone");
        tcf.setProductPriceString("\u20B9 160");
        tcf.setProductPrice(160);
        tcf.setProductPrice1(160);
        tcf.setProductWeight("1 piece");
        tcf.setQuantity(1);
        tcf.setQuantity1(1);
        tcf.setProductType("Farm");
        tcf.setTag(1);
        tcf.setGrandTotal(0);
        reference.child("TCF").setValue(tcf);
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
        Log.d("details","7");
    }

    public void addDetailsDesiTandoriChicken(View view)
    {
        CartModel tcd = new CartModel();
        tcd.setProductId("TCD");
        tcd.setFinalWeightString("1 piece");
        tcd.setFinalPriceString("\u20B9 320");
        tcd.setProductImg("@drawable/tandoorchicken");
        tcd.setProductName("Tandori Chicken");
        tcd.setBoneType("bone");
        tcd.setProductPriceString("\u20B9 320");
        tcd.setProductPrice(320);
        tcd.setProductPrice1(320);
        tcd.setProductWeight("1 piece");
        tcd.setQuantity(1);
        tcd.setQuantity1(1);
        tcd.setProductType("Desi");
        tcd.setTag(1);
        tcd.setGrandTotal(0);
        reference.child("TCD").setValue(tcd);
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
        Log.d("details","8");
    }

    public void addDetailsFarmChicken(View view)
    {
        CartModel fc = new CartModel();
        fc.setProductId("FC");
        fc.setFinalWeightString("1kg");
        fc.setFinalPriceString("\u20B9 150");
        fc.setProductImg("@drawable/farmchicken");
        fc.setProductName("Farm Chicken");
        fc.setBoneType("bone");
        fc.setProductPriceString("\u20B9 150");
        fc.setProductPrice(150);
        fc.setProductPrice1(150);
        fc.setProductWeight("1kg");
        fc.setQuantity(1000);
        fc.setQuantity1(1000);
        fc.setProductType("Farm");
        fc.setTag(4);
        fc.setGrandTotal(0);
        reference.child("FC").setValue(fc);Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
        Log.d("details","9");
    }

    public void addDetailsDesiChicken(View view)
    {
        CartModel dc = new CartModel();
        dc.setProductId("DC");
        dc.setFinalWeightString("500gms");
        dc.setFinalPriceString("\u20B9 300");
        dc.setProductImg("@drawable/desichicken");
        dc.setProductName("Desi Chicken");
        dc.setBoneType("bone");
        dc.setProductPriceString("\u20B9 300");
        dc.setProductPrice(300);
        dc.setProductPrice1(300);
        dc.setProductWeight("1kg");
        dc.setQuantity(1000);
        dc.setQuantity1(1000);
        dc.setProductType("Desi");
        dc.setTag(4);
        dc.setGrandTotal(0);
        reference.child("DC").setValue(dc);
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
        Log.d("details","10");
    }

    public void addDetailsFarmChickenKheema(View view)
    {
        CartModel ckf = new CartModel();
        ckf.setProductId("CKF");
        ckf.setFinalWeightString("500gms");
        ckf.setFinalPriceString("\u20B9 120");
        ckf.setProductImg("@drawable/chickenkeema");
        ckf.setProductName("Chicken Kheema");
        ckf.setBoneType("boneless");
        ckf.setProductPriceString("\u20B9 120");
        ckf.setProductPrice(120);
        ckf.setProductPrice1(120);
        ckf.setProductWeight("500gms");
        ckf.setQuantity(500);
        ckf.setQuantity1(500);
        ckf.setProductType("Farm");
        ckf.setTag(2);
        ckf.setGrandTotal(0);
        reference.child("CKF").setValue(ckf);
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
        Log.d("details","11");
    }
    public void addDetailsDesiChickenKheema(View view)
    {
        CartModel ckd = new CartModel();
        ckd.setProductId("CKD");
        ckd.setFinalWeightString("500gms");
        ckd.setFinalPriceString("\u20B9 190");
        ckd.setProductImg("@drawable/chickenkeema");
        ckd.setProductName("Chicken Kheema");
        ckd.setBoneType("boneless");
        ckd.setProductPriceString("\u20B9 190");
        ckd.setProductPrice(190);
        ckd.setProductPrice1(190);
        ckd.setProductWeight("500gms");
        ckd.setQuantity(500);
        ckd.setQuantity1(500);
        ckd.setProductType("Desi");
        ckd.setTag(2);
        ckd.setGrandTotal(0);
        reference.child("CKD").setValue(ckd);
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
        Log.d("details","12");
    }

    public void addDetailsFarmChickenLiver(View view)
    {
        CartModel clf = new CartModel();
        clf.setProductId("CLF");
        clf.setFinalWeightString("500gms");
        clf.setFinalPriceString("\u20B9 120");
        clf.setProductImg("@drawable/chickenliver");
        clf.setProductName("Chicken Liver");
        clf.setBoneType("boneless");
        clf.setProductPriceString("\u20B9 120");
        clf.setProductPrice(120);
        clf.setProductPrice1(120);
        clf.setProductWeight("500gms");
        clf.setQuantity(500);
        clf.setQuantity1(500);
        clf.setProductType("Farm");
        clf.setTag(2);
        clf.setGrandTotal(0);
        reference.child("CLF").setValue(clf);
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
        Log.d("details","13");
    }

    public void addDetailsDesiChickenLiver(View view)
    {
        CartModel cld = new CartModel();
        cld.setProductId("CLD");
        cld.setFinalWeightString("500gms");
        cld.setFinalPriceString("\u20B9 220");
        cld.setProductImg("@drawable/chickenliver");
        cld.setProductName("Chicken Liver");
        cld.setBoneType("boneless");
        cld.setProductPriceString("\u20B9 220");
        cld.setProductPrice(220);
        cld.setProductPrice1(220);
        cld.setProductWeight("500gms");
        cld.setQuantity(500);
        cld.setQuantity1(500);
        cld.setProductType("Desi");
        cld.setTag(2);
        cld.setGrandTotal(0);
        reference.child("CLD").setValue(cld);
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
        Log.d("details","14");

    }

    public void addDetailsGoatLiver(View view)
    {
        CartModel gl = new CartModel();
        gl.setProductId("GL");
        gl.setFinalWeightString("500gms");
        gl.setFinalPriceString("\u20B9 400");
        gl.setProductImg("@drawable/goatliver");
        gl.setProductName("Goat Liver");
        gl.setBoneType("boneless");
        gl.setProductPriceString("\u20b9 400");
        gl.setProductPrice(400);
        gl.setProductPrice1(400);
        gl.setProductWeight("500gms");
        gl.setQuantity(500);
        gl.setQuantity1(500);
        gl.setProductType("Goat");
        gl.setTag(2);
        gl.setGrandTotal(0);
        reference.child("GL").setValue(gl);
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
        Log.d("details","15");
    }

    public void addDetailsGoatMeat(View view)
    {
        CartModel gm = new CartModel();
        gm.setProductId("GM");
        gm.setFinalWeightString("500gms");
        gm.setFinalPriceString("\u20B9 500");
        gm.setProductImg("@drawable/goatmeat");
        gm.setProductName("Goat Meat");
        gm.setBoneType("bone");
        gm.setProductPriceString("\u20B9 500");
        gm.setProductPrice(500);
        gm.setProductPrice1(500);
        gm.setProductWeight("500gms");
        gm.setQuantity(500);
        gm.setQuantity1(500);
        gm.setProductType("Goat");
        gm.setTag(2);
        gm.setGrandTotal(0);
        reference.child("GM").setValue(gm);
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
        Log.d("details","16");
    }

    public void addDetailsGoatKheema(View view)
    {
        CartModel gk = new CartModel();
        gk.setProductId("GK");
        gk.setFinalWeightString("500gms");
        gk.setFinalPriceString("\u20B9 500");
        gk.setProductImg("@drawable/goatkeema");
        gk.setProductName("Goat Kheema");
        gk.setBoneType("boneless");
        gk.setProductPriceString("\u20B9 500");
        gk.setProductPrice(500);
        gk.setProductPrice1(500);
        gk.setProductWeight("500gms");
        gk.setQuantity(500);
        gk.setQuantity1(500);
        gk.setProductType("Goat");
        gk.setTag(2);
        gk.setGrandTotal(0);
        reference.child("GK").setValue(gk);
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
        Log.d("details","17");
    }

    public void addDetailsRehuFish(View view)
    {
        CartModel rf = new CartModel();
        rf.setProductId("RF");
        rf.setFinalWeightString("500gms");
        rf.setFinalPriceString("\u20B9 100");
        rf.setProductImg("@drawable/fish");
        rf.setProductName("Rehu Fish");
        rf.setBoneType("bone");
        rf.setProductPriceString("\u20B9 100");
        rf.setProductPrice(100);
        rf.setProductPrice1(100);
        rf.setProductWeight("500gms");
        rf.setQuantity(500);
        rf.setQuantity1(500);
        rf.setProductType("Fish");
        rf.setTag(2);
        rf.setGrandTotal(0);
        reference.child("RF").setValue(rf);
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
        Log.d("details","18");

    }

    public void addDetailsFarmEgg(View view)
    {
        CartModel ef = new CartModel();
        ef.setProductId("EF");
        ef.setFinalWeightString("12 pieces");
        ef.setFinalPriceString("\u20B9 100");
        ef.setProductImg("@drawable/egg");
        ef.setProductName("Egg");
        ef.setBoneType("bone");
        ef.setProductPriceString("\u20B9 100");
        ef.setProductPrice(100);
        ef.setProductPrice1(100);
        ef.setProductWeight("12 piece");
        ef.setProductType("Farm");
        ef.setQuantity(12);
        ef.setQuantity1(12);
        ef.setTag(12);
        ef.setGrandTotal(0);
        reference.child("EF").setValue(ef);
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
        Log.d("details","19");
    }

    public void addDetailsDesiEgg(View view)
    {
        CartModel ed = new CartModel();
        ed.setProductId("ED");
        ed.setFinalWeightString("12 pieces");
        ed.setFinalPriceString("\u20B9 140");
        ed.setProductImg("@drawable/egg");
        ed.setProductName("Egg");
        ed.setBoneType("bone");
        ed.setProductPriceString("\u20B9 140");
        ed.setProductPrice(140);
        ed.setProductPrice1(140);
        ed.setProductWeight("12 pieces");
        ed.setQuantity(12);
        ed.setQuantity1(12);
        ed.setProductType("Desi");
        ed.setTag(12);
        ed.setGrandTotal(0);
        reference.child("ED").setValue(ed);
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
        Log.d("details","20");
    }




}