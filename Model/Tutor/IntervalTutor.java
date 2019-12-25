// 
// Decompiled by Procyon v0.5.36
// 

package Model.Tutor;

import java.util.Iterator;
import java.util.Arrays;
import java.util.HashMap;
import Model.Note.Intervals.Interval;
import java.util.ArrayList;
import Model.Note.NoteMap;
import Model.Note.Intervals.IntervalMap;
import Model.Note.unitDuration.UnitDurationInformation;
import org.apache.log4j.Logger;
import Model.Note.unitDuration.UnitDuration;
import java.util.List;

public class IntervalTutor extends Tutor
{
    private int numOfOctaves;
    private String intervalType;
    private List<String> intervalRangeArray;
    private UnitDuration unitDuration;
    private static Logger log;
    
    public IntervalTutor() {
        this.unitDuration = UnitDurationInformation.getUnitDuration();
    }
    
    @Override
    public void generateQuestion() {
        ++this.currentQuestionNumber;
        while (this.questions.containsKey(this.currentQuestionNumber) && this.questions.get(this.currentQuestionNumber).isCorrect) {
            ++this.currentQuestionNumber;
        }
        if (this.currentQuestionNumber <= this.numQuestions && !this.questions.containsKey(this.currentQuestionNumber)) {
            final Question currentQuestion = new Question("The interval will play. What interval is this?");
            final int[] validInterval = this.generateValidInterval();
            currentQuestion.firstRandomNote = validInterval[0];
            currentQuestion.secondRandomNote = validInterval[1];
            currentQuestion.correctAnswer = IntervalMap.getIntervalWithSemitone(currentQuestion.secondRandomNote).getPrettyIntervalName().replace("\"", "");
            this.questions.put(this.currentQuestionNumber, currentQuestion);
        }
    }
    
    private int[] generateValidInterval() {
        final int[] possiblyInterval = new int[2];
        int tonic;
        Integer intervalSemitone;
        for (boolean validInterval = false; !validInterval; validInterval = true, possiblyInterval[0] = tonic, possiblyInterval[1] = intervalSemitone) {
            do {
                tonic = Tutor.randInt(0, 127);
            } while (tonic == -1 || (this.currentQuestionNumber > 1 && tonic == this.questions.get(this.currentQuestionNumber - 1).firstRandomNote));
            final int randomIntervalPos = Tutor.randInt(0, this.intervalRangeArray.size() - 1);
            final String interval = this.intervalRangeArray.get(randomIntervalPos);
            intervalSemitone = IntervalMap.getIntervalWithIntervalName(interval).getSemitone();
            final String tonicNote = NoteMap.getNoteString(tonic);
            final String resultNote = NoteMap.getSemitone(tonicNote, intervalSemitone);
            if (resultNote != null) {}
        }
        return possiblyInterval;
    }
    
    @Override
    public void runQuestion(final int gapAtStart) {
        if (this.questions.containsKey(this.currentQuestionNumber)) {
            final int tonic = this.questions.get(this.currentQuestionNumber).firstRandomNote;
            final int interval = this.questions.get(this.currentQuestionNumber).secondRandomNote;
            final String tonicNote = NoteMap.getNoteString(tonic);
            final String resultNote = NoteMap.getSemitone(tonicNote, interval);
            this.play.playTwoNotes(gapAtStart, tonic, 3, NoteMap.getMidi(resultNote), this.unitDuration);
        }
    }
    
    @Override
    public void setOptions(final List<String> options) {
        this.intervalRangeArray = new ArrayList<String>();
        List<Interval> intervals = new ArrayList<Interval>();
        this.currentQuestionNumber = 0;
        this.questions = new HashMap<Integer, Question>();
        try {
            this.numQuestions = Integer.parseInt(options.get(0));
            if (options.get(1).startsWith("[")) {
                final String intervalsInStr = options.get(1).replace("[", "").replace("]", "").replace("\"", "");
                final List<String> intervalsStrList = Arrays.asList(intervalsInStr.split("\\s*,\\s*"));
                for (final String intervalStr : intervalsStrList) {
                    intervals.add(IntervalMap.getIntervalWithIntervalName(intervalStr));
                }
            }
            else {
                this.numOfOctaves = Integer.parseInt(options.get(1));
                this.intervalType = options.get(2).replace("\"", "");
                if (this.intervalType.equals("Major")) {
                    intervals = IntervalMap.filterIntervalsWithDelimiter("Major");
                    intervals = IntervalMap.filterIntervalsWithMaxSemitone(intervals, this.numOfOctaves * 12);
                }
                else if (this.intervalType.equals("Minor")) {
                    intervals = IntervalMap.filterIntervalsWithDelimiter("Minor");
                    intervals = IntervalMap.filterIntervalsWithMaxSemitone(intervals, this.numOfOctaves * 12);
                }
                else {
                    intervals = IntervalMap.filterIntervalsWithMaxSemitone(IntervalMap.getAllIntervals(), this.numOfOctaves * 12);
                }
            }
            for (final Interval interval : intervals) {
                this.intervalRangeArray.add(interval.getPrettyIntervalName());
            }
        }
        catch (Exception e) {
            IntervalTutor.log.error(e.getMessage());
        }
    }
    
    static {
        IntervalTutor.log = Logger.getLogger(IntervalTutor.class.getName());
    }
}
