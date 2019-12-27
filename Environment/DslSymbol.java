

package Environment;

public interface DslSymbol
{
    public static final int COMMAND_INTERVALTUTOR = 26;
    public static final int COMMAND_FUNCTION_OF = 52;
    public static final int COMMAND_PLAY_MELODY = 73;
    public static final int COMMAND_PARENT_OF = 53;
    public static final int COMMAND_HASENHARMONIC = 6;
    public static final int COMMAND_INTERVAL = 17;
    public static final int REST = 85;
    public static final int COMMAND_HELP = 10;
    public static final int COMMAND_MODE_OF = 54;
    public static final int COMMAND_TUTORQUESTION = 19;
    public static final int COMMAND_QUALITY_OF = 50;
    public static final int COMMAND_TUTORANSWER = 18;
    public static final int SIGNATURE = 82;
    public static final int COMMAND_TUTOR_STATS = 64;
    public static final int COMMAND_REST = 38;
    public static final int COMMAND_ENHARMONICHIGH = 8;
    public static final int COMMA = 69;
    public static final int COMMAND_SET_TERM = 30;
    public static final int CLOSE_SQUARE_PARENTHESIS = 71;
    public static final int COMMAND_SET_INSTRUMENT = 59;
    public static final int COMMAND_CHORD_TUTOR = 40;
    public static final int COMMAND_PLAY_PERCUSSION_LOOP = 65;
    public static final int COMMAND_KEY_SIGNATURE = 31;
    public static final int COMMAND_ADD_PERCUSSION_LOOP = 66;
    public static final int COMMAND_SHOWVERSION = 2;
    public static final int COMMAND_SCALETUTOR = 37;
    public static final int COMMAND_CROTCHET = 29;
    public static final int COMMAND_PLAY_CHORD = 39;
    public static final int COMMAND_LIST_UNIT_DURATIONS = 48;
    public static final int COMMAND_SCALE_MODE_TUTOR = 58;
    public static final int COMMAND_GET_INSTRUMENT = 57;
    public static final int ARRAY = 84;
    public static final int CLOSE_SQUIGGLY_PARENTHESIS = 75;
    public static final int COMMAND_HAS_ENHARMONIC_INTERVAL = 44;
    public static final int COMMAND_CHORD = 28;
    public static final int OVERRIDES = 78;
    public static final int COMMAND_TEMPO = 11;
    public static final int COMMAND_MIDI = 3;
    public static final int COMMAND_PITCHTUTOR = 14;
    public static final int NUMBER = 79;
    public static final int COMMAND_PLAY_INTERVAL = 25;
    public static final int COMMAND_LANGUAGE_OF = 35;
    public static final int COMMAND_TUTORSAVE = 36;
    public static final int COMMAND_LIST_INSTRUMENTS = 56;
    public static final int QUOTE = 76;
    public static final int COMMAND_SET_PATTERN = 60;
    public static final int COMMAND_SET_MELODY = 72;
    public static final int COMMAND_MUSICAL_TERM_TUTOR = 27;
    public static final int OCTAVE = 81;
    public static final int COMMAND_CHORD_SPELLING_TUTOR = 49;
    public static final int COMMAND_PLAY_SCALE = 23;
    public static final int OPEN_SQUARE_PARENTHESIS = 70;
    public static final int COMMAND_SWING = 43;
    public static final int NOTE = 77;
    public static final int COMMAND_TUTORSTATS = 20;
    public static final int COMMAND_TUTORREPEAT = 16;
    public static final int COMMAND_SEMITONE = 5;
    public static final int COMMAND_SCALE = 22;
    public static final int OPEN_PARENTHESIS = 67;
    public static final int EOF = 0;
    public static final int COMMAND_NOTE = 4;
    public static final int FUNCTION = 83;
    public static final int COMMAND_GET_UNIT_DURATION = 46;
    public static final int COMMAND_CHORD_FUNCTION = 51;
    public static final int COMMAND_ENHARMONIC = 7;
    public static final int COMMAND_KEY_SIGNATURE_TUTOR = 32;
    public static final int error = 1;
    public static final int COMMAND_SET_UNIT_DURATION = 47;
    public static final int COMMAND_ENHARMONIC_INTERVAL = 45;
    public static final int COMMAND_PLAY = 13;
    public static final int COMMAND_SET_TEMPO = 12;
    public static final int COMMAND_CHORD_FINDER = 41;
    public static final int COMMAND_MEANING_OF = 34;
    public static final int STRING = 80;
    public static final int COMMAND_TUTORRUN = 21;
    public static final int COMMAND_TUTORSTART = 15;
    public static final int COMMAND_LIST_PERCUSSION_INSTRUMENTS = 63;
    public static final int COMMAND_LIST_PATTERNS = 61;
    public static final int COMMAND_CATEGORY_OF = 33;
    public static final int OPEN_SQUIGGLY_PARENTHESIS = 74;
    public static final int COMMAND_PLAY_PERCUSSION = 62;
    public static final int CLOSE_PARENTHESIS = 68;
    public static final int COMMAND_CHORD_FUNCTION_TUTOR = 55;
    public static final int COMMAND_SET_SWING = 42;
    public static final int COMMAND_GETINTERVALSEMITONE = 24;
    public static final int COMMAND_ENHARMONICLOW = 9;
    public static final String[] terminalNames = { "EOF", "error", "COMMAND_SHOWVERSION", "COMMAND_MIDI", "COMMAND_NOTE", "COMMAND_SEMITONE", "COMMAND_HASENHARMONIC", "COMMAND_ENHARMONIC", "COMMAND_ENHARMONICHIGH", "COMMAND_ENHARMONICLOW", "COMMAND_HELP", "COMMAND_TEMPO", "COMMAND_SET_TEMPO", "COMMAND_PLAY", "COMMAND_PITCHTUTOR", "COMMAND_TUTORSTART", "COMMAND_TUTORREPEAT", "COMMAND_INTERVAL", "COMMAND_TUTORANSWER", "COMMAND_TUTORQUESTION", "COMMAND_TUTORSTATS", "COMMAND_TUTORRUN", "COMMAND_SCALE", "COMMAND_PLAY_SCALE", "COMMAND_GETINTERVALSEMITONE", "COMMAND_PLAY_INTERVAL", "COMMAND_INTERVALTUTOR", "COMMAND_MUSICAL_TERM_TUTOR", "COMMAND_CHORD", "COMMAND_CROTCHET", "COMMAND_SET_TERM", "COMMAND_KEY_SIGNATURE", "COMMAND_KEY_SIGNATURE_TUTOR", "COMMAND_CATEGORY_OF", "COMMAND_MEANING_OF", "COMMAND_LANGUAGE_OF", "COMMAND_TUTORSAVE", "COMMAND_SCALETUTOR", "COMMAND_REST", "COMMAND_PLAY_CHORD", "COMMAND_CHORD_TUTOR", "COMMAND_CHORD_FINDER", "COMMAND_SET_SWING", "COMMAND_SWING", "COMMAND_HAS_ENHARMONIC_INTERVAL", "COMMAND_ENHARMONIC_INTERVAL", "COMMAND_GET_UNIT_DURATION", "COMMAND_SET_UNIT_DURATION", "COMMAND_LIST_UNIT_DURATIONS", "COMMAND_CHORD_SPELLING_TUTOR", "COMMAND_QUALITY_OF", "COMMAND_CHORD_FUNCTION", "COMMAND_FUNCTION_OF", "COMMAND_PARENT_OF", "COMMAND_MODE_OF", "COMMAND_CHORD_FUNCTION_TUTOR", "COMMAND_LIST_INSTRUMENTS", "COMMAND_GET_INSTRUMENT", "COMMAND_SCALE_MODE_TUTOR", "COMMAND_SET_INSTRUMENT", "COMMAND_SET_PATTERN", "COMMAND_LIST_PATTERNS", "COMMAND_PLAY_PERCUSSION", "COMMAND_LIST_PERCUSSION_INSTRUMENTS", "COMMAND_TUTOR_STATS", "COMMAND_PLAY_PERCUSSION_LOOP", "COMMAND_ADD_PERCUSSION_LOOP", "OPEN_PARENTHESIS", "CLOSE_PARENTHESIS", "COMMA", "OPEN_SQUARE_PARENTHESIS", "CLOSE_SQUARE_PARENTHESIS", "COMMAND_SET_MELODY", "COMMAND_PLAY_MELODY", "OPEN_SQUIGGLY_PARENTHESIS", "CLOSE_SQUIGGLY_PARENTHESIS", "QUOTE", "NOTE", "OVERRIDES", "NUMBER", "STRING", "OCTAVE", "SIGNATURE", "FUNCTION", "ARRAY", "REST" };
}
