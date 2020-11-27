package io.github.oldborn.atspot.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.oldborn.atspot.spotifywebapi.SptfyAuthService;

import java.io.IOException;
import java.net.URISyntaxException;

public class SetupSpotAction extends AnAction {

    private SptfyAuthService sptfyAuthService = SptfyAuthService.getInstance();

    @Override
    public void actionPerformed(AnActionEvent e) {
        try {
            sptfyAuthService.authorize();
        } catch (IOException | URISyntaxException e1) {
            e1.printStackTrace();
        }
    }
}
