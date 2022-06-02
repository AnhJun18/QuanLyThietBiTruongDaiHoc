package qltb.Controller;

import java.io.IOException;

import javax.persistence.ParameterMode;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import qltb.Entity.TaiKhoan;
import qltb.model.Account;
import qltb.recaptcha.Recaptchaverification;

@Transactional
@Controller
public class LoginController {
	
	@Autowired 
	SessionFactory factory;
	
	@RequestMapping("index")
	public String index(@ModelAttribute("account") Account acc) {
		return "index";
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
	
	public TaiKhoan getTKByID(String ID) {
		Session session = factory.getCurrentSession();
		String hql = "FROM TaiKhoan where id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", ID);
		TaiKhoan tk=(TaiKhoan) query.list().get(0);
		return tk;
	}

	@RequestMapping(value = "index", method = RequestMethod.POST)
	public String index2(@ModelAttribute("account") Account acc, ModelMap model, BindingResult errors,
			HttpServletRequest request, HttpSession ss) throws IOException {
		
		String gRepcaptchResponse = request.getParameter("g-recaptcha-response");
		boolean verity = Recaptchaverification.verify(gRepcaptchResponse);

		if (acc.getId().trim().isBlank() || acc.getId().trim().isEmpty()) {
			errors.rejectValue("id", "account", "Ten Đăng nhập ko đc để trống");
		}
		if (acc.getPass().trim().isBlank() || acc.getPass().trim().isEmpty()) {
			errors.rejectValue("pass", "account", "Mật khẩu ko đc để trống");
		}
	
		if (errors.hasErrors() || !verity) {
			if (!verity)
				model.addAttribute("tb", "Vui long Nhap Captcha");
		} else {

			if (checkLogin(acc.getId().trim().toString(), acc.getPass().trim().toString())) {
	
				TaiKhoan tk=this.getTKByID(acc.getId().trim().toString());
				ss.setAttribute("acc",tk.getId());
				if(tk.getTypeacc().equals("admin")) {
					ss.setAttribute("permission","admin");
					ss.setAttribute("info","Admin");
				}
				else {
					ss.setAttribute("permission","user");
					ss.setAttribute("info",tk.getNhanvien().getTenNV());
					ss.setAttribute("infoManv",tk.getNhanvien().getMaNV());
				}
				return "redirect:/home/index.htm";
			}
			else {
				ss.removeAttribute("permission");
				model.addAttribute("tb", "Sai Thong Tin");
			}
		}

		return "index";
	}
	
	
	
	
	@RequestMapping("logout")
	public String Logout(HttpSession ss) {

		ss.removeAttribute("acc");
		ss.removeAttribute("permission");
		ss.removeAttribute("info");
		ss.removeAttribute("infoManv");
		return "redirect:/index.htm";
	}
}
