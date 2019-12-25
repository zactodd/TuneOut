// 
// Decompiled by Procyon v0.5.36
// 

package Controller.LearningCompose;

import Controller.LearningCompose.SheetElement.SheetComposePercussion;
import java.util.Set;
import java.util.List;
import Controller.LearningCompose.SheetElement.SheetElement;
import Controller.OuterTemplateController;
import java.util.Iterator;
import Model.Note.unitDuration.UnitDurationInformation;
import Model.Note.Settings.TempoInformation;
import java.io.IOException;
import Controller.LearningCompose.SheetElement.SheetElementFactory;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import Controller.LearningCompose.TopToolbox.TopToolboxController;
import Controller.LearningCompose.TopToolbox.TopShowEditPercussionController;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.fxml.FXMLLoader;
import Controller.LearningCompose.Toolbox.ToolboxComposePercussionController;
import Model.Percussion.PercussionLoop;

public class OuterComposePercussionController extends OuterExploreCompose
{
    private static final Integer REST_DRUM;
    private PercussionLoop percussionLoop;
    private static final String REST_STRING = "-";
    private static final String HIT_STRING = "X";
    private static final String ADD_BEAT_FORMAT = "%s%s";
    private static final Double INTITAL_X;
    private static final Double INCREMENT_X;
    
    public OuterComposePercussionController() {
        this.percussionLoop = new PercussionLoop();
    }
    
    public void initialize() throws IOException {
        (this.toolboxController = new ToolboxComposePercussionController()).setOuterExploreController(this);
        final FXMLLoader sheetLoader = new FXMLLoader();
        sheetLoader.setLocation(this.getClass().getResource("/View/LearningCompose/sheet.fxml"));
        final ScrollPane sheetAnchorPane = (ScrollPane)sheetLoader.load();
        (this.sheetController = (SheetController)sheetLoader.getController()).setOuterExploreController(this);
        this.outerGridPane.add((Node)sheetAnchorPane, 0, 1);
        GridPane.setColumnSpan((Node)sheetAnchorPane, Integer.valueOf(2));
        final FXMLLoader topShowEditLoader = new FXMLLoader();
        topShowEditLoader.setLocation(this.getClass().getResource("/View/LearningCompose/TopToolbox/topShowEditPercussion.fxml"));
        final AnchorPane topShowEditAnchorPane = (AnchorPane)topShowEditLoader.load();
        (this.topShowEditPercussionController = (TopShowEditPercussionController)topShowEditLoader.getController()).setUp(this);
        final FXMLLoader noteLoader = new FXMLLoader();
        noteLoader.setLocation(this.getClass().getResource("/View/LearningCompose/TopToolbox/topToolbox.fxml"));
        final AnchorPane noteGridPane = (AnchorPane)noteLoader.load();
        (this.topToolboxController = (TopToolboxController)noteLoader.getController()).initializeTopToolBoxController(this);
        final HBox topWideHbox = new HBox();
        topWideHbox.setAlignment(Pos.TOP_CENTER);
        topWideHbox.getChildren().addAll((Object[])new Node[] { (Node)topShowEditAnchorPane, (Node)noteGridPane });
        this.outerGridPane.add((Node)topWideHbox, 0, 0);
        GridPane.setColumnSpan((Node)topWideHbox, Integer.valueOf(2));
        GridPane.setHalignment((Node)noteGridPane, HPos.CENTER);
        this.toolboxController.setTopToolboxController(this.topToolboxController);
        final Image cursorUnavailImg = new Image("View/LearningCompose/graphic/Cursors/cursor_unavail.png");
        this.unavailCursor = new ImageCursor(cursorUnavailImg);
        this.dropElementCursor = new ImageCursor();
        this.sheetElementFactory = new SheetElementFactory(this);
        this.setupHandlers();
        this.topToolboxController.showPercussionOptions();
        final PercussionSheetInfo defaultPercussion = this.topToolboxController.getPercussion();
        this.setPercussionInfo(defaultPercussion);
        this.sheetController.clearBtn.setDisable(false);
        this.sheetController.playBtn.setVisible(true);
        this.sheetController.playBtn.setManaged(true);
        this.sheetController.stopBtn.setVisible(true);
        this.sheetController.stopBtn.setManaged(true);
        final Image percussionClef = new Image("View/LearningCompose/graphic/PercussionClef.png");
        this.sheetController.clef.setImage(percussionClef);
        this.setElementDuration(ElementDurationMap.ELEMENT_DURATIONS.get("quaverNote"));
    }
    
    @Override
    public void playSheet() {
        this.percussionLoopFromSheet();
        final PercussionLoop newPercussionLoop = this.getPercussionLoopFromSheet();
        newPercussionLoop.setLoopNumber(this.getNumberLoops());
        this.env.getPlay().playPercussion(newPercussionLoop.getEvents(), TempoInformation.getTempInBpm(), UnitDurationInformation.getUnitDuration());
    }
    
    @Override
    public String translateNoteHint(final String noteWithOctave) {
        for (final PercussionSheetInfo info : PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.values()) {
            if (noteWithOctave.equals(info.getPositionOnStave().getNoteWithOctave()) && info.getCategory().equals(this.getPercussionInfo().getCategory())) {
                return info.getPercussion().getInstrument();
            }
        }
        return "No Instrument";
    }
    
    public void setOuterTemplateController(final OuterTemplateController outerTemplateController) {
        this.topShowEditPercussionController.setOuterTemplateController(outerTemplateController);
    }
    
    private void percussionLoopFromSheet() {
        this.percussionLoop = new PercussionLoop();
        String rests = "";
        final List<SheetElement> sortedElements = this.getSheetElements();
        sortedElements.sort((e1, e2) -> e1.getPlacedX().compareTo(e2.getPlacedX()));
        Double lastElementPos = 0.0;
        for (final SheetElement element : sortedElements) {
            final Double currentElementPos = element.getPlacedX();
            if (!lastElementPos.equals(currentElementPos) && lastElementPos != 0.0) {
                rests += "-";
            }
            Integer midi = element.getPercussionInfo().getPercussion().getMidi();
            String beat = String.format("%s%s", rests, "X");
            if (midi.equals(-1)) {
                beat = String.format("%s%s", rests, "-");
                midi = OuterComposePercussionController.REST_DRUM;
            }
            this.percussionLoop.addLoop(midi, beat);
            lastElementPos = currentElementPos;
        }
    }
    
    public void clearStave() {
        this.sheetController.clearStave();
    }
    
    public PercussionLoop getPercussionLoopFromSheet() {
        this.percussionLoop = new PercussionLoop();
        this.percussionLoopFromSheet();
        return this.percussionLoop;
    }
    
    public void placeLoopOnSheet(final PercussionLoop percussionLoop) {
        Double runningX = OuterComposePercussionController.INTITAL_X;
        this.clearStave();
        for (final Set<Integer> point : percussionLoop.getEvents()) {
            for (final Integer instrument : point) {
                final SheetComposePercussion newSheetElement = new SheetComposePercussion(this);
                newSheetElement.setPercussionInfo(PercussionSheetInfoMap.getInfoByMidi(instrument));
                newSheetElement.placeElements(runningX);
                this.getSheetElements().add(newSheetElement);
            }
            runningX += OuterComposePercussionController.INCREMENT_X;
        }
    }
    
    static {
        REST_DRUM = 35;
        INTITAL_X = 210.0;
        INCREMENT_X = 70.0;
    }
}
