package qltb.Scheduling;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import qltb.Entity.CTPhieuMuon;
import qltb.Entity.PhieuMuon;
import qltb.Entity.SinhVien;

@Component
@EnableScheduling
@Transactional
public class SchedulingApplication {

	@Autowired
	SessionFactory factory;

	public List<PhieuMuon> getPhieuMuonQuaHan() {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhieuMuon where thoiDiemTra is null and hanTra <= current_timestamp()";
		Query query = session.createQuery(hql);
		List<PhieuMuon> list = query.list();
		return list;
	}

	public List<SinhVien> getSVMuon_PM(String ID) {
		Session session = factory.getCurrentSession();
		String hql = "select distinct svMuon FROM CTPhieuMuon s where s.idCTPhieuMuon.idPhieuMuon = :id and trangThai= 3";
		Query query = session.createQuery(hql);
		query.setParameter("id", ID);
		List<SinhVien> list = query.list();
		return list;
	}

	@Autowired
	JavaMailSender mailer;
	@Autowired
	JavaMailSenderImpl mailsender;

	//@Scheduled(fixedDelay = 30000) // Test tự động delay 5s gửi mail 1 lần
	@Scheduled(cron = "0 0 7,12,17 * * *") // Tự động gửi mail lúc 7h 12h 17h
	public void sendmail() {
		System.out.println("Time: " + LocalDateTime.now());
		for (PhieuMuon pm : this.getPhieuMuonQuaHan()) {
			for (SinhVien sinhVien : getSVMuon_PM(pm.getMaPhieuMuon())) {
				try {
					MimeMessage mail = mailer.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(mail, true);
					helper.setFrom("n19dccn006@student.ptithcm.edu.vn");
					helper.setTo("anhle180101@gmail.com");
					// helper.setTo(sinhvien.getMaSV()+"@student.ptithcm.edu.vn");
					helper.setReplyTo(mailsender.getUsername());
					helper.setSubject("[ PCSVC! Yêu Cầu Trả Thiết Bị ]");
					String ctPhieuMuon = "";
					int i = 0;
					System.out.println("+++++++++++++++++++++++++" + sinhVien.getTenSV());
					for (CTPhieuMuon ctpm : pm.getListCTPhieuMuon()) {
						if (ctpm.getTrangThai() == 3 && ctpm.getSvMuon().getMaSV() == sinhVien.getMaSV())
							ctPhieuMuon += "<p>" + ++i + ": " + ctpm.getThietBi().getLoaiTBi().getTenLoai() + " - "
									+ ctpm.getPhieuMuon().getMaPhg().getMaPhong() + "</p>";
					}

					helper.setText(
							"<div style=\"margin:0;padding:0\" dir=\"ltr\" bgcolor=\"#ffffff\"><table style=\"border-style:solid;border-width:thin;border-color:#dadce0;border-radius:8px;padding:4px 20px\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" id=\"m_2897520027820035933email_table\" style=\"border-collapse:collapse\"><tbody><tr><td id=\"m_2897520027820035933email_content\" style=\"font-family:Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;background:#ffffff\"><table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse\"><tbody><tr><td height=\"20\" style=\"line-height:20px\" colspan=\"3\">&nbsp;</td></tr><tr><td height=\"1\" colspan=\"3\" style=\"line-height:1px\"><span style=\"color:#ffffff;font-size:1px;opacity:0\">&nbsp; Xin chào Anh, &nbsp; Chúng tôi nhận thấy một lần đăng nhập khác thường từ thiết bị hoặc vị trí bạn thường không sử dụng. Có phải bạn đã đăng nhập không? &nbsp; Lượt đăng nhập mới &nbsp; &nbsp; 29 tháng 3, 2022 lúc 19:17 &nbsp; &nbsp; Gần Phúc Lý, Vietnam &nbsp; &nbsp; Facebook Messenger for Android &nbsp;</span></td></tr><tr><td width=\"15\" style=\"display:block;width:15px\">&nbsp;&nbsp;&nbsp;</td><td><table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse\"><tbody><tr><td height=\"15\" style=\"line-height:15px\" colspan=\"3\">&nbsp;</td></tr><tr><td width=\"32\" align=\"left\" valign=\"middle\" style=\"height:32;line-height:0px\"><a style=\"color:#3b5998;text-decoration:none\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://www.facebook.com/n/?login_alerts%252Fstart%252F%26fbid%3D742416433798381%26s%3De%26aref%3D1648556226056122%26medium%3Demail%26mid%3D5db5a1ea90ce8G5afc965ce04eG5db5a683f0fbaG2bf%26n_m%3Danhle180101%2540gmail.com&amp;source=gmail&amp;ust=1650373264816000&amp;usg=AOvVaw16TX3eMYr8d8GWGTIhDVbJ\"><img src=\"https://user-images.githubusercontent.com/81857289/161365448-2d3a897e-8a96-4525-86b5-a7aa0cd7bb4f.png\" width=\"60\" height=\"60\" style=\"border:0\" class=\"CToWUd\"></a></td><td width=\"15\" style=\"display:block;width:15px\">&nbsp;&nbsp;&nbsp;</td><td width=\"100%\"><p  style=\"color:#ff0000;text-decoration:none;font-family:Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;font-size:19px;line-height:32px\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://www.facebook.com/n/?login_alerts%252Fstart%252F%26fbid%3D742416433798381%26s%3De%26aref%3D1648556226056122%26medium%3Demail%26mid%3D5db5a1ea90ce8G5afc965ce04eG5db5a683f0fbaG2bf%26n_m%3Danhle180101%2540gmail.com&amp;source=gmail&amp;ust=1650373264816000&amp;usg=AOvVaw16TX3eMYr8d8GWGTIhDVbJ\">"
									+ "Cảnh báo!! Quá Hạn Trả Thiết Bị</p></td></tr><tr style=\"border-bottom:solid 1px #e5e5e5\"><td height=\"15\" style=\"line-height:15px\" colspan=\"3\">&nbsp;</td></tr></tbody></table></td><td width=\"15\" style=\"display:block;width:15px\">&nbsp;&nbsp;&nbsp;</td></tr><tr><td width=\"15\" style=\"display:block;width:15px\">&nbsp;&nbsp;&nbsp;</td><td><table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse\"><tbody><tr><td height=\"28\" style=\"line-height:28px\">&nbsp;</td></tr><tr><td><span class=\"m_2897520027820035933mb_text\" style=\"font-family:Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;font-size:16px;line-height:21px;font-weight:bold;color:#141823\">"
									+ "Xin chào! " + sinhVien.getTenSV() + ","
									+ "</span></td></tr><tr><td height=\"28\" style=\"line-height:28px\">&nbsp;</td></tr><tr><td><span class=\"m_2897520027820035933mb_text\" style=\"font-family:Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;font-size:16px;line-height:21px;color:#141823\">"
									+ "Các thiết bị bạn mượn tại phòng Cơ Sở Vật Chất đã quá hạn trả! Vui lòng hoàn trả lại phòng cơ sở vật chất!</span></td></tr><tr><td height=\"28\" style=\"line-height:28px\">&nbsp;</td></tr><tr><td style=\"text-align: center;\"><span style=\"font-size:16px;font-weight:bold;color:#702fba\">"
									+ "Phiếu Mượn " + pm.getMaPhieuMuon()
									+ "</span></td></tr><tr><td height=\"28\" style=\"line-height:28px\">&nbsp;</td></tr><tr><td></td></tr><tr style=\"border-top:solid 1px #e5e5e5\"><td height=\"0\" style=\"line-height:0px\">&nbsp;</td></tr><tr><td></td></tr> <tr><td><table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"border-collapse:collapse;min-width:420px\"><tbody><tr><td valign=\"top\" style=\"padding:10px;font-size:0px\"><img src=\"https://ci4.googleusercontent.com/proxy/d6rX5gkKj0t5NvoFInNVBzDwZrsQ_zJreoQzj-jUuiyPDxRhRVGSAQpZC8P2bKfRj6Lb84l_bu70mCJ-L2XkGMB6RVfwhXXxcyw-W9hA3Q=s0-d-e1-ft#https://static.xx.fbcdn.net/rsrc.php/v3/yv/r/RdSO_hsxQmk.png\" width=\"16px\" height=\"16px\" style=\"border:0\" class=\"CToWUd\"></td><td width=\"100%\" valign=\"top\" style=\"padding:10px\"><span style=\"font-family:Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;font-size:16px;line-height:21px;color:#141823\">"
									+ " Phòng: " + pm.getMaPhg().getMaPhong() + " &nbsp; Lớp: "
									+ pm.getLopTC().getfullname()
									+ "</span></td></tr></tbody></table></td></tr> <tr><td></td></tr><tr style=\"border-top:solid 1px #e5e5e5;\"><td height=\"0\" style=\"line-height:0px\">&nbsp;</td></tr><tr><td><table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"border-collapse:collapse;min-width:420px\"><tbody><tr><td valign=\"top\" style=\"padding:10px;font-size:0px\"><img src=\"https://ci6.googleusercontent.com/proxy/XJJ9_W1e1lAf0DK343oJdaRXblm-vUxobgBwH-VCBLzypv2XcPj1g5Y0dsI3IL2kQqGlwSY9feRqwBjhyTDFkq4JQ-LhKCuSKEl0opvmug=s0-d-e1-ft#https://static.xx.fbcdn.net/rsrc.php/v3/yB/r/XazzlS6G-Oz.png\" width=\"16px\" height=\"16px\" style=\"border:0\" class=\"CToWUd\"></td><td width=\"100%\" valign=\"top\" style=\"padding:10px\"><span class=\"m_2897520027820035933mb_text\" style=\"font-family:Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;font-size:16px;line-height:21px;color:#141823\">"
									+ "Thời Gian Mượn: "
									+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(pm.getThoiDiemMuon())
									+ "</span></td></tr></tbody></table></td></tr><tr></tr><tr><td></td></tr><tr style=\"border-top:solid 1px #e5e5e5\"><td height=\"0\" style=\"line-height:0px\">&nbsp;</td></tr><tr><td><table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"border-collapse:collapse;min-width:420px\"><tbody><tr><td valign=\"top\" style=\"padding:10px;font-size:0px\"><img src=\"https://user-images.githubusercontent.com/81857289/163915325-fed7e057-dbed-4616-ac22-5585271e7a88.png\" width=\"16px\" height=\"19.18pxpx\" style=\"border:0\" class=\"CToWUd\"></td><td width=\"100%\" valign=\"top\" style=\"padding:10px\"><span class=\"m_2897520027820035933mb_text\" style=\"font-family:Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;font-size:16px;line-height:21px;color:#141823\">"
									+ "Hạn Trả: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(pm.getHanTra())
									+ " &nbsp; <b>Đã Quá Hạn </b>"
									+ "</span></td></tr></tbody></table></td></tr><tr></tr><tr><td></td></tr><tr style=\"border-top:solid 1px #e5e5e5\"><td height=\"0\" style=\"line-height:0px\">&nbsp;</td></tr><tr><td><table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"border-collapse:collapse;min-width:420px\"><tbody><tr><td valign=\"top\" style=\"padding:10px;font-size:0px\"><img src=\"https://user-images.githubusercontent.com/81857289/163916743-4bc66522-3224-4cce-9e92-bea249ad3235.png\" width=\"48px\" height=\"46px\" style=\"border:0\" class=\"CToWUd\"></td><td width=\"100%\" valign=\"top\" style=\"padding:10px\"><span class=\"m_2897520027820035933mb_text\" style=\"font-family:Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;font-size:16px;line-height:21px;color:#141823\">"
									+ "Các Thiết Bị Chưa Trả: " + ctPhieuMuon
									+ "</span></td></tr></tbody></table></td></tr><tr><td height=\"28\" style=\"line-height:28px\">&nbsp;</td></tr></tbody></table></td><td width=\"15\" style=\"display:block;width:15px\">&nbsp;&nbsp;&nbsp;</td></tr>"
									+ "<tr><td width=\"15\" style=\"display:block;width:15px\">&nbsp;&nbsp;&nbsp;</td><td><table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse\"><tbody><tr><td style=\"font-family:Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;font-size:11px;color:#aaaaaa;line-height:16px\"><span class=\"m_2897520027820035933mb_text\" style=\"font-family:Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;font-size:16px;line-height:21px;color:#141823;font-family:Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;font-size:12px;color:#aaaaaa;line-height:16px\">"
									+ "Vui lòng hoàn trả hoặc báo mất cho phòng CSVC! <br> Sinh viên không trả thiết bị đúng hạn bị xử lý theo quy định của Học Viện  </span></td></tr></tbody></table></td><td width=\"15\" style=\"display:block;width:15px\">&nbsp;&nbsp;&nbsp;</td></tr><tr><td height=\"20\" style=\"line-height:20px\" colspan=\"3\">&nbsp;</td></tr></tbody></table></td></tr></tbody></table></div><div dir=\"ltr\" data-smartmail=\"gmail_signature\"><div dir=\"ltr\"><div><div dir=\"ltr\"><div style=\"color:rgb(136,136,136);font-size:12.8px\"><i><font face=\"comic sans ms, sans-serif\">------------------------------<wbr>-----------------------------</font></i></div><div><p style=\"font-size:13px;color:rgb(136,136,136);margin:0px\"><i><font face=\"comic sans ms, sans-serif\"><span style=\"font-size:10pt;color:navy\"><b>"
									+ "Phòng Cơ sở vật chất</b><br>Học Viện Công Nghệ Bưu Chính Viễn Thông&nbsp;</span><span style=\"color:navy;font-size:10pt\">(Cơ sở TP.HCM)</span></font></i></p><p style=\"margin:0px\"><font face=\"comic sans ms, sans-serif\"><span style=\"font-size:13.3333px\"><i><font color=\"#000080\">"
									+ "Địa chỉ</font><font color=\"#073763\">:&nbsp;</font></i></span><span><font color=\"#0000ff\">97 Đường Man Thiện, Phường Hiệp Phú, Quận 9, TP. Hồ Chí Minh</font></span></font></p><p style=\"font-size:13px;color:rgb(136,136,136);margin:0px\"><span style=\"font-size:10pt;color:navy\"><i><font face=\"comic sans ms, sans-serif\">"
									+ "Phone: 028.389 666 75&nbsp;</font></i></span></p><p style=\"font-size:13px;color:rgb(136,136,136);margin:0px\"> \r\n<span style=\"font-size:10pt;color:navy\"><i><font face=\"comic sans ms, sans-serif\">"
									+ "Email:&nbsp;<a href=\"mailto:pcsvc@ptithcm.edu.vn\" style=\"color:rgb(17,85,204)\" target=\"_blank\">pcsvc@ptithcm.edu.vn</a>&nbsp;</font></i></span></p></div></div></div></div></div>",
							true);
					mailer.send(mail);

				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}

	}

}