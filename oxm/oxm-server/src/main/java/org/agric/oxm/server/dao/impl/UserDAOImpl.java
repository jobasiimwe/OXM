/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.User;
import org.agric.oxm.server.dao.UserDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 */
@Repository("userDAO")
public class UserDAOImpl extends BaseDAOImpl<User> implements UserDAO {

}
