/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.agric.oxm.server.dao.hibernameimpl;

import org.agric.oxm.model.User;
import org.agric.oxm.server.dao.UserDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 */
@Repository("UserDAO")
public class HibernateUserDAOImpl extends BaseDAOImpl<User> implements UserDAO {

}
