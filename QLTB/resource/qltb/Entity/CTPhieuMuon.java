package qltb.Entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;


@Entity
@Table(name="CTPHIEUMUON")
public class CTPhieuMuon {

	@EmbeddedId
	private CTPhieuMuonKey idCTPhieuMuon;
		
	
	@ManyToOne
	@MapsId("MAPHIEUMUON")
	@JoinColumn(name="MAPHIEUMUON")
	private PhieuMuon phieuMuon;
	
	@ManyToOne
	@MapsId("MATB")
	@JoinColumn(name="MATB")
	private ThietBi thietBi;

	@ManyToOne
	@JoinColumn(name="SVMUON")
	private SinhVien svMuon;
	
	@Column(name="TRANGTHAI",nullable = true)
	private int trangThai;
	
	public CTPhieuMuon() {
		super();
		this.trangThai=3;
	}
	
	public CTPhieuMuon(String idPhieuMuon,String idTB, SinhVien svMuon) {
		super();
		this.idCTPhieuMuon= new CTPhieuMuonKey(idPhieuMuon,idTB);
		this.svMuon = svMuon;
		this.trangThai=3;
	}

	public CTPhieuMuonKey getIdCTPhieuMuon() {
		return idCTPhieuMuon;
	}

	public void setIdCTPhieuMuon(CTPhieuMuonKey idCTPhieuMuon) {
		this.idCTPhieuMuon = idCTPhieuMuon;
	}

	
	public SinhVien getSvMuon() {
		return svMuon;
	}

	public void setSvMuon(SinhVien svMuon) {
		this.svMuon = svMuon;
	}
	
	public int getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

	public PhieuMuon getPhieuMuon() {
		return phieuMuon;
	}

	public void setPhieuMuon(PhieuMuon phieuMuon) {
		this.phieuMuon = phieuMuon;
	}

	public ThietBi getThietBi() {
		return thietBi;
	}

	public void setThietBi(ThietBi thietBi) {
		this.thietBi = thietBi;
	}

	@Override
	public String toString() {
		return "CTPhieuMuon [idCTPhieuMuon=" + idCTPhieuMuon.getIdPhieuMuon()+'+'+ idCTPhieuMuon.getIdThietBi()  + ", phieuMuon=" + phieuMuon + ", thietBi=" + thietBi
				+ ", svMuon=" + svMuon + ", trangThai=" + trangThai + "]";
	}


	
	
}