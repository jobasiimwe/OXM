/**
 * 
 */
package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.ProducerOrg;
import org.agric.oxm.model.StaffMember;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.ProducerOrgSearchParameters;

/**
 * @author Job
 * 
 */
public interface ProducerOrgService {

	void save(ProducerOrg producerOrganisation);

	void validate(ProducerOrg producerOrganisation) throws ValidationException;

	List<ProducerOrg> getProducerOrganisations();

	ProducerOrg getProducerOrganisationById(String id);

	void deleteProducerOrganisationsByIds(String[] ids);

	void validate(StaffMember staffMember) throws ValidationException;

	StaffMember getStaffMemberById(String id);

	// ============================================================================

	List<ProducerOrg> searchWithParams(ProducerOrgSearchParameters params,
			Integer pageNo);

	List<ProducerOrg> searchWithParams(ProducerOrgSearchParameters params);

	long numberInSearch(ProducerOrgSearchParameters params);
}
