package org.kpaas.sidecar.portal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

public class Envelope {
    public Envelope() {
    }

    public Envelope(//String instance_id,
                    Log log,
                    //String source_id,
                    Map<String, String> tags, Long timestamp)
                    //Map<String, String> deprecated_tags)
                    {
        //this.instance_id = instance_id;
        this.log = log;
        //this.source_id = source_id;
        this.tags = tags;
        this.timestamp = timestamp;
        //this.deprecated_tags = deprecated_tags;
    }

    //private String instance_id;

    @JsonProperty("log")
    private Log log;

    //private String source_id;
    @JsonProperty("tags")
    Map<String, String> tags;

    @JsonProperty("timestamp")
    private Long timestamp;


    //Map<String, String> deprecated_tags;

    public class Log{
        public String getPayloadAsText(String payload) {
            final byte[] decodedPayload = Base64.getDecoder().decode(payload);
            return StandardCharsets.UTF_8.decode(ByteBuffer.wrap(decodedPayload)).toString();
        }
        private String payload;

        private LogType type;

        public void setPayload(String payload) {
            this.payload = getPayloadAsText(payload);
        }

        public String getPayload() {
            return payload;
        }

        public LogType getType() {
            return type;
        }

        public void setType(LogType type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "Log{" +
                    "payload='" + payload + '\'' +
                    ", type=" + type +
                    '}';
        }
    }


    public enum LogType {

        OUT("OUT"),
        ERR("ERR");

        private final String value;

        LogType(String value) {
            this.value = value;
        }

        public static LogType from(String s) {
            switch (s.toLowerCase()) {
                case "out":
                    return OUT;
                case "err":
                    return ERR;
                default:
                    throw new IllegalArgumentException(String.format("Unknown log type: %s", s));
            }
        }
        public String getValue() {
            return this.value;
        }
        public String toString() {
            return getValue();
        }


    }

    //public String getInstance_id() {
    //    return instance_id;
    //}

    //public void setInstance_id(String instance_id) {
    //    this.instance_id = instance_id;
    //}

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }

    //public String getSource_id() {
    //    return source_id;
    //}

    //public void setSource_id(String source_id) {
    //    this.source_id = source_id;
    //}

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    //public Map<String, String> getDeprecated_tags() {
    //    return deprecated_tags;
    //}

    //public void setDeprecated_tags(Map<String, String> deprecated_tags) {
    //    this.deprecated_tags = deprecated_tags;
    //}

    @Override
    public String toString() {
        return "Envelope{" +
               // "instance_id='" + instance_id + '\'' +
                "log=" + log +
               // ", source_id='" + source_id + '\'' +
                ", tags=" + tags +
                ", timestamp=" + timestamp +
          //      ", deprecated_tags=" + deprecated_tags +
                '}';
    }
}
