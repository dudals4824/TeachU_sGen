package sGen.teachu.DTO;

public class ItemInfoDTO {
	private int itemId;
	private String itemName; //korean name
	private String itemFileName; // file name stored in application

	public ItemInfoDTO(int itemId, String itemName, String itemFileName) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemFileName = itemFileName;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemitemFileName() {
		return itemFileName;
	}

	public void setItemFileName(String itemFileName) {
		this.itemFileName = itemFileName;
	}

	@Override
	public String toString() {
		return "ItemInfo [itemId=" + itemId + ", itemName=" + itemName
				+ ", itemPron=" + itemFileName + "]";
	}

	
}
