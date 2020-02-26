package com.ait.callData;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PMData implements Serializable{

	private static final long serialVersionUID = 1L;

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
