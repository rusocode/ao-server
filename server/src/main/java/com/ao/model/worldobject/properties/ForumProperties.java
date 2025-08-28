package com.ao.model.worldobject.properties;

import com.ao.model.worldobject.WorldObjectType;

/**
 * Defines a Teleport's properties. Allows a lightweight pattern implementation.
 */

public class ForumProperties extends WorldObjectProperties {

    protected String forumName;

    /**
     * Creates a new ForumProperties instance.
     *
     * @param type    type of the item
     * @param id      id of the item
     * @param name    name of the item
     * @param graphic graphic for the item
     */
    public ForumProperties(WorldObjectType type, int id, String name, int graphic, String forumName) {
        super(type, id, name, graphic);
        this.forumName = forumName;
    }

    public String getForumName() {
        return forumName;
    }

}
