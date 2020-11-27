package io.github.oldborn.atspot.spotifywebapi.currenttrack.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor
public class GetCurrentTrackRS implements Serializable {
    private Context context;
    private Long timestamp;
    private Long progress_ms;
    private Boolean is_playing;
    private String currently_playing_type;
    private TrackItem item;
}
