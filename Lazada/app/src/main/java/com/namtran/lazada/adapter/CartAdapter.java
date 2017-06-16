package com.namtran.lazada.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.namtran.lazada.R;
import com.namtran.lazada.model.objectclass.Product;
import com.namtran.lazada.tools.Converter;

import java.util.List;

/**
 * Created by namtr on 06/16/2017.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private List<Product> productList;

    public CartAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.custom_recycler_cart, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Product product = productList.get(position);
        int price = product.getPrice();

        holder.tvProductName.setText(product.getProductName());
        holder.tvPrice.setText(Converter.formatCurrency(price));

        byte[] data = product.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        holder.imCover.setImageBitmap(bitmap);

        holder.btnDelete.setTag(product.getProductCode());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvPrice;
        ImageView imCover;
        ImageButton btnDelete;

        ViewHolder(View itemView) {
            super(itemView);

            tvProductName = (TextView) itemView.findViewById(R.id.custom_cart_tensp);
            tvPrice = (TextView) itemView.findViewById(R.id.custom_cart_gia);
            imCover = (ImageView) itemView.findViewById(R.id.custom_cart_hinhanh);
            btnDelete = (ImageButton) itemView.findViewById(R.id.custom_cart_btn_delete);
        }
    }
}
