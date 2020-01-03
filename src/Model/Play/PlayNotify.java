

package Model.Play;

import java.util.List;
import Controller.OuterTemplateController;

import java.util.ArrayList;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MetaEventListener;

public class PlayNotify implements MetaEventListener
{
    @Override
    public void meta(final MetaMessage meta) {
        final String rawData = new String(meta.getData());
        final String[] elements = rawData.substring(1, rawData.length() - 1).split(", ");
        final List<Integer> result = new ArrayList<Integer>(elements.length);
        for (final String item : elements) {
            result.add(Integer.parseInt(item));
        }
        if (PlayThread.toDrumPadAnimation) {
            OuterTemplateController.drumAnimate(new ArrayList<Integer>(result));
        }
        else {
            OuterTemplateController.highlightEnable(new ArrayList<Integer>(result));
        }
    }
}
