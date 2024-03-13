//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.cloudfoundry.client.v3.processes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.cloudfoundry.Nullable;
import org.immutables.value.Generated;

import java.util.*;

/* uptime, fileDescriptorQuota, host nullable */
@Generated(
        from = "_ProcessStatisticsResource",
        generator = "Immutables"
)
public final class ProcessStatisticsResource extends _ProcessStatisticsResource {
    @Nullable
    private final String details;
    @Nullable
    private final Long diskQuota;
    @Nullable
    private final Long fileDescriptorQuota;
    @Nullable
    private final String host;
    private final Integer index;
    private final List<PortMapping> instancePorts;
    @Nullable
    private final String isolationSegment;
    @Nullable
    private final Long memoryQuota;
    @Nullable
    private final ProcessState state;
    private final String type;
    @Nullable
    private final Long uptime;
    @Nullable
    private final ProcessUsage usage;

    private ProcessStatisticsResource(Builder builder) {
        this.details = builder.details;
        this.diskQuota = builder.diskQuota;
        this.fileDescriptorQuota = builder.fileDescriptorQuota;
        this.host = builder.host;
        this.index = builder.index;
        this.instancePorts = createUnmodifiableList(true, builder.instancePorts);
        this.isolationSegment = builder.isolationSegment;
        this.memoryQuota = builder.memoryQuota;
        this.state = builder.state;
        this.type = builder.type;
        this.uptime = builder.uptime;
        this.usage = builder.usage;
    }

    @JsonProperty("details")
    @Nullable
    public String getDetails() {
        return this.details;
    }

    @JsonProperty("disk_quota")
    @Nullable
    public Long getDiskQuota() {
        return this.diskQuota;
    }

    @JsonProperty("fds_quota")
    @Nullable
    public Long getFileDescriptorQuota() {
        return this.fileDescriptorQuota;
    }

    @JsonProperty("host")
    @Nullable
    public String getHost() {
        return this.host;
    }

    @JsonProperty("index")
    public Integer getIndex() {
        return this.index;
    }

    @JsonProperty("instance_ports")
    public List<PortMapping> getInstancePorts() {
        return this.instancePorts;
    }

    @JsonProperty("isolation_segment")
    @Nullable
    public String getIsolationSegment() {
        return this.isolationSegment;
    }

    @JsonProperty("mem_quota")
    @Nullable
    public Long getMemoryQuota() {
        return this.memoryQuota;
    }

    @JsonProperty("state")
    @Nullable
    public ProcessState getState() {
        return this.state;
    }

    @JsonProperty("type")
    public String getType() {
        return this.type;
    }

    @JsonProperty("uptime")
    @Nullable
    public Long getUptime() {
        return this.uptime;
    }

    @JsonProperty("usage")
    @Nullable
    public ProcessUsage getUsage() {
        return this.usage;
    }

    public boolean equals(Object another) {
        if (this == another) {
            return true;
        } else {
            return another instanceof ProcessStatisticsResource && this.equalTo((ProcessStatisticsResource)another);
        }
    }

    private boolean equalTo(ProcessStatisticsResource another) {
        return Objects.equals(this.details, another.details) && Objects.equals(this.diskQuota, another.diskQuota) && Objects.equals(this.fileDescriptorQuota, another.fileDescriptorQuota) && Objects.equals(this.host, another.host) && this.index.equals(another.index) && this.instancePorts.equals(another.instancePorts) && Objects.equals(this.isolationSegment, another.isolationSegment) && Objects.equals(this.memoryQuota, another.memoryQuota) && Objects.equals(this.state, another.state) && this.type.equals(another.type) && this.uptime.equals(another.uptime) && Objects.equals(this.usage, another.usage);
    }

    public int hashCode() {
        int h = 5381;
        h += (h << 5) + Objects.hashCode(this.details);
        h += (h << 5) + Objects.hashCode(this.diskQuota);
        h += (h << 5) + Objects.hashCode(this.fileDescriptorQuota);
        h += (h << 5) + Objects.hashCode(this.host);
        h += (h << 5) + this.index.hashCode();
        h += (h << 5) + this.instancePorts.hashCode();
        h += (h << 5) + Objects.hashCode(this.isolationSegment);
        h += (h << 5) + Objects.hashCode(this.memoryQuota);
        h += (h << 5) + Objects.hashCode(this.state);
        h += (h << 5) + this.type.hashCode();
        h += (h << 5) + Objects.hashCode(this.uptime);
        h += (h << 5) + Objects.hashCode(this.usage);
        return h;
    }

    public String toString() {
        return "ProcessStatisticsResource{details=" + this.details + ", diskQuota=" + this.diskQuota + ", fileDescriptorQuota=" + this.fileDescriptorQuota + ", host=" + this.host + ", index=" + this.index + ", instancePorts=" + this.instancePorts + ", isolationSegment=" + this.isolationSegment + ", memoryQuota=" + this.memoryQuota + ", state=" + this.state + ", type=" + this.type + ", uptime=" + this.uptime + ", usage=" + this.usage + "}";
    }

    /** @deprecated */
    @Deprecated
    @JsonCreator(
            mode = Mode.DELEGATING
    )
    static ProcessStatisticsResource fromJson(Json json) {
        Builder builder = builder();
        if (json.details != null) {
            builder.details(json.details);
        }

        if (json.diskQuota != null) {
            builder.diskQuota(json.diskQuota);
        }

        if (json.fileDescriptorQuota != null) {
            builder.fileDescriptorQuota(json.fileDescriptorQuota);
        }

        if (json.host != null) {
            builder.host(json.host);
        }

        if (json.index != null) {
            builder.index(json.index);
        }

        if (json.instancePorts != null) {
            builder.addAllInstancePorts(json.instancePorts);
        }

        if (json.isolationSegment != null) {
            builder.isolationSegment(json.isolationSegment);
        }

        if (json.memoryQuota != null) {
            builder.memoryQuota(json.memoryQuota);
        }

        if (json.state != null) {
            builder.state(json.state);
        }

        if (json.type != null) {
            builder.type(json.type);
        }

        if (json.uptime != null) {
            builder.uptime(json.uptime);
        }

        if (json.usage != null) {
            builder.usage(json.usage);
        }

        return builder.build();
    }

    private static ProcessStatisticsResource validate(ProcessStatisticsResource instance) {
        /* fileDescriptorQuota, host nullable */
        //instance.check();
        return instance;
    }

    public static Builder builder() {
        return new Builder();
    }

    private static <T> List<T> createSafeList(Iterable<? extends T> iterable, boolean checkNulls, boolean skipNulls) {
        ArrayList list;
        if (iterable instanceof Collection) {
            int size = ((Collection)iterable).size();
            if (size == 0) {
                return Collections.emptyList();
            }

            list = new ArrayList();
        } else {
            list = new ArrayList();
        }

        Iterator var6 = iterable.iterator();

        while(true) {
            Object element;
            do {
                if (!var6.hasNext()) {
                    return list;
                }

                element = var6.next();
            } while(skipNulls && element == null);

            if (checkNulls) {
                Objects.requireNonNull(element, "element");
            }

            list.add(element);
        }
    }

    private static <T> List<T> createUnmodifiableList(boolean clone, List<T> list) {
        switch (list.size()) {
            case 0:
                return Collections.emptyList();
            case 1:
                return Collections.singletonList(list.get(0));
            default:
                if (clone) {
                    return Collections.unmodifiableList(new ArrayList(list));
                } else {
                    if (list instanceof ArrayList) {
                        ((ArrayList)list).trimToSize();
                    }

                    return Collections.unmodifiableList(list);
                }
        }
    }

    @Generated(
            from = "_ProcessStatisticsResource",
            generator = "Immutables"
    )
    public static final class Builder {
        private static final long INIT_BIT_INDEX = 1L;
        private static final long INIT_BIT_TYPE = 2L;
        /* uptime nullable */
        //private static final long INIT_BIT_UPTIME = 4L;
        private long initBits;
        private String details;
        private Long diskQuota;
        private Long fileDescriptorQuota;
        private String host;
        private Integer index;
        private List<PortMapping> instancePorts;
        private String isolationSegment;
        private Long memoryQuota;
        private ProcessState state;
        private String type;
        private Long uptime;
        private ProcessUsage usage;

        private Builder() {
            /* uptime nullable */
            //this.initBits = 7L;
            this.initBits = 3L;
            this.instancePorts = new ArrayList();
        }

        public final Builder from(ProcessStatistics instance) {
            Objects.requireNonNull(instance, "instance");
            this.from((Object)instance);
            return this;
        }

        public final Builder from(ProcessStatisticsResource instance) {
            Objects.requireNonNull(instance, "instance");
            this.from((Object)instance);
            return this;
        }

        public final Builder from(_ProcessStatisticsResource instance) {
            Objects.requireNonNull(instance, "instance");
            this.from((Object)instance);
            return this;
        }

        private void from(Object object) {
            if (object instanceof ProcessStatistics) {
                ProcessStatistics instance = (ProcessStatistics)object;
                Long fileDescriptorQuotaValue = instance.getFileDescriptorQuota();
                if (fileDescriptorQuotaValue != null) {
                    this.fileDescriptorQuota(fileDescriptorQuotaValue);
                }

                String isolationSegmentValue = instance.getIsolationSegment();
                if (isolationSegmentValue != null) {
                    this.isolationSegment(isolationSegmentValue);
                }

                ProcessUsage usageValue = instance.getUsage();
                if (usageValue != null) {
                    this.usage(usageValue);
                }

                String hostValue = instance.getHost();
                if (hostValue != null) {
                    this.host(hostValue);
                }

                this.index(instance.getIndex());
                String detailsValue = instance.getDetails();
                if (detailsValue != null) {
                    this.details(detailsValue);
                }

                Long memoryQuotaValue = instance.getMemoryQuota();
                if (memoryQuotaValue != null) {
                    this.memoryQuota(memoryQuotaValue);
                }

                ProcessState stateValue = instance.getState();
                if (stateValue != null) {
                    this.state(stateValue);
                }

                this.type(instance.getType());
                Long diskQuotaValue = instance.getDiskQuota();
                if (diskQuotaValue != null) {
                    this.diskQuota(diskQuotaValue);
                }

                this.addAllInstancePorts(instance.getInstancePorts());
                this.uptime(instance.getUptime());
            }

        }

        @JsonProperty("details")
        public final Builder details(@Nullable String details) {
            this.details = details;
            return this;
        }

        @JsonProperty("disk_quota")
        public final Builder diskQuota(@Nullable Long diskQuota) {
            this.diskQuota = diskQuota;
            return this;
        }

        @JsonProperty("fds_quota")
        public final Builder fileDescriptorQuota(@Nullable Long fileDescriptorQuota) {
            this.fileDescriptorQuota = fileDescriptorQuota;
            return this;
        }

        @JsonProperty("host")
        public final Builder host(@Nullable String host) {
            this.host = host;
            return this;
        }

        @JsonProperty("index")
        public final Builder index(Integer index) {
            this.index = (Integer)Objects.requireNonNull(index, "index");
            this.initBits &= -2L;
            return this;
        }

        public final Builder instancePort(PortMapping element) {
            this.instancePorts.add(Objects.requireNonNull(element, "instancePorts element"));
            return this;
        }

        public final Builder instancePorts(PortMapping... elements) {
            PortMapping[] var2 = elements;
            int var3 = elements.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                PortMapping element = var2[var4];
                this.instancePorts.add(Objects.requireNonNull(element, "instancePorts element"));
            }

            return this;
        }

        @JsonProperty("instance_ports")
        public final Builder instancePorts(Iterable<? extends PortMapping> elements) {
            this.instancePorts.clear();
            return this.addAllInstancePorts(elements);
        }

        public final Builder addAllInstancePorts(Iterable<? extends PortMapping> elements) {
            Iterator var2 = elements.iterator();

            while(var2.hasNext()) {
                PortMapping element = (PortMapping)var2.next();
                this.instancePorts.add(Objects.requireNonNull(element, "instancePorts element"));
            }

            return this;
        }

        @JsonProperty("isolation_segment")
        public final Builder isolationSegment(@Nullable String isolationSegment) {
            this.isolationSegment = isolationSegment;
            return this;
        }

        @JsonProperty("mem_quota")
        public final Builder memoryQuota(@Nullable Long memoryQuota) {
            this.memoryQuota = memoryQuota;
            return this;
        }

        @JsonProperty("state")
        public final Builder state(@Nullable ProcessState state) {
            this.state = state;
            return this;
        }

        @JsonProperty("type")
        public final Builder type(String type) {
            this.type = (String)Objects.requireNonNull(type, "type");
            this.initBits &= -3L;
            return this;
        }

        @JsonProperty("uptime")
        public final Builder uptime(@Nullable Long uptime) {
            /* uptime nullable */
            //this.uptime = (Long)Objects.requireNonNull(uptime, "uptime");
            //this.initBits &= -5L;
            this.uptime = uptime;
            return this;
        }

        @JsonProperty("usage")
        public final Builder usage(@Nullable ProcessUsage usage) {
            this.usage = usage;
            return this;
        }

        public ProcessStatisticsResource build() {
            if (this.initBits != 0L) {
                throw new IllegalStateException(this.formatRequiredAttributesMessage());
            } else {
                return ProcessStatisticsResource.validate(new ProcessStatisticsResource(this));
            }
        }

        private String formatRequiredAttributesMessage() {
            List<String> attributes = new ArrayList();
            if ((this.initBits & 1L) != 0L) {
                attributes.add("index");
            }

            if ((this.initBits & 2L) != 0L) {
                attributes.add("type");
            }

            /* uptime nullable */
            //if ((this.initBits & 4L) != 0L) {
            //    attributes.add("uptime");
            //}

            return "Cannot build ProcessStatisticsResource, some of required attributes are not set " + attributes;
        }
    }

    /** @deprecated */
    @Deprecated
    @JsonDeserialize
    @JsonAutoDetect(
            fieldVisibility = Visibility.NONE
    )
    @Generated(
            from = "_ProcessStatisticsResource",
            generator = "Immutables"
    )
    static final class Json extends _ProcessStatisticsResource {
        String details;
        Long diskQuota;
        Long fileDescriptorQuota;
        String host;
        Integer index;
        List<PortMapping> instancePorts = Collections.emptyList();
        String isolationSegment;
        Long memoryQuota;
        ProcessState state;
        String type;
        Long uptime;
        ProcessUsage usage;

        Json() {
        }

        @JsonProperty("details")
        public void setDetails(@Nullable String details) {
            this.details = details;
        }

        @JsonProperty("disk_quota")
        public void setDiskQuota(@Nullable Long diskQuota) {
            this.diskQuota = diskQuota;
        }

        @JsonProperty("fds_quota")
        public void setFileDescriptorQuota(@Nullable Long fileDescriptorQuota) {
            this.fileDescriptorQuota = fileDescriptorQuota;
        }

        @JsonProperty("host")
        public void setHost(@Nullable String host) {
            this.host = host;
        }

        @JsonProperty("index")
        public void setIndex(Integer index) {
            this.index = index;
        }

        @JsonProperty("instance_ports")
        public void setInstancePorts(List<PortMapping> instancePorts) {
            this.instancePorts = instancePorts;
        }

        @JsonProperty("isolation_segment")
        public void setIsolationSegment(@Nullable String isolationSegment) {
            this.isolationSegment = isolationSegment;
        }

        @JsonProperty("mem_quota")
        public void setMemoryQuota(@Nullable Long memoryQuota) {
            this.memoryQuota = memoryQuota;
        }

        @JsonProperty("state")
        public void setState(@Nullable ProcessState state) {
            this.state = state;
        }

        @JsonProperty("type")
        public void setType(String type) {
            this.type = type;
        }

        @JsonProperty("uptime")
        public void setUptime(@Nullable Long uptime) {
            this.uptime = uptime;
        }

        @JsonProperty("usage")
        public void setUsage(@Nullable ProcessUsage usage) {
            this.usage = usage;
        }

        public String getDetails() {
            throw new UnsupportedOperationException();
        }

        public Long getDiskQuota() {
            throw new UnsupportedOperationException();
        }

        public Long getFileDescriptorQuota() {
            throw new UnsupportedOperationException();
        }

        public String getHost() {
            throw new UnsupportedOperationException();
        }

        public Integer getIndex() {
            throw new UnsupportedOperationException();
        }

        public List<PortMapping> getInstancePorts() {
            throw new UnsupportedOperationException();
        }

        public String getIsolationSegment() {
            throw new UnsupportedOperationException();
        }

        public Long getMemoryQuota() {
            throw new UnsupportedOperationException();
        }

        public ProcessState getState() {
            throw new UnsupportedOperationException();
        }

        public String getType() {
            throw new UnsupportedOperationException();
        }

        public Long getUptime() {
            throw new UnsupportedOperationException();
        }

        public ProcessUsage getUsage() {
            throw new UnsupportedOperationException();
        }
    }
}
