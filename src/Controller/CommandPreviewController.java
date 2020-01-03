

package Controller;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import Model.Note.Settings.TempoInformation;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import java.util.concurrent.ThreadPoolExecutor;
import javafx.concurrent.Task;
import javafx.collections.ObservableList;
import java.util.Collection;
import javafx.collections.FXCollections;
import java.util.Arrays;

import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import Model.Settings.StyleMap;
import javafx.scene.control.SelectionMode;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import javafx.stage.Stage;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class CommandPreviewController
{
    @FXML
    private ListView<String> commandTable;
    @FXML
    private Label warning;
    @FXML
    private Label playing;
    @FXML
    private ProgressBar progress;
    @FXML
    private Button runSelected;
    @FXML
    private Button runAll;
    @FXML
    private Button stop;
    private OuterTemplateController main;
    private int currentCommandPos;
    private List<String> commandList;
    private CommandLineController commandLine;
    public String commands;
    final int MAX_TIME_IN_LOOP = 240;
    Thread sleeperThread;
    private Stage stage;
    static Logger log;
    
    public CommandPreviewController() {
        this.currentCommandPos = 0;
        this.commandList = new ArrayList<String>();
        this.commands = "";
    }
    
    public void initializeController(final CommandLineController commandLine) throws IOException {
        this.commandLine = commandLine;
    }
    
    public void start(final Stage root, final String commands) {
        (this.stage = root).initModality(Modality.NONE);
        try {
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/View/commandPreview.fxml"));
            loader.setController((Object)this);
            final GridPane page = (GridPane)loader.load();
            final Scene scene = new Scene((Parent)page, 360.0, 300.0);
            this.stage.setScene(scene);
            this.stage.setMinWidth(360.0);
            this.stage.setMinHeight(300.0);
            this.setWarning(commands);
            this.commandList = this.extractCommands(commands);
            this.commandTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            this.loadCommandList();
            this.stage.show();
            this.stage.getScene().getStylesheets().clear();
            this.stage.getScene().getStylesheets().add((Object)StyleMap.getCurrentStyle());
            this.stage.setOnCloseRequest((EventHandler)new EventHandler<WindowEvent>() {
                public void handle(final WindowEvent we) {
                    CommandPreviewController.this.commandLine.mainEnvironment.getPlay().stopAllPlayingThreads();
                }
            });
        }
        catch (Exception e) {
            CommandPreviewController.log.error(e.toString());
        }
    }
    
    private List<String> extractCommands(final String commands) {
        final String commands_only = commands.replace("/* TuneOut Command File */", "").trim();
        return Arrays.asList(commands_only.split("\\r\\n|\\n|\\r"));
    }
    
    private void loadCommandList() {
        final ObservableList<String> commandItems = (ObservableList<String>)FXCollections.observableArrayList((Collection)this.commandList);
        this.commandTable.setItems((ObservableList)commandItems);
        this.commandTable.getSelectionModel().selectFirst();
    }
    
    @FXML
    private void runTo() throws IOException {
        final List<String> playCommands = new ArrayList<String>();
        final List<Integer> selectedCommands = (List<Integer>)this.commandTable.getSelectionModel().getSelectedIndices();
        for (final Integer i : selectedCommands) {
            this.commandLine.runCommand(this.commandList.get(i));
            if (this.commandList.get(i).startsWith("play")) {
                playCommands.add(this.commandList.get(i));
            }
        }
        if (playCommands.size() != 0) {
            this.runPlayCommands(playCommands);
        }
        else {
            this.playing.setText("");
            this.progress.setProgress(0.0);
        }
    }
    
    @FXML
    private void runAll() throws IOException {
        final List<String> playCommands = new ArrayList<String>();
        for (final String command : this.commandList) {
            this.commandLine.runCommand(command);
            if (command.startsWith("play")) {
                playCommands.add(command);
            }
        }
        if (playCommands.size() != 0) {
            this.runPlayCommands(playCommands);
        }
        else {
            this.playing.setText("");
            this.progress.setProgress(0.0);
        }
    }
    
    private void runPlayCommands(final List<String> playCommands) {
        final Task<Void> sleeper = new Task<Void>() {
            protected Void call() throws Exception {
                String currentCommand = "";
                final int maxProgress = playCommands.size();
                long completedTaskChecker = -1L;
                try {
                    final ThreadPoolExecutor playThreads = CommandPreviewController.this.commandLine.mainEnvironment.getPlay().getFixedPool();
                    if (!playThreads.isTerminated() && !playThreads.isTerminating()) {
                        int ticker = 0;
                        int incrementer = 0;
                        int commandNumber = -1;
                        while (playThreads.getActiveCount() > 0 && ticker < 240) {
                            if (!playCommands.isEmpty()) {
                                currentCommand = playCommands.get(0);
                            }
                            Thread.sleep(250L);
                            this.updateProgress(CommandPreviewController.this.calculateCurrentProgress(maxProgress, commandNumber, incrementer, currentCommand), (double)maxProgress);
                            if (playThreads.getCompletedTaskCount() != completedTaskChecker) {
                                ++commandNumber;
                                this.updateProgress((long)commandNumber, (long)maxProgress);
                                if (playCommands.size() != 0) {
                                    this.updateMessage((String)playCommands.get(0));
                                    playCommands.remove(0);
                                }
                                completedTaskChecker = playThreads.getCompletedTaskCount();
                                incrementer = 0;
                            }
                            ++ticker;
                            ++incrementer;
                        }
                    }
                }
                catch (Exception e) {
                    CommandPreviewController.log.error(e.toString());
                }
                return null;
            }
        };
        sleeper.setOnSucceeded((EventHandler)new EventHandler<WorkerStateEvent>() {
            public void handle(final WorkerStateEvent event) {
                CommandPreviewController.this.playing.textProperty().unbind();
                CommandPreviewController.this.progress.progressProperty().unbind();
                CommandPreviewController.this.runSelected.setDisable(false);
                CommandPreviewController.this.runAll.setDisable(false);
                CommandPreviewController.this.stop.setDisable(true);
            }
        });
        sleeper.setOnFailed((EventHandler)new EventHandler<WorkerStateEvent>() {
            public void handle(final WorkerStateEvent event) {
                CommandPreviewController.this.playing.textProperty().unbind();
                CommandPreviewController.this.progress.progressProperty().unbind();
                CommandPreviewController.this.playing.setText("Error, see log");
                CommandPreviewController.this.runSelected.setDisable(false);
                CommandPreviewController.this.runAll.setDisable(false);
                CommandPreviewController.this.stop.setDisable(true);
            }
        });
        this.playing.textProperty().bind((ObservableValue)sleeper.messageProperty());
        this.progress.progressProperty().bind((ObservableValue)sleeper.progressProperty());
        this.runSelected.setDisable(true);
        this.runAll.setDisable(true);
        this.stop.setDisable(false);
        new Thread((Runnable)sleeper).start();
        (this.sleeperThread = new Thread((Runnable)sleeper)).start();
    }
    
    private double calculateCurrentProgress(final int max, final int currentPosition, final int betweenNumCounter, final String command) {
        Double incrementValue;
        if (command.startsWith("playScale")) {
            incrementValue = 0.075;
        }
        else if (command.startsWith("playInterval")) {
            incrementValue = 0.1;
        }
        else {
            incrementValue = 0.2;
        }
        final int tempo = TempoInformation.getTempInBpm();
        if (tempo > 200) {
            incrementValue *= 2.0;
        }
        else if (tempo < 80) {
            incrementValue /= 2.0;
        }
        if (max > 25) {
            incrementValue /= 3.0;
        }
        else if (max > 15) {
            incrementValue /= 2.0;
        }
        if (betweenNumCounter * incrementValue < 1.0) {
            return currentPosition + betweenNumCounter * incrementValue;
        }
        return currentPosition + 1;
    }
    
    private boolean is30orMore(final String commands) {
        final String[] initcommands_array = commands.split("\\r\\n|\\n|\\r");
        final int initsize = initcommands_array.length - 1;
        final String[] commands_array = new String[initsize];
        System.arraycopy(initcommands_array, 1, commands_array, 0, initsize);
        final int size = commands_array.length;
        return size >= 30;
    }
    
    @FXML
    private void setWarning(final String commands) {
        if (this.is30orMore(commands)) {
            this.warning.setText("There are 30 or more commands in this file! ");
        }
    }
    
    @FXML
    private void stop() throws IOException {
        this.commandLine.mainEnvironment.getPlay().stopAllPlayingThreads();
    }
    
    @FXML
    private void cancel() throws IOException {
        this.commandLine.mainEnvironment.getPlay().stopAllPlayingThreads();
        final Stage stage = (Stage)this.commandTable.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void onMouseClick(final MouseEvent mouseClick) {
        if (mouseClick.getButton().equals((Object)MouseButton.PRIMARY) && !this.runSelected.isDisabled() && mouseClick.getClickCount() == 2) {
            try {
                this.runTo();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    private void onKeyPressed(final KeyEvent keyPressed) {
        if (keyPressed.getCode().toString().equals("ENTER") && !this.runSelected.isDisabled()) {
            try {
                this.runTo();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void updateStageStyle() {
        this.stage.getScene().getStylesheets().clear();
        this.stage.getScene().getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
    }
    
    static {
        CommandPreviewController.log = Logger.getLogger(CommandPreviewController.class.getName());
    }
}
