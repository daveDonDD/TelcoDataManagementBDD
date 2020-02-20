package com.ait.callData;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PMData {

    private String element;

    
    public PMData(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }
}
