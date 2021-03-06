package sGen.teachu.DTO;

public class ItemInfoDTO {
	private int itemId;
	private int cateId;
	private String itemName; // korean name
	private String itemFileName; // file name stored in application

	public ItemInfoDTO(int _itemId, int _cateId, String _itemName, String _itemFileName){
		itemId = _itemId;
		cateId = _cateId;
		itemName = _itemName;
		itemFileName = _itemFileName;
	}
	
	public ItemInfoDTO() {
		// TODO Auto-generated constructor stub
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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemFileName() {
		return itemFileName;
	}

	public void setItemFileName(String itemFileName) {
		this.itemFileName = itemFileName;
	}

	@Override
	public String toString() {
		return "ItemInfoDTO [itemId=" + itemId + ", cateId=" + cateId
				+ ", itemName=" + itemName + ", itemFileName=" + itemFileName
				+ "]";
	}

}
