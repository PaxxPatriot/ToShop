package com.toshop.plugins.ui.mvc.base;

import com.toshop.application.Application;
import com.toshop.plugins.ui.SwingUIPlugin;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

public abstract class View<T extends Controller> {
    protected T controller;
    protected SwingUIPlugin ui;
    protected Application application;

    public View() {
        try {
            // Instantiate controller using reflection, avoids unnecessary typing
            controller = (T) ((Class)((ParameterizedType)this.getClass().
                    getGenericSuperclass()).getActualTypeArguments()[0]).getConstructor(this.getClass()).newInstance(this);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


    public abstract JPanel buildUI();

    public final void setUI(SwingUIPlugin ui) {
        this.ui = ui;
        if (controller != null) controller.setUI(ui);
    }

    public final void setApplication(Application application) {
        this.application = application;
        if (controller != null) controller.setApplication(application);
    }

    public final void initController() {
        controller.init();
    }
}
