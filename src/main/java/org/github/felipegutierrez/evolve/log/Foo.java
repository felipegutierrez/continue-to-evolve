package org.github.felipegutierrez.evolve.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Foo {

    static final Logger LOGGER = LoggerFactory.getLogger(Foo.class);

    public void doThat() {
        LOGGER.info("start");
        //...
        LOGGER.info("finish");
    }

    public void doThatWithParameters() throws InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        LOGGER.info("start at {}", sdf.format(new Date()));
        Thread.sleep(1000l);
        LOGGER.error("error at {}", sdf.format(new Date()));
        Thread.sleep(1000l);
        //...
        LOGGER.info("finish at {}", sdf.format(new Date()));
    }
}
