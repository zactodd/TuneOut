// 
// Decompiled by Procyon v0.5.36
// 

package Environment;

import java.io.IOException;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import Model.CommandMessages;
import java.util.List;
import org.apache.log4j.Logger;
import Model.Play.Play;
import Model.Tutor.TutorDefinition;
import Model.Tutor.Tutor;
import Controller.OuterTemplateController;

public class Environment
{
    private String response;
    private boolean tutorMode;
    private OuterTemplateController outerTemplateController;
    private Tutor tutor;
    private TutorDefinition tutorDefinition;
    private Play play;
    private Logger log;
    private Boolean outputToTranscript;
    private List<String> filter;
    
    public Environment(final Play play) {
        this.tutorMode = false;
        this.log = Logger.getLogger(OuterTemplateController.class.getName());
        this.outputToTranscript = false;
        this.filter = new ArrayList<String>(Arrays.asList(CommandMessages.getMessage("INCORRECT_SYNTAX")));
        this.play = play;
    }
    
    public Environment(final Play play, final Boolean outputToTranscript) {
        this.tutorMode = false;
        this.log = Logger.getLogger(OuterTemplateController.class.getName());
        this.outputToTranscript = false;
        this.filter = new ArrayList<String>(Arrays.asList(CommandMessages.getMessage("INCORRECT_SYNTAX")));
        this.play = play;
        this.outputToTranscript = outputToTranscript;
    }
    
    public void println(final String str) {
        System.out.println(str);
    }
    
    public void error(final String errorMessage) {
        System.err.format("[ERROR] %s%n", errorMessage);
    }
    
    public String getResponse() {
        this.appendToTranscript(this.response);
        return this.response;
    }
    
    public void appendToTranscript(final String text) {
        if (this.outerTemplateController != null && this.outputToTranscript && !this.filter.contains(text)) {
            this.outerTemplateController.addToTranscript("\n" + text);
        }
    }
    
    public void setResponse(final String response) {
        this.response = response;
    }
    
    public void setOuterTemplateController(final OuterTemplateController outerTemplateController) {
        this.outerTemplateController = outerTemplateController;
    }
    
    public void setTutorMode(final boolean tutorMode) {
        this.tutorMode = tutorMode;
    }
    
    public boolean isTutorMode() {
        return this.tutorMode;
    }
    
    public boolean checkAndCreateTutor(final TutorDefinition tutorDefn) {
        if (this.outerTemplateController.checkTabExists(tutorDefn.tutorName)) {
            this.outerTemplateController.selectTab(tutorDefn.tutorName);
            return false;
        }
        this.outerTemplateController.createTutor(tutorDefn);
        return true;
    }
    
    public void checkAndOpenTutorStats() {
        try {
            this.outerTemplateController.openUserStats();
        }
        catch (IOException e) {
            this.log.error(e.toString());
        }
    }
    
    public Tutor getTutor() {
        return this.tutor;
    }
    
    public TutorDefinition getTutorDefinition() {
        return this.tutorDefinition;
    }
    
    public void setTutor(final Tutor tutor) {
        this.tutor = tutor;
    }
    
    public void setTutorDefinition(final TutorDefinition tutorDefinition) {
        this.tutorDefinition = tutorDefinition;
    }
    
    public Play getPlay() {
        return this.play;
    }
}
