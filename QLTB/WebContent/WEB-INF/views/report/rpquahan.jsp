<%@ page contentType="application/pdf" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="net.sf.jasperreports.engine.*" %>
<%@ page import="net.sf.jasperreports.engine.data.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>

<% 

	try{
		List<Map<String, ?>> dataSource =(List<Map<String, ?>>) request.getAttribute("listPM");
		JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataSource);
		String jrxmlFile = session.getServletContext().getRealPath("/report/thongkemuonquahan.jrxml");
	
		InputStream input = new FileInputStream(new File(jrxmlFile));
		JasperReport jasperReport = JasperCompileManager.compileReport(input);
		HashMap hm = new HashMap();
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hm,jrDataSource);
		JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}catch(Exception e){
		e.printStackTrace();
	}


%>