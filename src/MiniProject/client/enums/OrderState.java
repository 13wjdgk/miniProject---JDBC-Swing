package MiniProject.client.enums;

public enum OrderState {
	접수중("접수중"),
	주문완료("주문완료"),
	배달중("배달중"),
	주문취소("주문취소"),
	조리중("조리중");

	private final String state;

	OrderState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}
}
