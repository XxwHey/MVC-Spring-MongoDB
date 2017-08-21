package MongoDB.common.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiexw on 2017/8/17.
 */
public class CommonUtils {

    public static void copyValue(Object source, Object target) {
        copyValue(source, target, true);
    }

    public static void copyValue(Object source, Object target, boolean isCopyNull) {
        Class<?> sc = source.getClass();
        Field[] sFields = sc.getDeclaredFields();

        Class<?> tc = target.getClass();
        Field[] tFields = tc.getDeclaredFields();

        Map<String, Object> sMap = new HashMap<>();
        try {
            for (Field sf : sFields) {
                sMap.put(sf.getName(), sf.get(source));
            }

            for (Field tf: tFields) {
                tf.setAccessible(true);
                Object sValue = sMap.get(tf.getName());
                if (isCopyNull) {
                    if (sValue != null) {
                        tf.set(target, sValue);
                    }
                } else {
                    tf.set(target, sValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object castValue(String type, String value) {
        Object object = null;
        switch (type) {
            case "Integer":
                object = Integer.parseInt(value);
                break;
            case "String":
                object = String.valueOf(value);
                break;
        }
        return object;
    }
}
