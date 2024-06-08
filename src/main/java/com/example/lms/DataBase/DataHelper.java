package com.example.lms.DataBase;

import com.example.lms.Datas.Book;
import com.example.lms.Datas.BookBC;
import com.example.lms.Datas.BookUpdate;
import com.example.lms.Datas.Member;
import com.example.lms.Session.SessionManager;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

import java.sql.*;

public class DataHelper {

    public static boolean insertNewMember(Member user) {
        String sql = "INSERT INTO user (Username, email, password, phone, read_books) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = DataBaseHandler.getInstance().getConnection().prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getPhone());
            statement.setInt(5, 0);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException exception) {
            System.err.println("Error while inserting new user: " + exception.getMessage());
            return false;
        }
    }

    public static boolean insertBook(Book book) {
        String checkSql = "SELECT * FROM booktable WHERE book_title = ? AND author = ? AND user_id = ?";
        String insertSql = "INSERT INTO booktable (book_title, author, publisher, page_number, categories, user_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement checkStatement = DataBaseHandler.getInstance().getConnection().prepareStatement(checkSql);
             PreparedStatement insertStatement = DataBaseHandler.getInstance().getConnection().prepareStatement(insertSql)) {

            checkStatement.setString(1, book.getBookTitle());
            checkStatement.setString(2, book.getAuthor());
            checkStatement.setInt(3, book.getUserId());
            ResultSet rsCheck = checkStatement.executeQuery();

            if (rsCheck.next()) {
                showAlert("The book already exists");
                return false;
            }

            insertStatement.setString(1, book.getBookTitle());
            insertStatement.setString(2, book.getAuthor());
            insertStatement.setString(3, book.getPublisher());
            insertStatement.setString(4, book.getPageNumber());
            insertStatement.setString(5, book.getCategories());
            insertStatement.setInt(6, book.getUserId());

            int rowsInserted = insertStatement.executeUpdate();

            if (rowsInserted > 0) {
                UpdateBC(book.getBookTitle());
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error while inserting book: " + e.getMessage());
            return false;
        }
    }

    public static int getUserCount() {
        String sql = "SELECT COUNT(*) AS userCount FROM user";
        try (PreparedStatement statement = DataBaseHandler.getInstance().getConnection().prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("userCount");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getUserBooks() {
        String sql = "SELECT COUNT(*) AS total_rows FROM booktable";
        int userId = SessionManager.getInstance().getLoggedInUserId();
        try (PreparedStatement statement = DataBaseHandler.getInstance().getConnection().prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total_rows");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean UpdateBooks(BookUpdate book) {
        String sql = "UPDATE booktable SET book_title=?, author=?, publisher=?, page_number=?, categories=? WHERE book_id=?";
        try (PreparedStatement statement = DataBaseHandler.getInstance().getConnection().prepareStatement(sql)) {
            statement.setString(1, book.getBookTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getPublisher());
            statement.setString(4, book.getPage_number());
            statement.setString(5, book.getCategories());
            statement.setInt(6, book.getBookId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                UpdateBC(book.getBookTitle());
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean DeleteBook(int bookid) {
        String sql = "DELETE FROM booktable WHERE book_id = ?";
        try (PreparedStatement statement = DataBaseHandler.getInstance().getConnection().prepareStatement(sql)) {

            String booktitle = getBookTitleById(bookid);

            statement.setInt(1, bookid);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                UpdateBC(booktitle);
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean UpdateRB() {
        String sql = "UPDATE user u SET read_books = (SELECT COUNT(*) FROM booktable b WHERE b.user_id = u.user_id)";
        try (PreparedStatement statement = DataBaseHandler.getInstance().getConnection().prepareStatement(sql)) {

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void UpdateBC(String booktitle) {
        String sql = "UPDATE booktable bt1 " +
                "JOIN ( " +
                "    SELECT book_title, COUNT(*) AS count " +
                "    FROM booktable " +
                "    WHERE book_title = ? " +
                "    GROUP BY book_title " +
                ") bt2 ON bt1.book_title = bt2.book_title " +
                "SET bt1.book_count = bt2.count";

        try (PreparedStatement preparedStatement = DataBaseHandler.getInstance().getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, booktitle);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getBookTitleById(int bookId) throws SQLException {
        String sql = "SELECT book_title FROM booktable WHERE book_id = ?";
        try (PreparedStatement statement = DataBaseHandler.getInstance().getConnection().prepareStatement(sql)) {
            statement.setInt(1, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("book_title");
                }
                return null;
            }
        }
    }

    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Information Message");
        alert.setContentText(message);

        ButtonType okButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okButton);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Book.class.getResource("/css/dark_Theme.css").toExternalForm());

        alert.showAndWait();
    }
}
