// 
// Decompiled by Procyon v0.5.36
// 

package Model.File;

import java.util.HashMap;
import Model.Note.unitDuration.UnitDurationMap;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import Model.Note.NoteMap;
import java.util.Iterator;
import java.util.List;
import Model.Note.Melody.PlayStyle;
import Model.Note.Note;
import org.jsoup.nodes.Element;
import Model.Note.Melody.NoteCollection;
import java.util.ArrayList;
import Model.Note.Melody.Melody;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import java.io.File;
import java.util.Map;
import Model.Note.unitDuration.UnitDuration;

public class TuneOutMusicXmlFile extends TuneOutFile
{
    private static final String STEP_XML = "step";
    private static final String ALTER_XML = "alter";
    private static final String OCTAVE_XML = "octave";
    private static final String CHORD_XML = "chord";
    private static final String REST_XML = "rest";
    private static final String DURATION_XML = "duration";
    private static final String STAFF_XML = "staff";
    private static final UnitDuration DEMISEMIQUAVER;
    private static final UnitDuration SEMIQUAVER;
    private static final UnitDuration QUAVER;
    private static final UnitDuration CROTCHET;
    private static final UnitDuration MINIM;
    private static final UnitDuration SEMIBREVE;
    private static final UnitDuration BREVE;
    private static final Map<Integer, String> ALTER_MAP;
    private static final Map<Double, UnitDuration> DURATION_MAP;
    private static final String NOTE_FORMAT = "%s%s%s";
    
    public TuneOutMusicXmlFile(final File file) {
        super(file);
    }
    
    @Override
    public boolean isValid(final String fileText) {
        if (fileText.isEmpty()) {
            return false;
        }
        final Document doc = Jsoup.parse(fileText, "", Parser.xmlParser());
        return !doc.getElementsByTag("note").isEmpty();
    }
    
    public Melody parseMelodyFromXML(final String xml, final String melodyName) {
        final Document doc = Jsoup.parse(xml, "", Parser.xmlParser());
        final List<NoteCollection> noteCollections = new ArrayList<NoteCollection>();
        final String division = doc.select("divisions").get(0).text();
        final Integer divisionInt = Integer.parseInt(division);
        List<Note> chord = new ArrayList<Note>();
        final Element firstPart = doc.select("part").get(0);
        for (final Element noteElement : firstPart.select("note")) {
            final String staff = noteElement.select("staff").text();
            if (staff.equals("1") || staff.isEmpty()) {
                Boolean startChord = false;
                final UnitDuration duration = this.parseDuration(noteElement, divisionInt);
                if (!noteElement.select("rest").isEmpty()) {
                    chord = new ArrayList<Note>();
                    final Note rest = new Note("R", -1, -1, true);
                    chord.add(rest);
                }
                else {
                    if (!noteElement.select("chord").isEmpty()) {
                        startChord = true;
                    }
                    else {
                        chord = new ArrayList<Note>();
                        startChord = false;
                    }
                    chord.add(this.parseNote(noteElement));
                }
                if (startChord) {
                    noteCollections.remove(noteCollections.size() - 1);
                }
                noteCollections.add(new NoteCollection(chord, duration, PlayStyle.SIMULTANEOUS));
            }
        }
        final Melody melody = new Melody(melodyName);
        melody.setNoteCollection(noteCollections);
        return melody;
    }
    
    private UnitDuration parseDuration(final Element noteElement, final Integer division) {
        final String duration = noteElement.select("duration").text();
        if (duration.isEmpty()) {
            return null;
        }
        final Integer durationInt = Integer.parseInt(duration);
        final Double durationDivided = 1.0 / division * durationInt;
        return TuneOutMusicXmlFile.DURATION_MAP.get(durationDivided);
    }
    
    private Note parseNote(final Element noteElement) {
        final String step = noteElement.select("step").text();
        final String octave = noteElement.select("octave").text();
        String alter = noteElement.select("alter").text();
        if (!alter.isEmpty()) {
            final Integer alterInt = Integer.parseInt(alter);
            alter = TuneOutMusicXmlFile.ALTER_MAP.get(alterInt);
        }
        final String noteStr = String.format("%s%s%s", step, alter, octave);
        return NoteMap.getNote(noteStr);
    }
    
    public String extractXMLFile() throws IOException {
        String returnString = "";
        final ZipFile zipFile = new ZipFile(this.file);
        final Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            final ZipEntry entry = (ZipEntry)entries.nextElement();
            final String filename = entry.toString();
            if (filename.endsWith(".xml") && !filename.equals("META-INF/container.xml")) {
                final InputStream stream = zipFile.getInputStream(entry);
                returnString = convertStreamToString(stream);
            }
        }
        return returnString;
    }
    
    private static String convertStreamToString(final InputStream is) {
        final Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
    
    static {
        DEMISEMIQUAVER = UnitDurationMap.getUnitDurationByName("Demisemiquaver");
        SEMIQUAVER = UnitDurationMap.getUnitDurationByName("Semiquaver");
        QUAVER = UnitDurationMap.getUnitDurationByName("Quaver");
        CROTCHET = UnitDurationMap.getUnitDurationByName("Crotchet");
        MINIM = UnitDurationMap.getUnitDurationByName("Minim");
        SEMIBREVE = UnitDurationMap.getUnitDurationByName("Semibreve");
        BREVE = UnitDurationMap.getUnitDurationByName("Breve");
        ALTER_MAP = new HashMap<Integer, String>() {
            {
                this.put(1, "#");
                this.put(2, "x");
                this.put(-1, "b");
                this.put(-2, "bb");
            }
        };
        DURATION_MAP = new HashMap<Double, UnitDuration>() {
            {
                this.put(0.125, TuneOutMusicXmlFile.DEMISEMIQUAVER);
                this.put(0.25, TuneOutMusicXmlFile.SEMIQUAVER);
                this.put(0.5, TuneOutMusicXmlFile.QUAVER);
                this.put(0.75, new UnitDuration(TuneOutMusicXmlFile.QUAVER.getUnitDurationName(), TuneOutMusicXmlFile.QUAVER.getUnitDurationDivider(), true, 1));
                this.put(1.0, TuneOutMusicXmlFile.CROTCHET);
                this.put(1.5, new UnitDuration(TuneOutMusicXmlFile.CROTCHET.getUnitDurationName(), TuneOutMusicXmlFile.CROTCHET.getUnitDurationDivider(), true, 1));
                this.put(2.0, TuneOutMusicXmlFile.MINIM);
                this.put(3.0, new UnitDuration(TuneOutMusicXmlFile.MINIM.getUnitDurationName(), TuneOutMusicXmlFile.MINIM.getUnitDurationDivider(), true, 1));
                this.put(4.0, TuneOutMusicXmlFile.SEMIBREVE);
                this.put(6.0, new UnitDuration(TuneOutMusicXmlFile.SEMIBREVE.getUnitDurationName(), TuneOutMusicXmlFile.SEMIBREVE.getUnitDurationDivider(), true, 1));
                this.put(8.0, TuneOutMusicXmlFile.BREVE);
            }
        };
    }
}
