// 
// Decompiled by Procyon v0.5.36
// 

package Model.Project;

public class User
{
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private boolean selected;
    private PersistenceData data;
    
    public User(final String userName, final String password, final String firstName, final String lastName) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public PersistenceData getData() {
        return this.data;
    }
    
    public void setData(final PersistenceData data) {
        this.data = data;
    }
    
    public boolean isSelected() {
        return this.selected;
    }
    
    public void setSelected(final boolean selected) {
        this.selected = selected;
    }
    
    public String getUserName() {
        return this.userName;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }
}
