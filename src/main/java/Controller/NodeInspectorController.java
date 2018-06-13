package Controller;

import Util.InspectNodeEvent;
import com.google.common.eventbus.Subscribe;

public class NodeInspectorController {

    @Subscribe
    public void handleInspectCellEvent(InspectNodeEvent inspectNodeEvent) {
        //TODO: Handle this shit. Maybe simply just call this when creating a customLayer
    }

}
