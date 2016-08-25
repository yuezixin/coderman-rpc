package info.coderman.rpc.core.protocol;

import java.io.Serializable;
/**
 * 响应结果
 * @author yuezixin 2016-8-25 12:12:34
 *
 */
public class RpcResponse implements Serializable{

	private static final long serialVersionUID = -8182340171690941501L;
	private String messageId;
    private Exception exception;
    private Object result;

    public boolean hasException() {
        return exception != null;
    }

    
    public String getMessageId() {
		return messageId;
	}


	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}


	public Throwable getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
