package io.github.oldborn.atspot.spotifywebapi.currenttrack;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.util.concurrency.AppExecutorUtil;
import io.github.oldborn.atspot.spotifywebapi.SptfyGetAccessTokenService;
import io.github.oldborn.atspot.spotifywebapi.SptfyRefreshAccessTokenService;
import io.github.oldborn.atspot.spotifywebapi.currenttrack.model.GetCurrentTrackRS;
import io.github.oldborn.atspot.util.ObjectMapperProvider;
import io.github.oldborn.atspot.util.UnsafeOkHttpService;
import okhttp3.*;

import java.util.concurrent.TimeUnit;


public class SptfyGetCurrentTrackService {

    private SptfyGetAccessTokenService getAccessTokenService = SptfyGetAccessTokenService.getInstance();

    private SptfyRefreshAccessTokenService refreshAccessTokenService = SptfyRefreshAccessTokenService.getInstance();

    private OkHttpClient okHttpClient = UnsafeOkHttpService.getInstance().getOkHttpClient();

    private ObjectMapper objectMapper = ObjectMapperProvider.getInstance();
    
    private GetCurrentTrackRS lastKnownRecord = null;

    public static SptfyGetCurrentTrackService getInstance() {
        return ServiceManager.getService(SptfyGetCurrentTrackService.class);
    }
    
    public GetCurrentTrackRS getCurrentTrack() throws Exception {
        return getCurrentTrack(false);
    }

    public GetCurrentTrackRS getLastKnownRecord(){
        return lastKnownRecord;
    }
    
    public GetCurrentTrackRS getCurrentTrack(boolean tryAgain) throws Exception {
        String access_token = getAccessTokenService.getAccessToken();
        Response response = okHttpClient.newCall(new Request.Builder()
                .get()
                .url("https://api.spotify.com/v1/me/player/currently-playing")
                .header("Authorization","Bearer "+access_token)
                .header("Content-Type","application/json")
                .build()).execute();

        GetCurrentTrackRS currentTrackRS = null;

        if (response.code() == 200){
            currentTrackRS = objectMapper.readValue(response.body().string(),GetCurrentTrackRS.class);
        }else if (response.code() == 204){
            return null;
        }else if (tryAgain){
            response.close();
            access_token = refreshAccessTokenService.storeAndGetAccessToken();
            return getCurrentTrack();
        }

        lastKnownRecord = currentTrackRS;
        
        return currentTrackRS;
    }



}
