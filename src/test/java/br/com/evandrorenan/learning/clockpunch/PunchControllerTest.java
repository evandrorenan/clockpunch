package br.com.evandrorenan.learning.clockpunch;

import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.evandrorenan.learning.clockpunch.service.PunchDto;
import br.com.evandrorenan.learning.clockpunch.utils.ClockPunchUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
//@WebMvcTest(PunchController.class) 
//@ContextConfiguration(classes= { 
//		ClockpunchApplication.class,
//		PunchControllerTest.class}) 
public class PunchControllerTest extends ClockPunchTests {

	private static final String BASE_PUNCH_URL = "/clockpunch/punches";
	private static final Long MOCK_ID_UM = (long) 1;
	private static final String MOCK_PERSON_ID = "PersonId";
	private static final Date MOCK_TIMESTAMP = ClockPunchUtils.date(2020, 01, 02, 01, 30, 00);
	private static final String MOCK_LOCATION = "Brasil";

	private static final PunchDto MOCK_PUNCH_DTO = new PunchDto(
			MOCK_ID_UM, 
			MOCK_PERSON_ID, 
			MOCK_TIMESTAMP, 
			MOCK_LOCATION);

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;	
	
//	@Autowired
//	private PunchService service;
//	
	private MvcResult lastResult;
	
//	@Before
//	public void setUp() {
//		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//	}
//	

	@Test
	public void getPunchesReal() throws Exception {

		HttpUriRequest request = new HttpGet("http://localhost:9080/clockpunch/punches");
		
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
		
		assertEquals(HttpStatus.OK.value(), httpResponse.getStatusLine().getStatusCode());

		ObjectMapper objectMapper = new ObjectMapper();
		
		List<PunchDto> punches = new ArrayList<PunchDto>();

		punches.add(MOCK_PUNCH_DTO);
		
		JSONAssert.assertEquals(
				objectMapper.writeValueAsString(punches), 
				EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8.displayName()), 
				false);
	}
	
//	@Test
//	public void setPunchTest() throws Exception {
//		
//		Mockito.when(service.setPunch(Mockito.any(PunchDto.class)))
//			   .thenReturn(MOCK_PUNCH_DTO);
//
//		ObjectMapper objectMapper = new ObjectMapper();
//		
//		MockHttpServletResponse response = performRequest(
//				POST, new URI(BASE_PUNCH_URL), null, objectMapper.writeValueAsString(MOCK_PUNCH_DTO));
//		
//		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
//		assertTrue(response.getHeader(HttpHeaders.LOCATION).contains("/punches/"));
//		
//		getPunchTest();
//	}
//	
//	@Test
//	public void getPunchTest() throws Exception {
//
//		MockHttpServletResponse response = performRequest(
//				GET, new URI(lastResult.getResponse().getHeader(HttpHeaders.LOCATION)), null, null);
//		
//		ObjectMapper objectMapper = new ObjectMapper();
//		JSONAssert.assertEquals(objectMapper.writeValueAsString(MOCK_PUNCH_DTO), response.getContentAsString(), false);
//	}
//	
//	private MockHttpServletResponse performRequest(HttpMethod method, URI uri, MediaType mediaType, String content) throws Exception {
//		
//		RequestBuilder requestBuilder;
//		
//		switch (method) {
//		case GET:
//			requestBuilder = MockMvcRequestBuilders
//			.get		(uri)
//			.accept		(mediaType == null 	? MediaType.APPLICATION_JSON : mediaType)
//			.contentType(mediaType == null 	? MediaType.APPLICATION_JSON : mediaType);
//			break;
//			
//		case POST:
//			requestBuilder = MockMvcRequestBuilders
//			.post		(new URI("http://localhost:9080/clockpunch/punches"))
//			.characterEncoding(StandardCharsets.UTF_8.displayName())
//			.contentType(mediaType == null 	? MediaType.APPLICATION_JSON : mediaType)
//			.content(content)
//			.accept		(mediaType == null 	? MediaType.APPLICATION_JSON : mediaType);
//			break;
//			
//		default:
//			return null;
//		}
//		
//		lastResult = mockMvc.perform(requestBuilder).andReturn();
//		
//		return lastResult.getResponse();
//	}
}