// 
// Decompiled by Procyon v0.5.36
// 

package Controller;

import javafx.scene.control.ToggleButton;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import java.util.List;
import Environment.GrammarParser;
import java.util.Iterator;
import java.util.Map;
import Controller.OpenAndSave.FileInformation;
import Model.Tutor.TutorSession;
import Model.Tutor.Question;
import java.util.Calendar;
import Model.File.TuneOutTutorFile;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Toggle;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import Model.command.TutorCommands.TutorCommand;
import org.apache.log4j.Logger;
import java.io.File;
import Model.Tutor.RecordedTutorStats;
import javafx.scene.control.Control;
import Environment.Environment;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public abstract class TutorAnswer
{
    @FXML
    protected Label question;
    @FXML
    protected Label results;
    @FXML
    protected Label testRepeatError;
    @FXML
    protected Button playBtn;
    @FXML
    protected Button skipBtn;
    @FXML
    protected Button answerBtn;
    @FXML
    protected ProgressBar progress;
    @FXML
    protected Label selectionError;
    protected FlowPane answerPane;
    protected ToggleGroup group;
    protected OuterTemplateController main;
    protected TutorOption optionController;
    protected Environment tutorEnvironment;
    protected Control[] otherAnswerControls;
    protected TutorController tutorController;
    protected TutorResultsController tutorResultsController;
    private RecordedTutorStats recordedStats;
    private static File file;
    final int maxTimeinLoop = 240;
    static Logger log;
    Thread sleeperThread;
    protected Integer doneQuestions;
    protected Integer rightQuestions;
    protected Integer numQuestions;
    
    public TutorAnswer() {
        this.answerPane = new FlowPane();
        this.group = new ToggleGroup();
        this.doneQuestions = 0;
        this.rightQuestions = 0;
    }
    
    void disableAnswers(final boolean disable) {
        this.playBtn.setDisable(disable);
        this.answerBtn.setDisable(disable);
        this.answerBtn.setDefaultButton(!disable);
        this.skipBtn.setDisable(disable);
    }
    
    @FXML
    public void runQuestion() {
        this.selectionError.setVisible(false);
        this.progress.setProgress(this.doneQuestions / (double)this.numQuestions);
        final String returnedQuestion = this.runTutorCommand("question()");
        this.question.setText("");
        this.question.setText(returnedQuestion);
        if (returnedQuestion.equals(TutorCommand.noMoreQuestions())) {
            this.displayStats();
        }
        else {
            this.runTutorCommand("run(3)");
        }
    }
    
    @FXML
    public void checkAnswer() {
        boolean isToggle;
        try {
            final Node node = (Node)this.answerPane.getChildren().get(0);
            isToggle = !(node instanceof TextField);
        }
        catch (IndexOutOfBoundsException e) {
            isToggle = false;
        }
        if (isToggle && this.nullcheckToggled()) {
            this.showWarning();
        }
        else {
            this.results.setText("");
            this.tutorResultsController.clearResults();
            this.results.setText(this.runTutorCommand("answer(\"" + this.getAnswer() + "\")"));
            final String response = this.runTutorCommand("answer(\"" + this.getAnswer() + "\")");
            this.tutorResultsController.appendtoResults(response);
            if (!response.contains("incorrect")) {
                final Integer rightQuestions = this.rightQuestions;
                ++this.rightQuestions;
            }
            final Integer doneQuestions = this.doneQuestions;
            ++this.doneQuestions;
            this.runQuestion();
            this.group.selectToggle((Toggle)null);
        }
    }
    
    public void setUpListener() {
        this.group.selectedToggleProperty().addListener((ChangeListener)new ChangeListener<Toggle>() {
            public void changed(final ObservableValue<? extends Toggle> observable, final Toggle oldValue, final Toggle newValue) {
                TutorAnswer.this.selectionError.setVisible(false);
            }
        });
    }
    
    public void displayStats() {
        this.tutorResultsController.appendtoResults("\n" + this.runTutorCommand("stats()"));
        this.disableAnswers(true);
        this.optionController.disableOptions(false);
        this.tutorController.show_resultsPane();
        this.saveTestRecord();
    }
    
    public void showWarning() {
        this.selectionError.setVisible(true);
    }
    
    public void setNumQuestions(final Integer numQuestions) {
        this.numQuestions = numQuestions;
        this.doneQuestions = 0;
        this.rightQuestions = 0;
        this.progress.setStyle("-fx-accent: #FECC17;");
    }
    
    protected boolean nullcheckToggled() {
        return this.group.getSelectedToggle() == null;
    }
    
    private void saveTestRecord() {
        final TuneOutTutorFile tutorFile = new TuneOutTutorFile(this.main.userManager.getCurrentUser().getUserName());
        String testContent = "";
        testContent += "------------------------";
        testContent += String.format("%n%n%1$s %2$tH:%2$tM:%2$tS User: %3$s%n%n", this.tutorEnvironment.getTutorDefinition().tutorName, Calendar.getInstance(), this.main.userManager.getCurrentUser().getUserName());
        final Map<Integer, Question> questions = this.tutorEnvironment.getTutor().getQuestions();
        final Map<Integer, String> userAnswers = this.tutorEnvironment.getTutor().getAnswers();
        for (final Integer key : questions.keySet()) {
            final Question question = questions.get(key);
            if (question.HasAnswered()) {
                final String answer = userAnswers.get(key);
                final char correctSymbol = question.getAnswer().toLowerCase().equals(answer.toLowerCase()) ? '\u2713' : '\u274c';
                testContent += String.format("%1$s %2$c%nanswer: %3$s    correct answer: %4$s%n", question.getQuestionText(), correctSymbol, answer, question.getAnswer());
            }
        }
        testContent += String.format("%nTest Summary%n", new Object[0]);
        for (final String stat : this.tutorEnvironment.getTutor().getStats()) {
            testContent += String.format("%1$s%n", stat);
        }
        testContent += String.format("%n", new Object[0]);
        testContent += "------------------------";
        testContent += String.format("%n", new Object[0]);
        if (RecordedTutorStats.isSaveStats()) {
            tutorFile.appendTextToFile(testContent, tutorFile.getFile());
            if (this.main.userStatsController != null) {
                for (final TutorSession session : RecordedTutorStats.convertTutorDataToSessions(testContent, false)) {
                    this.main.userStatsController.addSessionToTable(session);
                }
            }
        }
        else {
            final String buttonPushed = FileInformation.showSaveTestDialog();
            if (buttonPushed.equals("Save")) {
                tutorFile.appendTextToFile(testContent, tutorFile.getFile());
                if (this.main.userStatsController != null) {
                    for (final TutorSession session2 : RecordedTutorStats.convertTutorDataToSessions(testContent, false)) {
                        this.main.userStatsController.addSessionToTable(session2);
                    }
                }
            }
        }
    }
    
    public static void resetTutorFile() {
        TutorAnswer.file = null;
    }
    
    @FXML
    public void repeatTutor() {
        final String repeatString = this.runTutorCommand("repeat()");
        if (repeatString.equals(TutorCommand.cannotRepeatQuestions())) {
            this.tutorResultsController.retryError();
        }
        else {
            final Integer remaining = this.numQuestions - this.rightQuestions;
            this.numQuestions = remaining;
            this.doneQuestions = 0;
            this.results.setText("");
            this.tutorController.show_answerFromResults();
            this.disableAnswers(false);
            this.optionController.disableOptions(true);
            this.runQuestion();
            this.tutorResultsController.clearResults();
        }
    }
    
    protected String runTutorCommand(final String command) {
        new GrammarParser(this.tutorEnvironment).executeCommand("tutor:" + command);
        return this.tutorEnvironment.getResponse();
    }
    
    public void clearResults() {
        this.results.setText("");
    }
    
    @FXML
    public void repeatQuestion() {
        this.runTutorCommand("run(0)");
    }
    
    @FXML
    abstract String getAnswer();
    
    abstract void initialize(final OuterTemplateController p0, final TutorOption p1, final Environment p2, final TutorController p3, final TutorResultsController p4);
    
    public void setUpButtonsSmallFLow(final List<String> list, final Node nodeIn) {
        this.setUpListener();
        this.answerPane.getChildren().clear();
        this.answerPane = (FlowPane)nodeIn;
        ((FlowPane)nodeIn).getChildren().clear();
        ((FlowPane)nodeIn).setPadding(new Insets(10.0, 10.0, 10.0, 10.0));
        ((FlowPane)nodeIn).setVgap(20.0);
        ((FlowPane)nodeIn).setHgap(10.0);
        ((FlowPane)nodeIn).setAlignment(Pos.CENTER);
        final int width = 160;
        final int height = 60;
        for (final String key : list) {
            final ToggleButton newButton = this.optionController.createButton(width, height);
            newButton.setText(key);
            newButton.setToggleGroup(this.group);
            ((FlowPane)nodeIn).getChildren().add((Object)newButton);
        }
    }
    
    public void setUpButtonsSmallFLow(final List<String> list, final Node nodeIn, final Integer width, final Integer height) {
        this.setUpListener();
        this.answerPane.getChildren().clear();
        this.answerPane = (FlowPane)nodeIn;
        ((FlowPane)nodeIn).getChildren().clear();
        ((FlowPane)nodeIn).setPadding(new Insets(10.0, 10.0, 10.0, 10.0));
        ((FlowPane)nodeIn).setVgap(20.0);
        ((FlowPane)nodeIn).setHgap(10.0);
        ((FlowPane)nodeIn).setAlignment(Pos.CENTER);
        for (final String key : list) {
            final ToggleButton newButton = this.optionController.createButton(width, height);
            newButton.setText(key);
            newButton.setToggleGroup(this.group);
            ((FlowPane)nodeIn).getChildren().add((Object)newButton);
        }
    }
    
    static {
        TutorAnswer.file = null;
        TutorAnswer.log = Logger.getLogger(TutorAnswer.class.getName());
    }
}
