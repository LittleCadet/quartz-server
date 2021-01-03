package la.kaike.ksearchapi.home.producer;

import com.alibaba.fastjson.JSON;
import la.kaike.ksearchapi.integration.kafka.producer.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.admin.TopicListing;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Ignore
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class KafkaProducerTest {

    @Value("${kafka.mobilelog.topic}")
    private String topic;

    @Value("#{new Integer('${kafka.mobilelog.partition}')}")
    private Integer partition;

    @Resource
    private KafkaProducer kafkaProducer;

    @Resource
    private AdminClient adminClient;

    @Test
    public void sendMessage() throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        String content = new String(Files.readAllBytes(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("mobilelog.json"))
                .toURI())));
        Object obj = JSON.parse(content);
        ListenableFuture<SendResult<String, String>> result = kafkaProducer.sendMessage(topic, "log-message." + System.currentTimeMillis(),
                obj, true, partition);
        result.get();
    }

    @Test
    public void checkStatus() throws ExecutionException, InterruptedException {
        ListTopicsResult result = adminClient.listTopics();
        log.info("开始列出kafka topics");
        for (TopicListing topicListing : result.listings().get()) {
            log.info("===========kafka topic：" + topicListing.name() + "=============");
        }
        DescribeTopicsResult topicsResult = adminClient.describeTopics(Collections.singletonList(topic));
        TopicDescription description = topicsResult.values().get(topic).get();
        log.info("查看关心的topic：{}， 几个分区: {}, 是否内部：{}", description.name(), description.partitions(), description.isInternal());
    }

    @Test
    public void deleteTopic() throws ExecutionException, InterruptedException {
        adminClient.deleteTopics(Collections.singletonList(topic)).all().get();
    }
}