package controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.AdminComponentFactory;
import launcher.EmployeeComponentFactory;
import model.Role;
import model.User;
import model.validator.Notification;
import service.user.AuthenticationService;
import view.LoginView;
import static database.Constants.Roles.*;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;

    public LoginController(LoginView loginView, AuthenticationService authenticationService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }


    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {



            String username = loginView.getUsername();
            String password = loginView.getPassword();


            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasErrors()){
                loginView.setActionTargetText(loginNotification.getFormattedErrors()); //TS 43:20
            }else{
                loginView.setActionTargetText("LogIn Successful!");

                User user = loginNotification.getResult();
                Role role = user.getRoles().getFirst();

                switch (role.getRole()) {
                    case ADMINISTRATOR -> AdminComponentFactory.getInstance(false, loginView.getPrimaryStage(), user);
                    case EMPLOYEE -> EmployeeComponentFactory.getInstance(false, loginView.getPrimaryStage(), user);
                    case CUSTOMER -> EmployeeComponentFactory.getInstance(false, loginView.getPrimaryStage(), user);
                }
            }
        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password, CUSTOMER);

            if (registerNotification.hasErrors()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Register Successful!");
            }
        }
    }
}