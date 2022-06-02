package qltb.report;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import qltb.Entity.PhieuMuon;

public class PhieuMuonReport {
    public List<Map<String, ?>>  dataPrint(List<PhieuMuon> data){
    	List<Map<String, ?>> listPM = new ArrayList<Map<String,?>>();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
    	for (PhieuMuon pm : data) {
    		Map<String,Object> m = new HashMap<String, Object>();
			m.put("id", pm.getMaPhieuMuon());
			m.put("name", pm.getMaPhg().getMaPhong());
			m.put("lop", pm.getLopTC().getfullname());
		//	m.put("sinhvien", pm.getSvMuon().getTenSV());
			String tmp=pm.getThoiDiemMuon().toLocalDateTime().format(formatter);
			m.put("timemuon",tmp);
			 tmp=pm.getHanTra().toLocalDateTime().format(formatter);
			m.put("hantra", tmp);
			listPM.add(m);
		}
    	return listPM;
    
    }
	
}
