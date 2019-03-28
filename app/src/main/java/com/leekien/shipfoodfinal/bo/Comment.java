package com.leekien.shipfoodfinal.bo;

public class Comment {
    private Integer id;

    private int feed_id;

    public Comment(int feed_id, int creator_id, String comment) {
        this.feed_id = feed_id;
        this.creator_id = creator_id;
        this.comment = comment;
    }

    public int getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(int creator_id) {
        this.creator_id = creator_id;
    }

    private int creator_id;

    private String comment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(int feed_id) {
        this.feed_id = feed_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
