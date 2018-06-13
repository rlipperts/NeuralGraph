package Controller;

import Util.InspectCellEvent;
import com.google.common.eventbus.Subscribe;
import com.mxgraph.model.mxCell;

public class NodeInspectorController {

    @Subscribe
    public void handleInspectCellEvent(InspectCellEvent inspectCellEvent) {
        //TODO: Handle this shit. Maybe simply just call this when creating a customLayer
    }

}
