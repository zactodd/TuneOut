

package Model.Note.Scale;

import Model.Note.Intervals.IntervalMap;
import Model.Note.NoteMap;
import java.util.ArrayList;
import Model.Note.Note;
import java.util.List;

public class BlueScale extends PentatonicMinorScale
{
    private static final Integer DIMINISED_5TH_POS;
    private static final Integer BLUE_SCALE_SIZE;
    private static final Integer DIMISHED_SEMITON_DIFFERENCE;
    
    protected BlueScale() {
        super.name = "blues";
    }
    
    @Override
    protected List<Note> afterProcess() {
        final List<Note> blues = new ArrayList<Note>();
        final List<Note> minorPentatonic = super.afterProcess();
        final String rootName = minorPentatonic.get(0).getNoteWithOctave();
        final Note flattened5th = NoteMap.getNote(NoteMap.getSemitone(rootName, BlueScale.DIMISHED_SEMITON_DIFFERENCE));
        Integer notePos = 0;
        Integer minorPentatonicPos = 0;
        while (notePos < BlueScale.BLUE_SCALE_SIZE) {
            if (notePos.equals(BlueScale.DIMINISED_5TH_POS)) {
                blues.add(notePos, flattened5th);
            }
            else {
                blues.add(notePos, minorPentatonic.get(minorPentatonicPos));
                ++minorPentatonicPos;
            }
            ++notePos;
        }
        return blues;
    }
    
    static {
        DIMINISED_5TH_POS = 3;
        BLUE_SCALE_SIZE = 7;
        DIMISHED_SEMITON_DIFFERENCE = IntervalMap.getIntervalWithIntervalName("dim5").getSemitone();
    }
}
