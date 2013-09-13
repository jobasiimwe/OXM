package org.agric.oxm.server.service.impl;

import java.util.List;

import org.agric.oxm.model.Crop;
import org.agric.oxm.model.enums.RecordStatus;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.CropSearchParameters;
import org.agric.oxm.server.OXMConstants;
import org.agric.oxm.server.dao.CropDAO;
import org.agric.oxm.server.service.CropService;
import org.agric.oxm.utils.MyValidate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@Transactional
@Service("cropService")
public class CropServiceImpl implements CropService {

	@Autowired
	private CropDAO cropDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void save(Crop crop) {
		cropDAO.save(crop);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public void validate(Crop crop) throws ValidationException {
		MyValidate.isNotBlank(crop.getName(), "Name");

		Crop cropWithSimilarName = getCropByName(crop.getName());
		if (cropWithSimilarName != null) {
			if (StringUtils.isBlank(cropWithSimilarName.getId())
					|| !cropWithSimilarName.equals(cropWithSimilarName))
				throw new ValidationException("Another crop with name - "
						+ cropWithSimilarName.getName() + " already exists");
		}
		
		if (crop.getUnitsOfMeasure() == null
				|| crop.getUnitsOfMeasure().size() == 0)
			throw new ValidationException("Units Of Measure can not be empty");
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Crop> getCrops() {
		return cropDAO.searchByRecordStatus(RecordStatus.ACTIVE);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Crop getCropById(String id) {
		return cropDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Crop getCropByName(String name) {
		return cropDAO.searchUniqueByPropertyEqual("name", name);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deleteCropsByIds(String[] ids) {
		cropDAO.removeByIds(ids);
	}

	@Override
	public List<Crop> searchWithParams(CropSearchParameters params, int pageNo) {
		Search search = prepareCropSearch(params);
		/*
		 * if the page number is less than or equal to zero, no need for paging
		 */
		if (pageNo > 0) {
			search.setPage(pageNo - 1);
		} else {
			search.setPage(0);
		}
		List<Crop> crops = cropDAO.search(search);
		return crops;
	}

	private Search prepareCropSearch(CropSearchParameters params) {
		Search search = new Search();

		if (StringUtils.isNotEmpty(params.getName())) {
			search.addFilter(new Filter("name", "%" + params.getName() + "%",
					Filter.OP_ILIKE));
		}

		if (params.getInput() != null) {
			search.addFilterEqual("input.concepts.name", params.getInput()
					.getName());
		}

		if (params.getSeedVariation() != null) {
			search.addFilterEqual("seedVariation.concepts.name", params
					.getSeedVariation().getName());
		}

		if (params.getPloughingMethod() != null) {
			search.addFilterEqual("ploughingMethod.concepts.name", params
					.getInterCropingType().getName());
		}

		if (params.getInterCropingType() != null) {
			search.addFilterEqual("interCropingType.concepts.name", params
					.getInput().getName());
		}

		search.addSort("name", false, true);

		return search;
	}

	@Override
	public int numberOfCropsWithSearchParams(CropSearchParameters params) {
		Search search = prepareCropSearch(params);
		return cropDAO.count(search);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Crop> getAnnonymouslyViewableCrops() {
		Search search = new Search();
		search.setMaxResults(OXMConstants.MAX_NUM_PRE_LOGIN_PAGE_RECORD);

		search.addSort("name", false, true);
		search.setPage(0);

		List<Crop> crops = cropDAO.search(search);
		return crops;
	}

}
