package org.agric.oxm.server.dao.hibernameimpl;


import org.agric.oxm.model.Permission;
import org.agric.oxm.server.dao.PermissionDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 *
 */
@Repository("permissionDAO")
public class HibernatePermissionDAOImpl extends BaseDAOImpl<Permission>
		implements PermissionDAO {

}
