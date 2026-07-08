package ir.security;

public class UserContextHolder {

    private static final ThreadLocal<String> USER_CONTEXT = new ThreadLocal<>();

    public static void setUserId(String userId) {
        USER_CONTEXT.set(userId);
    }

    public static String getUserId() {
        return USER_CONTEXT.get();
    }

    public static void clear() {
        USER_CONTEXT.remove();
    }
}
