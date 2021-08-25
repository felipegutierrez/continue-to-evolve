package org.github.felipegutierrez.evolve.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Foo {

    static final Logger LOGGER = LoggerFactory.getLogger(Foo.class);

    public void doThat() {
        LOGGER.info("start");
        //...
        LOGGER.info("finish");
    }
}
