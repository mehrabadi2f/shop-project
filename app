2026-07-08T23:25:39.397+04:30  INFO 14060 --- [main] ir.ShopApplication                       : Starting ShopApplication using Java 17.0.1 with PID 14060 (E:\j\enup\shop\target\classes started by DC in E:\j\enup\shop)
2026-07-08T23:25:39.442+04:30  INFO 14060 --- [main] ir.ShopApplication                       : No active profile set, falling back to 1 default profile: "default"
2026-07-08T23:25:42.491+04:30  WARN 14060 --- [main] ConfigServletWebServerApplicationContext : Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.BeanDefinitionStoreException: Failed to parse configuration class [ir.ShopApplication]
2026-07-08T23:25:43.404+04:30 ERROR 14060 --- [main] o.s.boot.SpringApplication               : Application run failed

org.springframework.beans.factory.BeanDefinitionStoreException: Failed to parse configuration class [ir.ShopApplication]
	at org.springframework.context.annotation.ConfigurationClassParser.parse(ConfigurationClassParser.java:179) ~[spring-context-6.1.13.jar:6.1.13]
	at org.springframework.context.annotation.ConfigurationClassPostProcessor.processConfigBeanDefinitions(ConfigurationClassPostProcessor.java:417) ~[spring-context-6.1.13.jar:6.1.13]
	at org.springframework.context.annotation.ConfigurationClassPostProcessor.postProcessBeanDefinitionRegistry(ConfigurationClassPostProcessor.java:290) ~[spring-context-6.1.13.jar:6.1.13]
	at org.springframework.context.support.PostProcessorRegistrationDelegate.invokeBeanDefinitionRegistryPostProcessors(PostProcessorRegistrationDelegate.java:349) ~[spring-context-6.1.13.jar:6.1.13]
	at org.springframework.context.support.PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(PostProcessorRegistrationDelegate.java:118) ~[spring-context-6.1.13.jar:6.1.13]
	at org.springframework.context.support.AbstractApplicationContext.invokeBeanFactoryPostProcessors(AbstractApplicationContext.java:789) ~[spring-context-6.1.13.jar:6.1.13]
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:607) ~[spring-context-6.1.13.jar:6.1.13]
	at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:146) ~[spring-boot-3.3.4.jar:3.3.4]
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:754) ~[spring-boot-3.3.4.jar:3.3.4]
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:456) ~[spring-boot-3.3.4.jar:3.3.4]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:335) ~[spring-boot-3.3.4.jar:3.3.4]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1363) ~[spring-boot-3.3.4.jar:3.3.4]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1352) ~[spring-boot-3.3.4.jar:3.3.4]
	at ir.ShopApplication.main(ShopApplication.java:10) ~[classes/:na]
Caused by: org.springframework.context.annotation.ConflictingBeanDefinitionException: Annotation-specified bean name 'eventPublisher' for bean class [ir.service.EventPublisher] conflicts with existing, non-compatible bean definition of same name and class [ir.model.EventPublisher]
	at org.springframework.context.annotation.ClassPathBeanDefinitionScanner.checkCandidate(ClassPathBeanDefinitionScanner.java:361) ~[spring-context-6.1.13.jar:6.1.13]
	at org.springframework.context.annotation.ClassPathBeanDefinitionScanner.doScan(ClassPathBeanDefinitionScanner.java:288) ~[spring-context-6.1.13.jar:6.1.13]
	at org.springframework.context.annotation.ComponentScanAnnotationParser.parse(ComponentScanAnnotationParser.java:128) ~[spring-context-6.1.13.jar:6.1.13]
	at org.springframework.context.annotation.ConfigurationClassParser.doProcessConfigurationClass(ConfigurationClassParser.java:306) ~[spring-context-6.1.13.jar:6.1.13]
	at org.springframework.context.annotation.ConfigurationClassParser.processConfigurationClass(ConfigurationClassParser.java:246) ~[spring-context-6.1.13.jar:6.1.13]
	at org.springframework.context.annotation.ConfigurationClassParser.parse(ConfigurationClassParser.java:197) ~[spring-context-6.1.13.jar:6.1.13]
	at org.springframework.context.annotation.ConfigurationClassParser.parse(ConfigurationClassParser.java:165) ~[spring-context-6.1.13.jar:6.1.13]
	... 13 common frames omitted

