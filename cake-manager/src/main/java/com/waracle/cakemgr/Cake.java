package com.waracle.cakemgr;

/**
 *  POJO to carry Cake attributes
 * @author srkrovi
 *
 */
public class Cake {
	
	public String title;
	public String desc;
	public String image;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

}
