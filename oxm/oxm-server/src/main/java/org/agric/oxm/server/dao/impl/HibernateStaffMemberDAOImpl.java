package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.StaffMember;
import org.agric.oxm.server.dao.StaffMemberDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 * 
 */
@Repository("staffMemberDAO")
public class HibernateStaffMemberDAOImpl extends BaseDAOImpl<StaffMember>
		implements StaffMemberDAO {

}
