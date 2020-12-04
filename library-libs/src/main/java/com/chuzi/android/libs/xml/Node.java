package com.chuzi.android.libs.xml;

import java.util.ArrayList;

class Node {

    static class Attribute {
        String mKey;
        String mValue;

        Attribute(String key, String value) {
            mKey = key;
            mValue = value;
        }
    }

    private String mName;
    private String mPath;
    private String mContent;
    private ArrayList<Attribute> mAttributes = new ArrayList<>();
    private ArrayList<Node> mChildren = new ArrayList<>();

    /* package */ Node(String name, String path) {
        mName = name;
        mPath = path;
    }

    /* package */ void addAttribute(String key, String value) {
        mAttributes.add(new Attribute(key, value));
    }

    /* package */ void setContent(String content) {
        mContent = content;
    }

    /* package */ void setName(String name) {
        mName = name;
    }

    /* package */ void addChild(Node child) {
        mChildren.add(child);
    }

    /* package */ ArrayList<Attribute> getAttributes() {
        return mAttributes;
    }

    /* package */ String getContent() {
        return mContent;
    }

    /* package */ ArrayList<Node> getChildren() {
        return mChildren;
    }

    /* package */ String getPath() {
        return mPath;
    }

    /* package */ String getName() {
        return mName;
    }

}
