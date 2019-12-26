package com.example.myapplication.mvpnetworklibrary.sp;

import android.content.Context;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public interface IPreference {
    IPreferenceHolder prefHolder = new IPreferenceHolder();

    /**
     * IPreference的持有类
     */
    class IPreferenceHolder {
        private IPreferenceHolder(){}
        /**
         * 获得一个新创建的IPreference对象 * @param context 上下文对象 * @param fileName 文件名 * @return
         */
        public IPreference newPreference(Context context, String fileName) {
            return new PreferenceImpl(context, fileName);
        }

        /**
         * 获取一个IPreference对象 * @param context 上下文对象 * @return
         */
        public IPreference getPreference(Context context) {
            return PreferenceImpl.getPreference(context);
        }

        /**
         * 获取一个IPreference对象 * @param context 上下文对象 * @param fileName 文件名 * @return
         */
        public IPreference getPreference(Context context, String fileName) {
            return PreferenceImpl.getPreference(context, fileName);
        }
    }

    /**
     * 保存单个信息进sp
     * 可以根据不同数据类型进行保存
     * @param key 键值对key值
     * @param value 键值对value值
     */
    <T> void put(String key, T value);

    /**
     * 批量保存信息
     * 保存一个map集合
     * @param map key-value 集合
     * @param <T> object类型，可以根据不同数据类型进行保存
     */
    <T> void putAll(Map<String, T> map);

    /**
     * 批量保存信息
     * 保存一个列表进sp
     * @param key 键值对key值
     * @param list 键值对value值
     */
    void putAll(String key, List<String> list);

    /**
     * 保存一个List集合，并且自定保存顺序 * @param key * @param list * @param comparator
     */
    void putAll(String key, List<String> list, Comparator<String> comparator);

    /**
     * 根据key取出一个数据 * @param key 键
     */
    <T> T get(String key, DataType type);

    /**
     * 遍历sp文件中所有的key-value值
     * @return Map<String,?>
     */
    Map<String, ?> getAll();

    /**
     * 获取sp中保存的list数据
     * @param key 键值对key值
     * @return List<String>
     */
    List<String> getAll(String key);

    /**
     * 删除一条数据
     * @param key 键值对key值
     */
    void remove(String key);

    /**
     * 删除多条数据，拼接所有的key值为list
     * @param keys 键值对key值
     */
    void removeAll(List<String> keys);

    /**
     * 删除多条数据，拼接所有的key值为String[]
     * @param keys 键值对key值
     */
    void removeAll(String[] keys);

    /**
     * 判断sp文件中是否含有该key值
     * @param key 键值对key值
     * @return boolean类型
     */
    boolean contains(String key);

    /**
     * 删除sp文件
     */
    void clear();

    /**
     * 获取String类型数据
     * @param key 键值对key值
     * @return String类型
     */
    String getString(String key);

    /**
     * 获取String类型数据
     * @param key 键值对key值
     * @param def 默认值
     * @return String类型
     */
    String getString(String key, String def);

    /**
     * 获取float类型数据
     * @param key 键值对key值
     * @return float类型
     */
    float getFloat(String key);

    /**
     * 获取int类型数据
     * @param key 键值对key值
     * @return int类型
     */
    int getInteger(String key);

    /**
     * 获取long类型数据
     * @param key 键值对key值
     * @return long类型
     */
    long getLong(String key);

    /**
     * 获取Set<String>，当批量保存List<String>类型的时候，其实是将list存放在set中保存
     * @param key 键值对key值
     * @return Set<String>
     */
    Set<String> getSet(String key);

    /**
     * 获取boolean类型数据
     * @param key 键值对key值
     * @return boolean类型
     */
    boolean getBoolean(String key);

    /**
     * 获取double类型数据
     * @param key 键值对key值
     * @return double类型
     */
    double getDouble(String key);

    /**
     * 枚举：存储或取出的数据类型
     */
    enum DataType {
        INTEGER, LONG, BOOLEAN, FLOAT, STRING, STRING_SET, DOUBLE
    }
}
