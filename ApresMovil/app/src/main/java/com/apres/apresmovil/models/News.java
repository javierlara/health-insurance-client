package com.apres.apresmovil.models;

/**
 * Created by javierlara on 11/26/16.
 */
public class News {
    public final String id;
    public final String title;
    public final String image;
    public final String content;
    public final Integer author_id;

    public News(String id, String title, String image, String content, Integer author_id) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.content = content;
        this.author_id = author_id;
    }
}
