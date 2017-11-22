package com.tsi2.streamrainadmin.security.user;

public class User {
    private String username;
    private String password;
    private boolean twitter;
    private String twitterID;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public boolean isTwitter() {
		return twitter;
	}

	public void setTwitter(boolean twitter) {
		this.twitter = twitter;
	}

	public String getTwitterID() {
		return twitterID;
	}

	public void setTwitterID(String twitterID) {
		this.twitterID = twitterID;
	}
	
	
    
}
