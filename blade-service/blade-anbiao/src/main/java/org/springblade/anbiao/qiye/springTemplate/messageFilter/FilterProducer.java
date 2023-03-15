package org.springblade.anbiao.qiye.springTemplate.messageFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class FilterProducer {
    @Autowired
    @Qualifier("filterKafkaTemplate")
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.filter}")
    private String topic;

    public void send(String message) {
        kafkaTemplate.send(topic, message);
    }

}
