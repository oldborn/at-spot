package io.github.oldborn.atspot.spotifywebapi.spottrack;


import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import io.github.oldborn.atspot.spotifywebapi.currenttrack.SptfyGetCurrentTrackService;
import io.github.oldborn.atspot.spotifywebapi.currenttrack.model.GetCurrentTrackRS;

import java.util.regex.Pattern;


public class SpotTrackService {
    public static SpotTrackService getInstance() {
        return ServiceManager.getService(SpotTrackService.class);
    }

    private static final String SPOT_REPEAT_ANNOTATION = "@SpotRepeat";
    private static final String SPOT_ONCE_ANNOTATION = "@SpotOnce";
    private static final String SPOT_LIVE_TEMP_IF_ANY = "${SPOT}";
    private SptfyGetCurrentTrackService getCurrentTrackService = SptfyGetCurrentTrackService.getInstance();

    public boolean addASpot(Editor editor){
        final Document document = editor.getDocument();
        addASpot(document);
        return true;
    }

    public boolean addASpot(Document document){
        String trackSummary = SPOT_REPEAT_ANNOTATION +"\n";
        String trackCreate = SPOT_ONCE_ANNOTATION +"\n";
        String trackSimple = SPOT_LIVE_TEMP_IF_ANY;

        try {
            GetCurrentTrackRS getCurrentTrackRS = getCurrentTrackService.getCurrentTrack(true);
            trackSummary = annotatedTrackInfo(SPOT_REPEAT_ANNOTATION,getCurrentTrackRS);
            trackCreate = annotatedTrackInfo(SPOT_ONCE_ANNOTATION,getCurrentTrackRS);
            trackSimple = trackInfo(getCurrentTrackRS);
        } catch (Exception e) {
            e.printStackTrace();
        }


        String text = document.getText();

        String trackRegex = Pattern.quote(SPOT_REPEAT_ANNOTATION) + "(.*?)" + Pattern.quote("}");
        String initTrackRegex = Pattern.quote(SPOT_ONCE_ANNOTATION) +Pattern.quote("{")+ "\\s*" + Pattern.quote("}");
        String liveTempRegex = Pattern.quote(SPOT_LIVE_TEMP_IF_ANY);

        text = text.replaceAll(liveTempRegex,trackSimple);
        text = text.replaceAll(trackRegex,trackSummary);
        text = text.replaceAll(initTrackRegex,trackCreate);


        String finalText = text;

        ApplicationManager.getApplication().runWriteAction(() -> document.setText(finalText));

        return true;
    }



    private String annotatedTrackInfo(String annotation, GetCurrentTrackRS getCurrentTrackRS){
        return String.format("%s {%s}",
                annotation,
                trackInfo(getCurrentTrackRS));
    }

    private String trackInfo(GetCurrentTrackRS getCurrentTrackRS){
        return String.format("%s - %s\t@Link %s",
                getCurrentTrackRS.getItem().getName(),
                getCurrentTrackRS.getItem().getArtists().get(0).getName(),
                getCurrentTrackRS.getItem().getExternal_urls().getSpotify());
    }

}
