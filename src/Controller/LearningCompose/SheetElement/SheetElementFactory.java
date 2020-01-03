

package Controller.LearningCompose.SheetElement;

import Controller.LearningCompose.OuterExploreCompose;

public class SheetElementFactory
{
    OuterExploreCompose outerExploreController;
    
    public SheetElementFactory(final OuterExploreCompose outerExploreController) {
        this.outerExploreController = outerExploreController;
    }
    
    public SheetElement getNewElement() {
        final String selectedToolboxElement = this.outerExploreController.getToolboxController().getSelectedToolboxElement();
        switch (selectedToolboxElement) {
            case "Note": {
                return new SheetNote(this.outerExploreController);
            }
            case "Scale": {
                return new SheetScale(this.outerExploreController);
            }
            case "Interval": {
                return new SheetInterval(this.outerExploreController);
            }
            case "Chord": {
                return new SheetChord(this.outerExploreController);
            }
            case "Key Signature": {
                return new SheetSignatureNote(this.outerExploreController);
            }
            case "Melody": {
                return new SheetComposeNoteRest(this.outerExploreController);
            }
            case "Percussion": {
                return new SheetComposePercussion(this.outerExploreController);
            }
            default: {
                return null;
            }
        }
    }
}
