import java.util.Properties;
import java.io.InputStream;
import com.boomi.execution.ExecutionUtil;
import groovy.json.JsonSlurper;

logger = ExecutionUtil.getBaseLogger();

for( int i = 0; i < dataContext.getDataCount(); i++ ) {
    InputStream is = dataContext.getStream(i);
    Properties props = dataContext.getProperties(i);

    String dataText = is.text;
    //logger.info("dataText1-->"+is.text);
    //String newDataText = ((((dataText.replace("&","&amp;")).replace("\"","&quot;")).replace("\'","&apos;")).replace("<","&lt;")).replace(">","&gt;");
    //logger.info("newDataText-->"+newDataText);

    def jsonObject = new JsonSlurper().parse(dataContext.getStream(i));
    def bizRefvalue = "", SourceID = "", AccountName = "", SystemName = "", errCode = "", errMsg = "";
    def validMsg = "N";

    try {

        if(jsonObject.containsKey("Task")){
            SourceID = jsonObject.Task.Body.eqxSourceId;
            AccountName = jsonObject.Task.Body.partyAccountName;
            SystemName = jsonObject.Task.Body.externalSystemName;
        }else if(jsonObject.containsKey("task")){
            SourceID = jsonObject.task.body.eqxSourceId;
            AccountName = jsonObject.task.body.partyAccountName;
            SystemName = jsonObject.task.body.externalSystemName;
        }else if(jsonObject.containsKey("body")){
            SourceID = jsonObject.body.eqxSourceId;
            AccountName = jsonObject.body.partyAccountName;
            SystemName = jsonObject.body.externalSystemName;
                if(SourceID){
                    validMsg = "Y";
                }
        }else{
            SourceID = "ERR-UNKNOWN";
            AccountName = "ERR-UNKNOWN";
            SystemName = "ERR-UNKNOWN";
        }
        if(validMsg != "Y"){
            errCode = "400-Bad Request";
            errMsg = "Bad Request:\n" + jsonObject;
        }
        bizRefvalue = SourceID + "|" + AccountName + "|" + SystemName;
    }
    catch(Exception ex) {
        bizRefvalue = "ERR-Unknown";
        errCode = "400-Exception";
        errMsg = "Exception Error Failure!";
        validMsg = "N";
    }
    finally {
        ExecutionUtil.setDynamicProcessProperty("DPP_3001_SourceID", SourceID, false);
        ExecutionUtil.setDynamicProcessProperty("DPP_3001_AccountName", AccountName, false);
        ExecutionUtil.setDynamicProcessProperty("DPP_3001_SystemName", SystemName, false);

        ExecutionUtil.setDynamicProcessProperty("DPP_ErrorCode", errCode, false);
        ExecutionUtil.setDynamicProcessProperty("DPP_ErrorMessage", errMsg, false);        

        //ExecutionUtil.setDynamicProcessProperty("DPP_BizRefName", "SourceID|AccountName|SystemName", false);
        //ExecutionUtil.setDynamicProcessProperty("DPP_BizRefName1", "SourceID|AccountName|SystemName", false);
        ExecutionUtil.setDynamicProcessProperty("DPP_BizRefValue", bizRefvalue, false);
        ExecutionUtil.setDynamicProcessProperty("DPP_TransactionID", bizRefvalue, false);
        ExecutionUtil.setDynamicProcessProperty("DPP_3001_ValidMsg", validMsg, false);

        //logger.info("dataText2-->"+is.text);
        //InputStream stream = new ByteArrayInputStream(newDataText.getBytes("UTF-8"));
        dataContext.storeStream(new ByteArrayInputStream(dataText.getBytes("UTF-8")), props);
        //dataContext.storeStream(is, props);
    }
}