package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.ForumProperties;

public class Forum extends AbstractWorldObject {

    /**
     * Creates a new Sign instance.
     *
     * @param properties object's properties
     */
    public Forum(ForumProperties properties) {
        super(properties);
    }

    /**
     * Retrieves the forum's name.
     *
     * @return the forum's name
     */
    public String getForumName() {
        return ((ForumProperties) properties).getForumName();
    }

    @Override
    public boolean isFixed() {
        return true;
    }

}
