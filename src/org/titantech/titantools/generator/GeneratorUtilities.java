package org.titantech.titantools.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class GeneratorUtilities extends GeneratorBase {

    public static void writeJSPFile(String toDir, String jspString, String jspFileName) {
        File srcDir = ensureSourceDirectoryExists(toDir);
        File fileOut = new File(srcDir, jspFileName + GeneratorBase.PERIOD +
                GeneratorBase.JSP_FILE_EXTENSION);
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(fileOut));
            w.write(jspString);
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeJavaClass(String toDir, String voStr, String packageName, String className) {
        File srcDir = ensureSourceDirectoryExists(toDir);
        File pckgDir = ensurePackageDirectoryStructureEsists(srcDir, packageName);
        File fileOut = new File(pckgDir, className + GeneratorBase.PERIOD +
                GeneratorBase.JAVA_CLASS_EXTENSION);
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(fileOut));
            w.write(voStr);
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeXMLFile(String toDir, String xmlString, String packageName, String fileName) {
        File srcDir = ensureSourceDirectoryExists(toDir);
        File pckgDir = ensurePackageDirectoryStructureEsists(srcDir, packageName);
        File fileOut = new File(pckgDir, fileName + JIBX_MAP_FILE_SUFFIX + GeneratorBase.PERIOD + GeneratorBase.XML_FILE_EXTENSION);
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(fileOut));
            w.write(xmlString);
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeTXTFile(String toDir, String txtString, String fileName) {
        File srcDir = ensureSourceDirectoryExists(toDir);
        File fileOut = new File(srcDir, fileName + GeneratorBase.PERIOD + GeneratorBase.TXT_FILE_EXTENSION);
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(fileOut));
            w.write(txtString);
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File ensurePackageDirectoryStructureEsists(File srcDir, String pckg) {
        //srcDir
        StringBuffer sb = new StringBuffer();
        sb.append(srcDir.getPath());
        StringTokenizer st = new StringTokenizer(pckg, GeneratorBase.PERIOD);
        while (st.hasMoreTokens()) {
            String str = (String) st.nextElement();
            sb.append(GeneratorBase.FORWARD_SLASH);
            sb.append(str);
        }
        return ensureSourceDirectoryExists(sb.toString());
    }

    public static File ensureSourceDirectoryExists(String dir) {
        File fileOut = new File(dir);
        if (!fileOut.exists()) {
            fileOut.mkdirs();
        }
        if (!fileOut.isDirectory()) {
            throw new RuntimeException(dir + " is not a valid directory.");
        }
        if (!fileOut.canWrite()) {
            throw new RuntimeException("Writing to " + dir + " is not allowed.");
        }
        return fileOut;
    }

}