package com.codewithpratik.meatchop;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ConfirmedOrderAdapter extends RecyclerView.Adapter<ConfirmedOrderAdapter.ViewHolder> {

    private Context context;
    private List<CartModel> confirmedOrderDetailsList;
    private String id;
    int nt_price;
    String str1="CCCBF",str2="CCCBD",str3="CCCF",str4="CCCD",str5="CDF",str6="CDD",str7="TCF",str8="TCD",str9="FC",str10="DC",
            str11="CKF",str12="CKD",str13="CLF",str14="CLD",str15="GL",str16="GM",str17="GK",str18="RF",str19="EF",str20="ED";

    public ConfirmedOrderAdapter(Context context, List<CartModel> confirmedOrderDetailsList) {
        this.context = context;
        this.confirmedOrderDetailsList = confirmedOrderDetailsList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirmorderdetailsview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartModel cartModel = confirmedOrderDetailsList.get(position);
        id =confirmedOrderDetailsList.get(position).getProductId();
        Log.d("productId",id);

        if(id.equals(str1) || id.equals(str2))
        {
            holder.confirm_order_image.setImageResource(R.drawable.boneless);
        }
        else if(id.equals(str3) || id.equals(str4))
        {
            holder.confirm_order_image.setImageResource(R.drawable.bone);
        }
        else if(id.equals(str5) || id.equals(str6))
        {
            holder.confirm_order_image.setImageResource(R.drawable.legpiece);
        }
        else if(id.equals(str7) || id.equals(str8))
        {
            holder.confirm_order_image.setImageResource(R.drawable.tandoorchicken);
        }
        else if(id.equals(str9))
        {
            holder.confirm_order_image.setImageResource(R.drawable.farmchicken);
        }
        else if(id.equals(str10))
        {
            holder.confirm_order_image.setImageResource(R.drawable.desichicken);
        }
        else if(id.equals(str11) || id.equals(str12))
        {
            holder.confirm_order_image.setImageResource(R.drawable.chickenkeema);
        }
        else if(id.equals(str13) || id.equals(str14))
        {
            holder.confirm_order_image.setImageResource(R.drawable.chickenliver);
        }
        else if(id.equals(str15))
        {
            holder.confirm_order_image.setImageResource(R.drawable.goatliver);
        }
        else if(id.equals(str16))
        {
            holder.confirm_order_image.setImageResource(R.drawable.goatmeat);
        }
        else if(id.equals(str17))
        {
            holder.confirm_order_image.setImageResource(R.drawable.goatkeema);
        }
        else if(id.equals(str18))
        {
            holder.confirm_order_image.setImageResource(R.drawable.fish);
        }
        else if(id.equals(str19) || id.equals(str20))
        {
            holder.confirm_order_image.setImageResource(R.drawable.egg);
        }

        holder.confirm_order_product_name.setText(cartModel.getProductName());
        holder.confirm_order_final_wt.setText(cartModel.getFinalWeightString());
        holder.confirm_order_final_price.setText(String.valueOf(cartModel.getFinalPriceString()));
        holder.confirm_order_product_type.setText(cartModel.getProductType());
    }

    @Override
    public int getItemCount() {
        return confirmedOrderDetailsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView confirm_order_product_name;
        ImageView confirm_order_image;
        TextView confirm_order_final_wt;
        TextView confirm_order_product_type;
        TextView confirm_order_final_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            confirm_order_product_name=itemView.findViewById(R.id.confirmed_order_product_name);
            confirm_order_image=itemView.findViewById(R.id.confirmed_order_image);
            confirm_order_final_wt=itemView.findViewById(R.id.confirmed_order_final_wt);
            confirm_order_product_type=itemView.findViewById(R.id.confirmed_order_product_type);
            confirm_order_final_price=itemView.findViewById(R.id.confirmed_order_product_final_price);
        }
    }
}
