package org.agric.oxm.server.service.impl;

import java.util.List;

import org.agric.oxm.model.ProducerOrg;
import org.agric.oxm.model.StaffMember;
import org.agric.oxm.model.enums.RecordStatus;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.ProducerOrgSearchParameters;
import org.agric.oxm.server.OXMConstants;
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
	public void save(ProducerOrg productionOrganisation) {
		producerOrgDAO.save(productionOrganisation);
	}

	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public void validate(ProducerOrg pOrg) throws ValidationException {
		if (StringUtils.isEmpty(pOrg.getName()))
			throw new ValidationException("Name can not be blank");

		if (pOrg.getDistrict() == null)
			throw new ValidationException("District can not be blank");

		if (pOrg.getCounty() == null)
			throw new ValidationException("Country can not be blank");

		if (pOrg.getSubCounty() == null)
			throw new ValidationException("SubCountry can not be blank");

		if (pOrg.getParish() == null)
			throw new ValidationException("Parish can not be blank");

		if (pOrg.getVillage() == null)
			throw new ValidationException("Village can not be blank");
	}

	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<ProducerOrg> getProducerOrganisations() {
		Search search = new Search();
		search.addSort("name", false, true);
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		return producerOrgDAO.search(search);
	}

	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public ProducerOrg getProducerOrganisationById(String id) {
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

	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<ProducerOrg> searchWithParams(
			ProducerOrgSearchParameters params, Integer pageNo) {
		Search search = preparePOrgSearch(params);

		search.setMaxResults(OXMConstants.MAX_NUM_PAGE_RECORD);

		/*
		 * if the page number is less than or equal to zero, no need for paging
		 */
		if (pageNo > 0) {
			search.setPage(pageNo - 1);
		} else {
			search.setPage(0);
		}

		return producerOrgDAO.search(search);
	}

	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<ProducerOrg> searchWithParams(ProducerOrgSearchParameters params) {
		Search search = preparePOrgSearch(params);
		return producerOrgDAO.search(search);
	}

	private Search preparePOrgSearch(ProducerOrgSearchParameters params) {
		Search search = new Search();

		search.addSort("name", false, true);
		if (StringUtils.isNotBlank(params.getName())) {
			search.addFilterILike("name", "%" + params.getName() + "%");
		}

		if (params.getDistrict() != null) {
			search.addFilterEqual("district", params.getDistrict());
		}
		if (params.getCounty() != null) {
			search.addFilterEqual("county", params.getCounty());
		}
		if (params.getSubCounty() != null) {
			search.addFilterEqual("subCounty", params.getSubCounty());
		}
		if (params.getParish() != null) {
			search.addFilterEqual("parish", params.getParish());
		}
		if (params.getVillage() != null) {
			search.addFilterEqual("village", params.getVillage());
		}
		if (params.getVillage() != null) {
			search.addFilterEqual("village", params.getVillage());
		}
		return search;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public long numberInSearch(ProducerOrgSearchParameters params) {
		Search search = preparePOrgSearch(params);
		return producerOrgDAO.count(search);
	}

}
