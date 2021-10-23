package com.encentral.imageconverter.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public abstract class AbstractImpl {
    protected JPAQueryFactory queryFactory = null;
    protected EntityManager entityManager = null;
    protected static final ThreadLocal<EntityManager> threadLocal;
      static {
        threadLocal = new ThreadLocal<EntityManager>();
    }

    public AbstractImpl() {

    }

    protected void reinitialize(EntityManager entityManager) {
        if(this.entityManager == null) {
            queryFactory = new JPAQueryFactory(entityManager);
            this.entityManager = entityManager;
            return;
        }
        // We have to create new entity managers if the current one is closed
        if(!this.entityManager.isOpen()) {

            this.entityManager = entityManager;
            queryFactory = new JPAQueryFactory(entityManager);
            return;
        }
    }

    protected void close(String entity) {
//        App.logger.info("Closing " + entity);
        this.entityManager.close();
    }

    protected EntityTransaction createTransaction() {
        EntityTransaction transaction = entityManager.getTransaction();
        return transaction;
    }

}
