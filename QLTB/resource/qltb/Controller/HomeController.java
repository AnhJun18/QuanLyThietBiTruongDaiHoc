package qltb.Controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import qltb.Entity.CTPhieuMuon;
import qltb.Entity.CTPhieuMuonKey;
import qltb.Entity.LoaiTB;
import qltb.Entity.LopTinChi;
import qltb.Entity.PhieuMuon;
import qltb.Entity.Phong;
import qltb.Entity.SinhVien;
import qltb.Entity.ThietBi;

@Transactional
@Controller
@RequestMapping("home")
public class HomeController {
	private PhieuMuon myPM = new PhieuMuon();
	private List<ThietBi> tbiMuon = new ArrayList<ThietBi>();

	@Autowired
	SessionFactory factory;

	public List<Phong> getPhong() {
		Session session = factory.openSession();
		String hql = "FROM Phong";
		Query query = session.createQuery(hql);
		List<Phong> list = query.list();
		session.close();
		return list;
	}

	@RequestMapping("index")
	public String showphong(ModelMap model, HttpSession ss) {
		ss.setAttribute("navigation", "home");
		model.addAttribute("thongbao", ss.getAttribute("changpassmessage"));
		ss.removeAttribute("changpassmessage");
		model.addAttribute("dsPhong", this.getPhong());
		return "home/phong";
	}

	@RequestMapping(value = "phong/{id}", params = "linkPhong")
	public String Phong(ModelMap model, @PathVariable("id") String id) {
		model.addAttribute("dsPhong", this.getPhong());
		model.addAttribute("statusmodal", "open");
		model.addAttribute("listLTC", this.getListLTC());
		model.addAttribute("roomclicked", id);
		return "home/phong";
	}

	@ModelAttribute("timeNow")
	public String getnow() {
		LocalDateTime time = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		String formatDateTime = time.format(formatter);
		return formatDateTime;
	}

	@ModelAttribute("timeDefault")
	public String getHanTra() {
		LocalDateTime time = LocalDateTime.now();
		if (time.getHour() < 12) {
			time = time.withHour(12).withMinute(0);
		} else if (time.getHour() < 17) {
			time = time.withHour(17).withMinute(0);
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		String formatDateTime = time.format(formatter);
		return formatDateTime;
	}

	public Phong getPhongByID(String ID) {
		Session session = factory.getCurrentSession();
		String hql = "FROM Phong where maPhong = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", ID);
		Phong list = (Phong) query.list().get(0);
		return list;
	}

	public LopTinChi getLTCByID(String ID) {
		Session session = factory.getCurrentSession();
		String hql = "FROM LopTinChi where maLTC = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", ID);
		LopTinChi list = (LopTinChi) query.list().get(0);
		return list;
	}

	public SinhVien getSVByID(String ID) {
		Session session = factory.getCurrentSession();
		String hql = "FROM SinhVien where maSV = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", ID);
		if (query.list().size() != 0) {
			SinhVien sv = (SinhVien) query.list().get(0);
			return sv;
		} else {
			return null;
		}
	}

	public Integer addsv(SinhVien sv) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(sv);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;

	}

	public List<PhieuMuon> getLichSuMuonSV(String ID) {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhieuMuon  " + "where  thoiDiemTra is null";
		Query query = session.createQuery(hql);
		query.setParameter("maSV", ID);
		List<PhieuMuon> list = query.list();
		return list;
	}

	@RequestMapping(value = "phong/{id}", params = "btnsave")
	public String savePM(@PathVariable("id") String id, HttpServletRequest sr, ModelMap model, HttpSession ss) {

		PhieuMuon phieumuon = new PhieuMuon();
		phieumuon.setMaPhg(getPhongByID(id));
		phieumuon.setLopTC(getLTCByID(sr.getParameter("loptc")));
		phieumuon.setHanTra(Timestamp.valueOf(sr.getParameter("hanTra").replace("T", " ") + ":00"));
		if (!ss.getAttribute("info").equals("Admin"))
			phieumuon.setNguoiLap(ss.getAttribute("infoManv").toString());
		myPM = phieumuon;
		model.addAttribute("statusmodal2", "open");
		model.addAttribute("dsPhong", this.getPhong());
		model.addAttribute("thongtinPhieuMuon", phieumuon);
		model.addAttribute("listTypeTB", this.getTypeTB(id));
		model.addAttribute("listTB", this.getListChoPhepMuon(id));
		// Toast

		return "home/phong";

	}

	public Integer addPM_CT(PhieuMuon pm, List<ThietBi> dstb, SinhVien sv) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(pm);
			System.out.println(pm.getMaPhieuMuon()+"MAPM");
			for (ThietBi thietBi : dstb) {
				CTPhieuMuon ctpm = new CTPhieuMuon();
				ctpm.setIdCTPhieuMuon(new CTPhieuMuonKey(pm.getMaPhieuMuon(), thietBi.getMaTBi()));
				System.out.println(thietBi.getMaTBi()+"MATB");
				ctpm.setSvMuon(sv);
				ctpm.setPhieuMuon(pm);
				ctpm.setThietBi(thietBi);
				session.save(ctpm);
			}
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}

		return 1;

	}

	@RequestMapping(value = "phong/{id}", params = "savebill")
	public String savePMAndCT(@PathVariable("id") String id, HttpServletRequest rq, ModelMap model, HttpSession ss) {
		System.out.println("ADDPM");
		String masvmuon = rq.getParameter("masvmuon").toUpperCase();
		String tenSV = rq.getParameter("tenSV");
		String tenLop = rq.getParameter("tenLop");
		if (this.getSVByID(masvmuon) == null) {
			if (tenSV == null || tenLop == null) {
				model.addAttribute("dsPhong", this.getPhong());
				model.addAttribute("SVien", masvmuon);
				model.addAttribute("statusmodal3", "open");
				tbiMuon.clear();
				for (ThietBi tb : getListChoPhepMuon(id)) {
					if (rq.getParameter(tb.getMaTBi()) != null) {
						tbiMuon.add(tb);
					}
				}
				return "home/phong";
			} else {
				SinhVien sv = new SinhVien(masvmuon, tenSV, tenLop);
				this.addsv(sv);
			}

		} else {
			tbiMuon.clear();
			for (ThietBi tb : getListChoPhepMuon(id)) {
				if (rq.getParameter(tb.getMaTBi()) != null) {
					tbiMuon.add(tb);
				}
			}

		}
		Integer tmp = 0;
		try {
			tmp = this.addPM_CT(myPM, tbiMuon, getSinhVienByID(masvmuon));
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (tmp != 0) {
			model.addAttribute("typeToast", "success");
			model.addAttribute("action", "Mượn Thiết Bị ");
		} else {
			model.addAttribute("typeToast", "error");
			model.addAttribute("action", "Mượn Thiết Bị ");
		}
		model.addAttribute("dsPhong", this.getPhong());
		return "home/phong";

	}

	public SinhVien getSinhVienByID(String ID) {
		Session session = factory.getCurrentSession();
		String hql = "FROM SinhVien where maSV = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", ID);
		SinhVien list = (SinhVien) query.list().get(0);
		return list;
	}

	public PhieuMuon getPhieuMuonByID(String ID) {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhieuMuon where maPhieuMuon = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", ID);
		PhieuMuon list = (PhieuMuon) query.list().get(0);
		return list;
	}

	public List<SinhVien> getSVMuon_PM(String ID) {
		Session session = factory.getCurrentSession();
		String hql = "select distinct svMuon FROM CTPhieuMuon s where s.idCTPhieuMuon.idPhieuMuon = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", ID);
		List<SinhVien> list = query.list();
		return list;
	}

	@RequestMapping(value = "bill{id}_{idPhong}")
	public String savePM(@PathVariable("id") String id, @PathVariable("idPhong") String idPhong, ModelMap model,
			HttpSession ss) {
		model.addAttribute("statusmodal2", "open");
		model.addAttribute("dsPhong", this.getPhong());
		model.addAttribute("listSVMuon", this.getSVMuon_PM(id));
		model.addAttribute("thongtinPhieuMuon", this.getPhieuMuonByID(id));
		model.addAttribute("listTypeTB", this.getTypeTB(idPhong));
		model.addAttribute("listTB", this.getListChoPhepMuon(idPhong));
		// Toast
		model.addAttribute("action", ss.getAttribute("action"));
		model.addAttribute("typeToast", ss.getAttribute("typeToast"));
		model.addAttribute("thongbao", ss.getAttribute("thongbao"));
		ss.removeAttribute("action");
		ss.removeAttribute("typeToast");
		ss.removeAttribute("thongbao");
		return "home/phong";

	}

	/*
	 * public List<Object[]> getListChoPhepMuon(String ID) { Session session =
	 * factory.getCurrentSession(); String hql = +
	 * "group by phong.maPhong,loaiTBi.tenLoai, tinhTrangTB,loaiTBi.maLoai  " +
	 * "having (phong.maPhong = :id or phong.maPhong= 'CSVC') and tinhTrangTB= '0'"
	 * +"order by phong.maPhong" ; Query query = session.createQuery(hql);
	 * query.setParameter("id", ID); List<Object[]> list = query.list(); return
	 * list; }
	 */
	public List<ThietBi> getListChoPhepMuon(String ID) {
		Session session = factory.getCurrentSession();
		String hql = "FROM ThietBi  " + "where (phong.maPhong = :id or phong.maPhong= 'CSVC') and tinhTrangTB= '0'";
		Query query = session.createQuery(hql);
		query.setParameter("id", ID);
		List<ThietBi> list = query.list();
		return list;
	}

	public List<LoaiTB> getTypeTB(String ID) {
		Session session = factory.getCurrentSession();
		String hql = "from LoaiTB where  maLoai in ( select distinct loaiTBi.maLoai from ThietBi where (phong.maPhong = :id or phong.maPhong = 'CSVC') and tinhTrangTB= '0')";
		Query query = session.createQuery(hql);
		query.setParameter("id", ID);
		List<LoaiTB> list = query.list();
		return list;

	}
	/*
	 * public List<Object[]> getListChoPhepMuon(String ID) { Session session =
	 * factory.getCurrentSession(); String hql =
	 * "select phong.maPhong, loaiTBi.tenLoai, count(MATbi),loaiTBi.maLoai FROM ThietBi  "
	 * + "group by phong.maPhong,loaiTBi.tenLoai, tinhTrangTB,loaiTBi.maLoai  " +
	 * "having (phong.maPhong = :id or phong.maPhong= 'CSVC') and tinhTrangTB= '0'"
	 * + "order by phong.maPhong"; Query query = session.createQuery(hql);
	 * query.setParameter("id", ID); List<Object[]> list = query.list(); return
	 * list; }
	 */

	public Integer addCTPM(String pm, List<ThietBi> dstb, SinhVien sv) {
		Session session1 = factory.openSession();
		Transaction t1 = session1.beginTransaction();
		try {
			for (ThietBi thietBi : dstb) {
				CTPhieuMuon ctpm = new CTPhieuMuon();
				ctpm.setIdCTPhieuMuon(new CTPhieuMuonKey(pm, thietBi.getMaTBi()));
				ctpm.setSvMuon(sv);
				session1.save(ctpm);
			}
			t1.commit();
		} catch (Exception e) {
			System.out.println(e);
			t1.rollback();
			return 0;
		} finally {
			session1.close();
		}

		return 1;
	}

	public ThietBi timTBChuaSD_Phong_LoaiTB(String phong, String loaiTB) {
		Session session = factory.getCurrentSession();
		String hql = "FROM ThietBi \r\n"
				+ "where (phong.maPhong = :phong or phong.maPhong= 'CSVC')  and loaiTBi.maLoai= :tb and tinhTrangTB= '0'";
		Query query = session.createQuery(hql);
		query.setParameter("phong", phong);
		query.setParameter("tb", loaiTB);
		ThietBi list = (ThietBi) query.list().get(0);
		return list;
	}

	@RequestMapping(value = "bill{id}_{idPhong}", params = "savebill")
	public String test1(@PathVariable("id") String id, @PathVariable("idPhong") String idPhong, HttpServletRequest rq,
			ModelMap model, HttpSession ss) {
		String masvmuon = rq.getParameter("masvmuon").toUpperCase();
		String tenSV = rq.getParameter("tenSV");
		String tenLop = rq.getParameter("tenLop");
		if (this.getSVByID(masvmuon) == null) {
			if (tenSV == null || tenLop == null) {
				System.out.println(tenSV + '-' + tenLop);
				model.addAttribute("dsPhong", this.getPhong());
				model.addAttribute("SVien", masvmuon);
				model.addAttribute("statusmodal3", "open");
				tbiMuon.clear();
				for (ThietBi tb : getListChoPhepMuon(idPhong)) {
					if (rq.getParameter(tb.getMaTBi()) != null) {
						tbiMuon.add(tb);
					}
				}
				return "home/phong";
			} else {
				SinhVien sv = new SinhVien(masvmuon, tenSV, tenLop);
				this.addsv(sv);
			}

		} else {
			tbiMuon.clear();
			for (ThietBi tb : getListChoPhepMuon(idPhong)) {

				if (rq.getParameter(tb.getMaTBi()) != null) {
					tbiMuon.add(tb);
				}
			}

		}
		Integer tmp = 0;
		try {
			tmp = this.addCTPM(id, tbiMuon, getSinhVienByID(masvmuon));

		} catch (Exception e) {
			// TODO: handle exception
		}
		if (tmp != 0) {
			model.addAttribute("typeToast", "success");
			model.addAttribute("action", "Mượn Thiết Bị ");
		} else {
			model.addAttribute("typeToast", "error");
			model.addAttribute("action", "Mượn Thiết Bị ");
		}
		model.addAttribute("dsPhong", this.getPhong());

		return "home/phong";

		/*
		 * for (Object[] ct : this.getListChoPhepMuon(idPhong)) { int tmp =
		 * Integer.parseInt(sp.getParameter((String) ct[0] + ":" + ct[3])); for (int i =
		 * 0; i < tmp; i++) { CTPhieuMuon pm = new CTPhieuMuon(); ThietBi TBMuon =
		 * this.timTBChuaSD_Phong_LoaiTB(idPhong, ct[3].toString()); CTPhieuMuonKey key
		 * = new CTPhieuMuonKey(id, TBMuon.getMaTBi()); pm.setIdCTPhieuMuon(key); if
		 * (this.addCTPM(pm) != 0) p += "showtoast({\r\n" +
		 * "	title : \"Thành công!\",\r\n" + "	message :  '" +
		 * TBMuon.getLoaiTBi().getTenLoai() + " (" + TBMuon.getLoaiTBi().getMaLoai() +
		 * ")! Cho mượn thành công \"',\r\n" + "	type : \"success\",\r\n" +
		 * "	duration : 3000\r\n" + "});"; else { p += "showtoast({\r\n" +
		 * "	title : \"Thất bại!\",\r\n" + "	message :  '" +
		 * TBMuon.getLoaiTBi().getTenLoai() + " (" + TBMuon.getLoaiTBi().getMaLoai() +
		 * ")! Cho mượn thất bại \"',\r\n" + "	type : \"error\",\r\n" +
		 * "	duration : 3000\r\n" + "});"; }
		 * 
		 * } }
		 */

	}

	public Integer traTB(String maPM, String maTB) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			String qryString = " update CTPhieuMuon s set s.trangThai= '4' "
					+ "where s.idCTPhieuMuon.idPhieuMuon = :maPM and s.idCTPhieuMuon.idThietBi = :maTB";
			Query query = session.createQuery(qryString);
			query.setParameter("maPM", maPM);
			query.setParameter("maTB", maTB);
			query.executeUpdate();
			t.commit();
		} catch (Exception e) {
			System.out.println(e);
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;

	}

	public Integer baoMatTB(String maPM, String maTB) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			String qryString = " update CTPhieuMuon s set s.trangThai= '5' "
					+ "where s.idCTPhieuMuon.idPhieuMuon = :maPM and s.idCTPhieuMuon.idThietBi = :maTB";
			Query query = session.createQuery(qryString);
			query.setParameter("maPM", maPM);
			query.setParameter("maTB", maTB);
			query.executeUpdate();
			t.commit();
		} catch (Exception e) {
			System.out.println(e);
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;

	}

	@RequestMapping(value = "pay{idPM}_{idTB}_{idPhong}")
	public String traPhieuMuon(@PathVariable("idPM") String idPM, @PathVariable("idTB") String idTB, @PathVariable("idPhong") String idPhong, ModelMap model,
			HttpSession ss) {

		ss.setAttribute("action", "Trả Thiết Bị ");
		if (this.traTB(idPM, idTB) != 0) {
			ss.setAttribute("typeToast", "success");
		} else {
			ss.setAttribute("typeToast", "error");
		}
		return "redirect:/home/bill{idPM}_{idPhong}.htm";
	}

	@RequestMapping(value = "loss{idPM}_{idTB}_{idPhong}")
	public String baoMatTB(@PathVariable("idPM") String idPM, @PathVariable("idTB") String idTB, ModelMap model, @PathVariable("idPhong") String idPhong,
			HttpSession ss) {
		ss.setAttribute("action", "Báo Mât Thiết Bị ");
		if (this.baoMatTB(idPM, idTB) != 0) {
			ss.setAttribute("typeToast", "success");
		} else {
			ss.setAttribute("typeToast", "error");
		}
		return "redirect:/home/bill{idPM}_{idPhong}.htm";
	}

	public List<LopTinChi> getListLTC() {
		Session session = factory.getCurrentSession();
		System.out.println("ACS");
		String hql = "FROM LopTinChi where maLTC not in (select distinct lopTC.maLTC from PhieuMuon where thoiDiemTra is null)";
		Query query = session.createQuery(hql);
		List<LopTinChi> list = query.list();
		System.out.println("ACScc");
		return list;
	}

}
