package com.github.heaweavy.common.components.webserver2;

import com.github.heaweavy.common.components.webserver2.admin.AdminModule;
import com.github.heaweavy.common.components.webserver2.admin.MybatisModule;
import com.github.heaweavy.common.components.webserver2.admin.rest.UserResource;
import com.github.heaweavy.common.components.webserver2.helper.SecurityInterceptor;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by Rogers on 15-3-3.
 */
public class MainModule extends AbstractModule {

    @Override
    public void configure(){
        install(new MybatisModule());
        install(new AdminModule());

        Names.bindProperties(binder(), loadProperties());

        bind(SecurityInterceptor.class);

        // admin
        bind(UserResource.class);


    }

    @Provides
    @Named("security.accounts")
    public List<String> parseSecurityAccount(@Named("security.roles") String accountList){
        List<String> parsedAccounts = new ArrayList<>();
        String[] accounts = accountList.split(",");
        if(accounts.length <= 0){
            return parsedAccounts;
        }

        for(String a : accounts){
            String account = a.trim();
            parsedAccounts.add(account);
        }

        return parsedAccounts;
    }

    private Properties loadProperties(){
        Properties configProperties = new Properties();

        try{
            configProperties.load(MainModule.class.getClassLoader().getResourceAsStream("application.properties"));
        }catch (FileNotFoundException exception){
            throw new RuntimeException("Load application.properties failed: " + exception.getMessage());

        } catch (IOException exception){
            throw new RuntimeException("Load application.properties failed: " + exception.getMessage());
        } catch (Exception e){
            throw new RuntimeException(e);
        }

        return configProperties;
    }
}
