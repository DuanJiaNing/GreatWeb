package com.duan.greatweb.entitly;

import com.duan.greatweb.dao.mapping.Mapping;

@Mapping("note")
public class Note {

	@Mapping("id")
	private int id;

	@Mapping("title")
	private String title;

	@Mapping("content")
	private String content;

	@Mapping("datetime")
	private long dateTime;

	@Mapping("uid")
	private int userId;

	public Note(){}
	
	public Note(int id, String title, String content, long dateTime, int userId) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.dateTime = dateTime;
		this.userId = userId;
	}

	public Note(String title, String content, long dateTime, int userId) {
		this.title = title;
		this.content = content;
		this.dateTime = dateTime;
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getDateTime() {
		return dateTime;
	}

	public void setDateTime(long dateTime) {
		this.dateTime = dateTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", title=" + title + ", content=" + content + ", dateTime=" + dateTime + ", userId="
				+ userId + "]";
	}

}
