import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.sikuli.script.*
import java.awt.Desktop as Desktop
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

Screen s = new Screen()

menus = ['Menu Login':['Journey','Education','Quickstart'], 'Menu About':['About','Impact Report',
	'Join Us','Strategic and Mobilization Partners','Subscribe to Bridge'], 'Menu Goer':['Goer', 'Quickstart','Start Your Journey',
	'Serve in Education','Jobs','Resources'], 'Menu Sender':['Sender', 'Organizations','Schools','Churches',
	'Strategic and Mobilization Partners', 'Resources'], 'Menu Supporter':['Supporter','Mobilizer','Donor','Intercessor', 
	'Strategic and Mobilization Partners', 'Volunteer'],'Menu Events':[null], 'Menu Blog':['MissionNext Blog','Founders Blog'],
	'Menu Donate':[null]]

pageText = ['Menu Login_Journey':'Log into Journey',
	'Menu Login_Education':'Log Into Education',
	'Menu Login_Quickstart':'Login to QuickStart',
	'Menu About_About':'Welcome to MissionNext',
	'Menu About_Impact Report':'Impact Report',
	'Menu About_Join Us':'Positions with MissionNext',
	'Menu About_Strategic and Mobilization Partners':'Strategic and Mobilization Partners',
	'Menu About_Subscribe to Bridge':'Sign Up for the Bridge!',
	'Menu Goer_Goer':'Am I ready?',
	'Menu Goer_Quickstart':'Complete our QuickStart',
	'Menu Goer_Start Your Journey':'Welcome to MissionNext Journey',
	'Menu Goer_Serve in Education':'Welcome To MissionNext Education',
	'Menu Goer_Jobs':'POSITIONS WITH OUR PARTNERS',
	'Menu Goer_Resources':'Resources for Goers',
	'Menu Sender_Sender':'What Kind of Sender are You?',
	'Menu Sender_Organizations':'Journey Pricing',
	'Menu Sender_Schools':'Education Pricing',
	'Menu Sender_Churches':'Resources for Churches',
	'Menu Sender_Strategic and Mobilization Partners':'Strategic and Mobilization Partners',
	'Menu Sender_Resources':'Resources for Senders',
	'Menu Supporter_Supporter':'How Do you Engage in Missions?',
	'Menu Supporter_Mobilizer':'Resources for mobilizers',
	'Menu Supporter_Donor':'Some Things Are Worth Investing In!',
	'Menu Supporter_Intercessor':'The Role of the Faithful Intercessor',
	'Menu Supporter_Strategic and Mobilization Partners':'Strategic and Mobilization Partners',
	'Menu Supporter_Volunteer':'Heart of a Volunteer',
	'Menu Events_null':'Events',
	'Menu Blog_MissionNext Blog':'The MissionNext Blog',
	'Menu Blog_Founders Blog':"The Founder's Blog",
	'Menu Donate_null':'Donate to MissionNext']
	
WebUI.openBrowser('')

WebUI.maximizeWindow()

WebUI.navigateToUrl('missionnext.org')

menusPath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/Menu/'

initImage = menusPath + 'Big M.png'

WebUI.delay(1)

s.click(initImage)


menus.each {
	
	menu = it.key
	
	options = it.value
	
	println(menu)
	
	println(options)
	
	for(option in options) {
		
		println(option)
		
		textKey = menu + '_' + option
		
		text = pageText.get(textKey)

		WebUI.waitForPageLoad(10)
		
//		testMenuOption(menu, option, text)
		
		imagePath = menusPath + menu + '/'
		
		menuImage = imagePath + menu + '.png'
		
		if(option != null) {
			optionImage = imagePath + option + '.png'
		} else {
			optionImage = imagePath + menu + '.png'
		}
		
		s.hover(menuImage)
		
		s.click(optionImage)
		
		WebUI.waitForPageLoad(10)
		
		found = WebUI.verifyTextPresent('(?i)' + text, true, FailureHandling.CONTINUE_ON_FAILURE)
	
		WebUI.navigateToUrl('missionnext.org')
	}
}

