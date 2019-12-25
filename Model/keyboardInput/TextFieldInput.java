// 
// Decompiled by Procyon v0.5.36
// 

package Model.keyboardInput;

import java.util.Iterator;
import java.util.List;
import javafx.scene.control.TextField;

public class TextFieldInput implements NoteInputField
{
    private TextField noteTextField;
    private boolean atStart;
    
    public TextFieldInput(final TextField noteTextField) {
        this.atStart = true;
        this.noteTextField = noteTextField;
    }
    
    public void reStart() {
        this.atStart = true;
    }
    
    @Override
    public void insertNote(String note) {
        if (!this.atStart) {
            note = ", " + note;
        }
        this.noteTextField.replaceSelection(note);
        this.atStart = false;
    }
    
    @Override
    public void insertNotes(final List<String> notes) {
        String outPut = "[";
        boolean first = true;
        if (!this.atStart) {
            outPut = ", [";
        }
        for (final String note : notes) {
            if (!first) {
                outPut += ", ";
            }
            outPut += note;
            first = false;
        }
        outPut += "]";
        this.noteTextField.replaceSelection(outPut);
        this.atStart = false;
    }
}
