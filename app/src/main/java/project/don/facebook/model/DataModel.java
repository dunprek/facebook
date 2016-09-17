package project.don.facebook.model;

/**
 * Created by don on 9/17/2016.
 */
public class DataModel {
    String albumName ;
    int photoCount;

    public DataModel(){

    }
    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
    }
}
