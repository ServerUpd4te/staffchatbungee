package com.majornoob.staffchat.util;

import com.majornoob.staffchat.Main;

import java.io.*;

/**
 * Created by Jake on 3/14/14.
 */
public class FileManager {
    private static final Main plugin = Main.getInstance();

    /**
     * Creates a directory from the given arguments
     *
     * @param directory The directory to create
     * @return True if successful and False otherwise
     */
    public static boolean createDirectory(String directory) {
        File file = new File(plugin.getDataFolder(), File.separator + directory);

        if (!file.exists()) {
            try {
                file.mkdir();
                file.setReadable(true);
                file.setWritable(true);
            } catch (Exception e) {
                plugin.getLogger().warning("An error occurred while creating specified directory.");

                return false;
            }
        }

        return true;
    }

    /**
     * Creates a file from the given arguments
     *
     * @param baseDirectory The directory to start in, usually "getDataFolder()"
     * @param path          The path to follow from base directory
     * @param fileName      "The file's name (& ext.) to create
     * @return True if successful, False otherwise
     */
    public static boolean createFile(File baseDirectory, String path, String fileName) {
        File file = new File(baseDirectory, path + fileName.toLowerCase());

        if (!file.exists()) {
            try {
                file.createNewFile();
                file.setReadable(true);
                file.setWritable(true);
            } catch (IOException e) {
                plugin.getLogger().warning("An error occurred while creating the specified file.");

                return false;
            }
        }

        return true;
    }

    /**
     * Makes a copy of a file from a given directory to another directory
     *
     * @param from The file to copy
     * @param to   The destination
     * @return True or false depending on success
     */
    public static boolean copy(InputStream from, File to) {
        InputStream input = from;
        if (input != null) {
            try {
                if (!to.exists()) {
                    to.createNewFile();
                }
                try (FileOutputStream output = new FileOutputStream(to)) {
                    byte[] b = new byte[8192];
                    int length;
                    while ((length = input.read(b)) > 0) {
                        output.write(b, 0, length);
                    }
                }
                input.close();
            } catch (IOException e) {
                plugin.getLogger().warning("There was a problem copying the requested file.");
                e.printStackTrace(System.out);
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Clears a file from the given arguments
     *
     * @param baseDirectory The directory to start in, usually "getDataFolder()"
     * @param path          The path to follow from base directory.
     * @param file          The file to clear
     * @return true/false
     */
    public static boolean clearFile(File baseDirectory, String path, String file) {
        File f = getFile(baseDirectory, path, file.toLowerCase());
        if (!f.exists()) {
            return false;
        }
        try {
            RandomAccessFile t = new RandomAccessFile(file, "rw");
            t.setLength(0L);
        } catch (IOException e) {
            plugin.getLogger().warning("An I/O exception occurred while clearing a file.");
            return false;
        }
        return true;
    }

    /**
     * Clears all files in a directory from the given arguments
     *
     * @param baseDirectory The directory to start in, usually "getDataFolder()"
     * @param path          The path to follow from base directory.
     * @return true/false
     */
    public static boolean clearFilesIn(File baseDirectory, String path) {
        File[] files = getFilesIn(baseDirectory, path);
        for (File file : files) {
            try {
                RandomAccessFile t = new RandomAccessFile(file, "rw");
                t.setLength(0L);
            } catch (IOException e) {
                plugin.getLogger().warning("An I/O error occurred while clearing files.");
                return false;
            }
        }
        return true;
    }

    /**
     * Retrieves a file from the given arguments
     *
     * @param baseDirectory The directory to start in, usually "getDataFolder()"
     * @param path          The path to follow from base directory
     * @param file          The file to get
     * @return The specified file
     */
    public static File getFile(File baseDirectory, String path, String file) {
        return (new File(baseDirectory, path + file));
    }

    /**
     * Retrieves all files in a directory from the given arguments
     *
     * @param baseDirectory The directory to start in, usually "getDataFolder()"
     * @param path          The path to follow from base directory
     * @return An array of Files in the specified directory
     */
    public static File[] getFilesIn(File baseDirectory, String path) {
        return (new File(baseDirectory, path)).listFiles();
    }
}