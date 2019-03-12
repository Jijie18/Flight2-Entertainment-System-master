package application;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HomePage extends Application {
	
	private Statement stmt;
	
	private Properties p;
	
	private Stage stage;
	
	private String language;
	
	private BorderPane rootLayout;
	
	private RootLayoutController rootLayoutController;
	
	private WelcomeController welcomeController;
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public RootLayoutController getRootLayoutController() {
		return rootLayoutController;
	}
	
	public BorderPane getRootLayout() {
		return rootLayout;
	}
		
	public Statement getStatement() {
		return stmt;
	}
	
	public Properties getProperties() {
		return p;
	}
	
	public Stage getStage() {
		return stage;
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException, ClassNotFoundException, SQLException {
		InputStream in = new BufferedInputStream(new FileInputStream("test.properties")); 
        p = new Properties(); 
        p.load(in);
		language = "Chinese";
		stage = primaryStage;
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("RootLayout.fxml"));
        rootLayout = (BorderPane) loader.load();
        rootLayoutController = loader.getController();
        rootLayoutController.setHomePage(this);
        Scene scene = new Scene(rootLayout);
        stage.setScene(scene);
		FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(HomePage.class.getResource("Welcome.fxml"));
        BorderPane root = (BorderPane) loader2.load();
        welcomeController = loader2.getController();
        welcomeController.setHomePage(this);
        rootLayoutController.getChineseVersion().setOnAction((ActionEvent t) -> {
            reloadChineseVersion();
        });
        rootLayoutController.getEnglishVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadEnglishVersion();
			} catch (IOException e) {
				e.printStackTrace();
			}
        });
        
        rootLayout.setCenter(root);
        primaryStage.setTitle("Welcome");
        primaryStage.show();
	}

	private void reloadEnglishVersion() throws IOException {
		this.setLanguage("English");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Welcome.fxml"));
        BorderPane root = (BorderPane)loader.load();
        welcomeController = loader.getController();
        welcomeController.getEnter_button().setText(p.getProperty("enter_button_US"));
        welcomeController.getWelcome_label().setText(p.getProperty("welcome_label_US"));
        welcomeController.setHomePage(this);
        welcomeController.setLanguage("English");
        rootLayout.setCenter(root);
        this.getStage().setTitle(p.getProperty("welcomeStage_title_US"));
	}

	private void reloadChineseVersion() {
		this.setLanguage("Chinese");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Welcome.fxml"));
        BorderPane root = null;
        try {
			root = (BorderPane)loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
        welcomeController = loader.getController();
        welcomeController.getEnter_button().setText(p.getProperty("enter_button_CN"));
        welcomeController.getWelcome_label().setText(p.getProperty("welcome_label_CN"));
        welcomeController.setHomePage(this);
        welcomeController.setLanguage("Chinese");
        rootLayout.setCenter(root);
        this.getStage().setTitle(p.getProperty("welcomeStage_title_CN"));
	}

	public static void main(String[] args) {
		launch(args);
	}
}
