package com.github.badaccuracy.id.dutisa.utils;

import java.io.Flushable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Closer implements AutoCloseable {

    private final List<AutoCloseable> closeableList;

    public Closer() {
        this.closeableList = new ArrayList<>();
    }


    /**
     * adds a closable instance
     *
     * @param closeable the closeable instance
     */
    public <T extends AutoCloseable> T add(T closeable) {
        if (closeable == null)
            return null;

        closeableList.add(closeable);
        return closeable;
    }


    @Override
    public void close() throws Exception {
        Iterator<AutoCloseable> iterator = closeableList.iterator();

        while (iterator.hasNext()) {
            AutoCloseable next = iterator.next();

            if (next != null) {
                if (next instanceof Flushable)
                    try {
                        ((Flushable) next).flush();
                    } catch (Exception ignored) {
                    }

                try {
                    next.close();
                } catch (Exception ignored) {
                }
            }

            iterator.remove();
        }

        closeableList.clear();
    }
}
