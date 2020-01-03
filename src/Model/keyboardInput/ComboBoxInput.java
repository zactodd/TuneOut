

package Model.keyboardInput;

import java.util.List;
import Model.command.Command;
import javafx.scene.control.ComboBox;

public class ComboBoxInput implements NoteInputField
{
    private ComboBox<String> comboBox;
    
    public ComboBoxInput(final ComboBox<String> comboBox) {
        this.comboBox = comboBox;
    }
    
    @Override
    public void insertNote(final String note) {
        this.comboBox.getSelectionModel().select((Object)Command.translate("C4", note));
    }
    
    @Override
    public void insertNotes(final List<String> notes) {
        this.insertNote(Command.translate("C4", notes.get(0)));
    }
}
