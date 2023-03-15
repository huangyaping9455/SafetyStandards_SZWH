package org.springblade.anbiao.qiye.springTemplate.transactional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class TransactionalConsumer {

//    @KafkaListener(topics = "${kafka.topic.transactional}", containerFactory = "transactionalKafkaListenerContainerFactory")
//    public void receive(@Payload String message,
//                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
//        System.out.println(String.format("From partition %d : %s", partition, message) );
//    }

}
