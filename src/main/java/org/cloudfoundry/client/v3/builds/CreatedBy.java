//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.cloudfoundry.client.v3.builds;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.cloudfoundry.Nullable;
import org.immutables.value.Generated;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* email, id, name nullable 수정 */
@Generated(
        from = "_CreatedBy",
        generator = "Immutables"
)
public final class CreatedBy extends _CreatedBy {
    private final String email;
    private final String id;
    private final String name;

    private CreatedBy(Builder builder) {
        this.email = builder.email;
        this.id = builder.id;
        this.name = builder.name;
    }

    @JsonProperty("email")
    @Nullable
    public String getEmail() {
        return this.email;
    }

    @JsonProperty("guid")
    @Nullable
    public String getId() {
        return this.id;
    }

    @JsonProperty("name")
    @Nullable
    public String getName() {
        return this.name;
    }

    public boolean equals(Object another) {
        if (this == another) {
            return true;
        } else {
            return another instanceof CreatedBy && this.equalTo((CreatedBy)another);
        }
    }

    private boolean equalTo(CreatedBy another) {
        return this.email.equals(another.email) && this.id.equals(another.id) && this.name.equals(another.name);
    }

    public int hashCode() {
        int h = 5381;
        h += (h << 5) + this.email.hashCode();
        h += (h << 5) + this.id.hashCode();
        h += (h << 5) + this.name.hashCode();
        return h;
    }

    public String toString() {
        return "CreatedBy{email=" + this.email + ", id=" + this.id + ", name=" + this.name + "}";
    }

    /** @deprecated */
    @Deprecated
    @JsonCreator(
            mode = Mode.DELEGATING
    )
    static CreatedBy fromJson(Json json) {
        Builder builder = builder();
        if (json.email != null) {
            builder.email(json.email);
        }

        if (json.id != null) {
            builder.id(json.id);
        }

        if (json.name != null) {
            builder.name(json.name);
        }

        return builder.build();
    }

    public static Builder builder() {
        return new Builder();
    }

    @Generated(
            from = "_CreatedBy",
            generator = "Immutables"
    )
    public static final class Builder {
        /* email nullable 수정 */
        //private static final long INIT_BIT_EMAIL = 1L;
        /* id nullable 수정 */
        //private static final long INIT_BIT_ID = 2L;
        /* name nullable 수정 */
        //private static final long INIT_BIT_NAME = 4L;
        private long initBits;
        private String email;
        private String id;
        private String name;

        private Builder() {
            /* email, id, name nullable 수정 */
            //this.initBits = 7L;
            this.initBits = 0L;
        }

        public final Builder from(CreatedBy instance) {
            return this.from((_CreatedBy)instance);
        }

        final Builder from(_CreatedBy instance) {
            Objects.requireNonNull(instance, "instance");
            this.email(instance.getEmail());
            this.id(instance.getId());
            this.name(instance.getName());
            return this;
        }

        @JsonProperty("email")
        public final Builder email(String email) {
            /* email nullable 수정 */
            //this.email = (String)Objects.requireNonNull(email, "email");
            //this.initBits &= -2L;
            this.email = email;
            return this;
        }

        @JsonProperty("guid")
        public final Builder id(String id) {
            /* id nullable 수정 */
            //this.id = (String)Objects.requireNonNull(id, "id");
            //this.initBits &= -3L;
            this.id = id;
            return this;
        }

        @JsonProperty("name")
        public final Builder name(String name) {
            /* name nullable 수정 */
            //this.name = (String)Objects.requireNonNull(name, "name");
            //this.initBits &= -5L;
            this.name = name;
            return this;
        }

        public CreatedBy build() {
            if (this.initBits != 0L) {
                throw new IllegalStateException(this.formatRequiredAttributesMessage());
            } else {
                return new CreatedBy(this);
            }
        }

        private String formatRequiredAttributesMessage() {
            List<String> attributes = new ArrayList();
            /* email, id, name nullable 수정 */
            //if ((this.initBits & 1L) != 0L) {
                //attributes.add("email");
            //}

            //if ((this.initBits & 2L) != 0L) {
                //attributes.add("id");
            //}

            //if ((this.initBits & 4L) != 0L) {
                //attributes.add("name");
            //}

            return "Cannot build CreatedBy, some of required attributes are not set " + attributes;
        }
    }

    /** @deprecated */
    @Deprecated
    @JsonDeserialize
    @JsonAutoDetect(
            fieldVisibility = Visibility.NONE
    )
    @Generated(
            from = "_CreatedBy",
            generator = "Immutables"
    )
    static final class Json extends _CreatedBy {
        String email;
        String id;
        String name;

        Json() {
        }

        @JsonProperty("email")
        public void setEmail(String email) {
            this.email = email;
        }

        @JsonProperty("guid")
        public void setId(String id) {
            this.id = id;
        }

        @JsonProperty("name")
        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            throw new UnsupportedOperationException();
        }

        public String getId() {
            throw new UnsupportedOperationException();
        }

        public String getName() {
            throw new UnsupportedOperationException();
        }
    }
}
