import java.util.Properties;
import java.io.InputStream;
import com.boomi.execution.ExecutionUtil;
//logger = ExecutionUtil.getBaseLogger();

for( int i = 0; i < dataContext.getDataCount(); i++ ) {
    InputStream is = dataContext.getStream(i);
    Properties props = dataContext.getProperties(i);
    def errMsg  = ExecutionUtil.getDynamicProcessProperty("DPP_ErrorMessage");
    def errCustomMsg  = ExecutionUtil.getDynamicProcessProperty("DPP_CustomMessage");
    def errCode  = ExecutionUtil.getDynamicProcessProperty("DPP_ErrorCode");

try{
    if(!errMsg){
        if (!errCustomMsg){
            errMsg = "Unknown Error";
        }else{
            errMsg = errCustomMsg;
        }
        ExecutionUtil.setDynamicProcessProperty("DPP_ErrorMessage", errMsg, false);
    }
    if(!errCode){
        if(errMsg.indexOf("Code 502: Bad Gateway") > 0){
            errCode = "502";
        }else{
            errCode = "400";
        }
        ExecutionUtil.setDynamicProcessProperty("DPP_ErrorCode", errCode, false);
    }
}catch(Exception ex){
    ExecutionUtil.setDynamicProcessProperty("DPP_ErrorCode", "Err-400", false);
    ExecutionUtil.setDynamicProcessProperty("DPP_ErrorMessage", "Error Loggin: Exception Failure!", false);
}finally{
    ExecutionUtil.setDynamicProcessProperty("error_flag", "true", false);
    ExecutionUtil.setDynamicProcessProperty("DPP_Raise_Exception", "true", false);
}
    dataContext.storeStream(is, props);
}