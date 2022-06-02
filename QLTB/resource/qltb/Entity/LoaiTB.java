package qltb.Entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="LOAITB")
public class LoaiTB {
		@Id
		@Column(name="MALOAI")
	   private String maLoai;
		
		@OneToMany(mappedBy = "loaiTBi", fetch = FetchType.EAGER)
		private Collection<ThietBi> dstb;
		
		@Column(name="TENLOAI")
	   private String tenLoai;

		
		
		public LoaiTB() {
			super();
		}

		public String getMaLoai() {
			return maLoai;
		}

		public void setMaLoai(String maLoai) {
			this.maLoai = maLoai;
		}

		public String getTenLoai() {
			return tenLoai;
		}

		public void setTenLoai(String tenLoai) {
			this.tenLoai = tenLoai;
		}

		public Collection<ThietBi> getDstb() {
			return dstb;
		}

		public void setDstb(Collection<ThietBi> dstb) {
			this.dstb = dstb;
		}

		

		
}