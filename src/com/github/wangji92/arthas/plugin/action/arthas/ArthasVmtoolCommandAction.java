package com.github.wangji92.arthas.plugin.action.arthas;

import com.github.wangji92.arthas.plugin.setting.AppSettingsState;
import com.github.wangji92.arthas.plugin.ui.ArthasVmToolDialog;
import com.github.wangji92.arthas.plugin.utils.OgnlPsUtils;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

/**
 * vmtool get instance to invoke method field
 * @author 汪小哥
 * @date 01-06-2021
 */
public class ArthasVmtoolCommandAction extends AnAction {


    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        DataContext dataContext = e.getDataContext();
        //获取当前事件触发时，光标所在的元素
        PsiElement psiElement = CommonDataKeys.PSI_ELEMENT.getData(dataContext);
        if (!OgnlPsUtils.isPsiFieldOrMethodOrClass(psiElement)) {
            e.getPresentation().setEnabled(false);
            return;
        }
        boolean staticField = OgnlPsUtils.isStaticField(psiElement);
        if (staticField) {
            e.getPresentation().setEnabled(false);
            return;
        }
        boolean anonymousClass = OgnlPsUtils.isAnonymousClass(psiElement);
        if (anonymousClass) {
            e.getPresentation().setEnabled(false);
            return;
        }
        e.getPresentation().setEnabled(true);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        DataContext dataContext = e.getDataContext();
        Project project = e.getProject();
        assert project != null;
        PsiElement psiElement = CommonDataKeys.PSI_ELEMENT.getData(dataContext);
        String className = OgnlPsUtils.getCommonOrInnerOrAnonymousClassName(psiElement);
        String executeInfo = OgnlPsUtils.getExecuteInfo(psiElement);
        AppSettingsState instance = AppSettingsState.getInstance(project);
        String depthPrintPropertyX = instance.depthPrintProperty;
        String command = String.join(" ", "vmtool", "-x", depthPrintPropertyX, "--action getInstances --className", className, "--express 'instances[0]." + executeInfo + "'");
        ArthasVmToolDialog dialog = new ArthasVmToolDialog(project,command,className);
        dialog.open();


    }
}
