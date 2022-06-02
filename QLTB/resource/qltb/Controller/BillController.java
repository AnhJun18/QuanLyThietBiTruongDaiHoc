
package qltb.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.ParameterMode;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.jpa.event.internal.core.JpaSaveOrUpdateEventListener;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import qltb.Entity.ThietBi;
import qltb.Entity.LoaiTB;
import qltb.Entity.PhieuMuon;
import qltb.Entity.Phong;
import qltb.Entity.SinhVien;
import qltb.report.PhieuMuonReport;

@Transactional
@Controller
@RequestMapping("bill")
public class BillController {
     private String radiomode="tatca";
     private String from=this.getNow();
     private String to=this.getNow();
     
	@Autowired
	SessionFactory factory;

	public List<PhieuMuon> getAllPM(String fromDate, String toDate) {
		fromDate += " 00:00";
		toDate += " 23:59";
		Session session = factory.getCurrentSession();
		String hql = "FROM PhieuMuon \r\n" + "where thoidiemmuon > :tungay and thoidiemmuon < :denngay ";
		Query query = session.createQuery(hql);
		query.setParameter("tungay", fromDate);
		query.setParameter("denngay", toDate);
		List<PhieuMuon> list = query.list();

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
	public PhieuMuon getPhieuMuonByID(String ID) {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhieuMuon where maPhieuMuon = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", ID);
		PhieuMuon list = (PhieuMuon) query.list().get(0);
		return list;
	}
	@ModelAttribute("timeNow")
	public String getNow() {
		LocalDateTime time = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formatDateTime = time.format(formatter);
		return formatDateTime;
	}

	public List<PhieuMuon> getPMQuaHan() {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhieuMuon \r\n" + "where hantra < current_timestamp() and thoidiemtra is null ";
		Query query = session.createQuery(hql);
		List<PhieuMuon> list = query.list();

		return list;
	}

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(ModelMap model, HttpServletRequest request, HttpSession ss) {
		ss.setAttribute("navigation", "bill");
		List<PhieuMuon> listCTThietBi = this.getAllPM(getNow(), getNow());
		PagedListHolder pagedListHolder = new PagedListHolder(listCTThietBi);
		int page = ServletRequestUtils.getIntParameter(request, "p", 0);

		model.addAttribute("timeFrom", getNow());
		model.addAttribute("timeTo", getNow());
		model.addAttribute("statusRd", "tatca");
		model.addAttribute("link", "/bill/index.htm");
		pagedListHolder.setPage(page);
		pagedListHolder.setPageSize(5);
		pagedListHolder.setMaxLinkedPages(4);
		model.put("pagedListHolder", pagedListHolder);
		
		model.addAttribute("statusModal",ss.getAttribute("statusModal"));
	    model.addAttribute("listSVMuon", ss.getAttribute("listSVMuon"));
	    model.addAttribute("thongtinPhieuMuon", ss.getAttribute("thongtinPhieuMuon"));
		ss.removeAttribute("statusModal");
	    ss.removeAttribute("listSVMuon");
	    ss.removeAttribute("thongtinPhieuMuon");

		return "bill/index";
	}
	@RequestMapping(value = "detail_{id}")
	public String ct(ModelMap model, HttpServletRequest request, HttpSession ss,@PathVariable("id") String id) {
		List<PhieuMuon> listCTThietBi = this.getAllPM(getNow(), getNow());
		PagedListHolder pagedListHolder = new PagedListHolder(listCTThietBi);
		int page = ServletRequestUtils.getIntParameter(request, "p", 0);

		model.addAttribute("timeFrom", getNow());
		model.addAttribute("timeTo", getNow());
		model.addAttribute("statusRd", "tatca");
		model.addAttribute("link", "/bill/index.htm");
		pagedListHolder.setPage(page);
		pagedListHolder.setPageSize(5);
		pagedListHolder.setMaxLinkedPages(4);
		model.put("pagedListHolder", pagedListHolder);
        ss.setAttribute("statusModal","open");
    	ss.setAttribute("listSVMuon", this.getSVMuon_PM(id));
    	ss.setAttribute("thongtinPhieuMuon", this.getPhieuMuonByID(id));
		
		return "redirect:/bill/index.htm";
	}

	@RequestMapping(value = "index", params = "btnfilter")
	public String filter(ModelMap model, HttpServletRequest request, HttpSession ss,
			@ModelAttribute("rdfilte") String radiobox, @ModelAttribute("from") String from,
			@ModelAttribute("to") String to) {

		List<PhieuMuon> listCTThietBi = null;
		if (radiobox.equals("tatca")) {
			this.radiomode="tatca";
			listCTThietBi = this.getAllPM(from, to);
			this.from=from;
			this.to=to;
		} else {
			this.radiomode="quahan";
			listCTThietBi = this.getPMQuaHan();
		}
		model.addAttribute("timeFrom", from);
		model.addAttribute("link", "bill\\?" + request.getQueryString().replaceAll("p=..", "") + "\t");
		model.addAttribute("timeTo", to);
		model.addAttribute("statusRd", radiobox);
		PagedListHolder pagedListHolder = new PagedListHolder(listCTThietBi);
		int page = ServletRequestUtils.getIntParameter(request, "p", 0);

		pagedListHolder.setPage(page);
		pagedListHolder.setPageSize(10);
		pagedListHolder.setMaxLinkedPages(4);
		model.put("pagedListHolder", pagedListHolder);

		return "bill/index";
	}

	@RequestMapping(value = "index", params = "print")
	public String tedst(HttpServletRequest sq, ModelMap model) {

		PhieuMuonReport pm = new PhieuMuonReport();
		if (this.radiomode.equals("quahan")) {
			model.addAttribute("listPM", pm.dataPrint(getPMQuaHan()));
			return "report/rpquahan";
		} else {
			model.addAttribute("from",this.from);
			model.addAttribute("to",this.to);
			model.addAttribute("listPM", pm.dataPrint(getAllPM(this.from, this.to)));
			return "report/rptheongay";
		}

	}

}