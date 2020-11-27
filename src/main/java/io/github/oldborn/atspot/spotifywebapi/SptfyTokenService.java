package io.github.oldborn.atspot.spotifywebapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.openapi.components.ServiceManager;
import io.github.oldborn.atspot.spotifywebapi.model.TokenServiceRS;
import io.github.oldborn.atspot.util.ObjectMapperProvider;
import io.github.oldborn.atspot.util.StorageService;
import io.github.oldborn.atspot.util.UnsafeOkHttpService;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

public class SptfyTokenService {

    private SptfyAuthService authService = SptfyAuthService.getInstance();

    private StorageService storageService = StorageService.getInstance();

    private OkHttpClient okHttpClient = UnsafeOkHttpService.getInstance().getOkHttpClient();

    private ObjectMapper objectMapper = ObjectMapperProvider.getInstance();

    public static SptfyTokenService getInstance() {
        return ServiceManager.getService(SptfyTokenService.class);
    }

    public String getAccessTokenAndStoreTokens() throws Exception {

        String code = Optional.ofNullable(storageService.getCode()).orElseGet(() ->{
            try {
                authService.authorize();
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
            return storageService.getCode();
        });


        String client_id = storageService.getCLIENT_ID();
        String client_secret = storageService.getCLIENT_SECRET();
        String redirect_url = storageService.getREDIRECT_URL();

        Response response = okHttpClient.newCall(
                new Request.Builder()
                        .url("https://accounts.spotify.com/api/token")
                        .post(new FormBody.Builder()
                                .add("grant_type","authorization_code")
                                .add("code",code)
                                .add("redirect_uri", redirect_url)
                                .add("client_id",client_id)
                                .add("client_secret",client_secret)
                                .build())
                        .build()
        ).execute();

        TokenServiceRS tokenServiceRS = null;
        if (response.code() == 200){
            tokenServiceRS = objectMapper.readValue(response.body().string(), TokenServiceRS.class);
        }else {
            throw new Exception("Can't retrieve tokens.");
        }

        storageService.setAccess_token(tokenServiceRS.getAccess_token());
        storageService.setRefresh_token(tokenServiceRS.getRefresh_token());


        return tokenServiceRS.getAccess_token();
    }
}
