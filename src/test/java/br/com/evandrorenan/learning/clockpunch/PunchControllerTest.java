package br.com.evandrorenan.learning.clockpunch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import java.net.URI;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.evandrorenan.learning.clockpunch.controller.PunchController;
import br.com.evandrorenan.learning.clockpunch.service.PunchDto;
import br.com.evandrorenan.learning.clockpunch.service.PunchService;
import br.com.evandrorenan.learning.clockpunch.utils.ClockPunchUtils;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=ClockpunchApplication.class)
@WebMvcTest(value=PunchController.class, secure=false)
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
	private MockMvc mockMvc;
	
	@Mock
	private PunchService service;
	
	private MvcResult lastResult;
	
	@Test
	public void setPunchTest() throws Exception {
		
		Mockito.when(service.setPunch(Mockito.any(PunchDto.class)))
			   .thenReturn(MOCK_PUNCH_DTO);

		ObjectMapper objectMapper = new ObjectMapper();
		
		MockHttpServletResponse response = performRequest(
				POST, new URI(BASE_PUNCH_URL), null, objectMapper.writeValueAsString(MOCK_PUNCH_DTO));
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertTrue(response.getHeader(HttpHeaders.LOCATION).contains("/punches/"));
		
		getPunchTest();
	}
	
	@Test
	public void getPunchTest() throws Exception {

		MockHttpServletResponse response = performRequest(
				GET, new URI(lastResult.getResponse().getHeader(HttpHeaders.LOCATION)), null, null);
		
		ObjectMapper objectMapper = new ObjectMapper();
		JSONAssert.assertEquals(objectMapper.writeValueAsString(MOCK_PUNCH_DTO), response.getContentAsString(), false);
	}
	
	private MockHttpServletResponse performRequest(HttpMethod method, URI uri, MediaType mediaType, String content) throws Exception {
		
		RequestBuilder requestBuilder;
		
		switch (method) {
		case GET:
			requestBuilder = MockMvcRequestBuilders
			.get		(uri)
			.accept		(mediaType == null 	? MediaType.APPLICATION_JSON : mediaType)
			.contentType(mediaType == null 	? MediaType.APPLICATION_JSON : mediaType);
			break;
			
		case POST:
			requestBuilder = MockMvcRequestBuilders
			.post		(uri)
			.accept		(mediaType == null 	? MediaType.APPLICATION_JSON : mediaType)
				.content(content)
			.contentType(mediaType == null 	? MediaType.APPLICATION_JSON : mediaType);
			break;
			
		default:
			return null;
		}
		
		lastResult = mockMvc.perform(requestBuilder).andReturn();
		
		return lastResult.getResponse();
	}
}