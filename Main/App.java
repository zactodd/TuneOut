// 
// Decompiled by Procyon v0.5.36
// 

package seng302;

import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCombination;
import javafx.event.Event;
import javafx.application.Platform;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import Controller.OpenAndSave.FileInformation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import Model.Play.SynthesizerInitialization;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import Controller.OuterTemplateController;
import javax.sound.midi.Synthesizer;
import org.apache.log4j.Logger;
import javafx.stage.Stage;
import javafx.application.Application;

public class App extends Application
{
    private static Stage primaryStage;
    public static Logger log;
    public static Synthesizer synth;
    private final int minWidth = 1000;
    private final int minHeight = 700;
    private static OuterTemplateController outerTemplate;
    @FXML
    private BorderPane mainBorderPane;
    
    public static void main(final String[] args) {
        App.log.debug("Application log has been initialised");
        launch(new String[0]);
    }
    
    public void start(final Stage primaryStage) throws IOException, NoSuchAlgorithmException {
        App.synth = SynthesizerInitialization.initialization();
        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource("/View/outerTemplate.fxml"));
        this.mainBorderPane = (BorderPane)fxmlLoader.load();
        App.outerTemplate = (OuterTemplateController)fxmlLoader.getController();
        App.primaryStage = primaryStage;
        final Scene scene = new Scene((Parent)this.mainBorderPane);
        (App.primaryStage = primaryStage).setScene(scene);
        primaryStage.show();
        primaryStage.setMinWidth(1000.0);
        primaryStage.setMinHeight(700.0);
        FileInformation.stage = primaryStage;
        App.outerTemplate.initializeController();
        App.outerTemplate.loadPersistentDataFromFile();
        App.outerTemplate.updateAppTitle();
        this.terminateStageOnClose(primaryStage);
        App.outerTemplate.focusCommandLine();
    }
    
    private void terminateStageOnClose(final Stage stage) {
        stage.setOnCloseRequest((EventHandler)new EventHandler<WindowEvent>() {
            public void handle(final WindowEvent t) {
                if (App.outerTemplate.getProject().checkPromptUnsavedChanges()) {
                    App.outerTemplate.savePersistentDataToFile();
                    App.outerTemplate.quit();
                    Platform.exit();
                    System.exit(0);
                }
                else {
                    t.consume();
                }
            }
        });
    }
    
    public static void setStyle(final String stylePath) {
        App.primaryStage.getScene().getStylesheets().clear();
        App.primaryStage.getScene().getStylesheets().add((Object)stylePath);
    }
    
    public static void setShortcut(final KeyCombination keyCombination, final Button button) {
        App.primaryStage.getScene().addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (keyCombination.match(event)) {
                button.fire();
            }
        });
    }
    
    public static OuterTemplateController getOuterTemplate() {
        return App.outerTemplate;
    }
    
    static {
        App.log = Logger.getLogger(App.class.getName());
    }
}
