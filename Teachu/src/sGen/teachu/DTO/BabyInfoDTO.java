package sGen.teachu.DTO;

import android.graphics.Bitmap;

public class BabyInfoDTO {
	private int babyId;
	private String name;
	private String password;
	private int sex;
	private long birth;
	private Bitmap photo;
	
	public BabyInfoDTO(int babyId, String name, String password, int sex,
			long birth, Bitmap photo) {
		super();
		this.babyId = babyId;
		this.name = name;
		this.password = password;
		this.sex = sex;
		this.birth = birth;
		this.photo = photo;
	}
	
	public BabyInfoDTO() {
		super();
	}

	public int getBabyId() {
		return babyId;
	}

	public void setBabyId(int babyId) {
		this.babyId = babyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public long getBirth() {
		return birth;
	}

	public void setBirth(long babyBirth) {
		this.birth = babyBirth;
	}

	public Bitmap getPhoto() {
		return photo;
	}

	public void setPhoto(Bitmap photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "babyInfo [babyId=" + babyId + ", name=" + name + ", password="
				+ password + ", sex=" + sex + ", birth=" + birth + "]";
	}

}
