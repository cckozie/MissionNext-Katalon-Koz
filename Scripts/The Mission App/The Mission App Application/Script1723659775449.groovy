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

adminMode = true

if(adminMode) {
	
		WebUI.callTestCase(findTestCase('The Mission App/Admin Login'), [:], FailureHandling.STOP_ON_FAILURE)
	
	} else {
	
		WebUI.openBrowser('')
	
		WebUI.navigateToUrl('https://www.themissionapp.com/')
	
	}
	
WebUI.maximizeWindow()

WebUI.delay(5)

WebUI.waitForElementClickable(findTestObject('The Mission App/span_HOME'), 10)

WebUI.click(findTestObject('Object Repository/The Mission App/span_HOME'))

WebUI.waitForElementClickable(findTestObject('Object Repository/The Mission App/a_Start Applying  45 agencies, one app'), 10)

WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/a_Start Applying  45 agencies, one app'), 10)

WebUI.click(findTestObject('Object Repository/The Mission App/a_Start Applying  45 agencies, one app'))

WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/strong_Start the mission application now'),10)

WebUI.click(findTestObject('Object Repository/The Mission App/strong_Start the mission application now'))

WebUI.setText(findTestObject('Object Repository/The Mission App/input__input_55.3'), 'Chris TEST')

WebUI.setText(findTestObject('Object Repository/The Mission App/input_First_input_55.6'), 'Kosieracki')

WebUI.setText(findTestObject('Object Repository/The Mission App/input__input_31'), 'cktest01@missionnext.org')

WebUI.setText(findTestObject('Object Repository/The Mission App/input__input_33'), '952-442-1703')

WebUI.setText(findTestObject('Object Repository/The Mission App/input_Current Mailing Address_input_34.1'), '1250 Waconia Pkwy N')

WebUI.setText(findTestObject('Object Repository/The Mission App/input_State  Province  Region_input_34.5'), 'Waconia')

WebUI.selectOptionByValue(findTestObject('Object Repository/The Mission App/select_AfghanistanAlbaniaAlgeriaAmerican Sa_79bd05'), 
    'United States', true)

WebUI.setText(findTestObject('Object Repository/The Mission App/input_City_input_34.4'), 'MN')

WebUI.setText(findTestObject('Object Repository/The Mission App/input__input_32'), '03/22/1949')

WebUI.setText(findTestObject('Object Repository/The Mission App/input__input_61'), 'USA')

WebUI.click(findTestObject('Object Repository/The Mission App/input_Female_input_70'))

WebUI.setText(findTestObject('Object Repository/The Mission App/input_Marital Status_input_37'), 'Widowed')

WebUI.setText(findTestObject('Object Repository/The Mission App/textarea_If applicable, tell us about your _4e3d56'), 'N/A')

WebUI.click(findTestObject('Object Repository/The Mission App/input_If applicable, tell us about your fam_38de59'))

WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/select_CallingI have a clear call to missio_423c8d'), 10)

WebUI.selectOptionByValue(findTestObject('Object Repository/The Mission App/select_CallingI have a clear call to missio_423c8d'), 
    'I feel like God is calling me to a life of missions.', true)

WebUI.selectOptionByValue(findTestObject('Object Repository/The Mission App/select_Source of financesDonations  raise f_07a00e'), 
    'Self-supported - using personal savings and income', true)

WebUI.setText(findTestObject('Object Repository/The Mission App/textarea_Tell us about your calling (People_32098e'), 'Anywhere, anything that builds the Kingdom')

WebUI.click(findTestObject('Object Repository/The Mission App/input__gform_previous_button_3_12'))

WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/input_If applicable, tell us about your fam_38de59'),10)

WebUI.click(findTestObject('Object Repository/The Mission App/input_If applicable, tell us about your fam_38de59'))

WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/input__gform_next_button_3_12'),10)

WebUI.click(findTestObject('Object Repository/The Mission App/input__gform_next_button_3_12'))

WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/textarea_Tell us about your educational exp_72340f'),10)

WebUI.setText(findTestObject('Object Repository/The Mission App/textarea_Tell us about your educational exp_72340f'), 'BSEE')

WebUI.setText(findTestObject('Object Repository/The Mission App/textarea_Describe your work history startin_f0a827'), 'Volunteering with mission organizations\nContractor/consultant\nSoftware developement')

WebUI.setText(findTestObject('Object Repository/The Mission App/textarea_List and describe any cross-cultur_a27b44'), 'Short term missions trip in Viet Nam')

WebUI.click(findTestObject('Object Repository/The Mission App/input_List and describe any cross-cultural _438cfd'))

WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/textarea_Share briefly how you became a fol_5b386a'), 10)

WebUI.setText(findTestObject('Object Repository/The Mission App/textarea_Share briefly how you became a fol_5b386a'), 'Witnessed to by my sister and future wife. Accepted Christ at Graham crusade. Grown much in intimacy with the Lord over the past 20 years.')

WebUI.setText(findTestObject('Object Repository/The Mission App/input_Name of your homecurrent church_input_49'), 'Parkside Church')

WebUI.setText(findTestObject('Object Repository/The Mission App/input_Name of Pastor_input_50'), 'Randy Burg')

WebUI.setText(findTestObject('Object Repository/The Mission App/input_Phone Number of Pastor  Church_input_51'), '952-442-1703')

WebUI.setText(findTestObject('Object Repository/The Mission App/textarea_How have you served in your home c_09e2c7'), 'Leadership team, adult Sunday School teacher, Embrace Grace advisory board, tech team')

WebUI.setText(findTestObject('Object Repository/The Mission App/textarea_How have you been involved in mini_479147'), 'Volunteer at unfoldingWord, Wycliffe Bible Translators, Wycliffe Associates, MissionNext, and others. Support many missionaries.')

WebUI.selectOptionByValue(findTestObject('Object Repository/The Mission App/select_Send my application to Match me with_5f9096'), 
    'I want to select the organizations', true)

WebUI.click(findTestObject('Object Repository/The Mission App/input_TMS Global_input_68.21'))

WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/input_Send International_input_68.24'),10)

WebUI.click(findTestObject('Object Repository/The Mission App/input_Send International_input_68.24'))

WebUI.click(findTestObject('Object Repository/The Mission App/input_ABWE International_input_68.37'))

WebUI.click(findTestObject('Object Repository/The Mission App/input_Africa Inland Mission, International__d2a3ac'))

WebUI.click(findTestObject('Object Repository/The Mission App/input_This field is for validation purposes_efb318'))

WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/h2_Thank you for filling out our online form'),5)

WebUI.click(findTestObject('Object Repository/The Mission App/h2_Thank you for filling out our online form'))

i = 0

found = WebUI.verifyTextPresent('Thank you for filling out our online form!', false, FailureHandling.OPTIONAL)

while (!found && i < 10) {
	
	WebUI.delay(1)

	found = WebUI.verifyTextPresent('Thank you for filling out our online form!', false, FailureHandling.OPTIONAL)
	
	i ++

}

if(found) {
	println('+++++ SUCCESS +++++')
} else {
	println('----- FAILED -----')
}

