/**
 * 
 */
package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.ProducerOrganisation;
import org.agric.oxm.model.StaffMember;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.ProducerOrgSearchParameters;

/**
 * @author Job
 * 
 */
public interface ProducerOrgService {

	void save(ProducerOrganisation producerOrganisation);

	void validate(ProducerOrganisation producerOrganisation) throws ValidationException;

	List<ProducerOrganisation> getProducerOrganisations();

	List<ProducerOrganisation> getProducerOrgsWithParams(ProducerOrgSearchParameters params);

	ProducerOrganisation getProducerOrganisationById(String id);

	void deleteProducerOrganisationsByIds(String[] ids);
	
	void validate(StaffMember staffMember) throws ValidationException;
	
	StaffMember getStaffMemberById(String id);

}
