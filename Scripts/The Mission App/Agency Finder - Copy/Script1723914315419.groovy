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

//"a_Agency Finder our agency directory tool"
religionList = [('div_Islam') : 'Showing 1 - 20 of 41', ('div_Buddhism') : 'Showing 1 - 20 of 39', ('div_Hinduism') : 'Showing 1 - 20 of 39'
    , ('div_Animism') : 'Showing 1 - 13 of 13', ('div_Atheism') : 'Showing 1 - 20 of 38']

regionList = [('div_North America') : 'Showing 1 - 20 of 33', ('div_South America') : 'Showing 1 - 20 of 26', ('div_Central America') : 'Showing 1 - 20 of 24'
    , ('div_European Union') : 'Showing 1 - 20 of 28', ('div_Eastern Europe') : 'Showing 1 - 20 of 29', ('div_North Africa') : 'Showing 1 - 20 of 27'
    , ('div_West Africa') : 'Showing 1 - 18 of 18', ('div_Sub Saharan Africa') : 'Showing 1 - 20 of 30', ('div_Middle East') : 'Showing 1 - 20 of 28'
    , ('div_East Asia') : 'Showing 1 - 20 of 30']

/*
	"div_South East Asia" : "Showing 1 - 20 of 41",
	"div_Western Asia" : "Showing 1 - 20 of 41",
	"div_Central Asia" : "Showing 1 - 20 of 41",
	"div_Southern Asia" : "Showing 1 - 20 of 41",
	"div_Northern Asia" : "Showing 1 - 20 of 41",
	"div_Oceania" : "Showing 1 - 20 of 41",
	"div_The Caribbean" : "Showing 1 - 20 of 41"]
*/
ministryList = [('div_Administration Accounting') : 'Showing 1 - 20 of 34', ('div_Agriculture') : 'Showing 1 - 20 of 21'
    , ('div_Aviation') : 'Showing 1 - 7 of 7', ('div_Church Planting') : 'Showing 1 - 20 of 33', ('div_Business Community Development') : 'Showing 1 - 20 of 33'
    , ('div_Camp Ministry') : 'Showing 1 - 19 of 19', ('div_Childrens Ministry') : 'Showing 1 - 20 of 30', ('div_Communications Media') : 'Showing 1 - 20 of 32'
    , ('div_Compassion Social Welfare Ministries') : 'Showing 1 - 20 of 30', ('div_Construction Projects') : 'Showing 1 - 18 of 18']

/*
		"div_Education" : "Showing 1 - 20 of 41",
	"div_English Language Ministries" : "Showing 1 - 20 of 41",
	"div_EvangelismDiscipleship" : "Showing 1 - 20 of 41",
	"div_Healthcare" : "Showing 1 - 20 of 41",
	"div_Information Technology" : "Showing 1 - 20 of 41",
	"div_Leadership Development" : "Showing 1 - 20 of 41",
	"div_Orphanage" : "Showing 1 - 20 of 41",
	"div_Restricted Access1040 Window" : "Showing 1 - 20 of 41",
	"div_SportsCommunity Outreach" : "Showing 1 - 20 of 41",
	"div_Student Ministries" : "Showing 1 - 20 of 41",
	"div_The Arts" : "Showing 1 - 20 of 41",
	"div_Theological Education" : "Showing 1 - 20 of 41",
	"div_Translation" : "Showing 1 - 20 of 41",
	"div_Womens Ministry" : "Showing 1 - 20 of 41"]
*/
if(adminMode) {

	WebUI.callTestCase(findTestCase('The Mission App/Admin Login'), [:], FailureHandling.STOP_ON_FAILURE)

} else {

	WebUI.openBrowser('')

	WebUI.navigateToUrl('https://www.themissionapp.com/')

}

WebUI.maximizeWindow()

WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/Agency Finder/a_Agency Finder our agency directory tool'), 
    10)

WebUI.click(findTestObject('Object Repository/The Mission App/Agency Finder/a_Agency Finder our agency directory tool'))

WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/Agency Finder/button_Click to filter'), 10)

WebUI.click(findTestObject('Object Repository/The Mission App/Agency Finder/button_Click to filter'))

WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/Agency Finder/button_Apply filters'), 10)

WebUI.delay(1)

failures = 0

religionList.each({
	
        WebUI.click(findTestObject('Object Repository/The Mission App/Agency Finder/' + it.key))

        WebUI.click(findTestObject('Object Repository/The Mission App/Agency Finder/button_Apply filters'))

        WebUI.delay(1)

        WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/Agency Finder/button_Click to filter'), 
            10)

        result = WebUI.getText(findTestObject('Object Repository/The Mission App/Agency Finder/span_Showing 1 - 20 of 43'))

        if (result == it.value) {
            println('+++ Search result successful for ' + it.key)
        } else {
            println('--- Search result failed for ' + it.key)

            failures++
        }
        
        WebUI.scrollToPosition(0, 0)

        WebUI.click(findTestObject('Object Repository/The Mission App/Agency Finder/button_Click to filter'))

        WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/Agency Finder/button_Apply filters'), 
            10)

        WebUI.delay(1)

        WebUI.click(findTestObject('Object Repository/The Mission App/Agency Finder/' + it.key))
    })

results = []

regionList.each({ 
        WebUI.click(findTestObject('Object Repository/The Mission App/Agency Finder/' + it.key))

        WebUI.click(findTestObject('Object Repository/The Mission App/Agency Finder/button_Apply filters'))

        WebUI.delay(1)

        WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/Agency Finder/button_Click to filter'), 
            10)

        result = WebUI.getText(findTestObject('Object Repository/The Mission App/Agency Finder/span_Showing 1 - 20 of 43'))

        results.add(result)

        if (result == it.value) {
            println('+++ Search result successful for ' + it.key)
        } else {
            println('--- Search result failed for ' + it.key)

            failures++
        }
        
        WebUI.scrollToPosition(0, 0)

        WebUI.click(findTestObject('Object Repository/The Mission App/Agency Finder/button_Click to filter'))

        WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/Agency Finder/button_Apply filters'), 
            10)

        WebUI.delay(1)

        WebUI.click(findTestObject('Object Repository/The Mission App/Agency Finder/' + it.key))
    })

println(results)

results = []

ministryList.each({ 
        WebUI.click(findTestObject('Object Repository/The Mission App/Agency Finder/' + it.key))

        WebUI.click(findTestObject('Object Repository/The Mission App/Agency Finder/button_Apply filters'))

        WebUI.delay(1)

        WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/Agency Finder/button_Click to filter'), 
            10)

        result = WebUI.getText(findTestObject('Object Repository/The Mission App/Agency Finder/span_Showing 1 - 20 of 43'))

        results.add(result)

        if (result == it.value) {
            println('+++ Search result successful for ' + it.key)
        } else {
            println('--- Search result failed for ' + it.key)

            failures++
        }
        
        WebUI.scrollToPosition(0, 0)

        WebUI.click(findTestObject('Object Repository/The Mission App/Agency Finder/button_Click to filter'))

        WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/Agency Finder/button_Apply filters'), 
            10)

        WebUI.delay(1)

        WebUI.click(findTestObject('Object Repository/The Mission App/Agency Finder/' + it.key))
    })

println(results)

println(('-------------- ' + failures) + ' FAILURE DISCOVERED --------------')

