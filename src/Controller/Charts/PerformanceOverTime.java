

package Controller.Charts;

import javafx.scene.chart.Chart;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import Model.Tutor.TutorSession;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.collections.FXCollections;
import java.time.format.DateTimeFormatter;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;

public class PerformanceOverTime extends AbstractChart
{
    private CategoryAxis categoryAxis;
    private NumberAxis numberAxis;
    private DateTimeFormatter formatter;
    
    public PerformanceOverTime() {
        this.formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        this.initChart();
    }
    
    @Override
    protected void makeChart() {
        this.initChart();
        final ObservableList<XYChart.Data<String, Number>> correct = (ObservableList<XYChart.Data<String, Number>>)FXCollections.observableArrayList();
        final ObservableList<XYChart.Data<String, Number>> total = (ObservableList<XYChart.Data<String, Number>>)FXCollections.observableArrayList();
        final SortedList<XYChart.Data<String, Number>> sortedNumberCorrect = (SortedList<XYChart.Data<String, Number>>)new SortedList((ObservableList)correct, (data1, data2) -> ((String)data1.getXValue()).compareTo((String)data2.getXValue()));
        final SortedList<XYChart.Data<String, Number>> sortedTotal = (SortedList<XYChart.Data<String, Number>>)new SortedList((ObservableList)total, (data1, data2) -> ((String)data1.getXValue()).compareTo((String)data2.getXValue()));
        for (final TutorSession point : this.data) {
            final String formattedDate = this.formatter.format(point.getDateTime());
            this.addData(correct, formattedDate, point.getNumCorrect());
            this.addData(total, formattedDate, point.getNumQuestions());
        }
        final XYChart.Series<String, Number> correctSeries = (XYChart.Series<String, Number>)new XYChart.Series((ObservableList)sortedNumberCorrect);
        correctSeries.setName("Correct Answers");
        final XYChart.Series<String, Number> totalSeries = (XYChart.Series<String, Number>)new XYChart.Series((ObservableList)sortedTotal);
        totalSeries.setName("Total Answers");
        ((LineChart)this.chart).getData().add((Object)correctSeries);
        ((LineChart)this.chart).getData().add((Object)totalSeries);
    }
    
    private void addData(final ObservableList<XYChart.Data<String, Number>> data, final String formattedDate, final Integer value) {
        final XYChart.Data<String, Number> newData;
        final XYChart.Data<String, Number> dataAtDate = (XYChart.Data<String, Number>)data.stream().filter(d -> ((String)d.getXValue()).equals(formattedDate)).findAny().orElseGet(() -> {
            newData = (XYChart.Data<String, Number>)new XYChart.Data((Object)formattedDate, (Object)value);
            data.add((Object)newData);
            return newData;
        });
        dataAtDate.setYValue((Object)(((Number)dataAtDate.getYValue()).doubleValue() + value));
    }
    
    private void initChart() {
        this.title = "Performance Over Time";
        this.categoryAxis = new CategoryAxis();
        this.numberAxis = new NumberAxis();
        this.chart = (Chart)new LineChart((Axis)this.categoryAxis, (Axis)this.numberAxis);
        this.numberAxis.setLabel("Question Answered");
        this.categoryAxis.setLabel("Date");
    }
}
