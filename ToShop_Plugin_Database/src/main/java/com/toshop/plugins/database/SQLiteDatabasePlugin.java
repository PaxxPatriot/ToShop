package com.toshop.plugins.database;

import com.toshop.application.interfaces.DatabasePlugin;
import com.toshop.domain.entities.Product;
import com.toshop.domain.entities.ShoppingList;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

public class SQLiteDatabasePlugin implements DatabasePlugin {

    private final String filePath;

    private SessionFactory sessionFactory;

    public SQLiteDatabasePlugin(String filePath) {
        this.filePath = filePath;

        //https://stackoverflow.com/a/25768383
        @SuppressWarnings("unused")
        org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        String connectionString = "jdbc:sqlite:" + filePath;
        Configuration configuration = new Configuration().configure().setProperty("hibernate.connection.url", connectionString).addResource("mappings.xml");
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(serviceRegistryBuilder.build());
    }

    @Override
    public void persistShoppingList(ShoppingList list) {
        var session = sessionFactory.openSession();
        try {
            var transaction = session.beginTransaction();
            session.persist(list);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<ShoppingList> getShoppingList(UUID shoppingListId) {
        var session = sessionFactory.openSession();
        try {
            return Optional.ofNullable(session.get(ShoppingList.class, shoppingListId));
        } finally {
            session.close();
        }
    }

    public List<ShoppingList> getAllShoppingLists() {
        var session = sessionFactory.openSession();
        try {
            return session.createQuery("FROM ShoppingList", ShoppingList.class).getResultList();
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteShoppingList(ShoppingList list) {
        var session = sessionFactory.openSession();
        try {
            var transaction = session.beginTransaction();
            session.delete(list);
            transaction.commit();
        } finally {
            session.close();
        }
    }

    @Override
    public ShoppingList updateShoppingList(ShoppingList shoppingList) {
        var session = sessionFactory.openSession();
        try {
            var transaction = session.beginTransaction();
            var updatedShoppingList = (ShoppingList)session.merge(shoppingList);
            transaction.commit();
            return updatedShoppingList;
        } finally {
            session.close();
        }
    }

    @Override
    public void persistProduct(Product product) {
        var session = sessionFactory.openSession();
        try {
            var transaction = session.beginTransaction();
            session.persist(product);
            transaction.commit();
        } finally {
            session.close();
        }
    }

    @Override
    public Collection<Product> getAllProducts() {
        var session = sessionFactory.openSession();
        try {
            return session.createQuery("FROM Product", Product.class).getResultList();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Product> getProduct(String name) {
        var session = sessionFactory.openSession();
        try {
            return Optional.ofNullable(session.get(Product.class, name));
        } finally {
            session.close();
        }
    }
}
