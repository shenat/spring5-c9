package com.sat.flow.file;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * 
 * @ClassName: FileWriterGateway
 * @Description:将数据发送到集成流中的网关接口
 * 				它使用了@MessagingGateway注解。这个注解会告诉SpringIntegration要在运行时生成该接口的实现，
 * 				这与SpringData在运行时生成repository接口的实现非常类似。其他地方的代码在希望写入文件的时候将会调用它
 * 				MessagingGateway的defaultRequestChannel属性表明接口方法调用时所返回的消息要发送至给定的消息通道（messagechannel）。 
 * @author: sat
 * @date: 2020年9月22日 下午4:45:41
 * @param:
 */
@MessagingGateway(defaultRequestChannel = "textInChannel")
public interface FileWriterGateway {
	
	/**
	 * Header注解表明传递给filename的值应该包含在消息头信息中
	 * （通过FileHeaders.FILENAME声明，它将会被解析成file_name）
	 *  而不是放到消息载荷（payload）中。
	 */
	void writeToFile(@Header(FileHeaders.FILENAME) String filename, String data); 
}
