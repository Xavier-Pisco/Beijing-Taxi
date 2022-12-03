package aic.g3t1.consumer.bolt;

import aic.g3t1.common.taxiposition.TaxiPosition;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import java.util.List;
import java.util.Map;

import static aic.g3t1.consumer.spout.TaxiPositionFields.*;

public class KafkaTupleBolt extends BaseRichBolt {

    private OutputCollector collector;

    @Override
    public void prepare(Map<String, Object> topoConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        TaxiPosition position = (TaxiPosition) input.getValueByField("value");
        collector.emit(input, List.of(position.getTaxiNumber(), position.getTimestamp(), position.getLongitude(), position.getLatitude()));
        collector.ack(input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(F_TAXI_NUMBER, F_TIMESTAMP, F_LONGITUDE, F_LATITUDE));
    }

}
