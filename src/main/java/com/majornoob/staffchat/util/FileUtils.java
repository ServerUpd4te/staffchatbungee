package com.majornoob.staffchat.util;

import com.majornoob.staffchat.Main;

import java.io.*;

/**
 * Created by Jake on 3/14/14.
 */
public class FileUtils {
    public static boolean copy(InputStream from, File to) {
        try (InputStream input = from) {
            if (!to.exists()) {
                boolean result = to.createNewFile();
                if (!result) {
                    throw new IOException("File extraction failed!");
                }
            }
            try (FileOutputStream output = new FileOutputStream(to)) {
                byte[] b = new byte[8192];
                int length;
                while ((length = input.read(b)) > 0) {
                    output.write(b, 0, length);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}