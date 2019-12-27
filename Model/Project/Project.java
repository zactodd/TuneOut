

package Model.Project;

import Model.Percussion.PercussionLoopMap;
import Model.DigitalPattern.DigitalPattern;
import Model.Note.Settings.SwingMap;
import Model.Note.unitDuration.UnitDurationInformation;
import Model.instrument.InstrumentInformation;
import Model.Note.Settings.TempoInformation;
import Model.Terms.MusicalTerms;
import Model.Note.Melody.MelodyMap;
import java.util.HashSet;
import java.util.Iterator;
import Controller.OpenAndSave.FileInformation;
import java.io.File;
import Model.File.TuneOutGeneralFile;
import Model.File.TuneOutProjectFile;
import java.util.Set;

public class Project
{
    public static final String UNSAVED_FILENAME = "Untitled";
    private static Set<PersistentStatus> persistentStatuses;
    private TuneOutProjectFile projectFile;
    private TuneOutGeneralFile tempFile;
    
    public boolean save(final boolean forceNewFile) {
        if (this.projectFile != null && !forceNewFile && this.projectFile.file.exists()) {
            final String fileText = this.projectFile.convertObjectsToJson();
            this.sendTextToFile(this.projectFile, fileText);
            this.resetChangedFlags();
            return true;
        }
        final TuneOutProjectFile tempProjectFile = new TuneOutProjectFile(this.getSaveFileFromChooser());
        final String fileText = tempProjectFile.convertObjectsToJson();
        if (tempProjectFile.file != null) {
            this.sendTextToFile(tempProjectFile, fileText);
            this.resetChangedFlags();
            this.projectFile = tempProjectFile;
            return true;
        }
        return false;
    }
    
    public File getSaveFileFromChooser() {
        return FileInformation.saveFile("Save Project");
    }
    
    public void sendTextToFile(final TuneOutProjectFile projFile, final String fileText) {
        projFile.textToFile(fileText, projFile.file);
    }
    
    public File getOpenFileFromChooser() {
        return FileInformation.openFile("Open Project");
    }
    
    public void showNotValidError() {
        FileInformation.showNotValidError();
    }
    
    public void showValidMessage() {
        FileInformation.showValidMessage(this.getProjectName());
    }
    
    public void open() {
        if (this.checkPromptUnsavedChanges()) {
            final TuneOutProjectFile tempProjectFile = new TuneOutProjectFile(this.tempFile.file);
            if (tempProjectFile.file != null) {
                final String fileText = this.getTextFromFile(tempProjectFile);
                if (tempProjectFile.isValid(fileText)) {
                    tempProjectFile.convertObjectsFromJson(fileText);
                    this.resetChangedFlags();
                    this.projectFile = tempProjectFile;
                    this.showValidMessage();
                }
                else {
                    this.showNotValidError();
                }
            }
        }
    }
    
    public void setTempProjectFile(final TuneOutGeneralFile file) {
        this.tempFile = file;
    }
    
    public String getTextFromFile(final TuneOutProjectFile projFile) {
        return projFile.fileToText(projFile.file);
    }
    
    public void initialState() {
        this.resetPersistentStates();
        this.resetChangedFlags();
        this.projectFile = null;
    }
    
    public String promptGetUnsavedOption() {
        return FileInformation.showUnsavedChangesError(this.getProjectName());
    }
    
    public boolean checkPromptUnsavedChanges() {
        boolean isSaved = false;
        boolean noUnsavedChanges = false;
        boolean dontSave = false;
        if (this.unsavedChanges()) {
            final String promptGetUnsavedOption;
            final String result = promptGetUnsavedOption = this.promptGetUnsavedOption();
            switch (promptGetUnsavedOption) {
                case "Save": {
                    isSaved = this.save(false);
                    break;
                }
                case "Don't Save": {
                    dontSave = true;
                    break;
                }
            }
        }
        else {
            noUnsavedChanges = true;
        }
        return noUnsavedChanges || isSaved || dontSave;
    }
    
    public boolean unsavedChanges() {
        for (final PersistentStatus status : Project.persistentStatuses) {
            if (status.isUpdated()) {
                return true;
            }
        }
        return false;
    }
    
    public void resetChangedFlags() {
        for (final PersistentStatus status : Project.persistentStatuses) {
            status.clearUpdateFlag();
        }
    }
    
    private void resetPersistentStates() {
        for (final PersistentStatus status : Project.persistentStatuses) {
            status.resetData();
        }
    }
    
    public void setProjectFile(final TuneOutProjectFile projectFile) {
        this.projectFile = projectFile;
    }
    
    public TuneOutProjectFile getProjectFile() {
        return this.projectFile;
    }
    
    public String getProjectName() {
        if (this.projectFile != null) {
            return this.projectFile.file.getName();
        }
        return "Untitled";
    }
    
    static {
        (Project.persistentStatuses = new HashSet<PersistentStatus>()).add(MelodyMap.getStatus());
        Project.persistentStatuses.add(MusicalTerms.getStatus());
        Project.persistentStatuses.add(TempoInformation.getStatus());
        Project.persistentStatuses.add(InstrumentInformation.getStatus());
        Project.persistentStatuses.add(UnitDurationInformation.getStatus());
        Project.persistentStatuses.add(SwingMap.getStatus());
        Project.persistentStatuses.add(DigitalPattern.getStatus());
        Project.persistentStatuses.add(PercussionLoopMap.getStatus());
    }
}
