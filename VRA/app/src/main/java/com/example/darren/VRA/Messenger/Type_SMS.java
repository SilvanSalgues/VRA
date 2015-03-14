// Copyright 2015 Darren McNeely. All Rights Reserved.

package com.example.darren.VRA.Messenger;

/**
 * Represents an item in a To Do list
 */
public class Type_SMS {

    @com.google.gson.annotations.SerializedName("user")
    private String mUser;

    /**
	 * Message text
	 */
	@com.google.gson.annotations.SerializedName("text")
	private String mText;

	/**
	 * Message Id
	 */
	@com.google.gson.annotations.SerializedName("id")
	private String mId;

	/**
	 * Indicates if the Message is completed
	 */
	@com.google.gson.annotations.SerializedName("complete")
	private boolean mComplete;



	public Type_SMS() {
	}

	@Override
	public String toString() {
		return getText();
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

	public String getId() {
		return mId;
	}
	public final void setId(String id) {
		mId = id;
	}

	/**
	 * Indicates if the item is marked as completed
	 */
	public boolean isComplete() {
		return mComplete;
	}
	public void setComplete(boolean complete) {
		mComplete = complete;
	}

	@Override
	public boolean equals(Object o)
    {
        return o instanceof Type_SMS && ((Type_SMS) o).mId.equals(mId);
	}
}
