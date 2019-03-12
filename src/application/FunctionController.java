package application;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class FunctionController {
	
	@FXML
	private Button movie_button;
	
	@FXML
	private Label askFunction_label;
	
	@FXML
	private Button back_button;
	
	private String language;
	
	private Properties p;
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public Button getBack_button() {
		return back_button;
	}
	
	public Button getMovie_button() {
		return movie_button;
	}

	public Label getAskFunction_label() {
		return askFunction_label;
	}

	
	private HomePage hp;
	
	
	public void setHomePage(HomePage hp) {
		this.hp = hp;
	}
	
	@FXML
	public void backToWelcome() throws Exception {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Welcome.fxml"));
        BorderPane root = (BorderPane)loader.load();
        WelcomeController welcomeController = loader.getController();
        if(language.equals("Chinese")) {
        	welcomeController.getEnter_button().setText(p.getProperty("enter_button_CN"));
        	welcomeController.getWelcome_label().setText(p.getProperty("welcome_label_CN"));
        	hp.getStage().setTitle(p.getProperty("welcomeStage_title_CN"));
        }
        else if(language.equals("English")) {
        	welcomeController.getEnter_button().setText(p.getProperty("enter_button_US"));
        	welcomeController.getWelcome_label().setText(p.getProperty("welcome_label_US"));
        	hp.getStage().setTitle(p.getProperty("welcomeStage_title_US"));
        }
        hp.getRootLayout().setCenter(root);
        hp.getRootLayoutController().getChineseVersion().setOnAction((ActionEvent t) -> {
            reloadWelcomeChineseVersion();
        });
        hp.getRootLayoutController().getEnglishVersion().setOnAction((ActionEvent t) -> {
            reloadWelcomeEnglishVersion();
        });
        welcomeController.setHomePage(hp);
	}
	
	private void reloadWelcomeEnglishVersion() {
		hp.setLanguage("English");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Welcome.fxml"));
        BorderPane root = null;
        try {
			root = (BorderPane)loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
        WelcomeController welcomeController = loader.getController();
        welcomeController.getEnter_button().setText(p.getProperty("enter_button_US"));
        welcomeController.getWelcome_label().setText(p.getProperty("welcome_label_US"));
        welcomeController.setHomePage(hp);
        welcomeController.setLanguage("English");
        hp.getRootLayout().setCenter(root);
        hp.getStage().setTitle(p.getProperty("welcomeStage_title_US"));
	}

	private void reloadWelcomeChineseVersion() {
		hp.setLanguage("Chinese");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Welcome.fxml"));
        BorderPane root = null;
        try {
			root = (BorderPane)loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
        WelcomeController welcomeController = loader.getController();
        welcomeController.getEnter_button().setText(p.getProperty("enter_button_CN"));
        welcomeController.getWelcome_label().setText(p.getProperty("welcome_label_CN"));
        welcomeController.setHomePage(hp);
        welcomeController.setLanguage("Chinese");
        hp.getRootLayout().setCenter(root);
        hp.getStage().setTitle(p.getProperty("welcomeStage_title_CN"));
	}
	
	@FXML
	public void watchMovie() throws Exception {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChooseMovie.fxml"));
        BorderPane moviePane = (BorderPane)loader.load();
        MovieController movieController = loader.getController();
        movieController.setHomePage(hp);
        if(language.equals("Chinese")) {
        	movieController.getBack_button().setText(p.getProperty("back_button_CN"));
        	movieController.getMovieChoose_label().setText(p.getProperty("movieChoose_label_CN"));
        	movieController.setLanguage("Chinese");
        	hp.getStage().setTitle(p.getProperty("movieStage_title_CN"));
        }
        else if(language.equals("English")) {
        	movieController.getBack_button().setText(p.getProperty("back_button_US"));
        	movieController.getMovieChoose_label().setText(p.getProperty("movieChoose_label_US"));
        	movieController.setLanguage("English");
        	hp.getStage().setTitle(p.getProperty("movieStage_title_US"));
        }
        hp.getRootLayoutController().getChineseVersion().setOnAction((ActionEvent t) -> {
            reloadMovieChineseVersion();
        });
        hp.getRootLayoutController().getEnglishVersion().setOnAction((ActionEvent t) -> {
            reloadMovieEnglishVersion();
        });
        hp.getRootLayout().setCenter(moviePane);
	}
	
	private void reloadMovieEnglishVersion() {
		hp.setLanguage("English");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChooseMovie.fxml"));
        BorderPane root = null;
        try {
			root = (BorderPane)loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
        MovieController movieController = loader.getController();
        movieController.getBack_button().setText(p.getProperty("back_button_US"));
        movieController.getMovieChoose_label().setText(p.getProperty("movieChoose_label_US"));
        movieController.setHomePage(hp);
        movieController.setLanguage("English");
        hp.getRootLayout().setCenter(root);
        hp.getStage().setTitle(p.getProperty("movieStage_title_US"));
	}

	private void reloadMovieChineseVersion() {
		hp.setLanguage("Chinese");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChooseMovie.fxml"));
        BorderPane root = null;
        try {
			root = (BorderPane)loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
        MovieController movieController = loader.getController();
        movieController.getBack_button().setText(p.getProperty("back_button_CN"));
        movieController.getMovieChoose_label().setText(p.getProperty("movieChoose_label_CN"));
        movieController.setHomePage(hp);
        movieController.setLanguage("Chinese");
        hp.getRootLayout().setCenter(root);
        hp.getStage().setTitle(p.getProperty("movieStage_title_CN"));
	}

	@FXML
	private void initialize() throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("test.properties")); 
        p = new Properties(); 
        p.load(in);
        movie_button.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
					try {
						watchMovie();
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
            }
        });
        back_button.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
					try {
						backToWelcome();
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
            }
        });
	}
	
}
