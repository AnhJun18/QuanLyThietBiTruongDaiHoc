package qltb.Entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CTPhieuMuonKey implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name="MAPHIEUMUON")
	private String idPhieuMuon;
	
	@Column(name="MATB")
	private String idThietBi;


	public String getIdPhieuMuon() {
		return idPhieuMuon;
	}

	public void setIdPhieuMuon(String idPhieuMuon) {
		this.idPhieuMuon = idPhieuMuon;
	}

	public String getIdThietBi() {
		return idThietBi;
	}

	public void setIdThietBi(String idThietBi) {
		this.idThietBi = idThietBi;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CTPhieuMuonKey(String idPhieuMuon, String idThietBi) {
		super();
		this.idPhieuMuon = idPhieuMuon;
		this.idThietBi = idThietBi;
	}

	public CTPhieuMuonKey() {
		super();
	}


	
}
