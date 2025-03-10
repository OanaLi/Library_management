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
import javafx.stage.Stage;
import view.model.BookDTO;
import java.util.List;

public class BookView{

    private TableView bookTableView;
    private final ObservableList<BookDTO> booksObservableList;
    //DTO = data transfer object. Practic aici tinem doar informatiile pe care le dam interfetei. Nu dam date sensibile. Daca book are adresa atunci in DTO nu o punem de ex
    //2:29:50 Data Binding
    private TextField authorTextField;
    private TextField titleTextField;
    private TextField stockTextField;
    private TextField priceTextField;
    private TextField quantityTextField;
    private Label authorLabel;
    private Label titleLabel;
    private Label stockLabel;
    private Label priceLabel;
    private Label quantityLabel;
    private Button saveButton;
    private Button deleteButton;
    private Button sellButton;
    private Button backButton;
    private Stage primaryStage;

    public BookView(Stage primaryStage, List<BookDTO> books){
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Library");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);

        booksObservableList = FXCollections.observableArrayList(books); //OBS ATRIBUIREA SE FACE DOAR O DATA
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
        bookTableView = new TableView<BookDTO>();

        bookTableView.setPlaceholder(new Label("No books to display"));

        TableColumn<BookDTO, String> titleColumn = new TableColumn<BookDTO, String>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<BookDTO, String> authorColumn = new TableColumn<BookDTO, String>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<BookDTO, Integer> priceColumn = new TableColumn<BookDTO, Integer>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<BookDTO, Integer> stockColumn = new TableColumn<BookDTO, Integer>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        bookTableView.getColumns().addAll(titleColumn, authorColumn, priceColumn, stockColumn);

        bookTableView.setItems(booksObservableList);

        gridPane.add(bookTableView, 0,0,5,1);
    }

    private void initSaveOptions(GridPane gridPane) {
        titleLabel = new Label("Title");
        gridPane.add(titleLabel, 1, 1); //coloana, rand

        titleTextField = new TextField();
        gridPane.add(titleTextField, 2, 1);

        authorLabel = new Label("Author");
        gridPane.add(authorLabel, 3, 1);

        authorTextField = new TextField();
        gridPane.add(authorTextField, 4, 1);

        priceLabel = new Label("Price");
        gridPane.add(priceLabel, 1, 2); //coloana, rand

        priceTextField = new TextField();
        gridPane.add(priceTextField, 2, 2);

        stockLabel = new Label("Stock");
        gridPane.add(stockLabel, 3, 2);

        stockTextField = new TextField();
        gridPane.add(stockTextField, 4, 2);

        saveButton = new Button("Save");
        gridPane.add(saveButton, 5, 1);

        deleteButton = new Button("Delete");
        gridPane.add(deleteButton, 6, 1);

        quantityLabel = new Label("Quantity");
        gridPane.add(quantityLabel, 1, 3); //coloana, rand

        quantityTextField = new TextField();
        gridPane.add(quantityTextField, 2, 3);

        sellButton = new Button("Sell");
        gridPane.add(sellButton, 5, 3);

        backButton = new Button("back");
        gridPane.add(backButton, 6, 3);
    }

    public void addSaveButtonListener(EventHandler<ActionEvent> saveButtonListener){
        saveButton.setOnAction(saveButtonListener);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener){
        deleteButton.setOnAction(deleteButtonListener);
    }

    public void addSellButtonListener(EventHandler<ActionEvent> sellButtonListener){
        sellButton.setOnAction(sellButtonListener);
    }

    public void addBackButtonListener(EventHandler<ActionEvent> backButtonListener){
        backButton.setOnAction(backButtonListener);
    }

    public void addDisplayAlertMessage(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public String getTitle(){
        return titleTextField.getText();
    }

    public String getAuthor(){
        return authorTextField.getText();
    }

    public String getPrice(){
        return priceTextField.getText();
    }

    public String getStock(){
        return stockTextField.getText();
    }

        public String getQuantity(){
        return quantityTextField.getText();
    }

    public void addBookToObservableList(BookDTO bookDTO){
        this.booksObservableList.add(bookDTO);
    }

    public void removeBookFromObservableList(BookDTO bookDTO){
        this.booksObservableList.remove(bookDTO);
    }

    public TableView getBookTableView(){
        return bookTableView;
    }

    public Stage getPrimaryStage(){
        return primaryStage;
    }


}
