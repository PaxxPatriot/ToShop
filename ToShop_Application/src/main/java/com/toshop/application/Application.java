package com.toshop.application;

import com.toshop.application.interfaces.*;

public class Application {

    private DatabasePlugin database;
    private UIPlugin ui;

    public Application(DatabasePlugin databasePlugin, UIPlugin uiPlugin) {
        this.database = databasePlugin;
        this.ui = uiPlugin;

        this.ui.Initialize();
    }
}
