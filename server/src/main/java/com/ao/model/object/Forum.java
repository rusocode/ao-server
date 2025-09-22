package com.ao.model.object;

import com.ao.model.object.properties.ForumProperties;

public class Forum extends AbstractObject {

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
