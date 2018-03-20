package com.github.heaweavy.common.components.mysql_test.genetator;

import com.github.heaweavy.common.components.mysql_test.entity.Children;
import com.github.heaweavy.common.components.mysql_test.entity.Person;
import com.github.heaweavy.common.components.mysql_test.entity.User;
import com.github.heaweavy.common.components.mysql_test.mapper.ChildrenMapper;
import com.github.heaweavy.common.components.mysql_test.mapper.PersonMapper;
import com.github.heaweavy.common.components.mysql_test.mapper.UserMapper;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import java.util.Random;

/**
 * @author caimb
 * @date Created in 2017/12/4 21:45
 * @modefier
 */
public class UserPersonGenerator {
    public static Long parentId = null;
    public static void generate(ApplicationReadyEvent appEvent,Random random) {
        UserMapper userMapper = appEvent.getApplicationContext().getBean( UserMapper.class );
        PersonMapper personMapper = appEvent.getApplicationContext().getBean( PersonMapper.class );
        ChildrenMapper childrenMapper = appEvent.getApplicationContext().getBean( ChildrenMapper.class );
        int flag;
        try {
            while ( true ) {
                User user = new User();
                String mobile = WordGenerator.generateNumber( 11 );
                user.setAccount( WordGenerator.generateCHENWord( 20 ) );
                user.setEmail( WordGenerator.generateENSYWord( 20 ) );
                user.setMobile( mobile );
                user.setName( WordGenerator.generateCHENSYWord( 16 ) );
                user.setPassword( WordGenerator.generateENSYWord( 32 ) );
                user.setEnabled( random.nextBoolean() );
                userMapper.createUser( user );
                Person person = new Person();
                person.setUserId( user.getId() );
                person.setAddress( WordGenerator.generateChineseWord( 32 ) );
                person.setAge( random.nextInt( 120 ) );
                person.setMarried( random.nextBoolean() );
                person.setQqName( WordGenerator.generateCHENSYWord( 1000 + random.nextInt( 1000000 ) ) );
                person.setQq( WordGenerator.generateNumber( 12 ) );
                person.setPhone( WordGenerator.generateNumber( 11 ) );
                person.setMobile( mobile );
                person.setRealName( WordGenerator.generateChineseWord( 4 ) );
                person.setWeChatName( WordGenerator.generateCHENSYWord( 16 ) );
                person.setWeChatOpenId( WordGenerator.generateENSYWord( 32 ) );
                person.setZipCode( WordGenerator.generateNumber( 6 ) );
                personMapper.createPerson( person );
                if ( parentId == null ) {
                    flag = random.nextInt( 6 );
                    if ( flag == 0 ) {
                        parentId = user.getId();
                    }
                } else {
                    Children children = new Children();
                    children.setUserId( user.getId() );
                    children.setParentId( parentId );
                    children.setEnabled( random.nextBoolean() );
                    childrenMapper.createChildren( children );
                    flag = random.nextInt( 6 );
                    if ( flag == 0 ) {
                        parentId = user.getId();
                    }
                }
                System.out.println( "user:" + user.getId() + ";person:" + person.getId() );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
