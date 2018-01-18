package com.dragon.util;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PageIndex implements Serializable {

    private long startindex;
    private long endindex;

    public PageIndex() {
    }

    public PageIndex(long startindex, long endindex) {
        this.startindex = startindex;
        this.endindex = endindex;
    }

    public long getStartindex() {
        return startindex;
    }

    public void setStartindex(long startindex) {
        this.startindex = startindex;
    }

    public long getEndindex() {
        return endindex;
    }

    public void setEndindex(long endindex) {
        this.endindex = endindex;
    }

}
