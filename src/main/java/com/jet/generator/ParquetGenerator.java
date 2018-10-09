package com.jet.generator;

import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.generic.GenericData;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ParquetGenerator {

    private static final int NUMBER_OF_RECORDS = 100_000;

    private static final List<String> OBJECT_TYPES = ImmutableList.of("package", "package title", "vendor");
    private static final List<String> EVENT_TYPES = ImmutableList.of("create", "update", "delete");

    private static final Schema SCHEMA = SchemaBuilder
            .record("User").namespace("example.avro")
            .fields()
            .name("eventId").type().stringType().noDefault()
            .name("customerId").type().intType().noDefault()
            .name("objectId").type().stringType().noDefault()
            .name("objectType").type().stringType().noDefault()
            .name("timestamp").type().longType().noDefault()
            .name("eventType").type().stringType().noDefault()
            .name("eventFields").type().stringType().noDefault()
            .endRecord();
    private static final String PATH_PATTERN = "../result/data/custId=%d/ts=%d/data%d.parquet";

    public static void main(String[] args) throws IOException {
        log.info("Application started");
        Random random = new Random();
        for (int i = 0; i < NUMBER_OF_RECORDS; i++) {
            final int customerId = random.nextInt(9999) + 1;
            final long timestamp = ((long) getRandomTimestamp(random)) + Instant.now().toEpochMilli();

            GenericData.Record record = new GenericData.Record(SCHEMA);
            record.put("eventId", UUID.randomUUID().toString());
            record.put("customerId", customerId);
            record.put("objectId", String.valueOf(random.nextInt(9999) + 1));
            record.put("objectType", getRandomObjectType(random));
            record.put("timestamp", timestamp);
            record.put("eventType", getRandomEvent(random));
            record.put("eventFields", RandomStringUtils.randomAlphanumeric(200));

            try (ParquetWriter<GenericData.Record> writer = AvroParquetWriter
                    .<GenericData.Record>builder(
                            new Path(String.format(PATH_PATTERN, customerId, timestamp, i)))
                    .withSchema(SCHEMA)
                    .withConf(new Configuration())
                    .withCompressionCodec(CompressionCodecName.SNAPPY)
                    .build()) {
                writer.write(record);
            }
            if (i % 10_000 == 0) {
                log.info("Index {}", i);
            }
        }
        log.info("Application finished");
    }

    private static String getRandomObjectType(Random random) {
        return OBJECT_TYPES.get(random.nextInt(OBJECT_TYPES.size()));
    }

    private static String getRandomEvent(Random random) {
        return EVENT_TYPES.get(random.nextInt(EVENT_TYPES.size()));
    }

    private static int getRandomTimestamp(Random random) {
        return random.nextInt(Math.abs((int) TimeUnit.DAYS.toMillis(30)));
    }

}
