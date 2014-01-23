package sGen.teachu.MakeItem;

import java.util.ArrayList;

import android.util.Log;
import sGen.teachu.R;
import sGen.teachu.DTO.*;
import sGen.teachu.database.*;

public class Fruit {
	private ArrayList<ItemInfoDTO> itemList = new ArrayList<ItemInfoDTO>();
	public Fruit(){
		itemList.add(new ItemInfoDTO(0, 1, "사과", "apple"));	
		itemList.add(new ItemInfoDTO(1, 1, "바나나", "banana"));
		itemList.add(new ItemInfoDTO(2, 1, "당근", "carrot"));
		itemList.add(new ItemInfoDTO(3, 1, "포도", "grape"));
		itemList.add(new ItemInfoDTO(4, 1, "레몬", "lemon"));
		itemList.add(new ItemInfoDTO(5, 1, "복숭아", "peach"));
		itemList.add(new ItemInfoDTO(6, 1, "감", "persimmon"));
		itemList.add(new ItemInfoDTO(7, 1, "딸기", "strawberry"));
		itemList.add(new ItemInfoDTO(8, 1, "토마토", "tomato"));
		itemList.add(new ItemInfoDTO(9, 1, "수박", "watermelon"));
		
		Log.e("minka", "make fruit complete");
	}
	public ArrayList<ItemInfoDTO> getItemList(){
		return itemList;
	}

}
