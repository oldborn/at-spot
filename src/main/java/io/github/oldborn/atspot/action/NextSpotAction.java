package io.github.oldborn.atspot.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.oldborn.atspot.spotifywebapi.nexttrack.SptfyNextTrackService;

public class NextSpotAction extends AuthorizedAction {

    private SptfyNextTrackService nextTrackService = SptfyNextTrackService.getInstance();

    @Override
    public void actionPerformed(AnActionEvent e) {
        try {
            nextTrackService.nextTrack(true);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
