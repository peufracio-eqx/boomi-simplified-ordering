import com.boomi.execution.ExecutionUtil;
import com.boomi.util.Base64Util;

for( int i = 0; i < dataContext.getDataCount(); i++ ) 
{
InputStream is = dataContext.getStream(i);
Properties props = dataContext.getProperties(i);

grant_type  = ExecutionUtil.getDynamicProcessProperty("DPP_grant_type");
client_id  =  ExecutionUtil.getDynamicProcessProperty("DPP_client_id");
//client_secret  =  Base64Util.decodeToString(ExecutionUtil.getDynamicProcessProperty("DPP_client_secret"));
client_secret  =  ExecutionUtil.getDynamicProcessProperty("DPP_client_secret");
refresh_token  =  ExecutionUtil.getDynamicProcessProperty("DPP_refresh_token");

//encodedString = URLEncoder.encode(stringToEncode, "UTF-8")

grant_type = URLEncoder.encode(grant_type, "UTF-8")
client_id = URLEncoder.encode(client_id, "UTF-8")
client_secret = URLEncoder.encode(client_secret, "UTF-8")
refresh_token = URLEncoder.encode(refresh_token, "UTF-8")

ExecutionUtil.setDynamicProcessProperty("DPP_grant_type", grant_type, false);
ExecutionUtil.setDynamicProcessProperty("DPP_client_id", client_id, false);
ExecutionUtil.setDynamicProcessProperty("DPP_client_secret", client_secret, false);
ExecutionUtil.setDynamicProcessProperty("DPP_refresh_token", refresh_token, false);

dataContext.storeStream(is, props);
}