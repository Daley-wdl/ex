package com.exhibition.po;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

public class Exhibitstore implements java.io.Serializable {

	// Fields:id,exhibitsName,exhibitorId,category,mainPhotoPath,intro,intro,creatTime,status
	private Integer id;
	@NotNull(message = "展品名不能为空")
	@Size(min = 1,max = 30,message = "展品名长度在1-30之间")
	private String exhibitsName;
	private Integer exhibitorId;//对应Exhibitor中的userId属性
	@NotNull(message = "展品类别不能为空")
	@Size(max = 10,message = "类别长度在10以内")
	private String category;
	private String mainPhotoPath;
	@Size(min = 1,max = 200,message = "展品介绍在1-200之间")
	private String intro;
	private String status;
	private Timestamp creatTime;
	private String published;//是否已经发布
	private Integer number;
	@NotNull(message = "价格不能为空")
	@Min(1)
	@Max(1000000)
	private Integer price;
	@NotNull
	@Min(1)
	@Max(300)
	private Integer shipmentAmount;//快递费
	// Constructors

	/** default constructor */
	public Exhibitstore() {
		this.creatTime = new Timestamp(System.currentTimeMillis());
	}

	public Exhibitstore(String exhibitsName, Integer exhibitorId,
						String category, String mainPhotoPath, String intro, String status) {
		this.exhibitsName = exhibitsName;
		this.exhibitorId = exhibitorId;
		this.category = category;
		this.mainPhotoPath = mainPhotoPath;
		this.intro = intro;
		this.status = status;
		this.creatTime = new Timestamp(System.currentTimeMillis());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getExhibitsName() {
		return exhibitsName;
	}

	public void setExhibitsName(String exhibitsName) {
		this.exhibitsName = exhibitsName;
	}

	public Integer getExhibitorId() {
		return exhibitorId;
	}

	public void setExhibitorId(Integer exhibitorId) {
		this.exhibitorId = exhibitorId;
	}

	public Timestamp getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Timestamp creatTime) {
		this.creatTime = creatTime;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getMainPhotoPath() {
		return mainPhotoPath;
	}

	public void setMainPhotoPath(String mainPhotoPath) {
		this.mainPhotoPath = mainPhotoPath;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getShipmentAmount() {
		return shipmentAmount;
	}

	public void setShipmentAmount(Integer shipmentAmount) {
		this.shipmentAmount = shipmentAmount;
	}

	@Override
	public String toString() {
		return "Exhibitstore{" +
				"id=" + id +
				", exhibitsName='" + exhibitsName + '\'' +
				", exhibitorId=" + exhibitorId +
				", category='" + category + '\'' +
				", mainPhotoPath='" + mainPhotoPath + '\'' +
				", intro='" + intro + '\'' +
				", status='" + status + '\'' +
				", creatTime=" + creatTime +
				", published='" + published + '\'' +
				", number=" + number +
				", price=" + price +
				'}';
	}
}