package qltb.Entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="PHONG")
public class Phong {
		@Id
		@Column(name="MAPHONG")
	   private String maPhong;
		
		@Column(name="TRANGTHAI",nullable = true)
	   private int trangThai;
		
		@OneToMany(mappedBy = "phong", fetch = FetchType.EAGER)
		private Collection<ThietBi> dstb;
		
		@OneToMany(mappedBy = "maPhg", fetch = FetchType.EAGER)
		private Collection<PhieuMuon> dspm;
		
		public Phong() {
			super();
			this.trangThai=0;
		}

		public String getMaPhong() {
			return maPhong;
		}

		public void setMaPhong(String maPhong) {
			this.maPhong = maPhong;
		}

		public int getTrangThai() {
			return trangThai;
		}

		public void setTrangThai(int trangThai) {
			this.trangThai = trangThai;
		}

		public Collection<ThietBi> getDstb() {
			return dstb;
		}

		public void setDstb(Collection<ThietBi> dstb) {
			this.dstb = dstb;
		}
		
        public Collection<PhieuMuon> getDspm() {
			return dspm;
		}

		public void setDspm(Collection<PhieuMuon> dspm) {
			this.dspm = dspm;
		}

		public Map<String, Integer> dsTBChoPhepMuon() {
        	int count = 1;
        	Map<String, Integer> map = new HashMap<>();
        	Set<String> set1 = new LinkedHashSet<String>();
			for (ThietBi ctThietBi : dstb) {
				if(ctThietBi.getTinhTrangTB()==0) {
				 if (!set1.add(ctThietBi.getLoaiTBi().getTenLoai())) {
	        	        count = map.get(ctThietBi.getLoaiTBi().getTenLoai()) + 1;
	        	    }
	        	    map.put(ctThietBi.getLoaiTBi().getTenLoai(), count);
	        	    count = 1;}
			}
			return map;
		}
}