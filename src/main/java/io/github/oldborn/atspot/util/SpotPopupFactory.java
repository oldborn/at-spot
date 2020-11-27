package io.github.oldborn.atspot.util;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.ui.popup.PopupFactoryImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpotPopupFactory extends PopupFactoryImpl {
    
    
    @NotNull
    @Override
    public ListPopup createActionGroupPopup(@NlsContexts.PopupTitle String title, @NotNull ActionGroup actionGroup, @NotNull DataContext dataContext, ActionSelectionAid aid, boolean showDisabledActions, Runnable disposeCallback, int maxRowCount, Condition<? super AnAction> preselectActionCondition, @Nullable String actionPlace) {
        SpotActionGroupPopup var10000 = new SpotActionGroupPopup(title, actionGroup, dataContext, aid == ActionSelectionAid.ALPHA_NUMBERING || aid == ActionSelectionAid.NUMBERING, aid == ActionSelectionAid.ALPHA_NUMBERING, showDisabledActions, aid == ActionSelectionAid.MNEMONICS, disposeCallback, maxRowCount, (Condition<AnAction>) preselectActionCondition, actionPlace);
        return var10000;
    }

    @NotNull
    @Override
    public ListPopup createActionGroupPopup(@NlsContexts.PopupTitle String title, @NotNull ActionGroup actionGroup, @NotNull DataContext dataContext, boolean showNumbers, boolean showDisabledActions, boolean honorActionMnemonics, Runnable disposeCallback, int maxRowCount, Condition<? super AnAction> preselectActionCondition) {
        SpotActionGroupPopup var10000 = new SpotActionGroupPopup(title, actionGroup, dataContext, showNumbers, true, showDisabledActions, honorActionMnemonics, disposeCallback, maxRowCount, (Condition<AnAction>) preselectActionCondition, (String)null);
        return var10000;
    }
}
