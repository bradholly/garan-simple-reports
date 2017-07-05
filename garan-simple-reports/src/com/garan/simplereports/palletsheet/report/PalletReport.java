package com.garan.simplereports.palletsheet.report;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.garan.simplereports.palletsheet.util.FileUtils;
import com.itextpdf.barcodes.Barcode39;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.TextAlignment;

public class PalletReport {

	private final Logger logger = LoggerFactory.getLogger(PalletReport.class);

	public String createReport(String dc, String po, String cases, String pallets) {
		try{
			String file = FileUtils.getTempFile(".pdf");
			logger.info("the random file name is: " + file);

			PdfWriter writer = new PdfWriter(file);
			PdfDocument pdfDocument = new PdfDocument(writer);
			Document document = new Document(pdfDocument, PageSize.LETTER.rotate());

			int palletY = Integer.parseInt(pallets);

			for (int palletX = 1; palletX <= palletY; palletX++) {
				createPalletPage(dc, po, cases, pdfDocument, document, palletY, palletX);
				document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
				createPalletPage(dc, po, cases, pdfDocument, document, palletY, palletX);
				
				if (palletX < palletY) {
					document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
				}
			}
			document.close();
			
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void createPalletPage(String dc, String po, String cases, PdfDocument pdfDocument, Document document,
			int palletY, int palletX) throws IOException {
		document.add(new Paragraph("PALLET CONTENTS")
				.setBold()
				.setFontSize(48)
				.setTextAlignment(TextAlignment.CENTER)
				.setFont(PdfFontFactory.createFont(FontConstants.COURIER)));

		document.add(new Paragraph("DC: " + dc)
				.setBold()
				.setFontSize(48)
				.setTextAlignment(TextAlignment.CENTER)
				.setFont(PdfFontFactory.createFont(FontConstants.COURIER)));

		document.add(new Paragraph("PO: " + po)
				.setBold()
				.setFontSize(48)
				.setTextAlignment(TextAlignment.CENTER)
				.setFont(PdfFontFactory.createFont(FontConstants.COURIER)));

		document.add(getBarcodeImage(pdfDocument, po)
				.setTextAlignment(TextAlignment.CENTER));

		document.add(new Paragraph("Total cases on PO: " + cases)
				.setBold()
				.setFontSize(48)
				.setTextAlignment(TextAlignment.CENTER)
				.setFont(PdfFontFactory.createFont(FontConstants.COURIER)));

		document.add(new Paragraph("Pallet " + palletX + " of " + palletY)
				.setBold()
				.setFontSize(48)
				.setTextAlignment(TextAlignment.CENTER)
				.setFont(PdfFontFactory.createFont(FontConstants.COURIER)));
	}

	private Image getBarcodeImage(PdfDocument pdfDocument, String data) {
		Barcode39 barcode = new Barcode39(pdfDocument);
		barcode.setCode(data);
		barcode.setX(4f);
		barcode.setBarHeight(150f);
		return new Image(barcode.createFormXObject(pdfDocument));
	}
}