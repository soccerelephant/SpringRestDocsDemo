package com.example;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;

@WebAppConfiguration
@ActiveProfiles({ "test", "jamon" })
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { DemoApplication.class, MockServletContext.class })
public class SoccerRestControllerDocumentation {

	private static final String URL = "/soccer";

	private static final String URL_PARAM = "/soccer/{id}";

	protected ObjectMapper objectMapper;

	@Autowired
	protected WebApplicationContext webApplicationContext;

	protected MockMvc mockMvc;
	
	@Rule
	public final StandardOutputStreamLog consoleLog = new StandardOutputStreamLog();

	@Rule
	public final RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");

	protected RestDocumentationResultHandler document;

	@Before()
	public void setUp() throws Exception {


		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(documentationConfiguration(restDocumentation)).build();

		// mockMvc = MockMvcBuilders.standaloneSetup(new
		// DocumentRestController()).build();
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
	
	@After
	public void cleanUp() {
		consoleLog.clear();
	}

	@Test
	public void testCrudSoccerAndGenerateApiDocument() throws Exception {

		Soccer soccer = createSoccer();

		soccer = updateSoccer(soccer);

		soccer = readSoccer(soccer.getId());

		deleteSoccer(soccer.getId());
	}

	private Soccer createSoccer() throws Exception {

		document = document(Thread.currentThread().getStackTrace()[1].getMethodName(), preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()));

		String soccerJsonToBeCreated = createSoccerJson();

		String soccerJsonCreated = mockMvc
				.perform(post(URL).contentType(MediaType.APPLICATION_JSON_VALUE).content(soccerJsonToBeCreated))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("id", notNullValue())).andExpect(jsonPath("name", equalTo("Soccer")))
				.andDo(document).andReturn().getResponse().getContentAsString();

		Soccer soccerCreated = objectMapper.readValue(soccerJsonCreated, Soccer.class);

		return soccerCreated;
	}

	private Soccer updateSoccer(Soccer soccer) throws Exception {

		document = document(Thread.currentThread().getStackTrace()[1].getMethodName(), preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()));

		String SoccerJsonToBeUpdated = updateSoccerJson(soccer);

		String SoccerJsonUpdated = mockMvc
				.perform(patch(URL_PARAM, soccer.getId()).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(SoccerJsonToBeUpdated))
				.andExpect(status().isOk()).andExpect(jsonPath("name", equalTo("US Soccer"))).andDo(document)
				.andReturn().getResponse().getContentAsString();

		Soccer SoccerUpdated = objectMapper.readValue(SoccerJsonUpdated, Soccer.class);

		return SoccerUpdated;
	}

	private Soccer readSoccer(Long SoccerId) throws Exception {

		document = document(Thread.currentThread().getStackTrace()[1].getMethodName(), preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()))
						.snippets(pathParameters(parameterWithName("id").description("The Soccer's id")));

		String SoccerJsonRead = mockMvc.perform(get(URL_PARAM, SoccerId)).andExpect(status().isOk())
				.andExpect(jsonPath("name", equalTo("Soccer"))).andDo(document).andReturn().getResponse()
				.getContentAsString();

		Soccer SoccerRead = objectMapper.readValue(SoccerJsonRead, Soccer.class);

		return SoccerRead;
	}

	private void deleteSoccer(Long SoccerId) throws Exception {

		document = document(Thread.currentThread().getStackTrace()[1].getMethodName(), preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()))
						.snippets(pathParameters(parameterWithName("id").description("The Soccer's id")));

		mockMvc.perform(delete(URL_PARAM, SoccerId)).andExpect(status().isOk()).andDo(document);
	}

	private String createSoccerJson() throws Exception {
		Soccer soccer = new Soccer(1L, "Soccer");

		return objectMapper.writeValueAsString(soccer);
	}

	private String updateSoccerJson(Soccer soccer) throws Exception {

		soccer.setName("US Soccer");

		return objectMapper.writeValueAsString(soccer);
	}

}
