package com.codewithpratik.meatchop;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    private Context context;
    private ArrayList<MyOrderContent> orderDetails;

    public MyOrderAdapter(Context context, ArrayList<MyOrderContent> orderDetails) {
        this.context = context;
        this.orderDetails = orderDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorderview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MyOrderContent myOrderContent = orderDetails.get(position);

        holder.orderId.setText(myOrderContent.getOrderId());
        holder.status.setText(myOrderContent.getStatus());
        holder.date.setText(myOrderContent.getDate());
        holder.paymentType.setText(myOrderContent.getPaymentType());
        holder.deliveryCharges.setText(myOrderContent.getDeliveryCharges());
        holder.quantity.setText(myOrderContent.getQuantity());
        holder.totalCost.setText(myOrderContent.getTotalCost());

    }

    @Override
    public int getItemCount() {
       return orderDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

       TextView orderId,status,date,paymentType,deliveryCharges,quantity,totalCost;

       public ViewHolder(@NonNull View itemView) {
            super(itemView);
             itemView.setOnClickListener(this);
            orderId = itemView.findViewById(R.id.tv_my_order_view_order_id);
            status = itemView.findViewById(R.id.tv_my_order_view_status);
            date = itemView.findViewById(R.id.tv_my_order_view_date);
            paymentType = itemView.findViewById(R.id.tv_my_order_view_payment_type);
            deliveryCharges = itemView.findViewById(R.id.tv_my_order_view_delivery_charges);
            quantity = itemView.findViewById(R.id.tv_my_order_view_quantity);
            totalCost = itemView.findViewById(R.id.tv_my_order_view_total_cost);

        }

        @Override
        public void onClick(View v) {
            String s =orderId.getText().toString();
            Intent intent = new Intent(context,DetailsMyOrder.class);
            Log.d("order","orderId :"+ s);
            intent.putExtra("orderId",s);
            context.startActivity(intent);
        }
    }
}
