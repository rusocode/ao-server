package com.ao.service.worldobject;

import com.ao.data.dao.WorldObjectPropertiesDAO;
import com.ao.data.dao.exception.DAOException;
import com.ao.model.worldobject.WorldObject;
import com.ao.model.worldobject.factory.WorldObjectFactory;
import com.ao.model.worldobject.factory.WorldObjectFactoryException;
import com.ao.service.WorldObjectService;
import com.google.inject.Inject;

/**
 * Default implementation of WorldObjectService.
 */

public class WorldObjectServiceImpl implements WorldObjectService {

    protected WorldObjectPropertiesDAO woPropertiesDao;
    protected WorldObjectFactory woFactory;

    @Inject
    public WorldObjectServiceImpl(WorldObjectPropertiesDAO woPropertiesDao, WorldObjectFactory woFactory) {
        super();
        this.woPropertiesDao = woPropertiesDao;
        this.woFactory = woFactory;
    }


    @Override
    public void loadObjects() throws DAOException {
        woPropertiesDao.load();
    }

    @Override
    public WorldObject createWorldObject(int id, int amount) throws WorldObjectFactoryException {
        return woFactory.getWorldObject(woPropertiesDao.getWorldObjectProperties(id), amount);
    }

}
