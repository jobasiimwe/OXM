package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.Season;

public interface SeasonService extends BaseService<Season> {
    
   
	List<Season> getAnnonymously();

}
