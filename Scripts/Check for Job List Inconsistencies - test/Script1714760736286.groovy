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
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper


Boolean printAll = false

WebUI.openBrowser('missionnext.org')

WebUI.maximizeWindow()

WebUI.focus(findTestObject('Page_Serve in Missions - MissionNext.org/a_Goer'))

WebUI.click(findTestObject('Page_Serve in Missions - MissionNext.org/Goer_arrow'))

WebUI.click(findTestObject('Page_Serve in Missions - MissionNext.org/a_Jobs'))

WebUI.click(findTestObject('Page_Positions - MissionNext.org/span_Click Here'))

// BUILD ARRAYS OF JOBS ON THE JOB LIST PAGE

List_Category = []

List_Category_Map = [:]

List_Description = []

List_Region = []

WebUI.callTestCase(findTestCase('_Functions/Navigate to Job Summary Page'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.switchToWindowTitle('Journey Missionary Job Summary - MissionNext.org')

WebDriver driver = DriverFactory.getWebDriver()

WebElement Table = driver.findElement(By.xpath("//table/tbody"))

List<WebElement> Rows = Table.findElements(By.tagName('tr'))

int row_count = Rows.size()

Boolean first = true

category_count = 0

old_category = ""

int row

cat_descriptions = []

for(row = 1; row < row_count; row++) {
		
	List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
	
	String category = Columns.get(0).getText()
	
	if(first) {
		old_category = category
		first = false
	}
	
	List_Category.add(category)
	
	String region = Columns.get(2).getText()
	
	String description = Columns.get(1).getText() + ' (' + region + ')'
	
	List_Description.add(description)
	
	List_Region.add(region)
	
	if(category == old_category) {
	
		cat_descriptions.add(description)
		
		category_count ++
		
	} else {
		
		List_Category_Map.put(old_category, cat_descriptions)
		
		category_count = 0
		
		old_category = category
		
		cat_descriptions = []
				
	}

}

List_Category_Map.put(old_category,cat_descriptions)

if(printAll) {

	for(int e = 0; e < row - 1; e ++) {
		
		println(List_Category[e] + " : " + List_Description[e] + " : " + List_Region[e])
		
	}
	
	List_Category_Map.each { key, val ->
	    println("Category $key count is $val")
	}

}

//def map = ['a':['123','456','789']]
def s = new JsonBuilder(List_Category_Map).toString()
println s




new FileWriter('/Users/cckozie/Documents/MissionNext/List_Category_Map.txt').with {
	write(s)
	flush()
}
