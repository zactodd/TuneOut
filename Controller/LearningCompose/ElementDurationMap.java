// 
// Decompiled by Procyon v0.5.36
// 

package Controller.LearningCompose;

import javafx.scene.image.Image;
import java.util.HashMap;
import Model.Note.unitDuration.UnitDurationMap;
import java.util.Map;
import Model.Note.unitDuration.UnitDuration;

public class ElementDurationMap
{
    private static final UnitDuration SEMIQUAVER;
    private static final UnitDuration QUAVER;
    private static final UnitDuration CROTCHET;
    private static final UnitDuration MINIM;
    private static final UnitDuration SEMIBREVE;
    private static final UnitDuration BREVE;
    public static final Map<String, ElementDuration> ELEMENT_DURATIONS;
    
    static {
        SEMIQUAVER = UnitDurationMap.getUnitDurationByName("Semiquaver");
        QUAVER = UnitDurationMap.getUnitDurationByName("Quaver");
        CROTCHET = UnitDurationMap.getUnitDurationByName("Crotchet");
        MINIM = UnitDurationMap.getUnitDurationByName("Minim");
        SEMIBREVE = UnitDurationMap.getUnitDurationByName("Semibreve");
        BREVE = UnitDurationMap.getUnitDurationByName("Breve");
        ELEMENT_DURATIONS = new HashMap<String, ElementDuration>() {
            {
                this.put("semiquaverNote", new ElementDuration(ElementDurationMap.SEMIQUAVER, new Image("View/LearningCompose/graphic/Notes/Semiquaver.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_semiquaver.png"), ElementDuration.ElementType.NOTE, false));
                this.put("semiquaverDottedNote", new ElementDuration(new UnitDuration(ElementDurationMap.SEMIQUAVER.getUnitDurationName(), ElementDurationMap.SEMIQUAVER.getUnitDurationDivider(), true, 1), new Image("View/LearningCompose/graphic/Notes/Semiquaver.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_semiquaver.png"), ElementDuration.ElementType.NOTE, true));
                this.put("quaverNote", new ElementDuration(ElementDurationMap.QUAVER, new Image("View/LearningCompose/graphic/Notes/Quaver.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_quaver.png"), ElementDuration.ElementType.NOTE, false));
                this.put("quaverDottedNote", new ElementDuration(new UnitDuration(ElementDurationMap.QUAVER.getUnitDurationName(), ElementDurationMap.QUAVER.getUnitDurationDivider(), true, 1), new Image("View/LearningCompose/graphic/Notes/Quaver.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_quaver.png"), ElementDuration.ElementType.NOTE, true));
                this.put("crotchetNote", new ElementDuration(ElementDurationMap.CROTCHET, new Image("View/LearningCompose/graphic/Notes/Crotchet.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_note.png"), ElementDuration.ElementType.NOTE, false));
                this.put("crotchetDottedNote", new ElementDuration(new UnitDuration(ElementDurationMap.CROTCHET.getUnitDurationName(), ElementDurationMap.CROTCHET.getUnitDurationDivider(), true, 1), new Image("View/LearningCompose/graphic/Notes/Crotchet.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_note.png"), ElementDuration.ElementType.NOTE, true));
                this.put("minimNote", new ElementDuration(ElementDurationMap.MINIM, new Image("View/LearningCompose/graphic/Notes/Minim.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_minim.png"), ElementDuration.ElementType.NOTE, false));
                this.put("minimDottedNote", new ElementDuration(new UnitDuration(ElementDurationMap.MINIM.getUnitDurationName(), ElementDurationMap.MINIM.getUnitDurationDivider(), true, 1), new Image("View/LearningCompose/graphic/Notes/Minim.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_minim.png"), ElementDuration.ElementType.NOTE, true));
                this.put("semibreveNote", new ElementDuration(ElementDurationMap.SEMIBREVE, new Image("View/LearningCompose/graphic/Notes/Semibreve.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_semibreve.png"), ElementDuration.ElementType.NOTE, false));
                this.put("semibreveDottedNote", new ElementDuration(new UnitDuration(ElementDurationMap.SEMIBREVE.getUnitDurationName(), ElementDurationMap.SEMIBREVE.getUnitDurationDivider(), true, 1), new Image("View/LearningCompose/graphic/Notes/Semibreve.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_semibreve.png"), ElementDuration.ElementType.NOTE, true));
                this.put("breveNote", new ElementDuration(ElementDurationMap.BREVE, new Image("View/LearningCompose/graphic/Notes/Breve.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_breve.png"), ElementDuration.ElementType.NOTE, false));
                this.put("breveDottedNote", new ElementDuration(new UnitDuration(ElementDurationMap.BREVE.getUnitDurationName(), ElementDurationMap.BREVE.getUnitDurationDivider(), true, 1), new Image("View/LearningCompose/graphic/Notes/Breve.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_breve.png"), ElementDuration.ElementType.NOTE, true));
                this.put("semiquaverRest", new ElementDuration(ElementDurationMap.SEMIQUAVER, new Image("View/LearningCompose/graphic/Rests/Rest semiquaver.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_rest_semiquaver.png"), ElementDuration.ElementType.REST, false, -18.0));
                this.put("semiquaverDottedRest", new ElementDuration(new UnitDuration(ElementDurationMap.SEMIQUAVER.getUnitDurationName(), ElementDurationMap.SEMIQUAVER.getUnitDurationDivider(), true, 1), new Image("View/LearningCompose/graphic/Rests/Rest semiquaver.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_rest_semiquaver.png"), ElementDuration.ElementType.REST, true, -18.0));
                this.put("quaverRest", new ElementDuration(ElementDurationMap.QUAVER, new Image("View/LearningCompose/graphic/Rests/Rest quaver.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_rest_quaver.png"), ElementDuration.ElementType.REST, false, -18.0));
                this.put("quaverDottedRest", new ElementDuration(new UnitDuration(ElementDurationMap.QUAVER.getUnitDurationName(), ElementDurationMap.QUAVER.getUnitDurationDivider(), true, 1), new Image("View/LearningCompose/graphic/Rests/Rest quaver.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_rest_quaver.png"), ElementDuration.ElementType.REST, true, -18.0));
                this.put("crotchetRest", new ElementDuration(ElementDurationMap.CROTCHET, new Image("View/LearningCompose/graphic/Rests/Rest crotchet.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_rest_crotchet.png"), ElementDuration.ElementType.REST, false, -40.0));
                this.put("crotchetDottedRest", new ElementDuration(new UnitDuration(ElementDurationMap.CROTCHET.getUnitDurationName(), ElementDurationMap.CROTCHET.getUnitDurationDivider(), true, 1), new Image("View/LearningCompose/graphic/Rests/Rest crotchet.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_rest_crotchet.png"), ElementDuration.ElementType.REST, true, -40.0));
                this.put("minimRest", new ElementDuration(ElementDurationMap.MINIM, new Image("View/LearningCompose/graphic/Rests/Rest minim.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_rest_minim.png"), ElementDuration.ElementType.REST, false, -15.0));
                this.put("minimDottedRest", new ElementDuration(new UnitDuration(ElementDurationMap.MINIM.getUnitDurationName(), ElementDurationMap.MINIM.getUnitDurationDivider(), true, 1), new Image("View/LearningCompose/graphic/Rests/Rest minim.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_rest_minim.png"), ElementDuration.ElementType.REST, true, -15.0));
                this.put("semibreveRest", new ElementDuration(ElementDurationMap.SEMIBREVE, new Image("View/LearningCompose/graphic/Rests/Rest semibreve.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_rest_semibreve.png"), ElementDuration.ElementType.REST, false, -28.5));
                this.put("semibreveDottedRest", new ElementDuration(new UnitDuration(ElementDurationMap.SEMIBREVE.getUnitDurationName(), ElementDurationMap.SEMIBREVE.getUnitDurationDivider(), true, 1), new Image("View/LearningCompose/graphic/Rests/Rest semibreve.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_rest_semibreve.png"), ElementDuration.ElementType.REST, true, -28.5));
                this.put("breveRest", new ElementDuration(ElementDurationMap.BREVE, new Image("View/LearningCompose/graphic/Rests/Rest breve.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_rest_breve.png"), ElementDuration.ElementType.REST, false, -28.0));
                this.put("breveDottedRest", new ElementDuration(new UnitDuration(ElementDurationMap.BREVE.getUnitDurationName(), ElementDurationMap.BREVE.getUnitDurationDivider(), true, 1), new Image("View/LearningCompose/graphic/Rests/Rest breve.png"), new Image("View/LearningCompose/graphic/Cursors/cursor_rest_breve.png"), ElementDuration.ElementType.REST, true, -28.0));
            }
        };
    }
}
