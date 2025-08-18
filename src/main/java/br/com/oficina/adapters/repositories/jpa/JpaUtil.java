package br.com.oficina.adapters.repositories.jpa;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class JpaUtil {
    private static EntityManagerFactory emf;

    private JpaUtil(){}

    public static synchronized EntityManagerFactory emf() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("oficinaPU");
        }
        return emf;
    }

    public static synchronized void close() {
        if (emf != null) {
            emf.close();
            emf = null;
        }
    }
}
