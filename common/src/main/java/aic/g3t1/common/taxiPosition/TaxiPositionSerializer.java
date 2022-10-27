package aic.g3t1.common.taxiPosition;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.io.*;

public class TaxiPositionSerializer implements Serializer {
    @Override
    public byte[] serialize(String topic, Object data) {
        if (data == null) {
            return new byte[0];
        }

        try {
            ByteArrayOutputStream arrayStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(arrayStream);
            objectStream.writeObject((TaxiPosition) data);
            byte[] byteArray = arrayStream.toByteArray();
            arrayStream.close();
            objectStream.close();
            return byteArray;
        } catch (IOException ex) {
            throw new SerializationException(ex);
        }
    }
}
