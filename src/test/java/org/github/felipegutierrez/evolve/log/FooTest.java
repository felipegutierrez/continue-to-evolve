package org.github.felipegutierrez.evolve.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class FooTest {

    @Test
    public void doThat() throws Exception {
        // get Logback Logger
        Logger fooLogger = (Logger) LoggerFactory.getLogger(Foo.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        // addAppender is outdated now
        fooLogger.addAppender(listAppender);

        // call method under test
        Foo foo = new Foo();
        foo.doThat();

        // JUnit assertions
        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals("start", logsList.get(0).getMessage());
        assertEquals(Level.INFO, logsList.get(0).getLevel());

        assertEquals("finish", logsList.get(1).getMessage());
        assertEquals(Level.INFO, logsList.get(1).getLevel());
    }

    @Test
    public void doThatWithParameters() throws Exception {
        // get Logback Logger
        Logger fooLogger = (Logger) LoggerFactory.getLogger(Foo.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        // addAppender is outdated now
        fooLogger.addAppender(listAppender);

        // call method under test
        Foo foo = new Foo();
        foo.doThatWithParameters();

        // JUnit assertions
        List<ILoggingEvent> logsList = listAppender.list;
        assertTrue(logsList.get(0).getMessage().contains("start at"));
        assertEquals(Level.INFO, logsList.get(0).getLevel());

        assertTrue(logsList.get(1).getMessage().contains("error at"));
        assertEquals(Level.ERROR, logsList.get(1).getLevel());

        assertTrue(logsList.get(2).getMessage().contains("finish at"));
        assertEquals(Level.INFO, logsList.get(2).getLevel());
    }

    @Test
    public void doThatAgain() throws Exception {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Foo.class);

        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        final ch.qos.logback.core.Appender mockAppender = mock(ch.qos.logback.core.Appender.class);
        when(mockAppender.getName()).thenReturn("MOCK");
        root.addAppender(mockAppender);

        // call method under test
        Foo foo = new Foo();
        foo.doThat();

        verify(mockAppender).doAppend(argThat(new ArgumentMatcher() {
            @Override
            public boolean matches(final Object argument) {
                String msg = ((ch.qos.logback.classic.spi.LoggingEvent) argument).getFormattedMessage();
                return msg.contains("start")
                        // && msg.contains("finish")
                        ;
            }
        }));
    }
}