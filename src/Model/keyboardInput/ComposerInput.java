

package Model.keyboardInput;

import java.util.List;
import Controller.LearningCompose.OuterComposeController;

public class ComposerInput implements NoteInputField
{
    OuterComposeController outerCompose;
    
    public ComposerInput(final OuterComposeController compose) {
        this.outerCompose = compose;
    }
    
    @Override
    public void insertNote(final String note) {
        this.outerCompose.getSheetController().setIsKeyboardInputMode(true);
        this.outerCompose.insertNote(note, 0.0);
        this.outerCompose.getSheetController().setIsKeyboardInputMode(false);
    }
    
    @Override
    public void insertNotes(final List<String> notes) {
        this.outerCompose.getSheetController().setIsKeyboardInputMode(true);
        this.outerCompose.insertNotes(notes);
        this.outerCompose.getSheetController().setIsKeyboardInputMode(false);
    }
}
