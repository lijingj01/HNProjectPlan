package com.hn.business.Data;

import android.security.NetworkSecurityPolicy;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import as.hn.com.hnprojectplan.feature.MainActivity;
import as.hn.com.hnprojectplan.feature.ProjectPlanEntity;

/*远程服务调用类
 * */
public class ServiceHelper {

    private String nameSpace = "http://tempuri.org/";
    private String endPoint = "https://ihomeapptest3.nw-sc.com:8034/DataService.asmx";
    private String soapAction = "http://tempuri.org//AddProjectPlan /";
    private String UserCode = "lijingj";

    public boolean AddPlanEntity(ProjectPlanEntity item) {

        boolean isAdd = false;

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
                System.out.println(envelope.getResponse());
                isAdd = Boolean.valueOf(envelope.getResponse().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isAdd;
    }

}
