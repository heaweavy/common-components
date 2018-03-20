package com.github.heaweavy.common.components.mysql_test.mapper;

import com.github.heaweavy.common.components.mysql_test.entity.Person;

import java.util.List;
import java.util.Map;

/**
 * Created by Rogers on 15-3-20.
 */

public interface PersonMapper {

    public int createPerson(Person Person);

    public void updatePerson(Person Person);

    public void deletePerson(int id);

    public Person getPersonById(int id);

    public Person getPersonByMobile(String mobile);

    public Person getPersonByAccount(String account);

    public List<Person> queryPersons(Map<String, Object> param);

    public Integer countPersons(Map<String, Object> param);
}
