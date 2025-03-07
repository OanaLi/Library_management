package repository.pdfReport;

import view.model.UserReportDTO;

import java.util.List;

public interface EmployeeOrdersRepository {
    List<UserReportDTO> findAll();
}
