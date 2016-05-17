
public class 优化入口 {

    public static void main(String[] args) throws CloneNotSupportedException, cc.hanzs.json.JSONException {
        cc.hanzs.json.JSONObject jd_JSON = null;
        jd_JSON = cc.hanzs.json.JSONObject.d副本();
        jd_JSON = cc.hanzs.json.JSONObject.d副本("{a:'sdfdsfa',c:{c:../},d:./a}");
        jd_JSON.set("{a:'sdfdsfa',c:{c:../},d:./a}");
        String ji_s = jd_JSON.toString();
        System.out.println(ji_s);
        jd_JSON.set(ji_s);
        ji_s = jd_JSON.toString();
        System.out.println(ji_s);
        cc.hanzs.json.JSONPath ddd = jd_JSON.getPath("d");
        Object dddd = ddd.get();
        jd_JSON.put("e", jd_JSON.getJSONObject("c"));
        ji_s = jd_JSON.toString();
        System.out.println(ji_s);
        jd_JSON.length();

        jd_JSON.clear();
        jd_JSON.removeall();
        jd_JSON.put(null, new Object());
        jd_JSON.put(null, new java.util.Date());
        jd_JSON.put(null, java.util.Calendar.getInstance());
        jd_JSON.get("");
        jd_JSON.getBigDecimal("");
        jd_JSON.getBigInteger("");
        jd_JSON.getBoolean("");
        jd_JSON.getByte("");
        jd_JSON.getCalendar("");
        jd_JSON.getDate("");
        jd_JSON.getDouble("");
        jd_JSON.getFloat("");
        jd_JSON.getInt("");
        jd_JSON.getJSONArray("");
        jd_JSON.getJSONObject("");
        jd_JSON.getLong("");
        jd_JSON.getShort("");
        jd_JSON.getString("");
        jd_JSON.toString();
        jd_JSON.keys();
        //
        cc.hanzs.json.JSONArray jd_ArrayJSON = null;
        cc.hanzs.json.JSONArray.d副本();
        cc.hanzs.json.JSONArray.d副本("[]");
        jd_ArrayJSON.set("[]");
        jd_ArrayJSON.append(jd_ArrayJSON);
        jd_ArrayJSON.toString();
        jd_ArrayJSON.length();
        jd_ArrayJSON.clear();
        jd_ArrayJSON.removeall();
        jd_ArrayJSON.put(null, null, null, null, null, null);
        jd_ArrayJSON.put(new Object());
        jd_ArrayJSON.put(new java.util.Date());
        jd_ArrayJSON.put(java.util.Calendar.getInstance());
        jd_ArrayJSON.put(1, new Object());
        jd_ArrayJSON.put(1, new java.util.Date());
        jd_ArrayJSON.put(1, java.util.Calendar.getInstance());
        jd_ArrayJSON.get(1);
        jd_ArrayJSON.getBigDecimal(1);
        jd_ArrayJSON.getBigInteger(1);
        jd_ArrayJSON.getBoolean(1);
        jd_ArrayJSON.getByte(1);
        jd_ArrayJSON.getCalendar(1);
        jd_ArrayJSON.getDate(1);
        jd_ArrayJSON.getDouble(1);
        jd_ArrayJSON.getFloat(1);
        jd_ArrayJSON.getInt(1);
        jd_ArrayJSON.getJSONArray(1);
        jd_ArrayJSON.getJSONObject(1);
        jd_ArrayJSON.getLong(1);
        jd_ArrayJSON.getShort(1);
        jd_ArrayJSON.getString(1);
        jd_ArrayJSON.getPath(1);
    }
}
