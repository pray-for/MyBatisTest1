import dao.IUserDao;
import domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * mybatis 的入门案例
 * 1.读取配置文件
 * 2.创建一个 SqlSessionFactory 工厂
 * 3.使用工厂生产 SqlSession 对象
 * 4.使用 SqlSession 创建 Dao 接口的代理对象
 * 5.使用代理对象执行方法
 * 6.释放资源
 */
public class MybatisTest {

    private InputStream in;
    private SqlSession sqlSession;
    private IUserDao userDao;

    @Before//用于在测试方法执行之前执行
    public void init() throws Exception{
        //1.读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建一个 SqlSessionFactory 工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.使用工厂生产 SqlSession 对象
        sqlSession = factory.openSession();
        //4.使用 SqlSession 创建 Dao 接口的代理对象
        userDao = sqlSession.getMapper(IUserDao.class);
    }

    @After//用于在测试方法执行之后执行
    public void destory() throws Exception{
        //提交事务
        sqlSession.commit();
        //6.释放资源
        sqlSession.close();
        in.close();
    }

    /**
     * 测试查询所有
     */
    @Test
    public void testFindAll(){
        //5.执行查询所有方法
        List<User> users = userDao.findAll();
        for (User user : users){
            System.out.println(user);
        }
    }


    /**
     * 测试保存操作
     */
    @Test
    public void testSave(){
        User user = new User();
        user.setUsername("ddd");
        user.setAddress("陕西");
        user.setSex("男");
        user.setBirthday(new Date());

        System.out.println("保存之前---" + user);
        //5.执行保存方法
        userDao.saveUser(user);
        System.out.println("保存之后---" + user);

    }


    /**
     * 测试更新操作
     */
    @Test
    public void testUpdate(){
        User user = new User();
        user.setId(23);
        user.setUsername("aaa");
        user.setAddress("陕西");
        user.setSex("女");
        user.setBirthday(new Date());

        userDao.updateUser(user);
    }


    /**
     * 测试删除方法
     */
    @Test
    public void testDelete(){
        userDao.deleteUser(12);
    }


    /**
     * 测试根据id查询用户操作
     */
    @Test
    public void testFindOne(){
        User user = userDao.findById(23);
        System.out.println(user);
    }


    /**
     * 测试模糊查询操作
     */
    @Test
    public void testFindByName(){
        List<User> users = userDao.findByName("%b%");
        for (User user : users){
            System.out.println(user);
        }
    }


    /**
     * 测试查询总记录条数
     */
    @Test
    public void testFindTotal(){
        int count = userDao.findTotal();
        System.out.println(count);
    }
}
