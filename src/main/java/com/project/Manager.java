package com.project;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import java.util.Collection;
import java.util.Set;

public class Manager {
    private static SessionFactory factory;

    // Otros

    public static void createSessionFactory() {
        try {
            Configuration configuration = new Configuration();

            // Add the mapping resources instead of annotated classes
            configuration.addResource("Ciutada.hbm.xml");
            configuration.addResource("Ciutat.hbm.xml");

            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            factory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void close() {
        factory.close();
    }

    // Ciutada

    public static Ciutada addCiutada(String nom, String cognom, int edat) {
        Session session = factory.openSession();
        Transaction tx = null;
        Ciutada result = null;
        try {
            tx = session.beginTransaction();
            result = new Ciutada(nom,cognom,edat);
            session.persist(result);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            result = null;
        } finally {
            session.close();
        }
        return result;
    }

    public static void updateCiutada(long ciutadaId, String nom, String cognom, int edat) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Ciutada ciutada = session.get(Ciutada.class, ciutadaId);
            if (ciutada == null) {
                throw new RuntimeException("Ciutada not found with id: " + ciutadaId);
            }

            ciutada.setNom(nom);
            ciutada.setCognom(cognom);
            ciutada.setEdat(edat);

            session.merge(ciutada);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    // Ciutat

    public static Ciutat addCiutat(String nom, String pais, int poblacio) {
        Session session = factory.openSession();
        Transaction tx = null;
        Ciutat result = null;
        try {
            tx = session.beginTransaction();
            result = new Ciutat();
            result.setNom(nom);
            result.setPais(pais);
            result.setPoblacio(poblacio);
            session.persist(result);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            result = null;
        } finally {
            session.close();
        }
        return result;
    }


    public static void updateCiutat(long ciutatId, String nom, String pais, int poblacio, Set<Ciutada> ciutadans) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Ciutat ciutat = session.get(Ciutat.class, ciutatId);
            if (ciutat == null) {
                throw new RuntimeException("Ciutat not found with id: " + ciutatId);
            }

            ciutat.setNom(nom);
            ciutat.setPais(pais);
            ciutat.setPoblacio(poblacio);
            ciutat.setCiutadans(ciutadans);

            session.merge(ciutat);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    // Ambos

    public static Ciutat getCiutatWithCiutadans(long ciutatId) {
        Session session = factory.openSession();
        Ciutat ciutat = null;
        try {
            ciutat = session.get(Ciutat.class, ciutatId);
            if (ciutat != null) {
                // Acceso directo para cargar la colección
                ciutat.getCiutadans().size(); // Inicializa la colección
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return ciutat;
    }



    public static void delete(Class<?> className, long id) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Object entity = session.get(className, id);
            if (entity != null) {
                session.delete(entity);
            } else {
                throw new RuntimeException("Entity not found with id: " + id);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    public static <T> Collection<?> listCollection(Class<? extends T> clazz, String where) {
        Session session = factory.openSession();
        Transaction tx = null;
        Collection<?> result = null;
        try {
            tx = session.beginTransaction();
            String queryString = "FROM " + clazz.getName();
            if (where != null && !where.trim().isEmpty()) {
                queryString += " WHERE " + where;
            }
            result = session.createQuery(queryString, clazz).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }


    public static <T> String collectionToString(Class<? extends T> clazz, Collection<?> collection){
        StringBuilder txt = new StringBuilder();
        for (Object obj : collection) {
            T cObj = clazz.cast(obj);
            // Verificamos si el objeto tiene colecciones lazy y las inicializamos
            if (cObj instanceof Ciutat ciutat) {
                // Inicializamos la colección de 'ciutadans' si es lazy
                if (ciutat.getCiutadans() != null) {
                    ciutat.getCiutadans().size(); // Esto inicializa la colección
                }
            }
            txt.append("\n").append(cObj.toString());
        }

        // Limpiar el primer salto de línea si existe
        if (txt.length() > 0 && txt.charAt(0) == '\n') {
            txt.deleteCharAt(0);
        }

        return txt.toString();
    }




}