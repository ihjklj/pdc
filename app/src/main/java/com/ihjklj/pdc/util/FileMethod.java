package com.ihjklj.pdc.util;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by ihjklj on 2018/5/6.
 */

public class FileMethod {

    static public void saveXml(String filename, String data) {

        try {
            File file = new File(filename);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream output = new FileOutputStream(file);
            output.write(data.getBytes());
            output.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
