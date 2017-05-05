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
import com.namtran.lazada.customview.DynamicExpandableLV;
import com.namtran.lazada.model.objectclass.LoaiSanPham;
import com.namtran.lazada.model.trangchu.xulymenu.XuLyJSONMenu;

import java.util.List;

/**
 * Created by namtr on 29/04/2017.
 */

public class ExpandableLVAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<LoaiSanPham> loaiSanPhams;

    public ExpandableLVAdapter(Context context, List<LoaiSanPham> loaiSanPhams) {
        this.context = context;
        this.loaiSanPhams = loaiSanPhams;

        getChilds();
    }

    // lấy các item con
    private void getChilds() {
        XuLyJSONMenu xuLyJSONMenu = new XuLyJSONMenu();
        int count = loaiSanPhams.size();
        for (int i = 0; i < count; i++) {
            int maLoaiSP = loaiSanPhams.get(i).getMaLoaiSP();
            List<LoaiSanPham> childs = xuLyJSONMenu.getTypeOfProductById(maLoaiSP);
            loaiSanPhams.get(i).setChilds(childs);
        }
    }

    @Override
    public int getGroupCount() {
        return loaiSanPhams.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int size = loaiSanPhams.get(groupPosition).getChilds().size();
        return size == 0 ? 0 : 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return loaiSanPhams.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return loaiSanPhams.get(groupPosition).getChilds().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return loaiSanPhams.get(groupPosition).getMaLoaiSP();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return loaiSanPhams.get(groupPosition).getChilds().get(childPosition).getMaLoaiSP();
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
            holder.menuName = (TextView) convertView.findViewById(R.id.txtTenLoaiSP);
            holder.oldColor = holder.menuName.getTextColors(); // lưu màu chữ hiện tại
            holder.indicator = (ImageView) convertView.findViewById(R.id.indicator);

            convertView.setTag(holder);
        } else {
            holder = (MenuItemHolder) convertView.getTag();
        }

        holder.menuName.setText(loaiSanPhams.get(groupPosition).getTenLoaiSP());

        final int numOfChilds = loaiSanPhams.get(groupPosition).getChilds().size();
        if (numOfChilds > 0) { // item chứa các item con
            if (isExpanded) { // nếu item đc mở rộng thì đổi icon và tô màu text
                holder.indicator.setImageResource(R.drawable.ic_remove_black_12dp);
                holder.menuName.setTextColor(ContextCompat.getColor(context, R.color.bgLogo));
            } else { // set về mặc định
                holder.indicator.setImageResource(R.drawable.ic_add_black_12dp);
                holder.menuName.setTextColor(holder.oldColor);
            }
            holder.indicator.setVisibility(View.VISIBLE);
        } else { // ẩn indicator
            holder.indicator.setVisibility(View.INVISIBLE);
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
        DynamicExpandableLV expandedListView = new DynamicExpandableLV(context);
        ExpandableLVAdapter adapter = new ExpandableLVAdapter(context, loaiSanPhams.get(groupPosition).getChilds());
        expandedListView.setAdapter(adapter);
        expandedListView.setPadding(16, 0, 0, 0);
        expandedListView.setGroupIndicator(null); // xóa indicator mặc định
        return expandedListView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private static class MenuItemHolder {
        TextView menuName;
        ImageView indicator;
        ColorStateList oldColor;
    }
}
