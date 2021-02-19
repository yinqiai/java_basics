package com.transsnet.annotation.usage;

/**
 * @author yinqi
 * @date 2019/12/25
 */
public class PersonDao {
    @InjectPerson(name = "yinqi",age=10)
    private Person person;

    public Person getPerson() {
        return person;
    }

    @InjectPerson(name = "chenlimin",age=9)
    public void setPerson(Person person) {
        this.person = person;
    }
}
