package com.prateek.kafka.sampleapp.anymessage.consumer.listener;

import com.prateek.kafka.sampleapp.anymessage.consumer.AnyMessageConsumerConfig;
import org.apache.kafka.clients.consumer.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import com.prateek.kafka.sampleapp.anymessage.consumer.usecases.AnyMessageConsumerSampleService;


/**
 * Note: If the retry-attempt > 0, the error handler will be only executed after all retries are failed.
 *
 * @return
 * @see SeekToCurrentErrorHandler
 */
@Component("anyMessageErrorHandler")
public class AnyMessageAutoAckListenerErrorHandler implements ConsumerAwareListenerErrorHandler {
    public static Logger LOGGER = LoggerFactory.getLogger(AnyMessageConsumerConfig.class);

    @Autowired
    private AnyMessageConsumerSampleService anyMessageConsumerSampleService;

    /**
     * @see KafkaListenerErrorHandler
     * @param message
     * @param exception
     * @param consumer
     * @return The return is ignore if there no @SendTo configuration. (see more at {@link KafkaListenerErrorHandler}
     */
    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
        Long offset = (Long)message.getHeaders().get(KafkaHeaders.OFFSET);
        LOGGER.error("Error in the consumer." +
                "\n\tConsumer Assignment: {}"+
                "\n\tData: {}." +
                "\n\tException: {}",consumer.assignment(), message, exception.getMessage(), exception);
        anyMessageConsumerSampleService.autoAckError();
        anyMessageConsumerSampleService.autoAckErrorAtOffset(offset);
        return null;
    }
}
