package io.github.oldborn.atspot.spotifywebapi.currenttrack.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class TrackItem implements Serializable {
    private Album album;
    private List<TrackArtist> artists;
    private Integer disc_number;
    private Long duration_ms;
    private Boolean explicit;
    private ExternalIDs external_ids;
    private ExternalURLs external_urls;
    private String href;
    private String id;
    private String name;
    private Integer popularity;
    private String preview_url;
    private Integer track_number;
    private String type;
    private String uri;
    private List<String> available_markets;
    private Boolean is_local;
}
