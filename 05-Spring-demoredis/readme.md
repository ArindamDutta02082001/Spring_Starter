# This is a SpringBoot rest api demo with redis
We follow the MVC model
>FLOW  → controller  → service → repository → model

### Integrate redis with spring boot
- **make sure you start your redis in your localhost ( port 6379 )**
- **install necessary dependencies + spring data redis ( access + driver )** . It installs the lettuce driver needed for the connection of your app to the redis driver . ( we can use other drivers like Jedis )
- **Create a Config.java file , inside it**
    - **Create a connection factory** . It will initiate the connection from the app to redis server
        <pre>
        // connection start
        public  LettuceConnectionFactory connectionFactory()
        {
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("localhost", 6379);
            LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
            lettuceConnectionFactory.afterPropertiesSet();
            return lettuceConnectionFactory;
        }
      </pre>
    - **Create a redis template** to handle the redis functions for set , hashmap, list , sorted set & other data structures
    - **Always use the serializer for storing the keys and values in the redis server as we have to serialize the key & value to String before storing**

          // redis template to use the apis of redis server
          public  RedisTemplate<String , Object> getTemplate()
           {
            RedisTemplate <String , Object> redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(connectionFactory());

            // to serialize for set , normal key-val pair , sorted set , hashmap etc
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());

            // to serialize the hash maps
            redisTemplate.setHashKeySerializer(new StringRedisSerializer()); // ~field
            redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer()); // value for a field

            redisTemplate.afterPropertiesSet();
            return redisTemplate;
          }
- Serialize the object by implementing **Serializable** in the entity class
<pre>
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person implements Serializable {

    private Integer id;
    private String name;
    private Long age;
    private Boolean isNRI;
    private Double creditScore;

}
</pre>
- Now we create the **controllers** to interact with the incoming requests by using the **redis template** from the factory class

learn form docs  :  [Redis official Commands](https://redis.io/docs/latest/commands/)
| [Redis Concept google doc]()

### All the Endpoints here
a. Strings | normal key-val pairs
- SET : `localhost:9000/person/set/13`
- GET : `localhost:9000/person/get/13`
- UPDATE : `localhost:9000/person/update/13`
- DELETE : `localhost:9000/person/del/13  ( not work as version < 5 )`

b. List
- LPUSH : `localhost:9000/person/lpush`
- RPUSH : `localhost:9000/person/rpush`
- GET : `localhost:9000/person/lrange?start=0&end=-1`
- LPOP : `localhost:9000/person/lpop`
- RPOP : `localhost:9000/person/rpop`

c. Hash map
- SET : `localhost:9000/person/hmset`
- GET : `localhost:9000/person/hmget?id=1234`
- UPDATE/INSERT a attr : `localhost:9000/person/hmupdate?id=1234&attribute=name3&value=ArindamD`
- DELETE a attr : ` localhost:9000/person/hdel?id=1234&attributes=name`

a. Set
- SET : `localhost:9000/person/add`
- GET : `localhost:9000/person/get`
- UPDATE : `localhost:9000/person/update    ( delete logic to be implemented )` + person payload
- DELETE : `localhost:9000/person/remove` + person payload

a. Sorted set
- SET a person with score : `localhost:9000/person/zadd?score=0`
- GET all person within a score range : `localhost:9000/person/zscorerange?min=0&max=9`
- GET all person within a index range : `localhost:9000/person/zindexrange?min=0&max=-1`
- UPDATE : `localhost:9000/person/zupdate?score=0` + person payload
- DELETE : `localhost:9000/person/zremove` + person payload

<pre>
payload of a Person :
    {
        "id": 1,
        "name": "John Doe",
        "age": 30,
        "isNRI": false,
        "creditScore": 750.0
    }

payload of an array of multiple Person :
[ {
        "id": 1,
        "name": "John Doe",
        "age": 30,
        "isNRI": false,
        "creditScore": 750.0
    } ,
 {
        "id": 1,
        "name": "John Doe",
        "age": 30,
        "isNRI": false,
        "creditScore": 750.0
} ]

</pre>


### application.properties file
<pre>
spring.application.name=demoredis
SERVER.port = 9000
</pre>