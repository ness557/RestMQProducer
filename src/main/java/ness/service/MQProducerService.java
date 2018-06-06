package ness.service;

import ness.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQProducerService implements ProducerService {

    private Producer producer;

    @Autowired
    public MQProducerService(Producer producer){
        this.producer = producer;
    }

    @Override
    public int produce(String message) {
        return producer.produce(message);
    }
}
