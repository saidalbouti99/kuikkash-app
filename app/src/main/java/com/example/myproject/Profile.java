package com.example.myproject;

public class Profile {
private String id;
    private String profilepic;
    private String gender;

    public Profile() {
    }

    public Profile(String id,String profilepic,String gender) {
        this.id=id;
        this.profilepic = profilepic;
        this.gender=gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }
}
