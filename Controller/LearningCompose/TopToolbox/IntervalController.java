// 
// Decompiled by Procyon v0.5.36
// 

package Controller.LearningCompose.TopToolbox;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import java.util.Arrays;
import java.util.ArrayList;
import Model.Note.Intervals.Interval;
import java.util.Collection;
import javafx.scene.control.Toggle;
import Model.Note.Intervals.IntervalMap;
import com.google.common.collect.HashBiMap;
import java.util.HashMap;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import com.google.common.collect.BiMap;
import java.util.List;
import java.util.Map;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class IntervalController
{
    @FXML
    private HBox intervalsAboveHBox;
    @FXML
    private HBox intervalsBelowHBox;
    @FXML
    private VBox header;
    private Map<String, List<String>> intervalToVariants;
    private BiMap<ToggleButton, ComboBox<String>> buttonToCombo;
    private ToggleGroup toggleGroup;
    private static final String INTERVAL_FORMAT = "%s %s";
    private TopToolboxController topToolboxController;
    private static final Integer MAX_ABOVE_CHILDREN;
    private String currentIntervalType;
    private String currentVariant;
    
    public IntervalController() {
        this.intervalToVariants = new HashMap<String, List<String>>();
        this.buttonToCombo = (BiMap<ToggleButton, ComboBox<String>>)HashBiMap.create();
        this.toggleGroup = new ToggleGroup();
    }
    
    public void initialize() {
        IntervalMap.getAllIntervals().forEach(interval -> this.updateMap(interval));
        this.intervalToVariants.forEach((intervalType, variants) -> this.setUpIntervalGroup(intervalType, variants));
        final ToggleButton toggleButton = (ToggleButton)this.toggleGroup.getToggles().get(0);
        this.toggleGroup.selectToggle((Toggle)toggleButton);
        this.addToggleGroupListener();
        this.currentIntervalType = toggleButton.getText();
        this.currentVariant = (String)this.buttonToCombo.get(toggleButton).getSelectionModel().getSelectedItem();
    }
    
    private void setUpIntervalGroup(final String intervalType, final List<String> variants) {
        final HBox intervalGroup = new HBox();
        final ToggleButton intervalToggle = new ToggleButton();
        intervalToggle.setId("exploreBtn");
        intervalToggle.setText(intervalType);
        intervalToggle.setToggleGroup(this.toggleGroup);
        intervalGroup.getChildren().add((Object)intervalToggle);
        final ComboBox<String> variantsCombo = new ComboBox<String>() {
            {
                this.getItems().addAll((Collection)variants);
                this.getSelectionModel().select(0);
            }
        };
        variantsCombo.setId("exploreCombo");
        this.buttonToCombo.put(intervalToggle, variantsCombo);
        intervalGroup.getChildren().add((Object)variantsCombo);
        if (this.intervalsAboveHBox.getChildren().size() < IntervalController.MAX_ABOVE_CHILDREN) {
            this.intervalsAboveHBox.getChildren().add((Object)intervalGroup);
        }
        else {
            this.intervalsBelowHBox.getChildren().add((Object)intervalGroup);
        }
        this.addVariantComboListener(variantsCombo);
    }
    
    public void setTopToolboxController(final TopToolboxController topToolboxController) {
        this.topToolboxController = topToolboxController;
    }
    
    private void updateMap(final Interval interval) {
        final String name = interval.getPrettyIntervalName();
        final String intervalType = name.substring(0, name.indexOf(" "));
        final String variant = name.substring(name.indexOf(" ") + 1, name.length());
        if (this.intervalToVariants.containsKey(intervalType)) {
            final List<String> variants = new ArrayList<String>();
            variants.addAll(this.intervalToVariants.get(intervalType));
            variants.add(variant);
            this.intervalToVariants.replace(intervalType, variants);
        }
        else {
            this.intervalToVariants.put(intervalType, Arrays.asList(variant));
        }
    }
    
    private void addVariantComboListener(final ComboBox<String> variantsCombo) {
        variantsCombo.valueProperty().addListener((ChangeListener)new ChangeListener<String>() {
            public void changed(final ObservableValue ov, final String oldValue, final String newValue) {
                IntervalController.this.toggleGroup.selectToggle((Toggle)IntervalController.this.buttonToCombo.inverse().get(variantsCombo));
                IntervalController.this.topToolboxController.placeNote(IntervalController.this.getIntervalName());
            }
        });
    }
    
    private void addToggleGroupListener() {
        this.toggleGroup.selectedToggleProperty().addListener((ChangeListener)new ChangeListener<Toggle>() {
            public void changed(final ObservableValue ov, final Toggle oldToggle, final Toggle newToggle) {
                IntervalController.this.topToolboxController.placeNote(IntervalController.this.getIntervalName());
            }
        });
    }
    
    private String getIntervalName() {
        final ToggleButton toggleButton = (ToggleButton)this.toggleGroup.getSelectedToggle();
        if (toggleButton != null) {
            this.currentIntervalType = toggleButton.getText();
            this.currentVariant = (String)this.buttonToCombo.get(toggleButton).getSelectionModel().getSelectedItem();
        }
        else {
            this.toggleGroup.getToggles().stream().filter(toggle -> toggle.getText().equals(this.currentIntervalType)).forEach(toggle -> toggle.setSelected(true));
        }
        return String.format("%s %s", this.currentIntervalType, this.currentVariant);
    }
    
    static {
        MAX_ABOVE_CHILDREN = 3;
    }
}
