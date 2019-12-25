// 
// Decompiled by Procyon v0.5.36
// 

package Model.Terms;

public class Term
{
    private String category;
    private String sourceLanguage;
    private String meaning;
    private String termName;
    
    public Term(final String termName, final String sourceLanguage, final String meaning, final String category) {
        this.category = category.toLowerCase();
        this.sourceLanguage = sourceLanguage.toLowerCase();
        this.meaning = meaning.toLowerCase();
        this.termName = termName.toLowerCase();
    }
    
    public String getCategory() {
        return this.category;
    }
    
    public String getSourceLanguage() {
        return this.sourceLanguage;
    }
    
    public String getMeaning() {
        return this.meaning;
    }
    
    public String getTermName() {
        return this.termName;
    }
}
