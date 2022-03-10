package com.toshop.application.tests;

import com.toshop.application.Application;
import com.toshop.application.interfaces.DatabasePlugin;
import com.toshop.application.interfaces.UIPlugin;
import org.easymock.EasyMock;
import org.easymock.Mock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class ApplicationTests {

    private static Application testApplication;

    @Mock
    private static DatabasePlugin database = EasyMock.createMock(DatabasePlugin.class);

    @Mock
    private static UIPlugin ui = EasyMock.createMock(UIPlugin.class);

    @Test
    @DisplayName("Example Test")
    void exampleTest() {
        ui.Initialize();
        EasyMock.replay(ui, database);
        testApplication = new Application(database, ui);
        assumeTrue(testApplication != null);
        EasyMock.verify(ui, database);
    }

}
