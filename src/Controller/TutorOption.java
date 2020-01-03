

package Controller;

import javafx.beans.property.StringProperty;
import Model.Tutor.Options;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import Environment.GrammarParser;
import javafx.scene.text.TextAlignment;
import java.util.List;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Toggle;
import javafx.scene.layout.HBox;
import javafx.scene.Node;
import java.util.Map;
import javafx.scene.control.ToggleGroup;
import Environment.Environment;
import javafx.scene.control.Control;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public abstract class TutorOption
{
    @FXML
    protected TextField numQuestionsTextField;
    @FXML
    protected Button startBtn;
    @FXML
    protected Label numQuestionsError;
    @FXML
    protected GridPane paneButton;
    @FXML
    protected Label selectionError;
    protected Control[] otherOptionControls;
    protected Control[] specialControls;
    protected Environment tutorEnvironment;
    protected TutorController tutorController;
    protected ToggleGroup group;
    private static final int MAX_NUM_QUESTIONS_LENGTH = 10;
    
    public TutorOption() {
        this.selectionError = new Label();
        this.group = new ToggleGroup();
    }
    
    public abstract void startTutor();
    
    abstract void initialize(final OuterTemplateController p0, final TutorAnswer p1, final Environment p2, final TutorController p3);
    
    public void disableOptions(final boolean disable) {
        this.numQuestionsTextField.setDisable(disable);
        this.startBtn.setDisable(disable);
        this.startBtn.setDefaultButton(!disable);
        if (this.otherOptionControls != null) {
            for (final Control control : this.otherOptionControls) {
                control.setDisable(disable);
            }
        }
    }
    
    public void setUpButtonsBig(final Map<String, String> map, final Node nodeIn) {
        this.setUpListener();
        final int width = 180;
        final int height = 60;
        for (final String key : map.keySet()) {
            final ToggleButton newButton = this.createButton(width, height);
            newButton.setText(key);
            newButton.setToggleGroup(this.group);
            ((HBox)nodeIn).getChildren().add((Object)newButton);
        }
        final int size = this.group.getToggles().size();
        this.group.selectToggle((Toggle)this.group.getToggles().get(size - 1));
    }
    
    public void setUpButtonsSmall(final List<String> list, final Node nodeIn) {
        this.setUpListener();
        final int width = 200;
        final int height = 30;
        for (final String key : list) {
            final ToggleButton newButton = this.createButton(width, height);
            newButton.setText(key);
            newButton.setToggleGroup(this.group);
            ((HBox)nodeIn).getChildren().add((Object)newButton);
        }
        final int size = this.group.getToggles().size();
        this.group.selectToggle((Toggle)this.group.getToggles().get(size - 1));
    }
    
    public ToggleButton createButton(final int width, final int height) {
        final ToggleButton newButton = new ToggleButton();
        newButton.setPrefSize((double)width, (double)height);
        newButton.wrapTextProperty().setValue(Boolean.valueOf(true));
        newButton.setStyle("-fx-background-radius: 30;");
        newButton.setTextAlignment(TextAlignment.CENTER);
        return newButton;
    }
    
    public void setUpButtonsBig(final List<String> list, final Node nodeIn) {
        final int width = 180;
        final int height = 60;
        for (final String key : list) {
            final ToggleButton newButton = this.createButton(width, height);
            newButton.setText(key);
            ((HBox)nodeIn).getChildren().add((Object)newButton);
            newButton.setToggleGroup(this.group);
        }
        final int size = this.group.getToggles().size();
        this.group.selectToggle((Toggle)this.group.getToggles().get(size - 1));
    }
    
    protected boolean nullcheckToggled() {
        return this.group.getSelectedToggle() == null;
    }
    
    protected ToggleButton getSelectedToggle() {
        return (ToggleButton)this.group.getSelectedToggle();
    }
    
    protected void selectToggle(final String labelToSelect) {
        for (final Toggle toggle : this.group.getToggles()) {
            final ToggleButton toggleBtn = (ToggleButton)toggle;
            if (labelToSelect.equals(toggleBtn.getText())) {
                this.group.selectToggle((Toggle)toggleBtn);
            }
        }
    }
    
    protected String runTutorCommand(final String command) {
        new GrammarParser(this.tutorEnvironment).executeCommand("tutor:" + command);
        return this.tutorEnvironment.getResponse();
    }
    
    protected void showWarning() {
        this.selectionError.setVisible(true);
    }
    
    public void setUpListener() {
        this.group.selectedToggleProperty().addListener((ChangeListener)new ChangeListener<Toggle>() {
            public void changed(final ObservableValue<? extends Toggle> observable, final Toggle oldValue, final Toggle newValue) {
                TutorOption.this.selectionError.setVisible(false);
            }
        });
    }
    
    public void setUpNumQuestionsListeners() {
        this.numQuestionsTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= 10) {
                ((StringProperty)observable).setValue(newValue.substring(0, 10));
            }
        });
        this.numQuestionsTextField.focusedProperty().addListener((ChangeListener)new ChangeListener<Boolean>() {
            public void changed(final ObservableValue<? extends Boolean> observable, final Boolean oldValue, final Boolean newValue) {
                if (!newValue) {
                    if (!Options.numQuestionsValid(TutorOption.this.numQuestionsTextField.getText())) {
                        TutorOption.this.numQuestionsError.setVisible(true);
                        TutorOption.this.startBtn.setDisable(false);
                    }
                    else {
                        TutorOption.this.numQuestionsError.setVisible(false);
                        TutorOption.this.startBtn.setDisable(false);
                    }
                }
            }
        });
    }
}
