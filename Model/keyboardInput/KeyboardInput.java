// 
// Decompiled by Procyon v0.5.36
// 

package Model.keyboardInput;

import java.util.List;

public abstract class KeyboardInput
{
    private static NoteInputField inputField;
    private static boolean inputAccepted;
    private static boolean groupMode;
    
    public static void setNoteInputField(final NoteInputField inputField) {
        KeyboardInput.inputField = inputField;
    }
    
    public static void copyToInputField(final String note) {
        if (KeyboardInput.inputField != null && !KeyboardInput.groupMode && KeyboardInput.inputAccepted) {
            KeyboardInput.inputField.insertNote(note);
        }
    }
    
    public static void copyToInputField(final List<String> notes) {
        if (KeyboardInput.inputField != null && KeyboardInput.groupMode && KeyboardInput.inputAccepted && notes.size() >= 1) {
            KeyboardInput.inputField.insertNotes(notes);
        }
        KeyboardInput.groupMode = false;
    }
    
    public static boolean isInputAccepted() {
        return KeyboardInput.inputAccepted;
    }
    
    public static void toggleInputAccepted() {
        KeyboardInput.inputAccepted = !KeyboardInput.inputAccepted;
    }
    
    public static void setGroupMode(final Boolean groupMode) {
        KeyboardInput.groupMode = groupMode;
    }
    
    static {
        KeyboardInput.inputAccepted = false;
        KeyboardInput.groupMode = false;
    }
}
