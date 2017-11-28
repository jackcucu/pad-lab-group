package md.jack.util;

public final class Constants
{
    public static class Http
    {
        public static class Header
        {
            public static final String TOKEN_HEADER_API_KEY = "API-KEY";
        }

        public static class Response
        {
            public static final String MESSAGE_INVALID_API_KEY = "Invalid api key";
            public static final int DEPLOY_READ_TIMEOUT = 60 * 1000 * 60;
        }
    }
}
