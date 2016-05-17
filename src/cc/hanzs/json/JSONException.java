package cc.hanzs.json;

public final class JSONException extends java.lang.RuntimeException implements Cloneable {

    public JSONException(final String message) {
        super("JSON：" + message);
    }
}
