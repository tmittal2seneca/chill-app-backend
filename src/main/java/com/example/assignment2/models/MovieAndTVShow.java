package com.example.assignment2.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("movie-and-tv-shows")
public class MovieAndTVShow {
    @Id
    private  String id;
    private  String name;
    private  String price;
    private String synopsis;
    private boolean isMovie; //if true, it is a movie. If false it will be a TV show
    private String smallPosterImgPath;
    private String largePosterImgPath;
    private String rentPrice;
    private String outrightPurchasePrice;
    private boolean isFeatured;

    public MovieAndTVShow() {
    }

    public MovieAndTVShow(String id, String name, String price, String synopsis, boolean isMovie, String smallPosterImgPath, String largePosterImgPath, String rentPrice, String outrightPurchasePrice, boolean isFeatured) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.synopsis = synopsis;
        this.isMovie = isMovie;
        this.smallPosterImgPath = smallPosterImgPath;
        this.largePosterImgPath = largePosterImgPath;
        this.rentPrice = rentPrice;
        this.outrightPurchasePrice = outrightPurchasePrice;
        this.isFeatured = isFeatured;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public boolean isMovie() {
        return isMovie;
    }

    public void setMovie(boolean movie) {
        isMovie = movie;
    }

    public String getSmallPosterImgPath() {
        return smallPosterImgPath;
    }

    public void setSmallPosterImgPath(String smallPosterImgPath) {
        this.smallPosterImgPath = smallPosterImgPath;
    }

    public String getLargePosterImgPath() {
        return largePosterImgPath;
    }

    public void setLargePosterImgPath(String largePosterImgPath) {
        this.largePosterImgPath = largePosterImgPath;
    }

    public String getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(String rentPrice) {
        this.rentPrice = rentPrice;
    }

    public String getOutrightPurchasePrice() {
        return outrightPurchasePrice;
    }

    public void setOutrightPurchasePrice(String outrightPurchasePrice) {
        this.outrightPurchasePrice = outrightPurchasePrice;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    @Override
    public String toString() {
        return "MovieAndTVShow{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", isMovie=" + isMovie +
                ", smallPosterImgPath='" + smallPosterImgPath + '\'' +
                ", largePosterImgPath='" + largePosterImgPath + '\'' +
                ", rentPrice='" + rentPrice + '\'' +
                ", outrightPurchasePrice='" + outrightPurchasePrice + '\'' +
                ", isFeatured=" + isFeatured +
                '}';
    }
}
