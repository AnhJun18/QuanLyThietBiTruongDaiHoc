
package qltb.Controller;

import java.util.List;

import javax.persistence.ParameterMode;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.procedure.ProcedureCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import qltb.Entity.NhanVien;
import qltb.Entity.TaiKhoan;

@Transactional
@Controller
@RequestMapping("administration")
public class AdministrationController {

	@Autowired
	SessionFactory factory;

	public List<NhanVien> getListNV() {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhanVien";
		Query query = session.createQuery(hql);
		List<NhanVien> list = query.list();
		return list;
	}

	@RequestMapping(value = "admin", method = RequestMethod.GET)
	public String index(ModelMap model, HttpSession ss, @ModelAttribute("NhanVien") NhanVien nv) {
		ss.setAttribute("navigation", "administration");
		model.addAttribute("listNV", getListNV());
		return "administration/admin";
	}

	public Integer addNV(NhanVien nhanvien) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(nhanvien);
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

	public boolean checkTKByID(String ID) {
		Session session = factory.getCurrentSession();
		String hql = "FROM TaiKhoan where id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", ID);
		if (query.list().size() != 0) {
			return true;
		} else {
			return false;
		}
	}

	@RequestMapping(value = "admin", params = "btnadd", method = RequestMethod.POST)
	public String add(ModelMap model, @ModelAttribute("NhanVien") NhanVien nv) {
		if (checkTKByID(nv.getTaiKhoan().getId())) {
			model.addAttribute("message", "error");
			model.addAttribute("action", "Tài khoản đã được sủ dụng");

		} else {

			TaiKhoan tk = new TaiKhoan(nv.getTaiKhoan().getId());
			nv.setTaiKhoan(tk);
			if (this.addNV(nv) != 0) {
				model.addAttribute("message", "success");
				model.addAttribute("action", "Thêm nhân viên thành công");

			} else {
				model.addAttribute("message", "error");
				model.addAttribute("action", "Thêm nhân viên thất bại");

			}

		}

		model.addAttribute("listNV", getListNV());
		return "administration/admin";
	}

	public Integer editTTNV(NhanVien nv) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			String qryString = " update NhanVien s set s.tenNV= :name,s.email = :email,"
					+ "s.cmnd = :cmnd,s.sdt = :sdt " + "where s.maNV = :maNV";
			Query query = session.createQuery(qryString);
			query.setParameter("name", nv.getTenNV());
			query.setParameter("email", nv.getEmail());
			query.setParameter("cmnd", nv.getCmnd());
			query.setParameter("sdt", nv.getSdt());
			query.setParameter("maNV", nv.getMaNV());
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

	@RequestMapping(value = "admin", params = "btn-save-edit", method = RequestMethod.POST)
	public String edit(ModelMap model, @ModelAttribute("NhanVien") NhanVien nv) {
		if (this.editTTNV(nv) != 0) {
			model.addAttribute("message", "success");
			model.addAttribute("action", "Sửa thông tin nhân viên thành công");
		} else {
			model.addAttribute("message", "error");
			model.addAttribute("action", "Sửa thông tin nhân viên thất bại");
		}

		model.addAttribute("listNV", getListNV());
		return "administration/admin";
	}

	public void spResetPass(String maNV) {
		Session session = factory.openSession();
		session.beginTransaction();
		ProcedureCall call = session.createStoredProcedureCall("sp_resetPass");
		call.registerParameter("MaNV", String.class, ParameterMode.IN).bindValue(maNV);
		call.getOutputs().getCurrent();
		session.getTransaction().commit();
		session.close();
	}

	@RequestMapping(value = "resetpass_id={id}")
	public String resetpass(@PathVariable("id") String id, ModelMap model, @ModelAttribute("NhanVien") NhanVien nv) {
		this.spResetPass(id);

		model.addAttribute("message", "success");
		model.addAttribute("action", "Sửa mật khẩu thành công");
		model.addAttribute("listNV", getListNV());
		return "administration/admin";
	}

	public Boolean checkLogin(String user, String pass) {
		Session session = factory.openSession();
		session.beginTransaction();
		ProcedureCall call = session.createStoredProcedureCall("sp_kiemTraDangNhap");
		call.registerParameter("UserName", String.class, ParameterMode.IN).bindValue(user);
		call.registerParameter("Pass", String.class, ParameterMode.IN).bindValue(pass);
		call.registerParameter("Result", Boolean.class, ParameterMode.OUT);
		Boolean check = (Boolean) call.getOutputs().getOutputParameterValue("Result");
		session.getTransaction().commit();
		session.close();
		return check;
	}

	public Boolean changePass(String username, String passold, String newPass) {
		Session session = factory.openSession();
		session.beginTransaction();
		ProcedureCall call = session.createStoredProcedureCall("sp_changepass");
		call.registerParameter("UserName", String.class, ParameterMode.IN).bindValue(username);
		call.registerParameter("PassOld", String.class, ParameterMode.IN).bindValue(passold);
		call.registerParameter("PassNew", String.class, ParameterMode.IN).bindValue(newPass);
		call.registerParameter("Result", Boolean.class, ParameterMode.OUT);
		Boolean check = (Boolean) call.getOutputs().getOutputParameterValue("Result");
		session.getTransaction().commit();
		session.close();
		return check;

	}

	@RequestMapping(value = "changepass")
	public String resetpass(HttpServletRequest rq, HttpSession ss, @RequestParam("currentpass") String currentpass,
			@RequestParam("newpass") String newpass, @RequestParam("retypepass") String retypepass) {
		if (this.checkLogin(ss.getAttribute("acc").toString(), currentpass.trim().toString()) == true) {
			if (newpass.trim().toString().equals(retypepass.trim().toString())) {
				Boolean tmp = this.changePass(ss.getAttribute("acc").toString(), currentpass.trim().toString(),
						newpass.trim().toString());
				if (tmp == true) {
					return "redirect:/logout.htm";
				} else {
					String message = "showtoast({\r\n" + "	title : \"Thất bại!\",\r\n" + "	message :  '"
							+ " Đổi mật khẩu thất bại\"',\r\n" + "	type : \"error\",\r\n"
							+ "	duration : 3000\r\n" + "});";
					ss.setAttribute("changpassmessage", message);
					return "redirect:/home/index.htm";
				}

			} else {
				String message = "showtoast({\r\n" + "	title : \"Thất bại!\",\r\n" + "	message :  '"
						+ " Mật khẩu mới không khớp\"',\r\n" + "	type : \"error\",\r\n" + "	duration : 3000\r\n"
						+ "});";
				ss.setAttribute("changpassmessage", message);
				return "redirect:/home/index.htm";
			}

		} else {
			String message = "showtoast({\r\n" + "	title : \"Thất bại!\",\r\n" + "	message :  '"
					+ " Mật khẩu cũ bạn nhập không đúng\"',\r\n" + "	type : \"error\",\r\n" + "	duration : 3000\r\n"
					+ "});";
			ss.setAttribute("changpassmessage", message);
			return "redirect:/home/index.htm";

		}
	}

}