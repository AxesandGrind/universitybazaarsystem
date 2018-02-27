package com.advse.universitybazaar.bean;

/**
 * Created by shrey on 2/26/2018.
 */

public class Club {

    int clubId;
    String clubName;
    String clubDescription;
    String clubOwner;

    public Club() {

    }

    public Club(int id,String name,String description,String owner) {
        this.clubId = id;
        this.clubName = name;
        this.clubDescription = description;
        this.clubOwner = owner;
    }

    public int getClubId() {
        return clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubDescription() {
        return clubDescription;
    }

    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }
}
