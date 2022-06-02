package qltb.Entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="LOPTINCHI")
public class LopTinChi {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name="MALTC")
	   private String maLTC;
		
		@Column(name="NIENKHOA")
		private String nienkhoa;

		@Column(name="HOCKY")
		private String hocky;
		
		@Column(name="MONHOC")
		private String monhoc;
		
		@Column(name="NHOM")
		private Integer nhom;
		
		@OneToMany(mappedBy = "lopTC", fetch = FetchType.EAGER)
		private Collection<PhieuMuon> dspm;
		
		public LopTinChi() {
			super();
		}

		public String getMaLTC() {
			return maLTC;
		}

		public void setMaLTC(String maLTC) {
			this.maLTC = maLTC;
		}

		public String getNienkhoa() {
			return nienkhoa;
		}

		public void setNienkhoa(String nienkhoa) {
			this.nienkhoa = nienkhoa;
		}

		public String getHocky() {
			return hocky;
		}

		public void setHocky(String hocky) {
			this.hocky = hocky;
		}

		public String getMonhoc() {
			return monhoc;
		}

		public void setMonhoc(String monhoc) {
			this.monhoc = monhoc;
		}

		public Integer getNhom() {
			return nhom;
		}

		public void setNhom(Integer nhom) {
			this.nhom = nhom;
		}

		public Collection<PhieuMuon> getDspm() {
			return dspm;
		}

		public void setDspm(Collection<PhieuMuon> dspm) {
			this.dspm = dspm;
		}
		public String getfullname() {
			return this.monhoc+" nhóm "+this.nhom.toString()+" "+this.nienkhoa+" "+this.hocky;
		}

		
}