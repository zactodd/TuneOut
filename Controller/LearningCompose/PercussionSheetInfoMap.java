

package Controller.LearningCompose;

import Model.Note.Note;
import Model.Percussion.Percussion;
import javafx.scene.image.Image;
import Model.Note.NoteMap;
import Model.Percussion.PercussionMap;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Map;

public class PercussionSheetInfoMap
{
    public static final Map<String, PercussionSheetInfo> PERCUSSION_SHEET_INFO;
    
    public static PercussionSheetInfo getInfoByMidi(final Integer midi) {
        for (final Map.Entry<String, PercussionSheetInfo> mapEntry : PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.entrySet()) {
            if (mapEntry.getValue().getPercussion().getMidi() == midi) {
                return mapEntry.getValue();
            }
        }
        return null;
    }
    
    static {
        PERCUSSION_SHEET_INFO = new LinkedHashMap<String, PercussionSheetInfo>();
        final Percussion pedalHiHat = PercussionMap.getPercussion(44);
        PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.put(pedalHiHat.getInstrument(), new PercussionSheetInfo(pedalHiHat, PercussionCategory.CROSS_UP, NoteMap.getNote("D4"), new Image("View/LearningCompose/graphic/Percussion/Cross.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_cross.png")));
        final Percussion bassDrum1 = PercussionMap.getPercussion(36);
        PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.put(bassDrum1.getInstrument(), new PercussionSheetInfo(bassDrum1, PercussionCategory.CROTCHET_UP, NoteMap.getNote("E4"), new Image("View/LearningCompose/graphic/Percussion/Crotchet.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_note.png")));
        final Percussion acousticBassDrum = PercussionMap.getPercussion(35);
        PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.put(acousticBassDrum.getInstrument(), new PercussionSheetInfo(acousticBassDrum, PercussionCategory.CROTCHET_UP, NoteMap.getNote("F4"), new Image("View/LearningCompose/graphic/Percussion/Crotchet.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_note.png")));
        final Percussion lowFloorTom = PercussionMap.getPercussion(41);
        PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.put(lowFloorTom.getInstrument(), new PercussionSheetInfo(lowFloorTom, PercussionCategory.CROTCHET_UP, NoteMap.getNote("G4"), new Image("View/LearningCompose/graphic/Percussion/Crotchet.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_note.png")));
        final Percussion highFloorTom = PercussionMap.getPercussion(43);
        PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.put(highFloorTom.getInstrument(), new PercussionSheetInfo(highFloorTom, PercussionCategory.CROTCHET_UP, NoteMap.getNote("A4"), new Image("View/LearningCompose/graphic/Percussion/Crotchet.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_note.png")));
        final Percussion lowTom = PercussionMap.getPercussion(45);
        PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.put(lowTom.getInstrument(), new PercussionSheetInfo(lowTom, PercussionCategory.CROTCHET_UP, NoteMap.getNote("B4"), new Image("View/LearningCompose/graphic/Percussion/Crotchet.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_note.png")));
        final Percussion tambourine = PercussionMap.getPercussion(54);
        PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.put(tambourine.getInstrument(), new PercussionSheetInfo(tambourine, PercussionCategory.TRIANGLE_MIDDLE, NoteMap.getNote("B4"), new Image("View/LearningCompose/graphic/Percussion/TriangleMiddle.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_trianglemiddle.png")));
        final Percussion acousticSnare = PercussionMap.getPercussion(38);
        PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.put(acousticSnare.getInstrument(), new PercussionSheetInfo(acousticSnare, PercussionCategory.CROTCHET_DOWN, NoteMap.getNote("C5"), new Image("View/LearningCompose/graphic/Percussion/Crotchet.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_note.png")));
        final Percussion lowWoodBlock = PercussionMap.getPercussion(77);
        PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.put(lowWoodBlock.getInstrument(), new PercussionSheetInfo(lowWoodBlock, PercussionCategory.TRIANGLE_SIDE, NoteMap.getNote("C5"), new Image("View/LearningCompose/graphic/Percussion/TriangleSide.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_triangleside.png")));
        final Percussion sideStick = PercussionMap.getPercussion(37);
        PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.put(sideStick.getInstrument(), new PercussionSheetInfo(sideStick, PercussionCategory.STRIKE, NoteMap.getNote("C5"), new Image("View/LearningCompose/graphic/Percussion/Strike.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_strike.png")));
        final Percussion lowMidTom = PercussionMap.getPercussion(47);
        PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.put(lowMidTom.getInstrument(), new PercussionSheetInfo(lowMidTom, PercussionCategory.CROTCHET_DOWN, NoteMap.getNote("D5"), new Image("View/LearningCompose/graphic/Percussion/Crotchet.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_note.png")));
        final Percussion highWoodBlock = PercussionMap.getPercussion(76);
        PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.put(highWoodBlock.getInstrument(), new PercussionSheetInfo(highWoodBlock, PercussionCategory.TRIANGLE_SIDE, NoteMap.getNote("D5"), new Image("View/LearningCompose/graphic/Percussion/TriangleSide.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_triangleside.png")));
        final Percussion highMidTom = PercussionMap.getPercussion(48);
        PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.put(highMidTom.getInstrument(), new PercussionSheetInfo(highMidTom, PercussionCategory.CROTCHET_DOWN, NoteMap.getNote("E5"), new Image("View/LearningCompose/graphic/Percussion/Crotchet.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_note.png")));
        final Percussion cowBell = PercussionMap.getPercussion(56);
        PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.put(cowBell.getInstrument(), new PercussionSheetInfo(cowBell, PercussionCategory.TRIANGLE_SIDE, NoteMap.getNote("E5"), new Image("View/LearningCompose/graphic/Percussion/TriangleSide.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_triangleside.png")));
        final Percussion highTom = PercussionMap.getPercussion(50);
        PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.put(highTom.getInstrument(), new PercussionSheetInfo(highTom, PercussionCategory.CROTCHET_DOWN, NoteMap.getNote("F5"), new Image("View/LearningCompose/graphic/Percussion/Crotchet.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_note.png")));
        final Percussion rideCymbal = PercussionMap.getPercussion(51);
        PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.put(rideCymbal.getInstrument(), new PercussionSheetInfo(rideCymbal, PercussionCategory.CROSS_DOWN, NoteMap.getNote("F5"), new Image("View/LearningCompose/graphic/Percussion/Cross.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_cross.png")));
        final Percussion closedHiHat = PercussionMap.getPercussion(42);
        PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.put(closedHiHat.getInstrument(), new PercussionSheetInfo(closedHiHat, PercussionCategory.CROSS_DOWN, NoteMap.getNote("G5"), new Image("View/LearningCompose/graphic/Percussion/Cross.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_cross.png")));
        final Percussion openHiHat = PercussionMap.getPercussion(46);
        PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.put(openHiHat.getInstrument(), new PercussionSheetInfo(openHiHat, PercussionCategory.DIAMOND, NoteMap.getNote("G5"), new Image("View/LearningCompose/graphic/Percussion/Diamond.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_diamond.png")));
        final Percussion crashCymbal = PercussionMap.getPercussion(49);
        PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.put(crashCymbal.getInstrument(), new PercussionSheetInfo(crashCymbal, PercussionCategory.CROSS_DOWN, NoteMap.getNote("A5"), new Image("View/LearningCompose/graphic/Percussion/Cross.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_cross.png")));
        final Percussion openTriangle = PercussionMap.getPercussion(81);
        PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.put(openTriangle.getInstrument(), new PercussionSheetInfo(openTriangle, PercussionCategory.TRIANGLE_SIDE, NoteMap.getNote("A5"), new Image("View/LearningCompose/graphic/Percussion/TriangleSide.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_triangleside.png")));
        PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.put("Rest", new PercussionSheetInfo(new Percussion(-1, "Rest"), PercussionCategory.REST, new Note("R", -1, -1, true), new Image("View/LearningCompose/graphic/Rests/Rest crotchet.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_rest_crotchet.png")));
    }
}
