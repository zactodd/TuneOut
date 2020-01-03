

package Controller;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.binding.Bindings;
import Model.keyboardInput.ComposerInput;
import Model.keyboardInput.LearningInput;
import javafx.scene.control.Tab;
import Model.keyboardInput.KeyboardInput;
import java.util.Collection;
import java.util.ArrayList;
import Environment.GrammarParser;
import App.App;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import java.io.IOException;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.List;
import Model.command.CommandStorage.CommandMap;
import javafx.scene.control.IndexRange;
import Model.keyboardInput.TextFieldInput;
import Model.command.CommandStorage.CommandHistory;
import Environment.Environment;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class CommandLineController
{
    @FXML
    private AnchorPane commandAnchorPane;
    @FXML
    private TextField commandTextField;
    @FXML
    private ComboBox predictionComboBox;
    @FXML
    private TranscriptController transcriptController;
    private OuterTemplateController main;
    protected Environment mainEnvironment;
    private CommandHistory commandHistory;
    private String keyboardOff;
    private String keyboardOn;
    private String keyboardHover;
    private String keyboardInputHover;
    private TextFieldInput commandLineInput;
    private IndexRange currentSelection;
    private CommandMap commandMap;
    private List<String> predictCommands;
    private final Integer maxPredictRowCount;
    private final int maxCommandLength = 1000;
    
    public CommandLineController() {
        this.keyboardInputHover = "-fx-background-image: url(%1$s); -fx-background-size: 100%% 100%%";
        this.maxPredictRowCount = 3;
    }
    
    CommandHistory getCommandHistory() {
        return this.commandHistory;
    }
    
    void setCommandHistory(final CommandHistory commandHistory) {
        this.commandHistory = commandHistory;
    }
    
    private void getCommand(final KeyEvent event) throws IOException {
        if (event.getCode().equals((Object)KeyCode.UP)) {
            final String previousCommand = this.commandHistory.getPreviousCommand();
            if (previousCommand != null) {
                this.commandTextField.setText(previousCommand);
            }
        }
        else if (event.getCode().equals((Object)KeyCode.DOWN)) {
            this.commandTextField.setText(this.commandHistory.getNextCommand());
        }
        else if (event.getCode().equals((Object)KeyCode.K) && event.isControlDown()) {
            final String beforeString = this.commandTextField.getText();
            final Integer caretPos = this.commandTextField.getCaretPosition();
            try {
                this.setPredictText();
            }
            catch (NullPointerException e) {
                this.commandTextField.setText(beforeString);
                this.commandTextField.positionCaret((int)caretPos);
            }
        }
    }
    
    public OuterTemplateController getMain() {
        return this.main;
    }
    
    void initializeController(final Environment commandEnvironment) throws IOException {
        this.commandTextField.requestFocus();
        this.initKeyboardInput();
        this.commandTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= 1000) {
                ((StringProperty)observable).setValue(newValue.substring(0, 1000));
            }
        });
        this.commandTextField.setOnKeyPressed(event -> {
            try {
                this.getCommand(event);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.commandHistory = new CommandHistory();
        this.commandMap = new CommandMap();
        this.predictionComboBox.getSelectionModel().selectedItemProperty().addListener((observable, t, t1) -> this.setPredictText());
        this.predictionComboBox.setCellFactory((Callback)new Callback<ListView<String>, ListCell<String>>() {
            public ListCell<String> call(final ListView<String> p) {
                return new ListCell<String>() {
                    protected void updateItem(final String item, final boolean empty) {
                        super.updateItem((Object)item, empty);
                        this.getListView().setFixedCellSize(25.0);
                        if (empty || item == null) {
                            this.setText((String)null);
                        }
                        else {
                            this.setText(item);
                        }
                    }
                };
            }
        });
        this.mainEnvironment = commandEnvironment;
    }
    
    private void initKeyboardInput() {
        this.keyboardOff = "View/Keyboard/graphic/keyboardInputOff.jpg";
        this.keyboardOn = "View/Keyboard/graphic/keyboardInputOn.jpg";
        this.setKeyboardInputHover(this.keyboardHover = "View/Keyboard/graphic/keyboardInputHover.jpg", this.keyboardOff);
        final KeyCombination inputMnemonic = (KeyCombination)new KeyCodeCombination(KeyCode.I, KeyCombination.ModifierValue.UP, KeyCombination.ModifierValue.DOWN, KeyCombination.ModifierValue.UP, KeyCombination.ModifierValue.UP, KeyCombination.ModifierValue.UP);
        App.setShortcut(inputMnemonic, this.main.keyboardInputButton);
        this.commandLineInput = new TextFieldInput(this.commandTextField);
        this.commandTextField.focusedProperty().addListener((observable, wasFocused, isFocused) -> {
            if (wasFocused && !isFocused) {
                this.commandLineInput.reStart();
                KeyboardInput.setNoteInputField(this.commandLineInput);
                this.currentSelection = this.commandTextField.getSelection();
            }
        });
    }
    
    @FXML
    private void handleButtonAction() throws IOException {
        String commandRequest = this.commandTextField.getText();
        if (commandRequest.contains("(")) {
            this.commandMap.increaseCount(commandRequest.substring(0, commandRequest.indexOf("(")));
        }
        if (this.mainEnvironment.isTutorMode()) {
            commandRequest = "tutor:" + commandRequest;
        }
        new GrammarParser(this.mainEnvironment).executeCommand(commandRequest);
        this.commandHistory.addCommand(commandRequest);
        final String commandResponse = this.mainEnvironment.getResponse();
        this.commandTextField.clear();
        this.transcriptController.appendToTranscript(">" + commandRequest + "\n" + commandResponse + "\n");
        this.main.updateAppTitle();
    }
    
    void runCommand(final String command) {
        this.commandTextField.setText(command);
        String commandRequest = command;
        if (this.mainEnvironment.isTutorMode()) {
            commandRequest = "tutor:" + commandRequest;
        }
        new GrammarParser(this.mainEnvironment).executeCommand(commandRequest);
        this.commandHistory.addCommand(commandRequest);
        final String commandResponse = this.mainEnvironment.getResponse();
        this.commandTextField.clear();
        this.transcriptController.appendToTranscript(">" + commandRequest + "\n" + commandResponse + "\n");
        this.main.updateAppTitle();
    }
    
    void copyCommand(final String command) {
        this.commandTextField.setText(command);
        this.main.focusCommandLine();
    }
    
    void setTranscript(final OuterTemplateController main, final TranscriptController transcriptController) {
        this.main = main;
        this.transcriptController = transcriptController;
    }
    
    void resetCommandHistory() {
        this.commandHistory = new CommandHistory();
    }
    
    @FXML
    public void onKeyPressed(final KeyEvent keyPressed) {
        if (keyPressed.getCode().toString().equals("ENTER")) {
            this.setPredictText();
        }
        else if (keyPressed.getCode().equals((Object)KeyCode.L) && keyPressed.isControlDown()) {
            this.commandHistorySearch();
        }
        else {
            this.getCloseCommand();
        }
    }
    
    @FXML
    private void getCloseCommand() {
        if (!this.commandTextField.getText().equals("")) {
            (this.predictCommands = new ArrayList<String>()).addAll(this.commandMap.closestCommands(this.commandTextField.getText()));
            this.predictionComboBox.hide();
            this.predictionComboBox.getItems().setAll((Collection)this.predictCommands);
            this.predictionComboBox.setVisibleRowCount(Integer.min(this.maxPredictRowCount, this.predictionComboBox.getItems().size()));
            this.predictionComboBox.show();
        }
        this.hideComboPredictIfEmpty();
    }
    
    @FXML
    private void setPredictText() {
        if (!this.commandTextField.getText().equals("") && this.commandMap.closestCommands(this.commandTextField.getText()).size() != 0) {
            String selectedItem;
            if (!this.predictionComboBox.getSelectionModel().isEmpty()) {
                selectedItem = this.predictionComboBox.getSelectionModel().getSelectedItem().toString();
            }
            else {
                selectedItem = this.predictionComboBox.getItems().get(0).toString();
            }
            this.commandTextField.setText(selectedItem + "()");
            this.commandTextField.positionCaret(this.commandTextField.getText().length() - 1);
        }
        this.predictionComboBox.hide();
    }
    
    private void hideComboPredictIfEmpty() {
        if (this.commandTextField.getText().equals("") || this.commandMap.closestCommands(this.commandTextField.getText()).size() == 0) {
            this.predictionComboBox.getItems().clear();
            this.predictionComboBox.hide();
        }
    }
    
    public void commandHistorySearch() {
        if (!this.commandTextField.getText().equals("")) {
            this.commandTextField.setText(this.commandHistory.searchCommand(this.commandTextField.getText()));
            this.commandTextField.positionCaret(this.commandTextField.getText().length());
        }
    }
    
    public void focus() {
        this.commandTextField.requestFocus();
    }
    
    void focusInCommandParam() {
        if (this.commandTextField.getText().contains("(") && this.commandTextField.getText().contains(")")) {
            this.commandTextField.positionCaret(this.commandTextField.getText().indexOf("(") + 1);
        }
    }
    
    public void turnOffKeyboardInput() {
        if (KeyboardInput.isInputAccepted()) {
            KeyboardInput.toggleInputAccepted();
            this.setKeyboardInputHover(this.keyboardHover, this.keyboardOff);
        }
    }
    
    @FXML
    protected void toggleKeyboardInput() throws IOException {
        KeyboardInput.toggleInputAccepted();
        if (KeyboardInput.isInputAccepted()) {
            if (this.currentSelection != null) {
                this.commandTextField.selectRange(this.currentSelection.getStart(), this.currentSelection.getEnd());
            }
            if (((Tab)this.main.mainTabPane.getSelectionModel().getSelectedItem()).equals(this.main.exploreTab)) {
                KeyboardInput.setNoteInputField(new LearningInput(this.main.learning));
            }
            else if (((Tab)this.main.mainTabPane.getSelectionModel().getSelectedItem()).equals(this.main.composeTab)) {
                KeyboardInput.setNoteInputField(new ComposerInput(this.main.compose));
            }
            else {
                KeyboardInput.setNoteInputField(this.commandLineInput);
            }
            this.setKeyboardInputHover(this.keyboardHover, this.keyboardOn);
        }
        else {
            this.setKeyboardInputHover(this.keyboardHover, this.keyboardOff);
        }
        this.main.keyboard();
    }
    
    private void setKeyboardInputHover(final String onHover, final String offHover) {
        this.main.keyboardInputButton.styleProperty().bind((ObservableValue)Bindings.when((ObservableBooleanValue)this.main.keyboardInputButton.hoverProperty()).then((ObservableStringValue)new SimpleStringProperty(String.format(this.keyboardInputHover, onHover))).otherwise((ObservableStringValue)new SimpleStringProperty(String.format(this.keyboardInputHover, offHover))));
    }
}
