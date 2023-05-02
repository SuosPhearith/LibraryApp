package controllers;

public class Category {
    private String catName;
    private String catInfo;
    public String getCatName() {
        return catName;
    }
    public void setCatName(String catName) {
        this.catName = catName;
    }
    public String getCatInfo() {
        return catInfo;
    }
    public void setCatInfo(String catInfo) {
        this.catInfo = catInfo;
    }
    public Category(String catName) {
        this.catName = catName;
    }
    @Override
    public String toString(){
        return catName;
        
    }
}
