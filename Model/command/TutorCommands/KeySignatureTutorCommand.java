// 
// Decompiled by Procyon v0.5.36
// 

package Model.command.TutorCommands;

import Model.Tutor.Options;
import Controller.TutorController;
import Model.Tutor.Tutor;
import Model.Tutor.TutorDefinition;
import Model.Tutor.KeySignatureTutorOption;
import java.util.Collection;
import java.util.Arrays;
import Controller.KeySignatureTutorController;
import Model.Tutor.KeySignatureTutor;
import Environment.Environment;
import java.util.ArrayList;
import java.util.List;

public class KeySignatureTutorCommand extends TutorCommand
{
    private static final String TUTOR_NAME = "Key Signature Tutor";
    private String returnValue;
    private List<String> argType;
    private int numQuestions;
    private String questionTypeString;
    private String questionType;
    private String keySignatureTypeString;
    private String keySignatureType;
    private String scaleNameTypeString;
    private String scaleNameType;
    private final int QUESTION_NUM_POS = 0;
    private final int QUESTION_TYPE_POS = 1;
    private final int KEY_SIGNATURE_OPTION_POS = 2;
    private final int SCALE_NAME_OPTION_POS = 3;
    private Boolean commandValid;
    private String error;
    private final String INVALID_OVERRIDE = "The optional override is not valid, please make sure they are correct";
    
    public KeySignatureTutorCommand(final List<String> args) {
        this.argType = new ArrayList<String>();
        this.numQuestions = 5;
        this.questionTypeString = "Both";
        this.questionType = "-t";
        this.keySignatureTypeString = "Both";
        this.keySignatureType = "-c";
        this.scaleNameTypeString = "Both";
        this.scaleNameType = "-b";
        this.commandValid = true;
        this.error = "";
        if (args != null) {
            switch (args.size()) {
                case 4: {
                    this.processNumQuestions(args.get(0));
                    this.processQuestionType(args.get(1));
                    this.processKeySignatureOption(args.get(2));
                    this.processScaleNameOption(args.get(3));
                    break;
                }
                case 3: {
                    if (args.get(0).startsWith("-")) {
                        this.processQuestionType(args.get(0));
                        this.processKeySignatureOption(args.get(1));
                        this.processScaleNameOption(args.get(2));
                        break;
                    }
                    this.processNumQuestions(args.get(0));
                    if (!args.get(1).startsWith("-") || this.processScaleNameOption(args.get(1))) {
                        this.commandValid = false;
                        this.error = "The optional override is not valid, please make sure they are correct";
                        break;
                    }
                    this.processOverride(args.get(1), 1);
                    if (this.commandValid) {
                        this.processOverride(args.get(2), 2);
                        break;
                    }
                    this.commandValid = false;
                    this.error = "The optional override is not valid, please make sure they are correct";
                    break;
                }
                case 2: {
                    if (args.get(0).matches(".*\\d+.*")) {
                        this.processNumQuestions(args.get(0));
                        if (this.commandValid) {
                            this.processOverride(args.get(1), 1);
                            break;
                        }
                        break;
                    }
                    else {
                        if (!args.get(0).startsWith("-") || this.processScaleNameOption(args.get(0))) {
                            this.commandValid = false;
                            this.error = "The optional override is not valid, please make sure they are correct";
                            break;
                        }
                        this.processOverride(args.get(0), 1);
                        if (this.commandValid) {
                            this.processOverride(args.get(1), 2);
                            break;
                        }
                        this.commandValid = false;
                        this.error = "The optional override is not valid, please make sure they are correct";
                        break;
                    }
                    break;
                }
                case 1: {
                    if (!args.get(0).startsWith("-")) {
                        this.processNumQuestions(args.get(0));
                        break;
                    }
                    if (args.get(0).equals("-k") || args.get(0).equals("-s") || args.get(0).equals("-t")) {
                        this.processQuestionType(args.get(0));
                        break;
                    }
                    if (args.get(0).equals("-n") || args.get(0).equals("-l") || args.get(0).equals("-c")) {
                        this.processKeySignatureOption(args.get(0));
                        break;
                    }
                    if (args.get(0).equals("-M") || args.get(0).equals("-m") || args.get(0).equals("-b")) {
                        this.processScaleNameOption(args.get(0));
                        break;
                    }
                    this.commandValid = false;
                    this.error = "The optional override is not valid, please make sure they are correct";
                    break;
                }
                default: {
                    this.wrongNumOfParamErrorRaiser();
                    break;
                }
            }
        }
        if (this.commandValid) {
            this.returnValue = "Started Key Signature Tutor with " + this.numQuestions + " question";
            this.returnValue += ((this.numQuestions == 1) ? "" : "s");
            this.returnValue = this.returnValue + "\nTutor will ask about " + this.questionTypeString.toLowerCase() + " question type";
            this.returnValue += (this.questionTypeString.contains("Both") ? "s" : "");
            this.returnValue = this.returnValue + "\nKey signature will be " + this.keySignatureTypeString.toLowerCase() + " option";
            this.returnValue += (this.keySignatureTypeString.contains("Both") ? "s" : "");
            this.returnValue = this.returnValue + "\nScale name will be in " + this.scaleNameTypeString.toLowerCase() + " scale";
            this.returnValue += (this.scaleNameTypeString.contains("Both") ? "s" : "");
            this.returnValue += "\nGo to the Key Signature Tutor tab to begin the tutor";
        }
        else {
            this.returnValue = this.error;
        }
    }
    
    @Override
    public void execute(final Environment env) {
        if (this.commandValid) {
            final KeySignatureTutor tutor = new KeySignatureTutor();
            final KeySignatureTutorController tutorController = new KeySignatureTutorController();
            tutor.setOptions(new ArrayList<String>(Arrays.asList(String.valueOf(this.numQuestions), this.questionType, this.keySignatureType, this.scaleNameType)));
            final Options options = new KeySignatureTutorOption(this.numQuestions, new ArrayList<String>(Arrays.asList(this.questionType, this.keySignatureType, this.scaleNameType)));
            final TutorDefinition tutorDefinition = new TutorDefinition("Key Signature Tutor", tutor, tutorController, options);
            final boolean tutorValid = env.checkAndCreateTutor(tutorDefinition);
            if (!tutorValid) {
                this.returnValue = TutorCommand.tutorAlreadyOpen();
            }
        }
        env.setResponse(this.returnValue);
    }
    
    private void processNumQuestions(final String arg) {
        if (Options.numQuestionsValid(arg)) {
            this.numQuestions = Integer.parseInt(arg);
        }
        else {
            this.commandValid = false;
            this.error = "The number of questions is not valid, it must be a number between 1 and 1000";
        }
    }
    
    private boolean processQuestionType(final String arg) {
        boolean valid = true;
        if (arg.equals("-k")) {
            this.questionTypeString = "Key signature";
            this.questionType = "-k";
        }
        else if (arg.equals("-s")) {
            this.questionTypeString = "Scale name";
            this.questionType = "-s";
        }
        else if (arg.equals("-t")) {
            this.questionTypeString = "Both";
            this.questionType = "-t";
        }
        else {
            valid = false;
            this.commandValid = false;
            this.error = "The optional override is not valid, please make sure they are correct";
        }
        return valid;
    }
    
    private boolean processKeySignatureOption(final String arg) {
        boolean valid = true;
        if (arg.equals("-l")) {
            this.keySignatureType = "-l";
            this.keySignatureTypeString = "Listed";
        }
        else if (arg.equals("-n")) {
            this.keySignatureType = "-n";
            this.keySignatureTypeString = "Numbered";
        }
        else if (arg.equals("-c")) {
            this.keySignatureTypeString = "Both";
            this.keySignatureType = "-c";
        }
        else {
            valid = false;
            this.commandValid = false;
            this.error = "The optional override is not valid, please make sure they are correct";
        }
        return valid;
    }
    
    private boolean processScaleNameOption(final String arg) {
        boolean valid = true;
        if (arg.equals("-M")) {
            this.scaleNameTypeString = "Major";
            this.scaleNameType = "-M";
        }
        else if (arg.equals("-m")) {
            this.scaleNameTypeString = "Minor";
            this.scaleNameType = "-m";
        }
        else if (arg.equals("-b")) {
            this.scaleNameTypeString = "Both";
            this.scaleNameType = "-b";
        }
        else {
            valid = false;
            this.commandValid = false;
            this.error = "The optional override is not valid, please make sure they are correct";
        }
        return valid;
    }
    
    private void processOverride(final String overrides, final int optionNum) {
        final String QUESTION_TYPE = "quesType";
        final String KEY_SIG_FORMAT = "keySigFormat";
        final String SCALE_TYPE = "scaleType";
        if (optionNum == 1) {
            final boolean quesType = this.processQuestionType(overrides);
            if (!quesType) {
                final boolean keySigOpt = this.processKeySignatureOption(overrides);
                this.error = "";
                if (!keySigOpt) {
                    final boolean scaleNameOpt = this.processScaleNameOption(overrides);
                    if (!scaleNameOpt) {
                        this.error = "The optional override is not valid, please make sure they are correct";
                    }
                    else if (!this.argType.contains(SCALE_TYPE)) {
                        this.argType.add(SCALE_TYPE);
                        this.commandValid = true;
                    }
                    else {
                        this.commandValid = false;
                        this.error = "The optional override is not valid, please make sure they are correct";
                    }
                }
                else if (!this.argType.contains(KEY_SIG_FORMAT)) {
                    this.argType.add(KEY_SIG_FORMAT);
                    this.commandValid = true;
                }
                else {
                    this.commandValid = false;
                    this.error = "The optional override is not valid, please make sure they are correct";
                }
            }
            else if (!this.argType.contains(QUESTION_TYPE)) {
                this.argType.add(QUESTION_TYPE);
                this.commandValid = true;
            }
            else {
                this.commandValid = false;
                this.error = "The optional override is not valid, please make sure they are correct";
            }
        }
        else if (optionNum == 2) {
            final boolean keySigOpt2 = this.processKeySignatureOption(overrides);
            if (!keySigOpt2) {
                final boolean scaleNameOpt2 = this.processScaleNameOption(overrides);
                if (!scaleNameOpt2) {
                    this.error = "The optional override is not valid, please make sure they are correct";
                }
                else if (!this.argType.contains(SCALE_TYPE)) {
                    this.argType.add(SCALE_TYPE);
                    this.commandValid = true;
                }
                else {
                    this.commandValid = false;
                    this.error = "The optional override is not valid, please make sure they are correct";
                }
            }
            else if (!this.argType.contains(KEY_SIG_FORMAT)) {
                this.argType.add(KEY_SIG_FORMAT);
                this.commandValid = true;
            }
            else {
                this.commandValid = false;
                this.error = "The optional override is not valid, please make sure they are correct";
            }
        }
        else if (optionNum == 3) {
            final boolean scaleNameOpt3 = this.processScaleNameOption(overrides);
            if (scaleNameOpt3) {
                if (!this.argType.contains(SCALE_TYPE)) {
                    this.argType.add(SCALE_TYPE);
                    this.commandValid = true;
                }
                else {
                    this.commandValid = false;
                    this.error = "The optional override is not valid, please make sure they are correct";
                }
            }
        }
        else {
            this.commandValid = false;
            this.error = "The optional override is not valid, please make sure they are correct";
        }
    }
}
