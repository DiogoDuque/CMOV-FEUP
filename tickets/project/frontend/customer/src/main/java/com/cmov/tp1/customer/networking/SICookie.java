package com.cmov.tp1.customer.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.HttpCookie;

/**
 * Copied from https://gist.github.com/manishk3008/2a2373c6c155a5df6326
 *
 * Taken from https://github.com/loopj/android-async-http
 * A simple wrapper for {@link HttpCookie} to work with {@link SiCookieStore}
 * Gives power of serialization-deserialization to {@link HttpCookie}
 * @author Manish
 *
 */
public class SICookie implements Serializable {

    private HttpCookie mHttpCookie;
    /**
     *
     */
    private static final long serialVersionUID = 2532101328282342578L;

    /**
     *
     */
    public SICookie(HttpCookie cookie) {
        this.mHttpCookie = cookie;
    }

    public HttpCookie getCookie() {
        return mHttpCookie;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(mHttpCookie.getName());
        out.writeObject(mHttpCookie.getValue());
        out.writeObject(mHttpCookie.getComment());
        out.writeObject(mHttpCookie.getCommentURL());
        out.writeBoolean(mHttpCookie.getDiscard());
        out.writeObject(mHttpCookie.getDomain());
        out.writeLong(mHttpCookie.getMaxAge());
        out.writeObject(mHttpCookie.getPath());
        out.writeObject(mHttpCookie.getPortlist());
        out.writeBoolean(mHttpCookie.getSecure());
        out.writeInt(mHttpCookie.getVersion());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        String name = (String) in.readObject();
        String value = (String) in.readObject();
        mHttpCookie = new HttpCookie(name, value);
        mHttpCookie.setComment((String) in.readObject());
        mHttpCookie.setCommentURL((String) in.readObject());
        mHttpCookie.setDiscard(in.readBoolean());
        mHttpCookie.setDomain((String) in.readObject());
        mHttpCookie.setMaxAge(in.readLong());
        mHttpCookie.setPath((String) in.readObject());
        mHttpCookie.setPortlist((String) in.readObject());
        mHttpCookie.setSecure(in.readBoolean());
        mHttpCookie.setVersion(in.readInt());
    }

}