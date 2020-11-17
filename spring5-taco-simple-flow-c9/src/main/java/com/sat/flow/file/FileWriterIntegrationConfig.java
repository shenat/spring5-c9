package com.sat.flow.file;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.MessageChannel;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: FileWriterIntegrationConfig
 * @Description: spring Integration 之 file端口 java配置演示
 * @author: sat
 * @date: 2020年9月22日 下午4:10:19
 * @param:
 */
@Slf4j
@Configuration
public class FileWriterIntegrationConfig {
	
	//普通java配置
	/**
	//集成流消息转换器,textInchannel不存在会自动创建
	@Bean
	@Transformer(inputChannel = "textInChannel", outputChannel = "fileWriterChannel")
	public GenericTransformer<String,String> upperCaseTransformer(){
		return text -> text.toUpperCase();
	}
	
	//集成流消息处理器，fileWriterChannel不存在会自动创建
	@Bean
	@ServiceActivator(inputChannel = "fileWriterChannel")
	public FileWritingMessageHandler fileWriter() {a
		FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("/tmp/integration/files"));
		handler.setExpectReply(false);//告知服务激活器（serviceactivator）不要期望会有答复通道（replychannel，通过这样的通道，我们可以将某个值返回到流中的上游组件）。
		handler.setFileExistsMode(FileExistsMode.APPEND);//将数据加入已有的文件中，并自动flush/close 流
		handler.setAppendNewLine(true);
		return handler;
	}
	
	//显示创建通道，以防需要细节上的操作
	@Bean
	public MessageChannel textInChannel() {
		return new DirectChannel();
	}
	
	@Bean
	public MessageChannel fileWriterChannel() {
		return new DirectChannel();
	}
	**/
	
	//使用java DSL(domain specific language)配置是推荐的做法  IntegrationFlow是构造集成流的API
	@Bean
	public IntegrationFlow fileWriterFlow() {
		log.info("开始处理消息");
		return IntegrationFlows.from(MessageChannels.direct("textInChannel"))//从哪个通道接收消息
							   .<String,String>transform(t -> t.toUpperCase())//消息转换
							   //.channel(MessageChannels.direct("fileWriterChannel"))//可以不显示申明 消息处理通道
							   .handle(Files.outboundAdapter(new File("C:\\Users\\V_QS4SCV\\Desktop\\test"))//消息处理，其中适配器是通过Integration File创建的
									   		.fileExistsMode(FileExistsMode.APPEND)
									   		.appendNewLine(true))
							   .get();
							   				
	}
	

	
	
	
}
