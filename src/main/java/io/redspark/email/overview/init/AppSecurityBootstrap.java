package io.redspark.email.overview.init;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Order(1)
public class AppSecurityBootstrap extends AbstractSecurityWebApplicationInitializer {}
