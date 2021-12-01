package com.codewithpratik.meatchop;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CartAdapter extends FirebaseRecyclerAdapter<CartModel,CartAdapter.CartViewHolder> {

  private String userNumber;
  private Context context;
  String str1="CCCBF",str2="CCCBD",str3="CCCF",str4="CCCD",str5="CDF",str6="CDD",str7="TCF",str8="TCD",str9="FC",str10="DC",
          str11="CKF",str12="CKD",str13="CLF",str14="CLD",str15="GL",str16="GM",str17="GK",str18="RF",str19="EF",str20="ED";
        int nt_price;


  public CartAdapter(@NonNull FirebaseRecyclerOptions<CartModel> options, String userNumber, Context context) {
    super(options);
    this.userNumber = userNumber;
    this.context = context;

  }


  @Override
    protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull CartModel model) {
         String id = model.getProductId();

      if(id.equals(str1) || id.equals(str2))
      {
        holder.cart_image.setImageResource(R.drawable.boneless);
      }
      else if(id.equals(str3) || id.equals(str4))
      {
        holder.cart_image.setImageResource(R.drawable.bone);
      }
      else if(id.equals(str5) || id.equals(str6))
      {
        holder.cart_image.setImageResource(R.drawable.legpiece);
      }
      else if(id.equals(str7) || id.equals(str8))
      {
        holder.cart_image.setImageResource(R.drawable.tandoorchicken);
      }
      else if(id.equals(str9))
      {
        holder.cart_image.setImageResource(R.drawable.farmchicken);
      }
      else if(id.equals(str10))
      {
        holder.cart_image.setImageResource(R.drawable.desichicken);
      }
      else if(id.equals(str11) || id.equals(str12))
      {
        holder.cart_image.setImageResource(R.drawable.chickenkeema);
      }
      else if(id.equals(str13) || id.equals(str14))
      {
        holder.cart_image.setImageResource(R.drawable.chickenliver);
      }
      else if(id.equals(str15))
      {
        holder.cart_image.setImageResource(R.drawable.goatliver);
      }
      else if(id.equals(str16))
      {
        holder.cart_image.setImageResource(R.drawable.goatmeat);
      }
      else if(id.equals(str17))
      {
        holder.cart_image.setImageResource(R.drawable.goatkeema);
      }
      else if(id.equals(str18))
      {
        holder.cart_image.setImageResource(R.drawable.fish);
      }
      else if(id.equals(str19) || id.equals(str20))
      {
        holder.cart_image.setImageResource(R.drawable.egg);
      }

      holder.cart_product_name.setText(model.getProductName());
      holder.cart_product_wt.setText(model.getProductWeight());
     holder.cart_product_price.setText(model.getProductPriceString());
      holder.cart_product_type.setText(model.getProductType());
      holder.cart_product_final_wt.setText(model.getFinalWeightString());
    holder.cart_product_final_price.setText(model.getFinalPriceString());

      holder.add_icon.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          FirebaseDatabase.getInstance().getReference().child("User").child(userNumber).child("MyCart").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

              CartModel c =snapshot.getValue(CartModel.class);
              Log.d("adapter","price_add: "+ c.getProductPrice());
              String product_id = c.getProductId();

              int price_add = c.getProductPrice();
              int price1_add = c.getProductPrice1();
              float qty_add = c.getQuantity();
              price_add = price_add + (price1_add/c.getTag());
              snapshot.getRef().child("productPrice").setValue(price_add);
              String price_add_string = "\u20B9"+ String.valueOf(price_add);
              snapshot.getRef().child("finalPriceString").setValue(price_add_string);
              holder.cart_product_final_price.setText(c.getFinalPriceString());
              nt_price = nt_price+price_add;
              // snapshot.getRef().child("grandTotal").setValue(nt_price);
              // holder.cart_product_final_price.setText(str);

              if(product_id.equals(str19) || product_id.equals(str20))
              {
                float qty_egg = qty_add + 1;
                snapshot.getRef().child("quantity").setValue(qty_egg);
                String qty_egg_string = String.valueOf(qty_egg)+ "piece";
                snapshot.getRef().child("finalWeightString").setValue(qty_egg_string);
                holder.cart_product_final_wt.setText(model.getFinalWeightString());
               // holder.cart_product_final_wt.setText(qty_egg_string);
            //    holder.cart_product_final_wt.setText(snapshot.child("finalWeightString").getValue(String.class));
              }
              else if(product_id.equals(str7) || product_id.equals(str8))
              {
                float qty_tc = qty_add + 1;
                snapshot.getRef().child("quantity").setValue(qty_tc);
                String qty_tc_string = String.valueOf(qty_tc)+ "piece";
                snapshot.getRef().child("finalWeightString").setValue(qty_tc_string);
                holder.cart_product_final_wt.setText(model.getFinalWeightString());

              }
              else
              {
                float qty = qty_add+250;
                snapshot.getRef().child("quantity").setValue(qty);
                String qty_string = String.valueOf(qty)+"gms";
                if(qty>=1000)
                {
                  qty = qty/1000;
                  qty_string = String.valueOf(qty)+"kg" ;
                }
                snapshot.getRef().child("finalWeightString").setValue(qty_string);
                holder.cart_product_final_wt.setText(model.getFinalWeightString());
              }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
          });
        }

      });

      holder.subtract_icon.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

          FirebaseDatabase.getInstance().getReference().child("User").child(userNumber).child("MyCart").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            Log.d("holder","subtract 1");
              CartModel c = snapshot.getValue(CartModel.class);
              Log.d("adapter", "price_add: " + c.getProductPrice());
              String product_id = c.getProductId();
              float qty_add = c.getQuantity();
              float minimumQuantity = c.getQuantity1();

              int price = c.getProductPrice();
              int price1 = c.getProductPrice1();
              int tg = c.getTag();

              if (qty_add <= minimumQuantity) {
                Toast.makeText(context, "Minimum Quantity", Toast.LENGTH_SHORT).show();
              } else {
                if (product_id.equals(str19) || product_id.equals(str20)) {

                  if (qty_add >= 12) {
                    float qty_egg = qty_add - 1;
                    snapshot.getRef().child("quantity").setValue(qty_egg);
                    String qty_egg_string = String.valueOf(qty_egg) + "piece";
                    snapshot.getRef().child("finalWeightString").setValue(qty_egg_string);
                    holder.cart_product_final_wt.setText(model.getFinalWeightString());
                  }

                  // holder.cart_product_final_wt.setText(qty_egg_string);
                  //    holder.cart_product_final_wt.setText(snapshot.child("finalWeightString").getValue(String.class));
                } else if (product_id.equals(str7) || product_id.equals(str8)) {
                  float qty_tc = qty_add - 1;
                  snapshot.getRef().child("quantity").setValue(qty_tc);
                  String qty_tc_string = String.valueOf(qty_tc) + "piece";
                  snapshot.getRef().child("finalWeightString").setValue(qty_tc_string);
                  holder.cart_product_final_wt.setText(model.getFinalWeightString());

                } else {
                  float qty = qty_add - 250;
                  snapshot.getRef().child("quantity").setValue(qty);
                  String qty_string = String.valueOf(qty) + "gms";
                  if (qty >= 1000) {
                    qty = qty / 1000;
                    qty_string = String.valueOf(qty) + "kg";
                  }
                  snapshot.getRef().child("finalWeightString").setValue(qty_string);
                  holder.cart_product_final_wt.setText(model.getFinalWeightString());
                }

                price = price - (price1 / tg);
                snapshot.getRef().child("productPrice").setValue(price);
                String price_string = "\u20B9" + String.valueOf(price);
                snapshot.getRef().child("finalPriceString").setValue(price_string);
                Log.d("holder","subtract 2");
                holder.cart_product_final_price.setText(c.getFinalPriceString());
                Log.d("holder","subtract 3");
                nt_price = nt_price + price;
              }
            }






            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
          });

        }
      });

      holder.delete_icon.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          FirebaseDatabase.getInstance().getReference().child("User").child(userNumber).child("MyCart").child(id).removeValue()
                                  .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                     Log.d("adapter","Deleted item");
                                    }
                                  });
        }
      });
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitemview,parent,false);
      return new CartViewHolder(view);
    }

    class CartViewHolder extends RecyclerView.ViewHolder{

      ImageView cart_image;
      TextView cart_product_name;
      TextView cart_product_wt;
      TextView cart_product_price;
      TextView cart_product_type;
      TextView cart_product_final_price;
      ImageView subtract_icon;
      TextView cart_product_final_wt;
      ImageView add_icon;
      ImageView delete_icon;




        public CartViewHolder(@NonNull View itemView) {
          super(itemView);
          cart_image = itemView.findViewById(R.id.cartitemview_image);
          cart_product_name = itemView.findViewById(R.id.cartitemview_product_name);
          cart_product_wt = itemView.findViewById(R.id.cartitemview_product_weight);
          cart_product_price = itemView.findViewById(R.id.cartitemview_product_price);
          cart_product_type = itemView.findViewById(R.id.cartitemview_product_type);
          cart_product_final_price = itemView.findViewById(R.id.cartitemview_product_final_price);
          subtract_icon = itemView.findViewById(R.id.cartitemview_subtract_icon);
          cart_product_final_wt = itemView.findViewById(R.id.cartitemview_finalWeight);
          add_icon = itemView.findViewById(R.id.cartitemview_add_icon);
          delete_icon = itemView.findViewById(R.id.cartitemview_delete_icon);

        }
      }
  }