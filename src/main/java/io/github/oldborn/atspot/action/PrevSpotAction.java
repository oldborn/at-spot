package io.github.oldborn.atspot.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.oldborn.atspot.spotifywebapi.prevtrack.SptfyPrevTrackService;

public class PrevSpotAction extends AuthorizedAction {

    private SptfyPrevTrackService prevTrackService = SptfyPrevTrackService.getInstance();

    @Override
    public void actionPerformed(AnActionEvent e) {
        try {
            prevTrackService.prevTrack(true);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
