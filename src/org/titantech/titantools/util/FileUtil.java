package org.titantech.titantools.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtil {
    final static int BUFFER = 2048;

    public static void compressDirectoryContents(String fileName, String dir) {
        String s = null;
        StringBuffer sbOutput = new StringBuffer();
        StringBuffer errorInfo = new StringBuffer();
        String[] cmd = {"tar", "-czf", fileName, dir};
        try {
            File f = new File(fileName);
            new FileOutputStream(f).close();
            //file.setLastModified(timestamp);

            Runtime rt = Runtime.getRuntime();
            System.out.println("Running command: " + cmd[0] + " " + cmd[1] + " " + cmd[2]);
            Process p = rt.exec(cmd);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            // If there is an error - only show that
            while ((s = stdError.readLine()) != null) {
                errorInfo.append(s + "\n");
            }
            if (errorInfo.length() > 0) {
                System.out.println(errorInfo.toString());
            }
            while ((s = stdInput.readLine()) != null) {
                sbOutput.append(s + "\n");
            }
            // wait for end of command execution
            try {
                p.waitFor();
            } catch (InterruptedException ie) {
                //new LogErrThread(ie).start();
                ie.printStackTrace();
            }
            p.destroy();
            if (sbOutput.length() > 0) {
                System.out.println(sbOutput.toString());

            }
        } catch (IOException e) {
            //new LogErrThread(e).start();
            e.printStackTrace();
        }
    }

    public static void deleteDirectoryContents(String dir) {
        String s = null;
        StringBuffer sbOutput = new StringBuffer();
        StringBuffer errorInfo = new StringBuffer();
        if (!dir.startsWith("/home/roman/dev/TitanTools/OUTPUT/")) {
            return;
        }
//		try {
//			Runtime.getRuntime().exec("rm -rf " + dir);
//		} catch (IOException e) {
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}
//	}
        String[] cmd = {"rm", "-rf", dir};
        try {
            Runtime rt = Runtime.getRuntime();
            System.out.println("Running command: " + cmd[0] + " " + cmd[1] + " " + cmd[2]);
            Process p = rt.exec(cmd);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            // If there is an error - only show that
            while ((s = stdError.readLine()) != null) {
                errorInfo.append(s + "\n");
            }
            if (errorInfo.length() > 0) {
                System.out.println(errorInfo.toString());
            }
            while ((s = stdInput.readLine()) != null) {
                sbOutput.append(s + "\n");
            }
            // wait for end of command execution
            try {
                p.waitFor();
            } catch (InterruptedException ie) {
                //new LogErrThread(ie).start();
                ie.printStackTrace();
            }
            p.destroy();
            if (sbOutput.length() > 0) {
                System.out.println(sbOutput.toString());

            }
        } catch (IOException e) {
            //new LogErrThread(e).start();
            e.printStackTrace();
        }
    }

    // public static void deleteDirectory(String dir) {
    // File file = new File(dir);
    // String[] myFiles;
    // if (file.isDirectory()) {
    // myFiles = file.list();
    // for (int i=0; i<myFiles.length; i++) {
    // File myFile = new File(file, myFiles[i]);
    // myFile.delete();
    // }
    // }
    // }

    // public static boolean createZipArchive(String srcFolder, String
    // newFileName) {
    // try {
    // BufferedInputStream origin = null;
    // FileOutputStream dest = new FileOutputStream(new File(srcFolder + "/" +
    // newFileName)); // + ".zip"
    // ZipOutputStream out = new ZipOutputStream(new
    // BufferedOutputStream(dest));
    // byte data[] = new byte[BUFFER];
    // File subDir = new File(srcFolder);
    // String subdirList[] = subDir.list();
    // for (String sd : subdirList) {
    // // get a list of files from current directory
    // File f = new File(srcFolder + "/" + sd);
    // if (f.isDirectory()) {
    // String files[] = f.list();
    // for (int i = 0; i < files.length; i++) {
    // System.out.println("Adding: " + files[i]);
    // FileInputStream fi = new FileInputStream(srcFolder
    // + "/" + sd + "/" + files[i]);
    // origin = new BufferedInputStream(fi, BUFFER);
    // ZipEntry entry = new ZipEntry(sd + "/" + files[i]);
    // out.putNextEntry(entry);
    // int count;
    // while ((count = origin.read(data, 0, BUFFER)) != -1) {
    // out.write(data, 0, count);
    // out.flush();
    // }
    // }
    // } else // it is just a file
    // {
    // FileInputStream fi = new FileInputStream(f);
    // origin = new BufferedInputStream(fi, BUFFER);
    // ZipEntry entry = new ZipEntry(sd);
    // out.putNextEntry(entry);
    // int count;
    // while ((count = origin.read(data, 0, BUFFER)) != -1) {
    // out.write(data, 0, count);
    // out.flush();
    // }
    // }
    // }
    // origin.close();
    // out.flush();
    // out.close();
    // } catch (Exception e) {
    // //log.info("createZipArchive threw exception: " + e.getMessage());
    // e.printStackTrace();
    // System.out.println(e.getMessage());
    // return false;
    // }
    // return true;
    // }
}