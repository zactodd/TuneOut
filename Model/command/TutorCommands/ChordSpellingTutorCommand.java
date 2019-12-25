// 
// Decompiled by Procyon v0.5.36
// 

package Model.command.TutorCommands;

import Controller.TutorController;
import Model.Tutor.Tutor;
import Model.Tutor.TutorDefinition;
import Model.Tutor.ChordSpellingTutorOption;
import java.util.Collection;
import java.util.ArrayList;
import Controller.ChordSpellingTutorController;
import Model.Tutor.ChordSpellingTutor;
import Environment.Environment;
import java.util.Iterator;
import Model.Tutor.Options;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChordSpellingTutorCommand extends TutorCommand
{
    private static final String TUTOR_NAME = "Chord Spelling Tutor";
    private static final String SPELLING = "spelling";
    private static final String CHORD = "chord";
    public static final String MAJOR = "major";
    public static final String MINOR = "minor";
    public static final String AUGMENTED = "augmented";
    public static final String DIMINISHED = "diminished";
    public static final String SIXTH = "6th";
    public static final String SEVENTH = "7th";
    private static final String RANDOM = "-r";
    private static final String CHORD_WARNING = "Chord types must only be set once.";
    private static final String TYPE_WARNING = "The question type must only be set once.";
    private static final String NUM_QUESTIONS_WARNING = "The number of questions must only be set once.";
    private static final String RANDOM_WARNING = "The random override must only be set once.";
    private static final String UNRECOGNISED_WARNING = "Could not parse \"%1s\". Please refer to help for valid options.";
    private static final String NUM_QUESTIONS_OUTSIDE_RANGE_WARNING = "The number of questions is not valid, it must be a number between 1 and %d";
    private static final String START_MSG = "Started Chord spelling tutor with %1$s question%2$s, using %3$s type questions%4$s.%5$s";
    private Integer numQuestions;
    private Boolean questionType;
    private Boolean random;
    private Set<String> chordTypes;
    private Set<String> warningMsgs;
    
    public ChordSpellingTutorCommand(final List<String> args) {
        this.numQuestions = 5;
        this.questionType = true;
        this.random = false;
        this.chordTypes = new HashSet<String>();
        this.warningMsgs = new HashSet<String>();
        Boolean typeIsSet = false;
        Boolean numQuestionsIsSet = false;
        if (args != null) {
            for (String arg : args) {
                arg = arg.replace("\"", "");
                if (this.questionTypeChecks(arg, typeIsSet)) {
                    typeIsSet = true;
                }
                else {
                    if (this.chordTypeChecks(arg)) {
                        continue;
                    }
                    if ("-r".equalsIgnoreCase(arg)) {
                        if (!this.random) {
                            this.random = true;
                        }
                        else {
                            this.warningMsgs.add("The random override must only be set once.");
                        }
                    }
                    else {
                        try {
                            final Integer tempNumQuestions = Integer.parseInt(arg);
                            if (!numQuestionsIsSet && Options.numQuestionsValid(arg)) {
                                this.numQuestions = tempNumQuestions;
                                numQuestionsIsSet = true;
                            }
                            else if (!Options.numQuestionsValid(arg)) {
                                this.warningMsgs.add(String.format("The number of questions is not valid, it must be a number between 1 and %d", 1000));
                            }
                            else {
                                this.warningMsgs.add("The number of questions must only be set once.");
                            }
                        }
                        catch (NumberFormatException exp) {
                            this.warningMsgs.add(String.format("Could not parse \"%1s\". Please refer to help for valid options.", arg));
                        }
                    }
                }
            }
        }
        this.setReturnValue();
    }
    
    private void setReturnValue() {
        if (this.warningMsgs.isEmpty()) {
            String questionsSuffix = "s";
            if (this.numQuestions == 1) {
                questionsSuffix = "";
            }
            String questionTypeName = "chord";
            if (this.questionType) {
                questionTypeName = "spelling";
            }
            String chordsUsed = ", and ";
            Boolean firstChord = true;
            for (final String chordType : this.chordTypes) {
                if (!firstChord) {
                    chordsUsed += ", ";
                }
                chordsUsed += chordType;
                firstChord = false;
            }
            if (!firstChord) {
                chordsUsed += " chords will be used";
            }
            else {
                chordsUsed = "";
            }
            String isRandom = "";
            if (this.random) {
                isRandom = " Random (non-existent) chords may be generated.";
            }
            this.returnValue = String.format("Started Chord spelling tutor with %1$s question%2$s, using %3$s type questions%4$s.%5$s", this.numQuestions, questionsSuffix, questionTypeName, chordsUsed, isRandom);
        }
        else {
            String warnings = "";
            for (final String warningMsg : this.warningMsgs) {
                warnings = warnings + warningMsg + "\n";
            }
            this.returnValue = warnings.substring(0, warnings.length() - 1);
        }
    }
    
    private Boolean chordTypeChecks(final String arg) {
        Boolean argMatched = false;
        argMatched = this.chordTypeCheck(arg, "major", argMatched);
        argMatched = this.chordTypeCheck(arg, "minor", argMatched);
        argMatched = this.chordTypeCheck(arg, "augmented", argMatched);
        argMatched = this.chordTypeCheck(arg, "diminished", argMatched);
        argMatched = this.chordTypeCheck(arg, "6th", argMatched);
        argMatched = this.chordTypeCheck(arg, "7th", argMatched);
        return argMatched;
    }
    
    private Boolean chordTypeCheck(final String arg, final String chordType, final Boolean argMatched) {
        if (!argMatched && chordType.equalsIgnoreCase(arg)) {
            if (!this.chordTypes.contains(chordType)) {
                this.chordTypes.add(chordType);
            }
            else {
                this.warningMsgs.add("Chord types must only be set once.");
            }
            return true;
        }
        return argMatched;
    }
    
    private Boolean questionTypeChecks(final String arg, final Boolean typeIsSet) {
        Boolean matched = false;
        matched = this.questionTypeCheck(arg, typeIsSet, "spelling", true, matched);
        matched = this.questionTypeCheck(arg, typeIsSet, "chord", false, matched);
        return matched;
    }
    
    private Boolean questionTypeCheck(final String arg, final Boolean typeIsSet, final String type, final Boolean result, final Boolean matched) {
        if (type.equalsIgnoreCase(arg)) {
            if (!typeIsSet) {
                this.questionType = result;
            }
            else {
                this.warningMsgs.add("The question type must only be set once.");
            }
            return true;
        }
        return matched;
    }
    
    @Override
    public void execute(final Environment env) {
        if (this.warningMsgs.isEmpty()) {
            final ChordSpellingTutor chordSpellingTutor = new ChordSpellingTutor();
            final ChordSpellingTutorController controller = new ChordSpellingTutorController();
            final List<String> arguments = new ArrayList<String>() {
                {
                    this.add(ChordSpellingTutorCommand.this.numQuestions.toString());
                    this.add(ChordSpellingTutorCommand.this.questionType.toString());
                    this.add(ChordSpellingTutorCommand.this.random.toString());
                }
            };
            arguments.addAll(this.chordTypes);
            chordSpellingTutor.setOptions(arguments);
            final Options options = new ChordSpellingTutorOption(this.numQuestions, arguments.subList(3, arguments.size()), this.random, this.questionType);
            final TutorDefinition chordSpellingTutorDefn = new TutorDefinition("Chord Spelling Tutor", chordSpellingTutor, controller, options);
            final Boolean tutorValid = env.checkAndCreateTutor(chordSpellingTutorDefn);
            if (!tutorValid) {
                this.returnValue = TutorCommand.tutorAlreadyOpen();
            }
        }
        env.setResponse(this.returnValue);
    }
}
