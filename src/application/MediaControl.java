package application;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class MediaControl extends BorderPane {
    private MediaPlayer mp;
    private HomePage hp;
    private String language;
    private MediaView mediaView;
    private final boolean repeat = false;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private Duration duration;
    private Slider timeSlider;
    private Label playTime;
    private Slider volumeSlider;
    private Button back_button;
    private HBox mediaBar;
    
    public void setHomePage(HomePage hp) {
    	this.hp = hp;
    }
    
    public void setLanguage(String language) {
    	this.language = language;
    }
    
    public Button getBack_button() {
    	return back_button;
    }
 
    public MediaControl(final MediaPlayer mp) {
        this.mp = mp;
        setStyle("-fx-background-color: #bfc2c7;");
        mediaView = new MediaView(mp);
        Pane mvPane = new Pane() {                };
        mvPane.getChildren().add(mediaView);
        mvPane.setStyle("-fx-background-color: black;"); 
        setCenter(mvPane);
        mediaBar = new HBox();
        mediaBar.setAlignment(Pos.CENTER);
        mediaBar.setPadding(new Insets(5, 10, 5, 10));
        BorderPane.setAlignment(mediaBar, Pos.CENTER);
        
        //Add Back button
        back_button = new Button();
        back_button.setPrefWidth(50);
        back_button.setMinWidth(50);
        back_button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) { 
            	try {
					backToChooseMovie();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
        });
        back_button.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
					try {
						backToChooseMovie();
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
            }
        });
        mediaBar.getChildren().add(back_button);
 
        final Button playButton  = new Button(">");
        playButton.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                	Status status = mp.getStatus();
                    
                    if (status == Status.UNKNOWN  || status == Status.HALTED)
                    {
                       // don't do anything in these states
                       return;
                    }
             
                      if ( status == Status.PAUSED
                         || status == Status.READY
                         || status == Status.STOPPED)
                      {
                         // rewind the movie if we're sitting at the end
                         if (atEndOfMedia) {
                            mp.seek(mp.getStartTime());
                            atEndOfMedia = false;
                         }
                         mp.play();
                         } else {
                           mp.pause();
                         }
                }
            }
        });
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Status status = mp.getStatus();
         
                if (status == Status.UNKNOWN  || status == Status.HALTED)
                {
                   // don't do anything in these states
                   return;
                }
         
                  if ( status == Status.PAUSED
                     || status == Status.READY
                     || status == Status.STOPPED)
                  {
                     // rewind the movie if we're sitting at the end
                     if (atEndOfMedia) {
                        mp.seek(mp.getStartTime());
                        atEndOfMedia = false;
                     }
                     mp.play();
                     } else {
                       mp.pause();
                     }
                 }
           });
        mp.currentTimeProperty().addListener(new InvalidationListener() 
        {
            public void invalidated(Observable ov) {
                updateValues();
            }
        });
 
        mp.setOnPlaying(new Runnable() {
            public void run() {
                if (stopRequested) {
                    mp.pause();
                    stopRequested = false;
                } else {
                    playButton.setText("||");
                }
            }
        });
 
        mp.setOnPaused(new Runnable() {
            public void run() {
                playButton.setText(">");
            }
        });
 
        mp.setOnReady(new Runnable() {
            public void run() {
                duration = mp.getMedia().getDuration();
                updateValues();
            }
        });
 
        mp.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
        mp.setOnEndOfMedia(new Runnable() {
            public void run() {
                if (!repeat) {
                    playButton.setText(">");
                    stopRequested = true;
                    atEndOfMedia = true;
                }
            }
       });
        mediaBar.getChildren().add(playButton);
     // Add spacer
        Label spacer = new Label("   ");
        mediaBar.getChildren().add(spacer);
         
        // Add Time label
        Label timeLabel = new Label("Time: ");
        mediaBar.getChildren().add(timeLabel);
         
        // Add time slider
        timeSlider = new Slider();
        HBox.setHgrow(timeSlider,Priority.ALWAYS);
        timeSlider.setMinWidth(50);
        timeSlider.setMaxWidth(Double.MAX_VALUE);
        timeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
               if (timeSlider.isValueChanging()) {
               // multiply duration by percentage calculated by slider position
                  mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
               }
            }
        });
        mediaBar.getChildren().add(timeSlider);
        
        // Add Play label
        playTime = new Label();
        playTime.setPrefWidth(130);
        playTime.setMinWidth(50);
        mediaBar.getChildren().add(playTime);
         
        // Add the volume label
        Label volumeLabel = new Label("Vol: ");
        mediaBar.getChildren().add(volumeLabel);
         
        // Add Volume slider
        volumeSlider = new Slider();        
        volumeSlider.setPrefWidth(70);
        volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
        volumeSlider.setMinWidth(30);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
               if (volumeSlider.isValueChanging()) {
                   mp.setVolume(volumeSlider.getValue() / 100.0);
               }
            }
        });
        mediaBar.getChildren().add(volumeSlider);
        setBottom(mediaBar); 
     }
    
    public void backToChooseMovie() throws Exception {
    	InputStream in = new BufferedInputStream(new FileInputStream("test.properties")); 
        Properties p = new Properties(); 
        p.load(in);
		mediaView.getMediaPlayer().stop();
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
        hp.getRootLayout().setCenter(moviePane);
        hp.getRootLayoutController().getChineseVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadMovieChineseVersion();
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
        hp.getRootLayoutController().getEnglishVersion().setOnAction((ActionEvent t) -> {
            try {
				reloadMovieEnglishVersion();
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
	}
    
    private void reloadMovieEnglishVersion() throws Exception {
    	InputStream in = new BufferedInputStream(new FileInputStream("test.properties")); 
        Properties p = new Properties(); 
        p.load(in);
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

	private void reloadMovieChineseVersion() throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream("test.properties")); 
        Properties p = new Properties(); 
        p.load(in);
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


protected void updateValues() {
	  if (playTime != null && timeSlider != null && volumeSlider != null) {
	     Platform.runLater(new Runnable() {
	        public void run() {
	          Duration currentTime = mp.getCurrentTime();
	          playTime.setText(formatTime(currentTime, duration));
	          timeSlider.setDisable(duration.isUnknown());
	          if (!timeSlider.isDisabled() 
	            && duration.greaterThan(Duration.ZERO) 
	            && !timeSlider.isValueChanging()) {
	              timeSlider.setValue(currentTime.divide(duration).toMillis()
	                  * 100.0);
	          }
	          if (!volumeSlider.isValueChanging()) {
	            volumeSlider.setValue((int)Math.round(mp.getVolume() 
	                  * 100));
	          }
	        }
	     });
	  }
	}

private static String formatTime(Duration elapsed, Duration duration) {
	   int intElapsed = (int)Math.floor(elapsed.toSeconds());
	   int elapsedHours = intElapsed / (60 * 60);
	   if (elapsedHours > 0) {
	       intElapsed -= elapsedHours * 60 * 60;
	   }
	   int elapsedMinutes = intElapsed / 60;
	   int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 
	                           - elapsedMinutes * 60;
	 
	   if (duration.greaterThan(Duration.ZERO)) {
	      int intDuration = (int)Math.floor(duration.toSeconds());
	      int durationHours = intDuration / (60 * 60);
	      if (durationHours > 0) {
	         intDuration -= durationHours * 60 * 60;
	      }
	      int durationMinutes = intDuration / 60;
	      int durationSeconds = intDuration - durationHours * 60 * 60 - 
	          durationMinutes * 60;
	      if (durationHours > 0) {
	         return String.format("%d:%02d:%02d/%d:%02d:%02d", 
	            elapsedHours, elapsedMinutes, elapsedSeconds,
	            durationHours, durationMinutes, durationSeconds);
	      } else {
	          return String.format("%02d:%02d/%02d:%02d",
	            elapsedMinutes, elapsedSeconds,durationMinutes, 
	                durationSeconds);
	      }
	      } else {
	          if (elapsedHours > 0) {
	             return String.format("%d:%02d:%02d", elapsedHours, 
	                    elapsedMinutes, elapsedSeconds);
	            } else {
	                return String.format("%02d:%02d",elapsedMinutes, 
	                    elapsedSeconds);
	            }
	        }
	    }}