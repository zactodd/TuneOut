

package Model.Terms;

import java.util.HashMap;
import java.util.Map;
import Model.Project.PersistentStatus;

public class MusicalTerms
{
    private static PersistentStatus status;
    private static Map<String, Term> termMap;
    private static boolean termsChanged;
    private static HashMap<String, Term> defaultTerms;
    
    public static void addTerm(final Term term) {
        MusicalTerms.termMap.put(term.getTermName().toLowerCase(), term);
        MusicalTerms.termsChanged = true;
    }
    
    public static void removeTerm(final String termName) {
        MusicalTerms.termMap.remove(termName.toLowerCase());
        MusicalTerms.termsChanged = true;
    }
    
    public static PersistentStatus getStatus() {
        return MusicalTerms.status;
    }
    
    public static Term getTerm(final String termName) {
        return MusicalTerms.termMap.get(termName.toLowerCase());
    }
    
    public static Map<String, Term> getTerms() {
        return MusicalTerms.termMap;
    }
    
    public static void setTermsFromFile(final Map<String, Term> terms) {
        (MusicalTerms.termMap = new HashMap<String, Term>()).putAll(MusicalTerms.defaultTerms);
        MusicalTerms.termMap.putAll(terms);
    }
    
    public static void resetTerms() {
        (MusicalTerms.termMap = new HashMap<String, Term>()).putAll(MusicalTerms.defaultTerms);
        MusicalTerms.termsChanged = false;
    }
    
    public static boolean isTermsChanged() {
        return MusicalTerms.termsChanged;
    }
    
    public static void setTermsChanged(final boolean termsChanged) {
        MusicalTerms.termsChanged = termsChanged;
    }
    
    public static HashMap<String, Term> getDefaultTerms() {
        return MusicalTerms.defaultTerms;
    }
    
    static {
        MusicalTerms.status = new PersistentStatus() {
            @Override
            public void clearUpdateFlag() {
                MusicalTerms.setTermsChanged(false);
            }
            
            @Override
            public Boolean isUpdated() {
                return MusicalTerms.isTermsChanged();
            }
            
            @Override
            public void resetData() {
                MusicalTerms.resetTerms();
            }
        };
        MusicalTerms.termMap = new HashMap<String, Term>();
        MusicalTerms.termsChanged = false;
        MusicalTerms.defaultTerms = new HashMap<String, Term>();
        final Term presto = new Term("presto", "Italian", "very fast", "tempo");
        MusicalTerms.defaultTerms.put(presto.getTermName(), presto);
        final Term acappella = new Term("a cappella", "Italian", "singing without any instruments", "singing");
        MusicalTerms.defaultTerms.put(acappella.getTermName(), acappella);
        final Term adagio = new Term("adagio", "Italian", "slow", "tempo");
        MusicalTerms.defaultTerms.put(adagio.getTermName(), adagio);
        final Term sonore = new Term("sonore", "French", "resonant, with rich tone", "tone");
        MusicalTerms.defaultTerms.put(sonore.getTermName(), sonore);
        final Term modere = new Term("mod\u00c3©r\u00c3©", "French", "at a moderate speed", "tempo");
        MusicalTerms.defaultTerms.put(modere.getTermName(), modere);
        MusicalTerms.termMap.putAll(MusicalTerms.defaultTerms);
    }
}
