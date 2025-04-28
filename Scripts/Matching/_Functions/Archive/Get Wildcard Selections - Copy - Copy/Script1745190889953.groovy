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
if(varSite == 'Journey') {
	siteValue = '3'
} else {
	siteValue = '6'
}

//Check to see if we're writing printed output also to a file
writeFile = false

WebUI.callTestCase(findTestCase('_Functions/Log In to AD'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.waitForPageLoad(10)

if (GlobalVariable.outFile != '') {
    String myFile = GlobalVariable.outFile

    println(myFile)

    outFile = new File(myFile)

    writeFile = true
}

object = 'Object Repository/Admin/AD Matching/a_Matching Schemes'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Admin/AD Matching/select_Website'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue', ('varObject') : object, ('varParm1') : siteValue], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Admin/AD Matching/radio_' + varMatchType + '-2-Candidate'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Admin/AD Matching/btn_Go'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
*/

WebUI.callTestCase(findTestCase('_Functions/Generic Login'), [('varProfile') : 'Journey Candidate 05'], FailureHandling.STOP_ON_FAILURE)

WebDriver driver = DriverFactory.getWebDriver()

//WebElement Table = driver.findElement(By.xpath('(.//*[normalize-space(text()) and normalize-space(.)="Start Again"])[1]/following::table[1]'))
//WebElements fields = driver.find_elements(By.XPATH, f'//input[@name="{text}"]'

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Dashboard/a_My Profile'))

JavascriptExecutor executor = (JavascriptExecutor) driver;


List<WebElement> tabs = driver.findElements(By.xpath("//*[@role='tabpanel']"))

println(tabs.size())
//System.exit(0)

List<WebElement> inputFields = driver.findElements(By.tagName('input'))

println(inputFields.size())

for (def field : inputFields) {
	myName = field.getAttribute("name")
	if(myName.contains('time_commitment')) {
		myLabel = field.getAttribute('role')
		println(myLabel)
		myValue = field.getAttribute('value')
		if(myValue.contains('!')) {
			println(myName + ' wildcard value is ' + myValue + '\n\n\n')
			WebElement parent = field.findElement(By.xpath("./.."));
//			println(parent)
			WebElement gParent = parent.findElement(By.xpath("./.."));
			println(gParent)
			elementAttributes = executor.executeScript('var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;',	gParent)
			attrs = elementAttributes.toString()
			println(attrs)
			WebElement ggParent = gParent.findElement(By.xpath("./.."));
			elementAttributes = executor.executeScript('var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;',	ggParent)
			attrs = elementAttributes.toString()
			println(attrs)
			WebElement gggParent = ggParent.findElement(By.xpath("./.."));
			elementAttributes = executor.executeScript('var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;',	gggParent)
			attrs = elementAttributes.toString()
			println(attrs)
			WebElement ggggParent = gggParent.findElement(By.xpath("./.."));
			elementAttributes = executor.executeScript('var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;',	ggggParent)
			attrs = elementAttributes.toString()
			println(attrs)
			WebElement gggggParent = ggggParent.findElement(By.xpath("./.."));
			elementAttributes = executor.executeScript('var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;',	gggggParent)
			attrs = elementAttributes.toString()
			println(attrs)
			break
		}
	}
}

/*
List<WebElement> inputFields = driver.findElements(By.tagName('aria-labelledby'))

println(inputFields.size())

for (def field : inputFields) {
	myName = field.getAttribute("name")
	if(myName.contains('time_commitment')) {
		myValue = field.getAttribute('value')
		if(myValue.contains('!')) {
			println(myName + ' wildcard value is ' + myValue + '\n\n\n')
		}
	}
}


<div id="group-1456436124.683" aria-labelledby="ui-id-3" role="tabpanel" class="ui-tabs-panel ui-corner-bottom ui-widget-content" aria-hidden="true" style="display: none;">

					
													<div class="form-group">
				
									<div class="col-sm-12 before-note">
						Select those positions for which you are currently recruiting.                    </div>
				
				<div class="col-sm-3">
					<label class="control-label" for="class">Available Positions*</label>                                    </div>
				<div class="col-sm-9">
					<p class="mn-group-choices">Administration</p><div class=" mn-checkbox mn-large-checkbox " data-key="educational_positions" data-id="132"><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Administrative Assistant">
<label>Administrative Assistant</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Administrator">
<label>Administrator</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Assistant Principal">
<label>Assistant Principal</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Athletic Director">
<label>Athletic Director</label>
</div></div><div class=" mn-checkbox mn-large-checkbox " data-key="educational_positions" data-id="132"><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Childcare Director">
<label>Childcare Director</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Day Care Center Director">
<label>Day Care Center Director</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Director">
<label>Director</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Outdoor Program Supervisor">
<label>Outdoor Program Supervisor</label>
</div></div><div class=" mn-checkbox mn-large-checkbox " data-key="educational_positions" data-id="132"><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Preschool Director">
<label>Preschool Director</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Principal">
<label>Principal</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Recruiting Teachers">
<label>Recruiting Teachers</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Superintendent of Child Education">
<label>Superintendent of Child Education</label>
</div></div><div class=" mn-checkbox mn-large-checkbox " data-key="educational_positions" data-id="132"><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Superintendent of Schools">
<label>Superintendent of Schools</label>
</div></div><p class="mn-group-choices">Education Support</p><div class=" mn-checkbox mn-large-checkbox " data-key="educational_positions" data-id="132"><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Boarding Home Assistant">
<label>Boarding Home Assistant</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Boarding Home Parent">
<label>Boarding Home Parent</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Childcare Attendant">
<label>Childcare Attendant</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Curriculum Specialist">
<label>Curriculum Specialist</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Education Consultant">
<label>Education Consultant</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Education Resource Coordinator">
<label>Education Resource Coordinator</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="IT Coordinator">
<label>IT Coordinator</label>
</div></div><div class=" mn-checkbox mn-large-checkbox " data-key="educational_positions" data-id="132"><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Manager, Business">
<label>Manager, Business</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Manager, Facilities">
<label>Manager, Facilities</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Manager, Finance">
<label>Manager, Finance</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Manager, Maintenance">
<label>Manager, Maintenance</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Non-Traditional Consultant">
<label>Non-Traditional Consultant</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="School Counselor">
<label>School Counselor</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="School Librarian">
<label>School Librarian</label>
</div></div><div class=" mn-checkbox mn-large-checkbox " data-key="educational_positions" data-id="132"><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="School Librarian Assistant">
<label>School Librarian Assistant</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="School Nurse">
<label>School Nurse</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="School Psychologist">
<label>School Psychologist</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="School Psychometrist">
<label>School Psychometrist</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Teacher's Aide">
<label>Teacher's Aide</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Youth Director/Worker">
<label>Youth Director/Worker</label>
</div></div><p class="mn-group-choices">General Teachers</p><div class=" mn-checkbox mn-large-checkbox " data-key="educational_positions" data-id="132"><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Distance Education Teacher">
<label>Distance Education Teacher</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" checked="checked" value="Elementary School Teacher">
<label>Elementary School Teacher</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" checked="checked" value="High School Teacher">
<label>High School Teacher</label>
</div></div><div class=" mn-checkbox mn-large-checkbox " data-key="educational_positions" data-id="132"><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Home Schooling">
<label>Home Schooling</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Kindergarten Teacher">
<label>Kindergarten Teacher</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" checked="checked" value="Middle School Teacher">
<label>Middle School Teacher</label>
</div></div><div class=" mn-checkbox mn-large-checkbox " data-key="educational_positions" data-id="132"><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Non-Traditional School Teacher">
<label>Non-Traditional School Teacher</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Preschool Teacher">
<label>Preschool Teacher</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" checked="checked" value="Substitute Teacher">
<label>Substitute Teacher</label>
</div></div><div class=" mn-checkbox mn-large-checkbox " data-key="educational_positions" data-id="132"><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Tutor">
<label>Tutor</label>
</div></div><p class="mn-group-choices">Language Teachers</p><div class=" mn-checkbox mn-large-checkbox " data-key="educational_positions" data-id="132"><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="English Second Language (ESL)">
<label>English Second Language (ESL)</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="English Teacher">
<label>English Teacher</label>
</div></div><div class=" mn-checkbox mn-large-checkbox " data-key="educational_positions" data-id="132"><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Foreign Language - French">
<label>Foreign Language - French</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Foreign Language - Portuguese">
<label>Foreign Language - Portuguese</label>
</div></div><div class=" mn-checkbox mn-large-checkbox " data-key="educational_positions" data-id="132"><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Foreign Language - Spanish">
<label>Foreign Language - Spanish</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Foreign Language Teacher">
<label>Foreign Language Teacher</label>
</div></div><p class="mn-group-choices">Specialty Teachers</p><div class=" mn-checkbox mn-large-checkbox " data-key="educational_positions" data-id="132"><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Art Teacher">
<label>Art Teacher</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" checked="checked" value="Arts - Performing">
<label>Arts - Performing</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Athletic Coach">
<label>Athletic Coach</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Bible Teacher">
<label>Bible Teacher</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Business Education Teacher">
<label>Business Education Teacher</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Computer Teacher (School)">
<label>Computer Teacher (School)</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Drama Teacher">
<label>Drama Teacher</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Geography Teacher">
<label>Geography Teacher</label>
</div></div><div class=" mn-checkbox mn-large-checkbox " data-key="educational_positions" data-id="132"><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Health Teacher">
<label>Health Teacher</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="History Teacher">
<label>History Teacher</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Home Economics Teacher">
<label>Home Economics Teacher</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Industrial Arts Teacher">
<label>Industrial Arts Teacher</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Mathematics Teacher">
<label>Mathematics Teacher</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Music Teacher">
<label>Music Teacher</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Physical Education Teacher">
<label>Physical Education Teacher</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Reading Teacher/Specialist">
<label>Reading Teacher/Specialist</label>
</div></div><div class=" mn-checkbox mn-large-checkbox " data-key="educational_positions" data-id="132"><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Resource Teacher">
<label>Resource Teacher</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Science, Biology">
<label>Science, Biology</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Science, Chemistry">
<label>Science, Chemistry</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Science, Physics">
<label>Science, Physics</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Science Teacher">
<label>Science Teacher</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Social Studies Teacher">
<label>Social Studies Teacher</label>
</div><div><input id="profile_group-1456436124.683_educational_positions" type="checkbox" name="profile[group-1456436124.683][educational_positions][]" value="Special Education Teacher">
<label>Special Education Teacher</label>
</div></div>                </div>

						
								</div>
				
				
				
													<div class="form-group">
				
				
				<div class="col-sm-3">
					<label class="control-label" for="class">Other Available Positions</label>                                    </div>
				<div class="col-sm-9">
					<textarea data-key="other_educational_positions" data-id="131" class="mn-small-field" name="profile[group-1456436124.683][other_educational_positions]" id="profile_group-1456436124.683_other_educational_positions" placeholder=""></textarea>                </div>

						
								</div>
				
				
				
													<div class="form-group">
				
				
				<div class="col-sm-3">
					<label class="control-label" for="class">Experience Preferred*</label>                                    </div>
				<div class="col-sm-9">
					<p class="mn-group-choices">Administration</p><div class=" mn-checkbox mn-large-checkbox " data-key="teacher_experience_preferred" data-id="139"><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Administrator">
<label>Administrator</label>
</div></div><div class=" mn-checkbox mn-large-checkbox " data-key="teacher_experience_preferred" data-id="139"><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Counseling">
<label>Counseling</label>
</div></div><p class="mn-group-choices">General Teacher</p><div class=" mn-checkbox mn-large-checkbox " data-key="teacher_experience_preferred" data-id="139"><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" checked="checked" value="Elementary">
<label>Elementary</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" checked="checked" value="High School">
<label>High School</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" checked="checked" value="Home Schooling">
<label>Home Schooling</label>
</div></div><div class=" mn-checkbox mn-large-checkbox " data-key="teacher_experience_preferred" data-id="139"><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Kindergarten">
<label>Kindergarten</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" checked="checked" value="Middle School">
<label>Middle School</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Non-Traditional">
<label>Non-Traditional</label>
</div></div><div class=" mn-checkbox mn-large-checkbox " data-key="teacher_experience_preferred" data-id="139"><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Pre-School">
<label>Pre-School</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Substitute Teacher">
<label>Substitute Teacher</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" checked="checked" value="Tutoring">
<label>Tutoring</label>
</div></div><p class="mn-group-choices">Support</p><div class=" mn-checkbox mn-large-checkbox " data-key="teacher_experience_preferred" data-id="139"><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Housing/Boarding/Childcare">
<label>Housing/Boarding/Childcare</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Maintenance">
<label>Maintenance</label>
</div></div><div class=" mn-checkbox mn-large-checkbox " data-key="teacher_experience_preferred" data-id="139"><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Recruiting">
<label>Recruiting</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="School Nurse">
<label>School Nurse</label>
</div></div><div class=" mn-checkbox mn-large-checkbox " data-key="teacher_experience_preferred" data-id="139"><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Support">
<label>Support</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Teacher's Aide">
<label>Teacher's Aide</label>
</div></div><p class="mn-group-choices">Specialty Teacher</p><div class=" mn-checkbox mn-large-checkbox " data-key="teacher_experience_preferred" data-id="139"><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" checked="checked" value="Arts - Performing">
<label>Arts - Performing</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" checked="checked" value="Arts - Visual">
<label>Arts - Visual</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Bible">
<label>Bible</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Business Education">
<label>Business Education</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Computer Science">
<label>Computer Science</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Health">
<label>Health</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Home Economics">
<label>Home Economics</label>
</div></div><div class=" mn-checkbox mn-large-checkbox " data-key="teacher_experience_preferred" data-id="139"><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="History">
<label>History</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Industrial Arts">
<label>Industrial Arts</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Mathematics">
<label>Mathematics</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Music Teacher">
<label>Music Teacher</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Physical Education">
<label>Physical Education</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Reading">
<label>Reading</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Sciences">
<label>Sciences</label>
</div></div><div class=" mn-checkbox mn-large-checkbox " data-key="teacher_experience_preferred" data-id="139"><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Sciences - Biology">
<label>Sciences - Biology</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Sciences - Chemistry">
<label>Sciences - Chemistry</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Sciences - Earth Science">
<label>Sciences - Earth Science</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Sciences - Physical Science/Physics">
<label>Sciences - Physical Science/Physics</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Social Subjects">
<label>Social Subjects</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Speech Pathologist">
<label>Speech Pathologist</label>
</div><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Special Needs">
<label>Special Needs</label>
</div></div><div class=" mn-checkbox mn-large-checkbox " data-key="teacher_experience_preferred" data-id="139"><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Technology">
<label>Technology</label>
</div></div><p class="mn-group-choices">Languages</p><div class=" mn-checkbox mn-large-checkbox " data-key="teacher_experience_preferred" data-id="139"><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" checked="checked" value="English">
<label>English</label>
</div></div><div class=" mn-checkbox mn-large-checkbox " data-key="teacher_experience_preferred" data-id="139"><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="ESL">
<label>ESL</label>
</div></div><div class=" mn-checkbox mn-large-checkbox " data-key="teacher_experience_preferred" data-id="139"><div><input id="profile_group-1456436124.683_teacher_experience_preferred" type="checkbox" name="profile[group-1456436124.683][teacher_experience_preferred][]" value="Language Teacher">
<label>Language Teacher</label>
</div></div>                </div>

						
								</div>
				
				
				
													<div class="form-group">
				
				
				<div class="col-sm-3">
					<label class="control-label" for="class">Other Experience Comment</label>                                    </div>
				<div class="col-sm-9">
					<textarea data-key="other_education_experience_comment" data-id="140" class="mn-small-field" name="profile[group-1456436124.683][other_education_experience_comment]" id="profile_group-1456436124.683_other_education_experience_comment" placeholder=""></textarea>                </div>

						
									<div class="col-sm-offset-3 col-sm-9 after-note">
						Scroll to the top and open the next tab.                    </div>
								</div>
				
				
							
		</div>
*/