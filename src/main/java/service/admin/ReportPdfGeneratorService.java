package service.admin;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import repository.pdfReport.EmployeeOrdersRepository;
import view.model.UserReportDTO;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportPdfGeneratorService {

    private final EmployeeOrdersRepository employeeOrderRepository;

    public ReportPdfGeneratorService(EmployeeOrdersRepository employeeOrderRepository) {
        this.employeeOrderRepository = employeeOrderRepository;
    }

    public void generateReport() {

        String monthYear = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMMyyyy"));
        String pdfName = "Sales_Report_" + monthYear + ".pdf";
        PdfWriter writer;

        try {
            writer = new PdfWriter(pdfName);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        document.add(new Paragraph("Raportul vanzarilor per angajat " + monthYear).setBold().setFontSize(16));

        float[] columnWidths = {1, 3, 3}; // Dimensiuni coloane
        Table table = new Table(columnWidths);

        tableAddCells(table);
        document.add(table);
        document.close();
    }

    private void tableAddCells(Table table) {
        List<UserReportDTO> employees = employeeOrderRepository.findAll();

        table.addCell("Angajat");
        table.addCell("Carti vandute");
        table.addCell("Suma totala");

        for(UserReportDTO employee : employees) {
            table.addCell(employee.getUsername());
            table.addCell(String.valueOf(employee.getSoldBooks()));
            table.addCell(String.valueOf(employee.getTotalSum()));
        }

    }
}