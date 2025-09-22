package com.ao.model.object;

import com.ao.model.object.properties.SignProperties;

public class Sign extends AbstractObject {

    public Sign(SignProperties properties) {
        super(properties);
    }

    public int getBigGraphic() {
        return ((SignProperties) properties).getBigGraphic();
    }

    public String getText() {
        return ((SignProperties) properties).getText();
    }

    @Override
    public boolean isFixed() {
        return true;
    }

}
