package com.qa.viou.tests;

import org.testng.annotations.Test;

import com.qa.viou.keyword.engine.KeyWordEngine;

public class LoginTest {

	public KeyWordEngine keyWordEngine;
	@Test
	public void loginTest()
	{
		keyWordEngine=new KeyWordEngine();
		keyWordEngine.startExecution("login");
		
	}
	
	
}
