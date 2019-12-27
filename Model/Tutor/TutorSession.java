

package Model.Tutor;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class TutorSession
{
    private String user;
    private LocalDateTime dateTime;
    private String tutorType;
    private Integer numQuestions;
    private Integer numCorrect;
    private Integer numIncorrect;
    private Double percentCorrect;
    private Boolean imported;
    
    public TutorSession() {
    }
    
    public TutorSession(final String user, final LocalDateTime dateTime, final String tutorType, final Integer numQuestions, final Integer numCorrect, final Integer numIncorrect, final Double percentCorrect, final Boolean imported) {
        this.user = user;
        this.dateTime = dateTime;
        this.tutorType = tutorType;
        this.numQuestions = numQuestions;
        this.numCorrect = numCorrect;
        this.numIncorrect = numIncorrect;
        this.percentCorrect = percentCorrect;
        this.imported = imported;
    }
    
    public String getTutorType() {
        return this.tutorType;
    }
    
    public void setTutorType(final String tutorType) {
        this.tutorType = tutorType;
    }
    
    public LocalDateTime getDateTime() {
        return this.dateTime;
    }
    
    public void setDateTime(final String date, final String time) {
        if (date.isEmpty() || time.isEmpty()) {
            this.dateTime = LocalDateTime.now();
        }
        else {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MMM/dd H:m:s");
            this.dateTime = LocalDateTime.parse(date + " " + time, formatter);
        }
    }
    
    public String getUser() {
        return this.user;
    }
    
    public void setUser(final String user) {
        this.user = user;
    }
    
    public Integer getNumQuestions() {
        return this.numQuestions;
    }
    
    public void setNumQuestions(final Integer numQuestions) {
        this.numQuestions = numQuestions;
    }
    
    public Integer getNumCorrect() {
        return this.numCorrect;
    }
    
    public Integer getNumIncorrect() {
        return this.numIncorrect;
    }
    
    public void setNumIncorrect(final Integer numIncorrect) {
        if (this.numQuestions != 0) {
            this.numCorrect = this.numQuestions - numIncorrect;
        }
        this.numIncorrect = numIncorrect;
    }
    
    public Double getPercentCorrect() {
        return this.percentCorrect;
    }
    
    public void setPercentCorrect(final Double percentCorrect) {
        this.percentCorrect = percentCorrect;
    }
    
    public Boolean getImported() {
        return this.imported;
    }
    
    public void setImported(final Boolean imported) {
        this.imported = imported;
    }
    
    @Override
    public String toString() {
        return "TutorSession{tutorType='" + this.tutorType + '\'' + ", dateTime=" + this.dateTime + ", user='" + this.user + '\'' + ", numQuestions=" + this.numQuestions + ", numCorrect=" + this.numCorrect + ", numIncorrect=" + this.numIncorrect + ", percentCorrect=" + this.percentCorrect + '}';
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!TutorSession.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final TutorSession other = (TutorSession)obj;
        return this.getUser().equals(other.user) && this.getDateTime().equals(other.getDateTime());
    }
}
