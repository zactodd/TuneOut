

package Controller.Keyboard;

import javafx.scene.input.MouseEvent;
import javafx.beans.property.DoubleProperty;
import javafx.animation.Timeline;
import javafx.beans.value.WritableValue;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import java.util.Iterator;
import java.io.IOException;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;

public class FullPianoKeyboardController
{
    private final Integer defaultRange;
    private final String left = "l";
    private final String right = "r";
    @FXML
    private Rectangle C7C8Range;
    @FXML
    private Rectangle C0C1Range;
    @FXML
    private Rectangle C1C2Range;
    @FXML
    private Rectangle C2C3Range;
    @FXML
    private Rectangle C3C4Range;
    @FXML
    private Rectangle C4C5Range;
    @FXML
    private Rectangle C5C6Range;
    @FXML
    private Rectangle C6C7Range;
    @FXML
    private List<Rectangle> fullRange;
    @FXML
    private Button leftBtn;
    @FXML
    private Button rightBtn;
    @FXML
    private ImageView A0;
    @FXML
    private ImageView B0;
    @FXML
    private ImageView C1;
    @FXML
    private ImageView D1;
    @FXML
    private ImageView E1;
    @FXML
    private ImageView F1;
    @FXML
    private ImageView G1;
    @FXML
    private ImageView A1;
    @FXML
    private ImageView B1;
    @FXML
    private ImageView C2;
    @FXML
    private ImageView D2;
    @FXML
    private ImageView E2;
    @FXML
    private ImageView F2;
    @FXML
    private ImageView G2;
    @FXML
    private ImageView A2;
    @FXML
    private ImageView B2;
    @FXML
    private ImageView C3;
    @FXML
    private ImageView D3;
    @FXML
    private ImageView E3;
    @FXML
    private ImageView F3;
    @FXML
    private ImageView G3;
    @FXML
    private ImageView A3;
    @FXML
    private ImageView B3;
    @FXML
    private ImageView C4;
    @FXML
    private ImageView D4;
    @FXML
    private ImageView E4;
    @FXML
    private ImageView F4;
    @FXML
    private ImageView G4;
    @FXML
    private ImageView A4;
    @FXML
    private ImageView B4;
    @FXML
    private ImageView C5;
    @FXML
    private ImageView D5;
    @FXML
    private ImageView E5;
    @FXML
    private ImageView F5;
    @FXML
    private ImageView G5;
    @FXML
    private ImageView A5;
    @FXML
    private ImageView B5;
    @FXML
    private ImageView C6;
    @FXML
    private ImageView D6;
    @FXML
    private ImageView E6;
    @FXML
    private ImageView F6;
    @FXML
    private ImageView G6;
    @FXML
    private ImageView A6;
    @FXML
    private ImageView B6;
    @FXML
    private ImageView C7;
    @FXML
    private ImageView D7;
    @FXML
    private ImageView E7;
    @FXML
    private ImageView F7;
    @FXML
    private ImageView G7;
    @FXML
    private ImageView A7;
    @FXML
    private ImageView B7;
    @FXML
    private ImageView C8;
    @FXML
    private ImageView A0S;
    @FXML
    private ImageView C1S;
    @FXML
    private ImageView D1S;
    @FXML
    private ImageView F1S;
    @FXML
    private ImageView G1S;
    @FXML
    private ImageView A1S;
    @FXML
    private ImageView C2S;
    @FXML
    private ImageView D2S;
    @FXML
    private ImageView F2S;
    @FXML
    private ImageView G2S;
    @FXML
    private ImageView A2S;
    @FXML
    private ImageView C3S;
    @FXML
    private ImageView D3S;
    @FXML
    private ImageView F3S;
    @FXML
    private ImageView G3S;
    @FXML
    private ImageView A3S;
    @FXML
    private ImageView C4S;
    @FXML
    private ImageView D4S;
    @FXML
    private ImageView F4S;
    @FXML
    private ImageView G4S;
    @FXML
    private ImageView A4S;
    @FXML
    private ImageView C5S;
    @FXML
    private ImageView D5S;
    @FXML
    private ImageView F5S;
    @FXML
    private ImageView G5S;
    @FXML
    private ImageView A5S;
    @FXML
    private ImageView C6S;
    @FXML
    private ImageView D6S;
    @FXML
    private ImageView F6S;
    @FXML
    private ImageView G6S;
    @FXML
    private ImageView A6S;
    @FXML
    private ImageView C7S;
    @FXML
    private ImageView D7S;
    @FXML
    private ImageView F7S;
    @FXML
    private ImageView G7S;
    @FXML
    private ImageView A7S;
    private HashMap<Integer, ImageView> highlights;
    private boolean highlightsState;
    private Integer currentRange;
    private TwoOctavesKeyboardController twoOctavesKeyboardController;
    
    public FullPianoKeyboardController() {
        this.defaultRange = 4;
        this.highlights = new HashMap<Integer, ImageView>();
        this.highlightsState = false;
        this.currentRange = this.defaultRange;
    }
    
    public void initialize(final TwoOctavesKeyboardController twoOctavesKeyboardController) throws IOException {
        this.highlights.put(21, this.A0);
        this.highlights.put(22, this.A0S);
        this.highlights.put(23, this.B0);
        this.highlights.put(24, this.C1);
        this.highlights.put(25, this.C1S);
        this.highlights.put(26, this.D1);
        this.highlights.put(27, this.D1S);
        this.highlights.put(28, this.E1);
        this.highlights.put(29, this.F1);
        this.highlights.put(30, this.F1S);
        this.highlights.put(31, this.G1);
        this.highlights.put(32, this.G1S);
        this.highlights.put(33, this.A1);
        this.highlights.put(34, this.A1S);
        this.highlights.put(35, this.B1);
        this.highlights.put(36, this.C2);
        this.highlights.put(37, this.C2S);
        this.highlights.put(38, this.D2);
        this.highlights.put(39, this.D2S);
        this.highlights.put(40, this.E2);
        this.highlights.put(41, this.F2);
        this.highlights.put(42, this.F2S);
        this.highlights.put(43, this.G2);
        this.highlights.put(44, this.G2S);
        this.highlights.put(45, this.A2);
        this.highlights.put(46, this.A2S);
        this.highlights.put(47, this.B2);
        this.highlights.put(48, this.C3);
        this.highlights.put(49, this.C3S);
        this.highlights.put(50, this.D3);
        this.highlights.put(51, this.D3S);
        this.highlights.put(52, this.E3);
        this.highlights.put(53, this.F3);
        this.highlights.put(54, this.F3S);
        this.highlights.put(55, this.G3);
        this.highlights.put(56, this.G3S);
        this.highlights.put(57, this.A3);
        this.highlights.put(58, this.A3S);
        this.highlights.put(59, this.B3);
        this.highlights.put(60, this.C4);
        this.highlights.put(61, this.C4S);
        this.highlights.put(62, this.D4);
        this.highlights.put(63, this.D4S);
        this.highlights.put(64, this.E4);
        this.highlights.put(65, this.F4);
        this.highlights.put(66, this.F4S);
        this.highlights.put(67, this.G4);
        this.highlights.put(68, this.G4S);
        this.highlights.put(69, this.A4);
        this.highlights.put(70, this.A4S);
        this.highlights.put(71, this.B4);
        this.highlights.put(72, this.C5);
        this.highlights.put(73, this.C5S);
        this.highlights.put(74, this.D5);
        this.highlights.put(75, this.D5S);
        this.highlights.put(76, this.E5);
        this.highlights.put(77, this.F5);
        this.highlights.put(78, this.F5S);
        this.highlights.put(79, this.G5);
        this.highlights.put(80, this.G5S);
        this.highlights.put(81, this.A5);
        this.highlights.put(82, this.A5S);
        this.highlights.put(83, this.B5);
        this.highlights.put(84, this.C6);
        this.highlights.put(85, this.C6S);
        this.highlights.put(86, this.D6);
        this.highlights.put(87, this.D6S);
        this.highlights.put(88, this.E6);
        this.highlights.put(89, this.F6);
        this.highlights.put(90, this.F6S);
        this.highlights.put(91, this.G6);
        this.highlights.put(92, this.G6S);
        this.highlights.put(93, this.A6);
        this.highlights.put(94, this.A6S);
        this.highlights.put(95, this.B6);
        this.highlights.put(96, this.C7);
        this.highlights.put(97, this.C7S);
        this.highlights.put(98, this.D7);
        this.highlights.put(99, this.D7S);
        this.highlights.put(100, this.E7);
        this.highlights.put(101, this.F7);
        this.highlights.put(102, this.F7S);
        this.highlights.put(103, this.G7);
        this.highlights.put(104, this.G7S);
        this.highlights.put(105, this.A7);
        this.highlights.put(106, this.A7S);
        this.highlights.put(107, this.B7);
        this.highlights.put(108, this.C8);
        this.twoOctavesKeyboardController = twoOctavesKeyboardController;
        (this.fullRange = new ArrayList<Rectangle>()).addAll(Arrays.asList(this.C0C1Range, this.C1C2Range, this.C2C3Range, this.C3C4Range, this.C4C5Range, this.C5C6Range, this.C6C7Range, this.C7C8Range));
        this.highlightsOff();
        this.mouseListener();
    }
    
    @FXML
    public void clickRightBtn() throws IOException {
        if (this.currentRange + 1 > this.fullRange.size() - 1) {
            this.currentRange = 0;
        }
        else {
            ++this.currentRange;
        }
        this.twoOctavesKeyboardController.updateFrameLocation(this.currentRange);
        this.updateFrame("r");
    }
    
    @FXML
    public void clickLeftBtn() throws IOException {
        if (this.currentRange - 1 < 0) {
            this.currentRange = this.fullRange.size() - 1;
        }
        else {
            --this.currentRange;
        }
        this.twoOctavesKeyboardController.updateFrameLocation(this.currentRange);
        this.updateFrame("l");
    }
    
    private void updateFrame(final String leftOrRight) {
        this.fullRange.get(this.currentRange).setOpacity(0.3);
        if (leftOrRight.equals("l")) {
            if (this.currentRange == this.fullRange.size() - 1) {
                this.fullRange.get(0).setOpacity(0.0);
            }
            else {
                this.fullRange.get(this.currentRange + 1).setOpacity(0.0);
            }
        }
        else if (this.currentRange == 0) {
            this.fullRange.get(this.fullRange.size() - 1).setOpacity(0.0);
        }
        else {
            this.fullRange.get(this.currentRange - 1).setOpacity(0.0);
        }
    }
    
    private void mouseListener() {
        this.leftBtn.setOnMousePressed(event -> {
            if (event.isControlDown() || event.isShiftDown()) {
                this.twoOctavesKeyboardController.setFocus();
            }
        });
        this.rightBtn.setOnMousePressed(event -> {
            if (event.isControlDown() || event.isShiftDown()) {
                this.twoOctavesKeyboardController.setFocus();
            }
        });
    }
    
    protected void highlightEnable(final ArrayList<Integer> midiList) {
        this.highlightsOff();
        this.redRectanglesDisable();
        if (!this.highlightsState) {
            this.highlightsState = true;
        }
        for (final Integer midi : midiList) {
            if (midi >= 21 && midi <= 108) {
                this.highlights.get(midi).setVisible(true);
            }
        }
    }
    
    private void highlightsOff() {
        for (int index = 21; index <= 108; ++index) {
            this.highlights.get(index).setVisible(false);
        }
    }
    
    protected void highlightsDisable() {
        if (this.highlightsState) {
            this.highlightsState = false;
            this.highlightsOff();
            final DoubleProperty opacity = this.fullRange.get(this.currentRange).opacityProperty();
            final Timeline fadein = new Timeline(new KeyFrame[] { new KeyFrame(Duration.ZERO, new KeyValue[] { new KeyValue((WritableValue)opacity, (Object)0.0) }), new KeyFrame(new Duration(100.0), new KeyValue[] { new KeyValue((WritableValue)opacity, (Object)0.3) }) });
            fadein.play();
        }
    }
    
    private void redRectanglesDisable() {
        for (final Rectangle fr : this.fullRange) {
            fr.setOpacity(0.0);
        }
    }
}
