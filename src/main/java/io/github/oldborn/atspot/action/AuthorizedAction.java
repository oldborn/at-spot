package io.github.oldborn.atspot.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import io.github.oldborn.atspot.util.StorageService;


public abstract class AuthorizedAction extends AnAction {

    private StorageService storageService = StorageService.getInstance();

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        Presentation presentation = e.getPresentation();
        if (storageService.getAccess_token() == null || storageService.getAccess_token().isEmpty())
            presentation.setEnabled(false);
        else
            presentation.setEnabled(true);
    }
}
