package com.example.darren.VRA.Messenger;

import java.util.Date;

/**
 * Represents a Message
 */
public class Type_SMS {

	public Type_SMS() {
	}

	@com.google.gson.annotations.SerializedName("id")
	private String mId;

	@com.google.gson.annotations.SerializedName("user")
	private String mUser;

	@com.google.gson.annotations.SerializedName("text")
	private String mText;

	@com.google.gson.annotations.SerializedName("complete")
	private boolean mComplete;

	@com.google.gson.annotations.SerializedName("__createdAt")
	private Date mCreatedAt;

	@com.google.gson.annotations.SerializedName("__version")
	private String mVersion;


	@Override
	public String toString() {
		return getText();
	}

	public String getId() {
		return mId;
	}
	public final void setId(String id) {
		mId = id;
	}

	public String getUser()
	{
		return mUser;
	}
	public final void setUser (String user) {
		mUser = user;
	}

	public String getText() {
		return mText;
	}
	public final void setText(String text) {
		mText = text;
	}

	public boolean isComplete() {
		return mComplete;
	}
	public void setComplete(boolean complete) {
		mComplete = complete;
	}

	public Date getCreatedAt() {return mCreatedAt;}

	public String getVersion() {return mVersion;}
	public void setVersion(String mVersion) {
		this.mVersion = mVersion;
	}


	@Override
	public boolean equals(Object o)
	{
		return o instanceof Type_SMS && ((Type_SMS) o).mId.equals(mId);
	}
}
