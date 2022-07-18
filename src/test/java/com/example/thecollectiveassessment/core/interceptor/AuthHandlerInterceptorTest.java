package com.example.thecollectiveassessment.core.interceptor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class AuthHandlerInterceptorTest {

    @InjectMocks
    AuthHandlerInterceptor interceptor;

    @Test
    public void testPreHandle() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("https://www.test.com/swagger");
        assertTrue(interceptor.preHandle(request, new MockHttpServletResponse(), new Object()));

        request.setRequestURI("https://www.test.com/api");
        assertFalse(interceptor.preHandle(request, new MockHttpServletResponse(), new Object()));

        request.addHeader("secret-key", "secret-key-value");
        assertTrue(interceptor.preHandle(request, new MockHttpServletResponse(), new Object()));
        System.out.println();
    }
}
