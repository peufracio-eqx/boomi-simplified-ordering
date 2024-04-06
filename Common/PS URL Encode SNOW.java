for( int i = 0; i < dataContext.getDataCount(); i++ ) 
{
InputStream is = dataContext.getStream(i);
Properties props = dataContext.getProperties(i);

grant_type  = props.getProperty("document.dynamic.userdefined.DDP_grant_type");
client_id  = props.getProperty("document.dynamic.userdefined.DDP_client_id");
client_secret  = props.getProperty("document.dynamic.userdefined.DDP_client_secret");
username  = props.getProperty("document.dynamic.userdefined.DDP_username");
password  = props.getProperty("document.dynamic.userdefined.DDP_password");

//encodedString = URLEncoder.encode(stringToEncode, "UTF-8")

grant_type = URLEncoder.encode(grant_type, "UTF-8")
client_id = URLEncoder.encode(client_id, "UTF-8")
client_secret = URLEncoder.encode(client_secret, "UTF-8")
username = URLEncoder.encode(username, "UTF-8")
password = URLEncoder.encode(password, "UTF-8")


props.setProperty("document.dynamic.userdefined.DDP_grant_type", grant_type);
props.setProperty("document.dynamic.userdefined.DDP_client_id", client_id);
props.setProperty("document.dynamic.userdefined.DDP_client_secret", client_secret);
props.setProperty("document.dynamic.userdefined.DDP_username", username);
props.setProperty("document.dynamic.userdefined.DDP_password", password);
dataContext.storeStream(is, props);
}