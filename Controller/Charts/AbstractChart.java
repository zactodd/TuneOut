

package Controller.Charts;

import java.util.Map;
import Model.Tutor.TutorSession;
import javafx.collections.ObservableList;
import javafx.scene.chart.Chart;

public abstract class AbstractChart
{
    protected String title;
    protected Chart chart;
    protected ObservableList<TutorSession> data;
    protected Map<String, String> colorMap;
    
    public Chart update(final ObservableList<TutorSession> data) {
        this.data = data;
        this.makeChart();
        this.chart.setTitle(this.title);
        this.chart.setAnimated(false);
        return this.chart;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    protected abstract void makeChart();
}
