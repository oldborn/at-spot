package io.github.oldborn.atspot.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.oldborn.atspot.spotifywebapi.playpausetrack.SptfyPlayPauseService;

public class PausePlaySpotAction extends AuthorizedAction {

    private SptfyPlayPauseService playPauseService = SptfyPlayPauseService.getInstance();

    @Override
    public void actionPerformed(AnActionEvent e) {
        try {
            playPauseService.playPause();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
