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
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor

/*
 * if select, go up until <label class="control-label" for="class">Relocation Option(s)*</label>
 * 						  <label class="control-label" for="class">I/We can be Available*</label>
 * if checkbox, <input id="profile_group-1449971279.254_time_commitment" type="checkbox" name="profile[group-1449971279.254][time_commitment][]" value="(!) Open - Will negotiate">
 * 				following above is <label> Open - Will negotiate</label>
 */
/*
if(varSite == 'Journey') {
	siteValue = '3'
} else {
	siteValue = '6'
}
*/
//Check to see if we're writing printed output also to a file
writeFile = false

if (GlobalVariable.outFile != '') {
    String myFile = GlobalVariable.outFile

    println(myFile)

    outFile = new File(myFile)

    writeFile = true
}

WebUI.callTestCase(findTestCase('_Functions/Generic Login'), [('varProfile') : 'Education Candidate 04'], FailureHandling.STOP_ON_FAILURE)

WebDriver driver = DriverFactory.getWebDriver()

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Dashboard/a_My Profile'))

JavascriptExecutor executor = (JavascriptExecutor) driver;

wildcards = [:]

List<WebElement> tabs = driver.findElements(By.xpath("//*[@role='tabpanel']"))

for(tab in tabs) {

	List<WebElement> inputFields = tab.findElements(By.tagName('input'))

	for (def field : inputFields) {
		values = []
		myName = field.getAttribute("name")
		myValue = field.getAttribute('value')
		myText = field.text
		if(myValue.contains('!')) {
			WebElement parent = field.findElement(By.xpath("./.."));
			elementAttributes = executor.executeScript('var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;',	parent)
			attrs = elementAttributes.toString()
			println(attrs)
			WebElement gParent = parent.findElement(By.xpath("./.."));
			elementAttributes = executor.executeScript('var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;',	gParent)
			attrs = elementAttributes.toString()
			println(attrs)
			WebElement ggParent = gParent.findElement(By.xpath("./.."));
			elementAttributes = executor.executeScript('var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;',	ggParent)
			attrs = elementAttributes.toString()
			println(attrs)
			println('\n\n' + attrs)
//			values.add(myValue.substring(4))
//			values.add(attrs)
//			wildcards.put(label,values)
			println('\n\n' + myName + ':' + myValue + ':' + myText)
		}
	}
}

wildcards.each {
	println(it.key + ':' + it.value)
}

/*
Journey Candidate
time_commitment:Open - Will negotiate
time_availability:Open
financial_support:Open
trip_lengths:Open
short-term_availability:Open
travel_support:No travel funding provided
region_preferences:No Preference

Journey Partner
time_commitments:Open - Will negotiate
available_start_options:No Preference
time_availability:Open
trip_lengths:Open
short-term_availability:Open
short-term_objective:Any or all candidate objectives
regions_of_the_world:No Preference
candidate_process_stages:I am just beginning to look at missions
cross-cultural_experience:Not served in a culture other than my own
bible_school_training:Not Applicable
attended_perspectives?:I have not taken the Perspectives Course
relocation_question:Not a Match criterion
financial_support:No Preference
profile_years:All

Education Candidate
time_commitment:Open - Will negotiate
world_region_preferences:No Preference
financial_support:Open
travel_support:No travel funding provided

Education Partner
time_commitments:Open - Will negotiate
available_start_options:No Preference
school_term_available:Open
candidate_process_stages:I am just beginning to look at missions
cross-cultural_experience:Not served in a culture other than my own
bible_school_training:Not Applicable
attended_perspectives?:I have not taken the Perspectives Course
relocation_question:Not a Match criterion
school_formal_education_degree:No
school_classroom_experience:No
has_teaching_credentials:No
english_skills:Not Applicable
financial_support:No Preference

*/
