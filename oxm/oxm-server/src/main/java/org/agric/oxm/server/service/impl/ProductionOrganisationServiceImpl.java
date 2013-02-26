package org.agric.oxm.server.service.impl;

import java.util.List;

import org.agric.oxm.model.ProductionOrganisation;
import org.agric.oxm.model.RecordStatus;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.ProductionOrganisationSearchParameters;
import org.agric.oxm.server.dao.ProductionOrganisationDAO;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.ProductionOrganisationService;
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
@Service("productionOrganisationService")
@Transactional
public class ProductionOrganisationServiceImpl implements
		ProductionOrganisationService {
	@Autowired
	private ProductionOrganisationDAO productionOrganisationDAO;

	@Secured({ PermissionConstants.ADD_PROD_ORG, PermissionConstants.EDIT_PROD_ORG })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void save(ProductionOrganisation productionOrganisation) {
		productionOrganisationDAO.save(productionOrganisation);
	}

	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public void validate(ProductionOrganisation productionOrganisation)
			throws ValidationException {

	}

	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<ProductionOrganisation> getProductionOrganisations() {
		return productionOrganisationDAO
				.searchByRecordStatus(RecordStatus.ACTIVE);
	}

	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<ProductionOrganisation> getProductionOrganisationsWithParams(
			ProductionOrganisationSearchParameters params) {
		Search search = new Search();

		if (StringUtils.isNotEmpty(params.getName())) {
			Filter nameFilter = new Filter("name",
					"%" + params.getName() + "%", Filter.OP_ILIKE);

			search.addFilterOr(nameFilter);
		}

		search.addSort("name", false, true);
		return productionOrganisationDAO.search(search);
	}

	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public ProductionOrganisation getProductionOrganisationById(String id) {
		return productionOrganisationDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Secured({ PermissionConstants.DELETE_PROD_ORG })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deleteProductionOrganisationsByIds(String[] ids) {
		productionOrganisationDAO.removeByIds(ids);
	}
}
