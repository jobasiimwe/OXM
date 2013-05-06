package org.agric.oxm.server.service.impl;

import java.util.List;

import org.agric.oxm.model.ProducerOrganisation;
import org.agric.oxm.model.RecordStatus;
import org.agric.oxm.model.StaffMember;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.ProducerOrgSearchParameters;
import org.agric.oxm.server.dao.ProducerOrgDAO;
import org.agric.oxm.server.dao.StaffMemberDAO;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.ProducerOrgService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

/**
 * 
 * @author Job
 * 
 */
@Service("producerOrgService")
@Transactional
public class ProducerOrgServiceImpl implements ProducerOrgService {
	@Autowired
	private ProducerOrgDAO producerOrgDAO;
	@Autowired
	private StaffMemberDAO staffMemberDAO;

	@Secured({ PermissionConstants.ADD_PROD_ORG,
			PermissionConstants.EDIT_PROD_ORG })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void save(ProducerOrganisation productionOrganisation) {
		producerOrgDAO.save(productionOrganisation);
	}

	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public void validate(ProducerOrganisation productionOrganisation)
			throws ValidationException {
		if (StringUtils.isEmpty(productionOrganisation.getName()))
			throw new ValidationException(
					"Supplied Production Org missing name");

		if (productionOrganisation.getDistrict() == null)
			throw new ValidationException(
					"Supplid Production Org missing district");

		if (productionOrganisation.getSubCounty() == null)
			throw new ValidationException(
					"Supplied Production Org missing subCountry");
	}

	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<ProducerOrganisation> getProducerOrganisations() {
		Search search = new Search();
		search.addSort("name", false, true);
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		return producerOrgDAO.search(search);
	}

	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<ProducerOrganisation> getProducerOrgsWithParams(
			ProducerOrgSearchParameters params) {
		Search search = new Search();

		if (StringUtils.isNotEmpty(params.getName())) {
			Filter nameFilter = new Filter("name",
					"%" + params.getName() + "%", Filter.OP_ILIKE);

			search.addFilterOr(nameFilter);
		}

		search.addSort("name", false, true);
		return producerOrgDAO.search(search);
	}

	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public ProducerOrganisation getProducerOrganisationById(String id) {
		return producerOrgDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Secured({ PermissionConstants.DELETE_PROD_ORG })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deleteProducerOrganisationsByIds(String[] ids) {
		producerOrgDAO.removeByIds(ids);
	}

	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public void validate(StaffMember staffMember) throws ValidationException {
		if (staffMember.getAppointmentDate() == null)
			throw new ValidationException("Appointment Date can not be null");

		if (staffMember.getPosition() == null)
			throw new ValidationException("Position can not be null");

		if (staffMember.getProducerOrg() == null)
			throw new ValidationException(
					"Production Organisation can not be null");
	}

	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public StaffMember getStaffMemberById(String id) {
		return staffMemberDAO.searchUniqueByPropertyEqual("id", id);
	}
}
