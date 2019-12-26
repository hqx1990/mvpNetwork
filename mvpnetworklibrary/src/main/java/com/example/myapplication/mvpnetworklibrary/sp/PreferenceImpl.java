package com.example.myapplication.mvpnetworklibrary.sp;

import android.content.Context;
import android.content.SharedPreferences;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static com.example.myapplication.mvpnetworklibrary.sp.IPreference.DataType.INTEGER;


/**
 * Created by hqx on 2019/4/16 9:40
 * Description:
 * Passed parameters:
 * Warning:
 */
public class PreferenceImpl implements IPreference {
    private static IPreference iPreference;
    /**
     * 对象锁
     */
    private static final Object lock = new Object();

    /**
     * 获取IPreference对象 * @param context * @return
     */
    public static IPreference getPreference(Context context) {
        synchronized (lock) {
            if (iPreference == null) {
                initPreference(context, FILE_NAME);
            }
        }
        return iPreference;
    }

    /**
     * 获取IPreference对象 * @param context * @param fileName * @return
     */
    public static IPreference getPreference(Context context, String fileName) {
        synchronized (lock) {
            if (iPreference == null) {
                initPreference(context, fileName);
            }
        }
        return iPreference;
    }

    /**
     * 初始化IPreference对象 * @param context * @param fileName
     */
    private static synchronized void initPreference(Context context, String fileName) {
        if (iPreference == null) {
            iPreference = new PreferenceImpl(context, fileName);
        }
    }

    /**
     * 默认的文件名
     */
    private static final String FILE_NAME = "shared_preferences";
    private SharedPreferences preferences;

    /**
     * package private
     */
    PreferenceImpl(){}

    /**
     * Instantiates a new Pref manager. * * @param context the context
     * package private
     */
    PreferenceImpl(Context context) {
        preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Instantiates a new Pref manager. * * @param context the context * @param fileName the file name
     * package private
     */
    PreferenceImpl(Context context, String fileName) {
        preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    @Override
    public void put(String key, Object value) {
        SharedPreferences.Editor edit = preferences.edit();
        put(edit, key, value);
        edit.commit();
    }

    /**
     * 保存一个Map集合 * @param map
     */
    @Override
    public <T> void putAll(Map<String, T> map) {
        SharedPreferences.Editor edit = preferences.edit();
        for (Map.Entry<String, T> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            put(edit, key, value);
        }
        edit.commit();
    }

    @Override
    public void putAll(String key, List<String> list) {
        putAll(key, list, new ComparatorImpl());
    }

    @Override
    public void putAll(String key, List<String> list, Comparator<String> comparator) {
        Set<String> set = new TreeSet<>(comparator);
        for (String value : list) {
            set.add(value);
        }
        preferences.edit().putStringSet(key, set).commit();
    }

    /**
     * 根据key取出一个数据 * @param key 键
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String key, DataType type) {
        return (T) getValue(key, type);
    }

    @Override
    public Map<String, ?> getAll() {
        return preferences.getAll();
    }

    @Override
    public List<String> getAll(String key) {
        List<String> list = new ArrayList<String>();
        Set<String> set = get(key, DataType.STRING_SET);
        for (String value : set) {
            list.add(value);
        }
        return list;
    }

    @Override
    public void remove(String key) {
        preferences.edit().remove(key).commit();
    }

    @Override
    public void removeAll(List<String> keys) {
        SharedPreferences.Editor edit = preferences.edit();
        for (String k : keys) {
            edit.remove(k);
        }
        edit.commit();
    }

    @Override
    public void removeAll(String[] keys) {
        removeAll(Arrays.asList(keys));
    }

    @Override
    public boolean contains(String key) {
        return preferences.contains(key);
    }

    @Override
    public void clear() {
        preferences.edit().clear().commit();
    }

    @Override
    public String getString(String key) {
        return get(key, DataType.STRING);
    }

    @Override
    public String getString(String key, String def) {
        return preferences.getString(key, def);
    }

    @Override
    public float getFloat(String key) {
        return get(key, DataType.FLOAT);
    }

    @Override
    public int getInteger(String key) {
        return get(key, INTEGER);
    }

    @Override
    public long getLong(String key) {
        return get(key, DataType.LONG);
    }

    @Override
    public Set<String> getSet(String key) {
        return get(key, DataType.STRING_SET);
    }

    @Override
    public boolean getBoolean(String key) {
        return get(key, DataType.BOOLEAN);
    }

    @Override
    public double getDouble(String key) {
        return get(key, DataType.DOUBLE);
    }

    /**
     * 保存数据 * @param editor * @param key * @param obj
     */
    @SuppressWarnings("unchecked")
    private void put(SharedPreferences.Editor editor, String key, Object obj) { // key 不为null时再存入，否则不存储
        if (key != null) {
            if (obj instanceof Integer) {
                editor.putInt(key, (Integer) obj);
            } else if (obj instanceof Long) {
                editor.putLong(key, (Long) obj);
            } else if (obj instanceof Boolean) {
                editor.putBoolean(key, (Boolean) obj);
            } else if (obj instanceof Float) {
                editor.putFloat(key, (Float) obj);
            } else if (obj instanceof Set) {
                editor.putStringSet(key, (Set<String>) obj);
            } else if (obj instanceof String) {
                editor.putString(key, String.valueOf(obj));
            } else if (obj instanceof Double) {
                BigDecimal decimal = new BigDecimal(String.valueOf(obj));
                editor.putFloat(key,decimal.floatValue());
            }
        }
    }

    /**
     * 根据key和类型取出数据 * @param key * @return
     */
    private Object getValue(String key, DataType type) {
        switch (type) {
            case INTEGER:
                return preferences.getInt(key, 0);
            case FLOAT:
                return preferences.getFloat(key, -1f);
            case BOOLEAN:
                return preferences.getBoolean(key, false);
            case LONG:
                return preferences.getLong(key, -1L);
            case STRING:
                return preferences.getString(key, "");
            case STRING_SET:
                return preferences.getStringSet(key, null);
            case DOUBLE:
                float f = preferences.getFloat(key, -1f);
                BigDecimal decimal = new BigDecimal(String.valueOf(f));
                return decimal.doubleValue();
            default: // 默认取出String类型的数据
                return "";
        }
    }
}
