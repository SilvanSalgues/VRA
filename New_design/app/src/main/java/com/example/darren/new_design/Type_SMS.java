package com.example.darren.new_design;

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

	/**
	 * Initializes a new ToDoItem
	 * 
	 * @param text
	 *            The item text
	 * @param id
	 *            The item id
	 */
	public Type_SMS(String text, String id) {
		this.setText(text);
		this.setId(id);
	}



    public String getUser()
    {
        return mUser;
    }


    public final void setUser (String user) {
        mUser = user;
    }

    /**
	 * Returns the item text
	 */
	public String getText() {
		return mText;
	}

	/**
	 * Sets the item text
	 * 
	 * @param text
	 *            text to set
	 */
	public final void setText(String text) {
		mText = text;
	}

	/**
	 * Returns the item id
	 */
	public String getId() {
		return mId;
	}

	/**
	 * Sets the item id
	 * 
	 * @param id
	 *            id to set
	 */
	public final void setId(String id) {
		mId = id;
	}

	/**
	 * Indicates if the item is marked as completed
	 */
	public boolean isComplete() {
		return mComplete;
	}

	/**
	 * Marks the item as completed or incompleted
	 */
	public void setComplete(boolean complete) {
		mComplete = complete;
	}

	@Override
	public boolean equals(Object o)
    {
        return o instanceof Type_SMS && ((Type_SMS) o).mId == mId;
	}
}
