package com.example.koniary.controllers;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        AdminControllerTest.class,
        UserControllerTest.class,
        LoginControllerTest.class
})
public class ControllersTestSuite {
}
