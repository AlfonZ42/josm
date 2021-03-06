// License: GPL. Copyright 2007 by Immanuel Scholz and others
package org.openstreetmap.josm.actions.audio;

import static org.openstreetmap.josm.gui.help.HelpUtil.ht;
import static org.openstreetmap.josm.tools.I18n.tr;
import static org.openstreetmap.josm.tools.I18n.trc;

import java.awt.event.KeyEvent;

import org.openstreetmap.josm.tools.Shortcut;

/**
 * Increase the speed of audio playback.
 * Each use increases the speed further until one of the other controls is used.
 * @since 563
 */
public class AudioFasterAction extends AudioFastSlowAction {

    /**
     * Constructs a new {@code AudioFasterAction}.
     */
    public AudioFasterAction() {
        super(trc("audio", "Faster"), "audio-faster", trc("audio", "Faster Forward"),
        Shortcut.registerShortcut("audio:faster", tr("Audio: {0}", trc("audio", "Faster")), KeyEvent.VK_F9, Shortcut.DIRECT), true);
        this.putValue("help", ht("/Action/AudioFaster"));
    }
}
