

package Model.Tutor;

import Controller.TutorController;

public class TutorDefinition
{
    public final String tutorName;
    public final Tutor tutor;
    public final TutorController tutorController;
    public final Options options;
    
    public TutorDefinition(final String tutorName, final Tutor tutor, final TutorController tutorController, final Options options) {
        this.tutorName = tutorName;
        this.tutor = tutor;
        this.tutorController = tutorController;
        this.options = options;
    }
}
