// 
// Decompiled by Procyon v0.5.36
// 

package Controller.Charts;

import javafx.scene.chart.Chart;
import Model.Tutor.TutorSession;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import java.util.Collection;
import javafx.collections.FXCollections;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.chart.CategoryAxis;

public class TutorUsage extends AbstractChart
{
    private CategoryAxis categoryAxis;
    private Map<String, Integer> answered;
    
    public TutorUsage() {
        this.answered = new HashMap<String, Integer>();
        this.colorMap = new HashMap<String, String>();
        this.initChart();
    }
    
    @Override
    protected void makeChart() {
        this.initChart();
        this.data.forEach(point -> this.updateAnswered(point.getTutorType(), point.getNumQuestions()));
        this.categoryAxis.setCategories(FXCollections.observableArrayList((Collection)this.answered.keySet()));
        final ObservableList<PieChart.Data> pieChartData = (ObservableList<PieChart.Data>)FXCollections.observableArrayList();
        this.answered.forEach((tutor, value) -> pieChartData.add((Object)new PieChart.Data(tutor, (double)value)));
        this.chart = (Chart)new PieChart((ObservableList)pieChartData);
    }
    
    private void initChart() {
        this.title = "Tutor Usage";
        this.chart = (Chart)new PieChart();
        (this.categoryAxis = new CategoryAxis()).setLabel("Date");
        this.answered = new HashMap<String, Integer>();
    }
    
    private void updateAnswered(final String key, final Integer value) {
        final Integer existingValue = this.answered.get(key);
        this.answered.put(key, (existingValue == null) ? value : (existingValue + value));
    }
}
