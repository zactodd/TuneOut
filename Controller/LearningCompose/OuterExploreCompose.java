

package Controller.LearningCompose;

import java.util.TreeMap;
import java.util.Iterator;
import javafx.scene.image.ImageView;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCode;
import java.util.ArrayList;
import Model.Play.Play;
import javafx.scene.input.KeyCombination;
import Controller.LearningCompose.SheetElement.SheetElement;
import java.util.List;
import Controller.LearningCompose.SheetElement.SheetElementFactory;
import javafx.scene.ImageCursor;
import Controller.LearningCompose.Toolbox.Toolbox;
import Controller.LearningCompose.TopToolbox.TopToolboxController;
import Controller.LearningCompose.TopToolbox.TopShowEditPercussionController;
import Controller.LearningCompose.TopToolbox.TopShowEditController;
import Environment.Environment;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public abstract class OuterExploreCompose
{
    @FXML
    public GridPane outerGridPane;
    @FXML
    protected AnchorPane outerExploreAnchor;
    protected Environment env;
    protected SheetController sheetController;
    public TopShowEditController topShowEditController;
    public TopShowEditPercussionController topShowEditPercussionController;
    protected TopToolboxController topToolboxController;
    protected Toolbox toolboxController;
    protected ImageCursor unavailCursor;
    protected ImageCursor dropElementCursor;
    protected SheetElementFactory sheetElementFactory;
    private List<SheetElement> sheetElements;
    protected String type;
    protected SheetElement signature;
    private String inversion;
    private String arrangement;
    private ElementDuration elementDuration;
    private PercussionSheetInfo percussionInfo;
    private Integer numberLoops;
    private final KeyCombination keyShiftTab;
    
    public OuterExploreCompose() {
        this.env = new Environment(new Play(Play.PlayType.REPLACE));
        this.sheetElements = new ArrayList<SheetElement>();
        this.type = "";
        this.inversion = "";
        this.arrangement = "";
        this.numberLoops = 1;
        this.keyShiftTab = (KeyCombination)new KeyCodeCombination(KeyCode.TAB, new KeyCombination.Modifier[] { KeyCombination.SHIFT_ANY });
    }
    
    public String getInversion() {
        return this.inversion;
    }
    
    public String getArrangement() {
        return this.arrangement;
    }
    
    public void setInversion(final String inversion) {
        this.inversion = inversion;
    }
    
    public void setArrangement(final String arrangement) {
        this.arrangement = arrangement;
    }
    
    public String getType() {
        return this.type;
    }
    
    public void setType(final String type) {
        this.type = type;
    }
    
    public SheetElement getSignature() {
        return this.signature;
    }
    
    public void setSignature(final SheetElement signature) {
        this.signature = signature;
    }
    
    public SheetElementFactory getSheetElementFactory() {
        return this.sheetElementFactory;
    }
    
    public void setDropElementCursor(final ImageCursor dropElementCursor) {
        this.dropElementCursor = dropElementCursor;
    }
    
    public ElementDuration getElementDuration() {
        return this.elementDuration;
    }
    
    public PercussionSheetInfo getPercussionInfo() {
        return this.percussionInfo;
    }
    
    public void setElementDuration(final ElementDuration elementDuration) {
        this.elementDuration = elementDuration;
        this.dropElementCursor = new ImageCursor(elementDuration.getCursorImage());
    }
    
    protected void setupHandlers() {
        final EventHandler<KeyEvent> event = (EventHandler<KeyEvent>)new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent event) {
                if (event.getCode() == KeyCode.UP) {
                    if (OuterExploreCompose.this.sheetController != null) {
                        OuterExploreCompose.this.sheetController.moveNote("up");
                    }
                }
                else if (event.getCode() == KeyCode.DOWN) {
                    if (OuterExploreCompose.this.sheetController != null) {
                        OuterExploreCompose.this.sheetController.moveNote("down");
                    }
                }
                else if (event.getCode() == KeyCode.DELETE) {
                    if (OuterExploreCompose.this.getSelectedElement() != null) {
                        OuterExploreCompose.this.removeElement(OuterExploreCompose.this.getSelectedElement());
                    }
                }
                else if (event.getCode() == KeyCode.PERIOD) {
                    OuterExploreCompose.this.topToolboxController.toggleDots();
                }
                else if (event.getCode() == KeyCode.RIGHT) {
                    if (OuterExploreCompose.this.sheetController.getComposeMode()) {
                        OuterExploreCompose.this.selectNext();
                    }
                }
                else if (event.getCode() == KeyCode.LEFT) {
                    if (OuterExploreCompose.this.sheetController.getComposeMode()) {
                        OuterExploreCompose.this.selectPrevious();
                    }
                }
                else if (event.getCode() == KeyCode.ESCAPE) {
                    OuterExploreCompose.this.clearSelectedElement();
                }
            }
        };
        this.outerGridPane.setOnKeyPressed((EventHandler)event);
        this.sheetController.stave.setOnKeyPressed((EventHandler)event);
    }
    
    protected void requestFocus() {
        Platform.runLater((Runnable)new Runnable() {
            @Override
            public void run() {
                OuterExploreCompose.this.outerGridPane.requestFocus();
            }
        });
    }
    
    public List<SheetElement> getSheetElements() {
        return this.sheetElements;
    }
    
    public void addElementToList(final SheetElement element) {
        this.sheetElements.add(element);
    }
    
    protected void removeElement(final SheetElement element) {
        for (final ImageView image : element.getElementImages().values()) {
            this.sheetController.stave.getChildren().remove((Object)image);
        }
        this.sheetElements.remove(element);
    }
    
    public SheetController getSheetController() {
        return this.sheetController;
    }
    
    public Toolbox getToolboxController() {
        return this.toolboxController;
    }
    
    public TopToolboxController getTopToolboxController() {
        return this.topToolboxController;
    }
    
    public void setNumberLoops(final Integer numberLoops) {
        this.numberLoops = numberLoops;
    }
    
    public Integer getNumberLoops() {
        return this.numberLoops;
    }
    
    public SheetElement getSelectedElement() {
        if (!this.sheetElements.isEmpty()) {
            for (final SheetElement element : this.sheetElements) {
                if (element.getSelected()) {
                    return element;
                }
            }
        }
        return null;
    }
    
    public void setSelectedElement(final SheetElement elementToSelect) {
        if (this.sheetElements.contains(elementToSelect)) {
            for (final SheetElement element : this.sheetElements) {
                element.setSelected(false);
                element.removeSelectedBorder();
            }
        }
        elementToSelect.setSelected(true);
        elementToSelect.setSelectedBorder();
    }
    
    private void selectNext() {
        final TreeMap<Integer, SheetElement> sameAtX = new TreeMap<Integer, SheetElement>();
        if (this.getSelectedElement() != null) {
            final SheetElement selectedElement = this.getSelectedElement();
            final Integer selectedElementMidi = selectedElement.getRootNote().getMidiNumber();
            sameAtX.put(selectedElementMidi, selectedElement);
            for (final SheetElement element : this.sheetElements) {
                if (element.getPlacedX().equals(selectedElement.getPlacedX())) {
                    sameAtX.put(element.getRootNote().getMidiNumber(), element);
                }
            }
            if (sameAtX.size() > 1) {
                Integer nextElement;
                if (selectedElementMidi.equals(sameAtX.firstKey())) {
                    nextElement = sameAtX.lastKey();
                }
                else {
                    nextElement = sameAtX.lowerKey(selectedElementMidi);
                }
                this.setSelectedElement(sameAtX.get(nextElement));
                this.syncTopToolbox(sameAtX.get(nextElement));
            }
        }
    }
    
    private void selectPrevious() {
        final TreeMap<Integer, SheetElement> sameAtX = new TreeMap<Integer, SheetElement>();
        if (this.getSelectedElement() != null) {
            final SheetElement selectedElement = this.getSelectedElement();
            final Integer selectedElementMidi = selectedElement.getRootNote().getMidiNumber();
            sameAtX.put(selectedElementMidi, selectedElement);
            for (final SheetElement element : this.sheetElements) {
                if (element.getPlacedX().equals(selectedElement.getPlacedX())) {
                    sameAtX.put(element.getRootNote().getMidiNumber(), element);
                }
            }
            if (sameAtX.size() > 1) {
                Integer prevElement;
                if (selectedElementMidi.equals(sameAtX.lastKey())) {
                    prevElement = sameAtX.firstKey();
                }
                else {
                    prevElement = sameAtX.higherKey(selectedElementMidi);
                }
                this.setSelectedElement(sameAtX.get(prevElement));
                this.syncTopToolbox(sameAtX.get(prevElement));
            }
        }
    }
    
    public void clearSelectedElement() {
        for (final SheetElement element : this.sheetElements) {
            element.setSelected(false);
            element.removeSelectedBorder();
        }
    }
    
    public void syncTopToolbox(final SheetElement element) {
        this.topToolboxController.syncTopToolbox(element.getRootNote(), element.getAccidental(), element.getElementDuration(), element.getPercussionInfo());
    }
    
    public void setPercussionInfo(final PercussionSheetInfo percussionInfo) {
        this.percussionInfo = percussionInfo;
        this.dropElementCursor = new ImageCursor(percussionInfo.getCursorImage());
    }
    
    public abstract void playSheet();
    
    public void stopSheetPlaying() {
        this.env.getPlay().stopAllPlayingThreads();
    }
    
    public String translateNoteHint(final String noteWithOctave) {
        return noteWithOctave;
    }
    
    public Double getNextX() {
        final Double gap = SheetController.GAP_BETWEEN_NOTES;
        final Double maxGap = 50.0 * gap;
        Double freeX = 0.0;
        for (double x = 210.0; x < maxGap; x += gap) {
            Boolean elementInSlot = false;
            for (final SheetElement element : this.getSheetElements()) {
                if (element.getPlacedX().equals(x)) {
                    elementInSlot = true;
                }
            }
            if (!elementInSlot) {
                freeX = x;
                break;
            }
        }
        return freeX;
    }
}
