package com.ants.order.pay.fuyou.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;


/**
 * 富有支付-工具类
 *
 * @author Yueyang
 * @create 2020-11-09 19:30
 **/
public class FyPayUtil {

    /**
     * 【推荐】集合初始化时，指定集合初始值大小
     * 说明：HashMap使用HashMap(int initialCapacity)初始化，
     * 正例：initialCapacity=(需要存储的元素个数/负载因子)+1。注意负载因子(即loaderfactor)默认为0.75，
     * 如果暂时无法确定初始值大小，请设置为16(即默认值)。
     * 反例：HashMap需要放置1024个元素，由于没有设置容量初始大小，随着元素不断增加，容量7次被迫扩大，resize需要重建hash表，严重影响性能。
     */
    private final static Integer HASH_MAP_DEFAULT = 16;
    private final static Integer HASH_MAP_ZERO = 0;
    /**
     * 小蚂蚁商户私钥
     */
    @Value("${ANTS.INS_PRIVATE_KEY}")
    private static String INS_PRIVATE_KEY;
    /**
     * 小蚂蚁商户公钥
     */
    @Value("${ANTS.INS_PUBLIC_KEY}")
    private static String INS_PUBLIC_KEY;
    /**
     * 富有公钥
     */
    @Value("${FY.FY_PUBLIC_KEY}")
    private static String FY_PUBLIC_KEY;

    /**
     * 检查字段
     *
     * @param map 预处理订单参数
     * @return 处理结果
     */
    public static Map<String, String> paraFilter(Map<String, String> map) {

        if (map == null || map.size() <= 0) {
            return new HashMap<>(HASH_MAP_ZERO);
        }

        Map<String, String> result = new HashMap<>(HASH_MAP_DEFAULT);

        for (String key : map.keySet()) {
            String value = map.get(key);
            if ("sign".equalsIgnoreCase(key)) {
                continue;
            }
            if ((key.length() >= 8 && "reserved".equalsIgnoreCase(key.substring(0, 8)))) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    public static String createLinkString(Map<String, String> map) {

        List<String> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = map.get(key);

            String string;
            if (i == keys.size() - 1) {
                //拼接时，不包括最后一个&字符
                string = prestr + key + "=" + value;
            } else {
                string = prestr + key + "=" + value + "&";
            }
            prestr = string;
        }

        return prestr;
    }

    public static String getSign(Map<String, String> map) throws InvalidKeySpecException, SignatureException, NoSuchAlgorithmException, InvalidKeyException, IOException {

        Map<String, String> mapNew = paraFilter(map);

        String preSignStr = createLinkString(mapNew);

        System.out.println("==============================待签名字符串==============================\r\n" + preSignStr);

        String sign = FyPaySign.sign(preSignStr, INS_PRIVATE_KEY);

        sign = sign.replace("\r\n", "");

        System.out.println("==============================签名字符串==============================\r\n" + sign);

        return sign;
    }

    public static Boolean verifySign(Map<String, String> map, String sign) throws Exception {

        Map<String, String> mapNew = paraFilter(map);

        String preSignStr = createLinkString(mapNew);

        return FyPaySign.verify(preSignStr.getBytes("GBK"), FY_PUBLIC_KEY, sign);
    }

    public static Map<String, String> xmlStr2Map(String xmlStr) {
        Map<String, String> map = new HashMap<String, String>();
        Document doc;
        try {
            doc = DocumentHelper.parseText(xmlStr);
            Element resroot = doc.getRootElement();
            List<Element> children = resroot.elements();
            if (children != null && children.size() > 0) {
                for (Object o : children) {
                    Element child = (Element) o;
                    map.put(child.getName(), child.getStringValue().trim());
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return map;
    }
}