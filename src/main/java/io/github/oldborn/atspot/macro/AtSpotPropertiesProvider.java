package io.github.oldborn.atspot.macro;

import com.intellij.ide.fileTemplates.DefaultTemplatePropertiesProvider;
import com.intellij.psi.PsiDirectory;
import io.github.oldborn.atspot.spotifywebapi.currenttrack.SptfyGetCurrentTrackService;
import io.github.oldborn.atspot.spotifywebapi.currenttrack.model.GetCurrentTrackRS;

import java.util.Properties;

public class AtSpotPropertiesProvider implements DefaultTemplatePropertiesProvider {

    private SptfyGetCurrentTrackService sptfyGetCurrentTrackService = SptfyGetCurrentTrackService.getInstance();

    @Override
    public void fillProperties(PsiDirectory psiDirectory, Properties properties) {

        GetCurrentTrackRS currentTrackRS = null;
        try {
            currentTrackRS = sptfyGetCurrentTrackService.getCurrentTrack(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (currentTrackRS == null) return;

        String value = String.format("%s - %s @Link %s",
                currentTrackRS.getItem().getName(),
                currentTrackRS.getItem().getArtists().get(0).getName(),
                currentTrackRS.getItem().getExternal_urls().getSpotify());
        properties.put("SPOT",value);
    }
}
