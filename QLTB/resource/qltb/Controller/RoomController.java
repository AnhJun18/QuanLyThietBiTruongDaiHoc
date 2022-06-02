
package qltb.Controller;

import java.util.List;

import javax.persistence.ParameterMode;
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
import org.springframework.web.bind.annotation.RequestParam;

import qltb.Entity.ThietBi;
import qltb.Entity.LoaiTB;
import qltb.Entity.Phong;

@Transactional
@Controller
@RequestMapping("room")
public class RoomController {
	
	
	@Autowired
	SessionFactory factory;
	
	public List<Phong> getPhong() {
		Session session = factory.getCurrentSession();
		String hql = "FROM Phong";
		Query query = session.createQuery(hql);
		List<Phong> list = query.list();
		return list;
	}
	
	public Integer addRoom(Phong phong) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(phong);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;

	}
     @RequestMapping("add")
     public String a(ModelMap model,@ModelAttribute("Phong") Phong phong,@RequestParam("idroom") String idroom ) {
    	 Phong room= new Phong();
    	 room.setMaPhong(idroom);
    	 if (this.addRoom(room)!=0) {
    		 model.addAttribute("message","success");
    		 model.addAttribute("action","Thêm Phòng");
    		 }
    	 else {
    		 model.addAttribute("message","error");
    		 model.addAttribute("action","Thêm Phòng");
    	 }
    	 model.addAttribute("listPhong", getPhong());
    	 model.addAttribute("listLoaiTB", getLoaiTB());
    	return "device/device_detail";
     }
     
     
     
     public void callSpBaoTriPhong(String maPhong) {

 		Session session = factory.openSession();
 		session.beginTransaction();
 		ProcedureCall call = session.createStoredProcedureCall("sp_baotriphong");
 		call.registerParameter("Phong", String.class, ParameterMode.IN).bindValue(maPhong);
 		call.getOutputs().getCurrent();
 		session.getTransaction().commit();
 		session.close();
 	}
     
     @RequestMapping("maintena_id={id}")
     public String a( @PathVariable("id") String id,ModelMap model,@ModelAttribute("Phong") Phong phong) {
    	 this.callSpBaoTriPhong(id);
    	model.addAttribute("listPhong", getPhong());
    	return "redirect:/device_detail/index.htm";
     }
     
     public void callSpHoanThanhBaoTriPhong(String maPhong) {

  		Session session = factory.openSession();
  		session.beginTransaction();
  		ProcedureCall call = session.createStoredProcedureCall("sp_hoantatbaotriphong");
  		call.registerParameter("Phong", String.class, ParameterMode.IN).bindValue(maPhong);
  		call.getOutputs().getCurrent();
  		session.getTransaction().commit();
  		session.close();
  	}
     
     @RequestMapping("maintenance_id={id}_completed")
     public String hoanTatBaoTri( @PathVariable("id") String id,ModelMap model,@ModelAttribute("Phong") Phong phong) {
    	 this.callSpHoanThanhBaoTriPhong(id);
    	model.addAttribute("listPhong", getPhong());
    	return "redirect:/device_detail/index.htm";
     }
     
     public Integer deletePhong(String phong) {
 		Session session = factory.openSession();
 		Transaction t = session.beginTransaction();
 		try {
 			String qryString = "delete from Phong s where s.maPhong= :id";
 			Query query = session.createQuery(qryString);
 			query.setParameter("id",phong);
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
 
 	@RequestMapping(value = "delete_id={id}")
 	public String delete_SP( ModelMap model, @ModelAttribute("Phong") Phong phong,
 			@PathVariable("id") String id) {
 
 		Integer tmp = this.deletePhong(id);
 		model.addAttribute("action", "Xóa Phòng");
 		if (tmp != 0) {
 			model.addAttribute("message", "success");
 		} else {
 			model.addAttribute("message", "error");
 		}
 		model.addAttribute("listPhong", getPhong());
 		return "device/device_detail";
 
 	}
 	
 	public Phong getPhongByID(String ID) {
		Session session = factory.getCurrentSession();
		String hql = "FROM Phong where maPhong = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", ID);
		Phong list = (Phong) query.list().get(0);
		return list;
	}
 	@RequestMapping(value = "device_detail={id}_pdf")
 	public String print( ModelMap model, @ModelAttribute("Phong") Phong phong,
 			@PathVariable("id") String id) {
 
 		model.addAttribute("listTB", this.getPhongByID(id).getDstb());
 		model.addAttribute("phong", id);
 		return "report/rpthietbi";
 
 	}
 	
	public List<LoaiTB> getLoaiTB() {
		Session session = factory.getCurrentSession();
		String hql = "FROM LoaiTB";
		Query query = session.createQuery(hql);
		List<LoaiTB> list = query.list();
		return list;

	}
}