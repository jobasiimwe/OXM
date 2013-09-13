package org.agric.oxm.server.service.impl;

import java.util.List;

import org.agric.oxm.model.ProductionLevel;
import org.agric.oxm.model.enums.RecordStatus;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.dao.ProductionLevelDAO;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.ProductionLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("productionLevelService")
@Transactional
public class ProductionLevelServiceImpl implements ProductionLevelService {

	@Autowired
	private ProductionLevelDAO productionLevelDAO;

	@Secured({ PermissionConstants.ADD_PROD_LEVEL, PermissionConstants.EDIT_PROD_LEVEL })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void save(ProductionLevel productionLevel) {
		productionLevelDAO.save(productionLevel);
	}

	@Secured({ PermissionConstants.VIEW_PROD_LEVEL })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public void validate(ProductionLevel productionLevel) throws ValidationException {
	    if(productionLevel.getCrop() == null)
		throw new ValidationException("Suppplied production level missing crop");
	    if(productionLevel.getProducer() == null)
		throw new ValidationException("Supplied production level missing producer");
	    
	    if(productionLevel.getSeason() == null)
		throw new ValidationException("Supplied production level missing season");
	    
	    if(productionLevel.getAcrage() < 0)
		throw new ValidationException("Supplied production level acrage is invalid");
	    if(productionLevel.getActualAmount() < 0)
		throw new ValidationException("Supplied production level actual ammont is invalid");
	    if(productionLevel.getProjectedAmount() < 0 )
		throw new ValidationException("Invalid Projected Amount");
	}

	@Secured({ PermissionConstants.VIEW_PROD_LEVEL })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<ProductionLevel> getProductionLevels() {
		return productionLevelDAO.searchByRecordStatus(RecordStatus.ACTIVE);
	}

	@Secured({ PermissionConstants.VIEW_PROD_LEVEL })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public ProductionLevel getProductionLevelById(String id) {
		return productionLevelDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Secured({ PermissionConstants.DELETE_PROD_LEVEL })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deleteProductionLevelsByIds(String[] ids) {
		productionLevelDAO.removeByIds(ids);
	}

}
