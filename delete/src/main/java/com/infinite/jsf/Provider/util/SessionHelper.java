package com.infinite.jsf.Provider.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionHelper {
    private static final Logger logger = LoggerFactory.getLogger(SessionHelper.class);
    private static final SessionFactory sessionFactory;

    static {
        logger.info("SessionHelper static initializer started");
        try {
            logger.debug("Loading Hibernate configuration (hibernate.cfg.xml)");
            AnnotationConfiguration cfg = new AnnotationConfiguration().configure();
            logger.debug("Building SessionFactory from configuration");
            
            sessionFactory = cfg.buildSessionFactory();
            logger.info("SessionFactory built successfully");
        } 
        catch (Throwable ex) {
            logger.error("Initial SessionFactory creation failed", ex);
            // rethrow to notify caller of the failure
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        logger.debug("getSessionFactory() called");
        return sessionFactory;
    }
}
