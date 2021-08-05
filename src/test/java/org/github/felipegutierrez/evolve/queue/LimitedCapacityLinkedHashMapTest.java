package org.github.felipegutierrez.evolve.queue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LimitedCapacityLinkedHashMapTest {

    @Test
    void putFixedAmountOfElements() {

        LimitedCapacityLinkedHashMap<Integer, String> linkedHashMap = new LimitedCapacityLinkedHashMap<Integer, String>(5);
        linkedHashMap.put(1, "one");
        linkedHashMap.put(2, "two");
        linkedHashMap.put(3, "three");
        linkedHashMap.put(4, "four");
        linkedHashMap.put(4, "four-again");
        linkedHashMap.put(5, "five");
        linkedHashMap.put(6, "six");
        linkedHashMap.put(7, "seven");
        linkedHashMap.put(7, "seven-again");

        assertEquals(5, linkedHashMap.size());

        assertNull(linkedHashMap.get(0));
        assertNull(linkedHashMap.get(1));
        assertNull(linkedHashMap.get(2));
        assertNull(linkedHashMap.get(-3));

        assertEquals("three", linkedHashMap.get(3));
        assertEquals("four", linkedHashMap.get(4));
        assertEquals("seven", linkedHashMap.get(7));
    }

    @Test
    void returnNullWhenItIsEmpty() {
        LimitedCapacityLinkedHashMap<Integer, String> linkedHashMap = new LimitedCapacityLinkedHashMap<Integer, String>(5);
        assertNull(linkedHashMap.get(1));
    }
}