package qltb.Entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "NHANVIEN")
public class NhanVien {
	@Id
	@Column(name = "MANV")
	private String maNV;

	@Column(name = "TENNV")
	private String tenNV;

	@Column(name = "CMND")
	private String cmnd;
	
	@Column(name = "EMAIL")
	private String email;
	 
	@Column(name = "SDT")
	private String sdt;
	
	@Column(name = "DANGLAMVIEC")
	private Boolean status;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TAIKHOAN",columnDefinition = "TENTAIKHOAN")
	private TaiKhoan taiKhoan;
    

	public NhanVien() {
		super();
		this.maNV="";
		this.status=true;
	}

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public String getTenNV() {
		return tenNV;
	}

	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public String getCmnd() {
		return cmnd;
	}

	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	
	

}