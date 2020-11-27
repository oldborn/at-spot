package io.github.oldborn.atspot.config;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import io.github.oldborn.atspot.config.view.SpotConfigurationForm;
import io.github.oldborn.atspot.spotifywebapi.SptfyAuthService;
import io.github.oldborn.atspot.spotifywebapi.SptfyTokenService;
import io.github.oldborn.atspot.util.StorageService;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Optional;

public class SpotConfigurable implements SearchableConfigurable {

    private SpotConfigurationForm gui;
    private StorageService storageService = StorageService.getInstance();

    private SptfyAuthService authService = SptfyAuthService.getInstance();
    private SptfyTokenService tokenService = SptfyTokenService.getInstance();

    private String currentCLIENT_ID = "";
    private String currentCLIENT_SECRET = "";
    private String currentREDIRECT_URL = "";


    @NotNull
    @Override
    public String getId() {
        return "Spot.Configuration";
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "@Spot";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        gui = new SpotConfigurationForm();

        pullFromStorage();

        if (currentCLIENT_ID != null) gui.getClient_id().setText(currentCLIENT_ID);
        if (currentCLIENT_SECRET != null) gui.getClient_secret().setText(currentCLIENT_SECRET);
        if(currentREDIRECT_URL != null) gui.getRedirect_url().setText(currentREDIRECT_URL);
        return gui.getRootPanel();
    }

    private void pullFromStorage(){
        currentCLIENT_ID = storageService.getCLIENT_ID();
        currentCLIENT_SECRET = storageService.getCLIENT_SECRET();
        currentREDIRECT_URL = storageService.getREDIRECT_URL();
    }

    @Override
    public boolean isModified() {
        boolean isCLIENT_IDchanged = !gui.getClient_id().getText().equals(currentCLIENT_ID);
        boolean isCLIENT_SECRETchanged = !gui.getClient_secret().getText().equals(currentCLIENT_SECRET);
        boolean isREDIRECT_URLchanged = !gui.getRedirect_url().getText().equals(currentREDIRECT_URL);
        return isCLIENT_IDchanged | isCLIENT_SECRETchanged | isREDIRECT_URLchanged;
    }

    @Override
    public void apply() throws ConfigurationException {
        if (nullOrEmptyString(gui.getClient_id().getText()) ||
                nullOrEmptyString(gui.getClient_secret().getText()) ||
                nullOrEmptyString(gui.getRedirect_url().getText())){
            throw new ConfigurationException("All credentials should be filled.");
        }

        storageService.setCLIENT_ID(gui.getClient_id().getText());
        storageService.setCLIENT_SECRET(gui.getClient_secret().getText());
        storageService.setREDIRECT_URL(gui.getRedirect_url().getText());

        try {
            authService.authorize();
            tokenService.getAccessTokenAndStoreTokens();
        } catch (Exception e) {
            throw new ConfigurationException("Wrong credentials.","Credentials Error");
        }

        pullFromStorage();
    }

    private boolean nullOrEmptyString(String str){
        return Optional.ofNullable(str).orElse("").isEmpty();
    }
}
