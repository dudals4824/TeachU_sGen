package sGen.teachu.DTO;

import java.util.Date;

public class BabyInfoDTO {
	private int babyId;
	private String name;
	private String password;
	private int sex;
	private Date birth;

	public BabyInfoDTO(int babyId, String name, String password, int sex,
			Date birth) {
		super();
		this.babyId = babyId;
		this.name = name;
		this.password = password;
		this.sex = sex;
		this.birth = birth;
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

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	@Override
	public String toString() {
		return "babyInfo [babyId=" + babyId + ", name=" + name + ", password="
				+ password + ", sex=" + sex + ", birth=" + birth + "]";
	}

}
