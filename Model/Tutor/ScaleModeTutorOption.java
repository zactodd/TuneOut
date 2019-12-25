// 
// Decompiled by Procyon v0.5.36
// 

package Model.Tutor;

public class ScaleModeTutorOption extends Options
{
    private String type;
    
    public ScaleModeTutorOption(final Integer numQuestions, final String type) {
        this.type = type;
        this.numQuestions = numQuestions;
    }
    
    public String getType() {
        return this.type;
    }
}
