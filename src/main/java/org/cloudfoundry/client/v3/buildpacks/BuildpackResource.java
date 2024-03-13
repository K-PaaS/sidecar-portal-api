//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.cloudfoundry.client.v3.buildpacks;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.cloudfoundry.Nullable;
import org.cloudfoundry.client.v3.Link;
import org.cloudfoundry.client.v3.Metadata;
import org.cloudfoundry.client.v3.Resource;
import org.immutables.value.Generated;

import java.util.*;

/* state nullable 수정 */
@Generated(
        from = "_BuildpackResource",
        generator = "Immutables"
)
public final class BuildpackResource extends _BuildpackResource {
    private final String createdAt;
    private final String id;
    private final Map<String, Link> links;
    @Nullable
    private final String updatedAt;
    private final Boolean enabled;
    @Nullable
    private final String filename;
    private final Boolean locked;
    @Nullable
    private final Metadata metadata;
    private final String name;
    private final Integer position;
    @Nullable
    private final String stack;
    private final BuildpackState state;

    private BuildpackResource(Builder builder) {
        this.createdAt = builder.createdAt;
        this.id = builder.id;
        this.links = createUnmodifiableMap(false, false, builder.links);
        this.updatedAt = builder.updatedAt;
        this.enabled = builder.enabled;
        this.filename = builder.filename;
        this.locked = builder.locked;
        this.metadata = builder.metadata;
        this.name = builder.name;
        this.position = builder.position;
        this.stack = builder.stack;
        this.state = builder.state;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return this.createdAt;
    }

    @JsonProperty("guid")
    public String getId() {
        return this.id;
    }

    @JsonProperty("links")
    public Map<String, Link> getLinks() {
        return this.links;
    }

    @JsonProperty("updated_at")
    @Nullable
    public String getUpdatedAt() {
        return this.updatedAt;
    }

    @JsonProperty("enabled")
    public Boolean getEnabled() {
        return this.enabled;
    }

    @JsonProperty("filename")
    @Nullable
    public String getFilename() {
        return this.filename;
    }

    @JsonProperty("locked")
    public Boolean getLocked() {
        return this.locked;
    }

    @JsonProperty("metadata")
    @Nullable
    public Metadata getMetadata() {
        return this.metadata;
    }

    @JsonProperty("name")
    public String getName() {
        return this.name;
    }

    @JsonProperty("position")
    public Integer getPosition() {
        return this.position;
    }

    @JsonProperty("stack")
    @Nullable
    public String getStack() {
        return this.stack;
    }

    @JsonProperty("state")
    @Nullable
    public BuildpackState getState() {
        return this.state;
    }

    public boolean equals(Object another) {
        if (this == another) {
            return true;
        } else {
            return another instanceof BuildpackResource && this.equalTo((BuildpackResource)another);
        }
    }

    private boolean equalTo(BuildpackResource another) {
        return this.createdAt.equals(another.createdAt) && this.id.equals(another.id) && this.links.equals(another.links) && Objects.equals(this.updatedAt, another.updatedAt) && this.enabled.equals(another.enabled) && Objects.equals(this.filename, another.filename) && this.locked.equals(another.locked) && Objects.equals(this.metadata, another.metadata) && this.name.equals(another.name) && this.position.equals(another.position) && Objects.equals(this.stack, another.stack) && this.state.equals(another.state);
    }

    public int hashCode() {
        int h = 5381;
        h += (h << 5) + this.createdAt.hashCode();
        h += (h << 5) + this.id.hashCode();
        h += (h << 5) + this.links.hashCode();
        h += (h << 5) + Objects.hashCode(this.updatedAt);
        h += (h << 5) + this.enabled.hashCode();
        h += (h << 5) + Objects.hashCode(this.filename);
        h += (h << 5) + this.locked.hashCode();
        h += (h << 5) + Objects.hashCode(this.metadata);
        h += (h << 5) + this.name.hashCode();
        h += (h << 5) + this.position.hashCode();
        h += (h << 5) + Objects.hashCode(this.stack);
        h += (h << 5) + this.state.hashCode();
        return h;
    }

    public String toString() {
        return "BuildpackResource{createdAt=" + this.createdAt + ", id=" + this.id + ", links=" + this.links + ", updatedAt=" + this.updatedAt + ", enabled=" + this.enabled + ", filename=" + this.filename + ", locked=" + this.locked + ", metadata=" + this.metadata + ", name=" + this.name + ", position=" + this.position + ", stack=" + this.stack + ", state=" + this.state + "}";
    }

    /** @deprecated */
    @Deprecated
    @JsonCreator(
            mode = Mode.DELEGATING
    )
    static BuildpackResource fromJson(Json json) {
        Builder builder = builder();
        if (json.createdAt != null) {
            builder.createdAt(json.createdAt);
        }

        if (json.id != null) {
            builder.id(json.id);
        }

        if (json.links != null) {
            builder.putAllLinks(json.links);
        }

        if (json.updatedAt != null) {
            builder.updatedAt(json.updatedAt);
        }

        if (json.enabled != null) {
            builder.enabled(json.enabled);
        }

        if (json.filename != null) {
            builder.filename(json.filename);
        }

        if (json.locked != null) {
            builder.locked(json.locked);
        }

        if (json.metadata != null) {
            builder.metadata(json.metadata);
        }

        if (json.name != null) {
            builder.name(json.name);
        }

        if (json.position != null) {
            builder.position(json.position);
        }

        if (json.stack != null) {
            builder.stack(json.stack);
        }

        if (json.state != null) {
            builder.state(json.state);
        }

        return builder.build();
    }

    public static Builder builder() {
        return new Builder();
    }

    private static <K, V> Map<K, V> createUnmodifiableMap(boolean checkNulls, boolean skipNulls, Map<? extends K, ? extends V> map) {
        switch (map.size()) {
            case 0:
                return Collections.emptyMap();
            case 1:
                Map.Entry<? extends K, ? extends V> e = (Map.Entry)map.entrySet().iterator().next();
                K k = e.getKey();
                V v = e.getValue();
                if (checkNulls) {
                    Objects.requireNonNull(k, "key");
                    Objects.requireNonNull(v, "value");
                }

                if (!skipNulls || k != null && v != null) {
                    return Collections.singletonMap(k, v);
                }

                return Collections.emptyMap();
            default:
                Map<K, V> linkedMap = new LinkedHashMap(map.size());
                if (!skipNulls && !checkNulls) {
                    linkedMap.putAll(map);
                    return Collections.unmodifiableMap(linkedMap);
                } else {
                    Iterator var9 = map.entrySet().iterator();

                    while(true) {
                        Object kk;
                        Object vv;
                        while(true) {
                            if (!var9.hasNext()) {
                                return Collections.unmodifiableMap(linkedMap);
                            }

                            Map.Entry<? extends K, ? extends V> ee = (Map.Entry)var9.next();
                            kk = ee.getKey();
                            vv = ee.getValue();
                            if (skipNulls) {
                                if (kk == null || vv == null) {
                                    continue;
                                }
                                break;
                            }

                            if (checkNulls) {
                                Objects.requireNonNull(kk, "key");
                                Objects.requireNonNull(vv, "value");
                            }
                            break;
                        }

                        linkedMap.put((K) kk, (V) vv);
                    }
                }
        }
    }

    @Generated(
            from = "_BuildpackResource",
            generator = "Immutables"
    )
    public static final class Builder {
        private static final long INIT_BIT_CREATED_AT = 1L;
        private static final long INIT_BIT_ID = 2L;
        private static final long INIT_BIT_ENABLED = 4L;
        private static final long INIT_BIT_LOCKED = 8L;
        private static final long INIT_BIT_NAME = 16L;
        private static final long INIT_BIT_POSITION = 32L;
        /* state nullable 수정 */
        //private static final long INIT_BIT_STATE = 64L;
        private long initBits;
        private String createdAt;
        private String id;
        private Map<String, Link> links;
        private String updatedAt;
        private Boolean enabled;
        private String filename;
        private Boolean locked;
        private Metadata metadata;
        private String name;
        private Integer position;
        private String stack;
        private BuildpackState state;

        private Builder() {
            /* state nullable 수정 */
            //this.initBits = 127L;
            this.initBits = 63L;
            this.links = new LinkedHashMap();
        }

        public final Builder from(Buildpack instance) {
            Objects.requireNonNull(instance, "instance");
            this.from((Object)instance);
            return this;
        }

        public final Builder from(Resource instance) {
            Objects.requireNonNull(instance, "instance");
            this.from((Object)instance);
            return this;
        }

        public final Builder from(BuildpackResource instance) {
            Objects.requireNonNull(instance, "instance");
            this.from((Object)instance);
            return this;
        }

        public final Builder from(_BuildpackResource instance) {
            Objects.requireNonNull(instance, "instance");
            this.from((Object)instance);
            return this;
        }

        private void from(Object object) {
            if (object instanceof Buildpack) {
                Buildpack instance = (Buildpack)object;
                Metadata metadataValue = instance.getMetadata();
                if (metadataValue != null) {
                    this.metadata(metadataValue);
                }

                String stackValue = instance.getStack();
                if (stackValue != null) {
                    this.stack(stackValue);
                }

                String filenameValue = instance.getFilename();
                if (filenameValue != null) {
                    this.filename(filenameValue);
                }

                this.name(instance.getName());
                this.position(instance.getPosition());
                this.state(instance.getState());
                this.locked(instance.getLocked());
                this.enabled(instance.getEnabled());
            }

            if (object instanceof Resource) {
                Resource instance = (Resource)object;
                this.createdAt(instance.getCreatedAt());
                this.putAllLinks(instance.getLinks());
                this.id(instance.getId());
                String updatedAtValue = instance.getUpdatedAt();
                if (updatedAtValue != null) {
                    this.updatedAt(updatedAtValue);
                }
            }

        }

        @JsonProperty("created_at")
        public final Builder createdAt(String createdAt) {
            this.createdAt = (String)Objects.requireNonNull(createdAt, "createdAt");
            this.initBits &= -2L;
            return this;
        }

        @JsonProperty("guid")
        public final Builder id(String id) {
            this.id = (String)Objects.requireNonNull(id, "id");
            this.initBits &= -3L;
            return this;
        }

        public final Builder link(String key, Link value) {
            this.links.put(key, value);
            return this;
        }

        public final Builder link(Map.Entry<String, ? extends Link> entry) {
            String k = (String)entry.getKey();
            Link v = (Link)entry.getValue();
            this.links.put(k, v);
            return this;
        }

        @JsonProperty("links")
        public final Builder links(Map<String, ? extends Link> entries) {
            this.links.clear();
            return this.putAllLinks(entries);
        }

        public final Builder putAllLinks(Map<String, ? extends Link> entries) {
            Iterator var2 = entries.entrySet().iterator();

            while(var2.hasNext()) {
                Map.Entry<String, ? extends Link> e = (Map.Entry)var2.next();
                String k = (String)e.getKey();
                Link v = (Link)e.getValue();
                this.links.put(k, v);
            }

            return this;
        }

        @JsonProperty("updated_at")
        public final Builder updatedAt(@Nullable String updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        @JsonProperty("enabled")
        public final Builder enabled(Boolean enabled) {
            this.enabled = (Boolean)Objects.requireNonNull(enabled, "enabled");
            this.initBits &= -5L;
            return this;
        }

        @JsonProperty("filename")
        public final Builder filename(@Nullable String filename) {
            this.filename = filename;
            return this;
        }

        @JsonProperty("locked")
        public final Builder locked(Boolean locked) {
            this.locked = (Boolean)Objects.requireNonNull(locked, "locked");
            this.initBits &= -9L;
            return this;
        }

        @JsonProperty("metadata")
        public final Builder metadata(@Nullable Metadata metadata) {
            this.metadata = metadata;
            return this;
        }

        @JsonProperty("name")
        public final Builder name(String name) {
            this.name = (String)Objects.requireNonNull(name, "name");
            this.initBits &= -17L;
            return this;
        }

        @JsonProperty("position")
        public final Builder position(Integer position) {
            this.position = (Integer)Objects.requireNonNull(position, "position");
            this.initBits &= -33L;
            return this;
        }

        @JsonProperty("stack")
        public final Builder stack(@Nullable String stack) {
            this.stack = stack;
            return this;
        }

        @JsonProperty("state")
        public final Builder state(BuildpackState state) {
            /* state nullable 수정 */
            //this.state = (BuildpackState)Objects.requireNonNull(state, "state");
            //this.initBits &= -65L;
            this.state = state;
            return this;
        }

        public BuildpackResource build() {
            if (this.initBits != 0L) {
                throw new IllegalStateException(this.formatRequiredAttributesMessage());
            } else {
                return new BuildpackResource(this);
            }
        }

        private String formatRequiredAttributesMessage() {
            List<String> attributes = new ArrayList();
            if ((this.initBits & 1L) != 0L) {
                attributes.add("createdAt");
            }

            if ((this.initBits & 2L) != 0L) {
                attributes.add("id");
            }

            if ((this.initBits & 4L) != 0L) {
                attributes.add("enabled");
            }

            if ((this.initBits & 8L) != 0L) {
                attributes.add("locked");
            }

            if ((this.initBits & 16L) != 0L) {
                attributes.add("name");
            }

            if ((this.initBits & 32L) != 0L) {
                attributes.add("position");
            }

            /* state nullable 수정 */
            //if ((this.initBits & 64L) != 0L) {
            //    attributes.add("state");
            //}

            return "Cannot build BuildpackResource, some of required attributes are not set " + attributes;
        }
    }

    /** @deprecated */
    @Deprecated
    @JsonDeserialize
    @JsonAutoDetect(
            fieldVisibility = Visibility.NONE
    )
    @Generated(
            from = "_BuildpackResource",
            generator = "Immutables"
    )
    static final class Json extends _BuildpackResource {
        String createdAt;
        String id;
        Map<String, Link> links = Collections.emptyMap();
        String updatedAt;
        Boolean enabled;
        String filename;
        Boolean locked;
        Metadata metadata;
        String name;
        Integer position;
        String stack;
        BuildpackState state;

        Json() {
        }

        @JsonProperty("created_at")
        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        @JsonProperty("guid")
        public void setId(String id) {
            this.id = id;
        }

        @JsonProperty("links")
        public void setLinks(Map<String, Link> links) {
            this.links = links;
        }

        @JsonProperty("updated_at")
        public void setUpdatedAt(@Nullable String updatedAt) {
            this.updatedAt = updatedAt;
        }

        @JsonProperty("enabled")
        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        @JsonProperty("filename")
        public void setFilename(@Nullable String filename) {
            this.filename = filename;
        }

        @JsonProperty("locked")
        public void setLocked(Boolean locked) {
            this.locked = locked;
        }

        @JsonProperty("metadata")
        public void setMetadata(@Nullable Metadata metadata) {
            this.metadata = metadata;
        }

        @JsonProperty("name")
        public void setName(String name) {
            this.name = name;
        }

        @JsonProperty("position")
        public void setPosition(Integer position) {
            this.position = position;
        }

        @JsonProperty("stack")
        public void setStack(@Nullable String stack) {
            this.stack = stack;
        }

        @JsonProperty("state")
        public void setState(BuildpackState state) {
            this.state = state;
        }

        public String getCreatedAt() {
            throw new UnsupportedOperationException();
        }

        public String getId() {
            throw new UnsupportedOperationException();
        }

        public Map<String, Link> getLinks() {
            throw new UnsupportedOperationException();
        }

        public String getUpdatedAt() {
            throw new UnsupportedOperationException();
        }

        public Boolean getEnabled() {
            throw new UnsupportedOperationException();
        }

        public String getFilename() {
            throw new UnsupportedOperationException();
        }

        public Boolean getLocked() {
            throw new UnsupportedOperationException();
        }

        public Metadata getMetadata() {
            throw new UnsupportedOperationException();
        }

        public String getName() {
            throw new UnsupportedOperationException();
        }

        public Integer getPosition() {
            throw new UnsupportedOperationException();
        }

        public String getStack() {
            throw new UnsupportedOperationException();
        }

        public BuildpackState getState() {
            throw new UnsupportedOperationException();
        }
    }
}
