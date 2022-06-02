package qltb.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TAIKHOAN")
public class TaiKhoan {
	@Id
	@Column(name = "TENTAIKHOAN")
	private String id;

	@Column(name = "MATKHAU")
	private String pass;
	
	@Column(name = "LOAITAIKHOAN")
	private String typeacc;
	
	@OneToOne(mappedBy = "taiKhoan")
    private NhanVien nhanvien;
	
	public TaiKhoan() {
		super();
	}
	

	public TaiKhoan(String id) {
		super();
		this.id = id;
		this.pass = "1";
		this.typeacc = "user";
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getTypeacc() {
		return typeacc;
	}

	public void setTypeacc(String typeacc) {
		this.typeacc = typeacc;
	}

	public NhanVien getNhanvien() {
		return nhanvien;
	}

	public void setNhanvien(NhanVien nhanvien) {
		this.nhanvien = nhanvien;
	}

	
	

}