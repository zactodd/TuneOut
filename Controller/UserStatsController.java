

package Controller;

import Controller.Charts.TutorUsage;
import Controller.Charts.PerformanceOverTime;
import Controller.Charts.PerformanceAcrossTutors;
import java.util.HashMap;
import java.time.temporal.TemporalAccessor;
import javafx.scene.control.TableCell;
import java.time.temporal.Temporal;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert;
import java.util.Collections;
import Model.File.TuneOutTutorFile;
import java.io.File;
import Controller.OpenAndSave.FileInformation;
import java.util.Set;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import javafx.collections.FXCollections;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.util.Callback;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Collection;
import java.util.ArrayList;
import Model.Tutor.RecordedTutorStats;
import javafx.scene.Node;
import java.util.Iterator;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import Model.Settings.StyleMap;
import javafx.scene.Parent;
import Controller.Charts.AbstractChart;
import java.util.Map;
import java.util.List;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.time.LocalDateTime;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import Model.Tutor.TutorSession;
import javafx.scene.control.TableView;
import javafx.scene.control.DatePicker;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class UserStatsController
{
    @FXML
    public ComboBox<String> userCombo;
    @FXML
    public ComboBox<String> tutorCombo;
    @FXML
    public DatePicker dateFrom;
    @FXML
    public DatePicker dateTo;
    @FXML
    private TableView<TutorSession> tableUserstats;
    @FXML
    public TableColumn<ObservableList<TutorSession>, String> userCol;
    @FXML
    public TableColumn<ObservableList<TutorSession>, LocalDateTime> dateCol;
    @FXML
    public TableColumn<ObservableList<TutorSession>, String> tutorCol;
    @FXML
    public TableColumn<ObservableList<TutorSession>, Integer> numQuestionsCol;
    @FXML
    public TableColumn<ObservableList<TutorSession>, Integer> correctCol;
    @FXML
    public TableColumn<ObservableList<TutorSession>, Integer> incorrectCol;
    @FXML
    public TableColumn<ObservableList<TutorSession>, Double> precentCol;
    @FXML
    public TableColumn<ObservableList<TutorSession>, Boolean> importedCol;
    @FXML
    private VBox userStatsVbox;
    @FXML
    private AnchorPane graphPane;
    @FXML
    private ComboBox graphCombo;
    private OuterTemplateController main;
    private Stage stage;
    private Scene scene;
    private final int stageWidth = 630;
    private final int stageHeight = 600;
    private List<TutorSession> allTutorData;
    private ObservableList<TutorSession> filteredTutorData;
    private static final long MONTHS_BEFORE_NOW_IN_DATE_PICKER = 1L;
    private static final Map<String, AbstractChart> graphMap;
    private static final Integer ALLOWED_GRAPH_CHILDREN;
    
    public void initializeController(final OuterTemplateController main) {
        if (this.stage == null) {
            this.main = main;
            (this.stage = new Stage()).setTitle("User Stats");
            if (this.scene == null) {
                this.scene = new Scene((Parent)this.userStatsVbox, 630.0, 600.0);
            }
            this.stage.setMinHeight(600.0);
            this.stage.setMinWidth(630.0);
            this.stage.setScene(this.scene);
            this.stage.getScene().getStylesheets().clear();
            this.scene.getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
            this.scene.getStylesheets().add((Object)this.getClass().getResource("/View/chart.css").toExternalForm());
            this.stage.show();
        }
        else if (!this.stage.isShowing()) {
            this.stage.show();
        }
        this.initialiseTableView();
        this.initialiseGraphView();
        this.initialiseFilters();
        this.updateFilteredData();
        this.setUpEventHandlers();
    }
    
    private void setUpEventHandlers() {
        final EventHandler<ActionEvent> changeFilter = (EventHandler<ActionEvent>)new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                UserStatsController.this.updateFilteredData();
            }
        };
        this.userCombo.setOnAction((EventHandler)changeFilter);
        this.tutorCombo.setOnAction((EventHandler)changeFilter);
        this.dateFrom.setOnAction((EventHandler)changeFilter);
        this.dateTo.setOnAction((EventHandler)changeFilter);
    }
    
    public void updateCss() {
        this.stage.getScene().getStylesheets().clear();
        this.stage.getScene().getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
        this.stage.getScene().getStylesheets().add((Object)this.getClass().getResource("/View/chart.css").toExternalForm());
    }
    
    private void initialiseGraphView() {
        this.graphCombo.getItems().clear();
        for (final Map.Entry<String, AbstractChart> entry : UserStatsController.graphMap.entrySet()) {
            this.graphCombo.getItems().add((Object)entry.getKey());
        }
        this.graphCombo.getSelectionModel().select(0);
        this.updateGraph();
    }
    
    @FXML
    private void updateGraph() {
        this.graphPane.getChildren().clear();
        String chartType;
        if (this.graphCombo.getValue() != null) {
            chartType = this.graphCombo.getValue().toString();
        }
        else {
            chartType = "Performance Across Tutors";
        }
        final Node chartNode = (Node)UserStatsController.graphMap.get(chartType).update(this.filteredTutorData);
        this.graphPane.getChildren().add((Object)chartNode);
        AnchorPane.setBottomAnchor(chartNode, Double.valueOf(0.0));
        AnchorPane.setLeftAnchor(chartNode, Double.valueOf(0.0));
        AnchorPane.setRightAnchor(chartNode, Double.valueOf(0.0));
        AnchorPane.setTopAnchor(chartNode, Double.valueOf(0.0));
    }
    
    public void initialiseTableView() {
        this.allTutorData = new ArrayList<TutorSession>(RecordedTutorStats.getTutorSessionData());
        this.userCol.setCellValueFactory((Callback)new PropertyValueFactory("user"));
        this.dateCol.setCellValueFactory((Callback)new PropertyValueFactory("dateTime"));
        final DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        this.dateCol.setCellFactory((Callback)getDateCell(format));
        this.tutorCol.setCellValueFactory((Callback)new PropertyValueFactory("tutorType"));
        this.tutorCol.setSortType(TableColumn.SortType.ASCENDING);
        this.numQuestionsCol.setCellValueFactory((Callback)new PropertyValueFactory("numQuestions"));
        this.correctCol.setCellValueFactory((Callback)new PropertyValueFactory("numCorrect"));
        this.incorrectCol.setCellValueFactory((Callback)new PropertyValueFactory("numIncorrect"));
        this.precentCol.setCellValueFactory((Callback)new PropertyValueFactory("percentCorrect"));
        this.importedCol.setCellValueFactory((Callback)new PropertyValueFactory("imported"));
        this.filteredTutorData = (ObservableList<TutorSession>)FXCollections.observableArrayList((Collection)this.allTutorData);
        this.tableUserstats.setItems((ObservableList)this.filteredTutorData);
        this.tableUserstats.getSortOrder().add((Object)this.tutorCol);
    }
    
    private void initialiseFilters() {
        if (!this.userCombo.getItems().isEmpty()) {
            final Iterator<String> itr = (Iterator<String>)this.userCombo.getItems().iterator();
            while (itr.hasNext()) {
                final String item = itr.next();
                if (!item.equals("Default User")) {
                    itr.remove();
                }
            }
        }
        final Set<String> users = new LinkedHashSet<String>((Collection<? extends String>)this.userCombo.getItems());
        users.add("Default User");
        users.add(this.main.userManager.getCurrentUser().getUserName());
        for (final TutorSession session : this.allTutorData) {
            if (session.getImported()) {
                users.add(session.getUser());
            }
        }
        users.removeAll((Collection<?>)this.userCombo.getItems());
        this.userCombo.getItems().addAll((Collection)users);
        this.userCombo.setValue((Object)this.main.userManager.getCurrentUser().getUserName());
        if (!this.tutorCombo.getItems().isEmpty()) {
            final Iterator<String> itr2 = (Iterator<String>)this.tutorCombo.getItems().iterator();
            while (itr2.hasNext()) {
                final String item2 = itr2.next();
                if (!item2.equals("All")) {
                    itr2.remove();
                }
            }
        }
        final Set<String> tutors = new LinkedHashSet<String>((Collection<? extends String>)this.tutorCombo.getItems());
        tutors.add("All");
        for (final TutorSession session2 : this.allTutorData) {
            if (this.userCombo.getItems().contains((Object)session2.getUser())) {
                tutors.add(session2.getTutorType());
            }
        }
        tutors.removeAll((Collection<?>)this.tutorCombo.getItems());
        this.tutorCombo.getItems().addAll((Collection)tutors);
        this.tutorCombo.setValue((Object)"All");
        this.dateFrom.setValue((Object)LocalDate.now().minusMonths(1L));
        this.dateTo.setValue((Object)LocalDate.now());
        this.updateFilteredData();
    }
    
    public void closeStats() {
        if (this.stage != null) {
            if (this.stage.isShowing()) {
                this.stage.close();
            }
            this.stage = null;
        }
    }
    
    public void importStats() {
        Boolean isValid = true;
        String invalidFiles = "";
        String msg = "";
        final List<TutorSession> importedSessions = new ArrayList<TutorSession>();
        final List<File> files = FileInformation.getMultipleFiles("Select Tutor Files", this.stage);
        if (files != null) {
            for (final File file : files) {
                final TuneOutTutorFile tutorFile = new TuneOutTutorFile(file);
                final String tutorText = tutorFile.fileToText(file);
                if (tutorFile.isValid(tutorText)) {
                    importedSessions.addAll(RecordedTutorStats.convertTutorDataToSessions(tutorText, true));
                }
                else {
                    isValid = false;
                    invalidFiles = invalidFiles + "\n" + file.getName();
                }
            }
            if (isValid && !importedSessions.isEmpty()) {
                Boolean importExistData = false;
                if (!Collections.disjoint(importedSessions, this.allTutorData)) {
                    importedSessions.removeAll(this.allTutorData);
                    importExistData = true;
                }
                this.allTutorData.addAll(importedSessions);
                this.initialiseFilters();
                msg = "Imported " + importedSessions.size() + " sessions.";
                if (importExistData) {
                    msg += " Some imported sessions already exist for the application and were ignored.";
                }
            }
            else if (!isValid) {
                msg = "The following files are not valid. No data was imported." + invalidFiles;
            }
            else if (importedSessions.isEmpty()) {
                msg = "The selected files had no data to import.";
            }
            final Alert alert = new Alert(Alert.AlertType.INFORMATION);
            final DialogPane dialogPane = alert.getDialogPane();
            alert.getDialogPane().setMinHeight(Double.NEGATIVE_INFINITY);
            dialogPane.getStylesheets().add((Object)StyleMap.getCurrentStyle());
            alert.setTitle("File Information");
            alert.setHeaderText("Imported File");
            alert.setContentText(msg);
            alert.showAndWait();
        }
    }
    
    public void updateFilteredData() {
        final List<String> users = new ArrayList<String>((Collection<? extends String>)Collections.singletonList(this.userCombo.getSelectionModel().getSelectedItem()));
        final List<String> tutors = new ArrayList<String>();
        if (((String)this.tutorCombo.getValue()).equals("All")) {
            tutors.addAll((Collection<? extends String>)this.tutorCombo.getItems());
            tutors.remove("All");
        }
        else {
            tutors.add((String)this.tutorCombo.getSelectionModel().getSelectedItem());
        }
        final LocalDateTime from = ((LocalDate)this.dateFrom.getValue()).atStartOfDay();
        final LocalDateTime to = ((LocalDate)this.dateTo.getValue()).atTime(23, 59, 59);
        this.filteredTutorData.clear();
        this.filteredTutorData.setAll((Collection)RecordedTutorStats.filterTutorSessionData(this.allTutorData, users, tutors, from, to));
        this.updateGraph();
    }
    
    public void resetTableData() {
        this.allTutorData.clear();
        this.allTutorData.addAll(RecordedTutorStats.getTutorSessionData());
        this.initialiseFilters();
    }
    
    public void addSessionToTable(final TutorSession event) {
        this.allTutorData.add(event);
        this.initialiseFilters();
    }
    
    public void requestFocus() {
        if (this.stage != null) {
            this.stage.requestFocus();
        }
    }
    
    public static <ROW, T extends Temporal> Callback<TableColumn<ROW, T>, TableCell<ROW, T>> getDateCell(final DateTimeFormatter format) {
        return (Callback<TableColumn<ROW, T>, TableCell<ROW, T>>)(column -> new TableCell<Object, Object>() {
            final /* synthetic */ DateTimeFormatter val$format;
            
            protected void updateItem(final Temporal item, final boolean empty) {
                super.updateItem((Object)item, empty);
                if (item == null || empty) {
                    this.setText((String)null);
                }
                else {
                    this.setText(this.val$format.format(item));
                }
            }
        });
    }
    
    static {
        graphMap = new HashMap<String, AbstractChart>();
        ALLOWED_GRAPH_CHILDREN = 2;
        UserStatsController.graphMap.put(new PerformanceAcrossTutors().getTitle(), new PerformanceAcrossTutors());
        UserStatsController.graphMap.put(new PerformanceOverTime().getTitle(), new PerformanceOverTime());
        UserStatsController.graphMap.put(new TutorUsage().getTitle(), new TutorUsage());
    }
}
