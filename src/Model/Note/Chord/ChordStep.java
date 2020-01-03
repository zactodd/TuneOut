

package Model.Note.Chord;

public class ChordStep
{
    private String scale;
    private int noteNumber;
    private CHORD_STEP_TYPE stepType;
    
    protected ChordStep(final String scale, final int scaleNoteNumber, final CHORD_STEP_TYPE stepType) {
        this.scale = scale;
        this.noteNumber = scaleNoteNumber;
        this.stepType = stepType;
    }
    
    protected String getScale() {
        return this.scale;
    }
    
    protected int getNoteNumber() {
        return this.noteNumber;
    }
    
    protected CHORD_STEP_TYPE getStepType() {
        return this.stepType;
    }
    
    protected enum CHORD_STEP_TYPE
    {
        FLAT, 
        EXACT, 
        SHARP;
    }
}
