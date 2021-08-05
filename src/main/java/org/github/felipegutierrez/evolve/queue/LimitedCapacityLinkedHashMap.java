package org.github.felipegutierrez.evolve.queue;

public class LimitedCapacityLinkedHashMap<K, V> extends java.util.LinkedHashMap<K, V> {
    private final int maxSize;

    public LimitedCapacityLinkedHashMap(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public V put(K k, V v) {

        // TODO: if the KEY does not exist
        if (!super.containsKey(k)) {
            // TODO: if the limit does not exceed
            if (size() < maxSize) {
                // add the new element
                return super.put(k, v);
            } else {
                // TODO: if the limit exceed then remove the oldest and add the new
                assert !super.isEmpty();
                K oldestKey = entrySet().iterator().next().getKey();
                super.remove(oldestKey);
                return super.put(k, v);
            }
        } // if the key already exists do nothing
        return null;
    }
}
