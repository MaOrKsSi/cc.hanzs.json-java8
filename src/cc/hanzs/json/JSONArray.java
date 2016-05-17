package cc.hanzs.json;

public final class JSONArray extends java.util.LinkedList<Object> implements Cloneable, java.io.Serializable {

    private JSONArray() {
    }

    protected Object parent = null;
    private String path_s = null;
    private static final JSONArray i_ArrayJSON = new JSONArray();

    protected void set(final JSONTokener x) throws JSONException {
        this.clear();
        if (x.nextClean() != '[') {
            throw new JSONException("JSONArray text 必需以'['开始！");
        }
        if (x.nextClean() == ']') {
            return;
        }
        x.back();
        for (;;) {
            if (x.nextClean() == ',') {
                x.back();
                super.add(null);
            } else {
                x.back();
                super.add(x.nextValue(this));
            }
            switch (x.nextClean()) {
                case ';':
                case ',':
                    if (x.nextClean() == ']') {
                        return;
                    }
                    x.back();
                    break;
                case ']':
                    return;
                default:
                    throw new JSONException("Expected a ',' or ']'");
            }
        }
    }

    protected void setPath(Object root) {
        this.parallelStream().forEach((value) -> {
            if (value != null) {
                if (value instanceof cc.hanzs.json.JSONPath) {
                    ((JSONPath) value).root = root;
                    ((JSONPath) value).i当前 = JSONArray.this;
                } else if (value instanceof cc.hanzs.json.JSONObject) {
                    ((cc.hanzs.json.JSONObject) value).setPath(root);
                } else if (value instanceof cc.hanzs.json.JSONArray) {
                    ((cc.hanzs.json.JSONArray) value).setPath(root);
                }
            }
        });
    }

    public JSONArray set(final String ci_s) throws JSONException {
        if (ci_s == null) {
            return null;
        }
        set(JSONTokener.d副本(ci_s));
        setPath(this);
        return this;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public cc.hanzs.json.JSONArray put(final Object... value_s) {
        for (Object value : value_s) {
            this.put(value);
        }
        return this;
    }

    public cc.hanzs.json.JSONArray put(final Object value) {
        if (value == null) {
            super.add(null);
            return this;
        }

        if (value instanceof Double || value instanceof Float) {
            if (JSONObject.testValidity(value)) {
                super.add(value);
            } else {
                super.add(null);
            }
            return this;
        }

        super.add(value);
        return this;
    }

    public cc.hanzs.json.JSONArray put(final java.util.Date value) {
        if (value != null) {
            super.add(value.getTime());
        } else {
            super.add(null);
        }
        return this;
    }

    public cc.hanzs.json.JSONArray put(final java.util.Calendar value) {
        if (value != null) {
            super.add(value.getTimeInMillis());
        } else {
            super.add(null);
        }
        return this;
    }

    public cc.hanzs.json.JSONArray put(final int index, final Object value) {
        if (index < 0) {
            return this;
        }
        while (index >= size()) {
            super.add(null);
        }

        if (value == null) {
            super.set(index, null);
            return this;
        }

        if (value instanceof Double || value instanceof Float) {
            if (JSONObject.testValidity(value)) {
                super.set(index, value);
            } else {
                super.set(index, null);
            }
            return this;
        }

        super.set(index, value);
        return this;
    }

    public cc.hanzs.json.JSONArray put(final int index, final java.util.Date value) {
        if (index < 0) {
            return this;
        }
        while (index >= size()) {
            super.add(null);
        }
        if (value != null) {
            super.set(index, value.getTime());
        } else {
            super.set(index, null);
        }
        return this;
    }

    public cc.hanzs.json.JSONArray put(final int index, final java.util.Calendar value) {
        if (index < 0) {
            return this;
        }
        while (index >= size()) {
            super.add(null);
        }
        if (value != null) {
            super.set(index, value.getTimeInMillis());
        } else {
            super.set(index, null);
        }
        return this;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Object get(int index) {
        if (index < 0) {
            return null;
        } else if (index >= size()) {
            return null;
        } else {
            return super.get(index);
        }
    }

    public Boolean getBoolean(int index) {
        Object ji_o;

        ji_o = get(index);
        if (ji_o == null) {
            return null;
        }
        if (ji_o.equals(Boolean.FALSE) || (ji_o instanceof String && ((String) ji_o).equalsIgnoreCase("false"))) {
            return false;
        } else if (ji_o.equals(Boolean.TRUE) || (ji_o instanceof String && ((String) ji_o).equalsIgnoreCase("true"))) {
            return true;
        }
        return null;
    }

    public Float getFloat(int index) {
        Object o = get(index);
        if (o == null) {
            return null;
        }
        if (o instanceof Number) {
            return ((Number) o).floatValue();
        }
        return Float.valueOf(o.toString());
    }

    public Double getDouble(int index) {
        Object o = get(index);
        if (o == null) {
            return null;
        }
        if (o instanceof Number) {
            return ((Number) o).doubleValue();
        }
        return Double.valueOf(o.toString());
    }

    public java.util.Date getDate(int index) {
        Object o = get(index);
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

    public java.util.Calendar getCalendar(int index) {
        Object o = get(index);
        if (o == null) {
            return null;
        }
        if (o instanceof java.util.Calendar) {
            return (java.util.Calendar) o;
        }
        java.util.Calendar value;
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

    public java.math.BigDecimal getBigDecimal(int index) {
        Object o = get(index);
        try {
            if (o == null) {
                return null;
            }
            if (o instanceof java.math.BigDecimal) {
                return (java.math.BigDecimal) o;
            } else if (o instanceof Number) {
                return new java.math.BigDecimal(o.toString());
            } else if (o instanceof String) {
                return new java.math.BigDecimal((String) o);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public java.math.BigInteger getBigInteger(int index) {
        Object o = get(index);
        try {
            if (o == null) {
                return null;
            }
            if (o instanceof java.math.BigInteger) {
                return (java.math.BigInteger) o;
            } else if (o instanceof Number) {
                return new java.math.BigInteger(o.toString());
            } else if (o instanceof String) {
                return new java.math.BigInteger((String) o);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public Integer getInt(int index) {
        Object o = get(index);
        if (o == null) {
            return null;
        }
        if (o instanceof Number) {
            return ((Number) o).intValue();
        }
        return Integer.valueOf(o.toString());
    }

    public JSONArray getJSONArray(int index) {
        Object o = get(index);
        if (o == null) {
            return null;
        }
        if (o instanceof JSONArray) {
            return (JSONArray) o;
        }
        return null;
    }

    public JSONObject getJSONObject(int index) {
        Object o = get(index);
        if (o == null) {
            return null;
        }
        if (o instanceof JSONObject) {
            return (JSONObject) o;
        }
        return null;
    }

    public Long getLong(int index) {
        Object o = get(index);
        if (o == null) {
            return null;
        }
        if (o instanceof Number) {
            return ((Number) o).longValue();
        }
        return Long.valueOf(o.toString());
    }

    public Short getShort(int index) {
        Object o = get(index);
        if (o == null) {
            return null;
        }
        if (o instanceof Number) {
            return ((Number) o).shortValue();
        }
        return Short.valueOf(o.toString());
    }

    public String getString(int index) {
        Object o = get(index);
        if (o == null) {
            return null;
        }
        return o.toString();
    }

    public Byte getByte(int index) {
        Object o = get(index);
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
    public JSONPath getPath(final int index) {
        Object o = get(index);
        if (o == null) {
            return null;
        }
        return (JSONPath) o;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

        this.parallelStream().forEach((value) -> {
            if (value != null) {
                if (value instanceof cc.hanzs.json.JSONObject) {
                    ((cc.hanzs.json.JSONObject) value).clearPath();
                } else if (value instanceof cc.hanzs.json.JSONArray) {
                    ((cc.hanzs.json.JSONArray) value).clearPath();
                }
            }
        });
    }

    protected String toString(final String path) {
        if (path_s != null) {
            return path_s;
        }
        path_s = path;

        StringBuilder sb = new StringBuilder();
        int ji_i = 0;

        for (Object value : this) {
            sb.append(",");

            if (value == null) {
                sb.append("null");
            } else if (path.endsWith("/")) {
                sb.append(JSONObject.valueToString(value, path + ji_i));
            } else {
                sb.append(JSONObject.valueToString(value, path + "/" + ji_i));
            }
            ji_i++;
        }
        if (sb.length() > 0) {
            sb.replace(0, 1, "[");
            sb.append("]");
            return sb.toString();
        } else {
            return "[]";
        }
    }

    public JSONArray append(JSONArray ci_ArrayJSON) {
        int ji_i = ci_ArrayJSON.size();
        for (int ji1_i = 0; ji1_i < ji_i; ji1_i++) {
            super.add(ci_ArrayJSON.get(ji1_i));
        }
        return this;
    }

    /**
     *
     * 移除自身条目及仔项内的条目，彻底清理佔用资源。用于释放
     */
    @Override
    public void clear() {
        this.parallelStream().forEach((jd) -> {
            if (jd instanceof JSONArray) {
                ((JSONArray) jd).clear();
            } else if (jd instanceof JSONObject) {
                ((JSONObject) jd).clear();
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

    public static cc.hanzs.json.JSONArray d副本() {
        return (cc.hanzs.json.JSONArray) i_ArrayJSON.clone();
    }

    /**
     *
     * @param ci_s
     * @return 新的JSONArray对象，建议用此方式，加快程序运行速度
     */
    public static cc.hanzs.json.JSONArray d副本(final String ci_s) {
        if (ci_s == null) {
            return null;
        }
        cc.hanzs.json.JSONArray jd;
        try {
            jd = (cc.hanzs.json.JSONArray) i_ArrayJSON.clone();
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
