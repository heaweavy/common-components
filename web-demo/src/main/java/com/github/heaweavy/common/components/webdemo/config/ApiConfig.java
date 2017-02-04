package com.schoolguard.commander.config;

import com.schoolguard.core.ws.client.admin.TeacherKgAPI;
import com.schoolguard.core.ws.client.collection.EquipmentAPI;
import com.schoolguard.core.ws.client.collection.SchoolInfoAPI;
import com.schoolguard.core.ws.client.collection.StudentInfoAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lihaopeng on 2016/8/30.
 */
@Configuration
public class ApiConfig {
    @Value("${app.collection.rootUrl}")
    private String apiRootUrl;

    @Value("${app.collection.credential.account}")
    private String username;

    @Value("${app.collection.credential.password}")
    private String password;

    @Value("${app.teacher-desktop.rootUrl}")
    private String teacher_apiRootUrl;

    @Value("${app.teacher-desktop.credential.account}")
    private String teacher_username;

    @Value("${app.teacher-desktop.credential.password}")
    private String teacher_password;


    @Bean(destroyMethod = "close")
    public SchoolInfoAPI getSchoolApi(){
        return new SchoolInfoAPI(apiRootUrl,username,password);
    }

    @Bean(destroyMethod = "close")
    public EquipmentAPI getEquipmentAPI(){
        return new EquipmentAPI(apiRootUrl,username,password);
    }

    @Bean(destroyMethod = "close")
    public StudentInfoAPI getStudentInfoAPI(){
        return new StudentInfoAPI(apiRootUrl,username,password);
    }


    @Bean(destroyMethod = "close")
    public TeacherKgAPI getTeacherKgAPI(){
        return new TeacherKgAPI(teacher_apiRootUrl,teacher_username,teacher_password);
    }
}
