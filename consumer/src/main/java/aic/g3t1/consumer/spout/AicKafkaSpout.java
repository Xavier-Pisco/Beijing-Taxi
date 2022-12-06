package aic.g3t1.consumer.spout;

import aic.g3t1.common.environment.EnvironmentVariables;
import aic.g3t1.common.exceptions.MissingEnvironmentVariableException;
import aic.g3t1.common.taxiposition.TaxiPosition;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;

import java.util.Properties;

public class AicKafkaSpout extends KafkaSpout<String, TaxiPosition> {

    public static String KAFKA_TOPIC = "taxi";

    public AicKafkaSpout() throws MissingEnvironmentVariableException {
        super(getKafkaSpoutConfig());
    }

    public static KafkaSpoutConfig<String, TaxiPosition> getKafkaSpoutConfig()
            throws MissingEnvironmentVariableException {
        String bootstrapServer = EnvironmentVariables.getVariable("KAFKA_BOOTSTRAP_SERVER");

        var properties = new Properties();
        properties.setProperty("group.id", EnvironmentVariables.getVariable("KAFKA_GROUP_ID"));
        properties.setProperty("bootstrap.servers", bootstrapServer);
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer", "aic.g3t1.common.taxiposition.TaxiPositionDeserializer");

        var builder = new KafkaSpoutConfig.Builder<String, TaxiPosition>(bootstrapServer, KAFKA_TOPIC);
        builder.setProp(properties);
        builder.setProcessingGuarantee(KafkaSpoutConfig.ProcessingGuarantee.AT_MOST_ONCE);
        return new KafkaSpoutConfig<>(builder);
    }

}
