// 
// Decompiled by Procyon v0.5.36
// 

package Controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TreeTableColumn;
import Model.Settings.StyleMap;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import java.util.Arrays;
import java.util.List;
import Model.File.CommandHelpParser;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.fxml.FXML;
import Model.command.CommandData;
import javafx.scene.control.TreeTableView;

class DSLReferenceCardController
{
    @FXML
    private TreeTableView<CommandData> dslTree;
    @FXML
    private TextArea descripText;
    @FXML
    private TextField commandTextField;
    @FXML
    private Label commandLabel;
    private CommandLineController commandLine;
    private Stage stage;
    private TitledPane pane;
    private TreeItem<CommandData> root;
    private CommandHelpParser commandFile;
    private List<String> categoryList;
    
    DSLReferenceCardController() {
        this.commandFile = new CommandHelpParser();
        this.categoryList = Arrays.asList("Application", "Information", "Set", "Play", "Tutor");
    }
    
    public void start(final Stage stage, final CommandLineController commandLine) {
        this.commandFile.start();
        this.commandLine = commandLine;
        this.stage = stage;
        try {
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/View/dslReferenceCard.fxml"));
            loader.setController((Object)this);
            final VBox page = (VBox)loader.load();
            this.initializeTree();
            final Scene scene = new Scene((Parent)page, 620.0, 600.0);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            stage.getScene().getStylesheets().clear();
            stage.getScene().getStylesheets().add((Object)StyleMap.getCurrentStyle());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void initializeTree() {
        (this.root = (TreeItem<CommandData>)new TreeItem((Object)new CommandData("Parent Root", "", "", "", ""))).setExpanded(false);
        this.dslTree.setRoot((TreeItem)this.root);
        this.dslTree.setShowRoot(false);
        this.dslTree.setEditable(false);
        final TreeItem<CommandData> applicationRoot = (TreeItem<CommandData>)new TreeItem((Object)new CommandData("Application", "", "", "", "Application"));
        final TreeItem<CommandData> informationRoot = (TreeItem<CommandData>)new TreeItem((Object)new CommandData("Information", "", "", "", "Information"));
        final TreeItem<CommandData> setRoot = (TreeItem<CommandData>)new TreeItem((Object)new CommandData("Set", "", "", "", "Set"));
        final TreeItem<CommandData> playRoot = (TreeItem<CommandData>)new TreeItem((Object)new CommandData("Play", "", "", "", "Play"));
        final TreeItem<CommandData> tutRoot = (TreeItem<CommandData>)new TreeItem((Object)new CommandData("Tutor", "", "", "", "Tutor"));
        applicationRoot.setExpanded(false);
        informationRoot.setExpanded(false);
        setRoot.setExpanded(false);
        playRoot.setExpanded(false);
        tutRoot.setExpanded(false);
        this.root.getChildren().add((Object)applicationRoot);
        this.root.getChildren().add((Object)informationRoot);
        this.root.getChildren().add((Object)setRoot);
        this.root.getChildren().add((Object)playRoot);
        this.root.getChildren().add((Object)tutRoot);
        final List<CommandData> appCommandList = this.commandFile.getAppList();
        final List<CommandData> infoCommandList = this.commandFile.getInfoList();
        final List<CommandData> setCommandList = this.commandFile.getSetList();
        final List<CommandData> playCommandList = this.commandFile.getPlayList();
        final List<CommandData> tutCommandList = this.commandFile.getTutList();
        appCommandList.stream().forEach(commandData -> applicationRoot.getChildren().add((Object)new TreeItem((Object)commandData)));
        infoCommandList.stream().forEach(commandData -> informationRoot.getChildren().add((Object)new TreeItem((Object)commandData)));
        setCommandList.stream().forEach(commandData -> setRoot.getChildren().add((Object)new TreeItem((Object)commandData)));
        playCommandList.stream().forEach(commandData -> playRoot.getChildren().add((Object)new TreeItem((Object)commandData)));
        tutCommandList.stream().forEach(commandData -> tutRoot.getChildren().add((Object)new TreeItem((Object)commandData)));
        final TreeTableColumn<CommandData, String> columnCommand = (TreeTableColumn<CommandData, String>)new TreeTableColumn("Command");
        columnCommand.setPrefWidth(200.0);
        columnCommand.setResizable(false);
        columnCommand.setCellValueFactory(param -> new ReadOnlyStringWrapper(((CommandData)param.getValue().getValue()).getCommandInput()));
        final TreeTableColumn<CommandData, String> columnDescription = (TreeTableColumn<CommandData, String>)new TreeTableColumn("Description");
        columnDescription.setPrefWidth(400.0);
        columnDescription.setResizable(false);
        columnDescription.setSortable(false);
        columnDescription.setCellValueFactory(param -> new ReadOnlyStringWrapper(((CommandData)param.getValue().getValue()).getDescription()));
        this.dslTree.getColumns().setAll((Object[])new TreeTableColumn[] { columnCommand, columnDescription });
    }
    
    @FXML
    private void commandClicked() {
        this.dslTree.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 1) {
                final TreeItem<CommandData> selected = (TreeItem<CommandData>)this.dslTree.getSelectionModel().getSelectedItem();
                this.checkIfCategory(selected);
            }
            else {
                this.copyToCommandLine();
            }
        });
    }
    
    @FXML
    private void commandSelected() {
        final TreeItem<CommandData> selected = (TreeItem<CommandData>)this.dslTree.getSelectionModel().getSelectedItem();
        this.checkIfCategory(selected);
        this.dslTree.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                this.isExpanded(selected);
                this.copyToCommandLine();
            }
        });
    }
    
    @FXML
    private void appendText(final TreeItem<CommandData> selected) {
        final String command = ((CommandData)selected.getValue()).getName();
        this.commandLabel.setText(command);
        String description = ((CommandData)selected.getValue()).getDescription();
        description += "\n";
        final String details = ((CommandData)selected.getValue()).getDetails();
        this.descripText.setText(description + details);
    }
    
    @FXML
    private void checkIfCategory(final TreeItem<CommandData> selected) {
        if (selected != null) {
            final String categoryDescription = ((CommandData)selected.getValue()).getDescription();
            if (!categoryDescription.equals("")) {
                this.appendText(selected);
            }
        }
    }
    
    @FXML
    private void copyToCommandLine() {
        final TreeItem<CommandData> selected = (TreeItem<CommandData>)this.dslTree.getSelectionModel().getSelectedItem();
        if (selected != null) {
            final String commandText = ((CommandData)selected.getValue()).getCommandInput();
            if (!this.categoryList.contains(commandText)) {
                this.commandLine.copyCommand(commandText);
            }
        }
        else {
            this.commandLine.copyCommand("help()");
        }
    }
    
    private void isExpanded(final TreeItem<CommandData> column) {
        if (column.isExpanded()) {
            column.setExpanded(false);
        }
        else {
            column.setExpanded(true);
        }
    }
}
