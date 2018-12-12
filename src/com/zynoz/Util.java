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
public final class Util<O> {
    private static Util INSTANCE;

    private Util() {}

    public static Util getInstance() {
       if (INSTANCE == null) {
           INSTANCE = new Util();
       }
       return INSTANCE;
    }

    /**
     * This method takes any object and serializes it.
     * @param path Path to the file.
     * @param object Object to be serialized.
     * @throws NullPointerException Throws NullPointerException if either path or object is null.
     */
    public void writeCollection(@NotNull final Path path, @NotNull final O object) throws NullPointerException {
        Objects.requireNonNull(path, "Path must not be null");
        Objects.requireNonNull(object, "Object must not be null");
        try(final ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
            oos.writeObject(object);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method reads a serialized object and returns it.
     * @param path Path to the serialized object.
     * @return Returns an optional that may or may not contain the serialized object depending on the succress of the ObjectInputStream.
     * @throws NullPointerException Throws NullPointerException if path is null.
     */
    public Optional<O> readCollection(@NotNull final Path path) throws Exception {
        Objects.requireNonNull(path, "Path must not be null");
        try(final ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))) {
            return Optional.of((O) ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }
}