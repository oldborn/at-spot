package io.github.oldborn.atspot.spotifywebapi;

import com.intellij.openapi.components.ServiceManager;
import io.github.oldborn.atspot.util.StorageService;

public class SptfyGetAccessTokenService {

    private StorageService storageService = StorageService.getInstance();

    private SptfyRefreshAccessTokenService refreshAccessTokenService = SptfyRefreshAccessTokenService.getInstance();

    public static SptfyGetAccessTokenService getInstance() {
        return ServiceManager.getService(SptfyGetAccessTokenService.class);
    }

    public String getAccessToken() throws Exception {
        String access_token = storageService.getAccess_token();
        if (access_token == null){
            access_token =  refreshAccessTokenService.storeAndGetAccessToken();
        }
        return access_token;
    }
}
