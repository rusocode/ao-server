package com.ao.model.object.properties;

import com.ao.model.object.ObjectType;

public class ObjectProperties {

    protected int id;
    protected int graphic;
    protected String name;
    protected ObjectType type;

    public ObjectProperties(ObjectType type, int id, String name, int graphic) {
        this.id = id;
        this.name = name;
        this.graphic = graphic;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGraphic() {
        return graphic;
    }

    public ObjectType getType() {
        return type;
    }

}
