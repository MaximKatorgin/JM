package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        Session session = Util.getSession();
        session.beginTransaction();
        session.createSQLQuery("create table if not exists `User` (" +
                "`id` bigint(20) not null auto_increment, " +
                "`name` varchar(45) null, " +
                "`lastName` varchar(100) null, " +
                "`age` tinyint(3) null, " +
                "primary key (`id`))").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSession();
        session.beginTransaction();
        session.createSQLQuery("drop table if exists User").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSession();
        session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSession();
        session.beginTransaction();
        User user = (User) session.get(User.class, id);
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSession();
        session.beginTransaction();
        List<User> userList = session.createQuery("FROM User").list();
        session.getTransaction().commit();
        session.close();
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSession();
        session.beginTransaction();
        session.createSQLQuery("truncate table User").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
