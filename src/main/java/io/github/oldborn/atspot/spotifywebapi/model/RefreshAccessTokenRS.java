package io.github.oldborn.atspot.spotifywebapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor
public class RefreshAccessTokenRS implements Serializable {
    private String access_token;
    private String token_type;
    private String scope;
    private Integer expires_in;
}
