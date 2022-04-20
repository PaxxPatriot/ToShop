package com.toshop.plugins.ui.mvc.base;

import com.toshop.application.Application;
import com.toshop.plugins.ui.SwingUIPlugin;

public abstract class Controller<T extends View> {
    protected T view;
    protected SwingUIPlugin ui;
    protected Application application;

    public Controller(T view) {
        this.view = view;
    }

    public final void setUI(SwingUIPlugin ui) {
        this.ui = ui;
    }

    public final void setApplication(Application application) {
        this.application = application;
    }

    public abstract void init();
}
