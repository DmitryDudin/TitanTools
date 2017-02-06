package org.titantech.titantools;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.titantech.titantools.dao.PGDAOFactory;

public class TitanToolsStartup implements ServletContextListener {

    private static Class CLAZZ = TitanToolsStartup.class;
    private static Logger logger = Logger.getLogger(CLAZZ);

    public void contextInitialized(ServletContextEvent event) {

        DOMConfigurator.configure(this.getClass().getClassLoader().getResource("log4j.properties"));
        System.out.println("TitanToolsStartup");
        logger.debug("TitanToolsStartup");
        try {
//			TextConstants.getInstance().load();
            AppConstants.getInstance().load();
            PGDAOFactory.initializeConnectionPool();


//			logger.debug("TitanToolsStartup Available Processors: " + Runtime.getRuntime().availableProcessors());
//			logger.debug("TitanToolsStartup Total Memory: " + Runtime.getRuntime().totalMemory());
//			logger.debug("TitanToolsStartup Max Memory: " + Runtime.getRuntime().maxMemory());
//			logger.debug("TitanToolsStartup Free Memory: " + Runtime.getRuntime().freeMemory());
//			Runtime.getRuntime().runFinalization();
//			logger.debug("TitanToolsStartup Executed Finalizers.");

//			RunRefresh runRefresh = new RunRefresh();
//			new Thread(runRefresh).start();
//
//			new AdminEvent(new Date());
        } catch (Throwable e) {
            String msg = "TitanToolsStartup.contextInitialized() : message " + e.getMessage();
            System.out.println(msg);
            //logger.error(msg);
        }
    }

//	private class RunRefresh implements Runnable {
//		public void run() {
//			
//			if (AppConstants.USE_MASTER_PRODUCT_BASED_LOGIC) {
//				ru.scarlettgroup.titanconsole.delegate.mp.ReportDelegate delegate = new ru.scarlettgroup.titanconsole.delegate.mp.ReportDelegate();
//				try {
//					delegate.refreshProductData();
//				} catch (HAppException e) {
//					e.printStackTrace();
//				}
//			} else {
//				ru.scarlettgroup.titanconsole.delegate.ReportDelegate delegate = new ru.scarlettgroup.titanconsole.delegate.ReportDelegate();
//				try {
//					delegate.refreshProductData();
//				} catch (HAppException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}

    public void contextDestroyed(ServletContextEvent event) {
//		try {
//			PGDAOFactory.deactivateConnectionPool();
//			AdminEvent.nextEventToExecute.cancel();
//			AdminEvent.nextEventToExecute.timer.cancel();
//		} catch (Throwable e) {
//			String msg = "TitanToolsStartup.contextDestroyed() : message " + e.getMessage();
//			System.out.println(msg);
//			//logger.error(msg);
//		}
    }
}