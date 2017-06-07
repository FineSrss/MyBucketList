package bibucketlist.com.mybucketlist.model;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * Created by pineone on 2017-06-02.
 */
@RealmClass
public class Bucket implements RealmModel{

    @PrimaryKey
    @Required
    private Integer bucketNum;          //버킷 번호

    private int category;           //0 : 하고싶은것    1 : 사고싶은 것     2 : 따고싶은 것
    private String bucketTitle;
    private byte[] bucketImage;     //버킷 이미지
    private int bucketPrice;        //사고싶은 버킷리스트 가격
    private String regDate;         //버킷 등록 날짜

    //Getter
    public int getBucketNum() { return bucketNum; }
    public int getCategory() { return category; }
    public String getBucketTitle() { return bucketTitle; }
    public byte[] getBucketImage() { return bucketImage; }
    public int getBucketPrice() { return bucketPrice; }
    public String getRegDate() { return regDate; }

    //Setter
    public void setBucketNum(int bucketNum) { this.bucketNum = bucketNum; }
    public void setCategory(int category) { this.category = category; }
    public void setBucketTitle(String bucketName) { this.bucketTitle = bucketName; }
    public void setBucketImage(byte[] bucketImage) { this.bucketImage = bucketImage; }
    public void setBucketPrice(int bucketPrice) { this.bucketPrice = bucketPrice; }
    public void setRegDate(String regDate) { this.regDate = regDate; }
}
