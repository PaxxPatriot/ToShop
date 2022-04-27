package com.toshop.application.tests;

import com.toshop.application.Application;
import com.toshop.application.tests.mock.MockDatabasePlugin;
import com.toshop.application.tests.mock.MockRecipeProviderPlugin;
import com.toshop.application.tests.mock.MockUIPlugin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class ApplicationTests {

    private static Application testApplication;

    @BeforeAll
    static void initialize() {
        var mockDatabase = new MockDatabasePlugin();
        var mockUI = new MockUIPlugin();
        var mockRecipeProvider = new MockRecipeProviderPlugin();
        testApplication = new Application(mockDatabase, mockUI, mockRecipeProvider);
    }

    @Test
    @DisplayName("Example Test")
    void exampleTest() {
        assumeTrue(testApplication != null);
    }

}
