package easy.domain.test;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.*;

import easy.domain.rules.BooleanRule;
import easy.domain.rules.DateShouldGreaterThanRule;
import easy.domain.rules.DateShouldLessThanRule;
import easy.domain.rules.EmailRule;
import easy.domain.rules.EntityRule;
import easy.domain.rules.IRule;
import easy.domain.rules.MaxLengthRule;
import easy.domain.rules.NumberShouldGreaterThanRule;
import easy.domain.rules.NumberShouldLessThanRule;

public class EntityRuleTest {

    /**
     * 规则验证
     */
    @Test
    public void brokenRuleEntitRuleTest() {
        User user = new User(StringUtils.EMPTY, 101);

        UserEntityRule rule = new UserEntityRule();

        boolean result = rule.isSatisfy(user);
        Assert.assertFalse(result);

        int size = user.getBrokenRules().size();
        Assert.assertEquals(2, size);
    }

    /**
     * email验证
     */
    @Test
    public void emailRuleTest() {
        User user = new User();
        user.setEmail("lee.bmw@foxmail.com");

        EmailRule<User> rule = new EmailRule<User>("email");

        boolean result = rule.isSatisfy(user);
        Assert.assertTrue(result);

        user.setEmail("dfdafdafads");

        result = rule.isSatisfy(user);
        Assert.assertFalse(result);
    }

    /**
     * 最大字符串长度验证
     */
    @Test
    public void maxLengthRuleTest() {
        User user = new User("lixiaojing", 1);

        MaxLengthRule<User> rule = new MaxLengthRule<User>("name", 5);
        boolean result = rule.isSatisfy(user);
        Assert.assertFalse(result);

        rule = new MaxLengthRule<User>("name", 10);
        result = rule.isSatisfy(user);
        Assert.assertTrue(result);

    }

    /**
     * 大于验证
     */
    @Test
    public void shouldGreaterThanRuleTest() {
        User user = new User("lixiaojing", 1);

        NumberShouldGreaterThanRule<User, Integer> rule = new NumberShouldGreaterThanRule<User, Integer>(
                "age", 2);

        boolean result = rule.isSatisfy(user);
        Assert.assertFalse(result);

        user = new User("lixiaojing", 3);

        result = rule.isSatisfy(user);
        Assert.assertTrue(result);
    }

    /**
     * 小于验证
     */
    @Test
    public void shouldLessThanRuleTest() {
        User user = new User("lixiaojing", 1);

        NumberShouldLessThanRule<User, Integer> rule = new NumberShouldLessThanRule<User, Integer>(
                "age", 1);

        boolean result = rule.isSatisfy(user);

        Assert.assertFalse(result);

        rule = new NumberShouldLessThanRule<User, Integer>("age", 2);
        result = rule.isSatisfy(user);

        Assert.assertTrue(result);
    }

    /**
     * 时间大于验证
     */
    @Test
    public void dateShouldGreaterThanTest() {
        User user = new User("lixiaojing", 1);

        Date d = DateUtils.addDays(user.getCreate(), -1);

        DateShouldGreaterThanRule<User> rule = new DateShouldGreaterThanRule<User>(
                "create", d);

        boolean result = rule.isSatisfy(user);
        Assert.assertTrue(result);

        d = DateUtils.addDays(user.getCreate(), 1);

        rule = new DateShouldGreaterThanRule<User>("create", d);
        result = rule.isSatisfy(user);

        Assert.assertFalse(result);
    }

    /**
     * 时间小于验证
     */
    @Test
    public void dateShouldLessThanTest() {
        User user = new User("lixiaojing", 1);

        Date d = DateUtils.addDays(user.getCreate(), -1);

        DateShouldLessThanRule<User> rule = new DateShouldLessThanRule<User>(
                "create", d);

        boolean result = rule.isSatisfy(user);
        Assert.assertFalse(result);

        d = DateUtils.addDays(user.getCreate(), 1);

        rule = new DateShouldLessThanRule<User>("create", d);
        result = rule.isSatisfy(user);

        Assert.assertTrue(result);
    }

    /**
     * boolean验证
     */
    @Test
    public void booleanRuleTest() {
        User u = new User();

        BooleanRule<User> rule = new BooleanRule<User>("isMan", true);

        boolean result = rule.isSatisfy(u);
        Assert.assertTrue(result);

        rule = new BooleanRule<User>("isMan", false);
        result = rule.isSatisfy(u);
        Assert.assertFalse(result);
    }

    @Test
    public void aliasTest() {
        User user = new User();
        Boolean validate = user.validate();
    }

}

class UserEntityRule extends EntityRule<User> {

    public UserEntityRule() {
        this.isBlank("name", UserBrokenRuleMessage.USER_NAME_ERROR, "aaaa");
        this.numberShouldEqual("age", 101, UserBrokenRuleMessage.AGE_ERROR, "aaaa");
        this.addRule("age", new IRule<User>() {
            @Override
            public boolean isSatisfy(User model) {
                if (model.getAge() > 100) {
                    return false;
                }
                return true;
            }
        }, UserBrokenRuleMessage.AGE_ERROR, "aaaaa");

    }
}
