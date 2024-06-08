package com.example.lms;

import com.example.lms.Import.Excel.ExcelDataImport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BooksController implements Initializable {
    @FXML
    Button AddBook,UpdateBook,DeleteBookButton,ListBook,SearchBook,ImportButton;
    ExcelDataImport excelDataImport;
    File file;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (excelDataImport == null) {
            excelDataImport = new ExcelDataImport();
        }
    }

    @FXML
    private void AddBookAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(AddBookController.class.getResource("addBook.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    @FXML
    private void UpdateBookAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdatedBook.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setOnCloseRequest(event1 -> {
            event1.consume();
            handleExit(stage);
        });

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    @FXML
    private void DeleteBookAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(DeleteBookController.class.getResource("DeleteBook.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setOnCloseRequest(event1 -> {
            event1.consume();
            handleExit(stage);
        });

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    private void ListBookAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Book_ListController.class.getResource("book_list.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


        stage.setOnCloseRequest(event1 -> {
            event1.consume();
            handleExit(stage);
        });
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    @FXML
    private void SearchBookAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(SearchController.class.getResource("Search.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setOnCloseRequest(event1 -> {
            event1.consume();
            handleExit(stage);
        });

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


    @FXML
    private void ImportAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.xls", "*.xlsm", "*.xlt", "*.xltx", "*.xltm", "*.xlsb", "*.xml", "*.csv", "*.dif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            excelDataImport.excelImport(selectedFile);
        }
    }

    private void handleExit(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(BooksController.class.getResource("Books.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.show();

            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
