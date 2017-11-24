package com.example.wudelin.cstudy.recyclerviewadapter;

/**
 * Created by wudelin on 2017/11/24.
 */

public class Section {
    private int sectionImageId;
    private String sectionName;

    public Section(int sectionImageId, String sectionName) {
        this.sectionImageId = sectionImageId;
        this.sectionName = sectionName;
    }

    public int getSectionImageId() {
        return sectionImageId;
    }

    public void setSectionImageId(int sectionImageId) {
        this.sectionImageId = sectionImageId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
