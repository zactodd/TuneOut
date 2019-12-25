// 
// Decompiled by Procyon v0.5.36
// 

package Model.command;

import javafx.beans.property.SimpleStringProperty;

public class CommandData
{
    public final SimpleStringProperty name;
    public final SimpleStringProperty category;
    public final SimpleStringProperty description;
    public final SimpleStringProperty details;
    public final SimpleStringProperty commandInput;
    
    public CommandData(final String commandName, final String type, final String description, final String details, final String commandInput) {
        this.name = new SimpleStringProperty(commandName);
        this.category = new SimpleStringProperty(type);
        this.description = new SimpleStringProperty(description);
        this.details = new SimpleStringProperty(details);
        this.commandInput = new SimpleStringProperty(commandInput);
    }
    
    public String getName() {
        return this.name.get();
    }
    
    public void setName(final String fName) {
        this.name.set(fName);
    }
    
    public String getCategory() {
        return this.category.get();
    }
    
    public void setCategory(final String fTame) {
        this.category.set(fTame);
    }
    
    public String getDescription() {
        return this.description.get();
    }
    
    public void setDescription(final String fDescription) {
        this.description.set(fDescription);
    }
    
    public String getCommandInput() {
        return this.commandInput.get();
    }
    
    public void setCommandInput(final String fCommandInput) {
        this.commandInput.set(fCommandInput);
    }
    
    public String getDetails() {
        return this.details.get();
    }
    
    public void setDetails(final String details) {
        this.details.set(details);
    }
}
