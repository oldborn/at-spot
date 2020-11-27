package io.github.oldborn.atspot.macro;

import com.intellij.codeInsight.template.Expression;
import com.intellij.codeInsight.template.ExpressionContext;
import com.intellij.codeInsight.template.Result;
import com.intellij.codeInsight.template.TextResult;
import com.intellij.codeInsight.template.macro.MacroBase;
import com.intellij.util.concurrency.AppExecutorUtil;
import io.github.oldborn.atspot.spotifywebapi.currenttrack.SptfyGetCurrentTrackService;
import io.github.oldborn.atspot.spotifywebapi.currenttrack.model.GetCurrentTrackRS;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AtSpotMacro extends MacroBase {


    private SptfyGetCurrentTrackService getCurrentTrackService = SptfyGetCurrentTrackService.getInstance();

    public AtSpotMacro() {
        super("spot", "spot");
    }
    
    @Nullable
    @Override
    protected Result calculateResult(@NotNull Expression[] expressions, ExpressionContext expressionContext, boolean b) {
        GetCurrentTrackRS currentTrackRS = null;
        String currentTrackString = "";
        try {
            currentTrackRS = getCurrentTrackService.getCurrentTrack(false);
        } catch (Exception ignored) {
            currentTrackRS = getCurrentTrackService.getLastKnownRecord();
        }

        if (currentTrackRS != null){
            currentTrackString = String.format("%s - %s @Link %s",
                    currentTrackRS.getItem().getName(),
                    currentTrackRS.getItem().getArtists().get(0).getName(),
                    currentTrackRS.getItem().getExternal_urls().getSpotify());
        }
        
        return new TextResult(currentTrackString);
    }

}
