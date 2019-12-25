// 
// Decompiled by Procyon v0.5.36
// 

package Model.Project;

import java.util.List;
import Model.command.CommandStorage.CommandHistory;
import java.io.File;

public class PersistenceData
{
    private Project project;
    private File previousDirectory;
    private CommandHistory commandHistory;
    private String transcript;
    private String style;
    private double zoom;
    private boolean saveStats;
    private List<String> tutors;
    private boolean isTranscriptOpen;
    
    public PersistenceData() {
    }
    
    public PersistenceData(final Project project, final File previousDirectory, final CommandHistory commandHistory, final String transcript, final String style, final double zoom, final boolean saveStats, final List<String> tutors, final boolean isTranscriptOpen) {
        this.project = project;
        this.previousDirectory = previousDirectory;
        this.commandHistory = commandHistory;
        this.transcript = transcript;
        this.style = style;
        this.zoom = zoom;
        this.saveStats = saveStats;
        this.tutors = tutors;
        this.isTranscriptOpen = isTranscriptOpen;
    }
    
    public boolean isTranscriptOpen() {
        return this.isTranscriptOpen;
    }
    
    public Project getProject() {
        return this.project;
    }
    
    public File getPreviousDirectory() {
        return this.previousDirectory;
    }
    
    public CommandHistory getCommandHistory() {
        return this.commandHistory;
    }
    
    public String getTranscript() {
        return this.transcript;
    }
    
    public String getStyle() {
        return this.style;
    }
    
    public double getZoom() {
        return this.zoom;
    }
    
    public boolean isSaveStats() {
        return this.saveStats;
    }
    
    public List<String> getTutors() {
        return this.tutors;
    }
}
