package org.springblade.anbiao.qiye.springTemplate.transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TransactionalProducerConfig {
    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;


//    @Bean
//    public KafkaTemplate<String, String> transactionalTemplate() {
//        KafkaTemplate template = new KafkaTemplate<String, String>(transactionalProducerFactory());
//        return template;
//    }
//
//    /**
//     * 生产者配置
//     * @return
//     */
//    private Map<String, Object> configs() {
//        Map<String, Object> props = new HashMap<>();
//        // 连接地址
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        // 键的序列化方式
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        // 值的序列化方式
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        // 重试，0为不启用重试机制
//        props.put(ProducerConfig.RETRIES_CONFIG, 1);
//        // 控制批处理大小，单位为字节
//        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
//        // 批量发送，延迟为1毫秒，启用该功能能有效减少生产者发送消息次数，从而提高并发量
//        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
//        // 生产者可以使用的总内存字节来缓冲等待发送到服务器的记录
//        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 1024000);
//        return props;
//    }
//
//
//    @Bean
//    public ProducerFactory<String, String> transactionalProducerFactory() {
//        DefaultKafkaProducerFactory factory = new DefaultKafkaProducerFactory<>(configs());
//        // 开启事务
//        factory.transactionCapable();
//        // 用来生成Transactional.id的前缀
//        factory.setTransactionIdPrefix("tran-");
//        return factory;
//    }
//
//    /**
//     * 事务管理器
//     * @param transactionalProducerFactory
//     * @return
//     */
//    @Bean
//    public KafkaTransactionManager transactionManager(ProducerFactory transactionalProducerFactory) {
//        KafkaTransactionManager manager = new KafkaTransactionManager(transactionalProducerFactory);
//        return manager;
//    }
}
