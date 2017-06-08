package com.namtran.lazada.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.namtran.lazada.R;
import com.namtran.lazada.customview.DynamicExpandableListView;
import com.namtran.lazada.model.objectclass.ProductType;
import com.namtran.lazada.model.home.handlingmenu.JsonParser;

import java.util.List;

/**
 * Created by namtr on 29/04/2017.
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<ProductType> productTypeList;

    public ExpandableListViewAdapter(Context context, List<ProductType> productTypeList) {
        this.context = context;
        this.productTypeList = productTypeList;

        getChilds();
    }

    // lấy các item con
    private void getChilds() {
        JsonParser menuModel = new JsonParser();
        int count = productTypeList.size();
        for (int i = 0; i < count; i++) {
            int maLoaiSP = productTypeList.get(i).getProductTypeCode();
            List<ProductType> childs = menuModel.getProductTypesList(maLoaiSP);
            productTypeList.get(i).setChilds(childs);
        }
    }

    @Override
    public int getGroupCount() {
        return productTypeList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int size = productTypeList.get(groupPosition).getChilds().size();
        return size == 0 ? 0 : 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return productTypeList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return productTypeList.get(groupPosition).getChilds().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return productTypeList.get(groupPosition).getProductTypeCode();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return productTypeList.get(groupPosition).getChilds().get(childPosition).getProductTypeCode();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final MenuItemHolder holder;
        if (convertView == null) {
            holder = new MenuItemHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_menu_parent, parent, false);
            holder.mTVMenuName = (TextView) convertView.findViewById(R.id.txtTenLoaiSP);
            holder.mOldColor = holder.mTVMenuName.getTextColors(); // lưu màu chữ hiện tại
            holder.mIVIndicator = (ImageView) convertView.findViewById(R.id.indicator);

            convertView.setTag(holder);
        } else {
            holder = (MenuItemHolder) convertView.getTag();
        }

        holder.mTVMenuName.setText(productTypeList.get(groupPosition).getProductTypeName());

        final int numOfChilds = productTypeList.get(groupPosition).getChilds().size();
        if (numOfChilds > 0) { // item chứa các item con
            if (isExpanded) { // nếu item đc mở rộng thì đổi icon và tô màu text
                holder.mIVIndicator.setImageResource(R.drawable.ic_remove_black_12dp);
                holder.mTVMenuName.setTextColor(ContextCompat.getColor(context, R.color.bgLogo));
            } else { // set về mặc định
                holder.mIVIndicator.setImageResource(R.drawable.ic_add_black_12dp);
                holder.mTVMenuName.setTextColor(holder.mOldColor);
            }
            holder.mIVIndicator.setVisibility(View.VISIBLE);
        } else { // ẩn mIVIndicator
            holder.mIVIndicator.setVisibility(View.INVISIBLE);
        }

        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        DynamicExpandableListView expandedListView = new DynamicExpandableListView(context);
        ExpandableListViewAdapter adapter = new ExpandableListViewAdapter(context, productTypeList.get(groupPosition).getChilds());
        expandedListView.setAdapter(adapter);
        expandedListView.setPadding(16, 0, 0, 0);
        expandedListView.setGroupIndicator(null); // xóa mIVIndicator mặc định
        return expandedListView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private static class MenuItemHolder {
        TextView mTVMenuName;
        ImageView mIVIndicator;
        ColorStateList mOldColor;
    }
}
