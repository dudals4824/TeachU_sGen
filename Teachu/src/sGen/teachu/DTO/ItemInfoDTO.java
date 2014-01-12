package sGen.teachu.DTO;

public class ItemInfoDTO {
	private int itemId;
	private String itemName;
	private String itemPron;

	public ItemInfoDTO(int itemId, String itemName, String itemPron) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemPron = itemPron;
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

	public String getItemPron() {
		return itemPron;
	}

	public void setItemPron(String itemPron) {
		this.itemPron = itemPron;
	}

	@Override
	public String toString() {
		return "ItemInfo [itemId=" + itemId + ", itemName=" + itemName
				+ ", itemPron=" + itemPron + "]";
	}

	
}
