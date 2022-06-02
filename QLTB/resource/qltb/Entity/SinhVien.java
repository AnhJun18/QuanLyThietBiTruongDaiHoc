package qltb.Entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SINHVIEN")
public class SinhVien {
	@Id
	@Column(name = "MASV")
	private String maSV;

	@Column(name = "TENSV")
	private String tenSV;

	@Column(name = "LOP")
	private String lop;
	
	@OneToMany(mappedBy = "svMuon", fetch = FetchType.EAGER)
	private Collection<CTPhieuMuon> dspm;
	
	public SinhVien() {
		super();
	}

	public SinhVien(String maSV, String tenSV, String lop) {
		super();
		this.maSV = maSV;
		this.tenSV = tenSV;
		this.lop = lop;
	}

	public String getMaSV() {
		return maSV;
	}

	public void setMaSV(String maSV) {
		this.maSV = maSV;
	}

	public String getTenSV() {
		return tenSV;
	}

	public void setTenSV(String tenSV) {
		this.tenSV = tenSV;
	}

	public String getLop() {
		return lop;
	}

	public void setLop(String lop) {
		this.lop = lop;
	}


}