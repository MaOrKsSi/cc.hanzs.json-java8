package cc.hanzs.json;

public final class JSONPath implements Cloneable {

    private java.util.ArrayList<String> path_List = null;
    private static final JSONPath i_JSONPath = new JSONPath();
    protected Object root, i当前;

    private JSONPath() {
    }

    public static JSONPath d副本(final String cpath_s) {
        String[] jipath_ss;
        try {
            JSONPath jd = (JSONPath) i_JSONPath.clone();
            jd.path_List = new java.util.ArrayList<String>();

            jipath_ss = cpath_s.split("/");//用分隔符“/”将路径分割成文本组

            //将文本组添加到集合，首个“.”添加到集合，其余“.”不添加到集合
            for (int jji_i = 0; jji_i < jipath_ss.length; jji_i++) {
                if ((jji_i == 0 || !jipath_ss[jji_i].equals(".")) && !jipath_ss[jji_i].equals("")) {
                    jd.path_List.add(jipath_ss[jji_i]);
                }
            }

            //清理path_List内的“..”
            boolean ji_b = true;
            while (ji_b) {
                ji_b = jd.jg清理path_List内的上级路径();
            }

            return jd;
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }

    private boolean jg清理path_List内的上级路径() {
        boolean ji被清理_b = false;
        for (int jji_i = path_List.size() - 1; jji_i > 0; jji_i--) {
            String jji_s = path_List.get(jji_i);
            if (!jji_s.equals("..")) {
                continue;
            }
            String jji1_s = path_List.get(jji_i - 1);
            if (jji1_s.equals(".")) {
                path_List.remove(jji_i - 1);
                ji被清理_b = true;
            } else if (jji1_s.equals("..")) {
            } else {
                path_List.remove(jji_i);
                path_List.remove(jji_i - 1);
                ji被清理_b = true;
            }
        }
        return ji被清理_b;
    }

    public Object get() {
        Object ji1;

        String ji_s = path_List.get(0);
        if (ji_s.equals(".") || ji_s.equals("..")) {
            ji1 = i当前;
        } else {
            ji1 = root;
        }

        for (String jji_s : path_List) {
            if (ji1 instanceof JSONObject) {
                if (jji_s.equals(".")) {
                } else if (jji_s.equals("..")) {
                    ji1 = ((JSONObject) ji1).parent;
                } else {
                    ji1 = ((JSONObject) ji1).get(jji_s);
                }
            } else if (ji1 instanceof JSONArray) {
                if (jji_s.equals(".")) {
                } else if (jji_s.equals("..")) {
                    ji1 = ((JSONArray) ji1).parent;
                } else {
                    ji1 = ((JSONArray) ji1).get(Integer.valueOf(jji_s));
                }
            } else {
                break;
            }
        }
        return ji1;
    }

    /**
     *
     * @return 序列化後的文本
     */
    @Override
    public String toString() {
        StringBuilder ji_S = new StringBuilder(path_List.size() * 32);
        for (String jji_s : path_List) {
            ji_S.append(jji_s);
            ji_S.append("/");
        }
        int ji_i = ji_S.length();
        if (ji_i > 0) {
            ji_S.delete(ji_i - 1, ji_i);
        }
        String ji_s = ji_S.toString();
        if (!ji_s.equals(".") && !ji_s.equals("..") && !ji_s.startsWith("../") && !ji_s.startsWith("./")) {
            ji_s = "/" + ji_s;
        }
        return ji_s;
    }
}
