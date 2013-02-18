package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.Role;
import org.agric.oxm.server.dao.RoleDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 * 
 */
@Repository("RoleDAO")
public class HibernateRoleDAOImpl extends BaseDAOImpl<Role> implements RoleDAO {

}
