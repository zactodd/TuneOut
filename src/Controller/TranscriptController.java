

package Controller;

import java.util.List;
import java.util.ArrayList;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import Model.command.CommandStorage.CommandHistory;
import javafx.scene.control.TextArea;
import javafx.scene.control.ScrollPane;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class TranscriptController
{
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextArea textTranscript;
    private CommandHistory commandHistory;
    private OuterTemplateController main;
    private static String TRANSCRIPT_HEADER;
    private StringProperty text;
    private final double defaultFontSize = 9.0;
    private double fontSize;
    private final double minFontSize = 4.0;
    private final double maxFontSize = 17.5;
    private double fontIncrement;
    
    public TranscriptController() {
        this.text = (StringProperty)new SimpleStringProperty((Object)this, "text", "");
        this.fontSize = 9.0;
        this.fontIncrement = 0.5;
    }
    
    public void initialize() {
        this.firstLineTranscript();
        this.textTranscript.setOnKeyPressed(this::zoomText);
        this.setTranscriptFontSize();
    }
    
    private void zoomText(final KeyEvent event) {
        if (event.isControlDown()) {
            if (event.isControlDown() && (event.getCode().equals((Object)KeyCode.PLUS) || event.getCode().equals((Object)KeyCode.EQUALS)) && this.fontSize < 17.5) {
                this.fontSize += this.fontIncrement;
            }
            else if (event.isControlDown() && event.getCode().equals((Object)KeyCode.MINUS) && this.fontSize > 4.0) {
                this.fontSize -= this.fontIncrement;
            }
            this.setTranscriptFontSize();
        }
    }
    
    public void firstLineTranscript() {
        this.textTranscript.setText(TranscriptController.TRANSCRIPT_HEADER);
    }
    
    public String getText() {
        String text = "";
        for (final String line : this.textTranscript.getText().split("\\n")) {
            text += line;
            text += '\n';
        }
        return text;
    }
    
    public ArrayList<String> getCommands() {
        final ArrayList<String> commands = new ArrayList<String>();
        for (final String line : this.textTranscript.getText().split("\\n")) {
            if (line.startsWith(">")) {
                commands.add(line.substring(1));
            }
        }
        return commands;
    }
    
    public void appendToTranscript(final String transcript) {
        this.textTranscript.appendText(transcript);
    }
    
    private void setTranscriptFontSize() {
        this.textTranscript.setStyle(String.format("-fx-font-size: %1fpt", this.fontSize));
    }
    
    public void setFontSize(final double fontSize) {
        this.fontSize = fontSize;
        this.setTranscriptFontSize();
    }
    
    public double getFontSize() {
        return this.fontSize;
    }
    
    public List<Double> getAllowableFontSizes() {
        final List<Double> returnList = new ArrayList<Double>();
        for (double x = 4.0; x < 17.5; x += this.fontIncrement) {
            returnList.add(x);
        }
        return returnList;
    }
    
    public void setTextTranscript(final String transcript) {
        if (this.textTranscript != null) {
            this.textTranscript.setText(transcript);
        }
    }
    
    public void setCommandHistory(final CommandHistory commandHistory) {
        this.commandHistory = commandHistory;
    }
    
    protected double getDefaultFontSize() {
        return 9.0;
    }
    
    static {
        TranscriptController.TRANSCRIPT_HEADER = "TuneOut Transcript ©2016 by Sweet :)\n";
    }
}
