/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package myvedioplayer;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

/**
 *
 * @author 91945
 */
public class FXMLDocumentController implements Initializable {
    private String path;
    private MediaPlayer m1;
    
    @FXML
    private MediaView mediaView;
    
    @FXML
    private Slider progressBar;
    
    @FXML 
    private Slider volumeSlider;
    
   public void ChooseFileMethod(ActionEvent event){
       FileChooser fc=new FileChooser();
       File file=fc.showOpenDialog(null);
       path=file.toURI().toString();
       if(path!=null){
           Media m=new Media(path);
           m1=new MediaPlayer(m);
           mediaView.setMediaPlayer(m1);
           
           DoubleProperty widthProp=mediaView.fitWidthProperty();
           DoubleProperty hightProp=mediaView.fitHeightProperty();
           
           widthProp.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
           hightProp.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));
           
           m1.currentTimeProperty().addListener(new ChangeListener<javafx.util.Duration>(){
               
               @Override
               public void changed(ObservableValue<? extends javafx.util.Duration> observable, javafx.util.Duration oldValue, javafx.util.Duration newValue) {
                   progressBar.setValue(newValue.toSeconds());
                   
               }
           });
           
           progressBar.setOnMousePressed(new EventHandler<MouseEvent>(){
               @Override
               public void handle(MouseEvent event) {
                  m1.seek(javafx.util.Duration.seconds(progressBar.getValue()));
               }
           
       });
           
           progressBar.setOnMouseDragged(new EventHandler<MouseEvent>(){
               @Override
               public void handle(MouseEvent event) {
                  m1.seek(javafx.util.Duration.seconds(progressBar.getValue()));
               }
       });
           
           m1.setOnReady(new Runnable() {
               @Override
               public void run() {
                   javafx.util.Duration total=m.getDuration();
                   progressBar.setMax(total.toSeconds());
               }
           });
           
           volumeSlider.setValue(m1.getVolume()*100);
           
           volumeSlider.valueProperty().addListener(new InvalidationListener(){
               @Override
               public void invalidated(Observable observable) {
                   m1.setVolume(volumeSlider.getValue()/100);
                   
               }
               
           });
           
           m1.play();
           
       }
       
   }
   
   public void play(ActionEvent event){
       m1.play();
       m1.setRate(1);
   }
   
   public void pause(ActionEvent event){
       m1.pause();
   }
   
   public void stop(ActionEvent event){
       m1.stop();
   }
   
   public void slowRate(ActionEvent event){
       m1.setRate(0.5);
   }
   
   public void fastForward(ActionEvent event){
       m1.setRate(2);
   }
   
   public void skip10(ActionEvent event){
       m1.seek(m1.getCurrentTime().add(javafx.util.Duration.seconds(10)));  
   }
   
   public void back10(ActionEvent event){
       m1.seek(m1.getCurrentTime().add(javafx.util.Duration.seconds(-10)));
       
   }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
