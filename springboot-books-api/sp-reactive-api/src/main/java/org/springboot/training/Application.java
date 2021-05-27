package org.springboot.training;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springboot.training.model.AuthorRef;
import org.springboot.training.model.BookAuthors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.nativex.hint.TypeHint;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@TypeHint(types = {AuthorRef.class, BookAuthors.class})
public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(Application.class, args);
        displayAllBeans();
    }

    public static void displayAllBeans() {
        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
        AtomicInteger counter = new AtomicInteger();
        String SPACE = " ";

        Arrays.stream(allBeanNames)
                .map(beanName -> new StringBuilder()
                        .append(counter.getAndIncrement())
                        .append(SPACE)
                        .append(beanName)
                        .toString())
                .forEach(LOG::info);
    }
}
