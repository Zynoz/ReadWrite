package com.zynoz;

import com.sun.istack.internal.NotNull;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("unchecked")
public final class Util<T> {
    private static Util INSTANCE;

    private Util() {}

    public static Util getInstance() {
       if (INSTANCE == null) {
           INSTANCE = new Util();
       }
       return INSTANCE;
    }

    public void writeCollection(@NotNull final Path path, @NotNull final Collection<T> collection) throws NullPointerException {
        Objects.requireNonNull(path, "Path must not be null");
        Objects.requireNonNull(collection, "Collection must not be null");
        try(final ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
            oos.writeObject(collection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<Collection<T>> readCollection(@NotNull final Path path) throws NullPointerException{
        Objects.requireNonNull(path, "Path must not be null");
        try(final ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))) {
            return Optional.of((Collection<T>) ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}