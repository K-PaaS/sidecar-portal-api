package org.kpaas.sidecar.portal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Batch{
    @JsonProperty("batch")
    List<Envelope> batch;

    public Batch() {
    }

    public Batch(List<Envelope> batch) {
        this.batch = batch;
    }

    public List<Envelope> getBatch() {
        return batch;
    }

    public void setBatch(List<Envelope> batch) {
        this.batch = batch;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "batch=" + batch +
                '}';
    }
}
