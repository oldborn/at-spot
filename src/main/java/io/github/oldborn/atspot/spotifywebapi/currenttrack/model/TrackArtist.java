package io.github.oldborn.atspot.spotifywebapi.currenttrack.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor
public class TrackArtist implements Serializable {
    private ExternalURLs external_urls;
    private String href;
    private String id;
    private String name;
    private String type;
    private String uri;
}
