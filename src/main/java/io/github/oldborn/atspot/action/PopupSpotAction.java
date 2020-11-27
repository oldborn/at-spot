package io.github.oldborn.atspot.action;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;
import io.github.oldborn.atspot.spotifywebapi.currenttrack.SptfyGetCurrentTrackService;
import io.github.oldborn.atspot.spotifywebapi.nexttrack.SptfyNextTrackService;
import io.github.oldborn.atspot.spotifywebapi.playpausetrack.SptfyPlayPauseService;
import io.github.oldborn.atspot.spotifywebapi.prevtrack.SptfyPrevTrackService;
import io.github.oldborn.atspot.util.SpotActionGroupPopup;
import io.github.oldborn.atspot.util.SpotKeyEventCallback;
import io.github.oldborn.atspot.util.SpotPopupFactory;

import java.awt.event.KeyEvent;

public class PopupSpotAction extends AnAction {

    private ActionGroup actionGroup = null;

    private SptfyGetCurrentTrackService getCurrentTrackService = SptfyGetCurrentTrackService.getInstance();

    private SptfyNextTrackService nextTrackService = SptfyNextTrackService.getInstance();

    private SptfyPrevTrackService prevTrackService = SptfyPrevTrackService.getInstance();

    private SptfyPlayPauseService playPauseService = SptfyPlayPauseService.getInstance();

    private SpotPopupFactory popupFactory = new SpotPopupFactory();

    private static final int A = 65;
    private static final int S = 83;
    private static final int D = 68;

    private SpotKeyEventCallback keyEventCallback = new SpotKeyEventCallback() {
        @Override
        public void keyPressed(KeyEvent keyEvent) throws Exception {
            System.out.println(keyEvent.getKeyCode());
            switch (keyEvent.getKeyCode()){
                case A:
                    prevTrackService.prevTrack(false);
                    keyEvent.consume();
                    break;
                case D:
                    nextTrackService.nextTrack(false);
                    keyEvent.consume();
                    break;
                case S:
                    playPauseService.playPause();
                    keyEvent.consume();
                    break;
            }
        }
    };

    @Override
    public void actionPerformed(AnActionEvent e) {

        String title = "@Spot: A-prev | S-pause | D-next";

        actionGroup = actionGroup == null ? (ActionGroup) ActionManager.getInstance().getAction("Spot.MainGroup") : actionGroup;
        
        SpotActionGroupPopup popup = (SpotActionGroupPopup) popupFactory.createActionGroupPopup(title,actionGroup,e.getDataContext(), JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,true);
        popup.setSpotKeyEventCallback(keyEventCallback);
        popup.show(RelativePoint.getCenterOf(WindowManager.getInstance().getIdeFrame(e.getProject()).getComponent()));
    }
}
