package qltb.Entity;

import java.sql.Timestamp;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="PHIEUMUON")
public class PhieuMuon{
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name="MAPM")
	   private String maPhieuMuon;
		
		@ManyToOne
		@JoinColumn(name="MAPHG")
	    private Phong maPhg;

		@ManyToOne
		@JoinColumn(name="LOPTC")
	    private LopTinChi lopTC;
		
		@Column(name="THOIDIEMMUON",nullable = true)
		private Timestamp thoiDiemMuon;
		
		@Column(name="HANTRA")
		private Timestamp hanTra;

		@Column(name="THOIDIEMTRA",nullable = true)
		private Timestamp thoiDiemTra;
		
		@Column(name="NGUOILAPPHIEU",nullable = true)
		private String nguoiLap;
		
		
		@OneToMany(mappedBy = "phieuMuon", fetch = FetchType.EAGER)
		private Collection<CTPhieuMuon> listCTPhieuMuon;
		public PhieuMuon() {
			super();
		}
		
		public String getMaPhieuMuon() {
			return maPhieuMuon;
		}

		public void setMaPhieuMuon(String maPhieuMuon) {
			this.maPhieuMuon = maPhieuMuon;
		}

		public Phong getMaPhg() {
			return maPhg;
		}

		public void setMaPhg(Phong maPhg) {
			this.maPhg = maPhg;
		}
	

		public Timestamp getThoiDiemMuon() {
			return thoiDiemMuon;
		}

		public void setThoiDiemMuon(Timestamp thoiDiemMuon) {
			this.thoiDiemMuon = thoiDiemMuon;
		}

		public Timestamp getHanTra() {
			return hanTra;
		}

		public void setHanTra(Timestamp hanTra) {
			this.hanTra = hanTra;
		}

		public Timestamp getThoiDiemTra() {
			return thoiDiemTra;
		}

		public void setThoiDiemTra(Timestamp thoiDiemTra) {
			this.thoiDiemTra = thoiDiemTra;
		}

		public String getNguoiLap() {
			return nguoiLap;
		}

		public void setNguoiLap(String nguoiLap) {
			this.nguoiLap = nguoiLap;
		}

		public LopTinChi getLopTC() {
			return lopTC;
		}

		public void setLopTC(LopTinChi lopTC) {
			this.lopTC = lopTC;
		}

		public Collection<CTPhieuMuon> getListCTPhieuMuon() {
			return listCTPhieuMuon;
		}

		public void setListCTPhieuMuon(Collection<CTPhieuMuon> listCTPhieuMuon) {
			this.listCTPhieuMuon = listCTPhieuMuon;
		}

		@Override
		public String toString() {
			return "PhieuMuon [maPhieuMuon=" + maPhieuMuon + ", maPhg=" + maPhg.getMaPhong() + ", lopTC=" + lopTC.getMaLTC() + ", thoiDiemMuon="
					+ thoiDiemMuon + ", hanTra=" + hanTra + ", thoiDiemTra=" + thoiDiemTra + ", nguoiLap=" + nguoiLap
					+ ", listCTPhieuMuon=" + listCTPhieuMuon + "]";
		}
		
	
		
}