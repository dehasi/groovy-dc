package decompiler.utils;

import org.objectweb.asm.ClassReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Loader {

    public static ClassReader loadFromFileSystem(String path) throws IOException {
        InputStream in = new FileInputStream(new File(path));
        ClassReader classReader = new ClassReader(in);
        return classReader;
    }

    public static ClassReader loadFromFileStandartLibrary(String path) throws IOException {
        ClassReader classReader = new ClassReader(path);
        return classReader;
    }

    public static ClassReader loadFromFileSystemOrNull(String path) {
        try {
            return loadFromFileSystem(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
