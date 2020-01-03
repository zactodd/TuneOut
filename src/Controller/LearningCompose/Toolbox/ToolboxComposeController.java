

package Controller.LearningCompose.Toolbox;

public class ToolboxComposeController extends Toolbox
{
    @Override
    public String getSelectedToolboxElement() {
        return "Melody";
    }
    
    @Override
    public Boolean toolboxIsSelected() {
        return true;
    }
}
