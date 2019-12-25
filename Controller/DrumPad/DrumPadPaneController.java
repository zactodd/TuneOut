// 
// Decompiled by Procyon v0.5.36
// 

package Controller.DrumPad;

import Controller.OuterTemplateController;
import javafx.stage.WindowEvent;
import javafx.beans.value.ObservableValue;
import Model.Note.unitDuration.UnitDurationInformation;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.DoubleProperty;
import javafx.animation.Timeline;
import javafx.beans.value.WritableValue;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import java.util.Iterator;
import javafx.stage.Modality;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.util.ArrayList;
import Model.Play.Play;
import javafx.scene.Scene;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.shape.Ellipse;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class DrumPadPaneController
{
    @FXML
    private AnchorPane drumPadPane;
    @FXML
    private ImageView drum36;
    @FXML
    private ImageView drum45;
    @FXML
    private ImageView drum50;
    @FXML
    private ImageView drum38;
    @FXML
    private ImageView drum43;
    @FXML
    private ImageView drum51;
    @FXML
    private ImageView drum57;
    @FXML
    private ImageView drum46;
    @FXML
    private ImageView drum49;
    @FXML
    private ImageView drum44;
    @FXML
    private Ellipse d36;
    @FXML
    private Ellipse d45;
    @FXML
    private Ellipse d50;
    @FXML
    private Ellipse d38;
    @FXML
    private Ellipse d43;
    @FXML
    private Ellipse d51;
    @FXML
    private Ellipse d57;
    @FXML
    private Ellipse d46;
    @FXML
    private Ellipse d49;
    @FXML
    private Ellipse d44;
    private final Image drumsticksBlack;
    private final Image drumsticksWhite;
    private final Image drumboot;
    private final Image bassDrumPress;
    private final Image bassDrumRelease;
    private final Image hiHatPedalPress;
    private final Image hiHatPedalRelease;
    private final Image hiHatCymbalsPress;
    private final Image hiHatCymbalsRelease;
    private List<ImageView> drums;
    private List<Double> drumsHeight;
    private List<Double> drumsWidth;
    private final int hiHatPedalIndex = 9;
    private final int hiHatCymbalsIndex = 7;
    private boolean shiftStatus;
    private List<Ellipse> buttons;
    private Scene scene;
    private Play playEnv;
    
    public DrumPadPaneController() {
        this.drumsticksBlack = new Image("/View/DrumPad/drumGraphics/drumsticks_black.png");
        this.drumsticksWhite = new Image("/View/DrumPad/drumGraphics/drumsticks_white.png");
        this.drumboot = new Image("/View/DrumPad/drumGraphics/drumboot.png");
        this.bassDrumPress = new Image("/View/DrumPad/drumGraphics/01_transA.png");
        this.bassDrumRelease = new Image("/View/DrumPad/drumGraphics/01_trans.png");
        this.hiHatPedalPress = new Image("/View/DrumPad/drumGraphics/08_transA.png");
        this.hiHatPedalRelease = new Image("/View/DrumPad/drumGraphics/08_trans.png");
        this.hiHatCymbalsPress = new Image("/View/DrumPad/drumGraphics/07_transA.png");
        this.hiHatCymbalsRelease = new Image("/View/DrumPad/drumGraphics/07_trans.png");
        this.drums = new ArrayList<ImageView>();
        this.drumsHeight = new ArrayList<Double>();
        this.drumsWidth = new ArrayList<Double>();
        this.shiftStatus = false;
        this.buttons = new ArrayList<Ellipse>();
    }
    
    public void start(final Stage stage) {
        try {
            this.scene = new Scene((Parent)this.drumPadPane, 962.0, 576.0);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Drum Pad");
            stage.setScene(this.scene);
            stage.setResizable(false);
            stage.setOnCloseRequest(windowEvent -> OuterTemplateController.removeDrumPadFromMemory());
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.playEnv = new Play(Play.PlayType.OVERLAPPING);
        this.drumsSetup();
        this.turnCursorOn();
        this.mouseListenerSetup();
        this.keyboardListenerSetup();
        stage.focusedProperty().addListener((observable, oldValue, newValue) -> this.isFocusLost(newValue));
    }
    
    private void drumsSetup() {
        this.drums.add(this.drum36);
        this.drums.add(this.drum45);
        this.drums.add(this.drum50);
        this.drums.add(this.drum38);
        this.drums.add(this.drum43);
        this.drums.add(this.drum51);
        this.drums.add(this.drum57);
        this.drums.add(this.drum46);
        this.drums.add(this.drum49);
        this.drums.add(this.drum44);
        for (final ImageView drum : this.drums) {
            this.drumsHeight.add(drum.getFitHeight());
            this.drumsWidth.add(drum.getFitWidth());
        }
        this.buttons.add(this.d36);
        this.buttons.add(this.d45);
        this.buttons.add(this.d50);
        this.buttons.add(this.d38);
        this.buttons.add(this.d43);
        this.buttons.add(this.d51);
        this.buttons.add(this.d57);
        this.buttons.add(this.d46);
        this.buttons.add(this.d49);
        this.buttons.add(this.d44);
    }
    
    private void mouseListenerSetup() {
        for (final Ellipse button : this.buttons) {
            button.setOnMousePressed(event -> {
                Integer midiNumber = Integer.valueOf(button.getId().substring(button.getId().length() - 2));
                this.drumReaction(midiNumber);
                if (midiNumber == 46 && this.shiftStatus) {
                    midiNumber = 42;
                }
                if (midiNumber != 44 || !this.shiftStatus) {
                    this.turnCursorOff();
                    this.playEnv.playPercussion(midiNumber, 300, UnitDurationInformation.DEFAULT_UNIT_DURATION, true);
                }
            });
            button.setOnMouseReleased(event -> this.turnCursorOn());
        }
    }
    
    private void keyboardListenerSetup() {
        this.scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SHIFT) {
                this.shiftStatus = true;
                this.drums.get(9).setImage(this.hiHatPedalPress);
                this.drums.get(7).setImage(this.hiHatCymbalsPress);
                this.d44.setCursor(Cursor.DEFAULT);
            }
        });
        this.scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SHIFT) {
                this.shiftRelease();
            }
        });
    }
    
    private void isFocusLost(final Boolean state) {
        if (!state) {
            this.shiftRelease();
        }
    }
    
    private void shiftRelease() {
        this.shiftStatus = false;
        this.drums.get(9).setImage(this.hiHatPedalRelease);
        this.drums.get(7).setImage(this.hiHatCymbalsRelease);
        this.d44.setCursor((Cursor)new ImageCursor(this.drumboot, this.drumboot.getWidth() / 2.0, this.drumboot.getHeight() / 2.0));
    }
    
    private void drumReaction(final int midiNumber) {
        int index = 0;
        for (final ImageView drum : this.drums) {
            if (midiNumber == Integer.valueOf(drum.getId().substring(drum.getId().length() - 2))) {
                if (midiNumber == 36) {
                    final Double heightNormal = this.drumsHeight.get(index);
                    final Double widthNormal = this.drumsWidth.get(index);
                    final DoubleProperty height = drum.fitHeightProperty();
                    final DoubleProperty width = drum.fitWidthProperty();
                    final ObjectProperty<Image> pict = (ObjectProperty<Image>)drum.imageProperty();
                    final Timeline pedalPress = new Timeline(new KeyFrame[] { new KeyFrame(Duration.ZERO, new KeyValue[] { new KeyValue((WritableValue)height, (Object)(heightNormal * 1.1)), new KeyValue((WritableValue)width, (Object)(widthNormal * 1.1)), new KeyValue((WritableValue)pict, (Object)this.bassDrumPress) }), new KeyFrame(new Duration(300.0), new KeyValue[] { new KeyValue((WritableValue)height, (Object)heightNormal), new KeyValue((WritableValue)width, (Object)widthNormal), new KeyValue((WritableValue)pict, (Object)this.bassDrumRelease) }) });
                    pedalPress.play();
                }
                else if (midiNumber == 44) {
                    if (!this.shiftStatus) {
                        final Double heightNormal = this.drumsHeight.get(index);
                        final Double widthNormal = this.drumsWidth.get(index);
                        final DoubleProperty height = drum.fitHeightProperty();
                        final DoubleProperty width = drum.fitWidthProperty();
                        final ObjectProperty<Image> pictPedal = (ObjectProperty<Image>)drum.imageProperty();
                        final ObjectProperty<Image> pictCymbals = (ObjectProperty<Image>)this.drums.get(7).imageProperty();
                        final Timeline pedalPress2 = new Timeline(new KeyFrame[] { new KeyFrame(Duration.ZERO, new KeyValue[] { new KeyValue((WritableValue)height, (Object)(heightNormal * 1.1)), new KeyValue((WritableValue)width, (Object)(widthNormal * 1.1)), new KeyValue((WritableValue)pictPedal, (Object)this.hiHatPedalPress), new KeyValue((WritableValue)pictCymbals, (Object)this.hiHatCymbalsPress) }), new KeyFrame(new Duration(300.0), new KeyValue[] { new KeyValue((WritableValue)height, (Object)heightNormal), new KeyValue((WritableValue)width, (Object)widthNormal), new KeyValue((WritableValue)pictPedal, (Object)this.hiHatPedalRelease), new KeyValue((WritableValue)pictCymbals, (Object)this.hiHatCymbalsRelease) }) });
                        pedalPress2.play();
                        this.resizeAnimation(7);
                    }
                }
                else if (midiNumber == 46) {
                    this.resizeAnimation(index);
                    this.resizeAnimation(9);
                }
                else {
                    this.resizeAnimation(index);
                }
            }
            ++index;
        }
    }
    
    private void resizeAnimation(final int index) {
        final ImageView drum = this.drums.get(index);
        final Double heightNormal = this.drumsHeight.get(index);
        final Double widthNormal = this.drumsWidth.get(index);
        final DoubleProperty height = drum.fitHeightProperty();
        final DoubleProperty width = drum.fitWidthProperty();
        final Timeline resize = new Timeline(new KeyFrame[] { new KeyFrame(Duration.ZERO, new KeyValue[] { new KeyValue((WritableValue)height, (Object)(heightNormal * 1.1)), new KeyValue((WritableValue)width, (Object)(widthNormal * 1.1)) }), new KeyFrame(new Duration(300.0), new KeyValue[] { new KeyValue((WritableValue)height, (Object)heightNormal), new KeyValue((WritableValue)width, (Object)widthNormal) }) });
        resize.play();
    }
    
    private void turnCursorOn() {
        this.drumPadPane.setCursor(Cursor.DEFAULT);
        this.d38.setCursor((Cursor)new ImageCursor(this.drumsticksBlack, this.drumsticksBlack.getWidth() / 2.0, this.drumsticksBlack.getHeight() / 2.0));
        this.d43.setCursor((Cursor)new ImageCursor(this.drumsticksBlack, this.drumsticksBlack.getWidth() / 2.0, this.drumsticksBlack.getHeight() / 2.0));
        this.d45.setCursor((Cursor)new ImageCursor(this.drumsticksBlack, this.drumsticksBlack.getWidth() / 2.0, this.drumsticksBlack.getHeight() / 2.0));
        this.d46.setCursor((Cursor)new ImageCursor(this.drumsticksWhite, this.drumsticksWhite.getWidth() / 2.0, this.drumsticksWhite.getHeight() / 2.0));
        this.d49.setCursor((Cursor)new ImageCursor(this.drumsticksWhite, this.drumsticksWhite.getWidth() / 2.0, this.drumsticksWhite.getHeight() / 2.0));
        this.d50.setCursor((Cursor)new ImageCursor(this.drumsticksBlack, this.drumsticksBlack.getWidth() / 2.0, this.drumsticksBlack.getHeight() / 2.0));
        this.d51.setCursor((Cursor)new ImageCursor(this.drumsticksWhite, this.drumsticksWhite.getWidth() / 2.0, this.drumsticksWhite.getHeight() / 2.0));
        this.d57.setCursor((Cursor)new ImageCursor(this.drumsticksWhite, this.drumsticksWhite.getWidth() / 2.0, this.drumsticksWhite.getHeight() / 2.0));
        this.d36.setCursor((Cursor)new ImageCursor(this.drumboot, this.drumboot.getWidth() / 2.0, this.drumboot.getHeight() / 2.0));
        if (!this.shiftStatus) {
            this.d44.setCursor((Cursor)new ImageCursor(this.drumboot, this.drumboot.getWidth() / 2.0, this.drumboot.getHeight() / 2.0));
        }
    }
    
    private void turnCursorOff() {
        this.drumPadPane.setCursor(Cursor.NONE);
        this.d38.setCursor(Cursor.NONE);
        this.d43.setCursor(Cursor.NONE);
        this.d45.setCursor(Cursor.NONE);
        this.d46.setCursor(Cursor.NONE);
        this.d49.setCursor(Cursor.NONE);
        this.d50.setCursor(Cursor.NONE);
        this.d51.setCursor(Cursor.NONE);
        this.d57.setCursor(Cursor.NONE);
        this.d36.setCursor(Cursor.NONE);
        if (this.shiftStatus) {
            this.d44.setCursor(Cursor.DEFAULT);
        }
        else {
            this.d44.setCursor(Cursor.NONE);
        }
    }
    
    public void drumAnimate(final ArrayList<Integer> midiList) {
        for (Integer midiNumber : midiList) {
            if (midiNumber == 42) {
                midiNumber = 44;
            }
            this.drumReaction(midiNumber);
        }
    }
}
