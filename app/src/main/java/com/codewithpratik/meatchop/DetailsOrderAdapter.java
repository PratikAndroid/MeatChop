package com.codewithpratik.meatchop;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DetailsOrderAdapter extends RecyclerView.Adapter<DetailsOrderAdapter.ViewHolder> {

    private Context context;
    private List<CartModel> detailsOrderList;
    private String id;



    String str1="CCCBF",str2="CCCBD",str3="CCCF",str4="CCCD",str5="CDF",str6="CDD",str7="TCF",str8="TCD",str9="FC",str10="DC",
            str11="CKF",str12="CKD",str13="CLF",str14="CLD",str15="GL",str16="GM",str17="GK",str18="RF",str19="EF",str20="ED";

    public DetailsOrderAdapter(Context context, List<CartModel> detailsOrderList) {
        this.context = context;
        this.detailsOrderList = detailsOrderList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detailsorderview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CartModel cartModel = detailsOrderList.get(position);
        id = detailsOrderList.get(position).getProductId();
        Log.d("productId",id);

        if(id.equals(str1) || id.equals(str2))
        {
            holder.detailsOrder_image.setImageResource(R.drawable.boneless);
        }
        else if(id.equals(str3) || id.equals(str4))
        {
            holder.detailsOrder_image.setImageResource(R.drawable.bone);
        }
        else if(id.equals(str5) || id.equals(str6))
        {
            holder.detailsOrder_image.setImageResource(R.drawable.legpiece);
        }
        else if(id.equals(str7) || id.equals(str8))
        {
            holder.detailsOrder_image.setImageResource(R.drawable.tandoorchicken);
        }
        else if(id.equals(str9))
        {
            holder.detailsOrder_image.setImageResource(R.drawable.farmchicken);
        }
        else if(id.equals(str10))
        {
            holder.detailsOrder_image.setImageResource(R.drawable.desichicken);
        }
        else if(id.equals(str11) || id.equals(str12))
        {
            holder.detailsOrder_image.setImageResource(R.drawable.chickenkeema);
        }
        else if(id.equals(str13) || id.equals(str14))
        {
            holder.detailsOrder_image.setImageResource(R.drawable.chickenliver);
        }
        else if(id.equals(str15))
        {
            holder.detailsOrder_image.setImageResource(R.drawable.goatliver);
        }
        else if(id.equals(str16))
        {
            holder.detailsOrder_image.setImageResource(R.drawable.goatmeat);
        }
        else if(id.equals(str17))
        {
            holder.detailsOrder_image.setImageResource(R.drawable.goatkeema);
        }
        else if(id.equals(str18))
        {
            holder.detailsOrder_image.setImageResource(R.drawable.fish);
        }
        else if(id.equals(str19) || id.equals(str20))
        {
            holder.detailsOrder_image.setImageResource(R.drawable.egg);
        }

        holder.detailsOrder_product_name.setText(cartModel.getProductName());
        holder.detailsOrder_final_wt.setText(cartModel.getFinalWeightString());
        holder.detailsOrder_final_price.setText(String.valueOf(cartModel.getFinalPriceString()));
        holder.detailsOrder_product_type.setText(cartModel.getProductType());


    }

    @Override
    public int getItemCount() {
        return detailsOrderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        TextView detailsOrder_product_name;
        ImageView detailsOrder_image;
        TextView detailsOrder_final_wt;
        TextView detailsOrder_product_type;
        TextView detailsOrder_final_price;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            detailsOrder_image=itemView.findViewById(R.id.details_order_image);
            detailsOrder_product_name=itemView.findViewById(R.id.details_order_product_name);
            detailsOrder_final_wt=itemView.findViewById(R.id.details_order_final_wt);
            detailsOrder_product_type=itemView.findViewById(R.id.details_order_product_type);
            detailsOrder_final_price=itemView.findViewById(R.id.details_order_product_final_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("intent","gasdf");
                    Intent intent = new Intent(context,DetailsMyOrder.class);
                    context.startActivity(intent);
                }
            });
        }


    }
}
