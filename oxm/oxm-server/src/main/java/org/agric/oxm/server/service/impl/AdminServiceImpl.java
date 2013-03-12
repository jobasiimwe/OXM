package org.agric.oxm.server.service.impl;

import java.util.List;

import org.agric.oxm.model.District;
import org.agric.oxm.model.Parish;
import org.agric.oxm.model.RecordStatus;
import org.agric.oxm.model.SubCounty;
import org.agric.oxm.model.Village;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.dao.DistrictDAO;
import org.agric.oxm.server.dao.ParishDAO;
import org.agric.oxm.server.dao.SubCountyDAO;
import org.agric.oxm.server.dao.VillageDAO;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.Adminservice;
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
@Service("adminService")
@Transactional
public class AdminServiceImpl implements Adminservice {

    @Autowired
    private DistrictDAO districtDAO;

    @Autowired
    private SubCountyDAO subCountyDAO;

    @Autowired
    private ParishDAO parishDAO;

    @Autowired
    private VillageDAO villageDAO;

    @Secured({ PermissionConstants.ADD_DISTRICT_DETAILS,
			PermissionConstants.EDIT_DISTRICT_DETAILS })
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void save(District district) throws ValidationException {
	districtDAO.save(district);
    }

    @Secured({ PermissionConstants.VIEW_DISTRICT_DETAILS })
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public void validate(District district) throws ValidationException {
	if (StringUtils.isBlank(district.getName()))
	    throw new ValidationException("Name can not be blank");

	District districtWithSameName = getDistrictByName(district.getName());
	if (districtWithSameName != null) {
	    if (StringUtils.isEmpty(district.getId())
					|| (StringUtils.isNotEmpty(district.getId()) && !district
							.equals(districtWithSameName)))
		throw new ValidationException("A district with the same name "
						+ district.getName() + " already exists");

	}
    }

    @Secured({ PermissionConstants.VIEW_DISTRICT_DETAILS })
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<District> getDistricts() {
	return districtDAO.searchByRecordStatus(RecordStatus.ACTIVE);
    }

    @Secured({ PermissionConstants.VIEW_DISTRICT_DETAILS })
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public District getDistrictById(String id) {
	return districtDAO.searchUniqueByPropertyEqual("id", id);
    }

    @Secured({ PermissionConstants.VIEW_DISTRICT_DETAILS })
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public District getDistrictByName(String name) {
	return districtDAO.searchUniqueByPropertyEqual("name", name);
    }

    @Secured({ PermissionConstants.DELETE_DISTRICT_DETAILS })
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteDistrict(String id) {
	District district = getDistrictById(id);
	districtDAO.delete(district);
    }

    // ---------------------------------

    @Secured({ PermissionConstants.ADD_DISTRICT_DETAILS,
			PermissionConstants.EDIT_DISTRICT_DETAILS })
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void save(SubCounty subCounty) throws ValidationException {
	subCountyDAO.save(subCounty);
    }

    @Secured({ PermissionConstants.VIEW_DISTRICT_DETAILS })
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public void validate(SubCounty subCounty) throws ValidationException {
	if (StringUtils.isBlank(subCounty.getName()))
	    throw new ValidationException("Name can not be blank");

	District district = getDistrictById(subCounty.getDistrict().getId());

	for (SubCounty s : district.getSubCounties()) {
	    if (s.getName().equalsIgnoreCase(subCounty.getName())) {
		if (StringUtils.isEmpty(subCounty.getId())
						|| (StringUtils.isNotEmpty(subCounty.getId()) && !subCounty
								.equals(s)))
		    throw new ValidationException("Sub-county "
							+ subCounty.getName()
							+ " already saved in district "
							+ district.getName());
	    }
	}
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<SubCounty> getSubCounties() {
	return subCountyDAO.searchByRecordStatus(RecordStatus.ACTIVE);
    }

    @Transactional(readOnly = true)
    @Override
    public SubCounty getSubCountyById(String id) {
	return subCountyDAO.searchUniqueByPropertyEqual("id", id);
    }

    @Secured({ PermissionConstants.DELETE_DISTRICT_DETAILS })
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteSubCounty(String id) {
	subCountyDAO.removeById(id);
    }

    @Secured({ PermissionConstants.ADD_DISTRICT_DETAILS,
			PermissionConstants.EDIT_DISTRICT_DETAILS })
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void save(Parish parish) throws ValidationException {
	parishDAO.save(parish);
    }

    @Secured({ PermissionConstants.VIEW_DISTRICT_DETAILS })
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public void validate(Parish parish) throws ValidationException {
	if (StringUtils.isBlank(parish.getName()))
	    throw new ValidationException("Name can not be blank");

	SubCounty subCounty = getSubCountyById(parish.getSubCounty().getId());

	for (Parish p : subCounty.getParishes()) {
	    if (p.getName().equalsIgnoreCase(parish.getName())) {
		if (StringUtils.isEmpty(parish.getId())
						|| (StringUtils.isNotEmpty(parish.getId()) && !parish
								.equals(p)))
		    throw new ValidationException("Parish " + parish.getName()
							+ " already saved in sub-county "
							+ subCounty.getName());
	    }
	}
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Parish> getParishes() {
	return parishDAO.searchByRecordStatus(RecordStatus.ACTIVE);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Parish getParishById(String id) {
	return parishDAO.searchUniqueByPropertyEqual("id", id);
    }

    @Secured({ PermissionConstants.DELETE_DISTRICT_DETAILS })
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteParish(String id) {
	parishDAO.removeById(id);
    }

    // -----------------------

    @Secured({ PermissionConstants.ADD_DISTRICT_DETAILS,
			PermissionConstants.EDIT_DISTRICT_DETAILS })
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void save(Village village) throws ValidationException {
	villageDAO.save(village);
    }

    @Secured({ PermissionConstants.VIEW_DISTRICT_DETAILS })
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public void validate(Village village) throws ValidationException {
	if (StringUtils.isBlank(village.getName()))
	    throw new ValidationException("Name can not be blank");

	Parish parish = getParishById(village.getParish().getId());

	for (Village v : parish.getVillages()) {
	    if (v.getName().equalsIgnoreCase(village.getName())) {
		if (StringUtils.isEmpty(village.getId())
						|| (StringUtils.isNotEmpty(village.getId()) && !village
								.equals(v)))
		    throw new ValidationException("Village "
							+ village.getName()
			    + " already saved in parish "
							+ parish.getName());
	    }
	}

    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Village> getVillages() {
	return villageDAO.searchByRecordStatus(RecordStatus.ACTIVE);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Village getVillageById(String id) {
	return villageDAO.searchUniqueByPropertyEqual("id", id);
    }

    @Secured({ PermissionConstants.DELETE_DISTRICT_DETAILS })
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteVillage(String id) {
	villageDAO.removeById(id);
    }

    @Override
    public List<District> searchDistrict(String query) {
	Search search = new Search();
	search.addFilterLike("name", query);
	return districtDAO.search(search);
    }
}
