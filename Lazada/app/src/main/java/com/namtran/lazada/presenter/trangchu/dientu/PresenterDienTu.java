package com.namtran.lazada.presenter.trangchu.dientu;

import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.model.objectclass.DienTu;
import com.namtran.lazada.model.objectclass.SanPham;
import com.namtran.lazada.model.objectclass.ThuongHieu;
import com.namtran.lazada.model.trangchu.dientu.ModelDienTu;
import com.namtran.lazada.view.trangchu.dientu.ViewDienTu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namtr on 08/05/2017.
 */

public class PresenterDienTu implements IPresenterDienTu {
    private ViewDienTu viewDienTu;
    private ModelDienTu modelDienTu;

    public PresenterDienTu(ViewDienTu viewDienTu) {
        this.viewDienTu = viewDienTu;
        this.modelDienTu = new ModelDienTu();
    }

    @Override
    public void layDanhSachDienTu() {
        List<DienTu> dienTuList = new ArrayList<>();
        DienTu dienTu;

        List<ThuongHieu> thuongHieuList = modelDienTu.layDanhSachThuongHieuLon(Action.THUONG_HIEU_LON_LIST.getAction(), Action.THUONG_HIEU_LON_LIST.getParentNodeName());
        List<SanPham> topDTVaMTBList = modelDienTu.layDanhSachTopSanPham(Action.TOP_DT_VA_MTB_LIST.getAction(), Action.TOP_DT_VA_MTB_LIST.getParentNodeName());
        dienTu = new DienTu();
        dienTu.setThuongHieuList(thuongHieuList);
        dienTu.setSanPhamList(topDTVaMTBList);
        dienTu.setTenTieuDe("Danh sách thương hiệu lớn");
        dienTu.setTenTieuDeTop("Top điện thoại & máy tính bảng");
        dienTuList.add(dienTu);

        List<ThuongHieu> phuKienList = modelDienTu.layDanhSachThuongHieuLon(Action.PHU_KIEN_LIST.getAction(), Action.PHU_KIEN_LIST.getParentNodeName());
        List<SanPham> topPhuKienList = modelDienTu.layDanhSachTopSanPham(Action.TOP_PHU_KIEN_LIST.getAction(), Action.TOP_PHU_KIEN_LIST.getParentNodeName());
        dienTu = new DienTu();
        dienTu.setThuongHieuList(phuKienList);
        dienTu.setSanPhamList(topPhuKienList);
        dienTu.setTenTieuDe("Danh sách phụ kiện");
        dienTu.setTenTieuDeTop("Top phụ kiện");
        dienTuList.add(dienTu);

        List<ThuongHieu> tienIchList = modelDienTu.layDanhSachThuongHieuLon(Action.TIEN_ICH_LIST.getAction(), Action.TIEN_ICH_LIST.getParentNodeName());
        List<SanPham> topTienIchList = modelDienTu.layDanhSachTopSanPham(Action.TOP_TIEN_ICH_LIST.getAction(), Action.TOP_TIEN_ICH_LIST.getParentNodeName());
        dienTu = new DienTu();
        dienTu.setThuongHieuList(tienIchList);
        dienTu.setSanPhamList(topTienIchList);
        dienTu.setTenTieuDe("Danh sách tiện ích");
        dienTu.setTenTieuDeTop("Top tiện ích");
        dienTuList.add(dienTu);

        viewDienTu.hienThiThuongHieuLon(dienTuList);
    }
}
