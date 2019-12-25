// 
// Decompiled by Procyon v0.5.36
// 

package Controller;

import javafx.scene.layout.FlowPane;
import java.util.Iterator;
import javafx.scene.layout.HBox;
import javafx.scene.Node;
import java.io.IOException;
import Environment.Environment;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;

public abstract class TutorController
{
    AnchorPane tutorOptionsAnchorPane;
    protected GridPane tutorPane;
    AnchorPane tutorAnswersAnchorPane;
    AnchorPane tutorResultsPane;
    TutorResultsController tutorResultsController;
    
    public abstract void initialize(final OuterTemplateController p0, final Environment p1) throws IOException;
    
    public GridPane getContent() {
        return this.tutorPane;
    }
    
    public Node getOptionButtonPane() {
        final Node parent = (Node)this.tutorOptionsAnchorPane.getChildren().get(0);
        final Node nodeOut = (Node)this.tutorOptionsAnchorPane.getChildren().get(0);
        if (nodeOut instanceof GridPane) {
            for (final Node nodeIn : ((GridPane)nodeOut).getChildren()) {
                if (nodeIn instanceof HBox) {
                    return nodeIn;
                }
            }
        }
        return parent;
    }
    
    public Node getAnswerButtonPane() {
        final Node parent = (Node)this.tutorAnswersAnchorPane.getChildren().get(0);
        final Node nodeOut = (Node)this.tutorAnswersAnchorPane.getChildren().get(0);
        if (nodeOut instanceof GridPane) {
            for (final Node nodeIn : ((GridPane)nodeOut).getChildren()) {
                if (nodeIn instanceof FlowPane) {
                    return nodeIn;
                }
            }
        }
        return parent;
    }
    
    public void show_answerPane() {
        this.tutorPane.getChildren().remove((Object)this.tutorOptionsAnchorPane);
        this.tutorPane.getChildren().add((Object)this.tutorAnswersAnchorPane);
    }
    
    public void show_optionsPane() {
        this.tutorPane.getChildren().remove((Object)this.tutorResultsPane);
        this.tutorPane.getChildren().add((Object)this.tutorOptionsAnchorPane);
    }
    
    public void show_resultsPane() {
        this.tutorPane.getChildren().remove((Object)this.tutorAnswersAnchorPane);
        this.tutorPane.getChildren().add((Object)this.tutorResultsPane);
    }
    
    public void show_answerFromResults() {
        this.tutorPane.getChildren().remove((Object)this.tutorResultsPane);
        this.tutorPane.getChildren().add((Object)this.tutorAnswersAnchorPane);
    }
}
