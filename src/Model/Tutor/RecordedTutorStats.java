

package Model.Tutor;

import java.util.Set;
import java.util.LinkedHashSet;
import java.time.LocalDateTime;
import java.io.File;
import Model.File.TuneOutTutorFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;

public class RecordedTutorStats
{
    private static boolean saveStats;
    
    public static boolean isSaveStats() {
        return RecordedTutorStats.saveStats;
    }
    
    public static void setSaveStats(final boolean saveStats) {
        RecordedTutorStats.saveStats = saveStats;
    }
    
    public static Collection<TutorSession> convertTutorDataToSessions(final String tutorData, final Boolean imported) {
        final Collection<TutorSession> tutorSessions = new ArrayList<TutorSession>();
        TutorSession session = new TutorSession();
        String date = "";
        boolean isLine = false;
        final List<String> lines = new ArrayList<String>(Arrays.asList(tutorData.split("\n")));
        for (final String line : lines) {
            if (line.contains("TuneOut Tutor File")) {
                date = line.replace("TuneOut Tutor File ", "");
            }
            if (isLine && line.contains("Tutor")) {
                session = new TutorSession();
                session.setImported(imported);
                session.setTutorType(getMatch(line, ".* Tutor"));
                session.setDateTime(date, getMatch(line, "\\d\\d:\\d\\d:\\d\\d"));
                session.setUser(getMatch(line, "User: .*").replace("User: ", ""));
                isLine = false;
            }
            if (!isLine && line.contains("Questions answered: ")) {
                session.setNumQuestions(Integer.parseInt(getMatch(line, "\\d+")));
            }
            if (!isLine && line.contains("incorrect")) {
                session.setNumIncorrect(Integer.parseInt(getMatch(line, "\\d+")));
            }
            if (!isLine && line.contains("% correct")) {
                session.setPercentCorrect(Double.parseDouble(getMatch(line, "\\d.*%").replace("%", "")));
                if (session.getNumQuestions() > 0) {
                    tutorSessions.add(session);
                }
            }
            if (!isLine && line.startsWith("Congratulations")) {
                session.setNumIncorrect(0);
                session.setPercentCorrect(100.0);
                if (session.getNumQuestions() > 0) {
                    tutorSessions.add(session);
                }
            }
            if (line.contains("---")) {
                isLine = true;
            }
        }
        return tutorSessions;
    }
    
    private static String getMatch(final String line, final String match) {
        final Pattern pattern = Pattern.compile(match);
        final Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }
    
    public static List<TutorSession> getTutorSessionData() {
        final List<TutorSession> tutorSessions = new ArrayList<TutorSession>();
        final TuneOutTutorFile emptyFile = new TuneOutTutorFile();
        final File tutorDirectory = emptyFile.getTutorDirectory();
        final File[] directoryListing = tutorDirectory.listFiles();
        if (directoryListing != null) {
            for (final File fileInDir : directoryListing) {
                final TuneOutTutorFile tutorFile = new TuneOutTutorFile(fileInDir);
                tutorSessions.addAll(convertTutorDataToSessions(tutorFile.fileToText(fileInDir), false));
            }
        }
        return tutorSessions;
    }
    
    public static List<TutorSession> filterTutorSessionData(final List<TutorSession> sessionData, final List<String> users, final List<String> tutors, final LocalDateTime from, final LocalDateTime to) {
        final Set<TutorSession> filteredTutorSessions = new LinkedHashSet<TutorSession>();
        for (final TutorSession session : sessionData) {
            if (users.contains(session.getUser()) && tutors.contains(session.getTutorType()) && session.getDateTime().isAfter(from) && session.getDateTime().isBefore(to)) {
                filteredTutorSessions.add(session);
            }
        }
        return new ArrayList<TutorSession>(filteredTutorSessions);
    }
    
    static {
        RecordedTutorStats.saveStats = false;
    }
}
