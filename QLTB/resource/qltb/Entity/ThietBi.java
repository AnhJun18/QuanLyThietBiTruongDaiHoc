package qltb.Entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="THIETBI")
public class ThietBi {
		@Id
		@Column(name="MATBI")
	   private String maTBi;
		
		@ManyToOne
		@JoinColumn(name="LOAITBI")
	    private LoaiTB loaiTBi;

	
		@Column(name="TINHTRANGTB",nullable = true)
		private int tinhTrangTB;
		
		@ManyToOne
		@JoinColumn(name="MAPHONG")
		private Phong phong;

		@OneToMany(mappedBy = "thietBi", fetch = FetchType.EAGER)
		private Collection<CTPhieuMuon> listCTPhieuMuon;
		
		public ThietBi() {
			super();
			this.tinhTrangTB=0;
		}

		public String getMaTBi() {
			return maTBi;
		}

		public void setMaTBi(String maTBi) {
			this.maTBi = maTBi;
		}

		
		public LoaiTB getLoaiTBi() {
			return loaiTBi;
		}

		public void setLoaiTBi(LoaiTB loaiTBi) {
			this.loaiTBi = loaiTBi;
		}

		public Phong getPhong() {
			return phong;
		}

		public void setPhong(Phong phong) {
			this.phong = phong;
		}

		public int getTinhTrangTB() {
			return tinhTrangTB;
		}

		public void setTinhTrangTB(int tinhTrangTB) {
			this.tinhTrangTB = tinhTrangTB;
		}

		public Collection<CTPhieuMuon> getListCTPhieuMuon() {
			return listCTPhieuMuon;
		}

		public void setListCTPhieuMuon(Collection<CTPhieuMuon> listCTPhieuMuon) {
			this.listCTPhieuMuon = listCTPhieuMuon;
		}
		
		
}