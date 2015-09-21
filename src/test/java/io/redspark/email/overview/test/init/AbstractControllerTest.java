package io.redspark.email.overview.test.init;

import static io.redspark.email.overview.init.AppProfile.TEST;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import io.redspark.email.overview.init.AppConfig;
import io.redspark.email.overview.init.AppWebConfig;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.WebApplicationContext;

import aleph.TestPersistentContext;

import com.jayway.jsonassert.JsonAssert;
import com.jayway.jsonassert.JsonAsserter;

@Transactional
@TransactionConfiguration(defaultRollback=true)
@WebAppConfiguration
@ActiveProfiles(TEST)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppTestProvider.class, AppConfig.class, AppWebConfig.class})
public abstract class AbstractControllerTest {

	protected MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;
	
	@Resource
    private FilterChainProxy springSecurityFilterChain;
	
	@Autowired
	private TransactionTemplate transactionManager;
	
	private MockHttpSession session;
	
	@Before 
	public void setup() {
		
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(this.context)
				.addFilter(springSecurityFilterChain)
				.build();
		clear();
		
	}
	
	protected void saveAll() {
		transactionManager.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				TestPersistentContext.get().saveAll();
			}
		});
	}
	
	protected void clear() {
		transactionManager.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				TestPersistentContext.get().clear();
			}
		});
	}
	
	protected void inTransaction(TransactionCallback<Object> action) {
	    transactionManager.execute(action);
	}
	
	public AbstractControllerTest signIn() throws Exception {

		MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/login/authenticate");
		post.param("username", "overview");
		post.param("password", "overview");
		
		
		session = (MockHttpSession) mockMvc.perform(post).andExpect(status().isOk()).andReturn().getRequest().getSession();

		return this;
	}

	// UTIL
	protected MockHttpServletRequestBuilder get(Object... variables) {
		return MockMvcRequestBuilders.get(getControllerBase(), variables);
	}

	protected MockHttpServletRequestBuilder get(String uri, Object... variables) {
		return MockMvcRequestBuilders.get(getControllerBase() + uri, variables);
	}
	
	protected MockHttpServletRequestBuilder post(Object... variables) {
		return MockMvcRequestBuilders.post(getControllerBase(), variables);
	}

	protected MockHttpServletRequestBuilder post(String uri, Object... variables) {
		return MockMvcRequestBuilders.post(getControllerBase() + uri, variables);
	}
	
	protected MockHttpServletRequestBuilder delete(String uri, Object... variables) {
		return MockMvcRequestBuilders.delete(getControllerBase() + uri, variables);
	}

	protected MockHttpServletRequestBuilder put(String uri, Object... variables) {
		return MockMvcRequestBuilders.put(getControllerBase() + uri, variables);
	}

	private String getControllerBase() {

		ControllerBase annotation = getClass().getAnnotation(ControllerBase.class);
		String base = annotation.value();

		return StringUtils.isNotBlank(base) ? base : StringUtils.EMPTY;
	}

	protected JsonAsserter jsonAssert(MvcResult result) throws UnsupportedEncodingException {
		return JsonAssert.with(result.getResponse().getContentAsString());
	}

	protected AssertUtil jsonError(MvcResult result) throws UnsupportedEncodingException {
		return AssertUtil.with(result.getResponse().getContentAsString()).isJsonError();
	}

	protected MvcResult perform(final MockHttpServletRequestBuilder requestBuilder, final ResultMatcher resultMatcher) throws Exception {
		requestBuilder.session(session);
		return mockMvc.perform(requestBuilder).andExpect(resultMatcher).andDo(print()).andReturn();
	}
}
