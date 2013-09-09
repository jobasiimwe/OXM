package org.agric.oxm.server.dao.impl;


import org.agric.oxm.model.Permission;
import org.agric.oxm.server.dao.PermissionDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 *
 */
@Repository("permissionDAO")
public class PermissionDAOImpl extends BaseDAOImpl<Permission>
		implements PermissionDAO {

}
