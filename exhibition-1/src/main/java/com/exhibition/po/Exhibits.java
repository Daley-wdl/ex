package com.exhibition.po;


import java.sql.Timestamp;

public class Exhibits implements java.io.Serializable {

	// Fields
	private Integer id;
	private String exhibitsName;
	private Timestamp creatTime;
	private String intro;
	private String mainPhotoPath;//封面照片访问路径
	private String status;
	private Integer exhibitorId;
	private String category;

	//在Exhibistore上增加的属性
	private Integer number;
	private String photoPath;//展品介绍的图片访问路径
	private Integer price;
	private Integer shipmentAmount;
	private Integer avgGrade;//平均好评度，默认为5（最高分）

	// Constructors

	/** default constructor */
	public Exhibits() {

	}

	public Exhibits(String exhibitsName, Integer number, Integer price, String intro, String mainPhotoPath,
					String photoPath, String status, Integer exhibitorId, String category) {
		this.exhibitsName = exhibitsName;
		this.number = number;
		this.price = price;
		this.intro = intro;
		this.mainPhotoPath = mainPhotoPath;
		this.photoPath = photoPath;
		this.status = status;
		this.exhibitorId = exhibitorId;
		this.category = category;
		this.creatTime = new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 根据展品仓库的记录创建展品
	 * <p>还需要继续填充属性</p>
	 * @param exhibitstore
	 */
	public Exhibits(Exhibitstore exhibitstore) {
		this.creatTime = exhibitstore.getCreatTime();
		this.category = exhibitstore.getCategory();
		this.exhibitsName = exhibitstore.getExhibitsName();
		this.intro = exhibitstore.getIntro();
		this.mainPhotoPath = exhibitstore.getMainPhotoPath();
		this.exhibitorId = exhibitstore.getExhibitorId();
		this.status = exhibitstore.getStatus();
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

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Timestamp getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Timestamp creatTime) {
		this.creatTime = creatTime;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getMainPhotoPath() {
		return mainPhotoPath;
	}

	public void setMainPhotoPath(String mainPhotoPath) {
		this.mainPhotoPath = mainPhotoPath;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getExhibitorId() {
		return exhibitorId;
	}

	public void setExhibitorId(Integer exhibitorId) {
		this.exhibitorId = exhibitorId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getShipmentAmount() {
		return shipmentAmount;
	}

	public void setShipmentAmount(Integer shipmentAmount) {
		this.shipmentAmount = shipmentAmount;
	}

	public Integer getAvgGrade() {
		return avgGrade;
	}

	public void setAvgGrade(Integer avgGrade) {
		this.avgGrade = avgGrade;
	}

	@Override
	public String toString() {
		return "Exhibits{" +
				"id=" + id +
				", exhibitsName='" + exhibitsName + '\'' +
				", creatTime=" + creatTime +
				", intro='" + intro + '\'' +
				", mainPhotoPath='" + mainPhotoPath + '\'' +
				", status='" + status + '\'' +
				", exhibitorId=" + exhibitorId +
				", category='" + category + '\'' +
				", number=" + number +
				", photoPath='" + photoPath + '\'' +
				", price=" + price +
				", shipmentAmount=" + shipmentAmount +
				", avgGrade=" + avgGrade +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Exhibits exhibits = (Exhibits) o;

		return id != null ? id.equals(exhibits.id) : exhibits.id == null;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}