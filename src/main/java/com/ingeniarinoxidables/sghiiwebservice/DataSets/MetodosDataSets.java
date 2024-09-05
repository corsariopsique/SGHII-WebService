package com.ingeniarinoxidables.sghiiwebservice.DataSets;

import java.util.*;

public class MetodosDataSets {

    public MetodosDataSets() {

    }

    public Set<String> getAllKeys(Map<String,Long> a, Map<String,Long> b) {
        Set<String> allKeys = new HashSet<>(a.keySet());
        allKeys.addAll(b.keySet());
        return allKeys;
    }

    public void completeDataset(Map<String, Long> dataset, Set<String> allKeys) {
        for (String key : allKeys) {
            dataset.putIfAbsent(key, 0L);
        }
    }

    public List<Long> getOrderedValues(Map<String, Long> dataset, List<String> labels) {
        List<Long> values = new ArrayList<>();
        for (String label : labels) {
            values.add(dataset.get(label));
        }
        return values;
    }

    public Set<String> getAllKeysFloat(Map<String,Float> a, Map<String,Float> b) {
        Set<String> allKeys = new HashSet<>(a.keySet());
        allKeys.addAll(b.keySet());
        return allKeys;
    }

    public List<Float> getOrderedValuesFloat(Map<String, Float> dataset, List<String> labels) {
        List<Float> values = new ArrayList<>();
        for (String label : labels) {
            values.add(dataset.get(label));
        }
        return values;
    }

    public Set<Integer> getAllKeysFloatItems(Map<Integer,Float> a, Map<Integer,Float> b) {
        Set<Integer> allKeys = new HashSet<>(a.keySet());
        allKeys.addAll(b.keySet());
        return allKeys;
    }

    public List<Float> getOrderedValuesFloatItems(Map<Integer, Float> dataset, List<Integer> labels) {
        List<Float> values = new ArrayList<>();
        for (Integer label : labels) {
            values.add(dataset.get(label));
        }
        return values;
    }

}
