package org.agric.oxm.server.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.agric.oxm.model.RecordStatus;
import org.agric.oxm.model.Season;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.dao.SeasonDAO;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("seasonService")
@Transactional
public class SeasonServiceImpl implements SeasonService {

    @Autowired
    private SeasonDAO seasonDAO;

    @Override
    @Secured({ PermissionConstants.MANAGE_SEASONS, PermissionConstants.VIEW_SEASONS })
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Season season) {
	season.setName(returnSeasonName(season.getStartDate()) + " - "
		+ returnSeasonName(season.getEndDate()));
	seasonDAO.save(season);
    }

    private String returnSeasonName(Date date) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	int month = cal.get(Calendar.MONTH);
	int year = cal.get(Calendar.YEAR);

	switch (month) {
	case 0:
	    return "Jan' " + year;
	case 1:
	    return "Feb' " + year;
	case 2:
	    return "Mar' " + year;
	case 3:
	    return "Apr' " + year;
	case 4:
	    return "May' " + year;
	case 5:
	    return "Jun' " + year;
	case 6:
	    return "Jul' " + year;
	case 7:
	    return "Aug' " + year;
	case 8:
	    return "Sep' " + year;
	case 9:
	    return "Oct' " + year;
	case 10:
	    return "Nov' " + year;
	case 11:
	    return "Dec' " + year;
	default:
	    return "Jan' " + year;
	}
    }

    @Override
    public void validate(Season season) throws ValidationException {
	if (season.getStartDate() == null)
	    throw new ValidationException("Supplied season missing start date");
	if (season.getEndDate() == null)
	    throw new ValidationException("Supplied season missing end date");
	if (season.getEndDate().before(season.getStartDate()))
	    throw new ValidationException("End date cant be before the start date");
    }

    @Override
    @Secured({ PermissionConstants.MANAGE_SEASONS, PermissionConstants.VIEW_SEASONS })
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Season> getSeasons() {
	return seasonDAO.searchByRecordStatus(RecordStatus.ACTIVE);
    }

    @Override
    @Secured({ PermissionConstants.MANAGE_SEASONS, PermissionConstants.VIEW_SEASONS })
    @Transactional(propagation = Propagation.REQUIRED)
    public Season getSeasonById(String id) {
	return seasonDAO.searchUniqueByPropertyEqual("id", id);
    }

    @Override
    @Secured({ PermissionConstants.MANAGE_SEASONS, PermissionConstants.VIEW_SEASONS })
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteSeasonByIds(String[] ids) {
	seasonDAO.removeByIds(ids);
    }

}
