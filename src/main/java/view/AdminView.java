package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.model.UserDTO;
import static database.Constants.Roles.ROLES;

import java.util.List;

public class AdminView {
    private TableView userTableView;
    private final ObservableList<UserDTO> usersObservableList;
    private TextField usernameTextField;
    private TextField passwordTextField;
    private ComboBox<String> roleComboBox;
    private Text actionTarget;

    private Label usernameLabel;
    private Label passwordLabel;
    private Label roleLabel;
    private Button saveButton;
    private Button generatePdfReportButton;

    public AdminView(Stage primaryStage, List<UserDTO> users){
        primaryStage.setTitle("Library");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);

        usersObservableList = FXCollections.observableArrayList(users); //OBS ATRIBUIREA SE FACE DOAR O DATA
        initTableView(gridPane);
        initSaveOptions(gridPane);

        primaryStage.show();
    }

    private void initializeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    //2:50:00
    private void initTableView(GridPane gridPane) {
        userTableView = new TableView<UserDTO>();

        userTableView.setPlaceholder(new Label("No users to display"));

        TableColumn<UserDTO, String> usernameColumn = new TableColumn<UserDTO, String>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<UserDTO, String> roleColumn = new TableColumn<UserDTO, String>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        userTableView.getColumns().addAll(usernameColumn, roleColumn);

        userTableView.setItems(usersObservableList);

        gridPane.add(userTableView, 0,0,5,1);
    }

    private void initSaveOptions(GridPane gridPane) {
        usernameLabel = new Label("Username");
        gridPane.add(usernameLabel, 1, 1); //coloana, rand

        usernameTextField = new TextField();
        gridPane.add(usernameTextField, 2, 1);

        passwordLabel = new Label("Password");
        gridPane.add(passwordLabel, 1, 2);

        passwordTextField = new TextField();
        gridPane.add(passwordTextField, 2, 2);

        roleLabel = new Label("Role");
        gridPane.add(roleLabel, 1, 3); //coloana, rand

        roleComboBox = new ComboBox<>();
        roleComboBox.setItems(FXCollections.observableArrayList(ROLES));
        roleComboBox.setPromptText("");
        gridPane.add(roleComboBox, 2, 3);

        saveButton = new Button("Save");
        gridPane.add(saveButton, 3, 3);

        generatePdfReportButton = new Button("Generate PDF Report");
        gridPane.add(generatePdfReportButton, 6, 3);

        actionTarget = new Text();
        actionTarget.setFill(Color.FIREBRICK); //AICI SE SCRIU ERORILE
        gridPane.add(actionTarget, 1, 6); //TRB SCHIMBAT
    }

    public void addSaveButtonListener(EventHandler<ActionEvent> saveButtonListener){
        saveButton.setOnAction(saveButtonListener);
    }

    public void addGeneratPdfReportButtonListener(EventHandler<ActionEvent> addGeneratPdfReportButtonListener){
        generatePdfReportButton.setOnAction(addGeneratPdfReportButtonListener);
    }

    public void addDisplayAlertMessage(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public String getUsername(){
        return usernameTextField.getText();
    }

    public String getPassword(){
        return passwordTextField.getText();
    }

    public String getRole(){
        if (roleComboBox.getValue() == null)
            return "";
        else
            return roleComboBox.getValue();
    }

    public void setActionTargetText(String text){ this.actionTarget.setText(text);}

    public void addUserToObservableList(UserDTO userDTO){
        this.usersObservableList.add(userDTO);
    }

    public TableView getUserTableView(){
        return userTableView;
    }
}
