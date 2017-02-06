package org.titantech.titantools;

import java.util.Map;

import org.titantech.titantools.delegate.DatabaseMetadataDelegate;

public class TestClass {
    public static void main(String[] args) {
        DatabaseMetadataDelegate dbMD = new DatabaseMetadataDelegate();
        try {
            Map data = dbMD.getDatabaseTablesMap("localhost", "test", "testadmin", "TEST123", 0);
        } catch (TTAppException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
