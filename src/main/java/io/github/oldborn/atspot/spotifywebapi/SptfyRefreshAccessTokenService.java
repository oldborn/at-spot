package io.github.oldborn.atspot.spotifywebapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.openapi.components.ServiceManager;
import io.github.oldborn.atspot.spotifywebapi.model.RefreshAccessTokenRS;
import io.github.oldborn.atspot.util.ObjectMapperProvider;
import io.github.oldborn.atspot.util.StorageService;
import io.github.oldborn.atspot.util.UnsafeOkHttpService;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class SptfyRefreshAccessTokenService {

    private StorageService storageService = StorageService.getInstance();

    private SptfyTokenService tokenService = SptfyTokenService.getInstance();

    private OkHttpClient okHttpClient = UnsafeOkHttpService.getInstance().getOkHttpClient();

    private ObjectMapper objectMapper = ObjectMapperProvider.getInstance();

    public static SptfyRefreshAccessTokenService getInstance() {
        return ServiceManager.getService(SptfyRefreshAccessTokenService.class);
    }

    public String storeAndGetAccessToken() throws Exception {

        String refresh_token = storageService.getRefresh_token();
        if (refresh_token == null){
            return tokenService.getAccessTokenAndStoreTokens();
        }

        Response response = tryOnce(refresh_token);
        if (response.code() == 200){
            RefreshAccessTokenRS refreshAccessTokenRS = objectMapper.readValue(response.body().string(),RefreshAccessTokenRS.class);
            storageService.setAccess_token(refreshAccessTokenRS.getAccess_token());

            return refreshAccessTokenRS.getAccess_token();
        }else {
            throw new Exception("Can't get access token");
        }
    }

    private Response tryOnce(String refresh_token) throws IOException {
        Response response = okHttpClient.newCall(
                new Request.Builder()
                        .url("https://accounts.spotify.com/api/token")
                        .header("Authorization",storageService.getAuthorizationHeader())
                        .header("Content-Type","application/x-www-form-urlencoded")
                        .post(new FormBody.Builder()
                                .add("grant_type","refresh_token")
                                .add("refresh_token",refresh_token)
                                .build())
                        .build()
        ).execute();

        return response;
    }
}
