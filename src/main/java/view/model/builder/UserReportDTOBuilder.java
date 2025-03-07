package view.model.builder;

import view.model.UserReportDTO;

public class UserReportDTOBuilder {
    private UserReportDTO userReport;

    public UserReportDTOBuilder(){
        userReport = new UserReportDTO();
    }

    public UserReportDTOBuilder setUsername(String username) {
        userReport.setUsername(username);
        return this;
    }

    public UserReportDTOBuilder setSoldBooks(int soldBooks) {
        userReport.setSoldBooks(soldBooks);
        return this;
    }

    public UserReportDTOBuilder setTotalSum(int totalSum) {
        userReport.setTotalSum(totalSum);
        return this;
    }

    public UserReportDTO build(){
        return userReport;
    }
}
