//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.cloudfoundry.client.v3;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Generated;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* data nullable 수정 */
@Generated(
        from = "_Lifecycle",
        generator = "Immutables"
)
public final class Lifecycle extends _Lifecycle {
    private final LifecycleData data;
    private final LifecycleType type;

    private Lifecycle(Builder builder) {
        this.data = builder.data;
        this.type = builder.type;
    }

    @JsonTypeInfo(
            use = Id.NAME,
            include = As.EXTERNAL_PROPERTY,
            property = "type"
    )
    @JsonSubTypes({@Type(
            name = "buildpack",
            value = BuildpackData.class
    ), @Type(
            name = "docker",
            value = DockerData.class
    ), @Type(
            name = "kpack",
            value = KpackData.class
    )})
    @JsonProperty("data")
    public LifecycleData getData() {
        return this.data;
    }

    @JsonProperty("type")
    public LifecycleType getType() {
        return this.type;
    }

    public boolean equals(Object another) {
        if (this == another) {
            return true;
        } else {
            return another instanceof Lifecycle && this.equalTo((Lifecycle)another);
        }
    }

    private boolean equalTo(Lifecycle another) {
        return this.data.equals(another.data) && this.type.equals(another.type);
    }

    public int hashCode() {
        int h = 5381;
        h += (h << 5) + this.data.hashCode();
        h += (h << 5) + this.type.hashCode();
        return h;
    }

    public String toString() {
        return "Lifecycle{data=" + this.data + ", type=" + this.type + "}";
    }

    /** @deprecated */
    @Deprecated
    @JsonCreator(
            mode = Mode.DELEGATING
    )
    static Lifecycle fromJson(Json json) {
        Builder builder = builder();
        if (json.data != null) {
            builder.data(json.data);
        }

        if (json.type != null) {
            builder.type(json.type);
        }

        return builder.build();
    }

    public static Builder builder() {
        return new Builder();
    }

    @Generated(
            from = "_Lifecycle",
            generator = "Immutables"
    )
    public static final class Builder {
        /* data nullable 수정 */
        //private static final long INIT_BIT_DATA = 1L;
        //private static final long INIT_BIT_TYPE = 2L;
        private static final long INIT_BIT_TYPE = 1L;
        private long initBits;
        private LifecycleData data;
        private LifecycleType type;

        private Builder() {
            /* data nullable 수정 */
            //this.initBits = 3L;
            this.initBits = 1L;
        }

        public final Builder from(Lifecycle instance) {
            return this.from((_Lifecycle)instance);
        }

        final Builder from(_Lifecycle instance) {
            Objects.requireNonNull(instance, "instance");
            this.data(instance.getData());
            this.type(instance.getType());
            return this;
        }

        @JsonTypeInfo(
                use = Id.NAME,
                include = As.EXTERNAL_PROPERTY,
                property = "type"
        )
        @JsonSubTypes({@Type(
                name = "buildpack",
                value = BuildpackData.class
        ), @Type(
                name = "docker",
                value = DockerData.class
        ), @Type(
                name = "kpack",
                value = KpackData.class
        )})
        @JsonProperty("data")
        public final Builder data(LifecycleData data) {
            /* data nullable 수정 */
            //this.data = (LifecycleData)Objects.requireNonNull(data, "data");
            //this.initBits &= -2L;
            this.data = data;
            return this;
        }

        @JsonProperty("type")
        public final Builder type(LifecycleType type) {
            this.type = (LifecycleType)Objects.requireNonNull(type, "type");
            /* data nullable 수정 */
            //this.initBits &= -3L;
            this.initBits &= -2L;
            return this;
        }

        public Lifecycle build() {
            if (this.initBits != 0L) {
                throw new IllegalStateException(this.formatRequiredAttributesMessage());
            } else {
                return new Lifecycle(this);
            }
        }

        private String formatRequiredAttributesMessage() {
            List<String> attributes = new ArrayList();
            /* data nullable 수정 */
            //if ((this.initBits & 1L) != 0L) {
            //    attributes.add("data");
            //}

            //if ((this.initBits & 2L) != 0L) {
            //    attributes.add("type");
            //}

            if ((this.initBits & 1L) != 0L) {
                attributes.add("type");
            }

            return "Cannot build Lifecycle, some of required attributes are not set " + attributes;
        }
    }

    /** @deprecated */
    @Deprecated
    @JsonDeserialize
    @JsonAutoDetect(
            fieldVisibility = Visibility.NONE
    )
    @Generated(
            from = "_Lifecycle",
            generator = "Immutables"
    )
    static final class Json extends _Lifecycle {
        LifecycleData data;
        LifecycleType type;

        Json() {
        }

        @JsonTypeInfo(
                use = Id.NAME,
                include = As.EXTERNAL_PROPERTY,
                property = "type"
        )
        @JsonSubTypes({@Type(
                name = "buildpack",
                value = BuildpackData.class
        ), @Type(
                name = "docker",
                value = DockerData.class
        ), @Type(
                name = "kpack",
                value = KpackData.class
        )})
        @JsonProperty("data")
        public void setData(LifecycleData data) {
            this.data = data;
        }

        @JsonProperty("type")
        public void setType(LifecycleType type) {
            this.type = type;
        }

        public LifecycleData getData() {
            throw new UnsupportedOperationException();
        }

        public LifecycleType getType() {
            throw new UnsupportedOperationException();
        }
    }
}
