package io.github.oldborn.atspot.util;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.ui.popup.ListPopupStep;
import com.intellij.openapi.util.Condition;
import com.intellij.ui.popup.PopupFactoryImpl;
import com.intellij.ui.popup.WizardPopup;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.event.KeyEvent;

public class SpotActionGroupPopup extends PopupFactoryImpl.ActionGroupPopup {

    @Setter private SpotKeyEventCallback spotKeyEventCallback = null;

    public SpotActionGroupPopup(String title, @NotNull ActionGroup actionGroup, @NotNull DataContext dataContext, boolean showNumbers, boolean useAlphaAsNumbers, boolean showDisabledActions, boolean honorActionMnemonics, Runnable disposeCallback, int maxRowCount, Condition<AnAction> preselectActionCondition, @Nullable String actionPlace) {
        super(title, actionGroup, dataContext, showNumbers, useAlphaAsNumbers, showDisabledActions, honorActionMnemonics, disposeCallback, maxRowCount, preselectActionCondition, actionPlace);
        this.mySpeedSearch.setEnabled(false);
    }

    public SpotActionGroupPopup(String title, @NotNull ActionGroup actionGroup, @NotNull DataContext dataContext, boolean showNumbers, boolean useAlphaAsNumbers, boolean showDisabledActions, boolean honorActionMnemonics, Runnable disposeCallback, int maxRowCount, Condition<AnAction> preselectActionCondition, @Nullable String actionPlace, boolean autoSelection) {
        super(title, actionGroup, dataContext, showNumbers, useAlphaAsNumbers, showDisabledActions, honorActionMnemonics, disposeCallback, maxRowCount, preselectActionCondition, actionPlace, autoSelection);
    }

    protected SpotActionGroupPopup(@Nullable WizardPopup aParent, @NotNull ListPopupStep step, @Nullable Runnable disposeCallback, @NotNull DataContext dataContext, @Nullable String actionPlace, int maxRowCount) {
        super(aParent, step, disposeCallback, dataContext, actionPlace, maxRowCount);
    }



    @Override
    protected void process(KeyEvent aEvent) {
        if (spotKeyEventCallback != null) {
            try {
                spotKeyEventCallback.keyPressed(aEvent);
            } catch (Exception ignored) {}
        }
        if(!aEvent.isConsumed()) super.process(aEvent);
    }
}
