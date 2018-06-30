package com.hn.business.Data;

import android.content.Context;
import android.security.NetworkSecurityPolicy;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import as.hn.com.hnprojectplan.feature.MainActivity;
import as.hn.com.hnprojectplan.feature.ProjectPlanEntity;

/*远程服务调用类
 * */
public class ServiceHelper {

    private String nameSpace = "http://tempuri.org/";
    private String endPoint = "https://ihomeapptest3.nw-sc.com:8034/DataService.asmx";
    private String UserCode = "lijingj";

    public boolean AddPlanEntity(ProjectPlanEntity item) {

        boolean isAdd = false;
        String soapAction = "http://tempuri.org//AddProjectPlan /";
        // 命名空间     String nameSpace = "http://tempuri.org/";
        // 调用的方法名称     String methodName = "HelloWorld";
        // EndPoint     String endPoint = "http://192.168.16.39:1215/WebService.asmx";
        // SOAP Action     String soapAction = "http://tempuri.org//HelloWorld/";
        // 指定WebService的命名空间和调用的方法名
        String methodName = "AddProjectPlan";
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        //string strUserCode,string strTitle,string strContent,string strBeginDate, string strEndDate
        // 设置需调用WebService接口需要传入的参数
        rpc.addProperty("strUserCode", UserCode);
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

        String soapAction = "http://tempuri.org//GetUserProjectPlans/";
        String methodName = "GetUserProjectPlans";
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
                int pIndex = (i + 1) * 10;
                ProjectPlanEntity item = new ProjectPlanEntity(iId, strTitle, strContent, strBDate, strEDate, pIndex);

                items.add(item);
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
