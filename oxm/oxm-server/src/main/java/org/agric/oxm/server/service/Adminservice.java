/**
 * 
 */
package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.District;
import org.agric.oxm.model.Parish;
import org.agric.oxm.model.SubCounty;
import org.agric.oxm.model.Village;
import org.agric.oxm.model.exception.ValidationException;

/**
 * 
 * interface for {@link District}, {@link SubCounty}, {@link Parish},
 * {@link Village}
 * 
 * @author Job
 * 
 */
public interface Adminservice {

	void save(District district);

	void validate(District district) throws ValidationException;

	List<District> getDistricts();

	District getDistrictById(String id);

	District getDistrictByName(String name);

	void deleteDistrictsByIds(String[] ids);

	// ---------------------

	void save(SubCounty subCounty);

	void validate(SubCounty subCounty) throws ValidationException;

	List<SubCounty> getSubCounties();

	SubCounty getSubCountyById(String id);

	void deleteSubCountiesByIds(String[] ids);

	// ------------------------

	void save(Parish parish);

	void validate(Parish parish) throws ValidationException;

	List<Parish> getParishes();

	Parish getParishById(String id);

	void deleteParishesByIds(String[] ids);

	// -------------------

	void save(Village village);

	void validate(Village village) throws ValidationException;

	List<Village> getVillages();

	Village getVillageById(String id);

	void deleteVillagesByIds(String[] ids);
}
