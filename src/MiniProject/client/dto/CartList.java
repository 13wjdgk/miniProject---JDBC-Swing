package MiniProject.client.dto;

import java.util.ArrayList;
import java.util.List;

public class CartList {
	//클래스의 하나뿐인 인스턴스를 저장하는 정적변수
	private static CartList cart;
	private static List<CartItem> cartList;
	private static int storeId = 0;
	//private 생성자
	public CartList(){}
	//인스턴스를 생성하고 전달한다.
	public static CartList getinstance(){
		if(cart == null){
			cart = new CartList();
			cartList = new ArrayList<>();
		}
		return cart;
	}
	public static List<CartItem> getCartList(){
		return cartList;
	}

	public static boolean saveItem(CartItem item,int itemStoreId){
		if(cartList != null){
			if(storeId == 0 || storeId == itemStoreId){
				storeId = itemStoreId;
				cartList.add(item);
				return true;
			}else{
				return false;
			}

		}
		return false;
	}
	public static void clearCart(){
		cartList.clear();
		storeId = 0;
	}
	public static int getStoreId(){
		return storeId;
	}
}
