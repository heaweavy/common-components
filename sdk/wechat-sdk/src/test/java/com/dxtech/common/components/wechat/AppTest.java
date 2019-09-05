package com.dxtech.common.components.wechat;

import com.github.heaweavy.common.components.wechat.exception.WeiXinApiException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.github.heaweavy.common.components.wechat.api.accessToken.AccessToken;

/**
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() throws WeiXinApiException {
        String appId = "";
        String appSecret = "";
        AccessToken accessToken = AccessToken.build( appId, appSecret );
        assertTrue( true );
    }
}
