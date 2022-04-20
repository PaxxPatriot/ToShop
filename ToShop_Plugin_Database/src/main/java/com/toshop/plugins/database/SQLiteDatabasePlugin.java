package com.toshop.plugins.database;

import com.toshop.application.interfaces.DatabasePlugin;
import com.toshop.domain.entities.ShoppingList;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SQLiteDatabasePlugin implements DatabasePlugin {

    private final String filePath;

    private SessionFactory sessionFactory;

    public SQLiteDatabasePlugin(String filePath) {
        this.filePath = filePath;

        String connectionString = "jdbc:sqlite:" + filePath;
        Configuration configuration = new Configuration().configure().setProperty("hibernate.connection.url", connectionString).addResource("mappings.xml");
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(serviceRegistryBuilder.build());
    }

    @Override
    public void persist(ShoppingList list) {
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
    public Optional<ShoppingList> get(UUID shoppingListId) {
        var session = sessionFactory.openSession();
        try {
            return Optional.ofNullable(session.get(ShoppingList.class, shoppingListId));
        } finally {
            session.close();
        }
    }

    public List<ShoppingList> getAll() {
        var session = sessionFactory.openSession();
        try {
            return session.createQuery("FROM ShoppingList", ShoppingList.class).getResultList();
        } finally {
            session.close();
        }
    }
}
