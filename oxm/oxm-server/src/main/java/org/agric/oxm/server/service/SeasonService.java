package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.Season;
import org.agric.oxm.model.exception.ValidationException;

public interface SeasonService {
    
    void save(Season season);

	void validate(Season season) throws ValidationException;

	List<Season> getSeasons();

	Season getSeasonById(String id);

	void deleteSeasonByIds(String[] ids);
}
