package com.amazon.ask.recomo.domain;

public class movie extends Object{
     public String movieName;
     public String type;
     public String description;
     public String actor;
     public String actress;
     public Integer rank;
     public String music;

    @Override
    public String toString() {
        return "movie{" +
                "movieName='" + movieName + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", actor='" + actor + '\'' +
                ", actress='" + actress + '\'' +
                ", rank=" + rank +
                ", music='" + music + '\'' +
                '}';
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getActress() {
        return actress;
    }

    public void setActress(String actress) {
        this.actress = actress;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }
}
