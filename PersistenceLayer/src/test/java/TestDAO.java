import com.restaurant.Application;
import com.restaurant.dto.Role;
import com.restaurant.dto.User;
import com.restaurant.service.ProductService;
import com.restaurant.service.ReservationService;
import com.restaurant.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import util.MockedData;

/**
 * Created by Martha on 3/1/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@TestPropertySource(locations="classpath:test.properties")
public class TestDAO {

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    ReservationService reservationService;

    @Test
    public void testServices(){
        Assert.assertNotNull(userService);
        Assert.assertNotNull(productService);
        Assert.assertNotNull(reservationService);
    }

    @Test
    public void createRole(){
        Role role1 = userService.createRole(MockedData.roleName());
        Assert.assertNotNull(role1.getId());
        Role role2 = userService.createRole(MockedData.roleName());
        Assert.assertNotNull(role2.getId());
        Role role3 = userService.createRole(MockedData.roleName());
        Assert.assertNotNull(role3.getId());
    }

    @Test
    public void createUser(){
        Role role1 = userService.createRole(MockedData.roleName());
        Assert.assertNotNull(role1.getId());
        Role role2 = userService.createRole(MockedData.roleName());
        Assert.assertNotNull(role2.getId());
        Role role3 = userService.createRole(MockedData.roleName());
        Assert.assertNotNull(role3.getId());

        User user1 = userService.createUser(MockedData.userName(), MockedData.password(), role1, role2);
        Assert.assertNotNull(user1.getId());
        User user2 = userService.createUser(MockedData.userName(), MockedData.password(), role2, role3);
        Assert.assertNotNull(user2.getId());
        User user3 = userService.createUser(MockedData.userName(), MockedData.password(), role3, role1);
        Assert.assertNotNull(user3.getId());
    }

    @Test
    public void testCreateUserAndRole(){
        // Create roles
        Role role1 = userService.createRole(MockedData.roleName());
        Assert.assertNotNull(role1.getId());
        Role role2 = userService.createRole(MockedData.roleName());
        Assert.assertNotNull(role2.getId());
        Role role3 = userService.createRole(MockedData.roleName());
        Assert.assertNotNull(role3.getId());

        // Create user
        User user1 = userService.createUser(MockedData.userName(), MockedData.password(), role1, role2);
        Assert.assertNotNull(user1.getId());
        User user2 = userService.createUser(MockedData.userName(), MockedData.password(), role2, role3);
        Assert.assertNotNull(user2.getId());
        User user3 = userService.createUser(MockedData.userName(), MockedData.password(), role3, role1);
        Assert.assertNotNull(user3.getId());

        // Override hashcode and equals applied effect
        Assert.assertEquals(user1, userService.findUser(user1.getId()));

        // Return null if duplicate user is attempt to record
        Assert.assertNull(userService.createUser(user1.getUsername(), user1.getPassword(), role1, role2));

        // Delete user
        Assert.assertTrue(userService.deleteUser(user2.getId()));
        Assert.assertNull(userService.findUser(user2.getId()));

    }

}
