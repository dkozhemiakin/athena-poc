package com.jet;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Metrics {

    private final List<Row> metricsData = new ArrayList<>();

    public void addMetric(String name, long time, long space) {
        metricsData.add(Row.of(name, time, space));
    }

    public String prettyPrint() {
        StringBuilder sb = new StringBuilder("Metrics\n");
        sb.append("-----------------------------------------\n");
        sb.append(" Ms    |   Bytes     |          Task name\n");
        sb.append("-----------------------------------------\n");
        NumberFormat timeFormat = NumberFormat.getNumberInstance();
        timeFormat.setMinimumIntegerDigits(6);
        timeFormat.setGroupingUsed(false);
        NumberFormat spaceFormat = NumberFormat.getNumberInstance();
        spaceFormat.setMinimumIntegerDigits(12);
        spaceFormat.setGroupingUsed(false);
        for (Row row : metricsData) {
            sb.append(timeFormat.format(row.getTime())).append("  ");
            sb.append(spaceFormat.format(row.getSpace())).append("  ");
            sb.append(row.getName()).append("\n");
        }
        return sb.toString();
    }

    @Value
    @AllArgsConstructor(staticName = "of")
    private static class Row {
        private final String name;
        private final long time;
        private final long space;
    }
}
