package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.ForumProperties;

public class Forum extends AbstractWorldObject {

    public Forum(ForumProperties properties) {
        super(properties);
    }

    public String getForumName() {
        return ((ForumProperties) properties).getForumName();
    }

    @Override
    public boolean isFixed() {
        return true;
    }

}
