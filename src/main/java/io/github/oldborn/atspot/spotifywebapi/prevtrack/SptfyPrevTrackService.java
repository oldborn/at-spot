package io.github.oldborn.atspot.spotifywebapi.prevtrack;

import com.intellij.openapi.components.ServiceManager;
import io.github.oldborn.atspot.spotifywebapi.SptfyGetAccessTokenService;
import io.github.oldborn.atspot.spotifywebapi.SptfyRefreshAccessTokenService;
import io.github.oldborn.atspot.util.UnsafeOkHttpService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SptfyPrevTrackService {

    private SptfyGetAccessTokenService getAccessTokenService = SptfyGetAccessTokenService.getInstance();

    private SptfyRefreshAccessTokenService refreshAccessTokenService = SptfyRefreshAccessTokenService.getInstance();

    private OkHttpClient okHttpClient = UnsafeOkHttpService.getInstance().getOkHttpClient();

    public static SptfyPrevTrackService getInstance() {
        return ServiceManager.getService(SptfyPrevTrackService.class);
    }

    public boolean prevTrack(boolean tryAgain) throws Exception {
        String access_token = getAccessTokenService.getAccessToken();
        Response response = okHttpClient.newCall(new Request.Builder()
                .post(RequestBody.create(null, new byte[]{}))
                .url("https://api.spotify.com/v1/me/player/previous")
                .header("Authorization","Bearer "+access_token)
                .header("Content-Type","application/json")
                .build()).execute();

        if (response.code() == 204){
            return true;
        }else if (tryAgain){
            response.close();
            access_token = refreshAccessTokenService.storeAndGetAccessToken();
            return prevTrack(false);
        }

        return false;
    }

}
