// 
// Decompiled by Procyon v0.5.36
// 

package Controller;

import java.util.Iterator;
import Model.Tutor.Options;
import javafx.scene.control.Control;
import Environment.Environment;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Map;
import java.util.List;
import javafx.scene.control.ComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class KeySignatureTutorOptionController extends TutorOption
{
    @FXML
    private Label numQuestionsError;
    @FXML
    private ComboBox<String> questionType;
    @FXML
    private ComboBox<String> keySignatureOption;
    @FXML
    private ComboBox<String> scaleNameOption;
    private int currentNumberTests;
    private final int MAX_NUM_QUESTIONS_LENGTH = 10;
    private KeySignatureTutorAnswerController answerController;
    private OuterTemplateController main;
    private final int QUESTION_TYPE_POS = 0;
    private final int KEY_SIGNATURE_OPTION_POS = 1;
    private final int SCALE_NAME_OPTION_POS = 2;
    private final List<String> QUESTION_TYPES;
    private final List<String> KEY_SIG_FORMAT;
    private final List<String> SCALE_TYPES;
    private final String KEY_SIGNATURE_ONLY_GUI = "Key signatures";
    private final String SCALE_NAMES_ONLY_GUI = "Scale names";
    private final String BOTH_QUESTION_TYPES_GUI = "Both";
    private final String KEY_SIGNATURES_LISTED_GUI = "Listed";
    private final String NUMBERED_AND_LISTED_GUI = "Both";
    private final String KEY_SIGNATURES_NUMBERED_GUI = "Numbered";
    private final String MAJOR_SCALES_ONLY_GUI = "Major";
    private final String MINOR_SCALES_ONLY_GUI = "Minor";
    private final String MAJOR_AND_MINOR_SCALES_GUI = "Both";
    private TutorController tutorController;
    private Map<String, String> questionTypeSpecifierToNameMap;
    private Map<String, String> keySignatureOptionSpecifierToNameMap;
    private Map<String, String> scaleNameOptionSpecifierToNameMap;
    
    public KeySignatureTutorOptionController() {
        this.currentNumberTests = 0;
        this.QUESTION_TYPES = Arrays.asList("-k", "-s", "-t");
        this.KEY_SIG_FORMAT = Arrays.asList("-l", "-n", "-c");
        this.SCALE_TYPES = Arrays.asList("-M", "-m", "-b");
        this.questionTypeSpecifierToNameMap = new HashMap<String, String>() {
            {
                this.put("-k", "Key signatures");
                this.put("-s", "Scale names");
                this.put("-t", "Both");
            }
        };
        this.keySignatureOptionSpecifierToNameMap = new HashMap<String, String>() {
            {
                this.put("-l", "Listed");
                this.put("-n", "Numbered");
                this.put("-c", "Both");
            }
        };
        this.scaleNameOptionSpecifierToNameMap = new HashMap<String, String>() {
            {
                this.put("-M", "Major");
                this.put("-m", "Minor");
                this.put("-b", "Both");
            }
        };
    }
    
    public void initialize(final OuterTemplateController main, final TutorAnswer answerController, final Environment tutorEnvironment, final TutorController tutorController) {
        this.answerController = (KeySignatureTutorAnswerController)answerController;
        this.main = main;
        this.tutorEnvironment = tutorEnvironment;
        this.tutorController = tutorController;
        this.setUpInputsAndDefaults();
        this.setUpListeners();
        this.otherOptionControls = new Control[] { (Control)this.questionType, (Control)this.keySignatureOption, (Control)this.scaleNameOption };
    }
    
    @Override
    public void startTutor() {
        if (!this.startBtn.isDisabled() && Options.numQuestionsValid(this.numQuestionsTextField.getText())) {
            this.tutorController.show_answerPane();
            this.numQuestionsError.setVisible(false);
            this.answerController.testRepeatError.setVisible(false);
            ++this.currentNumberTests;
            final String quesType = this.findKeyFromValue((String)this.questionType.getValue(), this.questionTypeSpecifierToNameMap);
            final String keySigOp = this.findKeyFromValue((String)this.keySignatureOption.getValue(), this.keySignatureOptionSpecifierToNameMap);
            final String scaleOp = this.findKeyFromValue((String)this.scaleNameOption.getValue(), this.scaleNameOptionSpecifierToNameMap);
            final String command = "start(" + this.numQuestionsTextField.getText() + "," + quesType + "," + keySigOp + "," + scaleOp + ")";
            this.runTutorCommand(command);
            this.answerController.disableAnswers(false);
            this.answerController.setNumQuestions(Integer.valueOf(this.numQuestionsTextField.getText()));
            this.disableOptions(true);
            this.answerController.runQuestion();
            this.answerController.clearResults();
        }
    }
    
    private String findKeyFromValue(final String value, final Map<String, String> map) {
        for (final String key : map.keySet()) {
            if (map.get(key).equals(value)) {
                return key;
            }
        }
        return null;
    }
    
    private void setUpInputsAndDefaults() {
        for (final String question : this.QUESTION_TYPES) {
            this.questionType.getItems().add((Object)this.questionTypeSpecifierToNameMap.get(question));
        }
        for (final String keySignatureOptionName : this.KEY_SIG_FORMAT) {
            this.keySignatureOption.getItems().add((Object)this.keySignatureOptionSpecifierToNameMap.get(keySignatureOptionName));
        }
        for (final String scaleNameOptionName : this.SCALE_TYPES) {
            this.scaleNameOption.getItems().add((Object)this.scaleNameOptionSpecifierToNameMap.get(scaleNameOptionName));
        }
        this.numQuestionsError.setText(this.numQuestionsError.getText() + 1000);
        this.numQuestionsTextField.appendText(String.valueOf(this.tutorEnvironment.getTutorDefinition().options.numQuestions));
        final String questionTypeString = this.questionTypeSpecifierToNameMap.get(this.tutorEnvironment.getTutorDefinition().options.otherOptions.get(0));
        final String keySignatureOptionString = this.keySignatureOptionSpecifierToNameMap.get(this.tutorEnvironment.getTutorDefinition().options.otherOptions.get(1));
        final String scaleNameOptionString = this.scaleNameOptionSpecifierToNameMap.get(this.tutorEnvironment.getTutorDefinition().options.otherOptions.get(2));
        this.questionType.getSelectionModel().select((Object)questionTypeString);
        this.keySignatureOption.getSelectionModel().select((Object)keySignatureOptionString);
        this.scaleNameOption.getSelectionModel().select((Object)scaleNameOptionString);
    }
    
    private void setUpListeners() {
        this.setUpNumQuestionsListeners();
    }
}
