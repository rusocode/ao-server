package com.ao.service.worldobject;

import com.ao.data.dao.ObjectDAO;
import com.ao.data.dao.exception.DAOException;
import com.ao.model.worldobject.Object;
import com.ao.model.worldobject.factory.ObjectFactory;
import com.ao.model.worldobject.factory.ObjectFactoryException;
import com.ao.service.ObjectService;
import com.google.inject.Inject;

/**
 * Default implementation of WorldObjectService.
 */

public class ObjectServiceImpl implements ObjectService {

    protected ObjectDAO objectDAO;
    protected ObjectFactory objectFactory;

    @Inject
    public ObjectServiceImpl(ObjectDAO objectDAO, ObjectFactory objectFactory) {
        super();
        this.objectDAO = objectDAO;
        this.objectFactory = objectFactory;
    }


    @Override
    public void loadObjects() throws DAOException {
        objectDAO.load();
    }

    @Override
    public Object createObject(int id, int amount) throws ObjectFactoryException {
        return objectFactory.getObject(objectDAO.getObjectProperties(id), amount);
    }

}
