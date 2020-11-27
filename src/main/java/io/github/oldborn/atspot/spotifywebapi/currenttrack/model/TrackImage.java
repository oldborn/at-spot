package io.github.oldborn.atspot.spotifywebapi.currenttrack.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor
public class TrackImage implements Serializable {
    private Integer height;
    private Integer width;
    private String url;
}
