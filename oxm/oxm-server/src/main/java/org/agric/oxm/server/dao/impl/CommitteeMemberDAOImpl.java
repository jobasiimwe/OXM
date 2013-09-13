package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.CommitteeMember;
import org.agric.oxm.server.dao.CommitteeMemberDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 * 
 */
@Repository("committeeMemberDAO")
public class CommitteeMemberDAOImpl extends
		BaseDAOImpl<CommitteeMember> implements CommitteeMemberDAO {

}
