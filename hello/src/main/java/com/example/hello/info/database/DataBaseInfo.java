package com.example.hello.info.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@PropertySource("classpath:application.properties")
@Getter
@Setter
@Component
public class DataBaseInfo {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public String toString() {
        return "url is " + url + "\n" +
                "userName is" + userName + "\n" +
                "password is " + password;
    }
}
