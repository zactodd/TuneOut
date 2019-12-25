// 
// Decompiled by Procyon v0.5.36
// 

package Controller.Charts;

import javafx.scene.chart.Chart;
import javafx.scene.chart.Axis;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import java.util.Collection;
import javafx.collections.FXCollections;
import Model.Tutor.TutorSession;
import java.util.HashSet;
import java.util.HashMap;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;

public class PerformanceAcrossTutors extends AbstractChart
{
    private CategoryAxis categoryAxis;
    private NumberAxis numberAxis;
    private static final String CORRECT_SERIES_NAME = "Correct Answers";
    
    public PerformanceAcrossTutors() {
        this.initChart();
    }
    
    @Override
    protected void makeChart() {
        this.initChart();
        final Map<String, Integer> incorrectAnswers = new HashMap<String, Integer>();
        final Map<String, Integer> correctAnswers = new HashMap<String, Integer>();
        final Set<String> tutors = new HashSet<String>();
        for (final TutorSession point : this.data) {
            final String tutorType = point.getTutorType();
            this.updateEntry(correctAnswers, tutorType, point.getNumCorrect());
            this.updateEntry(incorrectAnswers, tutorType, point.getNumIncorrect());
            tutors.add(tutorType);
        }
        this.categoryAxis.setCategories(FXCollections.observableArrayList((Collection)tutors));
        final XYChart.Series correctSeries = new XYChart.Series();
        correctSeries.setName("Correct Answers");
        final XYChart.Series incorrectSeries = new XYChart.Series();
        incorrectSeries.setName("Incorrect Answers");
        for (final String tutor : tutors) {
            correctSeries.getData().add((Object)new XYChart.Data((Object)tutor, (Object)correctAnswers.get(tutor)));
            incorrectSeries.getData().add((Object)new XYChart.Data((Object)tutor, (Object)incorrectAnswers.get(tutor)));
        }
        ((StackedBarChart)this.chart).getData().addAll(new Object[] { correctSeries, incorrectSeries });
        this.categoryAxis.setLabel("Tutors");
        this.numberAxis.setLabel("Question Answered");
    }
    
    private void updateEntry(final Map<String, Integer> map, final String key, final Integer value) {
        final Integer existingValue = map.get(key);
        map.put(key, (existingValue == null) ? value : (existingValue + value));
    }
    
    private void initChart() {
        this.title = "Performance Across Tutors";
        this.categoryAxis = new CategoryAxis();
        this.numberAxis = new NumberAxis();
        this.chart = (Chart)new StackedBarChart((Axis)this.categoryAxis, (Axis)this.numberAxis);
        this.categoryAxis.setLabel("Tutors");
        this.numberAxis.setLabel("Question Answered");
    }
}
