
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
import org.hibernate.result.Output;
import org.hibernate.result.ResultSetOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import qltb.Entity.CTPhieuMuon;
import qltb.Entity.ThietBi;
import qltb.Entity.LoaiTB;
import qltb.Entity.Phong;

@Transactional
@Controller
@RequestMapping("device_list")
public class DeviceListController{

	@Autowired
	SessionFactory factory;
	
	@ModelAttribute("listLoaiTB")
	public List<LoaiTB> getLoaiTB() {
		Session session = factory.getCurrentSession();
		String hql = "FROM LoaiTB";
		Query query = session.createQuery(hql);
		List<LoaiTB> list = query.list();
		return list;

	}
	public List<ThietBi> getListTB() {
		Session session = factory.getCurrentSession();
		String hql = "FROM ThietBi";
		Query query = session.createQuery(hql);
		List<ThietBi> list = query.list();
		return list;
	}
	
	public Integer add(ThietBi cttb) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(cttb);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;

	}

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(ModelMap model,HttpServletRequest request, HttpSession ss,@ModelAttribute("CTThietBi") ThietBi TB) {
		ss.setAttribute("navigation", "device_list");
		model.addAttribute("myparam", ss.getAttribute("myparam"));
		model.addAttribute("message", ss.getAttribute("message"));
		ss.removeAttribute("myparam");
		ss.removeAttribute("message");
		List<ThietBi> listCTThietBi = this.getListTB();
		PagedListHolder pagedListHolder = new PagedListHolder(listCTThietBi);
		int page = ServletRequestUtils.getIntParameter(request, "p", 0);

		pagedListHolder.setPage(page);
		pagedListHolder.setPageSize(8);
		pagedListHolder.setMaxLinkedPages(3);
		model.put("pagedListHolder", pagedListHolder);

		return "device/device";
	}

	public String getMaTuDong_CTTB( String LoaiTB) {

		Session session = factory.openSession();
		session.beginTransaction();
		ProcedureCall call = session.createStoredProcedureCall("sp_SinhID_CTThietBi");
		call.registerParameter("LoaiTB", String.class, ParameterMode.IN).bindValue(LoaiTB);
		Output output = call.getOutputs().getCurrent();
		ResultSetOutput resultSetReturn = ((ResultSetOutput) output);
		String id = (String) resultSetReturn.getSingleResult();
		session.getTransaction().commit();
		session.close();
		return id;
	}
	
	@RequestMapping(value = "index", params = "btnadd", method = RequestMethod.POST)
	public String addsp(HttpServletRequest request, ModelMap model, @ModelAttribute("CTThietBi") ThietBi TB, @ModelAttribute("soLuong") Integer soluong) {
		for (int i = 0; i < soluong; i++) {
			TB.setMaTBi(getMaTuDong_CTTB( TB.getLoaiTBi().getMaLoai()));
			Integer tmp = this.add(TB);
			model.addAttribute("myparam", "Thêm");
			if (tmp != 0) {
				model.addAttribute("message", "success");
			} else {
				model.addAttribute("message", "error");
			}
		}
		
		List<ThietBi> listCTThietBi = this.getListTB();
		PagedListHolder pagedListHolder = new PagedListHolder(listCTThietBi);
		int page = ServletRequestUtils.getIntParameter(request, "p", 0);

		pagedListHolder.setPage(page);
		pagedListHolder.setPageSize(8);
		pagedListHolder.setMaxLinkedPages(3);
		model.put("pagedListHolder", pagedListHolder);
		return "device/device";
	}

	public Integer deleteTb(String id) {
	
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			String qryString = "delete from ThietBi s where s.maTBi = :id";
			Query query = session.createQuery(qryString);
			query.setParameter("id", id);
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
	public String delete_SP(HttpServletRequest request, ModelMap model, @ModelAttribute("CTThietBi") ThietBi tb,
			@PathVariable("id") String id, HttpSession ss) {
		Integer tmp = this.deleteTb(id);
		ss.setAttribute("myparam", "Xóa");

		if (tmp != 0) {
			ss.setAttribute("message", "success");
		} else {
			ss.setAttribute("message", "error");
		}
		return "redirect:/device_list/index.htm";

	}

}