

package Model.Note.Intervals;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class IntervalMap
{
    public static final String ALL_INTERVALS = "All";
    public static final String MAJOR_ONLY = "Major";
    public static final String MINOR_ONLY = "Minor";
    public static final int AN_OCTAVE_SEMITONE = 12;
    public static final int MAX_OCTAVE = 2;
    public static final int MAX_OCTAVE_SEMITONE = 24;
    private static List<Interval> allIntervals;
    
    public static List<Interval> getAllIntervals() {
        return IntervalMap.allIntervals;
    }
    
    public static List<Interval> filterIntervalsWithMaxSemitone(final List<Interval> intervals, final Integer maxSemitone) {
        final List<Interval> uniqueInterval = new ArrayList<Interval>();
        for (final Interval interval : intervals) {
            if (interval.getSemitone() <= maxSemitone && interval.isPrimary()) {
                uniqueInterval.add(interval);
            }
        }
        return uniqueInterval;
    }
    
    public static List<Interval> filterIntervalsWithDelimiter(final String delimiter) {
        final List<Interval> filteredInterval = new ArrayList<Interval>();
        for (final Interval interval : IntervalMap.allIntervals) {
            if (interval.getPrettyIntervalName().startsWith(delimiter)) {
                filteredInterval.add(interval);
            }
            else {
                if (!interval.getRawIntervalName().startsWith(delimiter)) {
                    continue;
                }
                filteredInterval.add(interval);
            }
        }
        return filteredInterval;
    }
    
    public static Interval getIntervalWithSemitone(final Integer semitone) {
        for (final Interval interval : IntervalMap.allIntervals) {
            if (semitone == interval.getSemitone()) {
                return interval;
            }
        }
        return null;
    }
    
    public static Interval getIntervalWithIntervalName(final String name) {
        for (final Interval interval : IntervalMap.allIntervals) {
            if (name.matches(interval.getTheoryIntervalName()) || name.equalsIgnoreCase(interval.getRawIntervalName()) || name.equalsIgnoreCase(interval.getPrettyIntervalName())) {
                return interval;
            }
        }
        return null;
    }
    
    public static Boolean hasEnharmonicEquivalent(final String intervalName) {
        final Interval interval = getIntervalWithIntervalName(intervalName);
        final Integer semitone = interval.getSemitone();
        for (final Interval tempInterval : IntervalMap.allIntervals) {
            if (!interval.equals(tempInterval) && tempInterval.getSemitone().equals(semitone)) {
                return true;
            }
        }
        return false;
    }
    
    public static Interval getEnharmonicEquivalent(final String intervalName) {
        final Interval interval = getIntervalWithIntervalName(intervalName);
        final Integer semitone = interval.getSemitone();
        for (final Interval tempInterval : IntervalMap.allIntervals) {
            if (!interval.equals(tempInterval) && tempInterval.getSemitone().equals(semitone)) {
                return tempInterval;
            }
        }
        return null;
    }
    
    static {
        IntervalMap.allIntervals = new ArrayList<Interval>() {
            {
                final String quality_major_1 = "maj";
                final String quality_major_2 = "M";
                final String quality_major_3 = "Major ";
                final String quality_perfect_1 = "per";
                final String quality_perfect_2 = "P";
                final String quality_perfect_3 = "Perfect ";
                final String quality_diminished_1 = "dim";
                final String quality_diminished_2 = "d";
                final String quality_diminished_3 = "Diminished ";
                final String quality_minor_1 = "min";
                final String quality_minor_2 = "m";
                final String quality_minor_3 = "Minor ";
                final String quality_augmented_1 = "aug";
                final String quality_augmented_2 = "A";
                final String quality_augmented_3 = "Augmented ";
                final String ending_nd = "nd";
                final String ending_rd = "rd";
                final String ending_th = "th";
                int octave = 1;
                int semitone = 0;
                while (semitone < 24) {
                    if (semitone == 0) {
                        this.add(new Interval(Arrays.asList("unison", quality_perfect_2 + octave, "Perfect Unison"), semitone, true));
                    }
                    ++semitone;
                    ++octave;
                    if (semitone == 1) {
                        this.add(new Interval(Arrays.asList(quality_minor_1 + octave, quality_minor_2 + octave, quality_minor_3 + octave + ending_nd), semitone, true));
                    }
                    else {
                        this.add(new Interval(Arrays.asList(quality_minor_1 + octave, quality_minor_2 + octave, quality_minor_3 + octave + ending_th), semitone, true));
                    }
                    if (++semitone == 2) {
                        this.add(new Interval(Arrays.asList(quality_major_1 + octave, quality_major_2 + octave, quality_major_3 + octave + ending_nd), semitone, true));
                    }
                    else {
                        this.add(new Interval(Arrays.asList(quality_major_1 + octave, quality_major_2 + octave, quality_major_3 + octave + ending_th), semitone, true));
                    }
                    ++semitone;
                    ++octave;
                    if (semitone == 3) {
                        this.add(new Interval(Arrays.asList(quality_minor_1 + octave, quality_minor_2 + octave, quality_minor_3 + octave + ending_rd), semitone, true));
                    }
                    else {
                        this.add(new Interval(Arrays.asList(quality_minor_1 + octave, quality_minor_2 + octave, quality_minor_3 + octave + ending_th), semitone, true));
                    }
                    if (++semitone == 4) {
                        this.add(new Interval(Arrays.asList(quality_major_1 + octave, quality_major_2 + octave, quality_major_3 + octave + ending_rd), semitone, true));
                    }
                    else {
                        this.add(new Interval(Arrays.asList(quality_major_1 + octave, quality_major_2 + octave, quality_major_3 + octave + ending_th), semitone, true));
                    }
                    ++semitone;
                    ++octave;
                    this.add(new Interval(Arrays.asList(quality_perfect_1 + octave, quality_perfect_2 + octave, quality_perfect_3 + octave + ending_th), semitone, true));
                    ++semitone;
                    this.add(new Interval(Arrays.asList(quality_augmented_1 + octave, quality_augmented_2 + octave, quality_augmented_3 + octave + ending_th), semitone, true));
                    ++octave;
                    this.add(new Interval(Arrays.asList(quality_diminished_1 + octave, quality_diminished_2 + octave, quality_diminished_3 + octave + ending_th), semitone, false));
                    ++semitone;
                    this.add(new Interval(Arrays.asList(quality_perfect_1 + octave, quality_perfect_2 + octave, quality_perfect_3 + octave + ending_th), semitone, true));
                    ++semitone;
                    ++octave;
                    this.add(new Interval(Arrays.asList(quality_minor_1 + octave, quality_minor_2 + octave, quality_minor_3 + octave + ending_th), semitone, true));
                    ++semitone;
                    this.add(new Interval(Arrays.asList(quality_major_1 + octave, quality_major_2 + octave, quality_major_3 + octave + ending_th), semitone, true));
                    ++octave;
                    this.add(new Interval(Arrays.asList(quality_diminished_1 + octave, quality_diminished_2 + octave, quality_diminished_3 + octave + ending_th), semitone, false));
                    ++semitone;
                    this.add(new Interval(Arrays.asList(quality_minor_1 + octave, quality_minor_2 + octave, quality_minor_3 + octave + ending_th), semitone, true));
                    ++semitone;
                    this.add(new Interval(Arrays.asList(quality_major_1 + octave, quality_major_2 + octave, quality_major_3 + octave + ending_th), semitone, true));
                    ++semitone;
                    ++octave;
                    if (semitone == 12) {
                        this.add(new Interval(Arrays.asList("octave", quality_perfect_2 + octave, "Perfect Octave"), semitone, true));
                    }
                    else {
                        this.add(new Interval(Arrays.asList(quality_perfect_1 + octave, quality_perfect_2 + octave, quality_perfect_3 + octave + ending_th), semitone, true));
                    }
                }
            }
        };
    }
}
