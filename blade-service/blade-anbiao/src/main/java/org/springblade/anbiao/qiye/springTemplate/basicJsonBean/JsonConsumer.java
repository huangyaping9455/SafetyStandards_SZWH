package org.springblade.anbiao.qiye.springTemplate.basicJsonBean;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class JsonConsumer {

    @KafkaListener(topics = "${kafka.topic.json}", containerFactory = "basicKafkaListenerContainerFactory")
    public void receive(JsonBean basic) {
        System.out.println("receive:" + basic.toString());
    }
}
