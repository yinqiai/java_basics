package com.transsnet.string;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Iterator;

/**
 * author: JK
 * date: 2019/6/19 17:21
 * description:优化BaseParseJson类的解析
 */

public class BaseParseJson2 extends UDF {

    public String parseJsonObject(JSONObject str) {
        Object[] keys = str.keySet().toArray();
        //如果json中只用一个kv对
        if(keys.length == 1)
            return  str.getString(keys[0].toString());
        else{
            JSONObject resObj = null;
            try {
                resObj = new JSONObject();
                for (Object key : keys) {
                    String value = str.getString(key.toString());
                    if(value.startsWith("{") && value.endsWith("}")){
                        JSONObject valueJson = new JSONObject(value);
                        String innerkey2 = valueJson.keySet().toArray()[0].toString();
                        value = valueJson.getString(innerkey2);
                    }
                    resObj.put(key.toString(),value);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return  null;
            }
            return  resObj.toString();
        }
    }

    public  String evaluate(String json){


      if(json ==null || json.equals(""))  return null;

        //用于接收最终处理后的json结果
        JSONObject resJson = new JSONObject();

        try {
            Object o = new JSONTokener(json).nextValue();
            if(o instanceof JSONObject){
              JSONObject  jsonObj =   (JSONObject)o;
                Iterator<String> keys = jsonObj.keys();
                //去除掉o_id
                keys.next();
                while (keys.hasNext()){
                    String key = keys.next();
                    String value = jsonObj.getString(key);
                    Object o1 =null;

                    //如果字段為空JSONTOKener會報錯
                    if (!value.equals(""))
                        o1 = new JSONTokener(value).nextValue();

                    //如果返回的是json对象
                    if(o1 instanceof  JSONObject){
                        JSONObject jsono1 = (JSONObject)o1;
                       value =parseJsonObject(jsono1);
                    }
                    //如果返回的是json数组
                    if(o1 instanceof JSONArray && value.startsWith("[{")&& value.endsWith("}]")){
                        //用于接收处理过后的json数组
                        JSONArray desc2JSONArr = new JSONArray();

                        JSONArray jsonArro1 =(JSONArray)o1;
                        for (int i = 0; i < jsonArro1.length(); i++) {
                            JSONObject innerJsonObj = new JSONObject();
                            JSONObject jsonObject = jsonArro1.getJSONObject(i);
                            //System.out.println(jsonObject);
                            //keys2是json数组内部的keys
                            Iterator<String> keys2 = jsonObject.keys();
                            while (keys2.hasNext()) {
                                String key2 = keys2.next();
                                String value2 = jsonObject.getString(key2);
                                Object o2 = null;

                                //如果字段為空JSONTOKener會報錯
                                if (!value2.equals(""))
                                    o2 = new JSONTokener(value2).nextValue();

                                if (o2 instanceof JSONObject) {
                                    JSONObject  jsono2 = (JSONObject)o2;
                                    value2 = parseJsonObject(jsono2);
                                    innerJsonObj.put(key2, value2);
                                }
                                //将其他的json字段放入
                                innerJsonObj.put(key2, value2);
                            }
                            value = desc2JSONArr.put(innerJsonObj).toString();
                        }
                    }
                      resJson.put(key,value);
                }

            }
        } catch (JSONException e) {
           e.printStackTrace();
            return null;
        }
        return  resJson.toString();
    }


    public static void main(String[] args) {
        String line = "{\"_id\":{\"$oid\":\"5f70e0756b2f0700095a36a8\"},\"sub_pay_id\":\"060120200927000010001856539026543400_1\",\"pay_id\":\"060120200927000010001856539026543400\",\"pay_status\":1,\"end_sub_pay\":true,\"pay_amount\":{\"$numberLong\":\"0\"},\"loyalty_amount\":{\"$numberLong\":\"30\"},\"loyalty_point\":{\"$numberLong\":\"30\"},\"pay_type\":30,\"currency\":\"NGN\",\"extend_field\":\"{\\\"countryCode\\\":\\\"NG\\\",\\\"orderNo\\\":\\\"506212996482\\\",\\\"awardType\\\":\\\"1\\\",\\\"loyaltyAmount\\\":30,\\\"loyaltyPoint\\\":30,\\\"bizInfo\\\":{},\\\"memberId\\\":\\\"95EC31E585154A2D9660865EAF7BCA7C\\\",\\\"subTransType\\\":\\\"1\\\",\\\"systemType\\\":\\\"web\\\",\\\"transType\\\":\\\"13\\\",\\\"destinationAccount\\\":\\\"98be59276c37403fb355b019dd47d684\\\",\\\"remark\\\":\\\"签到积分发放\\\",\\\"serviceId\\\":\\\"14001214\\\",\\\"transferType\\\":\\\"1\\\",\\\"businessChannel\\\":\\\"Palmpoints rewards\\\",\\\"outOrderNo\\\":\\\"506212996482\\\",\\\"outOrderType\\\":\\\"Promotion\\\",\\\"appSource\\\":1,\\\"application\\\":\\\"marketing\\\",\\\"businessType\\\":\\\"checkIn\\\"}\",\"pay_channel\":\"PALMPOINT\",\"create_time\":{\"$date\":\"2020-09-27T18:56:53.266Z\"},\"update_time\":{\"$date\":\"2020-09-27T18:56:53.319Z\"},\"query_time\":{\"$date\":\"2020-09-27T19:02:53.294Z\"},\"record_account_flag\":0,\"retry_count\":0,\"record_flag\":0,\"recive_fee\":{\"$numberLong\":\"0\"},\"recive_tax_fee\":{\"$numberLong\":\"0\"},\"platform_fee\":{\"$numberLong\":\"0\"},\"platform_tax_fee\":{\"$numberLong\":\"0\"},\"recive_amount\":{\"$numberLong\":\"30\"},\"trans_type\":\"06\",\"app_source\":0,\"coupon_amount\":{\"$numberLong\":\"0\"},\"channel_transaction_id\":\"1418565330120200927706491897\"}";



        String json = new BaseParseJson2().evaluate(line);
        System.out.println(json);

    }
}
