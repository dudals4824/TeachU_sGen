package sGen.teachu.DTO;

public class BabyGrowthDTO {
	private int itemId;
	private int cateId;
	private int babyId;
	private int showCnt;
	private int correctAns;

	public BabyGrowthDTO() {
		super();
	}

	public BabyGrowthDTO(int itemId, int cateId, int babyId, int showCnt,
			int correctAns) {
		super();
		this.itemId = itemId;
		this.cateId = cateId;
		this.babyId = babyId;
		this.showCnt = showCnt;
		this.correctAns = correctAns;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getCateId() {
		return cateId;
	}

	public void setCateId(int cateId) {
		this.cateId = cateId;
	}

	public int getBabyId() {
		return babyId;
	}

	public void setBabyId(int babyId) {
		this.babyId = babyId;
	}

	public int getShowCnt() {
		return showCnt;
	}

	public void setShowCnt(int showCnt) {
		this.showCnt = showCnt;
	}

	public int getCorrectAns() {
		return correctAns;
	}

	public void setCorrectAns(int correctAns) {
		this.correctAns = correctAns;
	}

	@Override
	public String toString() {
		return "BabyGrowthDTO [itemId=" + itemId + ", cateId=" + cateId
				+ ", babyId=" + babyId + ", showCnt=" + showCnt
				+ ", correctAns=" + correctAns + "]";
	}

}
