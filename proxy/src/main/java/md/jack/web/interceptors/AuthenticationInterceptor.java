package md.jack.web.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import md.jack.model.api.ApiResponse;
import md.jack.util.Constants;
import md.jack.util.FunctionalUtils;
import md.jack.web.validators.ApiValidator;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthenticationInterceptor extends HandlerInterceptorAdapter
{
    private final ApiValidator apiValidator;

    public AuthenticationInterceptor(final ApiValidator apiValidator)
    {
        this.apiValidator = apiValidator;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception
    {
        final boolean isValidVersion = apiValidator.isApiKeyValid(request.getHeader(Constants.Http.Header.TOKEN_HEADER_API_KEY));

        FunctionalUtils.throwableExecuteIf(() -> !isValidVersion, () -> sendResponse(response));

        return isValidVersion;
    }

    private void sendResponse(final HttpServletResponse response) throws IOException
    {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        final String error = new ObjectMapper().writeValueAsString(new ApiResponse(Constants.Http.Response.MESSAGE_INVALID_API_KEY));
        response.getWriter().write(error);
    }
}
