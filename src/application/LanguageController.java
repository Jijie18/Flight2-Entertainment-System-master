package application;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LanguageController {
	
	private Properties p; 
	
	private FunctionController functionController;
	
	private BorderPane functionPane;
	
	@FXML
	private Button english_button;
	@FXML
	private Button chinese_button;
	
	private HomePage hp;
	
	public void setHomePage(HomePage hp) {
		this.hp = hp;
	}
	
	public Button getEnglish_button() {
		return english_button;
	}

	public Button getChinese_button() {
		return chinese_button;
	}

	@FXML
	public void enterEnglishVersion() throws IOException {
        functionController.getMovie_button().setText(p.getProperty("movie_button_US"));
        functionController.getAskFunction_label().setText(p.getProperty("askFunction_label_US"));
        functionController.getBack_button().setText(p.getProperty("back_button_US"));
        functionController.setHomePage(hp);
        functionController.setLanguage("English");
        hp.getRootLayout().setCenter(functionPane);
        hp.getStage().setTitle(p.getProperty("functionStage_title_US"));
        
        //改变语言, 重新加载界面
        hp.getRootLayoutController().getChineseVersion().setOnAction((ActionEvent t) -> {
            reloadChineseVersion();
        });
        hp.getRootLayoutController().getEnglishVersion().setOnAction((ActionEvent t) -> {
            reloadEnglishVersion();
        });
	}
	
	@FXML
	public void enterChineseVersion() throws IOException {
        functionController.getAskFunction_label().setText(p.getProperty("askFunction_label_CN"));
        functionController.getMovie_button().setText(p.getProperty("movie_button_CN"));
        functionController.getBack_button().setText(p.getProperty("back_button_CN"));
        functionController.setHomePage(hp);
        hp.getRootLayout().setCenter(functionPane);
        hp.getStage().setTitle(p.getProperty("functionStage_title_CN"));
        
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
        english_button.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
						enterEnglishVersion();
					} catch (IOException e) {
						e.printStackTrace();
					}
                }
            }
        });
        chinese_button.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
						enterChineseVersion();
					} catch (IOException e) {
						e.printStackTrace();
					}
                }
            }
        });
	}

}
