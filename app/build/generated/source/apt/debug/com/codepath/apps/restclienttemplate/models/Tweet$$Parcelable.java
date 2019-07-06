
package com.codepath.apps.restclienttemplate.models;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.parceler.Generated;
import org.parceler.IdentityCollection;
import org.parceler.ParcelWrapper;
import org.parceler.ParcelerRuntimeException;

@Generated("org.parceler.ParcelAnnotationProcessor")
@SuppressWarnings({
    "unchecked",
    "deprecation"
})
public class Tweet$$Parcelable
    implements Parcelable, ParcelWrapper<com.codepath.apps.restclienttemplate.models.Tweet>
{

    private com.codepath.apps.restclienttemplate.models.Tweet tweet$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Creator<Tweet$$Parcelable>CREATOR = new Creator<Tweet$$Parcelable>() {


        @Override
        public Tweet$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new Tweet$$Parcelable(read(parcel$$2, new IdentityCollection()));
        }

        @Override
        public Tweet$$Parcelable[] newArray(int size) {
            return new Tweet$$Parcelable[size] ;
        }

    }
    ;

    public Tweet$$Parcelable(com.codepath.apps.restclienttemplate.models.Tweet tweet$$2) {
        tweet$$0 = tweet$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(tweet$$0, parcel$$0, flags, new IdentityCollection());
    }

    public static void write(com.codepath.apps.restclienttemplate.models.Tweet tweet$$1, android.os.Parcel parcel$$1, int flags$$0, IdentityCollection identityMap$$0) {
        int identity$$0 = identityMap$$0 .getKey(tweet$$1);
        if (identity$$0 != -1) {
            parcel$$1 .writeInt(identity$$0);
        } else {
            parcel$$1 .writeInt(identityMap$$0 .put(tweet$$1));
            parcel$$1 .writeLong(tweet$$1 .uid);
            parcel$$1 .writeString(tweet$$1 .createdAt);
            if (tweet$$1 .comments == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(1);
                parcel$$1 .writeInt(tweet$$1 .comments);
            }
            parcel$$1 .writeString(tweet$$1 .imageUrl);
            parcel$$1 .writeString(tweet$$1 .relativeDate);
            if (tweet$$1 .hasMedia == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(1);
                parcel$$1 .writeInt((tweet$$1 .hasMedia? 1 : 0));
            }
            if (tweet$$1 .isFavorited == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(1);
                parcel$$1 .writeInt((tweet$$1 .isFavorited? 1 : 0));
            }
            parcel$$1 .writeString(tweet$$1 .body);
            if (tweet$$1 .retweets == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(1);
                parcel$$1 .writeInt(tweet$$1 .retweets);
            }
            if (tweet$$1 .isRetweeted == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(1);
                parcel$$1 .writeInt((tweet$$1 .isRetweeted? 1 : 0));
            }
            com.codepath.apps.restclienttemplate.models.User$$Parcelable.write(tweet$$1 .user, parcel$$1, flags$$0, identityMap$$0);
            if (tweet$$1 .likes == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(1);
                parcel$$1 .writeInt(tweet$$1 .likes);
            }
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.codepath.apps.restclienttemplate.models.Tweet getParcel() {
        return tweet$$0;
    }

    public static com.codepath.apps.restclienttemplate.models.Tweet read(android.os.Parcel parcel$$3, IdentityCollection identityMap$$1) {
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$1 .containsKey(identity$$1)) {
            if (identityMap$$1 .isReserved(identity$$1)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return identityMap$$1 .get(identity$$1);
        } else {
            com.codepath.apps.restclienttemplate.models.Tweet tweet$$4;
            int reservation$$0 = identityMap$$1 .reserve();
            tweet$$4 = new com.codepath.apps.restclienttemplate.models.Tweet();
            identityMap$$1 .put(reservation$$0, tweet$$4);
            tweet$$4 .uid = parcel$$3 .readLong();
            tweet$$4 .createdAt = parcel$$3 .readString();
            int int$$0 = parcel$$3 .readInt();
            java.lang.Integer integer$$0;
            if (int$$0 < 0) {
                integer$$0 = null;
            } else {
                integer$$0 = parcel$$3 .readInt();
            }
            tweet$$4 .comments = integer$$0;
            tweet$$4 .imageUrl = parcel$$3 .readString();
            tweet$$4 .relativeDate = parcel$$3 .readString();
            int int$$1 = parcel$$3 .readInt();
            java.lang.Boolean boolean$$0;
            if (int$$1 < 0) {
                boolean$$0 = null;
            } else {
                boolean$$0 = (parcel$$3 .readInt() == 1);
            }
            tweet$$4 .hasMedia = boolean$$0;
            int int$$2 = parcel$$3 .readInt();
            java.lang.Boolean boolean$$1;
            if (int$$2 < 0) {
                boolean$$1 = null;
            } else {
                boolean$$1 = (parcel$$3 .readInt() == 1);
            }
            tweet$$4 .isFavorited = boolean$$1;
            tweet$$4 .body = parcel$$3 .readString();
            int int$$3 = parcel$$3 .readInt();
            java.lang.Integer integer$$1;
            if (int$$3 < 0) {
                integer$$1 = null;
            } else {
                integer$$1 = parcel$$3 .readInt();
            }
            tweet$$4 .retweets = integer$$1;
            int int$$4 = parcel$$3 .readInt();
            java.lang.Boolean boolean$$2;
            if (int$$4 < 0) {
                boolean$$2 = null;
            } else {
                boolean$$2 = (parcel$$3 .readInt() == 1);
            }
            tweet$$4 .isRetweeted = boolean$$2;
            User user$$0 = com.codepath.apps.restclienttemplate.models.User$$Parcelable.read(parcel$$3, identityMap$$1);
            tweet$$4 .user = user$$0;
            int int$$5 = parcel$$3 .readInt();
            java.lang.Integer integer$$2;
            if (int$$5 < 0) {
                integer$$2 = null;
            } else {
                integer$$2 = parcel$$3 .readInt();
            }
            tweet$$4 .likes = integer$$2;
            com.codepath.apps.restclienttemplate.models.Tweet tweet$$3 = tweet$$4;
            identityMap$$1 .put(identity$$1, tweet$$3);
            return tweet$$3;
        }
    }

}
