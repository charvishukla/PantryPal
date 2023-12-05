import java.util.HashMap;
import java.util.Map;

/**
 * Class MockAuthentication 
 * - This implementation uses a mockUserDatabase instead of making real API calls 
 * to our MongoDB database.
 */
class MockAuthentication extends Authentication {
    
    /**
     * Using a Hashmap to mock the user Database. 
     */
    private Map<String, String> mockUserDatabase = new HashMap<>();

    
    /** 
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @param phone
     * @return boolean
     */
    @Override
    public boolean createUser(String username, String password, String firstName, String lastName, String phone) {
        System.out.println("Authentication.createUser called with username: " + username);
        if (!mockUserDatabase.containsKey(username)) {
            mockUserDatabase.put(username, password);
            return true;
        }
        return false;
    }

    /**
     * @param username 
     * @return boolean
     */
    @Override
    public boolean checkUserExists(String username) {
        System.out.println("Authentication.checkUserExists called with username: " + username);
        return mockUserDatabase.containsKey(username);
    }

    /**
     * @param username 
     * @param password
     * @return boolean
     */
    @Override
    public boolean verifyUser(String username, String password) {
        System.out.println("Authentication.verifyUser called with username: " + username);
        return mockUserDatabase.containsKey(username) && mockUserDatabase.get(username).equals(password);
    }

    /**
     * @param username 
     * @param password 
     * @return UserSession
     */
    @Override
    public UserSession login(String username, String password) {
        System.out.println("Authentication.login called with username: " + username);
        if (verifyUser(username, password)) {
            return new UserSession(username);
        }
        return null;
    }

    /**
     * 
     */
    @Override
    public void markAutoLoginStatus(String username) {
       System.out.println("At class Authentication, markAutoLoginStatus(String): Successfully marked auto login status.");
    }

    @Override
    public String SkipLoginIfRemembered() {
        System.out.println("At class Authentication, SkipLoginIfRemembered(): Successfully skipped login.");
        return null;
    }


    protected String hashPassword(String password) {
        return "hashedString-" + password;
    }

    protected boolean verifyPassword(String password, String hashedPassword) {
        return hashedPassword.equals(hashPassword(password));
    }
}