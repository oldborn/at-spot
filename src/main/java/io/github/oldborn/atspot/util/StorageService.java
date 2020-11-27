package io.github.oldborn.atspot.util;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.Base64;
import com.intellij.util.xmlb.XmlSerializerUtil;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@State(
        name="StorageService",
        storages = {
                @Storage("StorageService.xml")}
)
@Getter @Setter
public class StorageService implements PersistentStateComponent<StorageService> {

    private String code;
    private String CLIENT_ID;
    private String CLIENT_SECRET;
    private String REDIRECT_URL;

    private String access_token;
    private String refresh_token;

    public static StorageService getInstance() {
        return ServiceManager.getService(StorageService.class);
    }

    @Nullable
    @Override
    public StorageService getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull StorageService storageService) {
        XmlSerializerUtil.copyBean(storageService, this);
    }

    public String getAuthorizationHeader(){
        String encoded = Base64.encode(String.join(":",CLIENT_ID,CLIENT_SECRET).getBytes());
        return "Basic " + encoded;
    }

    public void reset(){
        this.code = null;
        this.CLIENT_ID = null;
        this.CLIENT_SECRET = null;
        this.REDIRECT_URL = null;
        this.access_token = null;
        this.refresh_token = null;
    }

}
