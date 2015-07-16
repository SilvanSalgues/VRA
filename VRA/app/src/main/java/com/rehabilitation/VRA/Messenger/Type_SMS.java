package com.rehabilitation.VRA.Messenger;

import java.util.Date;

/**
 * Represents a Message
 */
public class Type_SMS {

	public Type_SMS() {
	}

	@com.google.gson.annotations.SerializedName("id")
	private String id;

	@com.google.gson.annotations.SerializedName("username")
	private String username;

	@com.google.gson.annotations.SerializedName("text")
	private String text;

	@com.google.gson.annotations.SerializedName("complete")
	private boolean complete;

	@com.google.gson.annotations.SerializedName("__createdAt")
	private Date createdAt;

	@com.google.gson.annotations.SerializedName("__version")
	private String version;

	@com.google.gson.annotations.SerializedName("attachedId")
	private String attachedId;



	@Override
	public String toString() {
		return getText();
	}

	public String getId() {
		return id;
	}
	public final void setId(String id) {
		this.id = id;
	}

	public String getUsername()
	{
		return username;
	}
	public final void setUsername (String user) {
		username = user;
	}

	public String getText() {
		return text;
	}
	public final void setText(String text) {
		this.text = text;
	}

	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public Date getCreatedAt() {return createdAt;}

    public String getTimeSince(){

		String TimeSince;

		Date date = new Date();
		long diff = date.getTime() - createdAt.getTime();

		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000);
		long diffDays = diff / (60 * 60 * 1000 * 24);

		if (diffDays != 0) {
			if (diffDays == 1) {
				TimeSince = diffDays + " Day ago";
			} else
				TimeSince = diffDays + " Days ago";
		} else if (diffHours != 0) {
			if (diffHours == 1) {
				TimeSince = diffHours + " Hour ago";
			} else
				TimeSince = diffHours + " Hours ago";
		} else if (diffMinutes != 0) {
			if (diffMinutes == 1) {
				TimeSince = diffMinutes + " Minute ago";
			} else
				TimeSince = diffMinutes + " Minutes ago";
		} else{
			TimeSince = diffSeconds + " Seconds ago";
		}

		return TimeSince;
	}

	public String getVersion() {return version;}
	public void setVersion(String mVersion) {
		this.version = mVersion;
	}

    public String getattachedId() {return attachedId;}
    public void setattachedId(String mattachedId) {
        this.attachedId = mattachedId;
    }


    @Override
	public boolean equals(Object o)
	{
		return o instanceof Type_SMS && ((Type_SMS) o).id.equals(id);
	}
}
