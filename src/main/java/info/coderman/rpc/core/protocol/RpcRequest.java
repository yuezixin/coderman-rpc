package info.coderman.rpc.core.protocol;

import java.io.Serializable;
import java.util.Arrays;
/**
 * 请求信息
 * @author yuezixin 2016-8-25 12:12:18
 *
 */
public class RpcRequest implements Serializable  {
	private static final long serialVersionUID = 1521050723175659620L;
	private String messageId;
    private String interfaceName;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

   

    public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String className) {
        this.interfaceName = className;
    }
 

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

	@Override
	public String toString() {
		return "RpcRequest [messageId=" + messageId + ", interfaceName=" + interfaceName + ", methodName=" + methodName + ", parameterTypes=" + Arrays.toString(parameterTypes)
				+ ", parameters=" + Arrays.toString(parameters) + "]";
	}
    
}
