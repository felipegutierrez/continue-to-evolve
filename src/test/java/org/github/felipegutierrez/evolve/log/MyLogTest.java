package org.github.felipegutierrez.evolve.log;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MyLogTest {
    @Test
    public void warnCalled() {
        Logger loggerMock = mock(Logger.class);
        MyLog myClassToTest = new MyLog(loggerMock);
        myClassToTest.someOperation();
        verify(loggerMock).warn("warning message");
    }
}