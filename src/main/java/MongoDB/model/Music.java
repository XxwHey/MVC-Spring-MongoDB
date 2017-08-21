package MongoDB.model;

import MongoDB.common.utils.MongoUtils.autoKey.AutoKey;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by xiexw on 2017/8/8.
 */

@Document(collection = "Music")
public class Music {

    @Id
    @AutoKey
    private Integer id;

    @Field
    private String name;

    @Field
    private String artist;

    @Field
    private String album;

    @Field
    private String description;

    @Field
    private Integer album_id;

    public Music() {

    }

    public Music(Integer id, String name, String artist, String album, String description, Integer album_id) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.description = description;
        this.album_id = album_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(Integer album_id) {
        this.album_id = album_id;
    }

}
