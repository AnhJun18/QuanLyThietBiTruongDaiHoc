
package qltb.Controller;

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
import org.springframework.web.bind.annotation.RequestMethod;

import qltb.Entity.LoaiTB;

@Transactional
@Controller
@RequestMapping("device_type")
public class DeviceTypeController {

	@Autowired
	SessionFactory factory;

	public List<LoaiTB> getLoaiTB() {
		Session session = factory.getCurrentSession();
		String hql = "FROM LoaiTB";
		Query query = session.createQuery(hql);
		List<LoaiTB> list = query.list();
		return list;
	}
	

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(ModelMap model, @ModelAttribute("LoaiTB") LoaiTB loaitb, HttpSession ss) {
		List<LoaiTB> listLoaiTB = this.getLoaiTB();
		model.addAttribute("DsLoaiTB", listLoaiTB);
		model.addAttribute("myparam", ss.getAttribute("myparam"));
		model.addAttribute("message", ss.getAttribute("message"));
		ss.removeAttribute("myparam");
		ss.removeAttribute("message");
		ss.setAttribute("navigation","device_type");
		return "device/device_type";
	}

	public Integer add(LoaiTB loaitb) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(loaitb);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;

	}

	@RequestMapping(value = "index", params = "btnadd", method = RequestMethod.POST)
	public String addsp(HttpServletRequest request, ModelMap model, @ModelAttribute("LoaiTB") LoaiTB loaitb) {
		Integer tmp = this.add(loaitb);
		model.addAttribute("myparam", "Thêm");
		if (tmp != 0) {
			model.addAttribute("message", "success");
		} else {
			model.addAttribute("message", "error");
		}
		List<LoaiTB> listLoaiTB = this.getLoaiTB();
		model.addAttribute("DsLoaiTB", listLoaiTB);

		return "device/device_type";
	}

	public LoaiTB getLoaiTB_ID(String ID) {
		Session session = factory.getCurrentSession();
		String hql = "FROM LoaiTB where maLoai = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", ID);
		LoaiTB list = (LoaiTB) query.list().get(0);
		return list;
	}

	public Integer deleteTb(LoaiTB loaitb) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			String qryString = "delete from LoaiTB s where s.maLoai= :id";
			Query query = session.createQuery(qryString);
			query.setParameter("id", loaitb.getMaLoai());
			query.executeUpdate();
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;

	}

	@RequestMapping(value = "index/{id}", params = "linkDelete")
	public String delete_SP(HttpServletRequest request, ModelMap model, @ModelAttribute("LoaiTB") LoaiTB loaitb,
			@PathVariable("id") String id, HttpSession ss) {

		Integer tmp = this.deleteTb(this.getLoaiTB_ID(id));
		ss.setAttribute("myparam", "Xóa");

		if (tmp != 0) {
			ss.setAttribute("message", "success");
		} else {
			ss.setAttribute("message", "error");
		}
		return "redirect:/device_type/index.htm";

	}

	public Integer editTb(LoaiTB loaitb) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(loaitb);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;

	}

	@RequestMapping(value = "index", params = "btn-save-edit", method = RequestMethod.POST)
	public String editsp(HttpServletRequest request, ModelMap model, @ModelAttribute("LoaiTB") LoaiTB loaitb,
			HttpSession ss) {

		Integer tmp = this.editTb(loaitb);
		ss.setAttribute("myparam", "Sửa");

		if (tmp != 0) {
			ss.setAttribute("message", "success");
		} else {
			ss.setAttribute("message", "error");
		}
		List<LoaiTB> listLoaiTB = this.getLoaiTB();
		model.addAttribute("DsLoaiTB", listLoaiTB);
		return "redirect:/device_type/index.htm";
	}



}