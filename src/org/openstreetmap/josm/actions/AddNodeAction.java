// License: GPL. Copyright 2007 by Immanuel Scholz and others
package org.openstreetmap.josm.actions;

import static org.openstreetmap.josm.gui.help.HelpUtil.ht;
import static org.openstreetmap.josm.tools.I18n.tr;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.command.AddCommand;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.data.osm.Node;
import org.openstreetmap.josm.gui.dialogs.LatLonDialog;
import org.openstreetmap.josm.tools.Shortcut;

/**
 * This action displays a dialog where the user can enter a latitude and longitude,
 * and when ok is pressed, a new node is created at the specified position.
 */
public final class AddNodeAction extends JosmAction {
    // remember input from last time
    private String textLatLon, textEastNorth;

    public AddNodeAction() {
        super(tr("Add Node..."), "addnode", tr("Add a node by entering latitude / longitude or easting / northing."),
                Shortcut.registerShortcut("addnode", tr("Edit: {0}", tr("Add Node...")),
                        KeyEvent.VK_D, Shortcut.SHIFT), true);
        putValue("help", ht("/Action/AddNode"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isEnabled())
            return;

        LatLonDialog dialog = new LatLonDialog(Main.parent, tr("Add Node..."), ht("/Action/AddNode"));

        if (textLatLon != null) {
            dialog.setLatLonText(textLatLon);
        }
        if (textEastNorth != null) {
            dialog.setEastNorthText(textEastNorth);
        }

        dialog.showDialog();

        if (dialog.getValue() != 1)
            return;

        LatLon coordinates = dialog.getCoordinates();
        if (coordinates == null)
            return;

        textLatLon = dialog.getLatLonText();
        textEastNorth = dialog.getEastNorthText();

        Node nnew = new Node(coordinates);

        // add the node
        Main.main.undoRedo.add(new AddCommand(nnew));
        getCurrentDataSet().setSelected(nnew);
        Main.map.mapView.repaint();
    }

    @Override
    protected void updateEnabledState() {
        setEnabled(getEditLayer() != null);
    }
}
