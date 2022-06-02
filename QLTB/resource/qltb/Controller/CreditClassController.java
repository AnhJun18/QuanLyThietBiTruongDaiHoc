
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
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import qltb.Entity.LopTinChi;

@Transactional
@Controller
@RequestMapping("credit_class")
public class  CreditClassController {

	@Autowired
	SessionFactory factory;
	
	public List<LopTinChi> getListLTC() {
		Session session = factory.getCurrentSession();
		String hql = "FROM LopTinChi";
		Query query = session.createQuery(hql);
		List<LopTinChi> list = query.list();
		return list;
	}
	
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(ModelMap model,HttpServletRequest request, HttpSession ss, @ModelAttribute("LopTinChi") LopTinChi ltc) {
		ss.setAttribute("navigation", "creditclass");
		model.addAttribute("message", ss.getAttribute("message"));
		ss.removeAttribute("message");
		
		List<LopTinChi> listLTC = this.getListLTC();
		PagedListHolder pagedListHolder = new PagedListHolder(listLTC);
		int page = ServletRequestUtils.getIntParameter(request, "p", 0);

		pagedListHolder.setPage(page);
		pagedListHolder.setPageSize(8);
		pagedListHolder.setMaxLinkedPages(3);
		model.put("pagedListHolder", pagedListHolder);

		return "creditclass/index";
	}
	
	public Integer add(LopTinChi ltc) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			System.out.println('t'+ltc.getMaLTC());
			session.save(ltc);
			System.out.println('s'+ltc.getMaLTC());
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
	@RequestMapping(value = "index", params = "btnadd", method = RequestMethod.POST)
		public String addsp(HttpSession ss, ModelMap model, @ModelAttribute("LopTinChi") LopTinChi ltc,BindingResult errors) {
		    System.out.println(ltc.getMaLTC()+'|');
		    ltc.setMaLTC("CA");
			Integer tmp=this.add(ltc);
			if (tmp != 0) {
				ss.setAttribute("message", "success");
			} else {
				model.addAttribute("message", "error");
				model.addAttribute("myparam", "Thông tin LTC bạn nhập đã tồn tại!");
				model.put("pagedListHolder",new PagedListHolder(this.getListLTC()));
				model.addAttribute("statusmodal","open");
				return "creditclass/index";
			}

			return "redirect:index.htm";
	}


}