// License: GPL. Copyright 2007 by Immanuel Scholz and others
package org.openstreetmap.josm.command;

import static org.openstreetmap.josm.tools.I18n.marktr;
import static org.openstreetmap.josm.tools.I18n.tr;

import java.util.Collection;

import javax.swing.Icon;

import org.openstreetmap.josm.data.osm.OsmPrimitive;
import org.openstreetmap.josm.data.osm.OsmPrimitiveType;
import org.openstreetmap.josm.data.osm.Way;
import org.openstreetmap.josm.gui.DefaultNameFormatter;
import org.openstreetmap.josm.gui.layer.OsmDataLayer;
import org.openstreetmap.josm.tools.CheckParameterUtil;
import org.openstreetmap.josm.tools.ImageProvider;

/**
 * Command that basically replaces one OSM primitive by another of the
 * same type.
 *
 * @author Imi
 */
public class ChangeCommand extends Command {

    private final OsmPrimitive osm;
    private final OsmPrimitive newOsm;

    public ChangeCommand(OsmPrimitive osm, OsmPrimitive newOsm) {
        super();
        this.osm = osm;
        this.newOsm = newOsm;
        sanityChecks();
    }

    public ChangeCommand(OsmDataLayer layer, OsmPrimitive osm, OsmPrimitive newOsm) {
        super(layer);
        this.osm = osm;
        this.newOsm = newOsm;
        sanityChecks();
    }
    
    private void sanityChecks() {
        CheckParameterUtil.ensureParameterNotNull(osm, "osm");
        CheckParameterUtil.ensureParameterNotNull(newOsm, "newOsm");
        if (newOsm instanceof Way) {
            // Do not allow to create empty ways (see #7465)
            if (((Way)newOsm).getNodesCount() == 0) {
                throw new IllegalArgumentException(tr("New way {0} has 0 nodes", newOsm));
            }
        }
    }

    @Override public boolean executeCommand() {
        super.executeCommand();
        osm.cloneFrom(newOsm);
        osm.setModified(true);
        return true;
    }

    @Override public void fillModifiedData(Collection<OsmPrimitive> modified, Collection<OsmPrimitive> deleted, Collection<OsmPrimitive> added) {
        modified.add(osm);
    }

    @Override
    public String getDescriptionText() {
        String msg = "";
        switch(OsmPrimitiveType.from(osm)) {
        case NODE: msg = marktr("Change node {0}"); break;
        case WAY: msg = marktr("Change way {0}"); break;
        case RELATION: msg = marktr("Change relation {0}"); break;
        }
        return tr(msg, osm.getDisplayName(DefaultNameFormatter.getInstance()));
    }

    @Override
    public Icon getDescriptionIcon() {
        return ImageProvider.get(osm.getDisplayType());
    }
}
