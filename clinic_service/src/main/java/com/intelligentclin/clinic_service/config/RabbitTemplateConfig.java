// package com.intelligentclin.clinic_service.config;

// import org.springframework.amqp.rabbit.connection.ConnectionFactory;
// import org.springframework.amqp.rabbit.core.RabbitTemplate;
// import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class RabbitTemplateConfig {

//     @Bean
//     public Jackson2JsonMessageConverter producerJackson2JsonMessageConverter(){
//         return new Jackson2JsonMessageConverter();
//     }

//     @Bean
//     public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
//         RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//         rabbitTemplate.setMessageConverter(producerJackson2JsonMessageConverter());
//         return rabbitTemplate;
//     }
// }
