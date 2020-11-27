package io.github.oldborn.atspot.spotifywebapi.playpausetrack;

import com.intellij.openapi.components.ServiceManager;
import io.github.oldborn.atspot.spotifywebapi.currenttrack.SptfyGetCurrentTrackService;
import io.github.oldborn.atspot.spotifywebapi.currenttrack.model.GetCurrentTrackRS;
import io.github.oldborn.atspot.spotifywebapi.pausetrack.SptfyPauseTrackService;
import io.github.oldborn.atspot.spotifywebapi.playtrack.SptfyPlayTrackService;

public class SptfyPlayPauseService {

    private SptfyGetCurrentTrackService currentTrackService = SptfyGetCurrentTrackService.getInstance();
    private SptfyPlayTrackService playTrackService = SptfyPlayTrackService.getInstance();
    private SptfyPauseTrackService pauseTrackService = SptfyPauseTrackService.getInstance();

    public static SptfyPlayPauseService getInstance() {
        return ServiceManager.getService(SptfyPlayPauseService.class);
    }

    public boolean playPause() throws Exception {
        GetCurrentTrackRS currentTrackRS = currentTrackService.getCurrentTrack(true);
        if (currentTrackRS.getIs_playing()){
            return pauseTrackService.pauseTrack(false);
        }else {
            return playTrackService.playTrack(false);
        }
    }
}
