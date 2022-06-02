
package qltb.Controller;

import java.util.List;

import javax.persistence.ParameterMode;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sound.midi.Soundbank;
import javax.transaction.Transactional;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
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

import qltb.Entity.ThietBi;
import qltb.Entity.LoaiTB;
import qltb.Entity.Phong;

@Transactional
@Controller
@RequestMapping("device_detail")
public class RoomDeviceController {

	@Autowired
	SessionFactory factory;

	@ModelAttribute("listPhong")
	public List<Phong> getPhong() {
		Session session = factory.getCurrentSession();
		String hql = "FROM Phong";
		Query query = session.createQuery(hql);
		List<Phong> list = query.list();
		return list;
	}

	@ModelAttribute("listTypeTB")
	public List<LoaiTB> getTypeTB() {
		Session session = factory.getCurrentSession();
		String hql = "from LoaiTB where  maLoai in ( select distinct loaiTBi.maLoai from ThietBi where phong is null)";
		Query query = session.createQuery(hql);
		List<LoaiTB> list = query.list();
		return list;

	}
	@ModelAttribute("listTB")
	public List<ThietBi> getLoaiTB() {
		Session session = factory.getCurrentSession();
		String hql = "FROM ThietBi where phong is null";
		Query query = session.createQuery(hql);
		List<ThietBi> list = query.list();
		return list;

	}

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(ModelMap model, HttpServletRequest request,HttpSession ss,@ModelAttribute("Phong")Phong phg) {

		ss.setAttribute("navigation", "device_detail");
		return "device/device_detail";
	}

	public Integer addTBToRoom(String maphong, String matb) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			String hql = "UPDATE ThietBi set phong.maPhong= :maPhong where maTBi=  :matb";
			Query query = session.createQuery(hql);
			query.setParameter("maPhong",maphong);
			query.setParameter("matb",matb);
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

	

	@RequestMapping(value = "index", params = "btnadd", method = RequestMethod.POST)
	public String addsp(ModelMap model,HttpServletRequest rq,@ModelAttribute("Phong")Phong phg) {
		String p="";
		for (ThietBi tb:  getLoaiTB()) {
		if(rq.getParameter(tb.getMaTBi())!=null) {
			int tmp=this.addTBToRoom( phg.getMaPhong(), tb.getMaTBi());
			if (tmp != 0) { p += "showtoast({\r\n" + "	title : \"Thành công!\",\r\n" +
					  "	message :  '" + tb.getMaTBi() +
					  " ! Thêm thiết bị thành công \"',\r\n" + "	type : \"success\",\r\n" +
					  "	duration : 3000\r\n" + "});"; }
			else { p += "showtoast({\r\n" +
					 "	title : \"Thất bại!\",\r\n" + "	message :  '" + tb.getMaTBi() +
					  " ! Thêm thiết bị thất bại \"',\r\n" + "	type : \"error\",\r\n" +
					  "	duration : 3000\r\n" + "});"; } 
		}
		}
		model.addAttribute("thongbao", p);
		model.addAttribute("listPhong", getPhong());
		model.addAttribute("listTB", getLoaiTB());
		return "device/device_detail";
	}
	
	 public void callSpBaoTriThietBi(String maTB) {
	  		Session session = factory.openSession();
	  		session.beginTransaction();
	  		ProcedureCall call = session.createStoredProcedureCall("sp_baotrithietbi");
	  		call.registerParameter("MaTB", String.class, ParameterMode.IN).bindValue(maTB);
	  		call.getOutputs().getCurrent();
	  		session.getTransaction().commit();
	  		session.close();
	  	}
	     
	     @RequestMapping("repair_device_id={id}")
	     public String hoanTatBaoTri( @PathVariable("id") String id,ModelMap model,@ModelAttribute("CTThietBi") ThietBi CTThietBi) {
	    	 this.callSpBaoTriThietBi(id);
	    	model.addAttribute("listPhong", getPhong());
	    	return "redirect:/device_detail/index.htm";
	     }
	     
	     
	     public void callSpBaoMatThietBi(String maTB) {
		  		Session session = factory.openSession();
		  		session.beginTransaction();
		  		ProcedureCall call = session.createStoredProcedureCall("sp_baomatthietbi");
		  		call.registerParameter("MaTB", String.class, ParameterMode.IN).bindValue(maTB);
		  		call.getOutputs().getCurrent();
		  		session.getTransaction().commit();
		  		session.close();
		  	}
		     
		     @RequestMapping("lost_device_id={id}")
		     public String baoMatThietBi( @PathVariable("id") String id,ModelMap model,@ModelAttribute("CTThietBi") ThietBi CTThietBi) {
		    	 this.callSpBaoMatThietBi(id);
		    	model.addAttribute("listPhong", getPhong());
		    	return "redirect:/device_detail/index.htm";
		     }
		     
		     
		     public void callSpHoanThanhBTThietBi(String maTB) {
			  		Session session = factory.openSession();
			  		session.beginTransaction();
			  		ProcedureCall call = session.createStoredProcedureCall("sp_hoantatbaotrithietbi");
			  		call.registerParameter("MaTB", String.class, ParameterMode.IN).bindValue(maTB);
			  		call.getOutputs().getCurrent();
			  		session.getTransaction().commit();
			  		session.close();
			  	}
			     
			     @RequestMapping("cpl_repair_device_id={id}")
			     public String hoanthanhBTThietBi( @PathVariable("id") String id,ModelMap model,@ModelAttribute("CTThietBi") ThietBi CTThietBi) {
			    	 this.callSpHoanThanhBTThietBi(id);
			    	model.addAttribute("listPhong", getPhong());
			    	return "redirect:/device_detail/index.htm";
			     }
}