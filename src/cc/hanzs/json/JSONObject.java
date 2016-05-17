package cc.hanzs.json;

/**
 *
 * @author 韩占山
 */
public final class JSONObject extends java.util.TreeMap<String, Object> implements Cloneable, java.io.Serializable {

    private JSONObject() {
    }
    protected Object parent = null;
    private String path_s = null;
    private static final JSONObject i_JSON = new JSONObject();

    protected void set(final JSONTokener x) throws JSONException {
        this.clear();
        char c;
        String key;
        Object value;

        if (x.nextClean() != '{') {
            throw new JSONException("JSON文本必需以'{'开始！");
        }
        for (;;) {
            c = x.nextClean();
            switch (c) {
                case 0:
                    throw new JSONException("JSON文本必需以'}'结束！");
                case '}':
                    return;
                default:
                    x.back();
                    key = x.nextValue(null).toString();
            }

            /*
             * The key is followed by ':'. We will also tolerate '=' or '=>'.
             */
            c = x.nextClean();
            if (c == '=') {
                if (x.next() != '>') {
                    x.back();
                }
            } else if (c != ':') {
                throw new JSONException("Expected a ':' after a key");
            }
            value = x.nextValue(this);
            if (value != null) {
                super.put(key, value);
            }

            /*
             * Pairs are separated by ','. We will also tolerate ';'.
             */
            switch (x.nextClean()) {
                case ';':
                case ',':
                    if (x.nextClean() == '}') {
                        return;
                    }
                    x.back();
                    break;
                case '}':
                    return;
                default:
                    throw new JSONException("Expected a ',' or '}'");
            }
        }
    }

    protected void setPath(Object root) {
        this.forEach((key, value) -> {
            if (value instanceof JSONPath) {
                ((JSONPath) value).root = root;
                ((JSONPath) value).i当前 = JSONObject.this;
            } else if (value instanceof JSONObject) {
                ((cc.hanzs.json.JSONObject) value).setPath(root);
            } else if (value instanceof JSONArray) {
                ((cc.hanzs.json.JSONArray) value).setPath(root);
            }
        });
    }

    /**
     * @param ci_s 待解析文本
     * @return 解析生成的org.hzs.JSONObject
     */
    public JSONObject set(final String ci_s) throws JSONException {
        if (ci_s == null) {
            return null;
        }
        set(JSONTokener.d副本(ci_s));
        setPath(this);
        return this;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected static boolean testValidity(final Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Double) {
            if (((Double) o).isInfinite() || ((Double) o).isNaN()) {
//                ci_error.g添加错误信息("JSON不允许非有限数");
//                throw ci_error;
                return false;
            }
        } else if (o instanceof Float) {
            if (((Float) o).isInfinite() || ((Float) o).isNaN()) {
//                ci_error.g添加错误信息("JSON不允许非有限数");
//                throw ci_error;
                return false;
            }
        }
        return true;
    }

    /**
     * @param key 待加入的键，为空时不加入
     * @param value 待加入的值，为空时不加入
     * @return 加入的值
     */
    @Override
    public cc.hanzs.json.JSONObject put(final String key, final Object value) {
        if (key == null) {
            return null;
        }

        if (value == null) {
            super.remove(key);
            return null;
        }

        if (testValidity(value)) {
            super.put(key, value);
        }
        return this;
    }

    public cc.hanzs.json.JSONObject put(final Object... KeyValue_s) {
        if (KeyValue_s.length % 2 != 0) {
            return this;
        }
        for (int jji_i = 0; jji_i < KeyValue_s.length; jji_i += 2) {
            this.put(KeyValue_s[jji_i].toString(), KeyValue_s[jji_i + 1]);
        }
        return this;
    }

    /**
     * @param key 待加入的键，为空时不加入
     * @param value 待加入的值，为空时不加入
     * @return 加入的值
     */
    public cc.hanzs.json.JSONObject put(final String key, final java.util.Date value) {
        if (key == null) {
            return null;
        }
        if (value == null) {
            super.remove(key);
            return this;
        }
        super.put(key, value.getTime());
        return this;
    }

    /**
     * @param key 待加入的键，为空时不加入
     * @param value 待加入的值，为空时不加入
     * @return 加入的值
     */
    public cc.hanzs.json.JSONObject put(final String key, final java.util.Calendar value) {
        if (key == null) {
            return null;
        }
        if (value == null) {
            super.remove(key);
            return this;
        }
        super.put(key, value.getTimeInMillis());
        return this;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @since
     */
    private static String quote(final String string) {
        if (string == null || string.length() == 0) {
            return "\"\"";
        }

        char b;
        char c = 0;
        int i;
        int len = string.length();
        StringBuilder sb = new StringBuilder(len + 4);
        String t;

        sb.append('"');
        for (i = 0; i < len; i += 1) {
            b = c;
            c = string.charAt(i);
            switch (c) {
                case '\\':
                case '"':
                    sb.append('\\');
                    sb.append(c);
                    break;
                case '/':
                    if (b == '<') {
                        sb.append('\\');
                    }
                    sb.append(c);
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                default:
                    if (c < ' ') {
                        t = "000" + Integer.toHexString(c);
                        sb.append("\\u").append(t.substring(t.length() - 4));
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append('"');
        return sb.toString();
    }

    /**
     *
     * @param key 为空时抛出错误
     * @return 返回取得的值；若key不存在，则返回空值。
     */
    public Object get(final String key) {
        if (key == null) {
            return null;
        }
        if (super.containsKey(key)) {
            return super.get(key);
        } else {
            return null;
        }
    }

    /**
     *
     * @param key 为空时抛出错误
     * @return 返回取得的值；若key不存在，则返回空值。
     */
    public Byte getByte(final String key) {
        Object o = get(key);
        if (o == null) {
            return null;
        }
        if (o instanceof Number) {
            return ((Number) o).byteValue();
        }
        return Byte.valueOf(o.toString());
    }

    /**
     *
     * @param key 为空时抛出错误
     * @return 返回取得的值；若key不存在，则返回空值。
     */
    public Boolean getBoolean(final String key) {
        Object o = get(key);
        if (o == null) {
            return null;
        }
        if (o.equals(Boolean.FALSE)
                || (o instanceof String && ((String) o).equalsIgnoreCase("false"))
                || (o instanceof Number && !((Number) o).equals(0))) {
            return false;
        } else if (o.equals(Boolean.TRUE)
                || (o instanceof String && ((String) o).equalsIgnoreCase("true"))) {
            return true;
        }
        return null;
    }

    /**
     *
     * @param key 为空时抛出错误
     * @return 返回取得的值；若key不存在，则返回空值。
     */
    public Double getDouble(final String key) {
        Object o = get(key);
        try {
            if (o == null) {
                return null;
            }
            if (o instanceof Number) {
                return ((Number) o).doubleValue();
            }
            return Double.valueOf(o.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     *
     * @param key 为空时抛出错误
     * @return 返回取得的值；若key不存在，则返回空值。
     */
    public Float getFloat(final String key) {
        Object o = get(key);
        try {
            if (o == null) {
                return null;
            }
            if (o instanceof Number) {
                return ((Number) o).floatValue();
            }
            return Float.valueOf(o.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public java.util.Date getDate(final String key) {
        Object o;

        o = get(key);
        if (o == null) {
            return null;
        }
        if (o instanceof java.util.Date) {
            return (java.util.Date) o;
        }
        if (o instanceof Number) {
            return new java.util.Date(((Number) o).longValue());
        }
        if (o instanceof String) {
            return new java.util.Date((String) o);
        }
        return null;
    }

    public java.util.Calendar getCalendar(final String key) {
        Object o;
        java.util.Calendar value;

        o = get(key);
        if (o == null) {
            return null;
        }
        if (o instanceof java.util.Calendar) {
            return (java.util.Calendar) o;
        }
        if (o instanceof java.util.Date) {
            value = java.util.Calendar.getInstance();
            value.setTimeInMillis(((java.util.Date) o).getTime());
            return value;
        }
        if (o instanceof Number) {
            value = java.util.Calendar.getInstance();
            value.setTimeInMillis(((Number) o).longValue());
            return value;
        }
        if (o instanceof String) {
            value = java.util.Calendar.getInstance();
            value.setTimeInMillis((new java.util.Date((String) o)).getTime());
            return value;
        }
        return null;
    }

    /**
     *
     * @param key 为空时抛出错误
     * @return 返回取得的值；若key不存在，则返回空值。
     */
    public java.math.BigDecimal getBigDecimal(final String key) {
        Object o;
        try {
            o = get(key);
            if (o == null) {
                return null;
            }
            if (o instanceof java.math.BigDecimal) {
                return (java.math.BigDecimal) o;
            } else if (o instanceof Number) {
                return new java.math.BigDecimal(o.toString());
            } else if (o instanceof String) {
                if (o.equals("")) {
                    return java.math.BigDecimal.ZERO;
                } else {
                    return new java.math.BigDecimal((String) o);
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
        }
    }

    /**
     *
     * @param key 为空时抛出错误
     * @return 返回取得的值；若key不存在，则返回空值。
     */
    public java.math.BigInteger getBigInteger(final String key) {
        Object o;
        try {
            o = get(key);
            if (o == null) {
                return null;
            }
            if (o instanceof java.math.BigInteger) {
                return (java.math.BigInteger) o;
            } else if (o instanceof Number) {
                return new java.math.BigInteger(o.toString());
            } else if (o instanceof String) {
                if (o.equals("")) {
                    return java.math.BigInteger.ZERO;
                } else {
                    return new java.math.BigInteger((String) o);
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
        }
    }

    /**
     *
     * @param key 为空时抛出错误
     * @return 返回取得的值；若key不存在，则返回空值。
     */
    public Integer getInt(final String key) {
        Object o = get(key);
        if (o == null) {
            return null;
        }
        if (o instanceof Number) {
            return ((Number) o).intValue();
        }
        return Integer.valueOf(o.toString());
    }

    /**
     *
     * @param key 为空时抛出错误
     * @return 返回取得的值；若key不存在，则返回空值。
     */
    public JSONArray getJSONArray(final String key) {
        Object o = get(key);
        if (o == null) {
            return null;
        }
        if (o instanceof JSONArray) {
            return (JSONArray) o;
        }
        return null;
    }

    /**
     *
     * @param key 为空时抛出错误
     * @return 返回取得的值；若key不存在，则返回空值。
     */
    public JSONObject getJSONObject(final String key) {
        Object o = get(key);
        if (o == null) {
            return null;
        }
        if (o instanceof JSONObject) {
            return (JSONObject) o;
        }
        return null;
    }

    /**
     *
     * @param key 为空时抛出错误
     * @return 返回取得的值；若key不存在，则返回空值。
     */
    public Long getLong(final String key) {
        Object o = get(key);
        if (o == null) {
            return null;
        }
        if (o instanceof Number) {
            return ((Number) o).longValue();
        }
        return Long.valueOf(o.toString());
    }

    /**
     *
     * @param key 为空时抛出错误
     * @return 返回取得的值；若key不存在，则返回空值。
     */
    public Short getShort(final String key) {
        Object o = get(key);
        if (o == null) {
            return null;
        }
        if (o instanceof Number) {
            return ((Number) o).shortValue();
        }
        return Short.valueOf(o.toString());
    }

    /**
     *
     * @param key 为空时抛出错误
     * @return 返回取得的值；若key不存在，则返回空值。
     */
    public String getString(final String key) {
        Object o = get(key);
        if (o == null) {
            return null;
        }
        return o.toString();
    }

    /**
     * @param key 文本型
     * @return 返回JSONPath型的值；若key不存在，则返回空值。
     */
    public JSONPath getPath(final String key) {
        Object o = get(key);
        if (o == null) {
            return null;
        }
        return (JSONPath) o;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public java.util.Iterator keys() {
        return super.keySet().iterator();
    }

    private static String numberToString(final Number n) {
        if (n == null) {
            return null;
        }
        if (testValidity(n)) {

// Shave off trailing zeros and decimal point, if possible.
            String s = n.toString();
            if (s.indexOf('.') > 0 && s.indexOf('e') < 0 && s.indexOf('E') < 0) {
                while (s.endsWith("0")) {
                    s = s.substring(0, s.length() - 1);
                }
                if (s.endsWith(".")) {
                    s = s.substring(0, s.length() - 1);
                }
            }
            return s;
        } else {
            return null;
        }
    }

    /**
     * @param value
     * @param path
     * @return
     * @since
     */
    protected static String valueToString(final Object value, final String path) {
        if (value == null) {
            return "null";
        }
        if (value instanceof java.math.BigDecimal) {
            return ((java.math.BigDecimal) value).toPlainString();
        }
        if (value instanceof java.util.Date) {
            return Long.toString(((java.util.Date) value).getTime());
        }
        if (value instanceof java.util.Calendar) {
            return Long.toString(((java.util.Calendar) value).getTimeInMillis());
        }
        if (value instanceof Number) {
            return numberToString((Number) value);
        }
        if (value instanceof String) {
            return quote(value.toString());
        }
        if (value instanceof cc.hanzs.json.JSONObject) {
            return ((cc.hanzs.json.JSONObject) value).toString(path);
        }
        if (value instanceof cc.hanzs.json.JSONArray) {
            return ((cc.hanzs.json.JSONArray) value).toString(path);
        }
        return value.toString();
    }

    /**
     *
     * @return 序列化後的文本
     */
    @Override
    public String toString() {
        clearPath();
        return toString("/");
    }

    protected void clearPath() {
        if (path_s == null) {
            return;
        }
        path_s = null;
        this.forEach((key, value) -> {
            if (value instanceof cc.hanzs.json.JSONObject) {
                ((cc.hanzs.json.JSONObject) value).clearPath();
            }
            if (value instanceof cc.hanzs.json.JSONArray) {
                ((cc.hanzs.json.JSONArray) value).clearPath();
            }
        });
    }

    protected String toString(final String path) {
        if (path_s != null) {
            return path_s;
        }
        path_s = path;
        StringBuilder sb = new StringBuilder();

        this.forEach((key, value) -> {
            sb.append(',');
            if (path.endsWith("/")) {
                value = valueToString(super.get(key), path + key);
            } else {
                value = valueToString(super.get(key), path + "/" + key);
            }
            if (value != null) {
                sb.append(quote(key));
                sb.append(':');
                sb.append(value);
            }
        });
        if (sb.length() > 0) {
            sb.replace(0, 1, "{");
            sb.append('}');
            return sb.toString();
        } else {
            return "{}";
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     *
     * 移除自身条目及仔项内的条目，彻底清理佔用资源。用于释放
     */
    @Override
    public void clear() {
        this.forEach((key, value) -> {
            if (value instanceof JSONArray) {
                ((JSONArray) value).clear();
            } else if (value instanceof JSONObject) {
                ((JSONObject) value).clear();
            }
        });
        super.clear();
    }

    /**
     *
     * 移除自身的条目，但不移除仔项内的项目，用于清理缓冲
     */
    public void removeall() {
        super.clear();
    }

    /**
     *
     * @return 新的JSON对象，建议用此方式，加快程序运行速度
     */
    public static cc.hanzs.json.JSONObject d副本() {
        return (cc.hanzs.json.JSONObject) i_JSON.clone();
    }

    public static cc.hanzs.json.JSONObject d副本(final String ci_s) {
        if (ci_s == null) {
            return null;
        }
        cc.hanzs.json.JSONObject jd;
        try {
            jd = (cc.hanzs.json.JSONObject) i_JSON.clone();
            jd.set(JSONTokener.d副本(ci_s));
            jd.setPath(jd);
        } catch (JSONException ex) {
            return null;
        }
        return jd;
    }

    public int length() {
        return this.size();
    }
}
