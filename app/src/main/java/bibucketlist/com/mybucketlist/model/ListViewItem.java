package bibucketlist.com.mybucketlist.model;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

/**
 * Created by pineone on 2017-04-27.
 */

public class ListViewItem{// extends RealmObject {

    /*@PrimaryKey
    private int num;*/

    private Drawable iconDrawable;
    private String imgURL;

    @NonNull
    private String titleStr;

    private String regStr;

    @NonNull
    private String categories;
    private String price = "";

    //public void setNum(int num) { this.num = num; }
    public void setIcon(Drawable icon) {
        iconDrawable = icon;
    }
    public void setTitle(String title) {
        titleStr = title;
    }
    public void setRegDate(String regDate) {
        regStr = regDate;
    }
    public void setCategories(String categories) { this.categories = categories; }

    //public int getNum() { return this.num; }
    public Drawable getIcon() {
        return this.iconDrawable;
    }
    public String getTitle() {
        return this.titleStr;
    }
    public String getRegDate() { return this.regStr; }
    public String getCategories() { return this.categories; }

}