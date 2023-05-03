package controllers;

public class Category {
    private String categoryId;
    private String catName;
    public Category(String catName) {
        this.catName = catName;
    }
    private String catInfo;
    public Category(String categoryId, String catName, String catInfo) {
        this.categoryId = categoryId;
        this.catName = catName;
        this.catInfo = catInfo;
    }
    public String getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
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
    @Override
    public String toString(){
        return catName;
        
    }
}
