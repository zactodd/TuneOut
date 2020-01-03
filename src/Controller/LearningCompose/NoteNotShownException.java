

package Controller.LearningCompose;

public class NoteNotShownException extends Exception
{
    public NoteNotShownException() {
    }
    
    public NoteNotShownException(final String message) {
        super(message);
    }
}
