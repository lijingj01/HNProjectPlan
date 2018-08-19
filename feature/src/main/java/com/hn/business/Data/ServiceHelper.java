package com.hn.business.Data;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/*远程服务调用类
 * */
public class ServiceHelper {

    private String nameSpace = "http://tempuri.org/";
    private String endPoint = "https://ihomeapptest3.nw-sc.com:8034/DataService.asmx";
    private Context context;

    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String toHexString(byte[] b) { //String to byte
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    public String mmd5(String s) {
        try { // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            return toHexString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    //region 计划操作方法
    public boolean AddPlanEntity(ProjectPlanEntity item, String strUserCode) {

        boolean isAdd = false;
        String methodName = "AddProjectPlan";
        String soapAction = nameSpace + "/" + methodName + "/";
        // 命名空间     String nameSpace = "http://tempuri.org/";
        // 调用的方法名称     String methodName = "HelloWorld";
        // EndPoint     String endPoint = "http://192.168.16.39:1215/WebService.asmx";
        // SOAP Action     String soapAction = "http://tempuri.org//HelloWorld/";
        // 指定WebService的命名空间和调用的方法名

        SoapObject rpc = new SoapObject(nameSpace, methodName);

        //string strUserCode,string strTitle,string strContent,string strBeginDate, string strEndDate
        // 设置需调用WebService接口需要传入的参数
        rpc.addProperty("strUserCode", strUserCode);
        rpc.addProperty("strTitle", item.getPlanTitle());
        rpc.addProperty("strContent", item.getPlanContent());
        rpc.addProperty("strBeginDate", item.GetBeginDateString());
        rpc.addProperty("strEndDate", item.GetEndDateString());

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = rpc;

        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        (new MarshalBase64()).register(envelope);

        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);
        HttpTransportSE transport = new HttpTransportSE(endPoint);
        transport.debug = true;
        try {

            // 调用WebService
            transport.call(soapAction, envelope);
            if (envelope.getResponse() != null) {
//                System.out.println(envelope.getResponse());
                isAdd = Boolean.valueOf(envelope.getResponse().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isAdd;
    }

    public List<ProjectPlanEntity> GetUserProjectPlanList(String strUserCode) {
        List<ProjectPlanEntity> items = new ArrayList<>();

        String methodName = "GetUserProjectPlans";
        String soapAction = nameSpace + "/" + methodName + "/";

        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用WebService接口需要传入的参数
        rpc.addProperty("strUserCode", strUserCode);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = rpc;

        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        (new MarshalBase64()).register(envelope);

        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);
        HttpTransportSE transport = new HttpTransportSE(endPoint);
        transport.debug = true;
        try {

            // 调用WebService
            transport.call(soapAction, envelope);
            if (envelope.getResponse() != null) {
//                System.out.println(envelope.getResponse());
                String strJsonList = String.valueOf(envelope.getResponse());

                //region 将Json数据转换成实体对象
                LoadJsonList(items, strJsonList);
                //endregion
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    public ProjectPlanEntity GetProjectPlanEntity(Integer iPlanId) {
        ProjectPlanEntity item = new ProjectPlanEntity();

        String methodName = "GetProjectPlanEntity";
        String soapAction = nameSpace + "/" + methodName + "/";

        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用WebService接口需要传入的参数
        rpc.addProperty("iPlanId", iPlanId);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = rpc;

        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        (new MarshalBase64()).register(envelope);

        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);
        HttpTransportSE transport = new HttpTransportSE(endPoint);
        transport.debug = true;
        try {

            // 调用WebService
            transport.call(soapAction, envelope);
            if (envelope.getResponse() != null) {
//                System.out.println(envelope.getResponse());
                String strJsonList = String.valueOf(envelope.getResponse());

                //region 将Json数据转换成实体对象
                item = JsonToPlanEntity(strJsonList);
                //endregion
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return item;
    }
    //endregion

    //region 计划进度操作方法
    public boolean AddPlanPercentageEntity(PlanPercentageEntity item, UserInfoEntity myUser) {
        boolean isAdd = false;
        String methodName = "AddPlanPercentage";
        String soapAction = nameSpace + "/" + methodName + "/";
        // 命名空间     String nameSpace = "http://tempuri.org/";
        // 调用的方法名称     String methodName = "HelloWorld";
        // EndPoint     String endPoint = "http://192.168.16.39:1215/WebService.asmx";
        // SOAP Action     String soapAction = "http://tempuri.org//HelloWorld/";
        // 指定WebService的命名空间和调用的方法名

        SoapObject rpc = new SoapObject(nameSpace, methodName);

        //string strUserCode,string strTitle,string strContent,string strBeginDate, string strEndDate
        // 设置需调用WebService接口需要传入的参数
        rpc.addProperty("iPlanId", item.getPlanId());
        rpc.addProperty("strUserCode", myUser.getUserCode());
        rpc.addProperty("iEndPercentage", item.getEndPercentage());
        rpc.addProperty("isEnd", item.isEnd());
        rpc.addProperty("strRemark", item.getRemark());
        rpc.addProperty("strPwdCode", myUser.getPwdCode());

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = rpc;

        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        (new MarshalBase64()).register(envelope);

        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);
        HttpTransportSE transport = new HttpTransportSE(endPoint);
        transport.debug = true;
        try {

            // 调用WebService
            transport.call(soapAction, envelope);
            if (envelope.getResponse() != null) {
                isAdd = Boolean.valueOf(envelope.getResponse().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isAdd;
    }
    //endregion

    //region 用户登陆操作方法
    private void LoadJsonList(List<ProjectPlanEntity> items, String strJsonList) {
        try {
            JSONArray jsonArray = new JSONArray(strJsonList);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int iId = jsonObject.getInt("Id");
                String strTitle = jsonObject.getString("PlanTitle");
                String strContent = jsonObject.getString("PlanContent");
                String strBDate = jsonObject.getString("PlanBeginDate");
                String strEDate = jsonObject.getString("PlanEndDate");
                boolean isEnd = jsonObject.getBoolean("IsEnd");
                int iEndPercentage = jsonObject.getInt("EndPercentage");
                int iTimeUsePercentage = jsonObject.getInt("TimeUsePercentage");
                String strUserCode = jsonObject.getString("UserCode");
                String strAddTime = jsonObject.getString("AddTime");
                int pIndex = (i + 1) * 10;
                ProjectPlanEntity item = new ProjectPlanEntity(iId, strTitle, strContent, strBDate, strEDate, isEnd, iEndPercentage, iTimeUsePercentage, strUserCode, strAddTime);

                items.add(item);
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    private ProjectPlanEntity JsonToPlanEntity(String strJson) {
        try {
            JSONObject jsonObject = new JSONObject(strJson);
            int iId = jsonObject.getInt("Id");
            String strTitle = jsonObject.getString("PlanTitle");
            String strContent = jsonObject.getString("PlanContent");
            String strBDate = jsonObject.getString("PlanBeginDate");
            String strEDate = jsonObject.getString("PlanEndDate");
            boolean isEnd = jsonObject.getBoolean("IsEnd");
            int iEndPercentage = jsonObject.getInt("EndPercentage");
            int iTimeUsePercentage = jsonObject.getInt("TimeUsePercentage");
            String strUserCode = jsonObject.getString("UserCode");
            String strAddTime = jsonObject.getString("AddTime");

            ProjectPlanEntity item = new ProjectPlanEntity(iId, strTitle, strContent, strBDate, strEDate, isEnd, iEndPercentage, iTimeUsePercentage, strUserCode, strAddTime);

            return item;

        } catch (JSONException ex) {
//            ex.printStackTrace();
            return new ProjectPlanEntity();
        }
    }


    private boolean LoginToDB(String strLoginUser, String strUserJson) {
        //判断用户是否登陆成功，并将登陆系统保存到数据库
        try {
            boolean isLogin = false;

            JSONObject jsonObject = new JSONObject(strUserJson);
            int iId = jsonObject.getInt("Id");
            String strUserCode = jsonObject.getString("UserCode");
            String strNickName = jsonObject.getString("NickName");
            String strUserName = jsonObject.getString("UserName");
            String strWXCode = jsonObject.getString("WXCode");
            String strPwdCode = jsonObject.getString("PwdCode");
            String strMobilePhone = jsonObject.getString("MobilePhone");

            if (strUserCode.equals(strLoginUser)) {
                UserInfoEntity item = new UserInfoEntity(iId, strUserCode, strNickName, strUserName, strPwdCode);
                item.setWXCode(strWXCode);
                item.setMobilePhone(strMobilePhone);
                isLogin = true;
                //region 将登录账号数据写入本地数据库随时调用
                UserInLocal(item);
                //endregion
            }


            return isLogin;

        } catch (JSONException ex) {
            return false;
        }
    }

    //region 用户登陆信息的本地保存和读取
    private void UserInLocal(UserInfoEntity info) {
        SharedPreferences.Editor edit = context.getSharedPreferences("mypsd", context.MODE_PRIVATE).edit();
        edit.putString("USER_CODE", info.getUserCode());
        edit.putString("USER_NAME", info.getUserName());
        edit.putString("USER_NICK", info.getNickName());
        edit.putString("USER_PWD", info.getPwdCode());
        edit.commit();
    }

    /**
     * 获取本地存储的用户登陆信息
     */
    public UserInfoEntity LocalToUser(Context mContext) {
        SharedPreferences shared = mContext.getSharedPreferences("mypsd", mContext.MODE_PRIVATE);
        int iId = 0;
        String strUserCode = shared.getString("USER_CODE", "");
        String strNickName = shared.getString("USER_NAME", "");
        String strUserName = shared.getString("USER_NICK", "");
        String strPwdCode = shared.getString("USER_PWD", "");
        UserInfoEntity item = new UserInfoEntity(iId, strUserCode, strNickName, strUserName, strPwdCode);
        return item;
    }

    //endregion


    public boolean UserLoginSystem(String strUserCode, String strUserPwd, Context mContext) {
        this.context = mContext;
        boolean isLogin = false;
        String methodName = "UserLogin";
        String soapAction = nameSpace + "/" + methodName +"/";
        // 命名空间     String nameSpace = "http://tempuri.org/";
        // 调用的方法名称     String methodName = "HelloWorld";
        // EndPoint     String endPoint = "http://192.168.16.39:1215/WebService.asmx";
        // SOAP Action     String soapAction = "http://tempuri.org//HelloWorld/";
        // 指定WebService的命名空间和调用的方法名

        SoapObject rpc = new SoapObject(nameSpace, methodName);

        String strMD5PWd = mmd5(strUserPwd);
        // 设置需调用WebService接口需要传入的参数
        rpc.addProperty("strUserCode", strUserCode);
        rpc.addProperty("strPwdCode", strMD5PWd);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = rpc;

        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        (new MarshalBase64()).register(envelope);

        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);
        HttpTransportSE transport = new HttpTransportSE(endPoint);
        transport.debug = true;
        try {

            // 调用WebService
            transport.call(soapAction, envelope);
            if (envelope.getResponse() != null) {
//
                String strReturn = envelope.getResponse().toString();
                isLogin = LoginToDB(strUserCode, strReturn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isLogin;
    }

    //endregion
}
