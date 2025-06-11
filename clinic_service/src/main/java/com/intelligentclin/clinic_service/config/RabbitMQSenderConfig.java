// package com.intelligentclin.clinic_service.config;

// import org.springframework.amqp.core.Queue;
// import org.springframework.amqp.rabbit.core.RabbitTemplate;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class RabbitMQSenderConfig {

//     @Value("${queue.name}")
//     public String contaQueue;

//     @Autowired
//     private RabbitTemplate rabbitTemplate;

//     @Bean
//     public Queue queue() {
//         return new Queue(this.contaQueue, false);
//     }

//     public void convertAndSend(Object o) {
//         rabbitTemplate.convertAndSend(contaQueue, o);
//     }

// }
