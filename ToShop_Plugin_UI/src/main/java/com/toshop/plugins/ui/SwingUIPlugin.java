package com.toshop.plugins.ui;

import com.toshop.application.Application;
import com.toshop.application.interfaces.UIPlugin;
import com.toshop.plugins.ui.mvc.base.View;
import com.toshop.plugins.ui.mvc.views.MainMenuView;

import javax.swing.*;
import java.awt.*;

public class SwingUIPlugin implements UIPlugin {

    private Application application;
    private View currentView;
    private JPanel currentViewPanel;
    private MainWindow mainWindow;
    private Font defaultFont;

    @Override
    public void Initialize(Application application) {
        this.application = application;

        defaultFont = new Font("Helvetica", Font.PLAIN, 12);

        mainWindow = new MainWindow();
        setView(new MainMenuView());
    }

    public void setView(View view) {
        if (currentViewPanel != null) {
            mainWindow.remove(currentViewPanel);
        }

        currentView = view;
        currentView.setUI(this);
        currentView.setApplication(application);
        currentView.initController();
        currentViewPanel = currentView.buildUI();
        mainWindow.add(currentViewPanel);
        mainWindow.invalidate();
        mainWindow.validate();
        mainWindow.repaint();
    }

    public View getCurrentView() {
        return currentView;
    }

    public Font getDefaultFont() {
        return defaultFont;
    }

}
