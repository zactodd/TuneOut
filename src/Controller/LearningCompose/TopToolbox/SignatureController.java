

package Controller.LearningCompose.TopToolbox;

import javafx.event.ActionEvent;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Toggle;
import Model.Note.Scale.KeySignature;
import com.google.common.collect.HashBiMap;
import java.util.List;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import com.google.common.collect.BiMap;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SignatureController
{
    public VBox header;
    public HBox keysAboveHBox;
    public HBox keysBelowHBox;
    public Button leftBtnMaj;
    public Button rightBtnMaj;
    public Button leftBtnMin;
    public Button rightBtnMin;
    private TopToolboxController topToolboxController;
    private ToggleGroup toggleGroup;
    private static final int NUMBER_TOGGLE_BOXES_PER_LINE = 7;
    private BiMap<ToggleButton, ComboBox<String>> buttonToCombo;
    private Integer majorKeyNum;
    private Integer minKeyNum;
    private List<String> majKeysList;
    private List<String> minKeysList;
    
    public SignatureController() {
        this.toggleGroup = new ToggleGroup();
        this.buttonToCombo = (BiMap<ToggleButton, ComboBox<String>>)HashBiMap.create();
        this.majorKeyNum = 0;
        this.minKeyNum = 0;
        this.majKeysList = KeySignature.getListOfScaleNames("-M");
        this.minKeysList = KeySignature.getListOfScaleNames("-m");
    }
    
    public void initialize() {
        this.setUpToggles(this.majKeysList, this.keysAboveHBox, this.leftBtnMaj, this.rightBtnMaj, 0);
        this.setUpToggles(this.minKeysList, this.keysBelowHBox, this.leftBtnMin, this.rightBtnMin, 0);
        final ToggleButton toggleButton = (ToggleButton)this.toggleGroup.getToggles().get(0);
        this.toggleGroup.selectToggle((Toggle)toggleButton);
        this.addToggleGroupListener();
    }
    
    private void setUpToggles(final List<String> keysList, final HBox hbox, final Button leftBtn, final Button rightBtn, final Integer index) {
        final String prevSelectedToggle = this.getSelectedKeyToggle();
        hbox.getChildren().clear();
        hbox.getChildren().add((Object)leftBtn);
        if (index == 0) {
            leftBtn.setDisable(true);
        }
        else {
            leftBtn.setDisable(false);
        }
        if (index + 7 > keysList.size() - 1) {
            rightBtn.setDisable(true);
        }
        else {
            rightBtn.setDisable(false);
        }
        for (int x = index; x < index + 7; ++x) {
            if (keysList.size() >= x) {
                final ToggleButton keyToggle = new ToggleButton();
                keyToggle.setId("exploreBtn");
                keyToggle.setText((String)keysList.get(x));
                keyToggle.setToggleGroup(this.toggleGroup);
                hbox.getChildren().add((Object)keyToggle);
            }
        }
        hbox.getChildren().add((Object)rightBtn);
        if (prevSelectedToggle != null) {
            for (final Toggle button : this.toggleGroup.getToggles()) {
                final ToggleButton toggleBtn = (ToggleButton)button;
                if (toggleBtn.getText().equals(prevSelectedToggle)) {
                    this.toggleGroup.selectToggle((Toggle)toggleBtn);
                }
            }
        }
    }
    
    protected void setTopToolboxController(final TopToolboxController topToolboxController) {
        this.topToolboxController = topToolboxController;
    }
    
    private void addToggleGroupListener() {
        this.toggleGroup.selectedToggleProperty().addListener((ChangeListener)new ChangeListener<Toggle>() {
            public void changed(final ObservableValue ov, final Toggle oldToggle, final Toggle newToggle) {
                if (newToggle != null) {
                    final ToggleButton toggleBtn = (ToggleButton)newToggle;
                    final String btnText = toggleBtn.getText();
                    if (oldToggle != null) {
                        final ToggleButton oldToggleBtn = (ToggleButton)oldToggle;
                        final String oldBtnText = oldToggleBtn.getText();
                        if (!oldBtnText.equals(btnText)) {
                            SignatureController.this.topToolboxController.changeSignature(btnText);
                        }
                    }
                }
                else {
                    oldToggle.setSelected(true);
                }
            }
        });
    }
    
    protected String getSelectedKeyToggle() {
        final ToggleButton toggleBtn = (ToggleButton)this.toggleGroup.getSelectedToggle();
        if (toggleBtn != null) {
            return toggleBtn.getText();
        }
        return "";
    }
    
    public void moveDownMaj(final ActionEvent actionEvent) {
        --this.majorKeyNum;
        this.setUpToggles(this.majKeysList, this.keysAboveHBox, this.leftBtnMaj, this.rightBtnMaj, this.majorKeyNum);
    }
    
    public void moveUpMaj(final ActionEvent actionEvent) {
        ++this.majorKeyNum;
        this.setUpToggles(this.majKeysList, this.keysAboveHBox, this.leftBtnMaj, this.rightBtnMaj, this.majorKeyNum);
    }
    
    public void moveDownMin(final ActionEvent actionEvent) {
        --this.minKeyNum;
        this.setUpToggles(this.minKeysList, this.keysBelowHBox, this.leftBtnMin, this.rightBtnMin, this.minKeyNum);
    }
    
    public void moveUpMin(final ActionEvent actionEvent) {
        ++this.minKeyNum;
        this.setUpToggles(this.minKeysList, this.keysBelowHBox, this.leftBtnMin, this.rightBtnMin, this.minKeyNum);
    }
}
