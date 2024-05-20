package com.vko.labworkproducer.firefox;

import com.vko.labworkproducer.chrome.ResetVariantsTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({RegistrationTest.class, LoginTest.class, LabWorkListTest.class, LabWorkDetailTest.class,
        AddAnswerTest.class, VariantTest.class, MyAnswersTest.class, AddLabWorkTest.class, EditLabWorkTest.class,
        DeleteLabWorkTest.class, TopicListTest.class, AddTopicTest.class, DeleteTopicTest.class, TaskListTest.class,
        AddTaskTest.class, DeleteTaskTest.class, LabWorkAnswersTest.class, AnswerListTest.class, ResetVariantsTest.class})
public class FirefoxTestStarter {
}
