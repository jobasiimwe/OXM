/**
 * 
 */
package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.Committee;
import org.agric.oxm.model.CommitteeMember;
import org.agric.oxm.model.exception.ValidationException;

/**
 * @author Job
 * 
 */
public interface CommitteeService {

	void save(Committee committee);

	void validate(Committee committee) throws ValidationException;

	List<Committee> getCommittees();

	Committee getCommitteeById(String id);

	Committee getCommitteeByName(String name);

	void deleteCommitteesByIds(String[] ids);

	CommitteeMember getCommitteeMemberById(String id);

}
