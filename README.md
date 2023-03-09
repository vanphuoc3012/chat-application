# Real Time Chat Application Using Spring Boot, WebSocket, RabbitMQ, Redis
    
Overview:
- Oracle Database: store user information
- Websocket: realtime tranfer message (<a href="">See here</a>)
- RabbitMQ: STOMP broker, keep track of subscriptions and broadcasts messages to subscribed users. (Alternation for default in-memory STOMP broker of Spring) (<a href="src%2Fmain%2Fjava%2Fcom%2Fexample%2Fchatapplication%2Fconfiguration%2FWebSocketConfiguration.java">See here</a>)
- Redis: in-memory database, use for store WebSocket Session, Chat Room information and messages. (<a href="src%2Fmain%2Fjava%2Fcom%2Fexample%2Fchatapplication%2Fchatroom%2Fservice%2Fimplement">See here</a>)
- Apache Zookeeper and Apache Kafka (use these tool for study purpose)
- Apache Kafka: (<a href="src%2Fmain%2Fjava%2Fcom%2Fexample%2Fchatapplication%2Fconfiguration%2Fkafka">See here</a>)
- Docker

#### Result
- Chat Application with multiple room (multiple WebSocket channel):
    
    ![login.png](images%2Flogin.png)
    ![img.png](images%2Fimg.png)
    ![img_1.png](images%2Fimg_1.png)

#### Kafka configuration
[ProducerKafkaConfiguration.java](src%2Fmain%2Fjava%2Fcom%2Fexample%2Fchatapplication%2Fconfiguration%2Fkafka%2FProducerKafkaConfiguration.java)
[ConsumerKafkaConfiguration.java](src%2Fmain%2Fjava%2Fcom%2Fexample%2Fchatapplication%2Fconfiguration%2Fkafka%2FConsumerKafkaConfiguration.java)

- Dynamic create/delete topic, also change Consumer topic at runtime:

```agsl
@Service
@Slf4j
public class KafkaService {
    @Autowired
    private AdminClient adminClient;
    
    @Autowired
    ConcurrentKafkaListenerContainerFactory<String, Message> listenerContainerFactory;
    
    @Autowired
    ConsumerFactory<String, Message> consumerFactory;
    
    ConcurrentMessageListenerContainer<String, Message> listenerContainer;
    public void changeTopic(String topic) throws InterruptedException {
        log.info("Changing topic to: {}", topic);
        if(listenerContainer != null) {
            listenerContainer.stop();
            Thread.sleep(2000);
            listenerContainer.destroy();
            Thread.sleep(2000);
        }
        ContainerProperties containerProperties = new ContainerProperties(topic);
        containerProperties.setGroupId(RandomStringUtils.randomAlphanumeric(3));
        containerProperties.setMessageListener((MessageListener<String, Message>) message -> {
            System.out.println("Kafka listener, topic: " + message.topic().toString() + ", message content: " + message.value().getContent());
        });
        listenerContainer = new ConcurrentMessageListenerContainer<>(consumerFactory, containerProperties);
        listenerContainer.start();
    }

    public void createTopic(String topic) {
        adminClient.createTopics(List.of(TopicBuilder.name(topic).build()));
    }

    public void deleteTopic(String topic) {
        adminClient.deleteTopics(List.of(topic));
    }
}
```
        
### Run project using Docker
[docker-compose.yml](docker%2Fdocker-compose.yml)

    
    docker compose up -d
![img_5.png](images%2Fimg_5.png)


 