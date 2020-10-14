package com.kamilamalikova.help.ui.terminal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kamilamalikova.help.R;
import com.kamilamalikova.help.model.Product;
import com.kamilamalikova.help.ui.terminal.fragments.ApproveOrderFragment;

import java.util.List;

public class OrderDetailAdapter extends BaseAdapter {

    List<Product> productList;
    LayoutInflater mInflater;
    Context context;
    ApproveOrderFragment fragment;
    public OrderDetailAdapter(List<Product> productList, Context context, ApproveOrderFragment fragment) {
        this.productList = productList;
        this.context = context;
        this.fragment = fragment;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.menu_product_item_layout, null);
        }

        TextView menuItemId = convertView.findViewById(R.id.menuItemId);
        ImageButton addFromMenuBtn = convertView.findViewById(R.id.addFromMenuBtn);
        ImageButton minusFromMenuBtn = convertView.findViewById(R.id.minusFromMenuBtn);
        TextView menuItemNameTextView = convertView.findViewById(R.id.menuItemNameTextView);
        final TextView menuItemQtyTextView = convertView.findViewById(R.id.menuItemQtyTextView);
        TextView menuItemCostTextView = convertView.findViewById(R.id.menuItemCostTextView);
        final EditText menuItemSelectedQtyEditText = convertView.findViewById(R.id.menuItemSelectedQtyTextView);

        menuItemQtyTextView.setVisibility(View.INVISIBLE);

        final View finalConvertView = convertView;
        final Product product = productList.get(position);

        menuItemId.setText((product.getId()+""));
        menuItemNameTextView.setText((product.getProductName()+""));
        menuItemCostTextView.setText(("Цена: "+": "+product.getCost()));
        menuItemSelectedQtyEditText.setText((product.getBuyQty()+""));

        addFromMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double qty = (menuItemSelectedQtyEditText.getText().toString().isEmpty()) ? 0.0 : Double.parseDouble(menuItemSelectedQtyEditText.getText().toString());
                qty+=1.0;
                if (qty > product.getInStockQty()){
                    Toast.makeText(finalConvertView.getContext(), "Превышен лимит", Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                product.setBuyQty(qty);
                menuItemSelectedQtyEditText.setText((product.getBuyQty()+""));
                menuItemQtyTextView.setText(("Кол-во: "+" "+ (product.getInStockQty() - product.getBuyQty()) +""));
            }
        });


        minusFromMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double qty = (menuItemSelectedQtyEditText.getText().toString().isEmpty()) ? 0.0 : Double.parseDouble(menuItemSelectedQtyEditText.getText().toString());
                qty-=1.0;
                product.setBuyQty(qty);
                menuItemSelectedQtyEditText.setText((product.getBuyQty()+""));
                menuItemQtyTextView.setText(("Кол-во: "+" "+ (product.getInStockQty() - product.getBuyQty()) +""));
                if (qty < 0.0){
                    return;
                }
            }
        });



        return convertView;
    }
}
