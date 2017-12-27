package org.craftercms.studio.test.cases.apitestcases;

import org.craftercms.studio.test.api.objects.PublishAPI;
import org.craftercms.studio.test.api.objects.SecurityAPI;
import org.craftercms.studio.test.api.objects.SiteManagementAPI;
import org.craftercms.studio.test.utils.APIConnectionManager;
import org.craftercms.studio.test.utils.JsonTester;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by gustavo ortiz
 */

public class PublishStatusAPITest {

	private SiteManagementAPI siteManagementAPI;
	private SecurityAPI securityAPI;
	private PublishAPI publishAPI;

	public PublishStatusAPITest() {
		APIConnectionManager apiConnectionManager = new APIConnectionManager();
		JsonTester api = new JsonTester(apiConnectionManager.getProtocol(), apiConnectionManager.getHost(),
				apiConnectionManager.getPort());
    	securityAPI = new SecurityAPI(api, apiConnectionManager);
    	publishAPI = new PublishAPI(api, apiConnectionManager);
    	siteManagementAPI = new SiteManagementAPI(api, apiConnectionManager);
	}

	@BeforeTest
	public void beforeTest() {
		securityAPI.logInIntoStudioUsingAPICall();
		siteManagementAPI.testCreateSite(siteManagementAPI.getSiteId());
	}

	@Test(priority = 1,groups={"publishStatus"})
	public void testPublishStatus() {
		publishAPI.testPublishStatus(siteManagementAPI.getSiteId());
	}

	@Test(priority = 2,groups={"publishStatus"})
	public void testPublishStatusInvalidParameters() {
		publishAPI.testPublishStatusInvalidParameters(siteManagementAPI.getSiteId());
	}

	@Test(priority = 3,groups={"publishStatus"})
	public void testPublishStatusSiteNotFound() {
		publishAPI.testPublishStatusSiteNotFound(siteManagementAPI.getSiteId());
	}
	
	@AfterGroups(groups={"publishStatus"})
	public void afterTest(){
		siteManagementAPI.testDeleteSite(siteManagementAPI.getSiteId());
		securityAPI.logOutFromStudioUsingAPICall();
	}
	
	@Test(dependsOnGroups={"publishStatus"})
	public void testPublishStatusUnauthorized(){
		publishAPI.testPublishStatusUnauthorized(siteManagementAPI.getSiteId());
	}

}
