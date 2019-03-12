package application;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class WelcomeController {
	
	private Properties p; 
	
	private FunctionController functionController;
	
	private BorderPane functionPane;
	
	private HomePage hp;
	
	private String language;
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public void setHomePage(HomePage hp) {
		this.hp = hp;
	}
	
	@FXML
	private Button enter_button;
	
	public Button getEnter_button() {
		return enter_button;
	}
	
	@FXML
	private Label welcome_label;
	
	public Label getWelcome_label() {
		return welcome_label;
	}
	
	@FXML
	public void enterSystem() {
		if(hp.getLanguage().equals("Chinese")) {
			functionController.getAskFunction_label().setText(p.getProperty("askFunction_label_CN"));
	        functionController.getMovie_button().setText(p.getProperty("movie_button_CN"));
	        functionController.getBack_button().setText(p.getProperty("back_button_CN"));
	        hp.getStage().setTitle(p.getProperty("functionStage_title_CN"));
		}
		else if(hp.getLanguage().equals("English")) {
			functionController.getAskFunction_label().setText(p.getProperty("askFunction_label_US"));
	        functionController.getMovie_button().setText(p.getProperty("movie_button_US"));
	        functionController.getBack_button().setText(p.getProperty("back_button_US"));
	        hp.getStage().setTitle(p.getProperty("functionStage_title_US"));
		}
        functionController.setHomePage(hp);
        functionController.setLanguage(hp.getLanguage());
        hp.getRootLayout().setCenter(functionPane);
        
        
        //改变语言, 重新加载界面
        hp.getRootLayoutController().getChineseVersion().setOnAction((ActionEvent t) -> {
            reloadChineseVersion();
        });
        hp.getRootLayoutController().getEnglishVersion().setOnAction((ActionEvent t) -> {
            reloadEnglishVersion();
        });
	}
	
	public void reloadChineseVersion() {
		hp.setLanguage("Chinese");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChooseFunction.fxml"));
        try {
			functionPane = (BorderPane)loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
        functionController = loader.getController();
        functionController.getAskFunction_label().setText(p.getProperty("askFunction_label_CN"));
        functionController.getMovie_button().setText(p.getProperty("movie_button_CN"));
        functionController.getBack_button().setText(p.getProperty("back_button_CN"));
        functionController.setHomePage(hp);
        functionController.setLanguage("Chinese");
        hp.getRootLayout().setCenter(functionPane);
        hp.getStage().setTitle(p.getProperty("functionStage_title_CN"));
	}
	
	public void reloadEnglishVersion() {
		hp.setLanguage("English");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChooseFunction.fxml"));
        try {
			functionPane = (BorderPane)loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
        functionController = loader.getController();
        functionController.getAskFunction_label().setText(p.getProperty("askFunction_label_US"));
        functionController.getMovie_button().setText(p.getProperty("movie_button_US"));
        functionController.getBack_button().setText(p.getProperty("back_button_US"));
        functionController.setHomePage(hp);
        functionController.setLanguage("English");
        hp.getRootLayout().setCenter(functionPane);
        hp.getStage().setTitle(p.getProperty("functionStage_title_US"));
	}
	
	@FXML
	private void initialize() throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("test.properties")); 
        p = new Properties(); 
        p.load(in);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChooseFunction.fxml"));
        functionPane = (BorderPane)loader.load();
        functionController = loader.getController();
        enter_button.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    enterSystem();
                }
            }
        });
	}
}
