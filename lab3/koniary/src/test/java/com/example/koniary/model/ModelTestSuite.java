package com.example.koniary.model;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        HorseTest.class,
        StableTest.class,
        StableManagerTest.class
})
public class ModelTestSuite {
}
