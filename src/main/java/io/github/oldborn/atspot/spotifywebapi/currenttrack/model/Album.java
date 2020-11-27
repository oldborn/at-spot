package io.github.oldborn.atspot.spotifywebapi.currenttrack.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class Album implements Serializable {
    private String album_type;
    private List<TrackArtist> artists;
    private ExternalURLs external_urls;
    private String href;
    private String id;
    private List<TrackImage> images;
    private String name;
    private String type;
    private String uri;
    private List<String> available_markets;
    private String release_date;
    private String release_date_precision;
    private Integer total_tracks;
}
