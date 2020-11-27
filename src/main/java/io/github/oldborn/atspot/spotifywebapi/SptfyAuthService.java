package io.github.oldborn.atspot.spotifywebapi;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.ui.Messages;
import io.github.oldborn.atspot.util.StorageService;
import okhttp3.HttpUrl;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class SptfyAuthService {

    private StorageService storageService = StorageService.getInstance();

    public static SptfyAuthService getInstance() {
        return ServiceManager.getService(SptfyAuthService.class);
    }

    public void authorize() throws IOException, URISyntaxException {
            Desktop.getDesktop().browse(new URI(authUrl()));
            String txt = Messages.showInputDialog("Paste the redirect url","Redirect Url",Messages.getQuestionIcon());
            String code = HttpUrl.parse(txt).queryParameter("code");
            storageService.setCode(code);
            System.out.println(code);
    }

    public boolean isAuthorized(){
        String code = storageService.getCode();
        return !(code == null || code.isEmpty());
    }

    private String authUrl() {
        return HttpUrl.parse("https://accounts.spotify.com/authorize").newBuilder()
                .addQueryParameter("client_id",storageService.getCLIENT_ID())
                .addQueryParameter("response_type","code")
                .addQueryParameter("redirect_uri", storageService.getREDIRECT_URL())
                .addQueryParameter("scope","user-read-currently-playing user-read-playback-state user-modify-playback-state")
                .build().toString();
    }

}
