

package Controller.LearningCompose.Toolbox;

public class ToolboxComposePercussionController extends Toolbox
{
    @Override
    public String getSelectedToolboxElement() {
        return "Percussion";
    }
    
    @Override
    public Boolean toolboxIsSelected() {
        return true;
    }
}
