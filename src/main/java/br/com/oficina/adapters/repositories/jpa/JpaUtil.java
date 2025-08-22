package br.com.oficina.adapters.repositories.jpa;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public final class JpaUtil {
    private static EntityManagerFactory emf;

    private JpaUtil() {}

    public static synchronized EntityManagerFactory emf() {
        if (emf == null) {
            // Nome da PU padrão do seu projeto
            String pu = System.getProperty("PU_NAME");
            if (pu == null || pu.isBlank()) {
                pu = "oficinaPU";
            }

            // Permite override via variáveis de ambiente (útil no Docker Compose)
            Map<String, Object> overrides = new HashMap<>();
            putIfPresent(overrides, "jakarta.persistence.jdbc.url", System.getenv("JPA_JDBC_URL"));
            putIfPresent(overrides, "jakarta.persistence.jdbc.user", System.getenv("JPA_JDBC_USER"));
            putIfPresent(overrides, "jakarta.persistence.jdbc.password", System.getenv("JPA_JDBC_PASSWORD"));
            putIfPresent(overrides, "jakarta.persistence.jdbc.driver", System.getenv("JPA_JDBC_DRIVER"));
            // Hibernate extra (opcional)
            putIfPresent(overrides, "hibernate.hbm2ddl.auto", System.getenv("HIBERNATE_HBM2DDL"));

            emf = overrides.isEmpty()
                    ? Persistence.createEntityManagerFactory(pu)
                    : Persistence.createEntityManagerFactory(pu, overrides);
        }
        return emf;
    }

    private static void putIfPresent(Map<String, Object> map, String key, String value) {
        if (value != null && !value.isBlank()) {
            map.put(key, value);
        }
    }

    public static synchronized void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            emf = null;
        }
    }
}
