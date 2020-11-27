package io.github.oldborn.atspot.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import io.github.oldborn.atspot.spotifywebapi.SptfyAuthService;
import io.github.oldborn.atspot.spotifywebapi.spottrack.SpotTrackService;

import java.io.IOException;
import java.net.URISyntaxException;


public class AddSpotAction extends AuthorizedAction {
    
    private SptfyAuthService authService = SptfyAuthService.getInstance();

    private SpotTrackService spotTrackService = SpotTrackService.getInstance();

    @Override
    public void actionPerformed(AnActionEvent event) {
        if (!authService.isAuthorized()) {
            try {
                authService.authorize();
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }

        final Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        spotTrackService.addASpot(editor);
    }


}
