package org.agric.oxm.server.dao;

import org.agric.oxm.model.User;



/**
 * A Data Access interface that provide methods for performing CRUD operations
 * on the {@link User} objects <br/>
 * <br/>
 * 
 * The UserDAO interface can be implemented by any class that would like to
 * provide CRUD operations on the {@link User} object. Some implementation of
 * this interface could be using different ORM strategies like Hibernate,
 * IBatis. So one can have a class using Hibernate and implementing the UserDAO
 * interface
 * 
 * @author ctumwebaze
 * 
 */
public interface UserDAO extends BaseDAO<User> {

}
