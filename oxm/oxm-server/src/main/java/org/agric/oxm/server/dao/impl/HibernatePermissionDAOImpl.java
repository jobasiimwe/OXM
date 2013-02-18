package org.agric.oxm.server.dao.impl;


import org.agric.oxm.model.Permission;
import org.agric.oxm.server.dao.PermissionDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 *
 */
@Repository("PermissionDAO")
public class HibernatePermissionDAOImpl extends BaseDAOImpl<Permission>
		implements PermissionDAO {

}
