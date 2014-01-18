package sGen.teachu.DTO;

public class CategoryDTO {
	private int cateId;
	private String cateName;
	private boolean paid;

	public CategoryDTO() {
		super();
	}

	public CategoryDTO(int cateId, String cateName, boolean paid) {
		super();
		this.cateId = cateId;
		this.cateName = cateName;
		this.paid = paid;
	}

	public int getCateId() {
		return cateId;
	}

	public void setCateId(int cateId) {
		this.cateId = cateId;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	@Override
	public String toString() {
		return "CategoryDTO [cateId=" + cateId + ", cateName=" + cateName
				+ ", paid=" + paid + "]";
	}

}
