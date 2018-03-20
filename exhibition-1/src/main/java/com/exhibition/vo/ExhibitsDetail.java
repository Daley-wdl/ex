package com.exhibition.vo;

import com.exhibition.po.Comment;
import com.exhibition.po.Exhibits;

import java.util.List;

/**
 * 与前端展品详情页面进行交互的vo
 */
public class ExhibitsDetail {

    private Exhibits exhibits;
    private Comment comment;
    private List<String> photos;

    public ExhibitsDetail() {
    }

    public ExhibitsDetail(Exhibits exhibits, Comment comment, List<String> photos) {
        this.exhibits = exhibits;
        this.comment = comment;
        this.photos = photos;
    }

    public Exhibits getExhibits() {
        return exhibits;
    }

    public void setExhibits(Exhibits exhibits) {
        this.exhibits = exhibits;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
