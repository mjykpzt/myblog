import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author mjy
 * @create 2020-03-24-0:12
 */
public class T1 {
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("0"));
        boolean matches = bCryptPasswordEncoder.matches("0205",
                "$2a$10$wfP9B8o/gQ/ajdQ.ED87kuqZzOfvbxE9gtmP4MwXdhWJ1FB/VIXxy");
        System.out.println(matches);

    }
}
