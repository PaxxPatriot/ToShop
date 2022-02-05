package com.toshop.plugins.ui;

import com.toshop.application.interfaces.UIPlugin;

public class SwingUIPlugin implements UIPlugin {

    @Override
    public void Initialize() {
        var mainWindow = new MainWindow();
    }

}
