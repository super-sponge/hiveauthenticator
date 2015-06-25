package com.asiainfo.ctc.bigdata;


import com.asiainfo.ctc.bigdata.utils.CodesUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hive.service.auth.PasswdAuthenticationProvider;

import javax.security.sasl.AuthenticationException;

/**
 * Created by sponge on 15-6-25.
 */
public class AIPasswdAuthenticator implements PasswdAuthenticationProvider {
    private static final Log LOG= LogFactory.getLog(AIPasswdAuthenticator.class);
    HiveConf conf = null;

    private static final String HIVE_JDBC_PASSWD_AUTH_PREFIX="hive.jdbc_passwd.auth.%s";

    public AIPasswdAuthenticator() {
        if (this.conf == null )
            this.conf = new HiveConf();
    }

    @Override
    public void Authenticate(String userName, String passwd)
            throws AuthenticationException {
        LOG.info("user: "+userName+" try login.");

        String passwdMD5 = conf.get(String.format(HIVE_JDBC_PASSWD_AUTH_PREFIX, userName));

        if(passwdMD5==null){
            String message = "user's ACL configration is not found. user:"+userName;
            LOG.info(message);
            throw new AuthenticationException(message);
        }

        String md5 = CodesUtil.MD5(passwd);

        if(!md5.equals(passwdMD5)){
            String message = "user name and password is mismatch. user:"+userName;
            throw new AuthenticationException(message);
        }

        LOG.info("user "+userName+" login system successfully.");

    }

}
