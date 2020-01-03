

package Controller.Keyboard;

import Model.Note.unitDuration.UnitDuration;
import Model.Note.unitDuration.UnitDurationMap;
import Model.Note.unitDuration.UnitDurationInformation;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class TwoOctavesKeyboardController
{
    @FXML
    AnchorPane twoOctavesKeyboardAnchorPane;
    private Integer firstFrame;
    private Integer lastFrame;
    private Integer defaultRange;
    private String leftKeyboardLocation;
    private String middleKeyboardLocation;
    private String rightKeyboardLocation;
    private AnchorPane mainKeyboardAnchorPane;
    private KeyboardController keyboardController;
    private final String left = "l";
    private final String right = "r";
    private boolean labelOn;
    private boolean noteMidi;
    private boolean oneAll;
    private boolean dotNotationState;
    
    public TwoOctavesKeyboardController() {
        this.firstFrame = 0;
        this.lastFrame = 8;
        this.defaultRange = 4;
        this.leftKeyboardLocation = "/View/Keyboard/leftKeyboard.fxml";
        this.middleKeyboardLocation = "/View/Keyboard/middleKeyboard.fxml";
        this.rightKeyboardLocation = "/View/Keyboard/rightKeyboard.fxml";
        this.labelOn = false;
        this.noteMidi = false;
        this.oneAll = false;
        this.dotNotationState = false;
    }
    
    public void initialize() throws IOException {
        this.updateFrameLocation(this.defaultRange);
    }
    
    public void updateFrameLocation(final Integer currentFrame) throws IOException {
        this.twoOctavesKeyboardAnchorPane.getChildren().clear();
        final FXMLLoader twoOctavesKeyboardLoader = new FXMLLoader();
        String view = "m";
        if (currentFrame.equals(this.firstFrame)) {
            twoOctavesKeyboardLoader.setLocation(this.getClass().getResource(this.leftKeyboardLocation));
            view = "l";
        }
        else if (currentFrame.equals(this.lastFrame - 1)) {
            twoOctavesKeyboardLoader.setLocation(this.getClass().getResource(this.rightKeyboardLocation));
            view = "r";
        }
        else {
            twoOctavesKeyboardLoader.setLocation(this.getClass().getResource(this.middleKeyboardLocation));
        }
        this.mainKeyboardAnchorPane = (AnchorPane)twoOctavesKeyboardLoader.load();
        (this.keyboardController = (KeyboardController)twoOctavesKeyboardLoader.getController()).setUpKeys(view, currentFrame);
        this.keyboardController.turnLabelsOn(this.labelOn);
        this.keyboardController.noteOrMidi(this.noteMidi);
        this.keyboardController.setAllOrOne(this.oneAll);
        this.keyboardController.setDotNotationState(this.dotNotationState);
        final UnitDuration udFromPersistence = UnitDurationInformation.getUnitDuration();
        final String udFromPersistenceName = udFromPersistence.getUnitDurationName();
        int numberOfDots = 0;
        if (this.dotNotationState) {
            numberOfDots = 1;
        }
        final UnitDuration ud = new UnitDuration(udFromPersistenceName, UnitDurationMap.getUnitDurationByName(udFromPersistenceName).getUnitDurationDivider(), true, numberOfDots);
        this.keyboardController.setUnitDurationValue(ud);
        this.twoOctavesKeyboardAnchorPane.getChildren().add((Object)this.mainKeyboardAnchorPane);
    }
    
    public void turnLabelsOn(final boolean on) {
        this.keyboardController.turnLabelsOn(on);
        this.labelOn = on;
    }
    
    public void noteOrMidi(final boolean midiOn) {
        this.keyboardController.noteOrMidi(midiOn);
        this.noteMidi = midiOn;
    }
    
    public void allOrOne(final boolean oneOrAll) {
        this.keyboardController.setAllOrOne(oneOrAll);
        this.oneAll = oneOrAll;
    }
    
    public void setFocus() {
        this.keyboardController.focusOnPane();
    }
    
    public void changeKeyboardParametersForLostFocusSituation() {
        this.keyboardController.clearBuffer();
        this.keyboardController.clearStatusShiftControl();
        this.keyboardController.changeKeyboardStateAfterShiftControlReleased();
    }
    
    public boolean controlStatus() {
        return this.keyboardController.controlKeyStatus();
    }
    
    public boolean shiftStatus() {
        return this.keyboardController.shiftKeyStatus();
    }
    
    protected void setUnitDurationValue(final UnitDuration ud) {
        this.keyboardController.setUnitDurationValue(ud);
    }
    
    protected boolean getDotNotationState() {
        return this.keyboardController.getDotNotationState();
    }
    
    protected void setDotNotationState(final boolean dotNotation) {
        this.keyboardController.setDotNotationState(dotNotation);
        this.dotNotationState = dotNotation;
    }
}
