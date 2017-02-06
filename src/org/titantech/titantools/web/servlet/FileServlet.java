package org.titantech.titantools.web.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.titantech.titantools.TTAppException;
import org.titantech.titantools.generator.GeneratorBase;
import org.titantech.titantools.util.ClassAndObjectUtils;
import org.titantech.titantools.web.action.GeneratorAction;

public class FileServlet extends HttpServlet {
    private static final long serialVersionUID = 722452254545230995L;

    private static Class CLAZZ = FileServlet.class;
    private static Logger logger = Logger.getLogger(CLAZZ);

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        executeRequest(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        executeRequest(request, response);
    }

    public void executeRequest(HttpServletRequest request,
                               HttpServletResponse response) throws ServletException, IOException {

        String ipAddress = getOriginatingIP(request);
        //HttpSession session = request.getSession(false);
        String fileName = request.getParameter("fn");
        try {
            Data Data = getData(fileName);
            String mimeType = "application/zip";
            response.setHeader("Content-Length", String.valueOf(Data.length));
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            ServletContext sc = getServletContext();
            response.setContentType(mimeType);
            response.getOutputStream().write(Data.file, 0, (int) Data.length);
            response.getOutputStream().close();
        } catch (Exception e) {
            logger.error("SYSTEM ERROR! " + ipAddress + ". " + e.getMessage());
            try {
                ObjectOutputStream output = new ObjectOutputStream(
                        response.getOutputStream());
                // Security Check Starts
                byte[] bytes = ClassAndObjectUtils.serialize("SYSTEM ERROR!");
                output.writeObject(bytes);
                output.flush();
                output.close();
            } catch (Exception e2) {
                logger.error("SYSTEM ERROR! " + ipAddress + ". DOUBLE ERROR. "
                        + e2.getMessage());
            }
        } catch (Throwable e) {
            logger.error("SYSTEM ERROR!! " + ipAddress + ". " + e.getMessage());
            try {
                ObjectOutputStream output = new ObjectOutputStream(
                        response.getOutputStream());
                // Security Check Starts
                byte[] bytes = ClassAndObjectUtils.serialize("SYSTEM ERROR!!");
                output.writeObject(bytes);
                output.flush();
                output.close();
            } catch (Exception e2) {
                logger.error("SYSTEM ERROR!! " + ipAddress + ". DOUBLE ERROR. "
                        + e2.getMessage());
            }
        }
    }

    private class Data {
        byte[] file = null;
        String fileName = null;
        long length = 0;
    }

    private Data getData(String fileName) throws TTAppException {
        File pif = new File(GeneratorAction.OUTPUT_DIR + "/" + fileName);
        Data result = new Data();
        result.fileName = pif.getAbsolutePath();
        result.length = pif.length();
        try {
            FileInputStream fis = new FileInputStream(pif);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                bos.write(buf, 0, readNum);
            }
            fis.close();
            result.file = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return result;
    }

    private String getOriginatingIP(HttpServletRequest req) {
        String remoteAddressStr = req.getRemoteAddr();
        if (remoteAddressStr == null) {
            remoteAddressStr = req.getRemoteHost();
        }
        return remoteAddressStr;
    }
}