package com.garan.simplereports.palletsheet.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.garan.simplereports.palletsheet.report.PalletReport;

/**
 * Servlet implementation class PalletSheetServlet
 */
@WebServlet("/PalletSheetServlet")
public class PalletSheetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PalletSheetServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String dc = request.getParameter("dc");
		String po = request.getParameter("po");
		String cases = request.getParameter("cases");
		String pallets = request.getParameter("pallets");
		
		// validations
		if (! StringUtils.isNumeric(dc)) {
			response.getWriter().append("dc must be numeric");
			return;
		}

		if (! StringUtils.isNumeric(po)) {
			response.getWriter().append("po must be numeric");
			return;
		}
		
		if (! StringUtils.isNumeric(cases)) {
			response.getWriter().append("cases must be numeric");
			return;
		}
		
		if (! StringUtils.isNumeric(pallets)) {
			response.getWriter().append("pallets must be numeric");
			return;
		}
		
//		response.getWriter().append(" dc: ").append(dc);
//		response.getWriter().append(" po: ").append(po);
//		response.getWriter().append(" cases: ").append(cases);
//		response.getWriter().append(" pallets: ").append(pallets);
		
		PalletReport report = new PalletReport();
		String fileString = report.createReport(dc, po, cases, pallets);
		
		File file = new File(fileString);

		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "attachment; filename=" + file.getName());
		response.setContentLength((int) file.length());
		
		FileInputStream fileInputStream = new FileInputStream(file);
		OutputStream responseOutputStream = response.getOutputStream();
		int bytes;
		while ((bytes = fileInputStream.read()) != -1) {
			responseOutputStream.write(bytes);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
