package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import model.User;
import model.validator.Notification;
import service.admin.ReportPdfGeneratorService;
import service.user.AuthenticationService;
import view.model.UserDTO;
import view.model.builder.UserDTOBuilder;
import view.AdminView;

public class AdminController {

    private final AdminView adminView;
    private final AuthenticationService authenticationService;
    private final ReportPdfGeneratorService reportPdfGeneratorService;
    private final User appUser;

    public AdminController(User appUser, AdminView adminView, AuthenticationService userService, ReportPdfGeneratorService reportPdfGeneratorService) {
        this.adminView = adminView;
        this.authenticationService = userService;
        this.reportPdfGeneratorService = reportPdfGeneratorService;
        this.appUser = appUser;

        this.adminView.addSaveButtonListener(new AdminController.SaveButtonListener());
        this.adminView.addGeneratPdfReportButtonListener(new AdminController.GeneratePdfReportButtonListener());
    }


    private class SaveButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            String username = adminView.getUsername();
            String password = adminView.getPassword();
            String role = adminView.getRole();

            if(username.isEmpty() || password.isEmpty() || role.isEmpty()){
                adminView.addDisplayAlertMessage("Save Error", "Problem at Username, Password or Role fields", "Can not have an empty field.");
            } else{
                Notification<Boolean> registerNotification = authenticationService.register(username, password, role);

                if (registerNotification.hasErrors()) {
                    adminView.setActionTargetText(registerNotification.getFormattedErrors());
                } else {
                    UserDTO userDTO = new UserDTOBuilder().setUsername(username).setRole(role).build();
                    adminView.addUserToObservableList(userDTO);
                    adminView.setActionTargetText("User Added Successful!");
                }
            }
        }
    }


    private class GeneratePdfReportButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            reportPdfGeneratorService.generateReport();
        }
    }
}
