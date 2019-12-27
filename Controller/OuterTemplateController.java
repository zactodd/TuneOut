

package Controller;

import Model.Note.Melody.MelodyMap;
import javafx.scene.control.TextInputDialog;
import Model.File.TuneOutMusicXmlFile;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckMenuItem;
import java.util.TreeMap;
import Model.File.TuneOutProjectFile;
import java.security.NoSuchAlgorithmException;
import Model.Project.User;
import Model.File.TuneOutPersistentFile;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.scene.Parent;
import Model.Tutor.RecordedTutorStats;
import javafx.scene.Scene;
import Model.Settings.StyleMap;
import javafx.scene.control.Alert;
import Model.Project.PersistenceData;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.layout.GridPane;
import java.util.Iterator;
import Model.File.TuneOutCommandFile;
import Model.File.TuneOutTranscriptFile;
import Controller.OpenAndSave.FileInformation;
import Model.File.TuneOutGeneralFile;
import java.io.File;
import java.awt.Desktop;
import javafx.application.Platform;
import java.io.IOException;
import Model.keyboardInput.ComposerInput;
import Model.keyboardInput.NoteInputField;
import Model.keyboardInput.KeyboardInput;
import Model.keyboardInput.LearningInput;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import Model.Play.Play;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import Model.CommandMessages;
import org.apache.log4j.Logger;
import Controller.DrumPad.DrumPadPaneController;
import javafx.stage.Stage;
import Controller.Keyboard.InstrumentPaneController;
import java.util.Map;
import java.util.List;
import Model.Project.Project;
import Environment.Environment;
import Controller.LearningCompose.OuterLearningController;
import Controller.LearningCompose.OuterComposePercussionController;
import Controller.LearningCompose.OuterComposeController;
import Model.Tutor.TutorDefinition;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

public class OuterTemplateController
{
    @FXML
    public Tab exploreTab;
    @FXML
    public BorderPane exploreTabBorderPane;
    @FXML
    public Tab composeTab;
    @FXML
    public BorderPane composeTabBorderPane;
    @FXML
    public BorderPane mainBorderPane;
    @FXML
    public Tab composePercussionTab;
    @FXML
    public BorderPane composePercussionTabBorderPane;
    @FXML
    protected TabPane mainTabPane;
    @FXML
    private BorderPane mainTabBorderPane;
    @FXML
    private MenuButton userButton;
    @FXML
    private Menu userDeleteMenu;
    @FXML
    private MenuItem logged;
    @FXML
    private Tab mainTab;
    @FXML
    public Button keyboardInputButton;
    TutorDefinition tutorDefn;
    private TranscriptController transcript;
    private CommandLineController commandLine;
    private TutorController tutorController;
    protected OuterComposeController compose;
    protected OuterComposePercussionController percussionController;
    OuterLearningController learning;
    public UserManager userManager;
    private Environment tutorEnvironment;
    protected Project project;
    private static final String TITLE_ENDING = " - TuneOut";
    private static final String UNSAVED_INDICATOR = "*";
    private static final Integer NUM_FIXED_TABS;
    private final String defaultUser = "Default User";
    private final String cannotDeleteDefaultUser;
    private final String cannotDeleteSelectedUser;
    private final List<String> mandatoryTabs;
    private final String commandFileHeader = "/* TuneOut Command File */";
    private final String transcriptFileHeader = "TuneOut Transcript";
    private final String transcriptTab = "Commands and Transcript";
    private Map<String, TutorDefinition> tutors;
    private static InstrumentPaneController keyboard;
    private CommandPreviewController previewController;
    private SettingsController settings;
    protected UserStatsController userStatsController;
    private static Stage viewKeyboard;
    private Stage refCardStage;
    private Stage settingStage;
    private static DrumPadPaneController drumPad;
    private static Stage viewDrumPad;
    private Logger log;
    
    public OuterTemplateController() {
        this.cannotDeleteDefaultUser = CommandMessages.getMessage("CANNOT_DELETE_DEFAULT_USER");
        this.cannotDeleteSelectedUser = CommandMessages.getMessage("CANNOT_DELETE_SELECTED_USER");
        this.mandatoryTabs = new ArrayList<String>(Arrays.asList("Learning", "Compose Melody", "Compose Percussion"));
        this.tutors = new HashMap<String, TutorDefinition>();
        this.refCardStage = null;
        this.settingStage = null;
        this.log = Logger.getLogger(OuterTemplateController.class.getName());
    }
    
    public void initializeController() throws IOException {
        final FXMLLoader commandLineLoader = new FXMLLoader();
        commandLineLoader.setLocation(this.getClass().getResource("/View/commandLine.fxml"));
        final AnchorPane commandLineAnchorPane = (AnchorPane)commandLineLoader.load();
        this.commandLine = (CommandLineController)commandLineLoader.getController();
        this.mainTabBorderPane.setBottom((Node)commandLineAnchorPane);
        final FXMLLoader transcriptLoader = new FXMLLoader();
        transcriptLoader.setLocation(this.getClass().getResource("/View/transcript.fxml"));
        final AnchorPane transcriptAnchorPane = (AnchorPane)transcriptLoader.load();
        this.transcript = (TranscriptController)transcriptLoader.getController();
        this.mainTabBorderPane.setCenter((Node)transcriptAnchorPane);
        final Environment commandLineEnvironment = new Environment(new Play(Play.PlayType.QUEUED));
        commandLineEnvironment.setOuterTemplateController(this);
        final FXMLLoader learningLoader = new FXMLLoader();
        learningLoader.setLocation(this.getClass().getResource("/View/LearningCompose/outerLearning.fxml"));
        final AnchorPane learningAnchorPane = (AnchorPane)learningLoader.load();
        this.exploreTabBorderPane.setCenter((Node)learningAnchorPane);
        this.learning = (OuterLearningController)learningLoader.getController();
        final FXMLLoader composeLoader = new FXMLLoader();
        composeLoader.setLocation(this.getClass().getResource("/View/LearningCompose/outerCompose.fxml"));
        final AnchorPane composeAnchorPane = (AnchorPane)composeLoader.load();
        this.composeTabBorderPane.setCenter((Node)composeAnchorPane);
        (this.compose = (OuterComposeController)composeLoader.getController()).setOuterTemplateController(this);
        final FXMLLoader composePercussionLoader = new FXMLLoader();
        composePercussionLoader.setLocation(this.getClass().getResource("/View/LearningCompose/outerComposePercussion.fxml"));
        final AnchorPane composePercussionAnchorPane = (AnchorPane)composePercussionLoader.load();
        this.composePercussionTabBorderPane.setCenter((Node)composePercussionAnchorPane);
        (this.percussionController = (OuterComposePercussionController)composePercussionLoader.getController()).setOuterTemplateController(this);
        this.transcript.firstLineTranscript();
        this.commandLine.setTranscript(this, this.transcript);
        this.commandLine.initializeController(commandLineEnvironment);
        this.transcript.setCommandHistory(this.commandLine.getCommandHistory());
        this.project = new Project();
        this.userManager = new UserManager();
        this.mainBorderPane.getScene().addEventHandler(KeyEvent.KEY_RELEASED, (EventHandler)new EventHandler<KeyEvent>() {
            KeyCodeCombination keyCombination = new KeyCodeCombination(KeyCode.L, KeyCombination.ModifierValue.UP, KeyCombination.ModifierValue.DOWN, KeyCombination.ModifierValue.UP, KeyCombination.ModifierValue.UP, KeyCombination.ModifierValue.UP);
            
            public void handle(final KeyEvent event) {
                if (this.keyCombination.match(event)) {
                    OuterTemplateController.this.commandLineCommandHistorySearch();
                }
            }
        });
        if (this.checkTabExists("Commands and Transcript")) {
            this.mainTabPane.getTabs().remove((Object)this.mainTab);
        }
        this.mainTabPane.getSelectionModel().selectedItemProperty().addListener((ChangeListener)new ChangeListener<Tab>() {
            public void changed(final ObservableValue<? extends Tab> observable, final Tab oldTab, final Tab newTab) {
                if (newTab == OuterTemplateController.this.exploreTab) {
                    final LearningInput learningInput = new LearningInput(OuterTemplateController.this.learning);
                    KeyboardInput.setNoteInputField(learningInput);
                }
                else if (newTab == OuterTemplateController.this.composeTab) {
                    final ComposerInput composerInput = new ComposerInput(OuterTemplateController.this.compose);
                    KeyboardInput.setNoteInputField(composerInput);
                }
                else {
                    KeyboardInput.setNoteInputField(null);
                }
            }
        });
    }
    
    public void reopenMainTab() {
        if (!this.checkTabExists("Commands and Transcript")) {
            this.mainTab.setClosable(true);
            this.mainTabPane.getTabs().add((Object)this.mainTab);
        }
        this.mainTabPane.getSelectionModel().select((Object)this.mainTab);
        this.focusCommandLine();
    }
    
    @FXML
    public void quit() {
        if (this.project.checkPromptUnsavedChanges()) {
            if (this.settingStage != null) {
                this.settingStage.close();
            }
            Platform.exit();
            System.exit(0);
        }
    }
    
    @FXML
    public void openDslReferenceCard() {
        if (this.refCardStage == null) {
            final DSLReferenceCardController refCard = new DSLReferenceCardController();
            (this.refCardStage = new Stage()).setTitle("DSL Reference Card");
            refCard.start(this.refCardStage, this.commandLine);
        }
        else if (!this.refCardStage.isShowing()) {
            this.refCardStage.show();
        }
    }
    
    @FXML
    public void help() {
        if (Desktop.isDesktopSupported()) {
            final Desktop dt;
            final ClassLoader classLoader;
            File doc;
            String absPath;
            String absPath2;
            String absPath3;
            final File file;
            File doc2;
            new Thread(() -> {
                dt = Desktop.getDesktop();
                classLoader = this.getClass().getClassLoader();
                try {
                    doc = new File(classLoader.getResource("Help/commands.txt").getFile());
                    dt.open(doc);
                }
                catch (IOException e) {
                    this.log.error(e.toString());
                }
                catch (IllegalArgumentException e3) {
                    absPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
                    absPath2 = absPath.substring(0, absPath.lastIndexOf("/"));
                    absPath3 = absPath2.replaceAll("%20", " ");
                    new File(absPath3 + "/Help/commands.txt");
                    doc2 = file;
                    try {
                        dt.open(doc2);
                    }
                    catch (Exception e2) {
                        this.log.error(e2.toString());
                    }
                }
            }).start();
        }
        else {
            this.log.error("Desktop not supported");
            this.showError("Cannot open commands.txt, see application.log file for details");
        }
    }
    
    @FXML
    public void addToTranscript(final String text) {
        this.transcript.appendToTranscript(text);
    }
    
    @FXML
    public void openTranscript(final TuneOutGeneralFile file) {
        final String fileText = file.fileToText(file.file);
        if (file.isValid(fileText)) {
            this.transcript.setTextTranscript("");
            this.transcript.setTextTranscript(fileText);
        }
        else if (file.file != null) {
            FileInformation.showNotValidError();
        }
    }
    
    @FXML
    public void saveTranscript() {
        final TuneOutTranscriptFile transcriptFile = new TuneOutTranscriptFile(FileInformation.saveFile("Save Transcript"));
        final String fileText = this.transcript.getText();
        if (transcriptFile.file != null) {
            transcriptFile.textToFile(fileText, transcriptFile.file);
        }
    }
    
    @FXML
    public void openTranscriptCommands(final TuneOutGeneralFile tuneOutFile) throws IOException {
        final String commands = tuneOutFile.fileToText(tuneOutFile.file);
        (this.previewController = new CommandPreviewController()).initializeController(this.commandLine);
        final Stage previewCommands = new Stage();
        previewCommands.setTitle("Open Command File");
        if (tuneOutFile.isValid(commands)) {
            this.previewController.start(previewCommands, commands);
        }
        else if (tuneOutFile.file != null) {
            FileInformation.showNotValidError();
        }
    }
    
    @FXML
    public void saveTranscriptCommands() {
        final TuneOutCommandFile commandFile = new TuneOutCommandFile(FileInformation.saveFile("Save Command File"));
        final ArrayList<String> fileCommands = this.transcript.getCommands();
        if (commandFile.file != null) {
            commandFile.textToFile(commandFile.commandtoText(fileCommands), commandFile.file);
        }
    }
    
    @FXML
    public void saveProject() {
        this.project.save(false);
        this.updateAppTitle();
    }
    
    @FXML
    public void saveProjectAs() {
        this.project.save(true);
        this.updateAppTitle();
    }
    
    @FXML
    public void openProject() throws IOException {
        final TuneOutGeneralFile tuneOutFile = new TuneOutGeneralFile(FileInformation.openFile("Open"));
        final String fileText = tuneOutFile.fileToText(tuneOutFile.file);
        if (fileText.startsWith("TuneOut Transcript")) {
            this.openTranscript(tuneOutFile);
        }
        else if (fileText.startsWith("/* TuneOut Command File */")) {
            this.openTranscriptCommands(tuneOutFile);
        }
        else {
            this.project.setTempProjectFile(tuneOutFile);
            this.project.open();
            this.updateAppTitle();
        }
    }
    
    private void removeAddedTabs() {
        if (this.mainTabPane != null && this.mainTabPane.getTabs().size() > OuterTemplateController.NUM_FIXED_TABS) {
            final Iterator<Tab> tabs = (Iterator<Tab>)this.mainTabPane.getTabs().iterator();
            while (tabs.hasNext()) {
                final Tab tab = tabs.next();
                if (!this.mandatoryTabs.contains(tab.getText())) {
                    tabs.remove();
                }
            }
        }
    }
    
    public void newProject() {
        if (this.project.checkPromptUnsavedChanges()) {
            this.project.initialState();
            this.transcript.firstLineTranscript();
            this.removeAddedTabs();
            this.commandLine.mainEnvironment.getPlay().stopAllPlayingThreads();
            for (final Map.Entry<String, TutorDefinition> entry : this.tutors.entrySet()) {
                entry.getValue().tutor.getPlay().stopAllPlayingThreads();
            }
        }
        this.updateAppTitle();
    }
    
    @FXML
    public void keyboard() throws IOException {
        if (OuterTemplateController.viewKeyboard == null) {
            final FXMLLoader instrumentPaneLoader = new FXMLLoader();
            instrumentPaneLoader.setLocation(this.getClass().getResource("/View/Keyboard/instrumentPane.fxml"));
            final GridPane instrumentPane = (GridPane)instrumentPaneLoader.load();
            OuterTemplateController.keyboard = (InstrumentPaneController)instrumentPaneLoader.getController();
            OuterTemplateController.viewKeyboard = new Stage();
            OuterTemplateController.keyboard.start(OuterTemplateController.viewKeyboard, this.commandLine);
            this.updateAppTitle();
        }
        else if (!OuterTemplateController.viewKeyboard.isShowing()) {
            OuterTemplateController.viewKeyboard.show();
            if (KeyboardInput.isInputAccepted()) {
                OuterTemplateController.keyboard.turnOnInput();
            }
            else {
                OuterTemplateController.keyboard.turnOffInput();
            }
        }
        else if (OuterTemplateController.viewKeyboard.isShowing()) {
            if (KeyboardInput.isInputAccepted()) {
                OuterTemplateController.viewKeyboard.requestFocus();
                OuterTemplateController.keyboard.turnOnInput();
            }
            else {
                OuterTemplateController.keyboard.turnOffInput();
            }
        }
    }
    
    @FXML
    public void drumPad() throws IOException {
        if (OuterTemplateController.viewDrumPad == null) {
            final FXMLLoader drumPadPaneLoader = new FXMLLoader();
            drumPadPaneLoader.setLocation(this.getClass().getResource("/View/DrumPad/drumPadPane.fxml"));
            final AnchorPane drumPadPane = (AnchorPane)drumPadPaneLoader.load();
            OuterTemplateController.drumPad = (DrumPadPaneController)drumPadPaneLoader.getController();
            OuterTemplateController.viewDrumPad = new Stage();
            OuterTemplateController.drumPad.start(OuterTemplateController.viewDrumPad);
            this.updateAppTitle();
        }
        else if (!OuterTemplateController.viewDrumPad.isShowing()) {
            OuterTemplateController.viewDrumPad.show();
        }
    }
    
    private void createTutorView(final Tab tutorTab, final Environment tutorEnvironment) throws IOException {
        this.tutorDefn.tutorController.initialize(this, tutorEnvironment);
        tutorTab.setContent((Node)this.tutorDefn.tutorController.getContent());
    }
    
    public void createTutor(final TutorDefinition tutorDefn) {
        try {
            this.tutorDefn = tutorDefn;
            this.tutorController = tutorDefn.tutorController;
            (this.tutorEnvironment = new Environment(new Play(Play.PlayType.REPLACE), true)).setOuterTemplateController(this);
            this.tutorEnvironment.setTutorMode(true);
            this.tutorEnvironment.setTutor(tutorDefn.tutor);
            this.tutorEnvironment.setTutorDefinition(tutorDefn);
            final Tab tutorTab = this.addTab(tutorDefn.tutorName);
            this.tutors.put(tutorTab.getId(), tutorDefn);
            final SingleSelectionModel<Tab> selectionModel = (SingleSelectionModel<Tab>)this.mainTabPane.getSelectionModel();
            selectionModel.select((Object)tutorTab);
            this.createTutorView(tutorTab, this.tutorEnvironment);
        }
        catch (IOException ioException) {
            this.log.error(ioException.getMessage());
        }
    }
    
    private Tab addTab(final String tabName) throws IOException {
        final Tab tab = new Tab(tabName);
        tab.setId(tabName);
        this.mainTabPane.getTabs().add((Object)tab);
        this.addTutorCloseTabListener(tab);
        return tab;
    }
    
    private void addTutorCloseTabListener(final Tab tab) {
        tab.setOnClosed(event -> {
            this.tutors.get(tab.getId()).tutor.getPlay().stopAllPlayingThreads();
            this.tutors.remove(tab.getId());
        });
    }
    
    private void restoreTabs(final PersistenceData persistData) {
        if (!this.mainTabPane.getTabs().isEmpty()) {
            this.removeAddedTabs();
        }
        for (final String tutor : persistData.getTutors()) {
            this.restoreTab(tutor);
        }
        this.mainTabPane.getSelectionModel().select((Object)this.exploreTab);
    }
    
    private void restoreTab(final String tutor) {
        final Map<String, String> tutorsMap = new HashMap<String, String>();
        tutorsMap.put("Chord Spelling Tutor", "chordSpellingTutor()");
        tutorsMap.put("Chord Tutor", "chordTutor()");
        tutorsMap.put("Chord Function Tutor", "chordFunctionTutor()");
        tutorsMap.put("Interval Tutor", "intervalTutor()");
        tutorsMap.put("Musical Term Tutor", "musicalTermTutor()");
        tutorsMap.put("Pitch Tutor", "pitchTutor()");
        tutorsMap.put("Scale Recognition Tutor", "scaleTutor()");
        tutorsMap.put("Scale Mode Tutor", "scaleModeTutor()");
        tutorsMap.put("Key Signature Tutor", "keySignatureTutor()");
        if (tutorsMap.containsKey(tutor)) {
            this.commandLine.runCommand(tutorsMap.get(tutor));
        }
        else {
            this.log.error("Cannot open this tutor: " + tutor);
        }
    }
    
    void showError(final String errorText) {
        final Alert alert = new Alert(Alert.AlertType.ERROR);
        final Scene scene = alert.getDialogPane().getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(300.0, 200.0);
        alert.setTitle("Error");
        alert.setContentText(errorText);
        alert.showAndWait();
    }
    
    public void selectTab(final String tabName) {
        for (final Tab tab : this.mainTabPane.getTabs()) {
            if (tab.getText().equals(tabName)) {
                final SingleSelectionModel<Tab> selectionModel = (SingleSelectionModel<Tab>)this.mainTabPane.getSelectionModel();
                selectionModel.select((Object)tab);
            }
        }
    }
    
    public boolean checkTabExists(final String nameToCheck) {
        for (final Tab tab : this.mainTabPane.getTabs()) {
            if (tab.getText().equals(nameToCheck)) {
                return true;
            }
        }
        return false;
    }
    
    public Project getProject() {
        return this.project;
    }
    
    public void updateAppTitle() {
        final Stage stage = (Stage)this.mainBorderPane.getScene().getWindow();
        String title = this.project.getProjectName() + " - TuneOut";
        if (this.project.unsavedChanges()) {
            title = "*" + title;
        }
        stage.setTitle(title);
    }
    
    public void commandLineCommandHistorySearch() {
        this.commandLine.commandHistorySearch();
    }
    
    public void focusCommandLine() {
        final Stage stage = (Stage)this.mainBorderPane.getScene().getWindow();
        stage.requestFocus();
        this.commandLine.focus();
        this.commandLine.focusInCommandParam();
    }
    
    protected void resetUserSettings() {
        StyleMap.setCurrentStyle("default");
        this.setStyle();
        if (this.transcript != null) {
            this.transcript.setFontSize(this.transcript.getDefaultFontSize());
        }
        RecordedTutorStats.setSaveStats(false);
    }
    
    protected void updatePopUpStyle() {
        if (this.settingStage != null) {
            this.settingStage.getScene().getStylesheets().clear();
            this.settingStage.getScene().getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
        }
        if (this.refCardStage != null) {
            this.refCardStage.getScene().getStylesheets().clear();
            this.refCardStage.getScene().getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
        }
        if (this.userStatsController != null) {
            this.userStatsController.updateCss();
        }
        if (this.userManager != null) {
            this.userManager.updateStagesStyle();
        }
        if (this.previewController != null) {
            this.previewController.updateStageStyle();
        }
    }
    
    @FXML
    public void loadSettings() throws IOException {
        if (this.settingStage == null) {
            final FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(this.getClass().getResource("/View/userPreferences.fxml"));
            final Parent root = (Parent)fxmlLoader.load();
            (this.settingStage = new Stage()).setTitle("Settings");
            final Scene scene = new Scene(root);
            this.settingStage.initModality(Modality.APPLICATION_MODAL);
            this.settingStage.setScene(scene);
            this.settingStage.getScene().getStylesheets().clear();
            this.settingStage.getScene().getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
            (this.settings = (SettingsController)fxmlLoader.getController()).setTextSizeCombo(this.transcript);
            this.settings.start(this);
            this.settingStage.show();
            this.settingStage.setResizable(false);
        }
        else if (!this.settingStage.isShowing()) {
            this.settings.setUpSettingData();
            this.settings.setTextSizeCombo(this.transcript);
            this.settingStage.show();
        }
    }
    
    public void closeSettingPopUp() {
        this.settingStage.close();
        this.settingStage = null;
    }
    
    private void closePopUp() {
        if (this.userStatsController != null) {
            this.closeUserStats();
            this.userStatsController = null;
            this.userManager.userStatsController = null;
        }
        if (this.settingStage != null) {
            if (this.settingStage.isShowing()) {
                this.settingStage.close();
            }
            this.settingStage = null;
        }
        if (this.refCardStage != null) {
            if (this.refCardStage.isShowing()) {
                this.refCardStage.close();
            }
            this.refCardStage = null;
        }
    }
    
    public void setStyle() {
        final Stage primaryStage = (Stage)this.mainBorderPane.getScene().getWindow();
        primaryStage.getScene().getStylesheets().clear();
        primaryStage.getScene().getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
    }
    
    private void showResetPersistenceFileAlert() {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().setPrefSize(450.0, 200.0);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Reset user data");
        alert.setContentText("We no longer support the old version of persistence file.\nOnce clicked OK, all user data will be reset to data of Default User.");
        final Scene scene = alert.getDialogPane().getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
        final Optional<ButtonType> result = (Optional<ButtonType>)alert.showAndWait();
        if (result.get() != ButtonType.OK) {
            this.quit();
        }
    }
    
    @FXML
    public void showLogInDialog() throws IOException {
        this.userManager.showLogInDialog(this);
    }
    
    public void loadPersistentDataFromFile() throws NoSuchAlgorithmException {
        final TuneOutPersistentFile persistFile = new TuneOutPersistentFile();
        final String json = persistFile.fileToText(persistFile.getFile());
        if (persistFile.isValid(json)) {
            this.userManager.users = persistFile.convertFromJson(json);
            for (final User u : this.userManager.users.values()) {
                if (u.isSelected()) {
                    u.setSelected(false);
                    break;
                }
            }
            if (!this.userManager.users.isEmpty()) {
                this.userManager.users.get("Default User").setSelected(true);
            }
        }
        else {
            this.showResetPersistenceFileAlert();
        }
        if (this.userManager.isUserNameExists("Default User")) {
            this.loadUserData(this.userManager.users.get("Default User"));
        }
        else {
            this.loadUserData(null);
        }
        this.updateMenus();
    }
    
    public void loadUserData(final User user) throws NoSuchAlgorithmException {
        PersistenceData persistData = new PersistenceData();
        if (user == null) {
            this.userManager.users.clear();
            this.userManager.addDefaultUser(this);
            this.userManager.users.get("Default User").setSelected(true);
        }
        else if (!this.userManager.users.isEmpty() && this.userManager.users != null && this.userManager.users.containsKey(user.getUserName())) {
            persistData = user.getData();
        }
        if (persistData.getProject() != null) {
            this.project = persistData.getProject();
            if (persistData.getProject().getProjectFile() != null && persistData.getProject().getProjectFile().file.exists()) {
                final TuneOutProjectFile projFile = this.project.getProjectFile();
                final String fileText = projFile.fileToText(projFile.file);
                projFile.convertObjectsFromJson(fileText);
            }
            else {
                this.project.initialState();
            }
            this.updateAppTitle();
        }
        else {
            (this.project = new Project()).initialState();
        }
        if (persistData.getPreviousDirectory() != null) {
            FileInformation.setPrevDirectory(new File(persistData.getPreviousDirectory().getPath()));
        }
        if (persistData.getCommandHistory() != null && this.commandLine != null) {
            this.commandLine.setCommandHistory(persistData.getCommandHistory());
        }
        else if (persistData.getCommandHistory() != null) {
            final CommandLineController commandLine = new CommandLineController();
            commandLine.setCommandHistory(persistData.getCommandHistory());
        }
        if (persistData.getTranscript() != null && !persistData.getTranscript().isEmpty() && this.transcript != null) {
            this.transcript.setTextTranscript(persistData.getTranscript());
        }
        else if (persistData.getTranscript() != null && !persistData.getTranscript().isEmpty()) {
            final TranscriptController transcriptController = new TranscriptController();
            transcriptController.setTextTranscript(persistData.getTranscript());
        }
        if (persistData.getStyle() != null && !persistData.getStyle().isEmpty()) {
            StyleMap.setCurrentStyle(persistData.getStyle());
        }
        else {
            StyleMap.setCurrentStyle("default");
        }
        this.setStyle();
        if (persistData.getZoom() != 0.0 && this.transcript != null) {
            this.transcript.setFontSize(persistData.getZoom());
        }
        else if (persistData.getZoom() != 0.0) {
            final TranscriptController transcriptController = new TranscriptController();
            transcriptController.setTextTranscript(persistData.getTranscript());
        }
        if (persistData.getTutors() != null) {
            this.restoreTabs(persistData);
        }
        else if (persistData.getTutors() == null) {
            this.removeAddedTabs();
        }
        if (persistData.isTranscriptOpen()) {
            this.reopenMainTab();
        }
        if (this.exploreTab != null) {
            this.selectTab(this.exploreTab.getText());
        }
        RecordedTutorStats.setSaveStats(persistData.isSaveStats());
        this.project.resetChangedFlags();
        this.updateAppTitle();
    }
    
    public void savePersistentDataToFile() {
        for (final User u : this.userManager.users.values()) {
            if (u.isSelected()) {
                this.saveUserData(u);
                break;
            }
        }
        final TuneOutPersistentFile persistFile = new TuneOutPersistentFile();
        final String json = persistFile.convertToJson((TreeMap)this.userManager.users);
        persistFile.textToFile(json, persistFile.getFile());
        this.project.resetChangedFlags();
        this.updateAppTitle();
    }
    
    public void saveUserData(final User user) {
        List<String> openedTabs = new ArrayList<String>();
        if (this.mainTabPane.getTabs().size() > OuterTemplateController.NUM_FIXED_TABS) {
            for (final Tab tab : this.mainTabPane.getTabs()) {
                if (this.tutors.keySet().contains(tab.getText())) {
                    openedTabs.add(this.tutors.get(tab.getText()).tutorName);
                }
            }
        }
        else {
            openedTabs = null;
        }
        final PersistenceData persistData = new PersistenceData(this.project, FileInformation.getPrevDirectory(), this.commandLine.getCommandHistory(), this.transcript.getText(), StyleMap.getCurrentStyleName(), this.transcript.getFontSize(), RecordedTutorStats.isSaveStats(), openedTabs, this.checkTabExists("Commands and Transcript"));
        user.setData(persistData);
    }
    
    public void updateMenus() {
        this.userDeleteMenu.getItems().removeAll((Collection)this.userDeleteMenu.getItems());
        for (final User u : this.userManager.users.values()) {
            final CheckMenuItem deleteMenuItem = new CheckMenuItem(u.getUserName());
            deleteMenuItem.setOnAction(event -> {
                try {
                    this.deleteUser(deleteMenuItem.getText());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            });
            if (u.isSelected()) {
                this.userButton.setText(u.getUserName());
                deleteMenuItem.setSelected(true);
            }
            this.userDeleteMenu.getItems().add((Object)deleteMenuItem);
        }
    }
    
    @FXML
    public void addUser() throws IOException {
        if (this.userManager.users.size() >= 10) {
            this.showError("You cannot add a new user as you have the maximum number of 10 users added.");
        }
        else if (this.project.checkPromptUnsavedChanges()) {
            this.project.resetChangedFlags();
            this.updateAppTitle();
            this.userManager.showRegisterDialog(this);
        }
    }
    
    protected void loggedIn() {
        this.logged.setText("Log Out");
        this.closePopUp();
        this.updateMenus();
    }
    
    void switchUser(final User user, final boolean isNewUser) throws NoSuchAlgorithmException {
        if (!this.userManager.users.isEmpty()) {
            for (final User u : this.userManager.users.values()) {
                if (u.isSelected()) {
                    this.saveUserData(u);
                    u.setSelected(false);
                    break;
                }
            }
            user.setSelected(true);
        }
        if (!isNewUser) {
            this.loadUserData(user);
        }
        if (!isNewUser || !user.getUserName().equals("Default User")) {
            this.loggedIn();
        }
    }
    
    @FXML
    public void deleteUser(final String userName) throws IOException {
        if (userName.equals("Default User")) {
            this.showError(this.cannotDeleteDefaultUser);
        }
        else if (this.userManager.users.get(userName).isSelected()) {
            this.showError(this.cannotDeleteSelectedUser);
        }
        else {
            this.userManager.showDeleteDialog(this, userName);
            this.updateMenus();
        }
        if (this.userDeleteMenu != null) {
            this.userDeleteMenu.getParentPopup().hide();
        }
        this.updateMenus();
    }
    
    @FXML
    public void viewUserProfile() throws IOException {
        this.userManager.showUserProfileDialog(this);
    }
    
    public void logOut() throws NoSuchAlgorithmException {
        this.switchUser(this.userManager.users.get("Default User"), false);
        this.logged.setText("Log In");
    }
    
    @FXML
    public void handleLoggedButton() throws NoSuchAlgorithmException {
        final String log = this.logged.getText();
        try {
            if (this.project.checkPromptUnsavedChanges()) {
                this.project.resetChangedFlags();
                this.updateAppTitle();
                if (log.equals("Log In")) {
                    this.showLogInDialog();
                }
                else {
                    this.logOut();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void openUserStats() throws IOException {
        this.userStatsController = this.userManager.showUserStatsDialog(this);
    }
    
    public void closeUserStats() {
        this.userStatsController.closeStats();
    }
    
    @FXML
    public void runChordTutor() {
        final String tutorname = "Chord Tutor";
        this.runTutor(tutorname);
    }
    
    @FXML
    public void runChordSpellingTutor() {
        final String tutorname = "Chord Spelling Tutor";
        this.runTutor(tutorname);
    }
    
    @FXML
    public void runChordFunctionTutor() {
        final String tutorname = "Chord Function Tutor";
        this.runTutor(tutorname);
    }
    
    @FXML
    public void runIntervalTutor() {
        final String tutorname = "Interval Tutor";
        this.runTutor(tutorname);
    }
    
    @FXML
    public void runKeySignatureTutor() {
        final String tutorname = "Key Signature Tutor";
        this.runTutor(tutorname);
    }
    
    @FXML
    public void runMusicalTermTutor() {
        final String tutorname = "Musical Term Tutor";
        this.runTutor(tutorname);
    }
    
    @FXML
    public void runPitchTutor() {
        final String tutorname = "Pitch Tutor";
        this.runTutor(tutorname);
    }
    
    @FXML
    public void runScaleTutor() {
        final String tutorname = "Scale Tutor";
        this.runTutor(tutorname);
    }
    
    @FXML
    public void runScaleModeTutor() {
        final String tutorname = "Scale Mode Tutor";
        this.runTutor(tutorname);
    }
    
    private void warningPopUp(final String tabName) {
        final Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setContentText(tabName + " already exists!");
        alert.showAndWait();
    }
    
    private void runTutor(final String tutorname) {
        final Map<String, String> tutorsMap = new HashMap<String, String>();
        tutorsMap.put("Chord Spelling Tutor", "chordSpellingTutor()");
        tutorsMap.put("Chord Tutor", "chordTutor()");
        tutorsMap.put("Chord Function Tutor", "chordFunctionTutor()");
        tutorsMap.put("Interval Tutor", "intervalTutor()");
        tutorsMap.put("Musical Term Tutor", "musicalTermTutor()");
        tutorsMap.put("Pitch Tutor", "pitchTutor()");
        tutorsMap.put("Scale Tutor", "scaleTutor()");
        tutorsMap.put("Scale Mode Tutor", "scaleModeTutor()");
        tutorsMap.put("Key Signature Tutor", "keySignatureTutor()");
        if (this.checkTabExists(tutorname)) {
            for (final Tab tab : this.mainTabPane.getTabs()) {
                if (tab.getText().equals(tutorname)) {
                    this.mainTabPane.getSelectionModel().select((Object)tab);
                }
            }
        }
        else {
            this.commandLine.runCommand(tutorsMap.get(tutorname));
        }
    }
    
    public static void removeKeyboardFromMemory() {
        OuterTemplateController.viewKeyboard = null;
    }
    
    public static void removeDrumPadFromMemory() {
        OuterTemplateController.viewDrumPad = null;
    }
    
    public static void highlightEnable(final ArrayList<Integer> midiList) {
        if (OuterTemplateController.viewKeyboard != null) {
            OuterTemplateController.keyboard.highlightEnable(midiList);
        }
    }
    
    public static void highlightsDisable() {
        if (OuterTemplateController.viewKeyboard != null) {
            OuterTemplateController.keyboard.highlightsDisable();
        }
    }
    
    public static void drumAnimate(final ArrayList<Integer> midiList) {
        if (OuterTemplateController.viewDrumPad != null) {
            OuterTemplateController.drumPad.drumAnimate(midiList);
        }
    }
    
    @FXML
    public void setUpNotesGUI() throws IOException {
        final OuterLearningController notes = new OuterLearningController();
        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource("/View/Demos/notes.fxml"));
        final Parent root = (Parent)fxmlLoader.load();
        final Stage stage = new Stage();
        fxmlLoader.setController((Object)notes);
        stage.setTitle("Demos");
        stage.setScene(new Scene(root));
        stage.getScene().getStylesheets().clear();
        stage.getScene().getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
        stage.show();
    }
    
    public void importMelody(final ActionEvent actionEvent) {
        final TuneOutMusicXmlFile xmlFile = new TuneOutMusicXmlFile(FileInformation.openMXLFile("Import MusicXML"));
        if (xmlFile.file != null) {
            try {
                final String rawXml = xmlFile.extractXMLFile();
                if (xmlFile.isValid(rawXml)) {
                    final TextInputDialog textInput = new TextInputDialog("");
                    textInput.setTitle("Import Melody");
                    textInput.setHeaderText("Melody Name");
                    textInput.setContentText("Please enter a melody name:");
                    final Optional<String> result = (Optional<String>)textInput.showAndWait();
                    if (result.isPresent()) {
                        MelodyMap.addMelody(xmlFile.parseMelodyFromXML(rawXml, result.get().toLowerCase()));
                    }
                }
                else if (xmlFile.file != null) {
                    FileInformation.showNotValidError();
                }
            }
            catch (IOException e) {
                this.log.error(e.toString());
            }
        }
    }
    
    public void toggleKeyboardInput(final ActionEvent actionEvent) {
        try {
            this.commandLine.toggleKeyboardInput();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    static {
        NUM_FIXED_TABS = 3;
        OuterTemplateController.viewKeyboard = null;
        OuterTemplateController.viewDrumPad = null;
    }
}
