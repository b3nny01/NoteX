package domain.frontend;

public class APIResult {
	private boolean ok;
	private String msg;
	private Object obj;

	public static APIResult of(boolean result) {
		return new APIResult(result);
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public static APIResult of(boolean result, String msg) {
		return new APIResult(result,msg);
	}

	public APIResult() {
		super();
		this.ok = false;
		this.msg = "";
	}

	private APIResult(boolean result) {
		super();
		this.ok = result;
		this.msg = "";
	}

	private APIResult(boolean result, String msg) {
		super();
		this.ok = result;
		this.msg = msg;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean result) {
		this.ok = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
