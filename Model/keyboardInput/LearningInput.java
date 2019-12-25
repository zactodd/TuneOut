// 
// Decompiled by Procyon v0.5.36
// 

package Model.keyboardInput;

import java.util.List;
import Controller.LearningCompose.OuterLearningController;

public class LearningInput implements NoteInputField
{
    OuterLearningController outerLearning;
    
    public LearningInput(final OuterLearningController learning) {
        this.outerLearning = learning;
    }
    
    @Override
    public void insertNote(final String note) {
        this.outerLearning.insertNote(note);
    }
    
    @Override
    public void insertNotes(final List<String> notes) {
        this.outerLearning.insertNote(notes.get(0));
    }
}
