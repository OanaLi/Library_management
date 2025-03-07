package launcher;

import controller.AdminController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.UserMapper;
import model.User;
import repository.pdfReport.EmployeeOrdersRepository;
import repository.pdfReport.EmployeeOrdersRepositoryMySQL;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.admin.ReportPdfGeneratorService;
import service.admin.UserService;
import service.admin.UserServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;
import view.AdminView;
import view.model.UserDTO;

import java.sql.Connection;
import java.util.List;

public class AdminComponentFactory {

    private final AdminController adminController;
    private final AdminView adminView;
    private final RightsRolesRepositoryMySQL rightsRolesRepository;
    private final UserRepository userRepository;
    private final EmployeeOrdersRepository employeeOrdersRepository;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final ReportPdfGeneratorService reportPdfGeneratorService;

    private static volatile AdminComponentFactory instance;


    public static AdminComponentFactory getInstance(Boolean componentsForTest, Stage primaryStage, User appUser){
        if (instance == null){
            synchronized (AdminComponentFactory.class){
                if(instance == null){
                    instance = new AdminComponentFactory(componentsForTest, primaryStage, appUser);
                }
            }
        }
        return instance;
    }


    private AdminComponentFactory(Boolean componentsForTest, Stage primaryStage, User appUser){
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.employeeOrdersRepository = new EmployeeOrdersRepositoryMySQL(connection);

        this.authenticationService = new AuthenticationServiceImpl(userRepository, rightsRolesRepository);
        this.userService = new UserServiceImpl(userRepository);
        this.reportPdfGeneratorService = new ReportPdfGeneratorService(employeeOrdersRepository);

        List<UserDTO> userDTOs = UserMapper.convertUserDTOToListToUserDTOList(userService.findAll());
        this.adminView = new AdminView(primaryStage, userDTOs);
        this.adminController = new AdminController(appUser, adminView, authenticationService, reportPdfGeneratorService);
    }
}
